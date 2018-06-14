package com.example.localsurveys.localsurveys.firebase;

import android.util.Log;

import com.example.localsurveys.localsurveys.models.Survey;
import com.example.localsurveys.localsurveys.models.User;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseException;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class FirebaseHelper {

    DatabaseReference db;
    Boolean saved = null;
    ArrayList<Survey> surveys = new ArrayList<>();

    public FirebaseHelper(DatabaseReference db) {
        this.db = db;
    }

    public Boolean saveSurveyTest(Survey survey) {
        if (survey == null) {
            saved = false;
        } else {
            try {
                db.push().setValue(survey);
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
        } else {
            db.child("User").push().setValue(user);
            return true;
        }
    }

    //READ BY HOOKING ONTO DATABASE OPERATION CALLBACKS
    public ArrayList<Survey> retrieve() {
        db.addValueEventListener(new ValueEventListener() {
            public void onDataChange(DataSnapshot dataSnapshot) {
                Log.d("TEST", "Data changed. Getting dataSnapshot");
                fetchData(dataSnapshot);
                Log.d("TEST", "Got dataSnapshot : " + dataSnapshot);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.w("Failed to read values.", databaseError.toException());
            }
        });

        return surveys;
    }

    private void fetchData(DataSnapshot dataSnapshot) {
        surveys.clear();
        for (DataSnapshot ds : dataSnapshot.getChildren()) {
            Survey s = ds.getValue(Survey.class);
            surveys.add(s);
        }
    }
}