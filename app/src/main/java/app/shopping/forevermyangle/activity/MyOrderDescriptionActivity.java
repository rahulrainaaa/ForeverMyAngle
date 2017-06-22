package app.shopping.forevermyangle.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.TextView;

import app.shopping.forevermyangle.R;
import app.shopping.forevermyangle.model.order.Order;
import app.shopping.forevermyangle.utils.GlobalData;

public class MyOrderDescriptionActivity extends AppCompatActivity {

    /**
     * Class private data members.
     */
    private TextView sName, sCompany, sAddress, sCity, sState, sPostal, sCountry;
    private TextView bName, bCompany, bAddress, bCity, bState, bPostal, bCountry, bEmail, bPhone;
    private TextView price, shipping, total;
    private Button btnCheckOut = null;
    private Order orderHistory = GlobalData.orderHistory;

    /**
     * {@link AppCompatActivity} override methods.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_history_description);

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
        btnCheckOut.setOnClickListener(null);

        bName.setText(orderHistory.getBilling().getFirstName() + " " + orderHistory.getBilling().getLastName());
        bCompany.setText(orderHistory.getBilling().getCompany());
        bAddress.setText(orderHistory.getBilling().getAddress1() + "\n" + orderHistory.getBilling().getAddress2());
        bCity.setText(orderHistory.getBilling().getCity());
        bState.setText(orderHistory.getBilling().getState());
        bPostal.setText(orderHistory.getBilling().getPostcode());
        bCountry.setText(orderHistory.getBilling().getCountry());
        bEmail.setText(orderHistory.getBilling().getEmail());
        bPhone.setText(orderHistory.getBilling().getPhone());


        sName.setText(orderHistory.getShipping().getFirstName() + " " + orderHistory.getShipping().getLastName());
        sCompany.setText(orderHistory.getShipping().getCompany());
        sAddress.setText(orderHistory.getShipping().getAddress1() + "\n" + orderHistory.getShipping().getAddress2());
        sCity.setText(orderHistory.getShipping().getCity());
        sState.setText(orderHistory.getShipping().getState());
        sPostal.setText(orderHistory.getShipping().getPostcode());
        sCountry.setText(orderHistory.getShipping().getCountry());

        price.setText("AED " + orderHistory.getTotal());
        total.setText("AED " + orderHistory.getTotal());
    }

}
