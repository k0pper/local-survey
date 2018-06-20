package com.example.localsurveys.localsurveys.survey;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.support.v7.widget.Toolbar;

import com.example.localsurveys.localsurveys.GPS_SERVICE;
import com.example.localsurveys.localsurveys.R;
import com.example.localsurveys.localsurveys.adapters.LiveSurveysAdapter;
import com.example.localsurveys.localsurveys.adapters.SurveyToAnswerAdapter;
import com.example.localsurveys.localsurveys.firebase.FirebaseHelper;
import com.example.localsurveys.localsurveys.home.HomeActivity;
import com.example.localsurveys.localsurveys.models.AnswerOption;
import com.example.localsurveys.localsurveys.models.Question;
import com.example.localsurveys.localsurveys.models.Survey;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class FindSurveysActivity extends AppCompatActivity {

    private Button scanButton, stopButton;
    private BroadcastReceiver broadcastReceiver;
    private ProgressBar spinner;

    private double longitude;
    private double latitude;

    //Mock Umfrage anzeigen lassen - Showzwecke für die Answer Activity
    private ListView listViewSurveys;
    private ArrayList<Survey> visibleSurveys;

    private LiveSurveysAdapter liveSurveysAdapter;

    private DatabaseReference db;
    private FirebaseHelper helper;


    @Override
    protected void onResume() {
        super.onResume();
        if (broadcastReceiver == null) {
            broadcastReceiver = new BroadcastReceiver() {
                @Override
                public void onReceive(Context context, Intent intent) {
                    longitude = (double) intent.getExtras().get("COORD_LONGITUDE");
                    latitude = (double) intent.getExtras().get("COORD_LATITUDE");
//                    coordsText.append("\n" + longitude + " " + latitude );
                    Log.d("TEST", "Longitude: " + longitude);
                    Log.d("TEST", "Latitude: " + latitude);
                }
            };
        }
        registerReceiver(broadcastReceiver, new IntentFilter("location_update"));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_surveys);
        initializeUI();
        initializeFirebase();
        if (!runtimePermission()) {
            enableButtons();
            startGpsService();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (broadcastReceiver != null) {
            unregisterReceiver(broadcastReceiver);
        }
        stopGpsService();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 100) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED && grantResults[1] == PackageManager.PERMISSION_GRANTED) {
                enableButtons();
            } else {
                runtimePermission();
            }
        }
    }

    private void initializeUI() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        scanButton = findViewById(R.id.scan_button);
        stopButton = findViewById(R.id.stop_button);
//        coordsText = findViewById(R.id.coords);
        spinner = findViewById(R.id.spinner);
        listViewSurveys = findViewById(R.id.live_surveys);
    }

    private void initializeFirebase() {
        this.db = FirebaseDatabase.getInstance().getReference();
        this.helper = new FirebaseHelper(db);
    }

    private boolean runtimePermission() {
        // If Build Version > 23 and Location Permissions are NOT granted
        if (Build.VERSION.SDK_INT >= 23 && ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // Request permissions for GPS
            requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, 100);
            return true;
        }
        return false;
    }

    private void enableButtons() {
        scanButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setLoading();
                liveSurveysAdapter = new LiveSurveysAdapter(FindSurveysActivity.this, helper.retrieveVisibleSurveys(longitude, latitude));
                listViewSurveys.setAdapter(liveSurveysAdapter);
                setNoLoading();
            }
        });

        stopButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setNoLoading();
            }
        });

    }

    private void setNoLoading() {
        Log.d("TEST", "Loading stopped");
        spinner.setVisibility(View.INVISIBLE);
        scanButton.setVisibility(View.VISIBLE);
    }

    private void stopGpsService() {
        Log.d("TEST", "Stopped Service");
        Intent i = new Intent(getApplicationContext(), GPS_SERVICE.class);
        stopService(i);
    }

    private void setLoading() {
        Log.d("TEST", "Loading started");
        spinner.setVisibility(View.VISIBLE);
        scanButton.setVisibility(View.INVISIBLE);
    }

    private void startGpsService() {
        Log.d("TEST", "Started Service");
        Intent i = new Intent(getApplicationContext(), GPS_SERVICE.class);
        startService(i);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == android.R.id.home) {
            onBackPressed();
        }

        return super.onOptionsItemSelected(item);
    }

}
