package app.shopping.forevermyangle.activity;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import app.shopping.forevermyangle.R;
import app.shopping.forevermyangle.fragment.base.BaseFragment;
import app.shopping.forevermyangle.fragment.fragments.HomeDashboardFragment;

/**
 * @class DashboardActivity
 * @desc {@link AppCompatActivity} to handle dashboard Activity.
 */
public class DashboardActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {

    /**
     * Private data member objects.
     */
    private BaseFragment mCurrentFragment = null;  // Fragment Custom Class.
    private int mFlagFragment = 1;      // Fragment Number current.
    private FragmentManager mFragmentManager = null;
    private FragmentTransaction mFragmentTransaction = null;

    /**
     * {@link AppCompatActivity} override method(s).
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View view = getLayoutInflater().inflate(R.layout.app_title, null);
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(view);
        setContentView(R.layout.activity_dashboard);
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(this);
        mFragmentManager = getFragmentManager();
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadFragment();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.dashboard_menu, menu);
        return true;
    }

    /**
     * {@link BottomNavigationView.OnNavigationItemSelectedListener} implemented method(s).
     */

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.navigation_home:              // Navigation item 1 selected.

                mFlagFragment = 1;
                break;

            case R.id.navigation_dashboard:         // Navigation item 2 selected.

                mFlagFragment = 2;
                break;

            case R.id.navigation_notifications:     // Navigation item 3 selected.

                mFlagFragment = 3;
                break;

            case R.id.navigation_notificationss:    // Navigation item 4 selected.

                mFlagFragment = 4;
                break;
        }
        loadFragment();         // Load the corresponding Fragment on dashboard.
        return true;
    }

    /**
     * @return {@link BaseFragment}
     * @method getCurrentFragment
     * @desc Method to get instance of current fragment appeared on UI.
     */
    private BaseFragment getCurrentFragment() {

        switch (mFlagFragment) {
            case 1:     // Fragment 1 to load.
                return new HomeDashboardFragment();

            case 2:     // Fragment 2 to load.
                return new HomeDashboardFragment();

            case 3:     // Fragment 3 to load.
                return new HomeDashboardFragment();

            case 4:     // Fragment 4 to load.
                return new HomeDashboardFragment();

            default:    // Unexpected value for fragment load: Warning.
                Toast.makeText(this, "Warning: Unexpected value for mFlagFragment=" + mFlagFragment, Toast.LENGTH_SHORT).show();
                return null;
        }
    }

    /**
     * @method loadFragment
     * @desc Method to load the current Fragment in dashboard UI.
     */
    private void loadFragment() {

        mFragmentTransaction = mFragmentManager.beginTransaction();         // Begin with fragment transaction.
        if (!mFragmentTransaction.isEmpty()) {                              // Remove older fragment if any.
            mFragmentTransaction.remove(mCurrentFragment);
        }
        mCurrentFragment = getCurrentFragment();                            // Get a new Fragment for dashboard.
        mFragmentTransaction.replace(R.id.fragment, mCurrentFragment);      // Replace with new fragment in the container.
        mFragmentTransaction.commit();                                      // Commit fragment transition finally.
    }

}
