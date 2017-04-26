package app.shopping.forevermyangle.fragment.fragments;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import app.shopping.forevermyangle.R;
import app.shopping.forevermyangle.fragment.base.BaseFragment;

/**
 * @Class ConnectionFailFragment
 * @desc Fragment class to be shown as an internet connection fail.
 */
public class ConnectionFailFragment extends BaseFragment {

    /**
     * {@link BaseFragment} Class override method(s).
     */
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_connection_fail, container, false);

        return view;
    }

}