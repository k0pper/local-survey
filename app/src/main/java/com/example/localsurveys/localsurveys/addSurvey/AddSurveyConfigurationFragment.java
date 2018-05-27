package com.example.localsurveys.localsurveys.addSurvey;

import android.icu.text.SimpleDateFormat;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.example.localsurveys.localsurveys.R;

import java.text.ParseException;

public class AddSurveyConfigurationFragment extends Fragment {

    private EditText titleEditTextView;
    private EditText radiusView;
    private EditText availabilityFromDateView;
    private EditText availabilityToDateView;

    FloatingActionButton addSurveyConfigurationFab;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(false);

        View rootView = inflater.inflate(R.layout.fragment_add_survey_configuration, container, false);

        addSurveyConfigurationFab = rootView.findViewById(R.id.add_survey_configuration_fab);

        addSurveyConfigurationFab.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                long fromDate = parseStringToDate(availabilityFromDateView.getText().toString());
                long toDate = parseStringToDate(availabilityToDateView.getText().toString());

                if(fromDate == -1){
                    //TODO: show error: String Format
                    return;
                }
                if(toDate == -1){
                    //TODO: show error: String Format
                    return;
                }

                ((AddSurveyActivity)getActivity()).createSurveyWithConfiguration(titleEditTextView.getText().toString(), radiusView.getText().toString(), fromDate, toDate);
                ((AddSurveyActivity)getActivity()).showFragment(new AddSurveyQuestionsOverviewFragment());
            }
        });

        this.titleEditTextView = rootView.findViewById(R.id.add_survey_configuration__title_edit_text);
        this.radiusView = rootView.findViewById(R.id.add_survey_configuration__radius_text);
        this.availabilityFromDateView = rootView.findViewById(R.id.add_survey_configuration__availability_from_date);
        this.availabilityToDateView = rootView.findViewById(R.id.add_survey_configuration__availability_to_date);

        return rootView;
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
    }

    private long parseStringToDate(String dateString) {
        long date = 0;
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy hh:mm a");
            date = sdf.parse(dateString).getTime();

        } catch (ParseException e) {
            e.printStackTrace();
            return -1;
        }
        return date;
    }
}
