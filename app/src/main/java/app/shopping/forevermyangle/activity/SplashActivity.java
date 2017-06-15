package app.shopping.forevermyangle.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import app.shopping.forevermyangle.R;

/**
 * @class SplashActivity
 * @desc Full Screen Activity Splash screen.
 */
public class SplashActivity extends FragmentActivity implements Runnable {

    private ImageView imgIcon = null;

    /**
     * {@link FragmentActivity} class override method(s).
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);

        imgIcon = (ImageView) findViewById(R.id.fma_icon);
        imgIcon.startAnimation(AnimationUtils.loadAnimation(this, R.anim.anim_splash));
        Handler handler = new Handler();
        handler.postDelayed(this, 2000);

    }

    /**
     * {@link Runnable} interface callnack method.
     */
    @Override
    public void run() {
        startActivity(new Intent(this, DashboardActivity.class));

        finish();
    }
}
