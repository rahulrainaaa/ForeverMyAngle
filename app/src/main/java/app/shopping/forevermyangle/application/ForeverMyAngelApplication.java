package app.shopping.forevermyangle.application;

import android.app.Application;
import android.content.SharedPreferences;
import android.widget.Toast;

import com.google.gson.Gson;

import org.json.JSONObject;

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
        SharedPreferences sharedPreferences = getSharedPreferences(Constants.CACHE_USER, 0);
        String strJsonLogin = sharedPreferences.getString(Constants.CACHE_KEY_LOGIN, "");
        String strJsonUserDetail = sharedPreferences.getString(Constants.CACHE_KEY_USER_DETAIL, "{}");

        if (strJsonLogin.trim().isEmpty()) {
            GlobalData.login = null;
        } else {
            Gson gson = new Gson();
            GlobalData.login = gson.fromJson(strJsonLogin, Login.class);
        }

        try {
            if (!strJsonUserDetail.trim().isEmpty()) {

                GlobalData.jsonUserDetail = new JSONObject(strJsonUserDetail.trim());
            }
        } catch (Exception e) {

            e.printStackTrace();
            Toast.makeText(this, "" + e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }
}
