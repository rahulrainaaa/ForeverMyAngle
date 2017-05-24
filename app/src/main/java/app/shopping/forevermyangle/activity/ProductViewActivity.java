package app.shopping.forevermyangle.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.github.clans.fab.FloatingActionButton;
import com.github.clans.fab.FloatingActionMenu;

import org.json.JSONObject;

import java.util.List;
import java.util.Vector;

import app.shopping.forevermyangle.R;
import app.shopping.forevermyangle.adapter.pageradapter.PagerAdapter;
import app.shopping.forevermyangle.fragment.fragments.ProductImageFragment;
import app.shopping.forevermyangle.utils.GlobalData;

/**
 * @class ProductViewActivity
 * @desc Activity class to show the single product on complete screen.
 */
public class ProductViewActivity extends AppCompatActivity implements View.OnClickListener {

    /**
     * Class private data members.
     */
    private JSONObject mProductJsonObject = GlobalData.SelectedProduct;
    private FloatingActionMenu mFabMenu;
    private PagerAdapter mPagerAdapter;
    private ViewPager pager;
    private FloatingActionButton mFabShare, mFabWishlist, mFabCart, mFabReview, mFabDescription;
    private String[] mProductImageUrl = null;
    private float initialX;

    /**
     * {@link ProductViewActivity} override callback methods.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_view);

        // UI mapping with layout.
        pager = (ViewPager) super.findViewById(R.id.viewpager);
        mFabMenu = (FloatingActionMenu) findViewById(R.id.fab_action_menu);
        mFabShare = (FloatingActionButton) findViewById(R.id.fab_share);
        mFabWishlist = (FloatingActionButton) findViewById(R.id.fab_wishlist);
        mFabCart = (FloatingActionButton) findViewById(R.id.fab_cart);
        mFabReview = (FloatingActionButton) findViewById(R.id.fab_review);
        mFabDescription = (FloatingActionButton) findViewById(R.id.fab_description);

        // Add onclick events
        mFabShare.setOnClickListener(this);
        mFabWishlist.setOnClickListener(this);
        mFabCart.setOnClickListener(this);
        mFabReview.setOnClickListener(this);
        mFabDescription.setOnClickListener(this);

        mProductImageUrl = new String[]{

                "https://forevermyangel.com/wp-content/uploads/2017/05/little-silver-earring-main-image.jpg",
                "https://forevermyangel.com/wp-content/uploads/2017/05/little-golden-rose-earring-main-image.jpg",
                "https://forevermyangel.com/wp-content/uploads/2017/04/cuff-earrings-gallery-image1.jpg",
                "https://forevermyangel.com/wp-content/uploads/2017/05/crystal-rhinestone-earring-silver-main-image.jpg",
        };


        // parse the data
//        if (parseImageUrl()) {
//            mFlipperAdapter = new ProductImageViewFlipperAdapter(this, R.layout.image_product, mProductImageUrl);
//            mProductImgFlipper.setAdapter(mFlipperAdapter);
//        } else {
//            Toast.makeText(this, "No image for the product.", Toast.LENGTH_SHORT).show();
//        }

        initialisePaging();
    }

    /**
     * {@link android.view.View.OnClickListener} listener callback method.
     */
    @Override
    public void onClick(View view) {

        mFabMenu.close(true);
        switch (view.getId()) {
            case R.id.fab_share:

                break;
            case R.id.fab_wishlist:

                break;
            case R.id.fab_cart:

                break;
            case R.id.fab_review:

                break;
            case R.id.fab_description:

                break;
        }
    }


    /**
     * Initialise the fragments to be paged
     */
    private void initialisePaging() {

        List<Fragment> fragments = new Vector<>();

        for (int i = 0; i < mProductImageUrl.length; i++) {
            Bundle args = new Bundle();
            args.putString("url", mProductImageUrl[i].trim());
            fragments.add(Fragment.instantiate(this, ProductImageFragment.class.getName(), args));
        }
        mPagerAdapter = new PagerAdapter(getSupportFragmentManager(), fragments);
        pager.setAdapter(this.mPagerAdapter);

    }

    /**
     * @return boolean true = success-parsing. false= fail-parsing
     * @method parseImageUrl
     * @desc Method to parse selected product images url into String array for AdapterViewFlipper.
     */
    private boolean parseImageUrl() {
        int length = 0;
        try {
            length = mProductJsonObject.getJSONArray("images").length();
            mProductImageUrl = new String[length];
            for (int i = 0; i < length; i++) {
                mProductImageUrl[i] = mProductJsonObject.getJSONArray("images").getJSONObject(i).getString("src");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }
}
