package app.shopping.forevermyangle.network.handler;


import org.json.JSONObject;

import app.shopping.forevermyangle.network.callback.NetworkCallbackListener;

public class NetworkHandler {

    private String mUrl = null;
    private int requestCode = -1;
    private JSONObject mJsonRequest = null;
    private NetworkCallbackListener mNetworkCallbackListener = null;

    public void httpCreate(int requestCode, NetworkCallbackListener networkCallbackListener, JSONObject jsonRequest, String url) {

        this.mNetworkCallbackListener = networkCallbackListener;
        this.requestCode = requestCode;
        this.mJsonRequest = jsonRequest;
        this.mUrl = url;
    }

    public void executePost() {

    }

    public void executeGet() {

    }

}
