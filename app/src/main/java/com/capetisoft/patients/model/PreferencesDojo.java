package com.capetisoft.patients.model;

import android.app.Activity;
import android.content.SharedPreferences;

import com.capetisoft.patients.util.Utils;

/**
 * Created by carlospedroza on 27/05/16.
 */
public class PreferencesDojo {
    String currentPatientQueryType;
    String currentIdTemplate;
    String email;
    String currentPatientSearchQuery;
    String currentKey;
    String person_key;
    String currentIdPatient;

    public PreferencesDojo() {

    }

    public PreferencesDojo(Activity context) {
        SharedPreferences preferences = context.getSharedPreferences("PatientPreferences", context.MODE_PRIVATE);

        this.currentPatientQueryType = preferences.getString(Utils.PREFERENCES_CURRENT_PATIENT_QUERY_TYPE,"");
        this.currentIdTemplate = preferences.getString(Utils.PREFERENCES_CURRENT_ID_TEMPLATE,"");
        this.email = preferences.getString(Utils.PREFERENCES_EMAIL,"");
        this.currentPatientSearchQuery = preferences.getString(Utils.PREFERENCES_PATIENT_SEARCH_QUERY,"");
        this.currentKey = preferences.getString(Utils.PREFERENCES_CURRENT_KEY,"");
        this.person_key = preferences.getString(Utils.PREFERENCES_KEY,"");
        this.currentIdPatient = preferences.getString(Utils.PREFERENCES_CURRENT_ID_TEMPLATE,"");
        this.password = preferences.getString(Utils.PREFERENCES_PASSWORD,"");
    }

    String password;

    public PreferencesDojo(String currentPatientQueryType, String currentIdTemplate, String email, String currentPatientSearchQuery, String currentKey, String person_key, String currentIdPatient, String password) {
        this.currentPatientQueryType = currentPatientQueryType;
        this.currentIdTemplate = currentIdTemplate;
        this.email = email;
        this.currentPatientSearchQuery = currentPatientSearchQuery;
        this.currentKey = currentKey;
        this.person_key = person_key;
        this.currentIdPatient = currentIdPatient;
        this.password = password;
    }

    public String getCurrentPatientQueryType() {
        return currentPatientQueryType;
    }

    public void setCurrentPatientQueryType(String currentPatientQueryType) {
        this.currentPatientQueryType = currentPatientQueryType;
    }

    public String getCurrentIdTemplate() {
        return currentIdTemplate;
    }

    public void setCurrentIdTemplate(String currentIdTemplate) {
        this.currentIdTemplate = currentIdTemplate;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCurrentPatientSearchQuery() {
        return currentPatientSearchQuery;
    }

    public void setCurrentPatientSearchQuery(String currentPatientSearchQuery) {
        this.currentPatientSearchQuery = currentPatientSearchQuery;
    }

    public String getCurrentKey() {
        return currentKey;
    }

    public void setCurrentKey(String currentKey) {
        this.currentKey = currentKey;
    }

    public String getPerson_key() {
        return person_key;
    }

    public void setPerson_key(String person_key) {
        this.person_key = person_key;
    }

    public String getCurrentIdPatient() {
        return currentIdPatient;
    }

    public void setCurrentIdPatient(String currentIdPatient) {
        this.currentIdPatient = currentIdPatient;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
