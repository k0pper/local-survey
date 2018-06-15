package com.example.localsurveys.localsurveys.survey;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import com.example.localsurveys.localsurveys.R;
import com.example.localsurveys.localsurveys.models.Survey;

public class SurveyDetailActivity extends AppCompatActivity {
    Toolbar toolbar;
    Survey survey;
    TextView surveyTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_survey_detail);
        // Order is important
        setupToolbar();
        handleIntent();
        initializeUI();
    }

    private void setupToolbar() {
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private void handleIntent() {
        Bundle bundle = getIntent().getExtras();
        survey = (Survey) bundle.get("SURVEY");
    }

    private void initializeUI() {
        surveyTitle = findViewById(R.id.survey_title);
        surveyTitle.setText(survey.getTitle());
    }
}
