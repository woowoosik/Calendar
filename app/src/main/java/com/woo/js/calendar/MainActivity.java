package com.woo.js.calendar;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;

import com.woo.js.calendar.Fragment.DayFragment;
import com.woo.js.calendar.Fragment.MonthFragment;
import com.woo.js.calendar.Fragment.WeekFragment;


public class MainActivity extends AppCompatActivity {

    private FragmentManager fragmentManager = getSupportFragmentManager();

    private MonthFragment monthFragment ;
    private WeekFragment weekFragment ;
    private DayFragment dayFragment ;

    private SharedPreferences prefs;
    private  SharedPreferences.Editor edit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        prefs = getSharedPreferences("pref_tab", MODE_PRIVATE);
        String tab = prefs.getString("pref_tab","");
        Log.e("@tab", tab);

            FragmentTransaction transaction = fragmentManager.beginTransaction();
        if(tab.equals("2")){
            dayFragment = new DayFragment();
            transaction.replace(R.id.main_fragment, dayFragment).commitAllowingStateLoss();
            navigation.getMenu().findItem(R.id.navigation_day).setChecked(true);
        }else if(tab.equals("1")){
            weekFragment = new WeekFragment();
            transaction.replace(R.id.main_fragment, weekFragment).commitAllowingStateLoss();
            navigation.getMenu().findItem(R.id.navigation_week).setChecked(true);
        } else if(tab.equals("0")){
            monthFragment = new MonthFragment();
            transaction.replace(R.id.main_fragment, monthFragment).commitAllowingStateLoss();
            navigation.getMenu().findItem(R.id.navigation_month).setChecked(true);
        } else{
            monthFragment = new MonthFragment();
            transaction.replace(R.id.main_fragment, monthFragment).commitAllowingStateLoss();
            navigation.getMenu().findItem(R.id.navigation_month).setChecked(true);
        }

        FloatingActionButton fb = findViewById(R.id.floating);
        fb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, ScheduleActivity.class));
            }
        });
    }

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            FragmentTransaction transaction = fragmentManager.beginTransaction();
            switch (item.getItemId()) {
                case R.id.navigation_month:
                    edit = prefs.edit();
                    String mnum="0";
                    edit.putString("pref_tab", mnum);
                    Log.e("@ week_tab ",mnum);
                    edit.apply();

                    if(monthFragment == null) {
                        monthFragment = new MonthFragment();
                        if (weekFragment != null){
                            fragmentManager.beginTransaction().hide(weekFragment).commit();}
                        if (dayFragment != null){
                            fragmentManager.beginTransaction().hide(dayFragment).commit();}
                        fragmentManager.beginTransaction().add(R.id.main_fragment, monthFragment).commit();
                    }else {

                        if (monthFragment != null)
                            fragmentManager.beginTransaction().show(monthFragment).commit();
                        if (weekFragment != null)
                            fragmentManager.beginTransaction().hide(weekFragment).commit();
                        if (dayFragment != null)
                            fragmentManager.beginTransaction().hide(dayFragment).commit();
                    }

                    return true;
                case R.id.navigation_week:
                    edit = prefs.edit();
                    String wnum="1";
                    edit.putString("pref_tab", wnum);
                    Log.e("@ month_tab ",wnum);
                    edit.apply();
                    if(weekFragment == null) {
                        weekFragment = new WeekFragment();

                        if (monthFragment != null){
                        fragmentManager.beginTransaction().hide(monthFragment).commit();}
                        fragmentManager.beginTransaction().add(R.id.main_fragment, weekFragment).commit();
                        if (dayFragment != null){
                        fragmentManager.beginTransaction().hide(dayFragment).commit();}
                    }else {

                        if (monthFragment != null)
                            fragmentManager.beginTransaction().hide(monthFragment).commit();
                        if (weekFragment != null)
                            fragmentManager.beginTransaction().show(weekFragment).commit();
                        if (dayFragment != null)
                            fragmentManager.beginTransaction().hide(dayFragment).commit();
                    }

                    return true;
                case R.id.navigation_day:
                    edit = prefs.edit();
                    String dnum="2";
                    edit.putString("pref_tab", dnum);
                    Log.e("@ day_tab ",dnum);
                    edit.apply();
                    if(dayFragment == null) {

                        dayFragment = new DayFragment();

                        if (monthFragment != null)
                            fragmentManager.beginTransaction().hide(monthFragment).commit();
                        if (weekFragment != null)
                            fragmentManager.beginTransaction().hide(weekFragment).commit();
                        fragmentManager.beginTransaction().add(R.id.main_fragment, dayFragment).commit();
                    }else {

                        if (monthFragment != null)
                            fragmentManager.beginTransaction().hide(monthFragment).commit();
                        if (weekFragment != null)
                            fragmentManager.beginTransaction().hide(weekFragment).commit();
                        if (dayFragment != null)
                            fragmentManager.beginTransaction().show(dayFragment).commit();
                    }

                    return true;
            }
            return false;
        }

    };

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        if( newConfig.orientation == Configuration.ORIENTATION_PORTRAIT){

        }
        else if(newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE){

        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.e("@main_onStart","onStart");
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.e("@main_onResume","onResume");

    }
    @Override
    protected void onPause() {
        super.onPause();

        Log.e("@main_onPause","onPause");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.e("@main_onStop","_main_onStop");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.e("@main_onDestory","_main_onDestory");

    }
}
