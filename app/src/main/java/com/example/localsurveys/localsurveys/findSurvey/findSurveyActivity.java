package com.example.localsurveys.localsurveys.findSurvey;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.example.localsurveys.localsurveys.R;
import com.example.localsurveys.localsurveys.adapters.CustomAdapter;
import com.example.localsurveys.localsurveys.firebase.FirebaseHelper;
import com.example.localsurveys.localsurveys.models.AnswerOption;
import com.example.localsurveys.localsurveys.models.Question;
import com.example.localsurveys.localsurveys.models.Survey;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class findSurveyActivity extends AppCompatActivity {

    private Button btnRefresh;
    private Button testButton;
    private Toolbar toolbar;

    private ArrayList<Survey> surveys;
    private ListView surveyListView;
    private ArrayList<Survey> mockSurvey;

    DatabaseReference db;
    FirebaseHelper helper;
    CustomAdapter adapter;
    CustomAdapter mockAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initializeUI();
        setupFirebaseAndAdapter();

//        adapter = new CustomAdapter(com.example.localsurveys.localsurveys.home.HomeActivity.this, helper.retrieve());
//        surveyListView.setAdapter(adapter);

        //Firebase Workaround: sample survey will be added and displayed directly
        mockSurvey = new ArrayList<>();
        createMockSurvey();
        firebaseWorkaround();
    }

    public void initializeUI() {
        setContentView(R.layout.activity_find_survey);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        surveyListView = (ListView) findViewById(R.id.survey_list);

        testButton = (Button) findViewById(R.id.btn_add);
        testButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Survey testSurvey = Survey.random();
                //mock Array
                mockSurvey.add(Survey.random());
                try {
                    saveSurvey(testSurvey);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        btnRefresh = (Button) findViewById(R.id.btn_refresh);
        btnRefresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getSurveys();
            }
        });
    }

    private void setupFirebaseAndAdapter() {
        Log.d("TEST", "Initializing Firebase");
        this.db = FirebaseDatabase.getInstance().getReference().child("User");
        this.helper = new FirebaseHelper(db);
        Log.d("TEST", "Initialized Firebase!");
        adapter = new CustomAdapter(this, helper.retrieve());
        surveyListView.setAdapter(adapter);
    }

    private void getSurveys() {
//        adapter = new CustomAdapter(com.example.localsurveys.localsurveys.home.HomeActivity.this, helper.retrieve());
//        surveyListView.setAdapter(adapter);
        firebaseWorkaround();
    }

    public void saveSurvey(Survey survey) throws InterruptedException {
        Log.d("TEST", "Saving survey to Database ...");
        Boolean saved;
        if (saved = helper.saveSurvey(survey, "")) {
            getSurveys();
        }
        Log.d("TEST", "Saving success? : " + saved );
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == android.R.id.home) {
            onBackPressed();
        }

        return super.onOptionsItemSelected(item);
    }

    public void firebaseWorkaround(){
        mockAdapter = new CustomAdapter(findSurveyActivity.this, mockSurvey);
        surveyListView.setAdapter(mockAdapter);
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

        mockSurvey.add(s1);
    }

    // TODO Frage beantworten -> durchklicken? -> neue Acitvity mit Next und Abbruch bzw. Skrollfeld mit Textfeldern und Buttons... question_item_xml ähnlich
}