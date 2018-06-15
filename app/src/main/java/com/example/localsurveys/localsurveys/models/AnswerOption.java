package com.example.localsurveys.localsurveys.models;

import java.io.Serializable;
import java.util.UUID;

public class AnswerOption implements Serializable {
    private static final long serialVersionUID = 1L;

    private String id;
    private String text;

    public AnswerOption() {

    }

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
