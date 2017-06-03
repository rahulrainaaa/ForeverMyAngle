package app.shopping.forevermyangle.fragment.fragments;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.ArrayList;

import app.shopping.forevermyangle.R;
import app.shopping.forevermyangle.adapter.listviewadapter.CartListViewAdpter;
import app.shopping.forevermyangle.fragment.base.BaseFragment;

public class CartDashboardFragment extends BaseFragment {

    /**
     * Class private data members.
     */
    private ListView mListView = null;
    private CartListViewAdpter mAdapter = null;
    private ArrayList<String> list = new ArrayList<>();

    /**
     * {@link BaseFragment} Class override method(s).
     */
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_cart, container, false);

        mListView = (ListView) view.findViewById(R.id.list_view);
        for (int i = 0; i < 100; i++) {
            list.add("" + i);
        }
        mAdapter = new CartListViewAdpter(getActivity(), R.layout.item_list_cart, list);
        mListView.setAdapter(mAdapter);

        return view;
    }

}
