package app.shopping.forevermyangle.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ListView;

import java.util.ArrayList;

import app.shopping.forevermyangle.R;
import app.shopping.forevermyangle.adapter.listviewadapter.RatingListAdapter;
import app.shopping.forevermyangle.model.rating.Rating;
import app.shopping.forevermyangle.view.FMAProgressDialog;

public class RatingActivity extends AppCompatActivity {

    private ListView mListView = null;
    private ArrayList<Rating> mList = new ArrayList<>();
    private RatingListAdapter mAdapter = null;
    private FMAProgressDialog fmaProgressDialog = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LOW_PROFILE
                | View.SYSTEM_UI_FLAG_FULLSCREEN
                | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
        setContentView(R.layout.activity_rating);

        fmaProgressDialog = new FMAProgressDialog(this);
        for (int i = 0; i < 100; i++) {
            mList.add(new Rating());
        }
        mListView = (ListView) findViewById(R.id.list_view);
        mAdapter = new RatingListAdapter(this, R.layout.item_list_rating, mList);
        mListView.setAdapter(mAdapter);
    }
}
