package app.shopping.forevermyangle.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import app.shopping.forevermyangle.R;

public class HomeDashboardFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_home_dashboard, container, false);
        TextView textView = (TextView) view.findViewById(R.id.textView);
        textView.setText("Home Dashboard Fragment");
        return view;
    }
}
