package app.shopping.forevermyangle.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Random;

import app.shopping.forevermyangle.R;
import app.shopping.forevermyangle.model.login.Login;
import app.shopping.forevermyangle.network.callback.NetworkCallbackListener;
import app.shopping.forevermyangle.network.handler.HttpsTask;
import app.shopping.forevermyangle.network.handler.NetworkHandler;
import app.shopping.forevermyangle.utils.Constants;
import app.shopping.forevermyangle.utils.GlobalData;
import app.shopping.forevermyangle.utils.Network;
import app.shopping.forevermyangle.view.FMAProgressDialog;

/**
 * @class LoginActivity
 * @desc Activity for handling Login Activity.
 */
public class LoginActivity extends FragmentActivity implements View.OnClickListener, NetworkCallbackListener, GraphRequest.GraphJSONObjectCallback {

    /**
     * Class private data members.
     */
    private FMAProgressDialog mFMAProgressDialog = null;
    private NetworkHandler mNetworkHandler = null;
    private LoginButton facebookLogin = null;
    private CallbackManager callbackManager;
    private EditText mTxtUsername = null;
    private EditText mTxtPassword = null;
    private String mStrUsername = null;
    private String mStrPassword = null;

    // Temp purpose for holding social email and userID.
    private String mStrSocialEmail = null;
    private String mStrSocialId = null;

    /**
     * {@link FragmentActivity} class override methods.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(getApplicationContext());
        setContentView(R.layout.activity_login);
        callbackManager = CallbackManager.Factory.create();
        facebookLogin = (LoginButton) findViewById(R.id.login_facebook);
        facebookLogin.setReadPermissions("email");

        findViewById(R.id.link_forgot_password).setOnClickListener(this);
        findViewById(R.id.link_new_user).setOnClickListener(this);
        mTxtUsername = (EditText) findViewById(R.id.txt_username);
        mTxtPassword = (EditText) findViewById(R.id.txt_password);
        findViewById(R.id.login_btn).setOnClickListener(this);
        mFMAProgressDialog = new FMAProgressDialog(this);

        facebookLogin.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {

                GraphRequest request = GraphRequest.newMeRequest(loginResult.getAccessToken(), LoginActivity.this);
                Bundle parameters = new Bundle();
                parameters.putString("fields", "id, first_name, last_name, email, gender, birthday, location");
                request.setParameters(parameters);
                request.executeAsync();

            }

            @Override
            public void onCancel() {
                Toast.makeText(LoginActivity.this, "Login attempt cancelled.", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onError(FacebookException e) {
                Toast.makeText(LoginActivity.this, "Login attempt failed.", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    protected void onDestroy() {
        mFMAProgressDialog.dismiss();
        mFMAProgressDialog = null;
        super.onDestroy();
    }

    /**
     * {@link android.view.View.OnClickListener} interface callback method.
     */
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.login_btn:

                sendLoginRequest();
                break;
            case R.id.link_forgot_password:

                try {
                    Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://forevermyangel.com/my-account/"));
                    startActivity(browserIntent);
                } catch (Exception e) {
                    e.printStackTrace();
                    Toast.makeText(this, "Exception: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.link_new_user:

                Intent intent = new Intent(this, RegisterCustomerActivity.class);
                intent.putExtra("role", "customer");
                startActivity(intent);
                break;
            default:

                Toast.makeText(this, "Warning: Unhandled OnClick Event on LoginActivity.", Toast.LENGTH_SHORT).show();
                break;
        }
    }

    /**
     * @return boolean true = data valid, false = validation fail.
     * @method checkValidation
     * @desc Method to check the username and password field input data validation. true = valid, false = invalid
     */
    private boolean checkValidation() {

        boolean validationFlag = true;
        mStrUsername = mTxtUsername.getText().toString().trim();
        mStrPassword = mTxtPassword.getText().toString().trim();

        if (mStrUsername.isEmpty()) {

            mTxtUsername.setError("Username cannot be empty");
            validationFlag = false;
        }
        if (mStrPassword.isEmpty()) {

            mTxtPassword.setError("Password cannot be empty");
            validationFlag = false;
        }
        if (validationFlag == false) {
            return validationFlag;
        }

        mTxtUsername.setError(null);
        mTxtPassword.setError(null);

        return validationFlag;
    }

    /**
     * @method sendLoginRequest
     * @desc Method to create a request packet and send login request.
     */
    public void sendLoginRequest() {

        if (checkValidation()) {
            JSONObject jsonRequest = new JSONObject();
            try {
                jsonRequest.put("username", mStrUsername.trim());
                jsonRequest.put("password", mStrPassword.trim());
                HttpsTask httpsTask = new HttpsTask(1, this, this, "POST", Network.URL_FMA_USER_LOGIN, jsonRequest, HttpsTask.RESPONSE_TYPE_OBJECT);
                httpsTask.execute("");
                mFMAProgressDialog.show();
            } catch (Exception exception) {
                exception.printStackTrace();
                Toast.makeText(this, "" + exception.getMessage(), Toast.LENGTH_SHORT).show();
            }

        } else {
            // Validation failed for the Username-Password login fields.
        }
    }

    /**
     * {@link NetworkCallbackListener} interface callback methods.
     */
    @Override
    public void networkSuccessResponse(int requestCode, JSONObject rawObject, JSONArray rawArray) {
        mFMAProgressDialog.hide();
        switch (requestCode) {
            case 1:

                loginSuccessfully(rawObject);
                break;
            case 2:

                detailsFetched(rawArray);
                break;

            case 3:

                checkSocialUserPresent(rawArray);
                break;
        }
    }

