package com.example.localsurveys.localsurveys.answerSurvey;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.localsurveys.localsurveys.R;
import com.example.localsurveys.localsurveys.firebase.FirebaseHelper;
import com.example.localsurveys.localsurveys.models.Answer;
import com.example.localsurveys.localsurveys.models.Question;
import com.example.localsurveys.localsurveys.models.Survey;
import com.google.firebase.database.DatabaseReference;

import java.util.ArrayList;

public class AnswerSurveyActivity extends AppCompatActivity {

    Toolbar toolbar;
    private TextView surveyName;
    private TextView questionName;
    private TextView questionsLeft;

    private ListView answerListView;
    private ArrayAdapter<String> adapter;

    private Survey survey;
    private ArrayList<Question> Allquestions;
    private Question currentQuestion;
    private int posQuestion;
    private ArrayList<String> answerOptions;

    private Answer answer;
    private ArrayList<String> idsQuestion;
    private ArrayList<String> idsAnswer;

    DatabaseReference db;
    FirebaseHelper helper;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initializeUI();
        initializeSurvey();
        myClickListener();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == android.R.id.home) {
            onBackPressed();
        }

        return super.onOptionsItemSelected(item);
    }

    public void initializeUI() {
        Log.d("TEST", "Started Answer Service");

        setContentView(R.layout.activity_answer_survey);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        surveyName = findViewById(R.id.survey_name);
        questionName = findViewById(R.id.question_name);
        questionsLeft = findViewById(R.id.question_count);
        answerListView = (ListView) findViewById(R.id.answer_list);
    }

    public void initializeSurvey(){
        //survey holen & Namen in der UI anzeigen
        survey = (Survey) getIntent().getExtras().get("SURVEY");
        surveyName.setText(survey.getTitle());

        //Array von Fragen holen, schauen ob überhaupt welche Vorhanden sind
        Allquestions = survey.getQuestions();
        if(Allquestions.size() == 0) {
            questionName.setText("No question in this survey");
            questionsLeft.setText("0");
        }
        else {
            //Zähler setzen + die erste Frage holen und UI setzen
            posQuestion = 0;

            getNextQuestion();
        }

        //Antwort Array initialisieren
        answerOptions = new ArrayList<>();

        //Answer erstellen & Umfrage Id zuweisen
        answer = new Answer(survey.getId());

        getNextAnswers();
    }

    public void myClickListener(){
        //zunächst mal die zwei Arrays initialisieren
        idsQuestion = new ArrayList<>();
        idsAnswer = new ArrayList<>();

        //ItemListener
        answerListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                //Ids abspeichern;
                idsQuestion.add(currentQuestion.getId());
                idsAnswer.add(currentQuestion.getAnswerOptions().get(position).getId());

                if (posQuestion < Allquestions.size()){
                    getNextQuestion();
                    getNextAnswers();
                }
                else{
                    if(posQuestion == 0){
                        //gab keine Fragen und keinen Grund zu speichern
                    }
                    else {
                        answer.setIdsFragen(idsQuestion);
                        answer.setIdsAntworten(idsAnswer);
                        Toast.makeText(AnswerSurveyActivity.this, "Finished", Toast.LENGTH_SHORT).show();
                        //TODO Speichern, UserId hinzufügen?
                        onBackPressed();
                    }
                }

            }
        });
    }

    public void getNextQuestion(){
        currentQuestion = Allquestions.get(posQuestion);
        posQuestion ++;

        questionsLeft.setText(Integer.toString(Allquestions.size() - posQuestion + 1));
        questionName.setText(currentQuestion.getText());
    }

    public void getNextAnswers(){
        answerOptions.clear();

        for(int i = 0; i < currentQuestion.getAnswerOptions().size(); i++)
            answerOptions.add(currentQuestion.getAnswerOptions().get(i).getText());
        if(answerOptions.size() == 0){
            //tue nichts
        }

        //Adapter aufsetzen und Liste mit Material füllen
        adapter = new ArrayAdapter<>(AnswerSurveyActivity.this,
                android.R.layout.simple_list_item_1, android.R.id.text1, answerOptions);
        answerListView.setAdapter(adapter);
    }
}