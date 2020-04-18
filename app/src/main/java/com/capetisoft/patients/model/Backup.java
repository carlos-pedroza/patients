package com.capetisoft.patients.model;

import com.capetisoft.patients.model.template.PatientValueDojo;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by carlospedroza on 26/05/16.
 */
public class Backup {
    public Backup(long backupTime) {
        this.backupTime = new BackupTime(backupTime);
    }

    public BackupTime getBackupTime() {
        return backupTime;
    }

    public void setBackupTime(BackupTime backupTime) {
        this.backupTime = backupTime;
    }

    private BackupTime backupTime;

    public List<PatientDojo> getPatients() {
        return patients;
    }

    public void setPatients(List<PatientDojo> patients) {
        this.patients = patients;
    }

    private List<PatientDojo> patients;

    public List<PreferencesDojo> getPreferences() {
        return preferences;
    }

    public void setPreferences(PreferencesDojo preferences) {
        ArrayList<PreferencesDojo> preferencesDojos = new ArrayList<>();
        preferencesDojos.add(preferences);
        this.preferences = preferencesDojos;
    }

    private List<PreferencesDojo> preferences;
    private List<PersonDojo> person;
    private List<VisitDojo> visits;
    private List<PatientValueDojo> patientValue;

    public List<PersonDojo> getPerson() {
        return person;
    }

    public void setPerson(PersonDojo person) {
        ArrayList<PersonDojo> personDojos = new ArrayList<>();
        personDojos.add(person);
        this.person = personDojos;
    }

    public List<VisitDojo> getVisits() {
        return visits;
    }

    public void setVisits(List<VisitDojo> visits) {
        this.visits = visits;
    }

    public List<PatientValueDojo> getPatientValue() {
        return patientValue;
    }

    public void setPatientValue(List<PatientValueDojo> patientValue) {
        this.patientValue = patientValue;
    }
}
