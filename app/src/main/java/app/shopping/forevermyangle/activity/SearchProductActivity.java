package app.shopping.forevermyangle.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

import java.util.ArrayList;

import app.shopping.forevermyangle.R;
import app.shopping.forevermyangle.adapter.listviewadapter.ProductListViewAdapter;

/**
 * @class SearchProductActivity
 * @desc Activity to show the searched products.
 */

public class SearchProductActivity extends FragmentActivity implements AdapterView.OnItemClickListener {

    private GridView mProductGridList = null;

    /**
     * {@link android.support.v7.app.AppCompatActivity} Override methods.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        mProductGridList = (GridView) findViewById(R.id.product_listview);
        mProductGridList.setNumColumns(2);
        ArrayList<String> list = new ArrayList<String>();
        for (int i = 0; i < 200; i++) {
            list.add("" + i);
        }
        ProductListViewAdapter adapter = new ProductListViewAdapter(this, R.layout.item_gridview_product, list);
        mProductGridList.setAdapter(adapter);
        mProductGridList.setOnItemClickListener(this);
    }

    /**
     * {@link android.widget.AdapterView.OnItemClickListener} callback methods implemented.
     */
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        startActivity(new Intent(this, ProductDescriptionActivity.class));
    }
}
