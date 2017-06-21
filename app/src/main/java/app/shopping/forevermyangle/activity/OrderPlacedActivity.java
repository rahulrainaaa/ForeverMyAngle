package app.shopping.forevermyangle.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import app.shopping.forevermyangle.R;

/**
 * @class OrderPlacedActivity
 * @desc Class to handle the success screen of order placed with FMA portal.
 */
public class OrderPlacedActivity extends AppCompatActivity implements View.OnClickListener {

    /**
     * {@link AppCompatActivity} override method(s).
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        setContentView(R.layout.activity_order_placed);

        TextView txtOrderId = (TextView) findViewById(R.id.txt_orderid);
//        String id = getIntent().getExtras().getString("id");
//        txtOrderId.setText("Order ID: " + id.trim());
        txtOrderId.setVisibility(View.GONE);

        findViewById(R.id.btn_done).setOnClickListener(this);
    }

    /**
     * {@link android.view.View.OnClickListener} listener callback method(s).
     */
    @Override
    public void onClick(View view) {
        switch (view.getId()) {

            case R.id.btn_done:

                finish();
                break;
        }
    }
}
