package app.shopping.forevermyangle.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.BottomSheetDialog;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.github.clans.fab.FloatingActionButton;
import com.github.clans.fab.FloatingActionMenu;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;
import java.util.Vector;

import app.shopping.forevermyangle.R;
import app.shopping.forevermyangle.adapter.pageradapter.PagerAdapter;
import app.shopping.forevermyangle.fragment.fragments.ProductImageFragment;
import app.shopping.forevermyangle.network.callback.NetworkCallbackListener;
import app.shopping.forevermyangle.network.handler.NetworkHandler;
import app.shopping.forevermyangle.utils.Constants;
import app.shopping.forevermyangle.utils.GlobalData;
import app.shopping.forevermyangle.utils.Network;

/**
 * @class ProductViewActivity
 * @desc Activity class to show the single product on complete screen.
 */
public class ProductViewActivity extends AppCompatActivity implements View.OnClickListener, NetworkCallbackListener {

    /**
     * Class private data members.
     */
    private JSONObject mProductJsonObject = GlobalData.SelectedProduct;
    private FloatingActionMenu mFabMenu;
    private PagerAdapter mPagerAdapter;
    private ViewPager pager;
    private ImageButton imgBtnShare, imgBtnCart;
    private FloatingActionButton mFabWishlist, mFabReview, mFabDescription, mFabAddToCart;
    private String[] mProductImageUrl = null;
    private int totalImages = 0;
    private int timer = 0;
    private CoordinatorLayout coordinatorLayout = null;

    /**
     * {@link ProductViewActivity} override callback methods.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_view);

        // UI mapping with layout.
        coordinatorLayout = (CoordinatorLayout) findViewById(R.id.coordinator_layout);
        pager = (ViewPager) super.findViewById(R.id.viewpager);
        mFabMenu = (FloatingActionMenu) findViewById(R.id.fab_action_menu);
        mFabWishlist = (FloatingActionButton) findViewById(R.id.fab_wishlist);
        mFabReview = (FloatingActionButton) findViewById(R.id.fab_review);
        mFabAddToCart = (FloatingActionButton) findViewById(R.id.fab_addtocart);
        mFabDescription = (FloatingActionButton) findViewById(R.id.fab_description);
        imgBtnCart = (ImageButton) findViewById(R.id.img_btn_cart);
        imgBtnCart.setVisibility(View.GONE);
        imgBtnShare = (ImageButton) findViewById(R.id.img_btn_share);

        // Add onclick events
        mFabWishlist.setOnClickListener(this);
        mFabReview.setOnClickListener(this);
        mFabDescription.setOnClickListener(this);
        mFabAddToCart.setOnClickListener(this);
        imgBtnCart.setOnClickListener(this);
        imgBtnShare.setOnClickListener(this);

        parseImageUrl();
        initializePaging();
    }

    /**
     * {@link android.view.View.OnClickListener} listener callback method.
     */
    @Override
    public void onClick(View view) {

        mFabMenu.close(true);
        switch (view.getId()) {

            case R.id.fab_addtocart:

                fabAddToCart();
                break;
            case R.id.fab_wishlist:

                fabAddToWishlist();
                break;
            case R.id.fab_review:

                fabShowReview();
                break;
            case R.id.fab_description:

                fabShowDescription();
                break;
            case R.id.img_btn_cart:

                // Btn currently kept hidden.
                // To show item.
                break;
            case R.id.img_btn_share:

                try {
                    Intent i = new Intent(Intent.ACTION_SEND);
                    i.setType("text/plain");
                    i.putExtra(Intent.EXTRA_SUBJECT, "Forever My Angel");
                    String sAux = "Let me recommend you this product: ";
                    sAux = sAux + mProductJsonObject.getString("permalink");
                    i.putExtra(Intent.EXTRA_TEXT, sAux);
                    startActivity(Intent.createChooser(i, "Share via"));
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
        }
    }

    /**
     * Initialise the fragments to be paged.
     */
    private void initializePaging() {

        List<Fragment> fragments = new Vector<>();
        int length = mProductImageUrl.length;
        for (int i = 0; i < length; i++) {
            Bundle args = new Bundle();
            args.putString("url", mProductImageUrl[i].trim());
            args.putInt("position", i + 1);
            args.putInt("total", length);
            fragments.add(Fragment.instantiate(this, ProductImageFragment.class.getName(), args));
        }
        mPagerAdapter = new PagerAdapter(getSupportFragmentManager(), fragments);
        pager.setAdapter(this.mPagerAdapter);
        pager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {

                mFabMenu.setTranslationY(-100.0f);
                Snackbar.make(ProductViewActivity.this.coordinatorLayout, "Image: " + (position + 1) + " of " + totalImages, Snackbar.LENGTH_SHORT).show();
                timer++;
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {

                        timer--;
                        if (timer < 1) {
                            mFabMenu.setTranslationY(0.0f);
                        }
                    }
                }, 1850);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

    }

