package app.shopping.forevermyangle.adapter.listviewadapter;

import android.app.Activity;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import app.shopping.forevermyangle.R;
import app.shopping.forevermyangle.model.products.Product;
import app.shopping.forevermyangle.utils.Constants;

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
     * Class private UI members.
     */
    private ImageView imgproduct = null;
    private TextView txtProductName = null;
    private TextView txtProductPrice = null;
    private TextView txtproductRate = null;

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

        Holder holder = new Holder();
        View view = convertView;
        if (view == null) {
            view = mInflater.inflate(mResource, null);
            holder.imgproduct = (ImageView) view.findViewById(R.id.img_product);
            holder.txtProductName = (TextView) view.findViewById(R.id.txt_product_name);
            holder.txtProductPrice = (TextView) view.findViewById(R.id.txt_product_price);
            holder.txtproductRate = (TextView) view.findViewById(R.id.txt_rate);
            view.setTag(holder);
        } else {
            holder = (Holder) view.getTag();
        }

        int viewHeight = (Constants.RES_WIDTH - 100) / 2;
        holder.imgproduct.getLayoutParams().height = viewHeight;

        imgproduct = holder.imgproduct;
        txtProductName = holder.txtProductName;
        txtProductPrice = holder.txtProductPrice;
        txtproductRate = holder.txtproductRate;

        Product product = mList.get(position);

        try {
            Picasso.with(mActivity).load(product.image.trim()).into(imgproduct);
            txtProductName.setText(product.name);
            txtProductPrice.setText("AED " + product.price);
            txtproductRate.setText(product.average_rating);
        } catch (Exception e) {
            Snackbar.make(view, position + "" + e.getMessage(), Snackbar.LENGTH_INDEFINITE).show();
            e.printStackTrace();
        }

        return view;
    }
}
