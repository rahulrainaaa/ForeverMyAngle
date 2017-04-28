package app.shopping.forevermyangle.application;

import android.app.Application;
import android.content.SharedPreferences;

import com.google.gson.Gson;

import app.shopping.forevermyangle.model.login.Login;
import app.shopping.forevermyangle.utils.Constants;
import app.shopping.forevermyangle.utils.GlobalData;

/**
 * @class ForeverMyAngelApplication
 * @desc {@link Application} class for application callback events.
 */
public class ForeverMyAngelApplication extends Application {

    /**
     * {@link Application} override methods for callback.
     */
    @Override
    public void onCreate() {
        super.onCreate();

        // Fetch the login information from shared preferences (if present).
        SharedPreferences sharedPreferences = getSharedPreferences(Constants.CACHE_NAME, 0);
        String strJsonLogin = sharedPreferences.getString(Constants.CACHE_LOGIN, "");
        if (strJsonLogin.trim().isEmpty()) {
            GlobalData.login = null;
        } else {
            Gson gson = new Gson();
            GlobalData.login = gson.fromJson(strJsonLogin, Login.class);
        }
    }
}
