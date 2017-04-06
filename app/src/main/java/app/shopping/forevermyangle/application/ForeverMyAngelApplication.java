package app.shopping.forevermyangle.application;


import android.app.Application;
import android.content.res.Configuration;
import android.util.Log;
import android.widget.Toast;

public class ForeverMyAngelApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        Toast.makeText(this, "Application On Create", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        Toast.makeText(this, "Application On Low Memory", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        Toast.makeText(this, "Application On Configuration Changed", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onTerminate() {

        Toast.makeText(this, "Application On Terminate", Toast.LENGTH_SHORT).show();
        Log.d("ApplicationOnTerminate", "eeeeeeeeeeeeeeee");
        super.onTerminate();
    }

    @Override
    public void onTrimMemory(int level) {

        Toast.makeText(this, "Application On Trim Memory", Toast.LENGTH_SHORT).show();
        super.onTrimMemory(level);
    }
}