    @Override
    public void networkFailResponse(int requestCode, String message) {

        mFMAProgressDialog.hide();
        switch (requestCode) {      // Login Response.
            case 1:

                mTxtPassword.setText("");
                if (message == null) {
                    Toast.makeText(this, "Invalid Credentials.", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
                }
                break;
            case 2:

                Toast.makeText(this, "Unable to fetch user data.", Toast.LENGTH_SHORT).show();
                break;
            case 3:

                Toast.makeText(this, "Error: " + message, Toast.LENGTH_SHORT).show();
                break;
        }
    }

    /**
     * @param jsonResponse {@link Login} model class equivalent.
     * @method loginSuccessfully
     * @desc Method to handle manual login response and proceed for fetching profile.
     */
    private void loginSuccessfully(JSONObject jsonResponse) {

        Gson gson = new Gson();

        // Save the login model in sharedPreferences.
        Login login = (Login) gson.fromJson(jsonResponse.toString(), Login.class);
        GlobalData.login = login;
        String jsonLoginData = gson.toJson(login);
        getSharedPreferences(Constants.CACHE_USER, 0).edit().putString(Constants.CACHE_KEY_LOGIN, jsonLoginData).commit();

        String url = Network.URL_FMA_USER_DETAIL + "?email=" + login.getUserEmail();
        NetworkHandler handler = new NetworkHandler();
        handler.httpCreate(2, this, this, new JSONObject(), url, NetworkHandler.RESPONSE_ARRAY);
        handler.executeGet();
    }

    /**
     * @param jsonArray
     * @method detailsFetched
     * @desc Method to fetch the user profile data and save in the cache.
     */
    private void detailsFetched(JSONArray jsonArray) {

        mFMAProgressDialog.hide();
        if (jsonArray.length() < 1) {
            return;
        }
        try {

            // Saving user detail
            GlobalData.jsonUserDetail = jsonArray.getJSONObject(0);
            getSharedPreferences(Constants.CACHE_USER, 0).edit().putString(Constants.CACHE_KEY_USER_DETAIL, GlobalData.jsonUserDetail.toString()).commit();

            Gson gson = new Gson();
            Login login = new Login();
            GlobalData.login = login;
            login.setToken("");
            login.setUserDisplayName(jsonArray.getJSONObject(0).getString("first_name") + " " + jsonArray.getJSONObject(0).getString("last_name"));
            login.setUserEmail(jsonArray.getJSONObject(0).getString("email"));
            login.setUserNicename(jsonArray.getJSONObject(0).getString("username"));
            String jsonLoginData = gson.toJson(login);
            getSharedPreferences(Constants.CACHE_USER, 0).edit().putString(Constants.CACHE_KEY_LOGIN, jsonLoginData).commit();


            Toast.makeText(this, "Login Successfully.", Toast.LENGTH_SHORT).show();
            finish();

        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(this, "Exception: " + e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * @param arr single object (user profile) in JSONArray.
     * @method checkValidation
     * @desc Method to check if the social login is not present, then proceed for default register and then login.
     */
    private void checkSocialUserPresent(JSONArray arr) {

        int length = arr.length();

        if (length > 0) {

            // If the social (role = subscriber) customer is present.
            detailsFetched(arr);
            return;
        }

        /**
         * The social user is not yet registered.
         * Proceed for the new subscriber registration.
         */

        Toast.makeText(this, "Registering as new customer.", Toast.LENGTH_SHORT).show();
        try {
            JSONObject jsonRequest = new JSONObject();

            jsonRequest.put("email", mStrSocialEmail);
            jsonRequest.put("username", mStrSocialId);
            Random r = new Random();
//            jsonRequest.put("password", String.valueOf(r.nextDouble()));
            jsonRequest.put("password", "password");

            HttpsTask httpsTask = new HttpsTask(2, this, this, "POST", Network.URL_REGISTER_NEW, jsonRequest, HttpsTask.RESPONSE_TYPE_ARRAY);
            httpsTask.execute("");
            mFMAProgressDialog.show();
        } catch (JSONException jsonExc) {

            jsonExc.printStackTrace();
            Toast.makeText(this, "JSONException: " + jsonExc.getMessage(), Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }


    @Override
    public void onCompleted(JSONObject object, GraphResponse response) {

        try {
            mStrSocialId = object.getString("id");
            mStrSocialEmail = object.getString("email");
            String url = Network.URL_FMA_USER_DETAIL + "?email=" + mStrSocialEmail + "";
            NetworkHandler handler = new NetworkHandler();
            mFMAProgressDialog.show();
            handler.httpCreate(3, this, this, new JSONObject(), url, NetworkHandler.RESPONSE_ARRAY);
            handler.executeGet();
            return;
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(this, "Exception: " + e.getMessage(), Toast.LENGTH_SHORT).show();
        }

        try {
            mStrSocialId = object.getString("id");
            mStrSocialEmail = object.getString("id") + "@facebook.com";
            String url = Network.URL_FMA_USER_DETAIL + "?email=" + mStrSocialEmail + "";
            NetworkHandler handler = new NetworkHandler();
            mFMAProgressDialog.show();
            handler.httpCreate(3, this, this, new JSONObject(), url, NetworkHandler.RESPONSE_ARRAY);
            handler.executeGet();
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(this, "Exception: " + e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }
}
