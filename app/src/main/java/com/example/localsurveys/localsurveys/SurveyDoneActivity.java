package com.example.localsurveys.localsurveys;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.localsurveys.localsurveys.home.HomeActivity;
import com.example.localsurveys.localsurveys.models.Survey;
import com.example.localsurveys.localsurveys.survey.SurveyDetailActivity;

public class SurveyDoneActivity extends AppCompatActivity {
    Survey survey;
    Button toMySurveys, viewSurvey;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_survey_done);
        handleIntent();
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
                Intent i = new Intent(SurveyDoneActivity.this, SurveyDetailActivity.class);
                Bundle b = new Bundle();
                b.putSerializable("SURVEY", survey);
                i.putExtras(b);
                startActivity(i);
            }
        });
    }


    private void handleIntent() {
        Bundle b = getIntent().getExtras();
        this.survey = (Survey) b.get("SURVEY");
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(SurveyDoneActivity.this, HomeActivity.class));
    }
}
