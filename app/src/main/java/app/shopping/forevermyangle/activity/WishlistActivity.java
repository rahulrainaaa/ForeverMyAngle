package app.shopping.forevermyangle.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;

import app.shopping.forevermyangle.R;
import app.shopping.forevermyangle.adapter.listviewadapter.WishlistListViewAdpter;
import app.shopping.forevermyangle.model.products.WishlistProduct;
import app.shopping.forevermyangle.network.callback.NetworkCallbackListener;
import app.shopping.forevermyangle.network.handler.NetworkHandler;
import app.shopping.forevermyangle.utils.Constants;
import app.shopping.forevermyangle.utils.GlobalData;
import app.shopping.forevermyangle.utils.Network;
import app.shopping.forevermyangle.view.FMAProgressDialog;

/**
 * @class WishlistActivity
 * @desc Activity class to handler the list of Wishlist items.
 */
public class WishlistActivity extends AppCompatActivity implements View.OnClickListener, NetworkCallbackListener {

    /**
     * Class private data members.
     */
    private ListView listView;
    private ArrayList<WishlistProduct> list = new ArrayList();
    private WishlistListViewAdpter adapter = null;
    private FMAProgressDialog fmaProgressDialog = null;
    private int position = -1;

    /**
     * {@link AppCompatActivity} override callback methods.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wishlist);
        listView = (ListView) findViewById(R.id.listView);
        fmaProgressDialog = new FMAProgressDialog(this);
        fetchDataFromCache();

        adapter = new WishlistListViewAdpter(this, R.layout.item_list_wishlist, list);
        listView.setAdapter(adapter);

    }

    /**
     * @metthod fetchDataFromCache
     * @desc Method to fetch data from cache and load into data-structure.
     */
    private void fetchDataFromCache() {
        list.clear();
        SharedPreferences s = getSharedPreferences(Constants.CACHE_WISHLIST, 0);
        Map<String, ?> map = s.getAll();

        try {
            Iterator<String> iterator = map.keySet().iterator();
            while (iterator.hasNext()) {

                String key = iterator.next();
                String value = (String) map.get(key.trim());

                JSONObject json = new JSONObject(value.trim());
                WishlistProduct wishlistProduct = new WishlistProduct();

                wishlistProduct.prodid = json.getInt("id");
                wishlistProduct.prodName = json.getString("name");
                wishlistProduct.prodPrice = json.getString("price");
                wishlistProduct.prodLink = json.getString("permalink");
                wishlistProduct.prodDescription = json.getString("short_description");
                wishlistProduct.prodImage = json.getJSONArray("images").getJSONObject(0).getString("src");

                list.add(wishlistProduct);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (list.size() < 1) {

            finish();
            Toast.makeText(this, "No product in wishlist.", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * {@link android.view.View.OnClickListener} listener callback methods.
     */
    @Override
    public void onClick(View view) {

        switch (view.getId()) {

            case R.id.move_to_cart:

                moveToCart(view);
                break;
            case R.id.remove:

                removeFromWishlist(view);
                break;
            case R.id.img_product:

                showDescription(view);
                break;
        }
    }

    /**
     * @param view
     * @method removeFromWishlist
     * @desc Method to remove from Wishlist.
     */
    private void removeFromWishlist(View view) {

        final int position = (int) view.getTag();
        final int prodId = list.get(position).prodid;
        Snackbar.make(view, "Are you sure ?", Snackbar.LENGTH_SHORT).setAction("Delete", new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                SharedPreferences.Editor se = getSharedPreferences(Constants.CACHE_WISHLIST, 0).edit();
                se.remove("" + prodId);
                se.commit();
                list.remove(position);
                adapter.notifyDataSetChanged();
            }
        }).show();
    }

    /**
     * @method moveToCart
     * @desc Method to move product from wishlist to cart.
     */
    private void moveToCart(View view) {

        try {
            int userID = GlobalData.jsonUserDetail.getInt("id");
            position = (int) view.getTag();
            int productID = list.get(position).prodid;
            String ProductQty = "1";

            JSONObject jsonRequest = new JSONObject();
            jsonRequest.put("userid", "" + userID);
            jsonRequest.put("productid", "" + productID);
            jsonRequest.put("productqty", ProductQty);

            fmaProgressDialog.show();
            NetworkHandler networkHandler = new NetworkHandler();
            networkHandler.httpCreate(1, this, this, jsonRequest, Network.URL_ADD_TO_CART, NetworkHandler.RESPONSE_JSON);
            networkHandler.executePost();

        } catch (JSONException jsonE) {

            jsonE.printStackTrace();
            Toast.makeText(this, "Need Login.", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(this, LoginActivity.class));
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(this, "" + e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
        }

    }

    /**
     * {@link NetworkCallbackListener} interface callback methods.
     */
    @Override
    public void networkSuccessResponse(int requestCode, JSONObject rawObject, JSONArray rawArray) {

        fmaProgressDialog.hide();
        switch (requestCode) {

            case 1:         // Moved to cart.

                try {
                    int apiCode = rawObject.getInt("code");
                    String apiMsg = rawObject.getString("message");
                    if (apiCode == 200) {

                        Toast.makeText(this, "Moved to cart.", Toast.LENGTH_SHORT).show();

                        // remove from wishlist if present.
                        int productID = list.get(position).prodid;
                        SharedPreferences.Editor se = getSharedPreferences(Constants.CACHE_WISHLIST, 0).edit();
                        se.remove("" + productID);
                        se.commit();

                        fetchDataFromCache();
                        adapter.notifyDataSetChanged();

                    } else {
                        Toast.makeText(this, apiCode + "::" + apiMsg, Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {

                    e.printStackTrace();
                    Toast.makeText(this, "" + e.getMessage(), Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }

    @Override
    public void networkFailResponse(int requestCode, String message) {

        fmaProgressDialog.hide();
        switch (requestCode) {

            case 1:

                fetchDataFromCache();
                adapter.notifyDataSetChanged();
                Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
                break;
        }
    }

    /**
     * @method showDescription
     * @desc Method to handle the show description of the page.
     */
    private void showDescription(View view) {

        final int position = (int) view.getTag();
        final int prodId = list.get(position).prodid;

        SharedPreferences s = getSharedPreferences(Constants.CACHE_WISHLIST, 0);
        String value = s.getString("" + prodId, "{}");

        try {
            JSONObject json = new JSONObject(value.trim());
            GlobalData.SelectedProduct = json;
            startActivity(new Intent(this, ProductViewActivity.class));
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(this, "Exception" + e.getMessage(), Toast.LENGTH_SHORT).show();
        }

        Snackbar.make(listView, "position=" + position + "\nProdID=" + prodId, Snackbar.LENGTH_SHORT).show();
    }
}
