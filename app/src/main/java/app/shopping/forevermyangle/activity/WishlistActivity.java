package app.shopping.forevermyangle.activity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONArray;
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
import app.shopping.forevermyangle.view.FMAProgessDialog;

public class WishlistActivity extends AppCompatActivity implements View.OnClickListener, NetworkCallbackListener {

    private ListView listView;
    private ArrayList<WishlistProduct> list = new ArrayList();
    private WishlistListViewAdpter adapter = null;
    private FMAProgessDialog fmaProgessDialog = null;
    private int position = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wishlist);
        listView = (ListView) findViewById(R.id.listView);
        fmaProgessDialog = new FMAProgessDialog(this);
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
    }

    @Override
    public void onClick(View view) {

        switch (view.getId()) {

            case R.id.move_to_cart:

                moveToCart(view);
                break;
            case R.id.remove:

                removeFromCart(view);
                break;
            case R.id.img_product:

                showDescription(view);
                break;
        }
    }

    /**
     * @param view
     * @method remove
     * @desc Method to remove from cart.
     */
    private void removeFromCart(View view) {

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

        fmaProgessDialog.show();
        try {
            int userID = GlobalData.jsonUserDetail.getInt("id");
            position = (int) view.getTag();
            int productID = list.get(position).prodid;
            String ProductQty = "1";

            JSONObject jsonRequest = new JSONObject();
            jsonRequest.put("userid", "" + userID);
            jsonRequest.put("productid", "" + productID);
            jsonRequest.put("productqty", ProductQty);

            NetworkHandler networkHandler = new NetworkHandler();
            networkHandler.httpCreate(1, this, this, jsonRequest, Network.URL_ADD_TO_CART, NetworkHandler.RESPONSE_JSON);
            networkHandler.executePost();

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

        fmaProgessDialog.hide();
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
                        list.clear();
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

        switch (requestCode) {

            case 1:

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
        Snackbar.make(listView, "position=" + position + "\nProdID=" + prodId, Snackbar.LENGTH_SHORT).show();
    }
}
