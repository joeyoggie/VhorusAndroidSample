package com.vhorus.androidsample.activities;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import com.vhorus.androidsample.R;
import com.vhorus.androidsample.fragments.LocationsFragment;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = MainActivity.class.getSimpleName();

    private static MainActivity mInstance;

    Toolbar toolbar;
    private static TextView mTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mInstance = this;
        setContentView(R.layout.activity_main);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        mTitle = (TextView) toolbar.findViewById(R.id.toolbar_title);
        mTitle.setText(getString(R.string.app_name));
        mTitle.setTextColor(getResources().getColor(R.color.whiteColor));

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        LocationsFragment locationsFragment = new LocationsFragment();
        fragmentTransaction.replace(R.id.fragment_view, locationsFragment, "locationsFragment");
        fragmentManager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
        fragmentTransaction.commit();
    }

    public static void setActionBarTitle(String title, int colorID){
        mTitle.setText(title);
        mTitle.setTextColor(colorID);
    }
}
