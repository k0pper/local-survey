package com.example.localsurveys.localsurveys.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.localsurveys.localsurveys.R;
import com.example.localsurveys.localsurveys.models.Survey;

import java.util.ArrayList;

/**
 * Created by alexandermiller on 08.06.18.
 */

public class CustomAdapter extends ArrayAdapter<Survey> {


    public CustomAdapter(@NonNull Context context, ArrayList<Survey> surveys) {
        super(context, R.layout.survey_item, surveys);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(getContext());
        View customView = inflater.inflate(R.layout.survey_item, parent, false);

        Survey singleSurvey = getItem(position);

        TextView surveyName = (TextView) customView.findViewById(R.id.survey_name);
        TextView surveyQuestions = (TextView) customView.findViewById(R.id.survey_questions);

        surveyName.setText(singleSurvey.getId());
        // surveyQuestions.setText(singleSurvey.getListOfQuestionStrings().size());
        return customView;

    }
}
