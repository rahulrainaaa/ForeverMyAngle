package app.shopping.forevermyangle.activity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;

import app.shopping.forevermyangle.R;
import app.shopping.forevermyangle.adapter.listviewadapter.WishlistListViewAdpter;
import app.shopping.forevermyangle.model.products.WishlistProduct;

public class WishlistActivity extends AppCompatActivity implements AdapterView.OnItemLongClickListener, AdapterView.OnItemClickListener {

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
        listView.setOnItemClickListener(this);
        listView.setOnItemLongClickListener(this);

    }


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {

        final int prodId = list.get(position).prodid;
        final int pos = position;
        Snackbar.make(view, "Are you sure ?", Snackbar.LENGTH_SHORT).setAction("Delete", new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                SharedPreferences.Editor se = getSharedPreferences("wishlist", 0).edit();
                se.remove("" + prodId);
                se.commit();
                list.remove(pos);
                adapter.notifyDataSetChanged();
            }
        }).show();

        return true;
    }

}
