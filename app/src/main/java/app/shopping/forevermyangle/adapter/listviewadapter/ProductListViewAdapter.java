package app.shopping.forevermyangle.adapter.listviewadapter;

import android.app.Activity;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import app.shopping.forevermyangle.model.products.Product;

/**
 * @class ProductListViewAdapter
 * @desc Adapter class for showing product list on GridView.
 */
public class ProductListViewAdapter extends ArrayAdapter<Product> {

    /**
     * Private class data members.
     */
    private Activity mActivity = null;
    private LayoutInflater mInflater = null;
    private int mResource;
    private ArrayList<Product> mList = null;

    /**
     * @class Holder
     * @desc Holder class to hold the id (reference) of view.
     */
    private static class Holder {
        private ImageView imgproduct = null;
        private TextView txtProductName = null;
        private TextView txtProductPrice = null;
        private TextView txtproductRate = null;
    }

    /**
     * {@link ArrayAdapter<>} class override methods.
     */
    public ProductListViewAdapter(@NonNull Activity activity, @LayoutRes int resource, ArrayList<Product> list) {
        super(activity, resource, list);

        this.mList = list;
        this.mActivity = activity;
        this.mResource = resource;
        this.mInflater = mActivity.getLayoutInflater();
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        if (convertView == null) {
            convertView = mInflater.inflate(mResource, null);
        }


        return convertView;
    }
}
