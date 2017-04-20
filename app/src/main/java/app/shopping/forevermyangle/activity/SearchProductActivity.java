package app.shopping.forevermyangle.activity;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.widget.ListView;

import java.util.ArrayList;

import app.shopping.forevermyangle.R;
import app.shopping.forevermyangle.adapter.listviewadapter.ProductListViewAdapter;

/**
 * @class SearchProductActivity
 * @desc Activity to show the searched products.
 */

public class SearchProductActivity extends FragmentActivity {

    private ListView productList = null;

    /**
     * {@link android.support.v7.app.AppCompatActivity} Override methods.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        productList = (ListView) findViewById(R.id.product_listview);
        ArrayList<String> list = new ArrayList<String>();
        for (int i = 0; i < 200; i++) {
            list.add("" + i);
        }
        ProductListViewAdapter adapter = new ProductListViewAdapter(this, R.layout.item_listview_product, list);
        productList.setAdapter(adapter);
    }
}
