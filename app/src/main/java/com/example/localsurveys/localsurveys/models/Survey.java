package com.example.localsurveys.localsurveys.models;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Survey {
//Umfrage ( ID, User_FK, Titel, Radius, Passwortschutz, Von, Bis)

    private String id;
    private String title;
    private int radius;
    private User user;
    private long fromDate;
    private long toDate;
    private List<Question> questions;

    public Survey(String title, int radius, User user, long fromDate, long toDate) {
        this.title = title;
        this.radius = radius;
        this.user = user;
        this.fromDate = fromDate;
        this.toDate = toDate;

        this.id = UUID.randomUUID().toString();
        questions = new ArrayList<>();
    }

    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getRadius() {
        return radius;
    }

    public void setRadius(int radius) {
        this.radius = radius;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public long getFromDate() {
        return fromDate;
    }

    public void setFromDate(long fromDate) {
        this.fromDate = fromDate;
    }

    public long getToDate() {
        return toDate;
    }

    public void setToDate(long toDate) {
        this.toDate = toDate;
    }

    public void addQuestion(Question question) {
        this.questions.add(question);
    }
}
