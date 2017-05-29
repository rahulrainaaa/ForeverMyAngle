package app.shopping.forevermyangle.activity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ListView;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;

import app.shopping.forevermyangle.R;
import app.shopping.forevermyangle.adapter.listviewadapter.WishlistListViewAdpter;
import app.shopping.forevermyangle.model.products.WishlistProduct;

public class WishlistActivity extends AppCompatActivity implements View.OnClickListener {

    private ListView listView;
    private ArrayList<WishlistProduct> list = new ArrayList();
    private WishlistListViewAdpter adapter = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wishlist);
        listView = (ListView) findViewById(R.id.listView);

        SharedPreferences s = getSharedPreferences("wishlist", 0);
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

        adapter = new WishlistListViewAdpter(this, R.layout.item_list_wishlist, list);
        listView.setAdapter(adapter);

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

    private void removeFromCart(View view) {

        final int position = (int) view.getTag();
        final int prodId = list.get(position).prodid;
        Snackbar.make(view, "Are you sure ?", Snackbar.LENGTH_SHORT).setAction("Delete", new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                SharedPreferences.Editor se = getSharedPreferences("wishlist", 0).edit();
                se.remove("" + prodId);
                se.commit();
                list.remove(position);
                adapter.notifyDataSetChanged();
            }
        }).show();
    }

    private void moveToCart(View view) {

        final int position = (int) view.getTag();
        final int prodId = list.get(position).prodid;
        Snackbar.make(listView, "position=" + position + "\nProdID=" + prodId, Snackbar.LENGTH_SHORT).show();

    }

    private void showDescription(View view) {

        final int position = (int) view.getTag();
        final int prodId = list.get(position).prodid;
        Snackbar.make(listView, "position=" + position + "\nProdID=" + prodId, Snackbar.LENGTH_SHORT).show();
    }
}
