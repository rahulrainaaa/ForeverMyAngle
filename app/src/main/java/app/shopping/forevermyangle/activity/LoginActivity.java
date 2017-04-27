package app.shopping.forevermyangle.activity;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONObject;

import app.shopping.forevermyangle.R;
import app.shopping.forevermyangle.model.base.BaseModel;
import app.shopping.forevermyangle.model.login.Login;
import app.shopping.forevermyangle.network.callback.NetworkCallbackListener;
import app.shopping.forevermyangle.network.handler.NetworkHandler;
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
        mStrUsername = mTxtUsername.getText().toString();
        mStrPassword = mTxtPassword.getText().toString();

        if (mStrUsername.isEmpty()) {

            mTxtUsername.setError("Username cannot be empty");
            validationFlag = false;
        }
        if (mStrPassword.isEmpty()) {

            mTxtPassword.setError("Password cannot be empty");
            validationFlag = false;
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

            }
            mNetworkHandler = new NetworkHandler();
            mNetworkHandler.httpCreate(1, this, this, jsonRequest, Network.URL_FMA_USER_LOGIN, Login.class);
            mFMAProgessDialog.show();
            mNetworkHandler.executePost();
        } else {
            // Validation failed for the Username-Password login fields.
        }
    }

    @Override
    public void networkSuccessResponse(int requestCode, BaseModel responseModel) {
        if (requestCode == 1) {
            Login login = (Login) responseModel;
            Toast.makeText(this, "login done", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void networkFailResponse(int requestCode) {
        Toast.makeText(this, "error response in response code ", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void networkErrorResponse(int requestCode) {

    }
}
