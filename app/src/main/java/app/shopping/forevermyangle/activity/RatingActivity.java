package app.shopping.forevermyangle.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import com.google.gson.Gson;

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

/**
 * @class RatingActivity
 * @desc Activity class for showing Ratings and reviews of a product.
 */
public class RatingActivity extends AppCompatActivity implements NetworkCallbackListener {

    /**
     * Private class data members.
     */
    private ListView mListView = null;
    private ArrayList<Rating> mList = new ArrayList<>();
    private RatingListAdapter mAdapter = null;
    private FMAProgressDialog fmaProgressDialog = null;

    /**
     * {@link AppCompatActivity} override methods.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LOW_PROFILE
                | View.SYSTEM_UI_FLAG_FULLSCREEN
                | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);

        setContentView(R.layout.activity_rating);
        fmaProgressDialog = new FMAProgressDialog(this);
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
            networkHandler.executeGet();
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(this, "Exception: " + e.getMessage(), Toast.LENGTH_SHORT).show();
        }

    }

    /**
     * @param raw {@link JSONArray}
     * @method parseList
     * @desc Method to parse the http response array of reviews.
     */
    private void parseList(JSONArray raw) {

        Rating rating = null;
        mList.clear();
        Gson gson = new Gson();
        try {
            int length = raw.length();
            if (length < 1) {
                Toast.makeText(this, "No Review Available.", Toast.LENGTH_SHORT).show();
                finish();
            }
            for (int i = 0; i < length; i++) {
                JSONObject jsonObject = raw.getJSONObject(i);
                rating = gson.fromJson(jsonObject.toString(), Rating.class);
                mList.add(rating);
            }
            mAdapter.notifyDataSetChanged();

        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(this, "Exception: " + e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * {@link NetworkCallbackListener} interface callback methods.
     */
    @Override
    public void networkSuccessResponse(int requestCode, JSONObject rawObject, JSONArray rawArray) {

        fmaProgressDialog.hide();
        switch (requestCode) {      // Fetched all ratings.
            case 1:

                parseList(rawArray);
                break;
        }
    }

    @Override
    public void networkFailResponse(int requestCode, String message) {

        fmaProgressDialog.hide();
        switch (requestCode) {      // Get all reviews.
            case 1:

                Toast.makeText(this, requestCode + " Http failed", Toast.LENGTH_SHORT).show();
                break;
        }
    }
}
