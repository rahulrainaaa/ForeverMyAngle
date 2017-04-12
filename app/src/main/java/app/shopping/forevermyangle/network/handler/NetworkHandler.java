package app.shopping.forevermyangle.network.handler;


import android.app.Activity;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;

import org.json.JSONObject;

import app.shopping.forevermyangle.model.base.BaseModel;
import app.shopping.forevermyangle.network.callback.NetworkCallbackListener;

/**
 * @class NetworkHandler
 * @desc Network Handler Class (using Volley library) for the project.
 */
public class NetworkHandler implements Response.Listener<JSONObject>, Response.ErrorListener {

    /**
     * Class private data members
     */
    private Activity mActivity = null;
    private String mUrl = null;
    private int mRequestCode = -1;
    private JSONObject mJsonRequest = null;
    private Class<? extends BaseModel> mClass = null;
    private NetworkCallbackListener mNetworkCallbackListener = null;

    /**
     * @param requestCode             user specific code to determine the request among multiple.
     * @param activity
     * @param networkCallbackListener
     * @param jsonRequest             API request packet.
     * @param url                     API url.
     * @param c                       Response Model class.
     * @method httpCreate
     * @desc Method to initialize the class datamembers and create network handler.
     */
    public void httpCreate(int requestCode, Activity activity, NetworkCallbackListener networkCallbackListener, JSONObject jsonRequest, String url, Class<? extends BaseModel> c) {

        this.mUrl = url;
        this.mActivity = activity;
        this.mRequestCode = requestCode;
        this.mJsonRequest = jsonRequest;
        this.mNetworkCallbackListener = networkCallbackListener;
        this.mClass = c;
    }

    public void executePost() {

    }

    public void executeGet() {

        RequestQueue requestQueue = Volley.newRequestQueue(mActivity);

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(mUrl, this, this);
        requestQueue.add(jsonObjectRequest);

    }

    @Override
    public void onResponse(JSONObject response) {

        Gson gson = new Gson();
        try {
            BaseModel model = gson.fromJson(String.valueOf(response), mClass);
            if (this.mNetworkCallbackListener != null) {
                mNetworkCallbackListener.networkSuccessResponse(this.mRequestCode, model);
            }

        } catch (Exception e) {
            Toast.makeText(mActivity, "Exception: " + e.getMessage(), Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    public void onErrorResponse(VolleyError error) {

        Toast.makeText(mActivity, "" + error.getMessage(), Toast.LENGTH_LONG).show();
    }
}
