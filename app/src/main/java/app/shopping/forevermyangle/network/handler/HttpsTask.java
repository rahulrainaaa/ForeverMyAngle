package app.shopping.forevermyangle.network.handler;

import android.app.Activity;
import android.os.AsyncTask;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

import app.shopping.forevermyangle.network.callback.NetworkCallbackListener;

public class HttpsTask extends AsyncTask<String, String, String> {

    /**
     * Class constants.
     */
    public static final int RESPONSE_TYPE_OBJECT = 1;
    public static final int RESPONSE_TYPE_ARRAY = 2;

    /**
     * Class private data members.
     */
    private String mUrl = null;
    private int mStatusCode = -1;
    private String mMethod = null;
    private int mRequestCode = -1;
    private int mResponseType = 1;
    private String mStatusMsg = null;
    private Activity mActivity = null;
    private JSONObject mJsonRequest = null;
    private NetworkCallbackListener mListener = null;

    public HttpsTask(int requestCode, Activity activity, NetworkCallbackListener listener, String method, String url, JSONObject jsonRequest, int responseType) {

        this.mResponseType = responseType;
        this.mJsonRequest = jsonRequest;
        this.mRequestCode = requestCode;
        this.mActivity = activity;
        this.mListener = listener;
        this.mMethod = method;
        this.mUrl = url;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected String doInBackground(String... params) {


        try {
            URL url = new URL(mUrl);
            HttpsURLConnection urlConnection = (HttpsURLConnection) url.openConnection();
            urlConnection.setRequestProperty("Content-Type", "application/json");
            urlConnection.setRequestProperty("Authorization", "Basic Y2tfNDIyODg0NWI1YmZiZTVhZGZjZWNlOTA3ZDYyZjI4MDMxY2MyNmZkZjpjc181YjdjYTY5ZGM0OTUwODE3NzYwMWJhMmQ2OGQ0YTY3Njk1ZGYwYzcw");
            urlConnection.setRequestMethod(mMethod);
            urlConnection.connect();

            OutputStreamWriter out = new OutputStreamWriter(urlConnection.getOutputStream());
            out.write(mJsonRequest.toString());
            out.close();

            mStatusCode = urlConnection.getResponseCode();
            mStatusMsg = urlConnection.getResponseMessage();

            if (mStatusCode == 200 || mStatusCode == 201) {

                InputStream it = new BufferedInputStream(urlConnection.getInputStream());
                InputStreamReader read = new InputStreamReader(it);
                BufferedReader buff = new BufferedReader(read);
                StringBuilder dta = new StringBuilder();
                String chunks;
                while ((chunks = buff.readLine()) != null) {
                    dta.append(chunks);
                }
                String returndata = dta.toString();
                return returndata;
            } else {
                //Handle else
            }
        } catch (ProtocolException e) {

            e.printStackTrace();
            this.mStatusMsg = e.getMessage();
        } catch (MalformedURLException e) {

            e.printStackTrace();
            this.mStatusMsg = e.getMessage();
        } catch (IOException e) {

            e.printStackTrace();
            this.mStatusMsg = e.getMessage();
        } catch (Exception e) {

            e.printStackTrace();
            this.mStatusMsg = e.getMessage();
        }

        return null;

    }

    @Override
    protected void onPostExecute(String response) {
        super.onPostExecute(response);

        if (response == null) {

            responseFail();
        } else {

            try {
                if (mResponseType == RESPONSE_TYPE_OBJECT) {

                    JSONObject json = new JSONObject(response.toString());
                    responseSuccess(json, null);
                } else if (mResponseType == RESPONSE_TYPE_ARRAY) {

                    JSONArray json = new JSONArray(response.toString());
                    responseSuccess(null, json);
                }
            } catch (Exception e) {

                e.printStackTrace();
                mStatusMsg = mStatusMsg + " :: " + e.getMessage();
                responseFail();
            }
        }

    }

    private void responseSuccess(JSONObject object, JSONArray array) {

        if (mListener != null) {

            mListener.networkSuccessResponse(mRequestCode, object, array);
        }
    }

    private void responseFail() {

        if (mListener != null) {

            mListener.networkFailResponse(mRequestCode, mStatusMsg);
        }
    }
}
