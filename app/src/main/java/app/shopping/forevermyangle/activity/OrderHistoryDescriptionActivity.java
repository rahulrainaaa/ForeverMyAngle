package app.shopping.forevermyangle.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.TextView;

import app.shopping.forevermyangle.R;

public class OrderHistoryDescriptionActivity extends AppCompatActivity {

    /**
     * Class private data members.
     */
    private TextView sName, sCompany, sAddress, sCity, sState, sPostal, sCountry;
    private TextView bName, bCompany, bAddress, bCity, bState, bPostal, bCountry, bEmail, bPhone;
    private TextView price, shipping, total;
    private Button btnCheckOut = null;

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


    }


}
