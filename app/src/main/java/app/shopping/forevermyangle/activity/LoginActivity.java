package app.shopping.forevermyangle.activity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

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
import app.shopping.forevermyangle.view.FMAProgessDialog;

/**
 * @class LoginActivity
 * @desc Activity for handling Login Activity.
 */
public class LoginActivity extends FragmentActivity implements View.OnClickListener, NetworkCallbackListener {

    private FMAProgessDialog mFMAProgessDialog = null;
    private NetworkHandler mNetworkHandler = null;
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
        setContentView(R.layout.activity_login);
        mTxtUsername = (TextView) findViewById(R.id.txt_username);
        mTxtPassword = (TextView) findViewById(R.id.txt_password);
        findViewById(R.id.login_btn).setOnClickListener(this);
        mFMAProgessDialog = new FMAProgessDialog(this);

    }

    @Override
    protected void onDestroy() {
        mFMAProgessDialog.dismiss();
        mFMAProgessDialog = null;
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
            mFMAProgessDialog.show();

        } else {
            // Validation failed for the Username-Password login fields.
        }
    }

    /**
     * {@link NetworkCallbackListener} interface callback methods.
     */
    @Override
    public void networkSuccessResponse(int requestCode, JSONObject rawObject, JSONArray rawArray) {
        mFMAProgessDialog.hide();
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

        mFMAProgessDialog.hide();
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

        mFMAProgessDialog.hide();
        if (jsonArray.length() < 1) {
            return;
        }
        try {
            GlobalData.jsonUserDetail = jsonArray.getJSONObject(0);
            SharedPreferences.Editor se = getSharedPreferences(Constants.CACHE_USER, 0).edit();
            se.putString(Constants.CACHE_KEY_USER_DETAIL, GlobalData.jsonUserDetail.toString());
            se.commit();
            Toast.makeText(this, "Login Successfully.", Toast.LENGTH_SHORT).show();
            finish();

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
