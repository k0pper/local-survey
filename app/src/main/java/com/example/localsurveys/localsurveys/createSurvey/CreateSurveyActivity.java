package com.example.localsurveys.localsurveys.createSurvey;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.TextView;

import com.example.localsurveys.localsurveys.R;
import com.example.localsurveys.localsurveys.models.Survey;

public class CreateSurveyActivity extends AppCompatActivity {
    Survey survey = new Survey();

    Toolbar toolbar;
    SeekBar seekBar;
    EditText title;
    TextView radius;
    RadioGroup radioGroup;
    RadioButton fiveMinutes, thirtyMinutes, oneHour;
    FloatingActionButton fab;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_survey);
        inflateToolbar();
        initializeUI();
    }

    private void inflateToolbar() {
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private void initializeUI() {
        title = findViewById(R.id.title_txt);
        seekBar = findViewById(R.id.seekBar);
        radius = findViewById(R.id.radius_txt);
        radioGroup = findViewById(R.id.radioGroup);
        fiveMinutes = findViewById(R.id.fiveMinutes);
        thirtyMinutes = findViewById(R.id.thirtyMinutes);
        oneHour = findViewById(R.id.oneHour);
        fab = findViewById(R.id.fab);

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                radius.setText(progress + " meters");
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                survey.setTitle(title.getText().toString())
                        .setRadius(seekBar.getProgress());

                if (fiveMinutes.isChecked()) {
                    survey.setDuration(5 * 60000);
                } else if (thirtyMinutes.isChecked()) {
                    survey.setDuration(30 * 60000);
                } else if (oneHour.isChecked()) {
                    survey.setDuration(60 * 60000);
                }
                Bundle bundle = new Bundle();
                Intent intent = new Intent(CreateSurveyActivity.this, AddQuestionActivity.class);
                bundle.putSerializable("SURVEY", survey);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == android.R.id.home) {
            onBackPressed();
        }

        return super.onOptionsItemSelected(item);
    }

}
