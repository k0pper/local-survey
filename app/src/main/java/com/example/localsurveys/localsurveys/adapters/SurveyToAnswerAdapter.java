package com.example.localsurveys.localsurveys.adapters;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.localsurveys.localsurveys.R;
import com.example.localsurveys.localsurveys.answerSurvey.AnswerSurveyActivity;
import com.example.localsurveys.localsurveys.models.Survey;

import java.util.ArrayList;

public class SurveyToAnswerAdapter extends CustomAdapter {

    public SurveyToAnswerAdapter(Context c, ArrayList<Survey> surveys) {
        this.c = c;
        this.surveys = surveys;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(c).inflate(R.layout.survey_item, parent, false);
        }

        final Survey survey = (Survey) this.getItem(position);

        TextView titleTxt = (TextView) convertView.findViewById(R.id.survey_name);
        TextView questionsTxt = (TextView) convertView.findViewById(R.id.survey_questions);

        titleTxt.setText(survey.getTitle());
        questionsTxt.setText(survey.getLength() + " Questions");

        if (survey.isExpired()) {
            titleTxt.append(" (expired)");
            titleTxt.setTextColor(Color.GRAY);
        } else {
            titleTxt.setTextColor(Color.GREEN);
        }

        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showAnswerSurveyActivity(survey);
            }
        });

        return convertView;
    }

    //statt SurveyDeteilActivity soll Umfrage Beantworten Activity gestartet werden
    private void showAnswerSurveyActivity(Survey s) {
        Bundle b = new Bundle();
        Intent i = new Intent (c, AnswerSurveyActivity.class);
        b.putSerializable("SURVEY", s);
        i.putExtras(b);
        c.startActivity(i);
    }
}
