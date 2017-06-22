package app.shopping.forevermyangle.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import app.shopping.forevermyangle.R;
import app.shopping.forevermyangle.adapter.listviewadapter.MyOrderListAdapter;
import app.shopping.forevermyangle.model.order.Order;
import app.shopping.forevermyangle.network.callback.NetworkCallbackListener;
import app.shopping.forevermyangle.network.handler.NetworkHandler;
import app.shopping.forevermyangle.utils.GlobalData;
import app.shopping.forevermyangle.utils.Network;
import app.shopping.forevermyangle.view.FMAProgressDialog;


public class MyOrderListActivity extends AppCompatActivity implements NetworkCallbackListener, AdapterView.OnItemClickListener {

    private ListView mListView = null;
    private MyOrderListAdapter mAdapter = null;
    private ArrayList<Order> mList = new ArrayList<>();
    private FMAProgressDialog fmaProgressDialog = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_history);
        fmaProgressDialog = new FMAProgressDialog(this);

        mListView = (ListView) findViewById(R.id.list_view);
        mAdapter = new MyOrderListAdapter(this, R.layout.item_list_order_history, mList);
        mListView.setAdapter(mAdapter);
        mListView.setOnItemClickListener(this);
        fetchList();
    }

    private void fetchList() {

        try {
            String url = Network.URL_GET_ORDERS + "?customer=" + GlobalData.jsonUserDetail.getInt("id");
            NetworkHandler networkHandler = new NetworkHandler();
            networkHandler.httpCreate(1, this, this, new JSONObject(), url, NetworkHandler.RESPONSE_ARRAY);
            fmaProgressDialog.show();
            networkHandler.executeGet();
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(this, "Exception: " + e.getMessage(), Toast.LENGTH_SHORT).show();
        }

    }

    /**
     * {@link NetworkCallbackListener} interface callback methods implemented.
     */
    @Override
    public void networkSuccessResponse(int requestCode, JSONObject rawObject, JSONArray rawArray) {

        fmaProgressDialog.hide();
        switch (requestCode) {

            case 1:

                parseArrayResponse(rawArray);
                break;
        }
    }

    @Override
    public void networkFailResponse(int requestCode, String message) {

        fmaProgressDialog.hide();
        switch (requestCode) {

            case 1:

                Toast.makeText(this, "Connection Failed", Toast.LENGTH_SHORT).show();
                break;
        }
    }

    /**
     * @param rawArray
     * @method parseArrayResponse
     * @desc Method to parse the response array of order history.
     */
    private void parseArrayResponse(JSONArray rawArray) {

        try {
            mList.clear();
            Gson gson = new Gson();
            JSONObject jsonTemp = null;
            int length = rawArray.length();
            for (int i = 0; i < length; i++) {
                jsonTemp = rawArray.getJSONObject(i);
                Order tempOrderHistory = gson.fromJson(jsonTemp.toString(), Order.class);
                mList.add(tempOrderHistory);
            }
            mAdapter.notifyDataSetChanged();
        } catch (Exception e) {

            e.printStackTrace();
            Toast.makeText(this, "Exception: " + e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * {@link android.widget.AdapterView.OnItemClickListener} listener callback methods.
     */
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        GlobalData.orderHistory = mList.get(position);
        startActivity(new Intent(this, OrderHistoryDescriptionActivity.class));
    }
}
