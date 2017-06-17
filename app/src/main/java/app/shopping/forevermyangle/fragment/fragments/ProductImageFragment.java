package app.shopping.forevermyangle.fragment.fragments;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import app.shopping.forevermyangle.R;
import app.shopping.forevermyangle.activity.ZoomImageActivity;

public class ProductImageFragment extends Fragment implements View.OnClickListener {

    ImageView view;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        ImageView imgView = (ImageView) inflater.inflate(R.layout.image_layout, container, false);
        String url = getArguments().getString("url");

        try {
            Picasso.with(getActivity()).load(url.trim()).into(imgView);
        } catch (Exception e) {
            e.printStackTrace();
        }
        view = imgView;
        view.setOnClickListener(this);
        return imgView;
    }

    @Override
    public void onClick(View v) {

        Intent intent = new Intent(getActivity(), ZoomImageActivity.class);
        intent.putExtra("url", getArguments().getString("url"));
        startActivity(intent);
    }
}
