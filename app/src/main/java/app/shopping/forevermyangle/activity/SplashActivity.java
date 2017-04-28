package app.shopping.forevermyangle.activity;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;

import app.shopping.forevermyangle.R;

/**
 * @class SplashActivity
 * @desc Full Screen Activity Splash screen.
 */
public class SplashActivity extends FragmentActivity {

    /**
     * {@link FragmentActivity} class override method(s).
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_splash);

        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
    }
}
