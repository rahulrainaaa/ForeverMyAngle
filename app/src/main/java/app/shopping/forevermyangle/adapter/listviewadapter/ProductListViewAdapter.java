package app.shopping.forevermyangle.adapter.listviewadapter;

import android.app.Activity;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import java.util.ArrayList;

/**
 * @class Product
 */
public class ProductListViewAdapter extends ArrayAdapter<String> {

    Activity mActivity = null;
    LayoutInflater mInflater = null;
    int mResource;
    ArrayList<String> mList = null;

    public ProductListViewAdapter(@NonNull Activity activity, @LayoutRes int resource, ArrayList<String> list) {
        super(activity, resource, list);

        this.mList = list;
        this.mActivity = activity;
        this.mResource = resource;
        this.mInflater = mActivity.getLayoutInflater();
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        View view = mInflater.inflate(mResource, null);

        return view;
    }
}
