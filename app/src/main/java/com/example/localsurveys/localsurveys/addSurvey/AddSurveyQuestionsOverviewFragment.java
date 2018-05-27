package com.example.localsurveys.localsurveys.addSurvey;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.localsurveys.localsurveys.R;
import com.example.localsurveys.localsurveys.models.Survey;

import java.util.Arrays;
import java.util.List;

public class AddSurveyQuestionsOverviewFragment extends Fragment {

    private FloatingActionButton addSurveyQuestionsOverviewFab;
    private FloatingActionButton addSurveyQuestionsOverviewFabStartSurvey;
    private static String[] placeholderList = {"No Question added yet"};

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_add_survey_questions_overview, container, false);
        ListView listView;

        addSurveyQuestionsOverviewFab = rootView.findViewById(R.id.add_survey_questions_overview__fab);
        addSurveyQuestionsOverviewFabStartSurvey = rootView.findViewById(R.id.add_survey_questions_overview__fab_start_survey);

        Survey survey = ((AddSurveyActivity) getActivity()).getSurvey();

        listView = rootView.findViewById(R.id.list);

        List<String> list;

        if (!survey.getListOfQuestionStrings().isEmpty()) {
            list = survey.getListOfQuestionStrings();
            addSurveyQuestionsOverviewFabStartSurvey.setVisibility(View.VISIBLE);
        } else {
            list = Arrays.asList(placeholderList);
        }
        ArrayAdapter adapter = new ArrayAdapter<>(getContext(),
                android.R.layout.simple_list_item_1,
                list);

        listView.setAdapter(adapter);

        addSurveyQuestionsOverviewFab.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                ((AddSurveyActivity) getActivity()).showFragment(new AddSurveyQuestionsFragment());
            }
        });

        addSurveyQuestionsOverviewFabStartSurvey.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                ((AddSurveyActivity) getActivity()).onSurveyComplided();
            }
        });

        return rootView;
    }

}
