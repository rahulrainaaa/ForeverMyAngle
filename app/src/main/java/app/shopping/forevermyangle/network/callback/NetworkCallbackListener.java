package app.shopping.forevermyangle.network.callback;

import java.util.ArrayList;
import java.util.List;

import app.shopping.forevermyangle.model.base.BaseModel;

/**
 * @interface NetworkCallbackListener
 * @desc Interface to callback from {@link app.shopping.forevermyangle.network.handler.NetworkHandler}.
 */
public interface NetworkCallbackListener {

    /**
     * @param requestCode
     * @param responseModel
     * @param list
     * @callback networkSuccessResponse
     * @desc Callback method for success response from server and response parsed successfully.
     */
    public void networkSuccessResponse(int requestCode, BaseModel responseModel, List<? extends BaseModel> list);

    /**
     * @param requestCode The request id by user to handle distinctly.
     * @param message     The status message with the response.
     * @callback networkFailResponse
     * @desc Callback method for Fail in server response, connection error. Server status code != 200.
     */
    public void networkFailResponse(int requestCode, String message);
}
