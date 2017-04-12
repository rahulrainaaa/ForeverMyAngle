package app.shopping.forevermyangle.network.callback;

import app.shopping.forevermyangle.model.base.BaseModel;

/**
 * @interface NetworkCallbackListener
 * @desc Interface to callback from {@link app.shopping.forevermyangle.network.handler.NetworkHandler}.
 */
public interface NetworkCallbackListener {

    /**
     * @param requestCode
     * @param responseModel
     * @callback networkSuccessResponse
     * @desc Callback method for success response from server and response parsed successfully.
     */
    public void networkSuccessResponse(int requestCode, BaseModel responseModel);

    /**
     * @param requestCode
     * @callback networkFailResponse
     * @desc Callback method for Fail in server response, connection error. Server status code != 200.
     */
    public void networkFailResponse(int requestCode);

    /**
     * @param requestCode
     * @callback networkErrorResponse
     * @desc Callback Method for Error/Exception in response parsing when server response status code = 200 (Success).
     */
    public void networkErrorResponse(int requestCode);

}
