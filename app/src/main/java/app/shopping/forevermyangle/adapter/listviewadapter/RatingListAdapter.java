package app.shopping.forevermyangle.adapter.listviewadapter;

import android.app.Activity;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.RatingBar;
import android.widget.TextView;

import java.util.ArrayList;

import app.shopping.forevermyangle.R;
import app.shopping.forevermyangle.model.rating.Rating;

public class RatingListAdapter extends ArrayAdapter<Rating> {

    /**
     * Class private data members.
     */
    private Activity mActivity = null;
    private int mResource;
    private ArrayList<Rating> mList = null;
    private LayoutInflater mInflater = null;

    /**
     * Temp class private objects.
     */
    private Holder holder = null;
    private Rating rating = null;

    /**
     * @class Holder
     * @desc View Holder class to hold the refernce of listView item.
     */
    public static class Holder {
        public RatingBar ratingBar = null;
        public TextView txtReview = null;
        public TextView txtUser = null;
    }

    /**
     * @param activity
     * @param resource
     * @param list
     * @constructor OrderHistoryListAdapter
     */
    public RatingListAdapter(@NonNull Activity activity, @LayoutRes int resource, ArrayList<Rating> list) {
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
            holder.ratingBar = (RatingBar) view.findViewById(R.id.rating_bar);
            holder.txtReview = (TextView) view.findViewById(R.id.txt_review);
            holder.txtUser = (TextView) view.findViewById(R.id.txt_name);
            view.setTag(holder);
        } else {
            holder = (Holder) view.getTag();
        }

        rating = mList.get(position);
        holder.ratingBar.setRating(rating.getRating());
        holder.txtReview.setText(rating.getReview());
        holder.txtUser.setText("By: " + rating.getName());

        return view;
    }
}
