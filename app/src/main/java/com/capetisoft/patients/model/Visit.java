package com.capetisoft.patients.model;

import android.content.Context;
import android.database.Cursor;

import com.capetisoft.patients.dal.DataAccessLogic;
import com.capetisoft.patients.services.io.model.ResultServiceVisit;

import java.util.ArrayList;
import java.util.Vector;

/**
 * Created by carlospedroza on 05/11/15.
 */
public class Visit {
    private Context context;
    private Patient patient;
    private int id;
    private int identity;
    private long dateVisit;
    private String diagnostic;
    private String drugs;
    private String comment;
    private long lastUpdate;
    private long createDate;
    private long serverSync;
    private int deleted;
    private final int IDENTITY_NEW = 0;
    private Vector<Visit> list;

    private DataAccessLogic  dal;

    public Visit(Context context) {
        this.setContext(context);
        this.dal = new DataAccessLogic(this.getContext());
    }

    public Visit(Context context, Cursor cursor) {
        Patient patient = new Patient(context, true);
        patient.setIdentity(cursor.getInt(4));
        patient.setDevice(cursor.getString(2));
        patient.setKey(cursor.getString(3));
        this.patient = new Patient(context, patient.getTag());
        this.id = cursor.getInt(0);
        this.identity = cursor.getInt(1);
        this.dateVisit = cursor.getLong(5);
        this.diagnostic = cursor.getString(6);
        this.drugs = cursor.getString(7);
        this.comment = cursor.getString(8);
        this.serverSync = cursor.getLong(9);
        this.deleted = cursor.getInt(10);
    }
    public Visit(Context context, VisitDojo visitDojo) {
        this.setContext(context);
        this.dal = new DataAccessLogic(this.getContext());
        this.setIdentity(visitDojo.getIdentity());

        Patient patient = new Patient(context, true);
        patient.setIdentity(visitDojo.getIdentityPatient());
        patient.setDevice(visitDojo.getDevice());
        patient.setKey(visitDojo.getKey());
        this.setPatient(new Patient(context, patient.getTag()));

        this.setDateVisit(visitDojo.getDateVisit());
        this.setDiagnostic(visitDojo.getDiagnostic());
        this.setDrugs(visitDojo.getDrugs());
        this.setComment(visitDojo.getComments());
        this.setLastUpdate(visitDojo.getLastupdate());
        this.setCreateDate(visitDojo.getCreatedate());
        this.setServerSync(visitDojo.getServerSync());
        this.setDeleted(visitDojo.getDeleted());
    }

    public Visit(Context context, Patient patient, String tag) {
        this.setContext(context);
        this.patient  = patient;
        this.dal = new DataAccessLogic(this.getContext());
        this.read(tag);
    }
    public Visit(Context context, String tag) {
        this.setContext(context);
        this.dal = new DataAccessLogic(this.getContext());
        this.setTag2(tag);
    }
    public Visit(Context context, ResultServiceVisit.Visit visit) {
        this.setContext(context);
        this.dal = new DataAccessLogic(this.getContext());
        long lastUpdate = System.currentTimeMillis();
        Patient patient = new Patient(context, true);
        patient.setIdentity(visit.getIdentityPatient());
        patient.setDevice(visit.getDevice());
        patient.setKey(visit.getKey());
        this.setPatient(patient);
        this.setIdentity(visit.getIdentity());
        this.setDateVisit(visit.getDateVisit());
        this.setDiagnostic(visit.getDiagnostic());
        this.setDrugs(visit.getDrugs());
        this.setComment(visit.getComments());
        this.setLastUpdate(lastUpdate);
        this.setServerSync(visit.getServerSync());
    }
    public void setStatusSync(long serverSync) {
        String query="";
        query+="UPDATE visit ";
        query+=" SET";
        query+=" serverSync=" + serverSync;
        query+= " WHERE device = '" + this.getPatient().getDevice() + "'";
        query+= "   AND key = '" + this.getPatient().getKey() + "'";
        query+= "   AND identityPatient = " + this.getPatient().getIdentity();
        query+= "   AND identity = " + this.getIdentity();
        this.dal.query(query);
    }

