package com.example.idgaf.mobilenavigatoriii.Activities;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.idgaf.mobilenavigatoriii.Fragments.PlacesFragment;
import com.example.idgaf.mobilenavigatoriii.R;
import com.example.idgaf.mobilenavigatoriii.Fragments.UploadedFragment;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    private TextView email;
    private DrawerLayout drawer;
    private static final String TAG = "MainActivity";

    private static final int ERROR_DIALOG_REQUEST = 9001;

    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        auth = FirebaseAuth.getInstance();

        if(FirebaseAuth.getInstance() == null){
            finish();
            startActivity(new Intent(this, LoginActivity.class));
        }
        final FirebaseUser user = auth.getCurrentUser();

        email = (TextView) findViewById(R.id.nav_email);

//        if(isServicesOk()){
//            init();
//        }

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        useremail(user);

        ActionBarDrawerToggle toggle= new ActionBarDrawerToggle(this, drawer, toolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        if(savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                    new PlacesFragment()).commit();
                    navigationView.setCheckedItem(R.id.menuItem2);
        }


    }
    private void init(){

    }

    private void useremail(FirebaseUser m_user){
        NavigationView navVIew = findViewById(R.id.nav_view);
        View header = navVIew.getHeaderView(0);
        email =(TextView) header.findViewById(R.id.nav_email);
        email.setText(m_user.getEmail());
    }
//
//    public boolean isServicesOk(){
//        Log.d(TAG, "isServicesOk: Checking google services version");
//
//        int available = GoogleApiAvailability.getInstance().isGooglePlayServicesAvailable(MainActivity.this);
//
//        if( available == ConnectionResult.SUCCESS){
//            Log.d(TAG, "isServicesOk: Google play services is working");
//            return true;
//        }else if (GoogleApiAvailability.getInstance().isUserResolvableError(available)){
//            //an error occured but can be fixed
//            Log.d(TAG, "isServicesOk: an error occured but we can fix it");
//            Dialog dialog = GoogleApiAvailability.getInstance().getErrorDialog(MainActivity.this, available, ERROR_DIALOG_REQUEST);
//            dialog.show();
//        }else{
//            Toast.makeText(this, "You can't make maps request", Toast.LENGTH_SHORT).show();
//        }
//        return false;
//    }

    @Override
    public void onBackPressed(){
        if(drawer.isDrawerOpen(GravityCompat.START)){
            drawer.closeDrawer(GravityCompat.START);
        }else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.menuItem:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new UploadedFragment()).commit();
                break;
            case R.id.menuItem2:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new PlacesFragment()).commit();
                break;
            case R.id.NextMenuItem:
                Toast.makeText(this, "Action was made", Toast.LENGTH_SHORT).show();
                break;
            case R.id.NextMenuItem2:
                auth.signOut();
                finish();
                startActivity(new Intent(this, LoginActivity.class));
                break;
        }

        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
