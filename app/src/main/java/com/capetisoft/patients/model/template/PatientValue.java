package com.capetisoft.patients.model.template;

import android.content.Context;
import android.database.Cursor;

import com.capetisoft.patients.dal.DataAccessLogic;
import com.capetisoft.patients.services.io.model.ResultServicePatientValue;

import java.util.ArrayList;

/**
 * Created by carlospedroza on 12/11/15.
 */
public class PatientValue {
    private Context context;
    private DataAccessLogic dal;
    private int id;
    private TemplateData templateData;

    private int identity;
    private String device;
    private String key;
    private int id_templateData;
    private String value;
    private long serverSync;

    public PatientValue(Context context, String tag) {
        this.setContext(context);
        this.setTag(tag);
        this.setDal(new DataAccessLogic(context));
    }
    public void setStatusSync(long serverSync) {

        String query="";

        query+="UPDATE patientValue";
        query+="   SET serverSync = "+ serverSync;
        query+=" WHERE identity = " + this.getIdentity();
        query+="   AND device = '"+this.getDevice() + "'";
        query+="   AND key = '"+this.getKey() + "'";
        query+="   AND id_templateData = " + this.getId_templateData();

        this.dal.query(query);
    }

    public PatientValue(Cursor cursor) {
        this.id = cursor.getInt(0);
        this.identity = cursor.getInt(1);
        this.device = cursor.getString(2);
        this.key = cursor.getString(3);
        this.id_templateData = cursor.getInt(4);
        this.value = cursor.getString(5);
        this.serverSync = cursor.getLong(6);
    }

    public PatientValue(String value, TemplateData templateData) {
        this.setId(0);
        this.setValue(value);
        this.setTemplateData(templateData);
    }
    public PatientValue(Context context, TemplateData templateData, Cursor cursor) {
        this.setContext(context);
        this.setTemplateData(templateData);
        this.setId(cursor.getInt(0));
        this.setValue(cursor.getString(1));
        this.setServerSync(cursor.getLong(2));
    }
    public PatientValue(Context context, ResultServicePatientValue.PatientValue patientValue) {
        this.setContext(context);
        this.setDal(new DataAccessLogic(context));
        this.setIdentity(patientValue.getIdentity());
        this.setDevice(patientValue.getDevice());
        this.setKey(patientValue.getKey());
        this.setId_templateData(patientValue.getId_templateData());
        this.setValue(patientValue.getValue());
        this.setServerSync(patientValue.getServerSync());
    }

    public static ArrayList<PatientValue> getDirtyRecords(Context context) {
        String query = "SELECT id, identity, device, `key`, id_templateData, value, serverSync FROM patientValue WHERE serverSync = 0";
        DataAccessLogic dal = new DataAccessLogic(context);
        Cursor cursor = dal.read(query);
        ArrayList<PatientValue> patientValues = new ArrayList<>();
        while (cursor.moveToNext()) {
            PatientValue patientValue = new PatientValue(cursor);
            patientValues.add(patientValue);
        }
        cursor.close();

        return patientValues;
    }
    public static ArrayList<PatientValueDojo> getAll(Context context) {
        String query = "SELECT id, identity, device, `key`, id_templateData, value, serverSync FROM patientValue ";
        DataAccessLogic dal = new DataAccessLogic(context);
        Cursor cursor = dal.read(query);
        ArrayList<PatientValueDojo> patientValues = new ArrayList<>();
        while (cursor.moveToNext()) {
            PatientValueDojo patientValue = new PatientValueDojo(cursor);
            patientValues.add(patientValue);
        }
        cursor.close();

        return patientValues;
    }
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public TemplateData getTemplateData() {
        return templateData;
    }

    public void setTemplateData(TemplateData templateData) {
        this.templateData = templateData;
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

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public DataAccessLogic getDal() {
        return dal;
    }

    public void setDal(DataAccessLogic dal) {
        this.dal = dal;
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

    public void setTag(String _tag) {
        String[] tag = _tag.split(",");
        this.setIdentity(Integer.valueOf(tag[0]));
        this.setDevice(tag[1]);
        this.setKey(tag[2]);
        this.setId_templateData(Integer.valueOf(tag[3]));
    }

    public void update() {
        String query="";
        if (!this.exists()) {
            query+="INSERT INTO patientValue (identity, device, key, id_templateData, value, serverSync) VALUES (";
            query+=this.getIdentity() + ",";
            query+="'"+this.getDevice() + "',";
            query+="'"+this.getKey() + "',";
            query+=this.getId_templateData() + ",";
            query+="'"+this.getValue().replace("'","´") +  "',";
            query+=this.getServerSync() + ")";
        } else {
            query+="UPDATE patientValue";
            query+="   SET value = '"+this.getValue().replace("'","´") + "',";
            query+="       serverSync = " + this.getServerSync();
            query+=" WHERE identity = " + this.getIdentity();
            query+="   AND device = '"+this.getDevice() + "'";
            query+="   AND key = '"+this.getKey() + "'";
            query+="   AND id_templateData = " + this.getId_templateData();
        }
        this.dal.query(query);
    }
    public boolean exists() {
        boolean exists = false;
        String query = "";
        query += "SELECT id, identity, device, `key`, id_templateData, value, serverSync FROM patientValue ";
        query += " WHERE identity =" + this.getIdentity();
        query += "   AND device = '" + this.getDevice() + "'";
        query += "   AND `key` ='" + this.getKey() + "'";
        query += "   AND id_templateData =" + this.getId_templateData();
        DataAccessLogic dal = new DataAccessLogic(context);
        Cursor cursor = dal.read(query);
        if (cursor.moveToNext()) {
            exists = true;
        }
        cursor.close();

        return exists;
    }
    public static void insertBackup(Context context, PatientValueDojo patientValueDojo) {
        PatientValue patientValue = new PatientValue(context, patientValueDojo);
        patientValue.update();
    }
    public static void clearAll(Context context) {
        DataAccessLogic dal = new DataAccessLogic(context);

        String query="DELETE FROM patientValue";
        dal.query(query);

    }
    public PatientValue(Context context, PatientValueDojo patientValueDojo) {
        this.setContext(context);
        this.setDal(new DataAccessLogic(context));
        this.setIdentity(patientValueDojo.getIdentity());
        this.setDevice(patientValueDojo.getDevice());
        this.setKey(patientValueDojo.getKey());
        this.setId_templateData(patientValueDojo.getId_templateData());
        this.setValue(patientValueDojo.getValue());
        this.setServerSync(patientValueDojo.getServerSync());
    }
}
