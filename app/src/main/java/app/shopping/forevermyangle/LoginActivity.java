package app.shopping.forevermyangle;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

public class LoginActivity extends FragmentActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        // Picasso.with(this).load(R.drawable.login_bg).into(mLayout);
    }
}
