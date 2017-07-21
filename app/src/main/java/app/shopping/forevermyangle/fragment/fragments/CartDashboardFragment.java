package app.shopping.forevermyangle.fragment.fragments;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import com.shawnlin.numberpicker.NumberPicker;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;

import app.shopping.forevermyangle.R;
import app.shopping.forevermyangle.activity.CheckoutActivity;
import app.shopping.forevermyangle.activity.DashboardActivity;
import app.shopping.forevermyangle.activity.LoginActivity;
import app.shopping.forevermyangle.adapter.listviewadapter.CartListViewAdpter;
import app.shopping.forevermyangle.fragment.base.BaseFragment;
import app.shopping.forevermyangle.model.products.CartProduct;
import app.shopping.forevermyangle.network.callback.NetworkCallbackListener;
import app.shopping.forevermyangle.network.handler.NetworkHandler;
import app.shopping.forevermyangle.utils.Constants;
import app.shopping.forevermyangle.utils.GlobalData;
import app.shopping.forevermyangle.utils.Network;
import app.shopping.forevermyangle.view.FMAProgressDialog;

/**
 * @class CartDashboardFragment
 * @desc {@link BaseFragment} fragment class for showing & handling cart items.
 */
public class CartDashboardFragment extends BaseFragment implements View.OnClickListener, NetworkCallbackListener {

    /**
     * Class private data members.
     */
    private ListView mListView = null;
    private CartListViewAdpter mAdapter = null;
    private ArrayList<CartProduct> list = new ArrayList<>();
    private int mTotal, mSubTotal;
    private Button mBtnTotalPrice = null;
    private Button mCoupponButton = null;
    private FMAProgressDialog fmaProgressDialog = null;
    private JSONObject mRawJsonResponse = null;
    private LinearLayout mBottomPanel = null;
    private JSONArray jsonArrayUsedby = null;       // holds the used_by field from (list-all coupon) api.

