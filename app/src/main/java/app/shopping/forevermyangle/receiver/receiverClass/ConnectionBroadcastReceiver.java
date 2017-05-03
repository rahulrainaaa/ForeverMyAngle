package app.shopping.forevermyangle.receiver.receiverClass;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import app.shopping.forevermyangle.utils.GlobalData;

/**
 * @class ConnectionBroadcastReceiver
 * @desc {@link BroadcastReceiver} class to receiver any data connection change,
 */
public class ConnectionBroadcastReceiver extends BroadcastReceiver {

    /**
     * {@link BroadcastReceiver} class override methods.
     */
    @Override
    public void onReceive(Context context, Intent intent) {

        // Send the callback if there is an existing instance.
        if (GlobalData.connectionCallback != null) {
            GlobalData.connectionCallback.networkConnectionStateChange();

        }
    }
}
