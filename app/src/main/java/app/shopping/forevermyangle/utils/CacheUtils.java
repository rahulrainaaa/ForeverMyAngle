package app.shopping.forevermyangle.utils;

import android.content.Context;
import android.content.SharedPreferences;


/**
 * @class CacheUtils
 * @desc Class to handle SharedPreferences methods.
 */

class CacheUtils {

    /**
     * class private constants.
     */
    private final String CACHE_NAME = "FMACache";

    /**
     * Class private data members.
     */
    private static final CacheUtils ourInstance = new CacheUtils();
    private SharedPreferences.Editor mEditor = null;
    private SharedPreferences mSharedPreferences = null;
    private Context mContext = null;

    /**
     * @method getInstance
     * @desc Method to get the instance of this class.
     */
    static CacheUtils getInstance() {
        return ourInstance;
    }

    /**
     * @param context
     * @method setContext
     * @desc Method to save the context.
     */
    public void setContext(Context context) {

        this.mContext = context;
        mSharedPreferences = mContext.getSharedPreferences(CACHE_NAME, 0);
        mEditor = mContext.getSharedPreferences(CACHE_NAME, 0).edit();
    }

    /**
     * @param key   String keyName.
     * @param value String value to save.
     * @class saveValue
     * @desc Static method to save string value in SharedPreferences.
     */
    public void saveValue(String key, String value) {

        mEditor.putString(key, value);
    }

    /**
     * @param key
     * @param defVal
     * @class getValue
     * @desc Static method to get the data value from sharedPreferences.
     */
    public String getValue(String key, String defVal) {

        return mSharedPreferences.getString(key.trim(), defVal);
    }

}
