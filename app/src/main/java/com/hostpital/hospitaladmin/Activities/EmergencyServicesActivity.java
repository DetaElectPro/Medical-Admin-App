package com.hostpital.hospitaladmin.Activities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.tabs.TabLayout;
import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;

import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;


//import com.android.volley.AuthFailureError;
//import com.android.volley.NetworkError;
//import com.android.volley.NetworkResponse;
//import com.android.volley.NoConnectionError;
//import com.android.volley.Request;
//import com.android.volley.Response;
//import com.android.volley.TimeoutError;
//import com.android.volley.VolleyError;
//import com.android.volley.toolbox.JsonArrayRequest;

import com.hostpital.hospitaladmin.Adapters.Tab2Adapter;
import com.hostpital.hospitaladmin.Fragments.RequestEmergencyFragment;
import com.hostpital.hospitaladmin.Fragments.ShowEmergencyFragment;
import com.hostpital.hospitaladmin.Fragments.ShowMyEmergencyRequestsFragment;
import com.hostpital.hospitaladmin.Fragments.ShowMyEmergencyRequestsFragment2;
import com.hostpital.hospitaladmin.R;
import com.hostpital.hospitaladmin.Utils.CustomDialog;
import com.hostpital.hospitaladmin.Utils.Utilities;

public class EmergencyServicesActivity extends AppCompatActivity {

    public Context context = EmergencyServicesActivity.this;
    public Activity activity = EmergencyServicesActivity.this;

    private static final String TAG = "EmergencyActivity";
    private static final int ACTIVITY_NUM = 1;
//    private BottomNavigationView bottomNavigationView;

    private Spinner typeSpinner;
    private Button request;
    private EditText ed_hospital,ed_price, ed_address, available_bed;

    CustomDialog customDialog;
    Boolean isInternet;
    Utilities utils = new Utilities();

    private String token;

    private Context mContext = EmergencyServicesActivity.this;

    private Tab2Adapter adapter;
    private TabLayout tabLayout;
    private ViewPager viewPager;

    private int[] tabIcons = {
            R.drawable.request_doctor2,
            R.drawable.icu
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_emergency_services);

        viewPager = (ViewPager) findViewById(R.id.viewPager);
        tabLayout = (TabLayout) findViewById(R.id.tabLayout);

        // Hide ActionBar
//        if (getSupportActionBar() != null) {
//            getSupportActionBar().hide();
//        }

//        setupBottomNavigationView();

        adapter = new Tab2Adapter(getSupportFragmentManager(), this);
        adapter.addFragment(new RequestEmergencyFragment(), "Request");
        adapter.addFragment(new ShowEmergencyFragment(), "Show");
        adapter.addFragment(new ShowMyEmergencyRequestsFragment2(), "Accept");
        adapter.addFragment(new ShowMyEmergencyRequestsFragment(), "My Requests");
        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);
        highLightCurrentTab(0);

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }
            @Override
            public void onPageSelected(int position) {
                highLightCurrentTab(position);
            }
            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });

    }

    private void highLightCurrentTab(int position) {
        for (int i = 0; i < tabLayout.getTabCount(); i++) {
            TabLayout.Tab tab = tabLayout.getTabAt(i);
            assert tab != null;
            tab.setCustomView(null);
            tab.setCustomView(adapter.getTabView(i));
        }
        TabLayout.Tab tab = tabLayout.getTabAt(position);
        assert tab != null;
        tab.setCustomView(null);
        tab.setCustomView(adapter.getSelectedTabView(position));
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

}
