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

import org.json.JSONObject;

import java.util.ArrayList;

import app.shopping.forevermyangle.R;
import app.shopping.forevermyangle.adapter.adapterviewflipper.HomeImageViewFlipperAdapter;
import app.shopping.forevermyangle.adapter.recyclerview.CategoryRecyclerAdapter;
import app.shopping.forevermyangle.fragment.base.BaseFragment;
import app.shopping.forevermyangle.model.category.Category;
import app.shopping.forevermyangle.network.handler.NetworkHandler;
import app.shopping.forevermyangle.utils.Constants;
import app.shopping.forevermyangle.utils.Network;

public class HomeDashboardFragment extends BaseFragment implements View.OnTouchListener {

    private AdapterViewFlipper mFlipperBanner = null;
    private HomeImageViewFlipperAdapter mFlipperAdapter = null;
    private String[] mBannerImagesUrl = null;

    private RecyclerView mCategoryRecyclerView = null;
    private CategoryRecyclerAdapter mCategoryRecyclerAdapter = null;
    private ArrayList<String> mCategoryList = new ArrayList<>();

    private ImageView imgCategoryItem1, imgCategoryItem2, imgCategoryItem3, imgCategoryItem4;
    private ImageView imgBestSellerItem1, imgBestSellerItem2, imgBestSellerItem3, imgBestSellerItem4;
    private ImageView imgNewArrivalsItem1, imgNewArrivalsItem2, imgNewArrivalsItem3, imgNewArrivalsItem4;
    private LinearLayout mLayoutBestSeller;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        mBannerImagesUrl = new String[]{

                "https://www.w3schools.com/css/img_fjords.jpg",
                "https://www.w3schools.com/css/paris.jpg"
        };

        mCategoryList.add("All");
        for (int i = 0; i < 100; i++) {
            mCategoryList.add("Cat " + i);
        }

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
        NetworkHandler networkHandler = new NetworkHandler();
        networkHandler.httpCreate(1, getActivity(), null, new JSONObject(), Network.URL_GET_ALL_CATEGORIES, Category.class);
        networkHandler.executeGet();

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

}
