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

import java.util.ArrayList;

import app.shopping.forevermyangle.R;
import app.shopping.forevermyangle.adapter.adapterviewflipper.HomeImageViewFlipperAdapter;
import app.shopping.forevermyangle.adapter.recyclerview.CategoryRecyclerAdapter;
import app.shopping.forevermyangle.fragment.base.BaseFragment;

public class HomeDashboardFragment extends BaseFragment implements View.OnTouchListener {

    private AdapterViewFlipper mFlipperBanner = null;
    private HomeImageViewFlipperAdapter mFlipperAdapter = null;
    private String[] mBannerImagesUrl = null;

    private RecyclerView mCategoryRecyclerView = null;
    private CategoryRecyclerAdapter mCategoryRecyclerAdapter = null;
    private ArrayList<String> mCategoryList = new ArrayList<>();


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        mBannerImagesUrl = new String[]{

                "https://www.w3schools.com/css/img_fjords.jpg",
                "http://www.infocurse.com/wp-content/uploads/2014/11/716988156_1368083660.png",
                "https://www.w3schools.com/css/paris.jpg"
        };

        mCategoryList.add("All");
        for (int i = 0; i < 100; i++) {
            mCategoryList.add("Cat " + i);
        }

        View view = inflater.inflate(R.layout.fragment_home_dashboard, container, false);

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

}
