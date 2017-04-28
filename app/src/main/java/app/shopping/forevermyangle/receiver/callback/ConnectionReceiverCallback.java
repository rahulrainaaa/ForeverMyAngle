package app.shopping.forevermyangle.receiver.callback;

import app.shopping.forevermyangle.receiver.receiverClass.ConnectionBroadcastReceiver;

/**
 * @interface ConnectionReceiverCallback
 * @desc Interface for callback from broadcast receiver.
 */
public interface ConnectionReceiverCallback {

    /**
     * @method networkConnectionStateChange
     * @desc Abstract method for callback from {@link ConnectionBroadcastReceiver}.
     */
    public void networkConnectionStateChange();
}

