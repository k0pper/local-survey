package com.example.localsurveys.localsurveys.models;

class User {
//Nickname, Pro_User, Vorname, Nachname, E-Mail-Adresse
    private String id;
    private boolean isProUser;
    private String firstName;
    private String lastName;
    private String eMail;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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
