package com.example.localsurveys.localsurveys.createSurvey;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.localsurveys.localsurveys.GPS_SERVICE;
import com.example.localsurveys.localsurveys.R;
import com.example.localsurveys.localsurveys.SurveyDoneActivity;
import com.example.localsurveys.localsurveys.firebase.FirebaseHelper;
import com.example.localsurveys.localsurveys.models.AnswerOption;
import com.example.localsurveys.localsurveys.models.Question;
import com.example.localsurveys.localsurveys.models.Survey;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class AddQuestionActivity extends AppCompatActivity {
    Survey survey;

    Toolbar toolbar;
    FloatingActionButton fabMore, fabDone;

    final ArrayList<EditText> optionsEditTexts = new ArrayList<>();
    EditText questionText, option1, option2, option3, option4;

    FirebaseHelper helper;
    DatabaseReference db;
    FirebaseAuth auth;

    private BroadcastReceiver broadcastReceiver;
    private double longitude;
    private double latitude;


    @Override
    protected void onResume() {
        super.onResume();
        if (broadcastReceiver == null) {
            broadcastReceiver = new BroadcastReceiver() {
                @Override
                public void onReceive(Context context, Intent intent) {
                    longitude = (double) intent.getExtras().get("COORD_LONGITUDE");
                    latitude = (double) intent.getExtras().get("COORD_LATITUDE");
                }
            };
        }
        registerReceiver(broadcastReceiver, new IntentFilter("location_update"));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterGpsService();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode == 100){
            if( grantResults[0] == PackageManager.PERMISSION_GRANTED && grantResults[1] == PackageManager.PERMISSION_GRANTED){
                enableFabDoneButton();
            }else {
                fabDone.setEnabled(false);
                runtimePermission();
            }
        }
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_question);
        inflateToolbar();
        startGpsService();
        initializeUI();
        initalizeFirebase();
        handleIntent();
        if(!runtimePermission()) {
            enableFabDoneButton();
        }
    }

    private void startGpsService() {
        Intent i = new Intent(getApplicationContext(), GPS_SERVICE.class);
        startService(i);
    }

    private void inflateToolbar() {
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private void initializeUI() {
        fabMore = findViewById(R.id.fabMore);
        fabDone = findViewById(R.id.fabDone);
        fabDone.setEnabled(false);
        questionText = findViewById(R.id.questionText);
        option1 = findViewById(R.id.option1);
        option2 = findViewById(R.id.option2);
        option3 = findViewById(R.id.option3);
        option4 = findViewById(R.id.option4);

        fabMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                optionsEditTexts.clear();
                optionsEditTexts.add(option1);
                optionsEditTexts.add(option2);
                optionsEditTexts.add(option3);
                optionsEditTexts.add(option4);
                Question question = new Question(questionText.getText().toString());
                for (EditText option : optionsEditTexts) {
                    if (option.getText().toString() != "") {
                        AnswerOption answerOption = new AnswerOption(option.getText().toString());
                        question.addAnswerOption(answerOption);
                    }
                }
                survey.addQuestion(question);

                Toast.makeText(getApplicationContext(), "Question " + survey.getLength() + " added!", Toast.LENGTH_SHORT).show();
                resetView();
                Log.d("TEST", survey.toString());
            }
        });
    }

    private void initalizeFirebase() {
        this.auth = FirebaseAuth.getInstance();
        this.db = FirebaseDatabase.getInstance().getReference().child("User");
        this.helper = new FirebaseHelper(db);
    }

    private void handleIntent() {
        Bundle extras = getIntent().getExtras();
        survey = (Survey) extras.get("SURVEY");
        Log.d("TEST", survey.toString());
    }

    private void resetView() {
        questionText.setText("");
        option1.setText("");
        option2.setText("");
        option3.setText("");
        option4.setText("");
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == android.R.id.home) {
            onBackPressed();
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        Toast.makeText(getApplicationContext(), "All questions deleted", Toast.LENGTH_SHORT).show();
        super.onBackPressed();
    }

    private void updateSurvey() {
        long now = System.currentTimeMillis();
        long surveyEnd = now + survey.getDuration();
        survey.setFromDate(now)
                .setToDate(surveyEnd)
                .setLatitude(latitude)
                .setLongitude(longitude);
    }

    private void saveSurvey() {
        String email = auth.getCurrentUser().getEmail();
        if (helper.saveSurvey(survey, email)) {
            Toast.makeText(getApplicationContext(), "Survey saved!", Toast.LENGTH_SHORT).show();
            Intent i = new Intent(AddQuestionActivity.this, SurveyDoneActivity.class);
            Bundle b = new Bundle();
            b.putSerializable("SURVEY", survey);
            i.putExtras(b);
            startActivity(i);
        }
    }

    private void enableFabDoneButton() {
        fabDone.setEnabled(true);
        fabDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (survey.getQuestions().size() == 0) {
                    Toast.makeText(getApplicationContext(), "Add questions first!", Toast.LENGTH_SHORT).show();
                    return;
                }
                updateSurvey();
                if (broadcastReceiver != null) {
                    unregisterReceiver(broadcastReceiver);
                }
                saveSurvey();
            }
        });
    }

    private void unregisterGpsService() {
        if (broadcastReceiver != null) {
            unregisterReceiver(broadcastReceiver);
        }
    }
}
