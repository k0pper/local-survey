package com.example.localsurveys.localsurveys.models;

import java.io.Serializable;
import java.util.UUID;

public class User implements Serializable {
    //Nickname, Pro_User, Vorname, Nachname, E-Mail-Adresse
    private static final long serialVersionUID = 1L;

    private String id;
    private boolean isProUser;
    private String firstName;
    private String lastName;
    private String eMail;

    public User() {

    }

    public User(String email) {
        this.id = UUID.randomUUID().toString();
        this.eMail = email;
        this.isProUser = false;
    }

    public String getId() {
        return id;
    }

    public boolean isProUser() {
        return isProUser;
    }

    public void setProUser(boolean proUser) {
        isProUser = proUser;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String geteMail() {
        return eMail;
    }

    public void seteMail(String eMail) {
        this.eMail = eMail;
    }
}
