package com.example.localsurveys.localsurveys.addSurvey;

import android.os.Bundle;
import android.os.Debug;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;

import com.example.localsurveys.localsurveys.R;
import com.example.localsurveys.localsurveys.models.Question;
import com.example.localsurveys.localsurveys.models.Survey;
import com.example.localsurveys.localsurveys.models.User;

public class AddSurveyActivity extends AppCompatActivity {

    private Survey survey;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_survey);
        Toolbar toolbar = findViewById(R.id.toolbar_add_survey);
        setSupportActionBar(toolbar);

        showFragment(new AddSurveyConfigurationFragment());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.add_survey_menu, menu);
        return true;
    }


    public void showFragment(Fragment fragment) {
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.replace(R.id.fragment_container, fragment);
        transaction.commit();
    }

    public void createSurveyWithConfiguration(String title, String radius, long fromDate, long toDate) {
        int radiusDigit = Integer.parseInt(radius);
        survey = new Survey(title, radiusDigit, new User(), fromDate, toDate);
    }

    public void addQuestionToSurvey(Question question){

    }

    public Survey getSurvey() {
        return survey;
    }
}
