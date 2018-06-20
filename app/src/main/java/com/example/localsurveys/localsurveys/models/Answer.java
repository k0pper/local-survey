package com.example.localsurveys.localsurveys.models;

import java.io.Serializable;
import java.util.ArrayList;

public class Answer implements Serializable{
    public String idUmfrage;
    public ArrayList<String> idsFragen;
    public ArrayList<String> idsAntworten;

    public Answer(){
        idsFragen = new ArrayList<>();
        idsAntworten = new ArrayList<>();
    }

    public Answer(String uId){
        idUmfrage = uId;
        idsFragen = new ArrayList<>();
        idsAntworten = new ArrayList<>();
    }

    public Answer(String uId, ArrayList<String> fId, ArrayList<String> aID){
        if (fId.size() != aID.size())
        {
            //Fehlermeldung ? Umfrage kann/ sollte nicht gespeichert werden
            //Fehler im Code
        }
        else
        {
            idUmfrage = uId;
            idsFragen = new ArrayList<>();
            idsAntworten = new ArrayList<>();
        }
    }

    public String getIdUmfrage() {
        return idUmfrage;
    }

    public void setIdUmfrage(String idUmfrage) {
        this.idUmfrage = idUmfrage;
    }

    public ArrayList<String> getIdsFragen() {
        return idsFragen;
    }

    public void setIdsFragen(ArrayList<String> idsFragen) {
        this.idsFragen = idsFragen;
    }

    public ArrayList<String> getIdsAntworten() {
        return idsAntworten;
    }

    public void setIdsAntworten(ArrayList<String> idsAntworten) {
        this.idsAntworten = idsAntworten;
    }

}
