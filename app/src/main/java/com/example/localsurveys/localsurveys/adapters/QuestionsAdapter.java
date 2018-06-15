package com.example.localsurveys.localsurveys.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.localsurveys.localsurveys.R;
import com.example.localsurveys.localsurveys.models.AnswerOption;
import com.example.localsurveys.localsurveys.models.Question;
import com.example.localsurveys.localsurveys.models.Survey;

import java.util.ArrayList;

/**
 * Created by AlexanderMiller, on 15.06.2018.
 */

public class QuestionsAdapter extends BaseAdapter {
    Context c;
    ArrayList<Question> questions;

    TextView titleTxt, questionsTxt;

    public QuestionsAdapter(Context c, ArrayList<Question> questions) {
        this.c = c;
        this.questions = questions;
    }

    @Override
    public int getCount() {
        return questions.size();
    }

    @Override
    public Object getItem(int position) {
        return questions.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(c).inflate(R.layout.question_item, parent, false);
        }

        final Question q = (Question) this.getItem(position);

        titleTxt = (TextView) convertView.findViewById(R.id.question_name);
        questionsTxt = (TextView) convertView.findViewById(R.id.answer_options);

        titleTxt.setText(q.getText());
        questionsTxt.setText("");
        for (AnswerOption option : q.getAnswerOptions()) {
            questionsTxt.append(option.getText() + " ");
        }

        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        return convertView;
    }
}
