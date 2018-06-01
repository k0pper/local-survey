package com.example.localsurveys.localsurveys.home;


import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.localsurveys.localsurveys.R;
import com.example.localsurveys.localsurveys.login.LoginActivity;
import com.google.firebase.auth.FirebaseAuth;

// BeispielZweig3
public class HomeActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private FirebaseAuth auth;
    private TextView textUsername;
    private NavigationView navigationView;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initializeActivity();
        handleIntent();
        auth = FirebaseAuth.getInstance();
        showFragment(new SurveyOverviewFragment());
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_home) {
            // Navigate to Home Activity
        } else if (id == R.id.nav_start) {
            // Navigate to Survey Start Activity
        } else if (id == R.id.nav_find) {
            // Navigate to Find Survey Activity
        } else if (id == R.id.nav_history) {
            // Navigate to History Activity
        } else if (id == R.id.nav_my_surveys) {
            // Navigate to My Surveys Activity
        } else if (id == R.id.nav_settings) {
            // Navigate to Settings Activity
        } else if (id == R.id.nav_logout) {
            auth.signOut();
            startActivity(new Intent(HomeActivity.this, LoginActivity.class));
            finish();
        }


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void initializeActivity() {
        setContentView(R.layout.activity_home_activity);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        toolbar = findViewById(R.id.toolbar);
    }

    public void handleIntent() {
        Bundle extras = getIntent().getExtras();
        String username;
        if (extras != null) {
            username = extras.getString("USERNAME");
        } else {
            username = "NO USERNAME";
        }

        View headerView = navigationView.getHeaderView(0);
        textUsername = headerView.findViewById(R.id.username);
        textUsername.setText(username);
    }

    public void showFragment(Fragment fragment) {
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.replace(R.id.fragment_container, fragment);
        transaction.commit();
    }
}
