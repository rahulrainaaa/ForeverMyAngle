package app.shopping.forevermyangle.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.Toast;

import app.shopping.forevermyangle.R;
import app.shopping.forevermyangle.view.FMAProgessDialog;

/**
 * @class LoginActivity
 * @desc Activity for handling Login Activity.
 */
public class LoginActivity extends FragmentActivity implements View.OnClickListener {

    private FMAProgessDialog mFMAProgessDialog = null;

    /**
     * {@link FragmentActivity} class override methods.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        findViewById(R.id.login_btn).setOnClickListener(this);
        // Picasso.with(this).load(R.drawable.login_bg).into(mLayout);
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

                startActivity(new Intent(this, DashboardActivity.class));
                break;
            default:

                Toast.makeText(this, "Warning: Unhandeled OnClick Event on LoginActivity.", Toast.LENGTH_SHORT).show();
                break;
        }
    }

    @Override
    public void onBackPressed() {

    }
}
