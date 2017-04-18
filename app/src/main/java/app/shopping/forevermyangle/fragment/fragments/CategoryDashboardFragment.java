package app.shopping.forevermyangle.fragment.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import app.shopping.forevermyangle.R;
import app.shopping.forevermyangle.adapter.expandablelistview.CategoryExpandableListAdapter;
import app.shopping.forevermyangle.fragment.base.BaseFragment;
import app.shopping.forevermyangle.model.category.ProductCategory;
import app.shopping.forevermyangle.utils.GlobalData;

/**
 * @class CategoryDashboardFragment
 * @desc {@link BaseFragment} Fragment on dashboard scree to handle the category list.
 */
public class CategoryDashboardFragment extends BaseFragment {

    private ExpandableListView mCategoryExpandableListView = null;
    private ExpandableListAdapter expandableListAdapter = null;
    private List<ProductCategory> mCategoryList = null;
    private HashMap<ProductCategory, List<ProductCategory>> mSubCategoriesMap = null;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_category_dashboard, container, false);
        mCategoryExpandableListView = (ExpandableListView) view.findViewById(R.id.category_expandablelist);

        mSubCategoriesMap = GlobalData.category;
        mCategoryList = new ArrayList<>();
        Iterator<ProductCategory> iterator = mSubCategoriesMap.keySet().iterator();
        while (iterator.hasNext()) {
            mCategoryList.add(iterator.next());
        }
        expandableListAdapter = new CategoryExpandableListAdapter(getActivity(), mCategoryList, mSubCategoriesMap);
        mCategoryExpandableListView.setAdapter(expandableListAdapter);
        mCategoryExpandableListView.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {

            @Override
            public void onGroupExpand(int groupPosition) {
                Toast.makeText(getActivity(),
                        mCategoryList.get(groupPosition) + " List Expanded.",
                        Toast.LENGTH_SHORT).show();
            }
        });

        mCategoryExpandableListView.setOnGroupCollapseListener(new ExpandableListView.OnGroupCollapseListener() {

            @Override
            public void onGroupCollapse(int groupPosition) {
                Toast.makeText(getActivity(),
                        mCategoryList.get(groupPosition) + " List Collapsed.",
                        Toast.LENGTH_SHORT).show();

            }
        });

        mCategoryExpandableListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v,
                                        int groupPosition, int childPosition, long id) {
                Toast.makeText(
                        getActivity(),
                        mCategoryList.get(groupPosition)
                                + " -> "
                                + mSubCategoriesMap.get(
                                mCategoryList.get(groupPosition)).get(
                                childPosition), Toast.LENGTH_SHORT
                ).show();
                return false;
            }
        });

        return view;
    }
}
