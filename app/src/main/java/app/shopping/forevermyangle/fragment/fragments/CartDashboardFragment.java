package app.shopping.forevermyangle.fragment.fragments;


import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.shawnlin.numberpicker.NumberPicker;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;

import app.shopping.forevermyangle.R;
import app.shopping.forevermyangle.activity.DashboardActivity;
import app.shopping.forevermyangle.activity.LoginActivity;
import app.shopping.forevermyangle.adapter.listviewadapter.CartListViewAdpter;
import app.shopping.forevermyangle.fragment.base.BaseFragment;
import app.shopping.forevermyangle.model.products.CartProduct;
import app.shopping.forevermyangle.network.callback.NetworkCallbackListener;
import app.shopping.forevermyangle.network.handler.NetworkHandler;
import app.shopping.forevermyangle.utils.GlobalData;
import app.shopping.forevermyangle.utils.Network;
import app.shopping.forevermyangle.view.FMAProgessDialog;

public class CartDashboardFragment extends BaseFragment implements View.OnClickListener, NetworkCallbackListener {

    /**
     * Class private data members.
     */
    private ListView mListView = null;
    private CartListViewAdpter mAdapter = null;
    private ArrayList<CartProduct> list = new ArrayList<>();
    private int mTotal, mSubTotal;
    private TextView mTxtTotalPrice = null;
    private FMAProgessDialog fmaProgessDialog = null;

    /**
     * {@link BaseFragment} Class override method(s).
     */
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_cart, container, false);

        mTxtTotalPrice = (TextView) view.findViewById(R.id.txt_total_price);
        mListView = (ListView) view.findViewById(R.id.list_view);
        mAdapter = new CartListViewAdpter(getActivity(), this, R.layout.item_list_cart, list);
        mListView.setAdapter(mAdapter);
        fmaProgessDialog = new FMAProgessDialog(getActivity());
        fmaProgessDialog.show();
        getCart("");
        return view;
    }

    @Override
    public void onDetach() {

        fmaProgessDialog.dismiss();
        super.onDetach();
    }

    @Override
    public void onClick(View view) {

        switch (view.getId()) {

            case R.id.img_product:

                showDescription((int) view.getTag());
                break;
            case R.id.txt_product_name:

                showDescription((int) view.getTag());
                break;
            case R.id.txt_product_price:

                showDescription((int) view.getTag());
                break;
            case R.id.btn_move_to_wish:

                moveToWishlist((int) view.getTag());
                break;
            case R.id.txt_product_qty:

                updateQuantity((int) view.getTag());
                break;
            case R.id.btn_remove:

                removeFromCart((int) view.getTag());
                break;
        }
    }

    private void moveToWishlist(int position) {

    }

    private void showDescription(int position) {

    }

    private void removeFromCart(int position) {

    }

    private void updateQuantity(final int position) {

        final NumberPicker numberPicker = (NumberPicker) getActivity().getLayoutInflater().inflate(R.layout.layout_number_picker, null);

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Quantity");
        builder.setView(numberPicker)
                .setCancelable(false)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
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
                            fmaProgessDialog.show();
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

    @Override
    public void networkSuccessResponse(int requestCode, JSONObject rawObject, JSONArray rawArray) {

        fmaProgessDialog.hide();
        switch (requestCode) {
            case 1:

                showCartData(rawObject);
                break;
            case 2:

                getCart("");
                break;
        }
    }

    @Override
    public void networkFailResponse(int requestCode, String message) {

        fmaProgessDialog.hide();
        switch (requestCode) {
            case 1:

                ((DashboardActivity) getActivity()).signalMessage(1);
                break;
            case 2:

                ((DashboardActivity) getActivity()).signalMessage(1);
                break;
        }

    }

    private void getCart(String couponId) {
        try {
            int userID = GlobalData.jsonUserDetail.getInt("id");
            JSONObject jsonRequest = new JSONObject();
            jsonRequest.put("userid", "" + userID);
            jsonRequest.put("couponId", "" + couponId);
            NetworkHandler networkHandler = new NetworkHandler();
            networkHandler.httpCreate(1, getActivity(), this, jsonRequest, Network.URL_GET_CART, NetworkHandler.RESPONSE_JSON);
            networkHandler.executePost();
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(getActivity(), "Exception:" + e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    private void showCartData(JSONObject raw) {

        try {
            list.clear();
            int statusCode = raw.getInt("code");
            String statusMsg = raw.getString("message");
            if (statusCode != 200) {
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
            mTxtTotalPrice.setText("Total: AED " + mTotal);
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(getActivity(), "Exception" + e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

}
