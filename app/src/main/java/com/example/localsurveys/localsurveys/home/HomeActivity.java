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
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.example.localsurveys.localsurveys.R;
import com.example.localsurveys.localsurveys.adapters.CustomAdapter;
import com.example.localsurveys.localsurveys.createSurvey.CreateSurveyActivity;
import com.example.localsurveys.localsurveys.firebase.FirebaseHelper;
import com.example.localsurveys.localsurveys.info.InfoActivity;
import com.example.localsurveys.localsurveys.login.LoginActivity;
import com.example.localsurveys.localsurveys.models.Survey;
import com.example.localsurveys.localsurveys.models.User;
import com.example.localsurveys.localsurveys.settings.SettingsActivity;
import com.example.localsurveys.localsurveys.survey.FindSurveysActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class HomeActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    // UI
    private TextView textUsername;
    private NavigationView navigationView;
    private Toolbar toolbar;
    private Button btnRefresh;
    private Button testButton;

    private ArrayList<Survey> surveys;
    private ListView surveyListView;

    // Firebase
    FirebaseAuth auth;
    DatabaseReference db;
    FirebaseHelper helper;
    CustomAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initializeUI();
        setupFirebaseAndAdapter();
        handleIntent();
        adapter = new CustomAdapter(HomeActivity.this, helper.retrieve());
        surveyListView.setAdapter(adapter);
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
            startActivity(new Intent(HomeActivity.this, HomeActivity.class));
        } else if (id == R.id.nav_start) {
            startActivity(new Intent(HomeActivity.this, CreateSurveyActivity.class));
        } else if (id == R.id.nav_find) {
            startActivity(new Intent (HomeActivity.this, FindSurveysActivity.class));
        } else if (id == R.id.nav_settings) {
            startActivity(new Intent(HomeActivity.this, SettingsActivity.class));
        } else if (id == R.id.nav_logout) {
            auth.signOut();
            startActivity(new Intent(HomeActivity.this, LoginActivity.class));
            finish();
        } else if (id == R.id.nav_info) {
            startActivity(new Intent(HomeActivity.this, InfoActivity.class));
        }


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void initializeUI() {
        setContentView(R.layout.activity_home_activity);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        surveyListView = (ListView) findViewById(R.id.survey_list);

        testButton = (Button) findViewById(R.id.add_button);
        testButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Survey testSurvey = Survey.random();
                try {
                    saveSurvey(testSurvey);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
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

    private void setupFirebaseAndAdapter() {
        Log.d("TEST", "Initializing Firebase");
        this.auth = FirebaseAuth.getInstance();
        this.db = FirebaseDatabase.getInstance().getReference().child("User");
        this.helper = new FirebaseHelper(db);
        Log.d("TEST", "Initialized Firebase!");
        adapter = new CustomAdapter(this, helper.retrieve());
        surveyListView.setAdapter(adapter);
    }

    private void getSurveys() {
        adapter = new CustomAdapter(HomeActivity.this, helper.retrieve());
        surveyListView.setAdapter(adapter);
    }

    public void saveSurvey(Survey survey) throws InterruptedException {
        Log.d("TEST", "Saving survey to Database ...");
        Boolean saved;
        if (saved = helper.saveSurvey(survey, "")) {
            getSurveys();
        }
        Log.d("TEST", "Saving success? : " + saved );
    }

}
