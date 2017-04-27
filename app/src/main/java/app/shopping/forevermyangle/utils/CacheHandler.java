package app.shopping.forevermyangle.utils;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * @class CacheHandler
 * @desc Class to handle the method for SharedPreferences to save and retrieve data values from cache.
 */
public class CacheHandler {

    /**
     * class private constants.
     */
    private final String CACHE_NAME = "FMACache";

    /**
     * Class private data members.
     */
    private SharedPreferences.Editor mEditor = null;
    private SharedPreferences mSharedPreferences = null;
    private Context mContext = null;
    private static CacheHandler mCacheHandler = new CacheHandler();

    /**
     * @method getInstance
     * @desc Method to get the instance of this class.
     */
    public CacheHandler getInstance(Context context) {

        return mCacheHandler;
    }

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