    public Visit(Context context, Patient patient) {
        this.setContext(context);
        this.setPatient(patient);
        this.dal = new DataAccessLogic(this.getContext());
        this.setList(this.read());
    }
    public Visit(Context context, Patient patient, Cursor cursor) {
        this.setContext(context);
        this.setId(cursor.getInt(0));
        this.setIdentity(cursor.getInt(1));
        this.setPatient(patient);
        this.setDateVisit(cursor.getLong(2));
        this.setDiagnostic(cursor.getString(3));
        this.setDrugs(cursor.getString(4));
        this.setComment(cursor.getString(5));
        this.setLastUpdate(cursor.getLong(6));
        this.setCreateDate(cursor.getLong(7));
        this.setServerSync(cursor.getLong(8));
    }
    public Visit(Context context, int identity, Patient patient, long dateVisit,  String diagnostic, String drugs, String comment ) {
        this.setContext(context);
        this.setIdentity(identity);
        this.setPatient(patient);
        this.setDateVisit(dateVisit);
        this.setDiagnostic(diagnostic);
        this.setDrugs(drugs);
        this.setComment(comment);
        long now = System.currentTimeMillis();
        this.setLastUpdate(now);
        this.setCreateDate(now);
        this.setServerSync(0);
        this.dal =  new DataAccessLogic(this.context);
    }
    private Vector<Visit> read() {
        String query  = "";
        query += "SELECT id, identity, dateVisit, diagnostic, drugs, comments, lastupdate, createdate, serverSync FROM visit";
        query += " WHERE device = '" + this.getPatient().getDevice() + "'";
        query += "   AND key = '" + this.getPatient().getKey() + "'";
        query += "   AND identityPatient = " + this.getPatient().getIdentity();
        query += "   AND deleted = 0";
        query += " ORDER BY dateVisit DESC";

        Vector<Visit> list = new Vector<>();
        Cursor cursor = this.dal.read(query);
        while(cursor.moveToNext()) {
            Visit visit = new Visit(this.getContext(), this.getPatient(), cursor);
            list.add(visit);
        }
        cursor.close();
        return list;
    }
    private void read(String tag) {
        this.setTag(tag);
        String query  = "";
        query += "SELECT id, identity, dateVisit, diagnostic, drugs, comments, lastupdate, createdate, serverSync FROM visit";
        query += " WHERE device = '" + this.getPatient().getDevice() + "'";
        query += "   AND key = '" + this.getPatient().getKey() + "'";
        query += "   AND identityPatient = " + this.getPatient().getIdentity();
        query += "   AND identity = " + this.getIdentity();
        query += "   AND deleted = 0";
        Cursor cursor = this.dal.read(query);
        if(cursor.moveToNext()) {
            this.set(cursor);
        }
        cursor.close();
    }
    public static ArrayList<Visit> getDirtyRecords(Context context) {
        String query = "SELECT id, identity, device, `key`, identityPatient, dateVisit, diagnostic, drugs, comments, serverSync, deleted  FROM visit WHERE serverSync = 0 AND identity <> 0";
        DataAccessLogic dal = new DataAccessLogic(context);
        Cursor cursor = dal.read(query);
        ArrayList<Visit> visits = new ArrayList<>();
        while (cursor.moveToNext()) {
            Visit visit = new Visit(context, cursor);
            visits.add(visit);
        }
        cursor.close();

        return visits;
    }
    public static ArrayList<VisitDojo> getAll(Context context) {
        String query = "SELECT id, identity, device, `key`, identityPatient, dateVisit, diagnostic, drugs, comments,lastupdate, createdate, serverSync, deleted  FROM visit ";
        DataAccessLogic dal = new DataAccessLogic(context);
        Cursor cursor = dal.read(query);
        ArrayList<VisitDojo> visits = new ArrayList<>();
        while (cursor.moveToNext()) {
            VisitDojo visit = new VisitDojo(cursor);
            visits.add(visit);
        }
        cursor.close();

        return visits;
    }
    public void save() {
        long now = System.currentTimeMillis();
        String query="";
        query+="UPDATE visit ";
        query+=" SET";
        query+=" dateVisit=" + this.getDateVisit()  + ",";
        query+=" diagnostic='" + this.getDiagnostic().replace("'", "´")  + "',";
        query+=" drugs='" + this.getDrugs().replace("'", "´") + "',";
        query+=" comments='" + this.getComment().replace("'", "´") + "',";
        query+=" lastupdate=" + now + ",";
        query+=" serverSync=" + this.getServerSync() + ",";
        query+=" deleted=" + this.getDeleted();
        query+= " WHERE device = '" + this.getPatient().getDevice() + "'";
        query+= "   AND key = '" + this.getPatient().getKey() + "'";
        query+= "   AND identityPatient = " + this.getPatient().getIdentity();
        query+= "   AND identity = " + this.getIdentity();
        this.dal.query(query);
    }
    public void update() {
        if(this.exists()) {
            this.save();
        }
        else {
            this.insert();
        }
    }
    public void updateDevice() {
        if(this.exists()) {
            this.save();
        }
        else {
            this.insertDevice();
        }
    }
    public boolean exists() {
        boolean exists  = false;
        String query = "";
        query+= "SELECT id  FROM visit";
        query+= " WHERE device = '" + this.getPatient().getDevice() + "'";
        query+= "   AND key = '" + this.getPatient().getKey() + "'";
        query+= "   AND identityPatient = " + this.getPatient().getIdentity();
        query+= "   AND identity = " + this.getIdentity();
        DataAccessLogic dal = new DataAccessLogic(context);
        Cursor cursor = dal.read(query);
        if (cursor.moveToNext()) {
            exists = true;
        }
        cursor.close();

        return exists;
    }
    public void insert() {
        String query="";
        query+="INSERT INTO visit (identity, device, key, identityPatient, dateVisit, diagnostic, drugs, comments,lastupdate, createdate, serverSync, deleted) VALUES (";
        query+=this.getIdentity() + ",";
        query+="'" + this.getPatient().getDevice() + "',";
        query+="'" + this.getPatient().getKey() + "',";
        query+=this.getPatient().getIdentity() + ",";
        query+=this.getDateVisit() + ",";
        query+="'" + this.getDiagnostic().replace("'", "´") + "',";
        query+="'" + this.getDrugs().replace("'", "´") + "',";
        query+="'" + this.getComment().replace("'", "´") + "',";
        query+=this.getLastUpdate() + ",";
        query+=this.getCreateDate() + ",";
        query+=this.getServerSync() + ",";
        query+=this.getDeleted() + ")";
        this.dal.query(query);
        this.generateIdentity();
    }
    public static void insertBack(Context context, VisitDojo visitDojo) {
        DataAccessLogic dal = new DataAccessLogic(context);
        String query="";
        query+="INSERT INTO visit (identity, device, key, identityPatient, dateVisit, diagnostic, drugs, comments,lastupdate, createdate, serverSync, deleted) VALUES (";
        query+=visitDojo.getIdentity() + ",";
        query+="'" + visitDojo.getDevice() + "',";
        query+="'" + visitDojo.getKey() + "',";
        query+=visitDojo.getIdentityPatient() + ",";
        query+=visitDojo.getDateVisit() + ",";
        query+="'" + visitDojo.getDiagnostic().replace("'", "´") + "',";
        query+="'" + visitDojo.getDrugs().replace("'", "´") + "',";
        query+="'" + visitDojo.getComments().replace("'", "´") + "',";
        query+=visitDojo.getLastupdate() + ",";
        query+=visitDojo.getCreatedate() + ",";
        query+=visitDojo.getServerSync() + ",";
        query+=visitDojo.getDeleted() + ")";
        dal.query(query);
    }
    public void insertDevice() {
        String query="";
        query+="INSERT INTO visit (id, identity, device, key, identityPatient, dateVisit, diagnostic, drugs, comments,lastupdate, createdate, serverSync, deleted) VALUES (";
        query+=this.getIdentity() + ",";
        query+=this.getIdentity() + ",";
        query+="'" + this.getPatient().getDevice() + "',";
        query+="'" + this.getPatient().getKey() + "',";
        query+=this.getPatient().getIdentity() + ",";
        query+=this.getDateVisit() + ",";
        query+="'" + this.getDiagnostic().replace("'", "´") + "',";
        query+="'" + this.getDrugs().replace("'", "´") + "',";
        query+="'" + this.getComment().replace("'","´") + "',";
        query+=this.getLastUpdate() + ",";
        query+=this.getCreateDate() + ",";
        query+=this.getServerSync() + ",";
        query+=this.getDeleted() + ")";
        this.dal.query(query);
        this.generateIdentity();
    }
    public void set(Cursor cursor) {
        this.setId(cursor.getInt(0));
        this.setIdentity(cursor.getInt(1));
        this.setDateVisit(cursor.getLong(2));
        this.setDiagnostic(cursor.getString(3));
        this.setDrugs(cursor.getString(4));
        this.setComment(cursor.getString(5));
        this.setLastUpdate(cursor.getLong(6));
        this.setCreateDate(cursor.getLong(7));
        this.setServerSync(cursor.getLong(8));
    }

