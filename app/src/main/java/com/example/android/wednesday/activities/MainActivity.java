package com.example.android.wednesday.activities;

import android.content.ContentResolver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.wednesday.R;
import com.example.android.wednesday.fragments.ActivitiesTabFragment;
import com.example.android.wednesday.fragments.DineOutTabFragment;
import com.example.android.wednesday.fragments.EventsTabFragment;
import com.google.firebase.auth.FirebaseAuth;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity  {


    private Toolbar toolbar;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private SectionsPagerAdapter mSectionsPagerAdapter;

    TextView currentLocation;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
//        AppBarLayout.LayoutParams params = (AppBarLayout.LayoutParams) toolbar.getLayoutParams();
//        params.setScrollFlags(0);  // clear all scroll flags

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);



        LayoutInflater inflator = (LayoutInflater) this .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View v = inflator.inflate(R.layout.location_header, null);
        toolbar.addView(v);
        currentLocation = (TextView) toolbar.findViewById(R.id.current_location);




        viewPager = (ViewPager) findViewById(R.id.viewPager);
        viewPager.setAdapter(mSectionsPagerAdapter);

        setupViewPager(viewPager);

        tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);


        LocationManager locationManager = (LocationManager)
                getSystemService(Context.LOCATION_SERVICE);
        LocationListener locationListener = new MyLocationListener();

        boolean gpsIsEnabled = locationManager
                .isProviderEnabled(LocationManager.GPS_PROVIDER);
        boolean networkIsEnabled = locationManager
                .isProviderEnabled(LocationManager.NETWORK_PROVIDER);

        if (networkIsEnabled) {
            try {
                locationManager.requestLocationUpdates(
                        LocationManager.NETWORK_PROVIDER, 2000, 500, locationListener);
            } catch (SecurityException e) {
                e.printStackTrace();
            }
        } else if (gpsIsEnabled) {
            try {
                locationManager.requestLocationUpdates(
                        LocationManager.GPS_PROVIDER, 2000, 500, locationListener);
            } catch (SecurityException e) {
                e.printStackTrace();
            }
        }
        else{
            alertbox("Gps Status!!", "Your GPS is: OFF");
        }

    }

    private void setupViewPager(ViewPager viewPager) {
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        mSectionsPagerAdapter.addFrag(new EventsTabFragment(), "ONE");
        mSectionsPagerAdapter.addFrag(new ActivitiesTabFragment(), "TWO");
        mSectionsPagerAdapter.addFrag(new DineOutTabFragment(), "THREE");



        viewPager.setAdapter(mSectionsPagerAdapter);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch(item.getItemId()){

            case R.id.signOut:
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(getApplicationContext(), LogInActivity.class));
                finish();
                return true;


        }

        return super.onOptionsItemSelected(item);
    }



    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        public void addFrag(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public Fragment getItem(int position) {

            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            // Show 3 total pages.
            return mFragmentList.size();
        }



        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return "Events";
                case 1:
                    return "Activities";
                case 2:
                    return "Dine-Out";
            }
            return null;
        }
    }

    protected void alertbox(String title, String mymessage) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Your Device's GPS is Disable")
                .setCancelable(false)
                .setTitle("** Gps Status **")
                .setPositiveButton("Gps On",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                // finish the current activity
                                // AlertBoxAdvance.this.finish();
                                Intent myIntent = new Intent(
                                        Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                                startActivity(myIntent);
                                dialog.cancel();
                            }
                        })
                .setNegativeButton("Cancel",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                // cancel the dialog box
                                dialog.cancel();
                            }
                        });
        AlertDialog alert = builder.create();
        alert.show();
    }

    public class MyLocationListener implements LocationListener {
        private static final String TAG = "Location";

        @Override
        public void onLocationChanged(Location loc) {
            currentLocation.setText("Getting Location...");
//            pb.setVisibility(View.INVISIBLE);

//            String longitude = "Longitude: " + loc.getLongitude();
//            Log.v(TAG, longitude);
//            String latitude = "Latitude: " + loc.getLatitude();
//            Log.v(TAG, latitude);

        /*------- To get city name from coordinates -------- */
            String cityName = null;
            Geocoder gcd = new Geocoder(getBaseContext(), Locale.getDefault());
            List<Address> addresses;
            try {
                addresses = gcd.getFromLocation(loc.getLatitude(),
                        loc.getLongitude(), 1);
                if (addresses.size() > 0) {
//                Log.v(TAG, addresses.toString());
                    cityName = addresses.get(0).getSubLocality() + ", " + addresses.get(0).getLocality();
                    Toast.makeText(
                            getBaseContext(),
                            "Location: " + cityName, Toast.LENGTH_SHORT).show();
                }
            }
            catch (IOException e) {
                e.printStackTrace();
            }
            currentLocation.setText(cityName);
        }

        @Override
        public void onProviderDisabled(String provider) {}

        @Override
        public void onProviderEnabled(String provider) {}

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {}
    }
    private Boolean displayGpsStatus() {
        ContentResolver contentResolver = getBaseContext()
                .getContentResolver();
        boolean gpsStatus = Settings.Secure
                .isLocationProviderEnabled(contentResolver,
                        LocationManager.GPS_PROVIDER);
        if (gpsStatus) {
            return true;

        } else {
            return false;
        }
    }


}
