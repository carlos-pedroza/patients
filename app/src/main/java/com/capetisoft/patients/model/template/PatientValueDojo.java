package com.capetisoft.patients.model.template;

import android.database.Cursor;

/**
 * Created by carlospedroza on 27/05/16.
 */
public class PatientValueDojo {
    int id;
    int identity;
    String device;
    String key;
    int id_templateData;
    String value;
    long serverSync;

    public PatientValueDojo() {

    }

    public PatientValueDojo(Cursor cursor) {
        this.id = cursor.getInt(0);
        this.identity = cursor.getInt(1);
        this.device = cursor.getString(2);
        this.key = cursor.getString(3);
        this.id_templateData = cursor.getInt(4);
        this.value = cursor.getString(5);
        this.serverSync = cursor.getLong(6);
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

    public int getId_templateData() {
        return id_templateData;
    }

    public void setId_templateData(int id_templateData) {
        this.id_templateData = id_templateData;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public long getServerSync() {
        return serverSync;
    }

    public void setServerSync(long serverSync) {
        this.serverSync = serverSync;
    }
}