    public void generateIdentity() {
        String query = "";
        query += "UPDATE visit SET ";
        query += " identity = id";
        query += " WHERE identity = " + IDENTITY_NEW;
        this.dal.query(query);
    }

    public void delete() {
        String query = "";
        query += "UPDATE visit SET ";
        query += " deleted = 1,";
        query += " serverSync = 0";
        query += " WHERE device = '" + this.getPatient().getDevice() + "'";
        query += "   AND key = '" + this.getPatient().getKey() + "'";
        query += "   AND identityPatient = " + this.getPatient().getIdentity();
        query += "   AND identity = " + this.getIdentity();
        this.dal.query(query);
    }
    public void delete(Patient patient) {
        this.setPatient(patient);
        String query = "";
        query += "UPDATE visit SET ";
        query += " deleted = 1,";
        query += " serverSync = 0";
        query += " WHERE device = '" + this.getPatient().getDevice() + "'";
        query += "   AND key = '" + this.getPatient().getKey() + "'";
        query += "   AND identityPatient = " + this.getPatient().getIdentity();
        this.dal.query(query);
    }
    public static void delete(Context context, Patient patient) {
        DataAccessLogic  dal = new DataAccessLogic(context);
        String query = "";
        query += "UPDATE visit SET ";
        query += " deleted = 1,";
        query += " serverSync = 0";
        query += " WHERE device = '" + patient.getDevice() + "'";
        query += "   AND key = '" + patient.getKey() + "'";
        query += "   AND identityPatient = " + patient.getIdentity();
        dal.query(query);
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
        this.diagnostic = diagnostic.replace('\'', '\"');;
    }

