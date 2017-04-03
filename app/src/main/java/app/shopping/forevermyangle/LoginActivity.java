package app.shopping.forevermyangle;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.animation.AnimationUtils;

public class LoginActivity extends FragmentActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        findViewById(R.id.login_layout).startAnimation(AnimationUtils.loadAnimation(this, R.anim.login_scr_appear));
    }
}
