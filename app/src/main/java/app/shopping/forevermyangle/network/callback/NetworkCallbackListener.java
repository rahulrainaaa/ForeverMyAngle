package app.shopping.forevermyangle.network.callback;

public interface NetworkCallbackListener {

    public void networkAPISuccessResponse(int requestCode);

    public void networkAPIFailResponse(int requestCode);

    public void networkErrorResponse(int requestCode);

}
