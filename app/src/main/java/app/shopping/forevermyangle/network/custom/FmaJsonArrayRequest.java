package app.shopping.forevermyangle.network.custom;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.toolbox.JsonArrayRequest;

import org.json.JSONArray;

import java.util.HashMap;
import java.util.Map;


public class FmaJsonArrayRequest extends JsonArrayRequest {

    /**
     * @param method
     * @param url
     * @param requestBody
     * @param listener
     * @param errorListener
     * @constructor FmaJsonArrayRequest
     */
    public FmaJsonArrayRequest(int method, String url, String requestBody, Response.Listener<JSONArray> listener, Response.ErrorListener errorListener) {

        super(method, url, requestBody, listener, errorListener);
    }

    /**
     * {@link JsonArrayRequest} Class Override methods.
     */
    @Override
    public Map<String, String> getHeaders() throws AuthFailureError {
        Map<String, String> headers = new HashMap<>();
        headers.put("Content-Type", "application/json");
        headers.put("Authorization", "Basic Y2tfNDIyODg0NWI1YmZiZTVhZGZjZWNlOTA3ZDYyZjI4MDMxY2MyNmZkZjpjc181YjdjYTY5ZGM0OTUwODE3NzYwMWJhMmQ2OGQ0YTY3Njk1ZGYwYzcw");
        return headers;
    }

}
