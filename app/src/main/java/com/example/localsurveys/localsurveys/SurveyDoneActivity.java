package com.example.localsurveys.localsurveys;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.localsurveys.localsurveys.home.HomeActivity;

public class SurveyDoneActivity extends AppCompatActivity {
    Button toMySurveys, viewSurvey;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_survey_done);
        initializeUI();
    }

    private void initializeUI() {
        toMySurveys = findViewById(R.id.toMySurveys);
        viewSurvey = findViewById(R.id.viewSurvey);

        toMySurveys.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent (SurveyDoneActivity.this, HomeActivity.class));
            }
        });

        viewSurvey.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(SurveyDoneActivity.this, HomeActivity.class));
    }
}
