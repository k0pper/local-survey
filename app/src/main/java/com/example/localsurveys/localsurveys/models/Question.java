package com.example.localsurveys.localsurveys.models;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Question implements Serializable {
    private static final long serialVersionUID = 1L;

    private String id;
    private String text;
    private List<AnswerOption> answerOptions;

    public Question() {

    }

    public Question(String text) {
        this.id = UUID.randomUUID().toString();
        this.text = text;
        this.answerOptions = new ArrayList<>();
    }

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

    public void addAnswerOption(AnswerOption option) {
        answerOptions.add(option);
    }
}