    /**
     * {@link BaseFragment} Class override method(s).
     */
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_cart, container, false);

        view.findViewById(R.id.btn_proceed).setOnClickListener(this);
        mBtnTotalPrice = (Button) view.findViewById(R.id.txt_total_price);
        mCoupponButton = (Button) view.findViewById(R.id.ihaveacoupon);
        mCoupponButton.setOnClickListener(this);

        mBottomPanel = (LinearLayout) view.findViewById(R.id.bottom_panel);
        mListView = (ListView) view.findViewById(R.id.list_view);
        mAdapter = new CartListViewAdpter(getActivity(), this, R.layout.item_list_cart, list);
        mListView.setAdapter(mAdapter);

        fmaProgressDialog = new FMAProgressDialog(getActivity());

        if (!fmaProgressDialog.isVisible()) {
            getCart(null);
        }

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();

        if (GlobalData.login == null) {
            // No login ... hide buttons.
            mBottomPanel.setVisibility(View.GONE);
        } else {
            // App login... show buttons to proceed.
            mBottomPanel.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onDetach() {

        fmaProgressDialog.dismiss();
        super.onDetach();
    }

    /**
     * {@link android.view.View.OnClickListener} listener callback method.
     */
    @Override
    public void onClick(View view) {

        switch (view.getId()) {

            case R.id.ihaveacoupon:      // Input and apply coupon.

                inputCoupon();
                break;
            case R.id.btn_proceed:          // Proceed for checkout.

                proceed();
                break;
            case R.id.img_product:          // Show description.

                showDescription((int) view.getTag());
                break;
            case R.id.txt_product_name:      // Show description.

                showDescription((int) view.getTag());
                break;
            case R.id.txt_product_price:     // Show description.

                showDescription((int) view.getTag());
                break;
            case R.id.btn_move_to_wish:     // Move to wishlist.

                moveToWishlist((int) view.getTag());
                break;
            case R.id.txt_product_qty:      // Select and update quantity from picker.

                updateQuantity((int) view.getTag());
                break;
            case R.id.btn_remove:           // Remove item from cart.

                removeFromCart((int) view.getTag());
                break;
        }
    }

    /**
     * @method proceed
     * @desc Method called on proceed click.
     */
    private void proceed() {

        GlobalData.cartProducts = list;
        GlobalData.TotalPrice = mTotal;
        if (list != null) {
            if (list.size() > 0) {
                startActivity(new Intent(getActivity(), CheckoutActivity.class));
            } else {
                Toast.makeText(getActivity(), "No product in cart.", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(getActivity(), "No product in cart.", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * @param raw JSON response from API.
     * @method applyCoupon
     * @desc Method to apply couponID to the cart and get final price out of it.
     */
    private void applyCoupon(JSONArray raw) {

        try {
            if (raw.length() < 1) {
                Toast.makeText(getActivity(), "Invalid Coupon.", Toast.LENGTH_SHORT).show();
                return;
            }
            JSONObject jsonCoupon = raw.getJSONObject(0);
            int couponID = jsonCoupon.getInt("id");
            jsonArrayUsedby = jsonCoupon.getJSONArray("used_by");
            getCart(couponID);
        } catch (JSONException jsonE) {
            Toast.makeText(getActivity(), "Exception: " + jsonE.getMessage(), Toast.LENGTH_SHORT).show();
        }

    }

    /**
     * @param couponCode
     * @method getCouponID
     * @desc Method to get if from coupon code.
     */
    private void getCouponID(String couponCode) {

        fmaProgressDialog.show();
        String url = Network.URL_GET_COUPON_ID + "?code=" + couponCode;
        NetworkHandler networkHandler = new NetworkHandler();
        networkHandler.httpCreate(5, getActivity(), this, new JSONObject(), url, NetworkHandler.RESPONSE_ARRAY);
        networkHandler.executeGet();
    }

    /**
     * @method inputCoupon
     * @desc Method to input the coupon code from user and call method and proceed to apply.
     */
    private void inputCoupon() {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Enter Coupon Code");

        final EditText input = new EditText(getActivity());
        builder.setView(input);

        builder.setPositiveButton("Apply", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String couponCode = input.getText().toString().trim();
                if (couponCode.isEmpty()) {
                    Toast.makeText(getActivity(), "Empty string cannot be applied as coupon.", Toast.LENGTH_SHORT).show();
                    return;
                }
                getCouponID(couponCode.trim());
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        builder.show();

    }

    /**
     * @param position item index from the list array.
     * @method moveToWishlist
     * @desc Method to move item from cart to wishlist. (Remove from cart and then add to wishlist).
     */
    private void moveToWishlist(int position) {

        try {
            // Check the older response before proceeding.
            int statusCode = mRawJsonResponse.getInt("code");
            String statusMsg = mRawJsonResponse.getString("message");
            if (statusCode == 204) {
                Toast.makeText(getActivity(), "Empty Cart", Toast.LENGTH_SHORT).show();
                return;
            } else if (statusCode != 200) {
                Toast.makeText(getActivity(), "" + statusMsg, Toast.LENGTH_SHORT).show();
                return;
            }

            int userID = GlobalData.jsonUserDetail.getInt("id");
            String key = "" + list.get(position).key.trim();
            JSONObject jsonData = mRawJsonResponse.getJSONObject("data").getJSONObject(key);
            SharedPreferences.Editor se = getActivity().getSharedPreferences(Constants.CACHE_WISHLIST, 0).edit();
            se.putString("" + key, jsonData.toString());
            se.commit();

            int prodID = list.get(position).id;
            JSONObject jsonRequest = new JSONObject();
            jsonRequest.put("productid", "" + prodID);
            jsonRequest.put("userid", "" + userID);

            list.clear();
            mAdapter.notifyDataSetChanged();

            NetworkHandler networkHandler = new NetworkHandler();
            networkHandler.httpCreate(4, getActivity(), this, new JSONObject(), Network.URL_REM_FROM_CART, NetworkHandler.RESPONSE_JSON);
            networkHandler.executePost();


        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(getActivity(), "Exception" + e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * @param position
     * @method showDescription
     * @desc Method to show the description of selected item at index in list array.
     */
    private void showDescription(int position) {

    }

    /**
     * @param position
     * @method removeFromCart
     * @desc Method to remove selected item from cart, presented at the position.
     */
    private void removeFromCart(final int position) {
        Snackbar.make(mListView, "Are you sure ?", Snackbar.LENGTH_SHORT).setAction("Remove", new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                CartProduct cartProduct = list.get(position);
                try {
                    int userID = GlobalData.jsonUserDetail.getInt("id");
                    JSONObject jsonRequest = new JSONObject();
                    jsonRequest.put("productid", "" + cartProduct.id);
                    jsonRequest.put("userid", "" + userID);
                    NetworkHandler networkHandler = new NetworkHandler();
                    fmaProgressDialog.show();
                    networkHandler.httpCreate(3, getActivity(), CartDashboardFragment.this, jsonRequest, Network.URL_REM_FROM_CART, NetworkHandler.RESPONSE_JSON);
                    networkHandler.executePost();
                } catch (Exception e) {
                    e.printStackTrace();
                    Toast.makeText(getActivity(), "Exception:" + e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        }).show();
    }

    /**
     * @param position
     * @method updateQuantity
     * @desc Method to update Quantity of selected item in cart.
     */
    private void updateQuantity(final int position) {

        final NumberPicker numberPicker = (NumberPicker) getActivity().getLayoutInflater().inflate(R.layout.layout_number_picker, null);

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Quantity");
        builder.setView(numberPicker)
                .setCancelable(true)
                .setPositiveButton("Update Qty", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                        try {
                            int userID = GlobalData.jsonUserDetail.getInt("id");
                            int productID = list.get(position).id;
                            int ProductQty = numberPicker.getValue();

                            JSONObject jsonRequest = new JSONObject();
                            jsonRequest.put("userid", "" + userID);
                            jsonRequest.put("productid", "" + productID);
                            jsonRequest.put("productqty", "" + ProductQty);

                            NetworkHandler networkHandler = new NetworkHandler();
                            fmaProgressDialog.show();
                            networkHandler.httpCreate(2, getActivity(), CartDashboardFragment.this, jsonRequest, Network.URL_ADD_TO_CART, NetworkHandler.RESPONSE_JSON);
                            networkHandler.executePost();
                        } catch (JSONException jsonE) {
                            jsonE.getMessage();
                            Toast.makeText(getActivity(), "Need login.", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(getActivity(), LoginActivity.class));
                        } catch (Exception e) {
                            e.printStackTrace();
                            Toast.makeText(getActivity(), "" + e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
        AlertDialog alert = builder.create();
        alert.show();
    }

    /**
     * {@link NetworkCallbackListener} interface callback methods.
     */
    @Override
    public void networkSuccessResponse(int requestCode, JSONObject rawObject, JSONArray rawArray) {

        fmaProgressDialog.hide();

        switch (requestCode) {

            case 1:         // Get from cart.

                showCartData(rawObject);
                break;
            case 2:         // Add to cart.

                try {
                    int code = rawObject.getInt("code");
                    String message = rawObject.getString("message");
                    if (code == 200) {
                        getCart(null);
                    }
                } catch (Exception e) {

                    e.printStackTrace();
                    Toast.makeText(getActivity(), "Unable to process.", Toast.LENGTH_SHORT).show();
                }
                break;
            case 3:         // Remove from cart.

                getCart(null);
                break;
            case 4:         // move to wishlist.

                getCart(null);
                break;
            case 5:

                applyCoupon(rawArray);
                break;
        }
    }

    @Override
    public void networkFailResponse(int requestCode, String message) {

        fmaProgressDialog.hide();

        switch (requestCode) {
            case 1:

                ((DashboardActivity) getActivity()).signalMessage(1);
                break;
            case 2:

                ((DashboardActivity) getActivity()).signalMessage(1);
                break;
            case 3:

                ((DashboardActivity) getActivity()).signalMessage(1);
                break;
            case 4:

                ((DashboardActivity) getActivity()).signalMessage(1);
                break;
            case 5:

                ((DashboardActivity) getActivity()).signalMessage(1);
                break;
        }
    }

    /**
     * @param couponId; null if no coupon to apply.
     * @method getCart
     * @desc Method to fetch cart items from data as raw {@link JSONObject}.
     */
    private void getCart(Integer couponId) {
        try {
            int userID = GlobalData.jsonUserDetail.getInt("id");
            JSONObject jsonRequest = new JSONObject();
            jsonRequest.put("userid", "" + userID);

            // Adding 'used_by' field in Request.
            if (jsonArrayUsedby != null) {
                jsonRequest.put("usedby", jsonArrayUsedby);
            } else {
                jsonRequest.put("usedby", new JSONArray());
            }

            // Adding 'coupon_id' field in Request.
            if (couponId != null) {
                jsonRequest.put("couponId", String.valueOf(couponId));
            } else {
                jsonRequest.put("couponId", "");
            }
            fmaProgressDialog.show();
            NetworkHandler networkHandler = new NetworkHandler();
            networkHandler.httpCreate(1, getActivity(), this, jsonRequest, Network.URL_GET_CART, NetworkHandler.RESPONSE_JSON);
            networkHandler.executePost();
        } catch (JSONException jsonE) {
            jsonE.printStackTrace();

            AlertDialog alertDialog = new AlertDialog.Builder(getActivity()).create();
            alertDialog.setTitle("Alert");
            alertDialog.setMessage("Need login to show cart.");
            alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "Login",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            startActivity(new Intent(getActivity(), LoginActivity.class));
                        }
                    });
            alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, "Not now",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
            alertDialog.show();


            Toast.makeText(getActivity(), "Need login", Toast.LENGTH_SHORT).show();
        } catch (NullPointerException npE) {
            npE.printStackTrace();

            AlertDialog alertDialog = new AlertDialog.Builder(getActivity()).create();
            alertDialog.setTitle("Alert");
            alertDialog.setMessage("Need login to show cart.");
            alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "Login",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            startActivity(new Intent(getActivity(), LoginActivity.class));
                        }
                    });
            alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, "Not now",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
            alertDialog.show();

            Toast.makeText(getActivity(), "Need login", Toast.LENGTH_SHORT).show();

        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(getActivity(), "Exception:" + e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * @param raw
     * @method showCartData
     * @desc Method to parse and populate cart data into listView using custom adapter.
     */
    private void showCartData(JSONObject raw) {

        this.mRawJsonResponse = raw;
        try {
            list.clear();
            mAdapter.notifyDataSetChanged();
            int statusCode = raw.getInt("code");
            String statusMsg = raw.getString("message");
            if (statusCode == 204) {
                Toast.makeText(getActivity(), "Empty Cart", Toast.LENGTH_SHORT).show();
                return;
            } else if (statusCode != 200) {
                Toast.makeText(getActivity(), "" + statusMsg, Toast.LENGTH_SHORT).show();
                return;
            }
            JSONObject jsonData = raw.getJSONObject("data");
            Iterator<String> keyList = jsonData.keys();
            CartProduct cartProduct = null;
            while (keyList.hasNext()) {
                String key = (String) keyList.next();
                if (key.equals("total")) {
                    mTotal = jsonData.getInt("total");
                    continue;
                }
                if (key.equals("subtotal")) {
                    mSubTotal = jsonData.getInt("subtotal");
                    continue;
                }
                JSONObject item = jsonData.getJSONObject(key.trim());
                cartProduct = new CartProduct();
                cartProduct.key = key.trim();
                cartProduct.id = item.getInt("product_id");
                cartProduct.name = item.getString("title");
                cartProduct.qty = item.getString("quantity");
                cartProduct.image = item.getJSONArray("images").getJSONObject(0).getString("src");

                cartProduct.total = item.getInt("line_total");
                cartProduct.sub_total = item.getInt("line_total");
                cartProduct.total_tax = item.getInt("line_total");
                cartProduct.sub_total_tax = item.getInt("line_total");

                list.add(cartProduct);
            }
            mAdapter.notifyDataSetChanged();
            mBtnTotalPrice.setText("Total: AED " + mTotal);
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(getActivity(), "Exception" + e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

}
