package app.shopping.forevermyangle.adapter.recyclerview;

import android.app.Activity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import app.shopping.forevermyangle.R;

public class CategoryRecyclerAdapter extends RecyclerView.Adapter<CategoryRecyclerAdapter.CategoryItemHolder> {


    /**
     * @class CategoryItemHolder
     * @desc ViewHolder for Recycler View items.
     */
    public static class CategoryItemHolder extends RecyclerView.ViewHolder {
        public CardView cv;
        public TextView name;
        public ImageView image;

        CategoryItemHolder(View itemView) {
            super(itemView);
            cv = (CardView) itemView.findViewById(R.id.cv);
            name = (TextView) itemView.findViewById(R.id.name);
            image = (ImageView) itemView.findViewById(R.id.image);
        }
    }

    public List<String> categories = null;
    public Activity activity = null;
    public int lastPosition;

    /**
     * @constructor SectionAdapter
     * @desc Constructor method for SectionAdapter class.
     */
    public CategoryRecyclerAdapter(Activity activity, List<String> categories) {
        this.activity = activity;
        this.categories = categories;
    }

    @Override
    public CategoryItemHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_rv_category, parent, false);
        CategoryItemHolder categoryHolder = new CategoryItemHolder(v);
        return categoryHolder;
    }

    @Override
    public void onBindViewHolder(final CategoryItemHolder holder, int i) {
        setAnimation(holder.image, i);
        setAnimation(holder.name, i);
    }

    @Override
    public int getItemCount() {
        return categories.size();
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    private void setAnimation(View viewToAnimate, int position) {
        // If the bound view wasn't previously displayed on screen, it's animated
        if (position > lastPosition) {
            Animation animation = AnimationUtils.loadAnimation(this.activity, android.R.anim.slide_in_left);
            viewToAnimate.startAnimation(animation);
            lastPosition = position;
        }
    }

}
