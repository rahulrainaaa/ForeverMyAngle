package app.shopping.forevermyangle.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

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
        Toast.makeText(context, "connection change : FMA BCR", Toast.LENGTH_SHORT).show();

    }


}
