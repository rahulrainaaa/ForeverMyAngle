package app.shopping.forevermyangle.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * @class NetworkUtils
 * @desc Utility class for Network business logic handling.
 */
public class NetworkUtils {

    /**
     * @param context
     * @return boolean true = connection_available / false = no_connection_available.
     * @method isOnline
     */
    public static boolean isOnline(Context context) {

        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        //should check null because in airplane mode it will be null
        return (netInfo != null && netInfo.isConnected());
    }


}
