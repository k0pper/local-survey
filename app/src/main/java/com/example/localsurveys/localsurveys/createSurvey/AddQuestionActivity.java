package com.example.localsurveys.localsurveys.createSurvey;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

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



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_question);
        inflateToolbar();
        initializeUI();
        initalizeFirebase();
        handleIntent();
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

        fabDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (survey.getQuestions().size() == 0) {
                    Toast.makeText(getApplicationContext(), "Add questions first!", Toast.LENGTH_SHORT).show();
                    return;
                }
                Boolean saved;
                String email = auth.getCurrentUser().getEmail();
                long now = System.currentTimeMillis();
                long surveyEnd = now + survey.getDuration();
                survey.setFromDate(now);
                survey.setToDate(surveyEnd);
                if (saved = helper.saveSurvey(survey, email)) {
                    Toast.makeText(getApplicationContext(), "Survey saved!", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent (AddQuestionActivity.this, SurveyDoneActivity.class));
                }
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
}
