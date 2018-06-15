package com.example.localsurveys.localsurveys.models;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;

public class Survey implements Serializable {
    //Umfrage ( ID, User_FK, Titel, Radius, Passwortschutz, Von, Bis)
    private static final long serialVersionUID = 1L;

    private String id;
    private String title;
    private int radius;
    private long fromDate;
    private long toDate;
    private long duration;
    private User user;
    private ArrayList<Question> questions;

    public Survey() {
        this.id = UUID.randomUUID().toString();
        this.questions = new ArrayList<>();
    }

    @Override
    public String toString() {
        String s = "Survey{ title: " + this.getTitle() + ", length: " + this.getLength() + " }";
        return s;
    }

    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public Survey setTitle(String title) {
        this.title = title;
        return this;
    }

    public int getRadius() {
        return radius;
    }

    public Survey setRadius(int radius) {
        this.radius = radius;
        return this;
    }

    public User getUser() {
        return user;
    }

    public Survey setUser(User user) {
        this.user = user;
        return this;
    }

    public long getFromDate() {
        return fromDate;
    }

    public Survey setFromDate(long fromDate) {
        this.fromDate = fromDate;
        return this;
    }

    public long getToDate() {
        return toDate;
    }

    public Survey setToDate(long toDate) {
        this.toDate = toDate;
        return this;
    }

    public String getLength() {
        return questions.size() + "";
    }

    public void addQuestion(Question question) {
        this.questions.add(question);
    }

    public ArrayList<Question> getQuestions() {
        return questions;
    }


    public long getDuration() {
        return duration;
    }

    public Survey setDuration(long duration) {
        this.duration = duration;
        return this;
    }

    public static Survey random() {
        Random r = new Random();
        char c = (char) (r.nextInt(26) + 'a');

        Survey s = new Survey();
        s.setTitle(c + "(title)")
                .setFromDate(r.nextLong())
                .setToDate(r.nextLong())
                .setUser(new User("testuser@gmx.de"))
                .setRadius(r.nextInt());

        return s;
    }

}
