package app.shopping.forevermyangle.adapter.listviewadapter;


import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import app.shopping.forevermyangle.R;
import app.shopping.forevermyangle.model.products.WishlistProduct;

public class WishlistListViewAdpter extends ArrayAdapter {

    private ArrayList<WishlistProduct> list = null;
    private Activity activity = null;
    private int resourceId;
    private LayoutInflater inflater = null;

    public WishlistListViewAdpter(Activity activity, int resourceId, ArrayList<WishlistProduct> list) {

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
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View view, @NonNull ViewGroup parent) {

        Holder holder = null;
        if (view == null) {
            view = (View) this.inflater.inflate(resourceId, null);
            holder = new Holder();
            holder.imageView = (ImageView) view.findViewById(R.id.img_product);
            holder.txtProductName = (TextView) view.findViewById(R.id.txt_product_name);
            holder.txtProductPrice = (TextView) view.findViewById(R.id.txt_product_price);
            view.setTag(holder);
        } else {
            holder = (Holder) view.getTag();
        }
        WishlistProduct wishlistProduct = list.get(position);
        holder.txtProductName.setText(wishlistProduct.prodName);
        holder.txtProductPrice.setText("AED" + wishlistProduct.prodPrice);
        try {
            Picasso.with(activity).load(wishlistProduct.prodImage.trim()).into(holder.imageView);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return view;
    }
}
