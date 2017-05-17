package app.shopping.forevermyangle.network.callback;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * @interface NetworkCallbackListener
 * @desc Interface to callback from {@link app.shopping.forevermyangle.network.handler.NetworkHandler}.
 */
public interface NetworkCallbackListener {

    /**
     * @param requestCode
     * @param rawArray
     * @param rawObject
     * @callback networkSuccessResponse
     * @desc Callback method for success response from server and response parsed successfully.
     */
    public void networkSuccessResponse(int requestCode, JSONObject rawObject, JSONArray rawArray);

    /**
     * @param requestCode The request id by user to handle distinctly.
     * @param message     The status message with the response.
     * @callback networkFailResponse
     * @desc Callback method for Fail in server response, connection error. Server status code != 200.
     */
    public void networkFailResponse(int requestCode, String message);
}
