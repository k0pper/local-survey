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
import com.example.localsurveys.localsurveys.models.AnswerOption;
import com.example.localsurveys.localsurveys.models.Question;
import com.example.localsurveys.localsurveys.models.Survey;

import java.util.ArrayList;

public class FindSurveysActivity extends AppCompatActivity {

    private Button scanButton, stopButton;
//    private TextView coordsText;
    private BroadcastReceiver broadcastReceiver;
    private ProgressBar spinner;

    private double longitude;
    private double latitude;

    //Mock Umfrage anzeigen lassen - Showzwecke für die Answer Activity
    private ListView liveSurveys;
    private ArrayList<Survey> mockSurveys;

    //Umfrage lässt sich beim LiveSurveyAdapter nicht starten (wieso auch immer)
    private SurveyToAnswerAdapter mockAdapter;
//    private LiveSurveysAdapter mockAdapter;


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

        mockUmfrageAnzeigen();

        if(!runtimePermission()) {
            enableButtons();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (broadcastReceiver != null) {
            unregisterReceiver(broadcastReceiver);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode == 100){
            if( grantResults[0] == PackageManager.PERMISSION_GRANTED && grantResults[1] == PackageManager.PERMISSION_GRANTED){
                enableButtons();
            }else {
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
    }

    private boolean runtimePermission() {
        // If Build Version > 23 and Location Permissions are NOT granted
        if(Build.VERSION.SDK_INT >= 23 && ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED){
            // Request permissions for GPS
            requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION},100);
            return true;
        }
        return false;
    }

    private void enableButtons() {
        scanButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                spinner.setVisibility(View.VISIBLE);
                stopButton.setVisibility(View.VISIBLE);
                scanButton.setVisibility(View.INVISIBLE);
                Log.d("TEST", "Started Service");
                Intent i = new Intent(getApplicationContext(), GPS_SERVICE.class);
                startService(i);
            }
        });

        stopButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                spinner.setVisibility(View.INVISIBLE);
                stopButton.setVisibility(View.INVISIBLE);
                scanButton.setVisibility(View.VISIBLE);
                Intent i = new Intent(getApplicationContext(),GPS_SERVICE.class);
                stopService(i);
            }
        });

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == android.R.id.home) {
            onBackPressed();
        }

        return super.onOptionsItemSelected(item);
    }

    public void mockUmfrageAnzeigen(){
        liveSurveys = findViewById(R.id.listView2);
        mockSurveys = new ArrayList<>();
        createMockSurvey();
        mockAdapter = new SurveyToAnswerAdapter(FindSurveysActivity.this, mockSurveys);
//        mockAdapter = new LiveSurveysAdapter(FindSurveysActivity.this, mockSurveys);
        liveSurveys.setAdapter(mockAdapter);
    }

    public void createMockSurvey(){

        Survey s1 = new Survey();
        s1.setTitle("How are you?");
        s1.setDuration(60 * 60000);
        s1.setRadius(50);
        s1.setFromDate(System.currentTimeMillis());
        s1.setToDate(s1.getFromDate() + s1.getDuration());

        Question q1 = new Question("How was lunch?");
        q1.addAnswerOption(new AnswerOption("awesome"));
        q1.addAnswerOption(new AnswerOption("soso"));
        s1.addQuestion(q1);

        Question q2 = new Question(("And your day?"));
        q2.addAnswerOption(new AnswerOption("I need coffee"));
        q2.addAnswerOption(new AnswerOption("I need sleep"));
        s1.addQuestion(q2);

        mockSurveys.add(s1);
    }
}
