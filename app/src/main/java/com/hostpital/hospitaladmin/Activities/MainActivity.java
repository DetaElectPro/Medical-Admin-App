package com.hostpital.hospitaladmin.Activities;

import android.content.Context;
import android.content.Intent;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import com.google.android.material.tabs.TabLayout;
import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatDelegate;

import com.hostpital.hospitaladmin.Adapters.TabAdapter;
import com.hostpital.hospitaladmin.Fragments.ActiveRequestsFragment;
import com.hostpital.hospitaladmin.Fragments.HistoryRequestsFragment;
import com.hostpital.hospitaladmin.Fragments.MyRequestsFragment;
import com.hostpital.hospitaladmin.Fragments.RequestDoctorFragment;
import com.hostpital.hospitaladmin.Fragments.ShowUserRequestsFragment;
import com.hostpital.hospitaladmin.R;

public class MainActivity extends AppCompatActivity {

    private final int REQUEST_CODE_PERMISSION = 55, PLACE_PICKER_REQUEST = 66, MY_PERMISSIONS_REQUEST_LOCATION = 44;

    private static final String TAG = "MainActivity";
    private static final int ACTIVITY_NUM = 0;
    private BottomNavigationView bottomNavigationView;

    private Context mContext = MainActivity.this;

    private TabAdapter adapter;
    private TabLayout tabLayout;
    private ViewPager viewPager;

    private int[] tabIcons = {
            R.drawable.request_doctor2,
            R.drawable.ambulance2,
            R.drawable.ambulance2,
            R.drawable.icu
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        viewPager = (ViewPager) findViewById(R.id.viewPager);
        tabLayout = (TabLayout) findViewById(R.id.tabLayout);

//         Hide ActionBar
//        if (getSupportActionBar() != null) {
//            getSupportActionBar().hide();
//        }
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
//        setupBottomNavigationView();

        adapter = new TabAdapter(getSupportFragmentManager(), this);
        adapter.addFragment(new RequestDoctorFragment(), "Add");
        adapter.addFragment(new ShowUserRequestsFragment(), "Show");
        adapter.addFragment(new ActiveRequestsFragment(), "Active");
        adapter.addFragment(new HistoryRequestsFragment(), "History");
        adapter.addFragment(new MyRequestsFragment(), "My Requests");
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

//    private void setupBottomNavigationView() {
//        Log.d(TAG, "setupBottomNavigationView: setting up BottomNavigationView");
//        BottomNavigationViewEx bottomNavigationViewEx = (BottomNavigationViewEx) findViewById(R.id.bottomNavViewBar);
//        BottomNavigationViewHelper.setupBottomNavigationView(bottomNavigationViewEx);
//        BottomNavigationViewHelper.enableNavigation(mContext, this, bottomNavigationViewEx);
//        Menu menu = bottomNavigationViewEx.getMenu();
//        MenuItem menuItem = menu.getItem(ACTIVITY_NUM);
//        menuItem.setChecked(true);
//    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }



}
