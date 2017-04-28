package app.shopping.forevermyangle.adapter.expandablelistview;


import android.content.Context;
import android.graphics.Typeface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.HashMap;
import java.util.List;

import app.shopping.forevermyangle.R;
import app.shopping.forevermyangle.model.category.ProductCategory;
import app.shopping.forevermyangle.view.RoundedImageView;

/**
 * @class CategoryExpandableListAdapter
 * @desc {@link BaseExpandableListAdapter} adapter class for showing expandable listview to show categories and sub-categories.
 */
public class CategoryExpandableListAdapter extends BaseExpandableListAdapter {

    /**
     * Class private data members.`
     */
    private Context mContext = null;
    private List<ProductCategory> mTitleCategoryList = null;
    private HashMap<Integer, List<ProductCategory>> mSubCategoryMap = null;

    /**
     * @param context
     * @param expandableListTitle
     * @param expandableListDetail
     * @constructor CategoryExpandableListAdapter
     * @desc Constructor class to initialize the data object members.
     */
    public CategoryExpandableListAdapter(Context context, List<ProductCategory> expandableListTitle,
                                         HashMap<Integer, List<ProductCategory>> expandableListDetail) {
        this.mContext = context;
        this.mTitleCategoryList = expandableListTitle;
        this.mSubCategoryMap = expandableListDetail;
    }

    /**
     * {@link BaseExpandableListAdapter} class override methods.
     */

    @Override
    public long getChildId(int titlePosition, int childPosition) {
        return this.mSubCategoryMap.get(titlePosition).size();
    }

    @Override
    public View getChildView(int titlePosition, final int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {

        String subCategoryText = mSubCategoryMap.get(titlePosition).get(childPosition).getName();
        String subCategoryImage = mSubCategoryMap.get(titlePosition).get(childPosition).getImage();

        if (convertView == null) {
            LayoutInflater layoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.elv_subcategory_item, null);
        }
        TextView subCategoryTextView = (TextView) convertView.findViewById(R.id.expandedListItem);
        subCategoryTextView.setText(subCategoryText);

        RoundedImageView subCategoryImageView = (RoundedImageView) convertView.findViewById(R.id.img_subcategory);
        subCategoryImageView.setImageResource(R.drawable.default_image);
        try {
            Picasso.with(mContext).load(subCategoryImage).into(subCategoryImageView);
        } catch (Exception e) {
            Log.d("CategoExpandListAdapter", "Picasso Error while loading " + subCategoryImage + " into RoundedImageView.");
        }


        return convertView;
    }

    @Override
    public int getChildrenCount(int listPosition) {
        return mSubCategoryMap.get(listPosition).size();
    }

    @Override
    public Object getGroup(int listPosition) {
        return mTitleCategoryList.get(listPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return null;
    }

    @Override
    public int getGroupCount() {
        return mTitleCategoryList.size();
    }

    @Override
    public long getGroupId(int listPosition) {
        return listPosition;
    }

    @Override
    public View getGroupView(int listPosition, boolean isExpanded, View convertView, ViewGroup parent) {

        String listTitle = mTitleCategoryList.get(listPosition).getName();
        if (convertView == null) {
            LayoutInflater layoutInflater = (LayoutInflater) this.mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.elv_category_item, null);
        }
        TextView listTitleTextView = (TextView) convertView.findViewById(R.id.listTitle);
        listTitleTextView.setTypeface(null, Typeface.BOLD);
        listTitleTextView.setText(listTitle);
        return convertView;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public boolean isChildSelectable(int listPosition, int expandedListPosition) {
        return true;
    }
}