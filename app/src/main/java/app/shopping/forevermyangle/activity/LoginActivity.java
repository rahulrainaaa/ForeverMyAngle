package app.shopping.forevermyangle.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.TextView;
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
import org.json.JSONObject;

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

    private FMAProgressDialog mFMAProgressDialog = null;
    private NetworkHandler mNetworkHandler = null;
    private LoginButton facebookLogin = null;
    private CallbackManager callbackManager;
    private TextView mTxtUsername = null;
    private TextView mTxtPassword = null;
    private String mStrUsername = null;
    private String mStrPassword = null;

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

        mTxtUsername = (TextView) findViewById(R.id.txt_username);
        mTxtPassword = (TextView) findViewById(R.id.txt_password);
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
                mTxtUsername.setText("Login attempt cancelled.");
            }

            @Override
            public void onError(FacebookException e) {
                mTxtUsername.setText("Login attempt failed.");
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
            } catch (Exception exception) {
                exception.printStackTrace();
                Toast.makeText(this, "" + exception.getMessage(), Toast.LENGTH_SHORT).show();
            }
            HttpsTask httpsTask = new HttpsTask(1, this, this, "POST", Network.URL_FMA_USER_LOGIN, jsonRequest, HttpsTask.RESPONSE_TYPE_OBJECT);
            httpsTask.execute("");
            mFMAProgressDialog.show();

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
        }
    }

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

    private void detailsFetched(JSONArray jsonArray) {

        mFMAProgressDialog.hide();
        if (jsonArray.length() < 1) {
            return;
        }
        try {

            // Saving user detail
            GlobalData.jsonUserDetail = jsonArray.getJSONObject(0);
            getSharedPreferences(Constants.CACHE_USER, 0).edit().putString(Constants.CACHE_KEY_USER_DETAIL, GlobalData.jsonUserDetail.toString()).commit();

            if (jsonArray.getJSONObject(0).getString("role").contains("subscriber")) {

                // Saving user data
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
            }

        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(this, "Exception: " + e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }


    @Override
    public void onCompleted(JSONObject object, GraphResponse response) {
        try {
            String url = Network.URL_FMA_USER_DETAIL + "?email=" + object.getString("email") + "&role=subscriber";
            NetworkHandler handler = new NetworkHandler();
            mFMAProgressDialog.show();
            handler.httpCreate(2, this, this, new JSONObject(), url, NetworkHandler.RESPONSE_ARRAY);
            handler.executeGet();
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(this, "Exception: " + e.getMessage(), Toast.LENGTH_SHORT).show();
        }

    }
}
