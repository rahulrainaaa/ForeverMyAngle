package app.shopping.forevermyangle.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import app.shopping.forevermyangle.R;
import app.shopping.forevermyangle.adapter.listviewadapter.RatingListAdapter;
import app.shopping.forevermyangle.model.rating.Rating;
import app.shopping.forevermyangle.network.callback.NetworkCallbackListener;
import app.shopping.forevermyangle.network.handler.NetworkHandler;
import app.shopping.forevermyangle.utils.GlobalData;
import app.shopping.forevermyangle.utils.Network;
import app.shopping.forevermyangle.view.FMAProgressDialog;

public class RatingActivity extends AppCompatActivity implements NetworkCallbackListener {

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

        getReviews();
    }

    /**
     * @method getReviews
     * @desc Method to get all reviews of the selected product from internet.
     */
    private void getReviews() {

        try {
            int prodId = GlobalData.SelectedProduct.getInt("id");
            String url = Network.URL_GET_PROD_REVIEWS + "/" + prodId + "/reviews";
            NetworkHandler networkHandler = new NetworkHandler();
            fmaProgressDialog.show();
            networkHandler.httpCreate(1, this, this, new JSONObject(), url, NetworkHandler.RESPONSE_ARRAY);
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(this, "Exception: " + e.getMessage(), Toast.LENGTH_SHORT).show();
        }

    }

    private void parseList(JSONArray raw) {

    }

    /**
     * {@link NetworkCallbackListener} interface callback methods.
     */
    @Override
    public void networkSuccessResponse(int requestCode, JSONObject rawObject, JSONArray rawArray) {

        switch (requestCode) {
            case 1:

                parseList(rawArray);
                break;
        }
    }

    @Override
    public void networkFailResponse(int requestCode, String message) {

        switch (requestCode) {
            case 1:

                Toast.makeText(this, requestCode + " Http failed", Toast.LENGTH_SHORT).show();
                break;
        }
    }
}
