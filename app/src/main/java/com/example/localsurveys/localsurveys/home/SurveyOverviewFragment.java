package com.example.localsurveys.localsurveys.home;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.localsurveys.localsurveys.R;
import com.example.localsurveys.localsurveys.addSurvey.AddSurveyActivity;

public class SurveyOverviewFragment extends Fragment {

    FloatingActionButton addSurveyFab;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_survey_overview, container, false);

        addSurveyFab = rootView.findViewById(R.id.survey_overview__add_survey_fab);

        addSurveyFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SurveyOverviewFragment.this.getActivity().startActivity(new Intent(SurveyOverviewFragment.this.getActivity(), AddSurveyActivity.class));
            }
        });

        return rootView;
    }
}
