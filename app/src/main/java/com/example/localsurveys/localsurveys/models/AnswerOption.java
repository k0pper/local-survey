package com.example.localsurveys.localsurveys.models;

import java.util.UUID;

public class AnswerOption {

    private String id;
    private String text;

    public AnswerOption(String text) {
        this.id = UUID.randomUUID().toString();
        this.text = text;
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
}
