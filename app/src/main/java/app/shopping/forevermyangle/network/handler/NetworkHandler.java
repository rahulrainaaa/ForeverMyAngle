package app.shopping.forevermyangle.network.handler;


import android.app.Activity;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;

import app.shopping.forevermyangle.network.callback.NetworkCallbackListener;
import app.shopping.forevermyangle.network.custom.FmaJsonArrayRequest;
import app.shopping.forevermyangle.network.custom.FmaJsonObjectRequest;

/**
 * @class NetworkHandler
 * @desc Network Handler Class (using Volley library) for the project.
 */
public class NetworkHandler implements Response.ErrorListener {

    /**
     * Class public static data members.
     */
    public static int RESPONSE_JSON = 1;    // if response is JSONObject.
    public static int RESPONSE_ARRAY = 2;   // if response is JSONArray.

    /**
     * Class private data members
     */
    private Activity mActivity = null;
    private String mUrl = null;
    private int mResponseType = 1;
    private int mRequestCode = -1;
    private JSONObject mJsonRequest = null;
    private NetworkCallbackListener mNetworkCallbackListener = null;

    /**
     * @param requestCode             user specific code to determine the request among multiple.
     * @param activity
     * @param networkCallbackListener
     * @param jsonRequest             API request packet.
     * @param url                     API url.
     * @param responseType            JSONObject or JSONArray.
     * @method httpCreate
     * @desc Method to initialize the class datamembers and create network handler.
     */
    public void httpCreate(int requestCode, Activity activity, NetworkCallbackListener networkCallbackListener, JSONObject jsonRequest, String url, int responseType) {

        this.mUrl = url;
        this.mActivity = activity;
        this.mRequestCode = requestCode;
        this.mJsonRequest = jsonRequest;
        this.mNetworkCallbackListener = networkCallbackListener;
        this.mResponseType = responseType;
    }

    /**
     * @method stopExecute
     * @desc Method to remove callback and stop the network api call execution.
     */
    public void stopExecute() {

        mNetworkCallbackListener = null;

    }

    /**
     * @method executeGet
     * @desc Method to execute POST request with the available JSON data.
     */
    public void executePost() {

        execute(Request.Method.POST);
    }


    /**
     * @method executeGet
     * @desc Method to execute network API call with GET Method via Volley.
     */
    public void executeGet() {

        execute(Request.Method.GET);
    }

    /**
     * @param method {@link com.android.volley.Request.Method} GET/POST.
     * @method execute
     * @desc Method to call REST API with given Http Method.
     */
    private void execute(int method) {
        if (mUrl == null) {
            return;
        } else if (mUrl.isEmpty()) {
            return;
        }

        RequestQueue requestQueue = Volley.newRequestQueue(mActivity);

        if (mResponseType == 1) {           // JSONObject response.
            FmaJsonObjectRequest fmaJsonObjectRequest = new FmaJsonObjectRequest(method, mUrl, mJsonRequest.toString(), new JsonObjectResponse(), this);
            fmaJsonObjectRequest.setRetryPolicy(new DefaultRetryPolicy(
                    30000,
                    DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                    DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
            requestQueue.add(fmaJsonObjectRequest);
        } else if (mResponseType == 2) {    // JSONArray response.
            FmaJsonArrayRequest fmaJsonArrayRequest = new FmaJsonArrayRequest(method, mUrl, mJsonRequest.toString(), new JsonArrayResponse(), this);
            fmaJsonArrayRequest.setRetryPolicy(new DefaultRetryPolicy(
                    30000,
                    DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                    DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
            requestQueue.add(fmaJsonArrayRequest);
        }
    }

    /**
     * @class JsonObjectResponse
     * @desc Class to handle the success response in case of JSONObject response.
     */
    private class JsonObjectResponse implements Response.Listener<JSONObject> {

        /**
         * {@link com.android.volley.Response.Listener} interface implemented method.
         */
        @Override
        public void onResponse(JSONObject response) {

            if (NetworkHandler.this.mNetworkCallbackListener != null) {
                mNetworkCallbackListener.networkSuccessResponse(NetworkHandler.this.mRequestCode, response, null);
            }
        }
    }

    /**
     * @class JSONArrayResponse
     * @desc Class to handle the success response in case of JSONArray response.
     */
    private class JsonArrayResponse implements Response.Listener<JSONArray> {

        /**
         * {@link com.android.volley.Response.Listener} interface implemented method.
         */
        @Override
        public void onResponse(JSONArray response) {

            if (NetworkHandler.this.mNetworkCallbackListener != null) {
                try {
                    mNetworkCallbackListener.networkSuccessResponse(NetworkHandler.this.mRequestCode, null, response);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * {@link com.android.volley.Response.ErrorListener} interface method implemented.
     */
    @Override
    public void onErrorResponse(VolleyError error) {

        String responseMessage = null;
        if (error.getCause() == null) {
            responseMessage = error.getMessage();       // API authentication fail error.
        } else {
            responseMessage = error.getCause().getMessage();    // Network issue.
        }

        if (this.mNetworkCallbackListener != null) {
            try {
                mNetworkCallbackListener.networkFailResponse(this.mRequestCode, responseMessage);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
