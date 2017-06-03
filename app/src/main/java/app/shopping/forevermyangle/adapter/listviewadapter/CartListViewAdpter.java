package app.shopping.forevermyangle.adapter.listviewadapter;


import android.app.Activity;
import android.content.DialogInterface;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.shawnlin.numberpicker.NumberPicker;

import java.util.ArrayList;

import app.shopping.forevermyangle.R;

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

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NumberPicker numberPicker = (NumberPicker) activity.getLayoutInflater().inflate(R.layout.layout_number_picker, null);

                AlertDialog.Builder builder = new AlertDialog.Builder(activity);
                builder.setTitle("Quantity");
                builder.setView(numberPicker)
                        .setCancelable(false)
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {

                            }
                        });
                AlertDialog alert = builder.create();
                alert.show();
            }
        });
        return view;
    }
}
