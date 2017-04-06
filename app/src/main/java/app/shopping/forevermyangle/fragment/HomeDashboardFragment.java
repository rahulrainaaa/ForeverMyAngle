package app.shopping.forevermyangle.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterViewFlipper;

import app.shopping.forevermyangle.R;
import app.shopping.forevermyangle.adapter.HomeImageViewFlipperAdapter;

public class HomeDashboardFragment extends Fragment {

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

}
