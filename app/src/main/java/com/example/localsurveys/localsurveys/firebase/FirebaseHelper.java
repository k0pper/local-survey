package com.example.localsurveys.localsurveys.firebase;

import android.util.Log;

import com.example.localsurveys.localsurveys.models.Survey;
import com.example.localsurveys.localsurveys.models.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseException;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class FirebaseHelper {

    FirebaseAuth auth;
    DatabaseReference db;
    Boolean saved = null;
    ArrayList<Survey> surveys = new ArrayList<>();

    public FirebaseHelper(DatabaseReference db) {
        this.auth = FirebaseAuth.getInstance();
        this.db = db;
    }

    public Boolean saveSurvey(Survey survey, String email) {
        if (survey == null || email == null) {
            saved = false;
        } else {
            try {
                db.child(this.getFormattedMail(email)).child("surveys").push().setValue(survey);
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
            String childName = getFormattedMail(user.geteMail());
            db.child("User").child(childName.toLowerCase()).setValue(user);
            return true;
        }
    }

    public String getFormattedMail(String email) {
        String[] userSplitted = email.split("\\.");
        StringBuilder childName = new StringBuilder();

        for (int i = 0; i < userSplitted.length; ++i) {
            if (i == userSplitted.length - 1) {
                childName.append(userSplitted[i]);
            } else {
                childName.append(userSplitted[i]).append("DOT");
            }
        }
        return childName.toString().toLowerCase();
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
        for (DataSnapshot ds : dataSnapshot.child(getFormattedMail(auth.getCurrentUser().getEmail())).child("surveys").getChildren()) {
            Survey s = ds.getValue(Survey.class);
            surveys.add(s);
            Log.d("TEST", "Key: " + ds.getKey());
        }
    }

    public ArrayList<Survey> retrieveVisibleSurveys(final double longitude, final double latitude) {
        db.addValueEventListener(new ValueEventListener() {
            public void onDataChange(DataSnapshot dataSnapshot) {
                double lng = longitude;
                double lat = latitude;
                Log.d("TEST", "Fetching visibles with " + lng + " " + lat);
                fetchVisibles(dataSnapshot, lng, lat);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        return surveys;
    }

    private void fetchVisibles(DataSnapshot dataSnapshot, double lng, double lat) {
        surveys.clear();
        for (DataSnapshot userSnapshot : dataSnapshot.child("User").getChildren()) {
            DataSnapshot surveysOfUserSnapshot = userSnapshot.child("surveys");
            for (DataSnapshot surveySnapshot : surveysOfUserSnapshot.getChildren()) {
                Survey iterateSurvey = surveySnapshot.getValue(Survey.class);
                if (iterateSurvey.isVisibleFrom(lng, lat)) {
                    surveys.add(iterateSurvey);
                }
            }
        }
    }
}