    /**
     * @return boolean true = success-parsing. false= fail-parsing
     * @method parseImageUrl
     * @desc Method to parse selected product images url into String array for AdapterViewFlipper.
     */
    private boolean parseImageUrl() {

        try {
            totalImages = mProductJsonObject.getJSONArray("images").length();
            mProductImageUrl = new String[totalImages];
            for (int i = 0; i < totalImages; i++) {
                mProductImageUrl[i] = mProductJsonObject.getJSONArray("images").getJSONObject(i).getString("src");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    /**
     * @method fabShowDescription
     * @desc Method to handle login on show description FAB button click.
     */
    private void fabShowDescription() {
        final BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(this);
        View view = (View) getLayoutInflater().inflate(R.layout.layout_description, null);

        TextView txtProdName = (TextView) view.findViewById(R.id.txt_product_name);
        TextView txtProdPrice = (TextView) view.findViewById(R.id.txt_product_price);
        TextView txtProdRating = (TextView) view.findViewById(R.id.txt_prod_rating);
        TextView txtProdDescription = (TextView) view.findViewById(R.id.txt_product_desc);
        LinearLayout containrtLayout = (LinearLayout) view.findViewById(R.id.container_layout);

        try {
            txtProdName.setText(mProductJsonObject.getString("name"));
            txtProdPrice.setText("AED " + mProductJsonObject.getString("price"));
            txtProdRating.setText(mProductJsonObject.getString("average_rating"));
            String html = mProductJsonObject.getString("short_description") + "\n" + mProductJsonObject.getString("description");
            html = html.replaceAll("<(.*?)\\>", " ");//Removes all items in brackets
            html = html.replaceAll("<(.*?)\\\n", " ");//Must be undeneath
            html = html.replaceFirst("(.*?)\\>", " ");//Removes any connected item to the last bracket
            html = html.replaceAll("&nbsp;", " ");
            html = html.replaceAll("&amp;", " ");
            txtProdDescription.setText(html);
            int attrLength = mProductJsonObject.getJSONArray("attributes").length();
            for (int i = 0; i < attrLength; i++) {

                JSONObject jsonAttr = mProductJsonObject.getJSONArray("attributes").getJSONObject(i);
                View attrCell = (View) getLayoutInflater().inflate(R.layout.cell_prod_attr, null);
                TextView txtAttributeKey = (TextView) attrCell.findViewById(R.id.txt_key);
                TextView txtAttributeValue = (TextView) attrCell.findViewById(R.id.txt_key_value);
                txtAttributeKey.setText("" + jsonAttr.getString("name"));
                txtAttributeValue.setText("" + jsonAttr.getJSONArray("options").getString(0));
                containrtLayout.addView(attrCell);
            }

        } catch (Exception e) {
            Toast.makeText(this, "Exception: " + e.getMessage(), Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
        bottomSheetDialog.setContentView(view);
        bottomSheetDialog.show();
    }

    /**
     * @method fabShowReview
     * @desc Method to show Rating Activity.
     */
    private void fabShowReview() {

        startActivity(new Intent(this, RatingActivity.class));
    }

    /**
     * @method fabAddToWishlist
     * @desc Method to add product to Wishlist.
     */
    private void fabAddToWishlist() {

        try {
            SharedPreferences.Editor se = getSharedPreferences(Constants.CACHE_WISHLIST, 0).edit();
            int pid = mProductJsonObject.getInt("id");
            se.putString("" + pid, mProductJsonObject.toString());
            se.commit();
            Toast.makeText(this, "Added to wishlist", Toast.LENGTH_SHORT).show();

        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(this, "" + e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * @method fabAddToCart
     * @desc Method to add product to cart.
     */
    private void fabAddToCart() {

        try {
            if (GlobalData.jsonUserDetail == null) {

                startActivity(new Intent(this, LoginActivity.class));
                Toast.makeText(this, "Need login.", Toast.LENGTH_SHORT).show();
                return;
            }
            int userID = GlobalData.jsonUserDetail.getInt("id");
            int productID = GlobalData.SelectedProduct.getInt("id");
            String ProductQty = "1";

            JSONObject jsonRequest = new JSONObject();
            jsonRequest.put("userid", "" + userID);
            jsonRequest.put("productid", "" + productID);
            jsonRequest.put("productqty", ProductQty);

            NetworkHandler networkHandler = new NetworkHandler();
            networkHandler.httpCreate(1, this, this, jsonRequest, Network.URL_ADD_TO_CART, NetworkHandler.RESPONSE_JSON);
            networkHandler.executePost();
        } catch (JSONException jsonE) {
            jsonE.getMessage();
            Toast.makeText(this, "Need login.", Toast.LENGTH_SHORT).show();
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


        switch (requestCode) {

            case 1:         // Added to cart.

                try {

                    int apiCode = rawObject.getInt("code");
                    String apiMsg = rawObject.getString("message");
                    if (apiCode == 200) {

                        Toast.makeText(this, "Added to cart.", Toast.LENGTH_SHORT).show();

                        // remove from wishlist if present.
                        int productID = GlobalData.SelectedProduct.getInt("id");
                        SharedPreferences.Editor se = getSharedPreferences(Constants.CACHE_WISHLIST, 0).edit();
                        se.remove("" + productID);
                        se.commit();

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
}
