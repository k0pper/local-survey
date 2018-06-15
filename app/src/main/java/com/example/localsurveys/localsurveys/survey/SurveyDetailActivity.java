package com.example.localsurveys.localsurveys.survey;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.TextView;

import com.example.localsurveys.localsurveys.R;
import com.example.localsurveys.localsurveys.adapters.QuestionsAdapter;
import com.example.localsurveys.localsurveys.models.Survey;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class SurveyDetailActivity extends AppCompatActivity {
    QuestionsAdapter adapter;
    Toolbar toolbar;
    Survey survey;
    TextView surveyTitle, questionCount, expiresAt;
    ListView lv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_survey_detail);
        // Order is important
        setupToolbar();
        handleIntent();
        initializeUI();
        showQuestions();
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
        questionCount = findViewById(R.id.questionCount);
        expiresAt = findViewById(R.id.expires_at);

        lv = findViewById(R.id.lv);

        questionCount.setText(survey.getQuestions().size() + " questions");
        surveyTitle.setText(survey.getTitle());
        expiresAt.setText("expires at " + getDateFromMillis(survey.getToDate()));
    }

    private void showQuestions() {
        adapter = new QuestionsAdapter(SurveyDetailActivity.this, survey.getQuestions());
        lv.setAdapter(adapter);
    }

    private String getDateFromMillis(long millis) {
        DateFormat formatter = new SimpleDateFormat("dd.MM.yyyy kk:mm:ss");
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(millis);
        String formatted = formatter.format(calendar.getTime());
        return formatted;
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
