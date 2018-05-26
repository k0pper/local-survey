package com.example.localsurveys.localsurveys.models;

import java.util.List;

class Question {

    private String id;
    private String text;
    private List<AnswerOption> answerOptions;

    public String getId() {
        return id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public List<AnswerOption> getAnswerOptions() {
        return answerOptions;
    }

    public void setAnswerOptions(List<AnswerOption> answerOptions) {
        this.answerOptions = answerOptions;
    }


}