    public String getDrugs() {
        return drugs;
    }

    public void setDrugs(String drugs) {
        this.drugs = drugs.replace('\'', '\"');;
    }

    public long getLastUpdate() {
        return lastUpdate;
    }

    public void setLastUpdate(long lastUpdate) {
        this.lastUpdate = lastUpdate;
    }

    public long getCreateDate() {
        return createDate;
    }

    public void setCreateDate(long createDate) {
        this.createDate = createDate;
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

    public Patient getPatient() {
        return patient;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment.replace('\'', '\"');;
    }

    public Vector<Visit> getList() {
        return list;
    }

    public void setList(Vector<Visit> list) {
        this.list = list;
    }

    public String getTag() {
        return this.getPatient().getDevice() + "," + this.getPatient().getKey() + "," + this.getPatient().getIdentity() + "," + this.getIdentity();
    }

    public void setTag(String _tag) {
        String[] tag = _tag.split(",");
        this.getPatient().setDevice(tag[0]);
        this.getPatient().setKey(tag[1]);
        this.getPatient().setIdentity(Integer.parseInt(tag[2]));
        this.setIdentity(Integer.parseInt(tag[3]));
    }
    public void setTag2(String _tag) {
        String[] tag = _tag.split(",");
        this.setPatient(new Patient(this.getContext(), true));
        this.getPatient().setDevice(tag[0]);
        this.getPatient().setKey(tag[1]);
        this.getPatient().setIdentity(Integer.parseInt(tag[2]));
        this.setIdentity(Integer.parseInt(tag[3]));
    }

    public int getDeleted() {
        return deleted;
    }

    public void setDeleted(int deleted) {
        this.deleted = deleted;
    }

    public static void insertBackup(Context context, VisitDojo visitDojo) {
        Visit.insertBack(context, visitDojo);
    }
    public static void clearAll(Context context) {
        DataAccessLogic dal = new DataAccessLogic(context);

        String query="DELETE FROM visit";
        dal.query(query);

    }
}
