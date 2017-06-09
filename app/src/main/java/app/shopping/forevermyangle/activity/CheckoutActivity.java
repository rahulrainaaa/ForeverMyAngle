package app.shopping.forevermyangle.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import app.shopping.forevermyangle.R;
import app.shopping.forevermyangle.utils.GlobalData;

public class CheckoutActivity extends AppCompatActivity implements View.OnClickListener {

    /**
     * Class private data members.
     */
    TextView sName, sCompany, sAddress, sCity, sState, sPostal, sCountry;
    TextView bName, bCompany, bAddress, bCity, bState, bPostal, bCountry, bEmail, bPhone;
    TextView price, shipping, total;
    Button btnCheckOut = null;

    /**
     * {@link AppCompatActivity} override methods.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkout);

        sName = (TextView) findViewById(R.id.s_name);
        sCompany = (TextView) findViewById(R.id.s_company);
        sAddress = (TextView) findViewById(R.id.s_address);
        sCity = (TextView) findViewById(R.id.s_city);
        sState = (TextView) findViewById(R.id.s_state);
        sPostal = (TextView) findViewById(R.id.s_postal);
        sCountry = (TextView) findViewById(R.id.s_country);

        bName = (TextView) findViewById(R.id.b_name);
        bCompany = (TextView) findViewById(R.id.b_company);
        bAddress = (TextView) findViewById(R.id.b_address);
        bCity = (TextView) findViewById(R.id.b_city);
        bState = (TextView) findViewById(R.id.b_state);
        bPostal = (TextView) findViewById(R.id.b_postal);
        bCountry = (TextView) findViewById(R.id.b_country);
        bEmail = (TextView) findViewById(R.id.b_email);
        bPhone = (TextView) findViewById(R.id.b_phone);

        price = (TextView) findViewById(R.id.price);
        shipping = (TextView) findViewById(R.id.shipping);
        total = (TextView) findViewById(R.id.total);

        btnCheckOut = (Button) findViewById(R.id.button_place_order);
        btnCheckOut.setOnClickListener(this);

        try {

            JSONObject jsonShippingAddress = GlobalData.jsonUserDetail.getJSONObject("shipping");

            sName.setText(jsonShippingAddress.getString("first_name") + " " + jsonShippingAddress.getString("last_name"));
            sCompany.setText(jsonShippingAddress.getString("company"));
            sAddress.setText(jsonShippingAddress.getString("address_1") + "\n" + jsonShippingAddress.getString("address_2"));
            sCity.setText(jsonShippingAddress.getString("city"));
            sState.setText(jsonShippingAddress.getString("state"));
            sPostal.setText(jsonShippingAddress.getString("postcode"));
            sCountry.setText(jsonShippingAddress.getString("country"));

            JSONObject jsonBillingAddress = GlobalData.jsonUserDetail.getJSONObject("billing");

            bName.setText(jsonBillingAddress.getString("first_name") + " " + jsonBillingAddress.getString("last_name"));
            bCompany.setText(jsonBillingAddress.getString("company"));
            bAddress.setText(jsonBillingAddress.getString("address_1") + "\n" + jsonBillingAddress.getString("address_2"));
            bCity.setText(jsonBillingAddress.getString("city"));
            bState.setText(jsonBillingAddress.getString("state"));
            bPostal.setText(jsonBillingAddress.getString("postcode"));
            bCountry.setText(jsonBillingAddress.getString("country"));
            bEmail.setText(jsonBillingAddress.getString("email"));
            bPhone.setText(jsonBillingAddress.getString("phone"));

            price.setText("AED " + GlobalData.TotalPrice);
            shipping.setText("AED 0 (Free shipping)");
            total.setText("AED " + GlobalData.TotalPrice);

        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(this, "Exception:" + e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * {@link android.view.View.OnClickListener} listener callback method.
     */
    @Override
    public void onClick(View view) {

        switch (view.getId()) {

            case R.id.button_place_order:

                placeOrder();
                break;
        }
    }

    /**
     * @method placeOrder
     * @desc Method to create request and place an order in the web portal.
     */
    private void placeOrder() {

        try {

            JSONObject jsonRequest = new JSONObject();
            jsonRequest.put("payment_method", "");
            jsonRequest.put("payment_method_title", "");
            jsonRequest.put("set_paid", false);
            jsonRequest.put("billing", GlobalData.jsonUserDetail.getJSONObject("billing"));
            jsonRequest.put("shipping", GlobalData.jsonUserDetail.getJSONObject("shipping"));
            jsonRequest.put("line_items", new JSONArray());
            jsonRequest.put("shipping_lines", new JSONArray());

        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(this, "Exception:" + e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }
}
