package app.shopping.forevermyangle.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import app.shopping.forevermyangle.R;
import app.shopping.forevermyangle.network.callback.NetworkCallbackListener;
import app.shopping.forevermyangle.network.handler.HttpsTask;
import app.shopping.forevermyangle.network.handler.NetworkHandler;
import app.shopping.forevermyangle.utils.GlobalData;
import app.shopping.forevermyangle.utils.Network;
import app.shopping.forevermyangle.view.FMAProgressDialog;

public class CheckoutActivity extends AppCompatActivity implements View.OnClickListener, NetworkCallbackListener {

    /**
     * Class private data members.
     */
    private TextView sName, sCompany, sAddress, sCity, sState, sPostal, sCountry;
    private TextView bName, bCompany, bAddress, bCity, bState, bPostal, bCountry, bEmail, bPhone;
    private TextView price, shipping, total;
    private Button btnCheckOut = null;
    private FMAProgressDialog fmaProgressDialog = null;

    /**
     * {@link AppCompatActivity} override methods.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkout);
        fmaProgressDialog = new FMAProgressDialog(this);

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

    @Override
    protected void onDestroy() {

        fmaProgressDialog.dismiss();
        super.onDestroy();
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

            jsonRequest.put("customer_id", GlobalData.jsonUserDetail.getInt("id"));
            jsonRequest.put("payment_method", "cod");
            jsonRequest.put("payment_method_title", "Cash on delivery");
            jsonRequest.put("set_paid", true);
            jsonRequest.put("billing", GlobalData.jsonUserDetail.getJSONObject("billing"));
            jsonRequest.put("shipping", GlobalData.jsonUserDetail.getJSONObject("shipping"));

            JSONArray jsonArrayLineItems = new JSONArray();
            for (int i = 0; i < GlobalData.cartProducts.size(); i++) {

                JSONObject jsonItem = new JSONObject();
                jsonItem.put("product_id", GlobalData.cartProducts.get(i).id);
                jsonItem.put("quantity", Integer.parseInt(GlobalData.cartProducts.get(i).qty));
                jsonArrayLineItems.put(jsonItem);
            }
            jsonRequest.put("line_items", jsonArrayLineItems);

            JSONArray jsonArrayShippingLines = new JSONArray();
            JSONObject jsonShippingLines = new JSONObject();
            jsonShippingLines.put("method_id", "flat_rate");
            jsonShippingLines.put("method_title", "Flat Rate");
            jsonShippingLines.put("total", 0);
            jsonArrayShippingLines.put(jsonShippingLines);

            jsonRequest.put("shipping_lines", jsonArrayShippingLines);

            fmaProgressDialog.show();
            HttpsTask httpsTask = new HttpsTask(1, this, this, "POST", Network.URL_PLACE_ORDER, jsonRequest, HttpsTask.RESPONSE_TYPE_OBJECT);
            httpsTask.execute("");

        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(this, "Exception:" + e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * {@link NetworkCallbackListener} network callback listener methods.
     */
    @Override
    public void networkSuccessResponse(int requestCode, JSONObject rawObject, JSONArray rawArray) {

        fmaProgressDialog.hide();
        switch (requestCode) {
            case 1:     // order placed
                try {
                    // Need to clear cart after placing order from cart.
                    JSONObject jsonRequest = new JSONObject();
                    int userID = GlobalData.jsonUserDetail.getInt("id");
                    jsonRequest.put("userid", "" + userID);
                    NetworkHandler networkHandler = new NetworkHandler();
                    networkHandler.httpCreate(2, this, this, jsonRequest, Network.URL_CLEAR_CART, NetworkHandler.RESPONSE_JSON);
                    networkHandler.executePost();
                    Toast.makeText(this, "Order Placed...\nWait a moment.", Toast.LENGTH_SHORT).show();
                    fmaProgressDialog.show();
                } catch (Exception e) {
                    e.printStackTrace();
                    Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                }

                break;
            case 2:     // items removed from cart after ordering.

                try {
//                    int id = rawObject.getInt("id");
//                    Toast.makeText(this, "Done.", Toast.LENGTH_SHORT).show();
//                    Intent intent = ;
//                    intent.putExtra("id", String.valueOf(id));
                    startActivity(new Intent(this, OrderPlacedActivity.class));

                } catch (Exception exception) {
                    exception.printStackTrace();
                    Toast.makeText(this, "Exception: " + exception.getMessage(), Toast.LENGTH_SHORT).show();
                }

                finish();
                break;
        }
    }

    @Override
    public void networkFailResponse(int requestCode, String message) {

        fmaProgressDialog.hide();
        switch (requestCode) {
            case 1:

                Toast.makeText(this, "Request Failed with code: " + requestCode, Toast.LENGTH_SHORT).show();
                break;
            case 2:

                Toast.makeText(this, "Order Placed, but  items still present in cart.\nPlease remove manually.", Toast.LENGTH_LONG).show();
                break;
        }

    }
}
