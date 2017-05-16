package app.shopping.forevermyangle.network.custom;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class FmaJsonObjectRequest extends JsonObjectRequest {

    /**
     * @param method
     * @param url
     * @param request
     * @param listener
     * @param errorListener
     * @constructor FmaJsonObjectRequest
     */
    public FmaJsonObjectRequest(int method, String url, String request, Response.Listener<JSONObject> listener, Response.ErrorListener errorListener) {

        super(method, url, request, listener, errorListener);
    }

    /**
     * {@link JsonObjectRequest} Class Override methods.
     */
    @Override
    public Map<String, String> getHeaders() throws AuthFailureError {
        Map<String, String> headers = new HashMap<>();
        headers.put("Content-Type", "application/json");
        headers.put("Authorization", "Basic Y2tfNDIyODg0NWI1YmZiZTVhZGZjZWNlOTA3ZDYyZjI4MDMxY2MyNmZkZjpjc181YjdjYTY5ZGM0OTUwODE3NzYwMWJhMmQ2OGQ0YTY3Njk1ZGYwYzcw");
        return headers;
    }
}
