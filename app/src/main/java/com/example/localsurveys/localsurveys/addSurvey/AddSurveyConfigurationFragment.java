package com.example.localsurveys.localsurveys.addSurvey;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.SeekBar;

import com.example.localsurveys.localsurveys.R;

public class AddSurveyConfigurationFragment extends Fragment {

    protected EditText titleEditTextView;
    protected SeekBar radiusView;
    protected EditText availabilityFromDateView;
    protected EditText availabilityToDateView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(false);

        View rootView = inflater.inflate(R.layout.fragment_add_survey_configuration, container, false);

        this.titleEditTextView = rootView.findViewById(R.id.add_survey__title_edit_text);
        this.radiusView = rootView.findViewById(R.id.add_survey__radius_bar);
        this.availabilityFromDateView = rootView.findViewById(R.id.add_survey__availability_from_date);
        this.availabilityToDateView = rootView.findViewById(R.id.add_survey__availability_to_date);

        return rootView;
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
    }
}
