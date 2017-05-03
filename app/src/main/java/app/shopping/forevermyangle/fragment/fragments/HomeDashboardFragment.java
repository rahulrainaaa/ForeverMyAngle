package app.shopping.forevermyangle.fragment.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterViewFlipper;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import app.shopping.forevermyangle.R;
import app.shopping.forevermyangle.activity.DashboardActivity;
import app.shopping.forevermyangle.adapter.adapterviewflipper.HomeImageViewFlipperAdapter;
import app.shopping.forevermyangle.adapter.recyclerview.CategoryRecyclerAdapter;
import app.shopping.forevermyangle.fragment.base.BaseFragment;
import app.shopping.forevermyangle.model.base.BaseModel;
import app.shopping.forevermyangle.model.category.Category;
import app.shopping.forevermyangle.model.category.ProductCategory;
import app.shopping.forevermyangle.network.callback.NetworkCallbackListener;
import app.shopping.forevermyangle.network.handler.NetworkHandler;
import app.shopping.forevermyangle.parser.category.CategoryParser;
import app.shopping.forevermyangle.receiver.callback.ConnectionReceiverCallback;
import app.shopping.forevermyangle.utils.Constants;
import app.shopping.forevermyangle.utils.GlobalData;
import app.shopping.forevermyangle.utils.Network;

/**
 * @class HomeDashboardFragment
 * @desc {@link BaseFragment} fragment class for handling home screen.
 */
public class HomeDashboardFragment extends BaseFragment implements View.OnTouchListener, NetworkCallbackListener, ConnectionReceiverCallback {

    /**
     * Class private data members for {@link AdapterViewFlipper}.
     */
    private AdapterViewFlipper mFlipperBanner = null;
    private HomeImageViewFlipperAdapter mFlipperAdapter = null;
    private String[] mBannerImagesUrl = null;

    /**
     * Class private data members for {@link RecyclerView}.
     */
    private RecyclerView mCategoryRecyclerView = null;
    private CategoryRecyclerAdapter mCategoryRecyclerAdapter = null;
    private ArrayList<ProductCategory> mCategoryList = new ArrayList<>();

    /**
     * Class private data members for Home screen images.
     */
    private ImageView imgCategoryItem1, imgCategoryItem2, imgCategoryItem3, imgCategoryItem4;
    private ImageView imgBestSellerItem1, imgBestSellerItem2, imgBestSellerItem3, imgBestSellerItem4;
    private ImageView imgNewArrivalsItem1, imgNewArrivalsItem2, imgNewArrivalsItem3, imgNewArrivalsItem4;
    private LinearLayout mLayoutBestSeller;
    private DashboardActivity activity;

    /**
     * {@link BaseFragment} class methods override.
     */
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        activity = (DashboardActivity) getActivity();

        mBannerImagesUrl = new String[]{

                "https://www.w3schools.com/css/img_fjords.jpg",
                "https://www.w3schools.com/css/paris.jpg"
        };

        mCategoryList = GlobalData.parentCategories;
        View view = inflater.inflate(R.layout.fragment_home_dashboard, container, false);

        // BannerItems Product ImageViews.
        imgCategoryItem1 = (ImageView) view.findViewById(R.id.imgCategoryItem1);
        imgCategoryItem2 = (ImageView) view.findViewById(R.id.imgCategoryItem2);
        imgCategoryItem3 = (ImageView) view.findViewById(R.id.imgCategoryItem3);
        imgCategoryItem4 = (ImageView) view.findViewById(R.id.imgCategoryItem4);

        mLayoutBestSeller = (LinearLayout) view.findViewById(R.id.layout_bestSeller);

        imgBestSellerItem1 = (ImageView) view.findViewById(R.id.imgBestSellerItem1);
        imgBestSellerItem2 = (ImageView) view.findViewById(R.id.imgBestSellerItem2);
        imgBestSellerItem3 = (ImageView) view.findViewById(R.id.imgBestSellerItem3);
        imgBestSellerItem4 = (ImageView) view.findViewById(R.id.imgBestSellerItem4);

        imgNewArrivalsItem1 = (ImageView) view.findViewById(R.id.imgNewArrivals1);
        imgNewArrivalsItem2 = (ImageView) view.findViewById(R.id.imgNewArrivals2);
        imgNewArrivalsItem3 = (ImageView) view.findViewById(R.id.imgNewArrivals3);
        imgNewArrivalsItem4 = (ImageView) view.findViewById(R.id.imgNewArrivals4);

        // Setting up AdapterViewFlipper - Animated image banner.
        mFlipperBanner = (AdapterViewFlipper) view.findViewById(R.id.adapterviewflipper);
        mFlipperAdapter = new HomeImageViewFlipperAdapter(getActivity(), R.layout.image_layout, mBannerImagesUrl);
        mFlipperBanner.setAdapter(mFlipperAdapter);
        mFlipperBanner.setFlipInterval(4000);
        mFlipperBanner.startFlipping();
        mFlipperBanner.setAutoStart(true);
        mFlipperBanner.setOnTouchListener(this);
        mFlipperBanner.setInAnimation(getActivity(), android.R.animator.fade_in);
        mFlipperBanner.setOutAnimation(getActivity(), android.R.animator.fade_out);

