package app.shopping.forevermyangle.network.callback;

public interface NetworkCallbackListener {

    public void networkSuccess(int requestCode);

    public void networkFailure(int requestCode);

    public void networkError(int requestCode);

}
