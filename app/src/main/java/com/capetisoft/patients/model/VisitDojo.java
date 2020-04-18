package com.capetisoft.patients.model;

import android.database.Cursor;

/**
 * Created by carlospedroza on 27/05/16.
 */
public class VisitDojo {
    int id;
    int identity;
    String device;
    String key;
    int identityPatient;
    long dateVisit;
    String diagnostic;
    String drugs;
    String comments;
    long lastupdate;
    long createdate;
    long serverSync;
    int deleted;

    public VisitDojo() {

    }

    public VisitDojo(Cursor cursor) {
        this.id = cursor.getInt(0);
        this.identity = cursor.getInt(1);
        this.device = cursor.getString(2);
        this.key = cursor.getString(3);
        this.identityPatient = cursor.getInt(4);
        this.dateVisit = cursor.getLong(5);
        this.diagnostic = cursor.getString(6);
        this.drugs = cursor.getString(7);
        this.comments = cursor.getString(8);
        this.lastupdate = cursor.getLong(9);
        this.createdate = cursor.getLong(10);
        this.serverSync = cursor.getLong(11);
        this.deleted = cursor.getInt(12);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdentity() {
        return identity;
    }

    public void setIdentity(int identity) {
        this.identity = identity;
    }

    public String getDevice() {
        return device;
    }

    public void setDevice(String device) {
        this.device = device;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public int getIdentityPatient() {
        return identityPatient;
    }

    public void setIdentityPatient(int identityPatient) {
        this.identityPatient = identityPatient;
    }

    public long getDateVisit() {
        return dateVisit;
    }

    public void setDateVisit(long dateVisit) {
        this.dateVisit = dateVisit;
    }

    public String getDiagnostic() {
        return diagnostic;
    }

    public void setDiagnostic(String diagnostic) {
        this.diagnostic = diagnostic;
    }

    public String getDrugs() {
        return drugs;
    }

    public void setDrugs(String drugs) {
        this.drugs = drugs;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public long getLastupdate() {
        return lastupdate;
    }

    public void setLastupdate(long lastupdate) {
        this.lastupdate = lastupdate;
    }

    public long getCreatedate() {
        return createdate;
    }

    public void setCreatedate(long createdate) {
        this.createdate = createdate;
    }

    public long getServerSync() {
        return serverSync;
    }

    public void setServerSync(long serverSync) {
        this.serverSync = serverSync;
    }

    public int getDeleted() {
        return deleted;
    }

    public void setDeleted(int deleted) {
        this.deleted = deleted;
    }
}