        // Setting up Category Recycler View.
        LinearLayoutManager categoryLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        mCategoryRecyclerView = (RecyclerView) view.findViewById(R.id.rv_category);
        mCategoryRecyclerView.setHasFixedSize(true);
        mCategoryRecyclerView.setLayoutManager(categoryLayoutManager);
        mCategoryRecyclerAdapter = new CategoryRecyclerAdapter(getActivity(), mCategoryList);
        mCategoryRecyclerView.setAdapter(mCategoryRecyclerAdapter);
        mCategoryRecyclerAdapter.notifyDataSetChanged();

        // Network Handler to load all categories.
        if (mCategoryList.isEmpty()) {
            activity.showProgressing("Loading.");
            NetworkHandler networkHandler = new NetworkHandler();
            networkHandler.httpCreate(1, getActivity(), this, new JSONObject(), Network.URL_GET_ALL_CATEGORIES, Category.class);
            networkHandler.executeGet();
        } else {
            // Already received category list data.
        }


        // Size handling method depending resolution.
        resolveResolutionDependency();

        return view;
    }

    @Override
    public boolean onTouch(View view, MotionEvent event) {

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mFlipperBanner.showNext();
                break;
        }

        return false;
    }

    @Override
    public void onResume() {
        super.onResume();
        GlobalData.connectionCallback = this;
    }

    @Override
    public void onPause() {
        super.onPause();
        GlobalData.connectionCallback = null;
    }

    /**
     * @method resolveResolutionDependency
     * @desc Method to check and change the dimension of views based on screen resolution.
     */
    private void resolveResolutionDependency() {

        // Fix Banner height.
        int flipperWidth = Integer.valueOf((380 * Constants.RES_WIDTH) / 700);
        mFlipperBanner.getLayoutParams().height = flipperWidth;

        // fix Display Item size.

        int viewHeight = (16 * Constants.RES_WIDTH) / 60;

        imgCategoryItem1.getLayoutParams().height = viewHeight;
        imgCategoryItem2.getLayoutParams().height = viewHeight;
        imgCategoryItem3.getLayoutParams().height = viewHeight;
        imgCategoryItem4.getLayoutParams().height = viewHeight;

        mLayoutBestSeller.getLayoutParams().height = viewHeight * 2;

        imgNewArrivalsItem1.getLayoutParams().height = viewHeight;
        imgNewArrivalsItem2.getLayoutParams().height = viewHeight;
        imgNewArrivalsItem3.getLayoutParams().height = viewHeight;
        imgNewArrivalsItem4.getLayoutParams().height = viewHeight;
    }

    /**
     * {@link NetworkCallbackListener} Callback methods implemented.
     */
    @Override
    public void networkSuccessResponse(int requestCode, BaseModel responseModel) {

        switch (requestCode) {
            case 1:     // Get all categories.

                activity.hideProgressing();
                Category category = (Category) responseModel;
                int responseCode = category.getHttp().getResponse().getCode();
                if (responseCode == 200) {
                    updateCategories(category.getProductCategories());
                } else {
                }
                break;

            case 2:

                break;

            default:    // Unknown request code.

                Toast.makeText(getActivity(), "Unhandled network Request Code.", Toast.LENGTH_SHORT).show();
                break;
        }
    }

    @Override
    public void networkFailResponse(int requestCode, String message) {

        activity.hideProgressing();
        activity.signalMessage(1);
    }

    @Override
    public void networkErrorResponse(int requestCode, String message) {

        activity.hideProgressing();
        activity.signalMessage(1);
    }

    /**
     * @param categoryList List of all categories.
     * @method updateCategories
     * @desc Method to reload all categories into recycler view from web response.
     */
    private void updateCategories(List<ProductCategory> categoryList) {

        // Parse the raw category list.
        CategoryParser categoryParser = new CategoryParser();
        categoryParser.parseRawCategoryList(categoryList);

        mCategoryRecyclerAdapter.notifyDataSetChanged();
    }

    @Override
    public void networkConnectionStateChange() {
        // Network Handler to load all categories.
        if (mCategoryList.isEmpty()) {
            Toast.makeText(activity, "Refreshing Page", Toast.LENGTH_SHORT).show();
            activity.showProgressing("");
            NetworkHandler networkHandler = new NetworkHandler();
            networkHandler.httpCreate(1, getActivity(), this, new JSONObject(), Network.URL_GET_ALL_CATEGORIES, Category.class);
            networkHandler.executeGet();
        } else {
            // Already received category list data.
        }
    }
}
