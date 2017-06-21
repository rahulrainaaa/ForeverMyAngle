package app.shopping.forevermyangle.adapter.listviewadapter;

import android.app.Activity;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

import app.shopping.forevermyangle.R;
import app.shopping.forevermyangle.model.order.Order;
import app.shopping.forevermyangle.utils.Constants;

/**
 * @class OrderHistoryListAdapter
 * @desc ArrayAdapter class to show the list orders in the order history.
 */
public class OrderHistoryListAdapter extends ArrayAdapter<Order> {

    /**
     * Class private data members.
     */
    private Activity mActivity = null;
    private int mResource;
    private ArrayList<Order> mList = null;
    private LayoutInflater mInflater = null;

    /**
     * Temp class private objects.
     */
    private Holder holder = null;
    private Order order = null;

    /**
     * @class Holder
     * @desc View Holder class to hold the refernce of listView item.
     */
    public static class Holder {
        public TextView txtOrderId = null;
        public TextView txtPrice = null;
        public TextView txtDateTime = null;
        public LinearLayout indicator = null;
    }

    /**
     * @param activity
     * @param resource
     * @param list
     * @constructor OrderHistoryListAdapter
     */
    public OrderHistoryListAdapter(@NonNull Activity activity, @LayoutRes int resource, ArrayList<Order> list) {
        super(activity, resource, list);

        this.mActivity = activity;
        this.mResource = resource;
        this.mList = list;
        this.mInflater = activity.getLayoutInflater();
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View view, @NonNull ViewGroup parent) {
        if (view == null) {

            view = this.mInflater.inflate(mResource, null);
            holder = new Holder();
            holder.txtOrderId = (TextView) view.findViewById(R.id.txt_orderid);
            holder.txtPrice = (TextView) view.findViewById(R.id.txt_price);
            holder.txtDateTime = (TextView) view.findViewById(R.id.txt_datetime);
            holder.indicator = (LinearLayout) view.findViewById(R.id.indicator);
            view.setTag(holder);
        } else {
            holder = (Holder) view.getTag();
        }

        order = mList.get(position);
        holder.txtOrderId.setText("OrderID: " + order.getId());
        holder.txtPrice.setText(order.getCurrency() + " " + order.getTotal());
        holder.txtDateTime.setText("" + order.getDatePaid());

        int color = Constants.COLOR_ORANGE;
        if (order.getStatus().contains("pending")) {

            color = Constants.COLOR_ORANGE;
        } else if (order.getStatus().toLowerCase().contains("onhold")) {

            color = Constants.COLOR_GRAY;
        } else if (order.getStatus().toLowerCase().contains("processing")) {

            color = Constants.COLOR_GREEN;
        } else if (order.getStatus().toLowerCase().contains("completed")) {

            color = Constants.COLOR_BLUE;
        } else if (order.getStatus().toLowerCase().contains("refunded")) {

            color = Constants.COLOR_GRAY;
        } else if (order.getStatus().toLowerCase().contains("failed")) {

            color = Constants.COLOR_YELLOW;
        } else if (order.getStatus().toLowerCase().contains("cancelled")) {

            color = Constants.COLOR_RED;
        }
        holder.indicator.setBackgroundColor(color);
        return view;
    }
}
