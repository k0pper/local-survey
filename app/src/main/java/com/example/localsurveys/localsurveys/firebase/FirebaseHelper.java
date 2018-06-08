package com.example.localsurveys.localsurveys.firebase;

import com.example.localsurveys.localsurveys.models.Survey;
import com.example.localsurveys.localsurveys.models.User;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseException;
import com.google.firebase.database.DatabaseReference;

import java.util.ArrayList;

public class FirebaseHelper {

    DatabaseReference db;
    Boolean saved = null;
    ArrayList<Survey> surveys = new ArrayList<>();

    public FirebaseHelper(DatabaseReference db) {
        this.db = db;
    }

    //WRITE IF NOT NULL
    public Boolean saveSurvey(Survey survey, User user) {
        if (survey == null || user == null) {
            saved = false;
        } else {
            try {
                db.child("Survey").child(user.getId()).push().setValue(survey);
                saved = true;
            } catch (DatabaseException e) {
                e.printStackTrace();
                saved = false;
            }
        }

        return saved;
    }

    public Boolean saveSurveyTest(Survey survey) {
        if (survey == null) {
            saved = false;
        } else {
            try {
                db.child("Survey").push().setValue(survey);
                saved = true;
            } catch (DatabaseException e) {
                e.printStackTrace();
                saved = false;
            }
        }

        return saved;
    }

    public Boolean saveUser(User user) {
        if (user == null) {
            return false;
        }
        else {
            db.child("User").push().setValue(user);
            return true;
        }
    }

    //IMPLEMENT FETCH DATA AND FILL ARRAYLIST
    private void fetchData(DataSnapshot dataSnapshot) {
        surveys.clear();

        for (DataSnapshot ds : dataSnapshot.getChildren()) {
            Survey survey = ds.getValue(Survey.class);
            surveys.add(survey);
        }
    }

    //READ BY HOOKING ONTO DATABASE OPERATION CALLBACKS
    public ArrayList<Survey> retrieve() {
        db.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                fetchData(dataSnapshot);
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                fetchData(dataSnapshot);

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        return surveys;
    }
}