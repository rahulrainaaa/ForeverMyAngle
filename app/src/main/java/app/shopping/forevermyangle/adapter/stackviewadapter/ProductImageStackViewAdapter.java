package app.shopping.forevermyangle.adapter.stackviewadapter;

import android.app.Activity;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

import app.shopping.forevermyangle.R;

public class ProductImageStackViewAdapter extends ArrayAdapter<Integer> {

    private Activity activity = null;
    private Integer[] list = null;
    private int resourceID;
    private LayoutInflater inflater = null;

    private static class Holder {
        public ImageView imagwView = null;
    }

    public ProductImageStackViewAdapter(@NonNull Activity activity, @LayoutRes int resource, Integer[] list) {
        super(activity, resource, list);
        this.activity = activity;
        this.list = list;
        this.inflater = activity.getLayoutInflater();
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        Holder holder = null;
        ImageView imageView = null;
        if (convertView == null) {
            holder = new Holder();
            imageView = (ImageView) inflater.inflate(R.layout.image_layout, null);
            holder.imagwView = imageView;
            imageView.setTag(holder);
        } else {
            holder = (Holder) convertView.getTag();
            imageView = holder.imagwView;
        }
        imageView.setImageResource(list[position]);
        return imageView;
    }
}
