package app.shopping.forevermyangle.activity;

import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.StackView;
import android.widget.Toast;

import app.shopping.forevermyangle.R;
import app.shopping.forevermyangle.adapter.stackviewadapter.ProductImageStackViewAdapter;

/**
 * @class ProductDescriptionActivity
 * @desc Activity to show product description.
 */
public class ProductDescriptionActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {

    private ProductImageStackViewAdapter adapter = null;
    private StackView stackView = null;
    private Integer[] i = new Integer[]{
            R.drawable.temp_a,
            R.drawable.temp_b,
            R.drawable.temp_c,
            R.drawable.temp_d,
            R.drawable.temp_e,
            R.drawable.temp_f,
            R.drawable.temp_g,
            R.drawable.temp_h,
            R.drawable.temp_i,
            R.drawable.temp_j,
            R.drawable.temp_k,
            R.drawable.temp_l,
            R.drawable.temp_a,
            R.drawable.temp_b,
            R.drawable.temp_c,
            R.drawable.temp_d,
            R.drawable.temp_e,
            R.drawable.temp_f,
            R.drawable.temp_g,
            R.drawable.temp_h,
            R.drawable.temp_i,
            R.drawable.temp_j,
            R.drawable.temp_k,
            R.drawable.temp_l,
            R.drawable.temp_a,
            R.drawable.temp_b
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Set Custom Title.
        View view = getLayoutInflater().inflate(R.layout.app_title, null);
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(view);
        setContentView(R.layout.activity_dashboard);
        setContentView(R.layout.activity_product_description);
        stackView = (StackView) findViewById(R.id.stackview);

        adapter = new ProductImageStackViewAdapter(this, R.layout.image_layout, i);
        stackView.setAdapter(adapter);

        stackView.setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Snackbar.make(view, "on item click: " + position, Snackbar.LENGTH_SHORT).show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.product_desc_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.option_menu_share:

                Toast.makeText(this, "Share the url this product description.", Toast.LENGTH_SHORT).show();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
