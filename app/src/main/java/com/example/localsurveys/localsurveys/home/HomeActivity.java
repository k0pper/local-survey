package com.example.localsurveys.localsurveys.home;


import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
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
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.localsurveys.localsurveys.R;
import com.example.localsurveys.localsurveys.adapters.CustomAdapter;
import com.example.localsurveys.localsurveys.firebase.FirebaseHelper;
import com.example.localsurveys.localsurveys.info.InfoActivity;
import com.example.localsurveys.localsurveys.login.LoginActivity;
import com.example.localsurveys.localsurveys.models.Survey;
import com.example.localsurveys.localsurveys.models.User;
import com.example.localsurveys.localsurveys.settings.SettingsActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class HomeActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private FirebaseAuth auth;
    private TextView textUsername;
    private NavigationView navigationView;
    private Toolbar toolbar;
    private Button btnRefresh;
    private Button testButton;

    private ArrayList<Survey> surveys;
    private ListView surveyListView;

    private FirebaseHelper helper;
    private DatabaseReference db;


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

        } else if (id == R.id.nav_my_surveys) {

        } else if (id == R.id.nav_settings) {
            startActivity(new Intent(HomeActivity.this, SettingsActivity.class));
        } else if (id == R.id.nav_logout) {
            auth.signOut();
            startActivity(new Intent(HomeActivity.this, LoginActivity.class));
            finish();
        }
        else if (id == R.id.nav_info) {
            startActivity(new Intent(HomeActivity.this, InfoActivity.class));
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

        surveyListView = (ListView) findViewById(R.id.survey_list);
        db = FirebaseDatabase.getInstance().getReference();
        helper = new FirebaseHelper(db);
        surveys = new ArrayList<Survey>();

        testButton = (Button) findViewById(R.id.add_button);
        testButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Survey testSurvey = new Survey("title", 20, new User("rofl@kopter.de"), 30000, 310000);
                saveSurvey(testSurvey);
                Toast.makeText(HomeActivity.this, "Survey saved", Toast.LENGTH_SHORT);
            }
        });

        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        btnRefresh = (Button) findViewById(R.id.refresh_button);
        btnRefresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getSurveys();
            }
        });
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

    private void getSurveys() {
        surveys = helper.retrieve();
        ListAdapter customAdapter = new CustomAdapter(this, surveys);
        surveyListView.setAdapter(customAdapter);
    }

    public void saveSurvey(Survey survey) {
        helper.saveSurveyTest(survey);
    }
}
