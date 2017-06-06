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

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import app.shopping.forevermyangle.R;
import app.shopping.forevermyangle.model.products.CartProduct;

public class CartListViewAdpter extends ArrayAdapter {

    /**
     * Class private data members.
     */
    private ArrayList<CartProduct> list = null;
    private Activity activity = null;
    private int resourceId;
    private LayoutInflater inflater = null;
    private View.OnClickListener mListener = null;

    public CartListViewAdpter(Activity activity, View.OnClickListener listener, int resourceId, ArrayList<CartProduct> list) {

        super(activity, resourceId, list);

        this.resourceId = resourceId;
        this.activity = activity;
        this.list = list;
        this.inflater = activity.getLayoutInflater();
        this.mListener = listener;
    }

    /**
     * @class Holder
     * @desc View Holder class to hold the view UI reference.
     */
    public static class Holder {

        public ImageView imgProduct = null;
        public TextView txtProductName = null;
        public TextView txtProductPrice = null;
        public Button btnM2W = null;
        public Button btnRemove = null;
        public Button btnQty = null;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View view, @NonNull ViewGroup parent) {

        CartProduct cartProduct = list.get(position);
        Holder holder = null;
        if (view == null) {
            view = (View) this.inflater.inflate(resourceId, null);

            holder = new Holder();
            holder.imgProduct = (ImageView) view.findViewById(R.id.img_product);
            holder.txtProductName = (TextView) view.findViewById(R.id.txt_product_name);
            holder.txtProductPrice = (TextView) view.findViewById(R.id.txt_product_price);
            holder.btnM2W = (Button) view.findViewById(R.id.btn_move_to_wish);
            holder.btnQty = (Button) view.findViewById(R.id.txt_product_qty);
            holder.btnRemove = (Button) view.findViewById(R.id.btn_remove);

            holder.imgProduct.setOnClickListener(mListener);
            holder.txtProductName.setOnClickListener(mListener);
            holder.txtProductPrice.setOnClickListener(mListener);
            holder.btnM2W.setOnClickListener(mListener);
            holder.btnQty.setOnClickListener(mListener);
            holder.btnRemove.setOnClickListener(mListener);

            view.setTag(holder);
        } else {
            holder = (Holder) view.getTag();
        }

        try {
            Picasso.with(activity).load(cartProduct.image.trim()).into(holder.imgProduct);
            holder.txtProductName.setText(cartProduct.name);
            holder.txtProductPrice.setText("AED " + cartProduct.total);
            holder.btnQty.setText("Qty: " + cartProduct.qty);
        } catch (Exception e) {
            e.printStackTrace();
        }

        holder.imgProduct.setTag(position);
        holder.txtProductName.setTag(position);
        holder.txtProductPrice.setTag(position);
        holder.btnM2W.setTag(position);
        holder.btnQty.setTag(position);
        holder.btnRemove.setTag(position);

        return view;
    }
}
