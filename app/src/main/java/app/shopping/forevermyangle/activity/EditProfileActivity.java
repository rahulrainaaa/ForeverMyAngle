package app.shopping.forevermyangle.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import app.shopping.forevermyangle.R;
import app.shopping.forevermyangle.model.profile.Profile;
import app.shopping.forevermyangle.network.callback.NetworkCallbackListener;
import app.shopping.forevermyangle.network.handler.HttpsTask;
import app.shopping.forevermyangle.utils.Network;
import app.shopping.forevermyangle.view.FMAProgressDialog;

public class EditProfileActivity extends AppCompatActivity implements NetworkCallbackListener {

    private String mStrCustomerId = null;

    private FMAProgressDialog mFMAProgressDialog = null;
    private Button mBtnRegister = null;
    private EditText mEtFirstname = null;
    private EditText mEtLastname = null;
    private EditText mEtPhone = null;
    private EditText mEtAddress1 = null;
    private EditText mEtAddress2 = null;
    private EditText mEtCity = null;
    private EditText mEtState = null;
    private EditText mEtPostalCode = null;
    private EditText mEtCountry = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        /**
         * Check if customerID is provided in Intent. Else show error and finish Activity.
         */
        mStrCustomerId = getIntent().getStringExtra("id");
        if (mStrCustomerId == null) {

            Toast.makeText(this, "ERROR: Customer ID not found in Intent.", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }
        Toast.makeText(this, "Customer ID: " + mStrCustomerId, Toast.LENGTH_SHORT).show();

        setContentView(R.layout.activity_edit_profile);

        mFMAProgressDialog = new FMAProgressDialog(this);
        mEtFirstname = (EditText) findViewById(R.id.first_name);
        mEtLastname = (EditText) findViewById(R.id.last_name);
        mEtPhone = (EditText) findViewById(R.id.phone);
        mEtAddress1 = (EditText) findViewById(R.id.address1);
        mEtAddress2 = (EditText) findViewById(R.id.address2);
        mEtCity = (EditText) findViewById(R.id.city);
        mEtState = (EditText) findViewById(R.id.state);
        mEtPostalCode = (EditText) findViewById(R.id.postal_code);
        mEtCountry = (EditText) findViewById(R.id.country);
        mBtnRegister = (Button) findViewById(R.id.btn_update_profile);

        mBtnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                registerUser(view);
            }
        });

    }

    /**
     * @param view
     * @method registerUser
     * @desc Callback method on register button click
     */
    public void registerUser(View view) {

        // Fetch all the string values from database;
        String strFirstName = mEtFirstname.getText().toString().trim();
        String strLastName = mEtLastname.getText().toString().trim();
        String strPhone = mEtPhone.getText().toString().trim();
        String strAddress1 = mEtAddress1.getText().toString().trim();
        String strAddress2 = mEtAddress2.getText().toString().trim();
        String strCity = mEtCity.getText().toString().trim();
        String strState = mEtState.getText().toString().trim();
        String strCountry = mEtCountry.getText().toString().trim();
        String strPostalCode = mEtPostalCode.getText().toString().trim();

        boolean isValid = false;

        if (strFirstName.isEmpty()) {

            mEtFirstname.setError(getString(R.string.cannot_be_empty));
        } else {
            isValid = true;
            mEtFirstname.setError(null);
        }

        if (strAddress1.isEmpty()) {

            isValid = false;
            mEtAddress1.setError(getString(R.string.cannot_be_empty));
        } else {

            isValid = true & isValid;
            mEtAddress1.setError(null);
        }

        if (strPostalCode.isEmpty()) {

            isValid = false;
            mEtPostalCode.setError(getString(R.string.cannot_be_empty));
        } else {

            isValid = true & isValid;
            mEtPostalCode.setError(null);
        }

        if (strCountry.isEmpty()) {

            isValid = false;
            mEtCountry.setError(getString(R.string.cannot_be_empty));
        } else {

            isValid = true & isValid;
            mEtCountry.setError(null);
        }

        if (!isValid) {
            return;
        }

        JSONObject jsonRegisterRequest = new JSONObject();
        JSONObject jsonBillingAddress = new JSONObject();
        JSONObject jsonShippingAddress = new JSONObject();
        try {
            jsonRegisterRequest.put("first_name", strFirstName.trim());
            jsonRegisterRequest.put("last_name", strLastName.trim());
            jsonRegisterRequest.put("last_name", strLastName.trim());

            jsonBillingAddress.put("first_name", strFirstName);
            jsonBillingAddress.put("last_name", strLastName);
            jsonBillingAddress.put("company", "");
            jsonBillingAddress.put("address_1", strAddress1);
            jsonBillingAddress.put("address_2", strAddress2);
            jsonBillingAddress.put("city", strCity);
            jsonBillingAddress.put("state", strState);
            jsonBillingAddress.put("postcode", strPostalCode);
            jsonBillingAddress.put("country", strCountry);
            jsonBillingAddress.put("phone", strPhone);

            jsonShippingAddress.put("first_name", strFirstName);
            jsonShippingAddress.put("last_name", strLastName);
            jsonShippingAddress.put("company", "");
            jsonShippingAddress.put("address_1", strAddress1);
            jsonShippingAddress.put("address_2", strAddress2);
            jsonShippingAddress.put("city", strCity);
            jsonShippingAddress.put("state", strState);
            jsonShippingAddress.put("postcode", strPostalCode);
            jsonShippingAddress.put("country", strCountry);

            jsonRegisterRequest.put("billing", jsonBillingAddress);
            jsonRegisterRequest.put("shipping", jsonShippingAddress);

            String URL = Network.URL_REGISTER_NEW + "//" + mStrCustomerId;
            HttpsTask httpsTask = new HttpsTask(1, this, this, "POST        ", URL, jsonRegisterRequest, HttpsTask.RESPONSE_TYPE_OBJECT);
            httpsTask.execute("");

            mFMAProgressDialog.show();

        } catch (JSONException jsonExc) {

            Toast.makeText(this, "EXCEPTION: " + jsonExc.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * {@link NetworkCallbackListener} http response callback methods.
     */
    @Override
    public void networkSuccessResponse(int requestCode, JSONObject rawObject, JSONArray rawArray) {

        mFMAProgressDialog.hide();
        if (requestCode == 1) {

            successResponse(rawObject);
        }
    }

    @Override
    public void networkFailResponse(int requestCode, String message) {

        mFMAProgressDialog.hide();
        Toast.makeText(this, "Network Fail: " + message, Toast.LENGTH_SHORT).show();
    }

    /**
     * @param json Http Response from API.
     * @method successResponse
     */
    private void successResponse(JSONObject json) {

        Gson gson = new Gson();
        Profile profile = gson.fromJson(json.toString(), Profile.class);
        Toast.makeText(this, "Profile updated #" + profile.id, Toast.LENGTH_SHORT).show();
        finish();
    }
}
