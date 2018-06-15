package com.example.localsurveys.localsurveys.adapters;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.localsurveys.localsurveys.R;
import com.example.localsurveys.localsurveys.home.HomeActivity;
import com.example.localsurveys.localsurveys.models.Survey;
import com.example.localsurveys.localsurveys.survey.SurveyDetailActivity;

import java.util.ArrayList;

/**
 * Created by alexandermiller on 08.06.18.
 */

public class CustomAdapter extends BaseAdapter {
    Context c;
    ArrayList<Survey> surveys;

    public CustomAdapter(Context c, ArrayList<Survey> surveys) {
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
                showDetailActivity(survey);
            }
        });

        return convertView;
    }

    private void showDetailActivity(Survey s) {
        Bundle b = new Bundle();
        Intent i = new Intent (c, SurveyDetailActivity.class);
        b.putSerializable("SURVEY", s);
        i.putExtras(b);
        c.startActivity(i);
    }
}
