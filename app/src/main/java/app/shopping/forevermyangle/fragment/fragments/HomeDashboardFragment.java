package app.shopping.forevermyangle.fragment.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterViewFlipper;
import android.widget.Toast;

import app.shopping.forevermyangle.R;
import app.shopping.forevermyangle.adapter.HomeImageViewFlipperAdapter;
import app.shopping.forevermyangle.fragment.base.BaseFragment;

public class HomeDashboardFragment extends BaseFragment {

    private AdapterViewFlipper mFlipperBanner = null;
    private HomeImageViewFlipperAdapter mFlipperAdapter = null;
    private String[] mBannerImagesUrl = null;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        mBannerImagesUrl = new String[]{

                "https://www.w3schools.com/css/img_fjords.jpg",
                "https://www.w3schools.com/css/paris.jpg"
        };

        View view = inflater.inflate(R.layout.fragment_home_dashboard, container, false);
        mFlipperBanner = (AdapterViewFlipper) view.findViewById(R.id.adapterviewflipper);
        mFlipperAdapter = new HomeImageViewFlipperAdapter(getActivity(), R.layout.image_layout, mBannerImagesUrl);
        mFlipperBanner.setAdapter(mFlipperAdapter);
        mFlipperBanner.setFlipInterval(4000);
        mFlipperBanner.startFlipping();
        mFlipperBanner.setAutoStart(true);
        return view;
    }


    @Override
    public void onTouchEventCallback(MotionEvent event) {

        Toast.makeText(getActivity(), "on touch", Toast.LENGTH_SHORT).show();
        Log.d("cbbbbbbbbbbbbb","callback");

    }
}
