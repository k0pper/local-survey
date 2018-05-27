package com.example.localsurveys.localsurveys.addSurvey;


import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.example.localsurveys.localsurveys.R;
import com.example.localsurveys.localsurveys.models.Question;

public class AddSurveyQuestionsFragment extends Fragment {

    private EditText titleQuestionView;
    private EditText optionOneView;
    private EditText optionTwoView;
    private EditText optionThreeView;
    private EditText optionFourView;

    private FloatingActionButton addSurveyQuestionsFabDone;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_add_survey_questions, container, false);

        this.titleQuestionView = rootView.findViewById(R.id.add_survey_questions__title);
        this.optionOneView = rootView.findViewById(R.id.add_survey_questions__option_1);
        this.optionTwoView = rootView.findViewById(R.id.add_survey_questions__option_2);
        this.optionThreeView = rootView.findViewById(R.id.add_survey_questions__option_3);
        this.optionFourView = rootView.findViewById(R.id.add_survey_questions__option_4);

        addSurveyQuestionsFabDone = rootView.findViewById(R.id.add_survey_questions__fab_done);

        addSurveyQuestionsFabDone.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                if (!titleQuestionView.getText().toString().isEmpty()) {
                    Question question;
                    question = new Question(titleQuestionView.getText().toString());

                    if (!optionOneView.getText().toString().isEmpty()) {
                        question.addAnswerOption(optionOneView.getText().toString());
                    }
                    if (!optionTwoView.getText().toString().isEmpty()) {
                        question.addAnswerOption(optionTwoView.getText().toString());
                    }
                    if (!optionThreeView.getText().toString().isEmpty()) {
                        question.addAnswerOption(optionThreeView.getText().toString());
                    }
                    if (!optionFourView.getText().toString().isEmpty()) {
                        question.addAnswerOption(optionFourView.getText().toString());
                    }
                    ((AddSurveyActivity) getActivity()).addQuestionToSurvey(question);
                    ((AddSurveyActivity) getActivity()).showFragment(new AddSurveyQuestionsOverviewFragment());
                }
            }
        });

        return rootView;
    }

}
