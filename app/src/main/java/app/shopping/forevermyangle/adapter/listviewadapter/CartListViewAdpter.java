package app.shopping.forevermyangle.adapter.listviewadapter;


import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import app.shopping.forevermyangle.activity.WishlistActivity;

public class CartListViewAdpter extends ArrayAdapter {

    private ArrayList<String> list = null;
    private Activity activity = null;
    private int resourceId;
    private LayoutInflater inflater = null;

    public CartListViewAdpter(Activity activity, int resourceId, ArrayList<String> list) {

        super(activity, resourceId, list);

        this.resourceId = resourceId;
        this.activity = activity;
        this.list = list;
        this.inflater = activity.getLayoutInflater();
    }


    /**
     * @class Holder
     * @desc View Holder class to hold the view UI reference.
     */
    public static class Holder {
        public ImageView imageView = null;
        public TextView txtProductName = null;
        public TextView txtProductPrice = null;
        public Button btnM2C = null;
        public Button btnRemove = null;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        View view = (View) this.inflater.inflate(resourceId, null);


        return view;
    }
}
