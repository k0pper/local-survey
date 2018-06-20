package com.example.localsurveys.localsurveys.adapters;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.localsurveys.localsurveys.R;
import com.example.localsurveys.localsurveys.answerSurvey.AnswerSurveyActivity;
import com.example.localsurveys.localsurveys.models.AnswerOption;
import com.example.localsurveys.localsurveys.models.Question;
import com.example.localsurveys.localsurveys.models.Survey;

import java.util.ArrayList;

/**
 * Created by AlexanderMiller, on 19.06.2018.
 */

public class LiveSurveysAdapter extends BaseAdapter{
    ArrayList<Survey> surveys;
    Context c;
    TextView titleTxt, questionsTxt, creatorTxt, publishedTxt;

    public LiveSurveysAdapter(){}

    public LiveSurveysAdapter(Context c, ArrayList<Survey> surveys) {
        this.c = c;
        this.surveys = surveys;
    }

    @Override
    public int getCount() {
        return surveys.size();
    }

    @Override
    public Object getItem(int position) {
        return surveys.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(c).inflate(R.layout.participate_survey_model, parent, false);
        }

        final Survey s = (Survey) this.getItem(position);
        titleTxt = convertView.findViewById(R.id.title_txt);
        questionsTxt = convertView.findViewById(R.id.question_txt);
        creatorTxt = convertView.findViewById(R.id.creatorTxt);
        publishedTxt = convertView.findViewById(R.id.publishTxt);

        titleTxt.setText(s.getTitle());
        questionsTxt.setText("Questions: " + s.getQuestions().size());
        creatorTxt.setText("Testemail");

        // Calculate Time since Publishing
        long now = System.currentTimeMillis();
        long surveyStart = s.getFromDate();
        long difference = now - surveyStart;
        String unit;
        int result;
        if (difference < 60000) {
            unit = "Seconds";
            result = (int) difference / 1000;
        } else {
            unit = "Minutes";
            result = (int) difference / 60000;
        }
        publishedTxt.setText("Published " + result + " " + unit + " ago");
        creatorTxt.setText("Testemail");


        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Log.d("TEST", "Clicked ListItem");
                showAnswerSurveyActivity(s);
            }
        });

        return convertView;
    }

    private void showAnswerSurveyActivity(Survey s) {
        //Bundle b = new Bundle();
        Intent i = new Intent (c, AnswerSurveyActivity.class);
        //b.putSerializable("SURVEY", s);
        //i.putExtras(b);
        i.putExtra("SURVEY",s);
        c.startActivity(i);
    }
}
