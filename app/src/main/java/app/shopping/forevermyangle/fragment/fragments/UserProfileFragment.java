package app.shopping.forevermyangle.fragment.fragments;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import app.shopping.forevermyangle.R;
import app.shopping.forevermyangle.activity.DashboardActivity;
import app.shopping.forevermyangle.activity.LoginActivity;
import app.shopping.forevermyangle.fragment.base.BaseFragment;
import app.shopping.forevermyangle.model.login.Login;
import app.shopping.forevermyangle.utils.Constants;
import app.shopping.forevermyangle.utils.GlobalData;

/**
 * @class UserProfileFragment
 * @desc Activity class to handle user profile fragment screen.
 */
public class UserProfileFragment extends BaseFragment implements View.OnClickListener {

    /**
     * Private class data members.
     */
    private TextView txtFullName = null;
    private TextView txtEmail = null;
    private LinearLayout layout = null;
    private Button btnLogin, btnAddress, btnTrackOrder, btnOrderHistory, btnPrivacyPolicy, btnTerms, btnLogout;

    /**
     * {@link BaseFragment} class override methods.
     */
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_user_profile, container, false);

        txtFullName = (TextView) view.findViewById(R.id.txt_full_name);
        txtEmail = (TextView) view.findViewById(R.id.txt_email);
        layout = (LinearLayout) view.findViewById(R.id.option_layout);
        btnLogin = (Button) view.findViewById(R.id.btn_login);
        btnAddress = (Button) view.findViewById(R.id.btn_address);
        btnTrackOrder = (Button) view.findViewById(R.id.btn_track_order);
        btnOrderHistory = (Button) view.findViewById(R.id.btn_order_history);
        btnPrivacyPolicy = (Button) view.findViewById(R.id.btn_privacy);
        btnTerms = (Button) view.findViewById(R.id.btn_terms);
        btnLogout = (Button) view.findViewById(R.id.btn_logout);

        btnLogin.setOnClickListener(this);
        btnAddress.setOnClickListener(this);
        btnTrackOrder.setOnClickListener(this);
        btnOrderHistory.setOnClickListener(this);
        btnPrivacyPolicy.setOnClickListener(this);
        btnTerms.setOnClickListener(this);
        btnLogout.setOnClickListener(this);

        try {
            checkSession();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return view;
    }

    private void checkSession() throws JSONException {

        JSONObject jsonUserData = GlobalData.jsonUserDetail;
        Login login = GlobalData.login;

        if (jsonUserData != null) {

            if (login != null) {

                String fName = jsonUserData.getString("first_name");
                String lName = jsonUserData.getString("last_name");
                String fullName = fName + lName;
                String email = jsonUserData.getString("email");

                txtFullName.setText(fullName.trim());
                txtEmail.setText(email.trim());

                onSessionValid();
            } else {
                onNoSession();
            }
        } else {
            onNoSession();
        }
    }

    private void onSessionValid() {

        btnLogin.setVisibility(View.GONE);
        //layout.setVisibility(View.VISIBLE);
        btnAddress.setVisibility(View.VISIBLE);
        btnOrderHistory.setVisibility(View.VISIBLE);
        btnTrackOrder.setVisibility(View.VISIBLE);
        btnLogout.setVisibility(View.VISIBLE);
    }

    private void onNoSession() {

        btnLogin.setVisibility(View.VISIBLE);
        // layout.setVisibility(View.GONE);

        btnAddress.setVisibility(View.GONE);
        btnOrderHistory.setVisibility(View.GONE);
        btnTrackOrder.setVisibility(View.GONE);
        btnLogout.setVisibility(View.GONE);
    }

    /**
     * {@link android.view.View.OnClickListener} Listener callback methods.
     */
    @Override
    public void onClick(View view) {

        switch (view.getId()) {

            case R.id.btn_login:

                startActivity(new Intent(getActivity(), LoginActivity.class));
                break;
            case R.id.btn_address:

                break;
            case R.id.btn_track_order:

                break;
            case R.id.btn_order_history:

                break;
            case R.id.btn_privacy:

                break;
            case R.id.btn_terms:

                break;
            case R.id.btn_logout:

                removeSession();
                break;
        }
    }

    /**
     * @method removeSession
     * @desc Method to remove the session from cache.
     */
    private void removeSession() {

        Snackbar.make(layout, "Are you sure?", Snackbar.LENGTH_SHORT).setAction("Logout", new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                GlobalData.jsonUserDetail = null;
                GlobalData.login = null;
                SharedPreferences.Editor se = getActivity().getSharedPreferences(Constants.CACHE_USER, 0).edit();
                se.remove(Constants.CACHE_KEY_USER_DETAIL);
                se.remove(Constants.CACHE_KEY_LOGIN);
                ((DashboardActivity) getActivity()).signalMessage(2);
            }
        }).show();

    }

}
