package app.shopping.forevermyangle.activity;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONObject;

import app.shopping.forevermyangle.R;
import app.shopping.forevermyangle.utils.GlobalData;

/**
 * @class ProductDescriptionActivity
 * @desc Activity to show product description.
 */
public class ProductDescriptionActivity extends AppCompatActivity {

    /**
     * Class private data members.
     */
    private JSONObject mProjectObject = GlobalData.SelectedProduct;
    private ImageView mImgProduct = null;
    private LinearLayout mLayoutExtraImageSpace = null;
    private ImageView[] mImgExtra = null;
    private JSONArray mJsonArrayImages = null;
    private String mUrlProdUrl = null;

    /**
     * {@link AppCompatActivity} override callback methods.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Set Custom Title.
        View titleView = getLayoutInflater().inflate(R.layout.app_title, null);
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(titleView);
        setContentView(R.layout.activity_product_description);

        mLayoutExtraImageSpace = (LinearLayout) findViewById(R.id.extra_image_space);
        mImgProduct = (ImageView) findViewById(R.id.img_product);
        try {
            mJsonArrayImages = mProjectObject.getJSONArray("images");
            int length = mJsonArrayImages.length();
            mImgExtra = new ImageView[length];
            for (int i = 0; i < length; i++) {
                View imgView = (View) getLayoutInflater().inflate(R.layout.image_product, null);
                mImgExtra[i] = (ImageView) titleView.findViewById(R.id.img_product_extra);
                mLayoutExtraImageSpace.addView(mImgExtra[i]);
                String imgUrl = mJsonArrayImages.getJSONObject(i).getString("src");
                Picasso.with(this).load(imgUrl.trim()).into(mImgExtra[i]);
                if (i == 0) {
                    mUrlProdUrl = imgUrl;
                    Picasso.with(this).load(mUrlProdUrl.trim()).into(mImgProduct);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }


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
