package app.shopping.forevermyangle.activity;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONObject;

import app.shopping.forevermyangle.R;
import app.shopping.forevermyangle.utils.GlobalData;

public class AddressActivity extends AppCompatActivity {

    private SectionsPagerAdapter mSectionsPagerAdapter;
    private ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_address);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {

        private static final String ARG_SECTION_NUMBER = "section_number";
        private TextView txtFirstName, txtLastName, txtCompany, txtAddress1, txtAddress2, txtCity, txtState, txtPostalCode, txtCountry, txtEmail, txtPhone;
        private int fragmentNumber = 1;

        public PlaceholderFragment() {
        }

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static PlaceholderFragment newInstance(int sectionNumber) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View view = inflater.inflate(R.layout.fragment_address, container, false);

            txtFirstName = (TextView) view.findViewById(R.id.first_name);
            txtLastName = (TextView) view.findViewById(R.id.last_name);
            txtCompany = (TextView) view.findViewById(R.id.company);
            txtAddress1 = (TextView) view.findViewById(R.id.address_1);
            txtAddress2 = (TextView) view.findViewById(R.id.address_2);
            txtCity = (TextView) view.findViewById(R.id.city);
            txtState = (TextView) view.findViewById(R.id.state);
            txtPostalCode = (TextView) view.findViewById(R.id.postal_code);
            txtCountry = (TextView) view.findViewById(R.id.country);
            txtEmail = (TextView) view.findViewById(R.id.email);
            txtPhone = (TextView) view.findViewById(R.id.phone);

            int fragNo = getArguments().getInt(ARG_SECTION_NUMBER);

            try {

                if (fragNo == 1) {

                    JSONObject jsonAddress = GlobalData.jsonUserDetail.getJSONObject("billing");

                    txtFirstName.setText(jsonAddress.getString("first_name"));
                    txtLastName.setText(jsonAddress.getString("last_name"));
                    txtCompany.setText(jsonAddress.getString("company"));
                    txtAddress1.setText(jsonAddress.getString("address_1"));
                    txtAddress2.setText(jsonAddress.getString("address_2"));
                    txtCity.setText(jsonAddress.getString("city"));
                    txtState.setText(jsonAddress.getString("state"));
                    txtPostalCode.setText(jsonAddress.getString("postcode"));
                    txtCountry.setText(jsonAddress.getString("country"));
                    txtEmail.setText(jsonAddress.getString("email"));
                    txtPhone.setText(jsonAddress.getString("phone"));

                } else if (fragNo == 2) {

                    JSONObject jsonAddress = GlobalData.jsonUserDetail.getJSONObject("shipping");

                    txtFirstName.setText(jsonAddress.getString("first_name"));
                    txtLastName.setText(jsonAddress.getString("last_name"));
                    txtCompany.setText(jsonAddress.getString("company"));
                    txtAddress1.setText(jsonAddress.getString("address_1"));
                    txtAddress2.setText(jsonAddress.getString("address_2"));
                    txtCity.setText(jsonAddress.getString("city"));
                    txtState.setText(jsonAddress.getString("state"));
                    txtPostalCode.setText(jsonAddress.getString("postcode"));
                    txtCountry.setText(jsonAddress.getString("country"));
                    txtEmail.setVisibility(View.GONE);
                    txtPhone.setVisibility(View.GONE);
                    view.findViewById(R.id.lbl_email).setVisibility(View.GONE);
                    view.findViewById(R.id.lbl_phone).setVisibility(View.GONE);

                }
            } catch (Exception e) {
                e.printStackTrace();
                Toast.makeText(getActivity(), "" + e.getMessage(), Toast.LENGTH_SHORT).show();
            }

            return view;
        }
    }

    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {

            return PlaceholderFragment.newInstance(position + 1);
        }

        @Override
        public int getCount() {

            return 2;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return "Billing";
                case 1:
                    return "Shipping";
            }
            return null;
        }
    }
}
