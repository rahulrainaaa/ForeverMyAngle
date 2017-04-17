package app.shopping.forevermyangle.adapter.expandablelistview;


import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import java.util.HashMap;
import java.util.List;

import app.shopping.forevermyangle.R;

/**
 * @class CategoryExpandableListAdapter
 * @desc {@link BaseExpandableListAdapter} adapter class for showing expandable listview to show categories and sub-categories.
 */
public class CategoryExpandableListAdapter extends BaseExpandableListAdapter {

    /**
     * Class private data members.`
     */
    private Context mContext = null;
    private List<String> mExpandableListTitle = null;
    private HashMap<String, List<String>> mExpandableListDetail = null;

    /**
     * @param context
     * @param expandableListTitle
     * @param expandableListDetail
     * @constructor CategoryExpandableListAdapter
     * @desc Constructor class to initialize the data object members.
     */
    public CategoryExpandableListAdapter(Context context, List<String> expandableListTitle,
                                         HashMap<String, List<String>> expandableListDetail) {
        this.mContext = context;
        this.mExpandableListTitle = expandableListTitle;
        this.mExpandableListDetail = expandableListDetail;
    }

    /**
     * {@link BaseExpandableListAdapter} class override methods.
     */
    @Override
    public Object getChild(int listPosition, int expandedListPosition) {
        return this.mExpandableListDetail.get(this.mExpandableListTitle.get(listPosition))
                .get(expandedListPosition);
    }

    @Override
    public long getChildId(int listPosition, int expandedListPosition) {
        return expandedListPosition;
    }

    @Override
    public View getChildView(int listPosition, final int expandedListPosition,
                             boolean isLastChild, View convertView, ViewGroup parent) {
        final String expandedListText = (String) getChild(listPosition, expandedListPosition);
        if (convertView == null) {
            LayoutInflater layoutInflater = (LayoutInflater) this.mContext
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.elv_subcategory_item, null);
        }
        TextView expandedListTextView = (TextView) convertView
                .findViewById(R.id.expandedListItem);
        expandedListTextView.setText(expandedListText);
        return convertView;
    }

    @Override
    public int getChildrenCount(int listPosition) {
        return this.mExpandableListDetail.get(this.mExpandableListTitle.get(listPosition))
                .size();
    }

    @Override
    public Object getGroup(int listPosition) {
        return this.mExpandableListTitle.get(listPosition);
    }

    @Override
    public int getGroupCount() {
        return this.mExpandableListTitle.size();
    }

    @Override
    public long getGroupId(int listPosition) {
        return listPosition;
    }

    @Override
    public View getGroupView(int listPosition, boolean isExpanded,
                             View convertView, ViewGroup parent) {
        String listTitle = (String) getGroup(listPosition);
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