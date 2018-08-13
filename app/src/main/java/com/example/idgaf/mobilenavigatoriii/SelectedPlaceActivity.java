package com.example.idgaf.mobilenavigatoriii.Activities;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.example.idgaf.mobilenavigatoriii.Fragments.MapFragment;
import com.example.idgaf.mobilenavigatoriii.R;
import com.example.idgaf.mobilenavigatoriii.Fragments.SelectedPlaceFragment;
import com.example.idgaf.mobilenavigatoriii.Fragments.TransportationFragment;

public class SelectedPlaceActivity extends AppCompatActivity {

    private static final String TAG = "SelectedPlaceActivity";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_selectedplace);
        Log.d(TAG, "onCreate: started");

        BottomNavigationView bottomNav = findViewById(R.id.bottom_navigation);
        bottomNav.setOnNavigationItemSelectedListener(bottomNavigationListener);

        getIncomingIntent();

    }

    public boolean onCreateOptionsMenu(Menu  menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_tab_bottom_navigation, menu);
        return true;
    }

    private void getIncomingIntent() {
        Log.d(TAG, "getIncomingIntent: check for incoming intent");

        if (getIntent().hasExtra("image_content") && getIntent().hasExtra("image_name")) {
            Log.d(TAG, "getIncomingIntent: found intent extras");

            String url = getIntent().getStringExtra("image_content");
            String imageName = getIntent().getStringExtra("image_name");
            String description = getIntent().getStringExtra("image_description");

            SelectedPlaceFragment detail = new SelectedPlaceFragment();
            detail.setArguments(getIntent().getExtras());
            getSupportFragmentManager().beginTransaction().add(R.id.fragment_container2, detail).commit();
        }
    }

    private BottomNavigationView.OnNavigationItemSelectedListener bottomNavigationListener = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item ) {
        Fragment selectedFragment = null;

        switch (item.getItemId()) {
            case R.id.description:
                selectedFragment = new SelectedPlaceFragment();
                break;
            case R.id.transportation:
                selectedFragment = new TransportationFragment();
                break;
            case R.id.map:
                selectedFragment = new MapFragment();
                break;
            }
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container2, selectedFragment).commit();
            return true;
        }
    };


}


