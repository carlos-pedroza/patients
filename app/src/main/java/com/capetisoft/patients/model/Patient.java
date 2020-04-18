package com.capetisoft.patients.model;

import android.content.ContentValues;
import android.content.Context;
import android.content.res.Resources;
import android.database.Cursor;
import android.provider.Settings;

import com.capetisoft.patients.R;
import com.capetisoft.patients.dal.DataAccessLogic;
import com.capetisoft.patients.model.template.Template;
import com.capetisoft.patients.services.io.model.ResultServicePatient;
import com.capetisoft.patients.services.sync.SyncNotifications;
import com.capetisoft.patients.util.Utils;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by carlospedroza on 07/10/15.
 */
public class Patient {
    private DataAccessLogic dal;

    private Context context;
    private int id;
    private int identity;
    private String device;
    private String key;
    private String name;
    private String gender;
    private int idTemplate;
    private long dateBirth;
    private String phone;
    private String email;
    private int isAppointment;
    private long appointment;
    private int appointmentNotice;
    private String isRepeatTime;
    private int repeatTimes;
    private String skipAppointment;
    private String commentAppointment;
    private int idUser;
    private long lastUpdate;
    private long createDate;
    private long serverSync;
    private int deleted;
    private String serverSyncDevice;
    private ArrayList<Patient> list;
    private  final int IDENTITY_NEW = 0;

    @Override
    public boolean equals(Object o) {
        Patient patient = (Patient) o;
        return this.getTag().equals(patient.getTag());
    }

    public String getServerSyncDevice() {
        return serverSyncDevice;
    }

    public void setServerSyncDevice(String serverSyncDevice) {
        this.serverSyncDevice = serverSyncDevice;
    }

    public int getDeleted() {
        return deleted;
    }

    public void setDeleted(int deleted) {
        this.deleted = deleted;
    }

    public enum QueryType {
        all,today,week,month, sync
    }

    public static QueryType getQueryType(String queryType)  {
        if(queryType.equals("today")) {
            return QueryType.today;
        }
        else if(queryType.equals("week")) {
            return  QueryType.week;
        }
        else if(queryType.equals("month")) {
            return  QueryType.month;
        }
        else if(queryType.equals("sync")) {
            return  QueryType.sync;
        }
        else {
            return QueryType.all;
        }
    }
    public static String QueryTypeToString(QueryType queryType) {
        switch (queryType) {
            case today:
                return "today";
            case week:
                return "week";
            case month:
                return "month";
            case sync:
                return "sync";
            default:
                return "all";
        }
    }

    public Patient(Cursor cursor) {
        this.setId(cursor.getInt(0));
        this.setIdentity(cursor.getInt(1));
        this.setDevice(cursor.getString(2));
        this.setKey(cursor.getString(3));
        this.setName(cursor.getString(4));
        this.setGender(cursor.getString(5));
        this.setIdTemplate(cursor.getInt(6));
        this.setDateBirth(cursor.getLong(7));
        this.setPhone(cursor.getString(8));
        this.setEmail(cursor.getString(9));
        this.setIsAppointment(cursor.getInt(10));
        this.setAppointment(cursor.getLong(11));
        this.setAppointmentNotice(cursor.getInt(12));
        this.setIsRepeatTime(cursor.getString(13));
        this.setRepeatTimes(cursor.getInt(14));
        this.setSkipAppointment(cursor.getString(15));
        this.setCommentAppointment(cursor.getString(16));
        this.setIdUser(cursor.getInt(17));
        this.setLastUpdate(cursor.getLong(18));
        this.setCreateDate(cursor.getLong(19));
        this.setServerSync(cursor.getLong(20));
        this.setDeleted(cursor.getInt(21));
    }

    public Patient(Context context, ResultServicePatient.Patient patient) {
        this.setContext(context);
        this.dal = new DataAccessLogic(this.getContext());
        this.setId(patient.getId());
        this.setIdentity(patient.getIdentity());
        this.setDevice(patient.getDevice());
        this.setKey(patient.getKey());
        this.setName(patient.getName());
        this.setGender(patient.getGender());
        this.setIdTemplate(patient.getIdTemplate());
        this.setDateBirth(patient.getDateBirth());
        this.setPhone(patient.getPhone());
        this.setEmail(patient.getEmail());
        this.setIsAppointment(patient.getIsAppointment());
        this.setAppointment(patient.getAppointment());
        this.setAppointmentNotice(patient.getAppointmentNotice());
        this.setIsRepeatTime(patient.getIsRepeatTime());
        this.setRepeatTimes(patient.getRepeatTimes());
        this.setSkipAppointment(patient.getSkipAppointment());
        this.setCommentAppointment(patient.getCommentAppointment());
        this.setIdUser(patient.getIdUser());
        this.setLastUpdate(patient.getLastUpdate());
        this.setCreateDate(patient.getCreateDate());
        this.setServerSync(patient.getServerSync());
        this.setServerSyncDevice(patient.getServerSyncDevice());
        this.setDeleted(patient.getDeleted());
    }

    public Patient(Context context, boolean isVisit) {
        this.setContext(context);
        this.dal = new DataAccessLogic(this.getContext());
    }
    public Patient(Context context) {
        this.setContext(context);
        this.dal = new DataAccessLogic(this.getContext());

        String android_id = Settings.Secure.getString(getContext().getContentResolver(),
                            Settings.Secure.ANDROID_ID);
        this.setIdentity(IDENTITY_NEW);
        this.setDevice(android_id);
        SyncNotifications syncNotifications = new SyncNotifications(this.getContext());
        String key = syncNotifications.getcurrentKey();
        this.setKey(key);
        this.setIsAppointment(0);
        this.setAppointment(0);
        this.setAppointmentNotice(0);
        this.setIsRepeatTime("");
        this.setSkipAppointment("");
        this.setCommentAppointment("");
        this.setIdUser(0);
        long now = System.currentTimeMillis();
        this.setLastUpdate(now);
        this.setCreateDate(now);
        this.setServerSync(0);
        this.list = null;
    }
    public Patient(String letter) {
        this.setKey("letter");
        this.setName(letter);
    }
    public Patient(Context context, Patient.QueryType queryType, String key) {
        this.setContext(context);
        this.dal = new DataAccessLogic(this.getContext());
        switch (queryType) {
            case all:
                this.setList(read(queryType, key));
                break;
            case today:
                Utils.DateStructure dateStructureToday = new Utils.DateStructure();
                dateStructureToday.today();
                this.setList(read(dateStructureToday.getValuesMillis(), key));
                break;
            case week:
                Utils.DateStructure dateStructureWeek = new Utils.DateStructure();
                dateStructureWeek.week();
                this.setList(read(dateStructureWeek.getValuesMillis(), key));
                break;
            case month:
                Utils.DateStructure dateStructureMonth = new Utils.DateStructure();
                dateStructureMonth.month();
                this.setList(read(dateStructureMonth.getValuesMillis(), key));
                break;
        }
    }
    public Patient(Context context, Patient.QueryType queryType, String key, String name) {
        this.setContext(context);
        this.dal = new DataAccessLogic(this.getContext());
        switch (queryType) {
            case all:
                this.setList(read(key, name));
                break;
            case today:
                Utils.DateStructure dateStructureToday = new Utils.DateStructure();
                dateStructureToday.today();
                this.setList(read(dateStructureToday.getValuesMillis(), key, name));
                break;
            case week:
                Utils.DateStructure dateStructureWeek = new Utils.DateStructure();
                dateStructureWeek.week();
                this.setList(read(dateStructureWeek.getValuesMillis(), key, name));
                break;
            case month:
                Utils.DateStructure dateStructureMonth = new Utils.DateStructure();
                dateStructureMonth.month();
                this.setList(read(dateStructureMonth.getValuesMillis(), key, name));
                break;
        }
    }
    public Patient(Context context, String tag) {
        this.setContext(context);
        this.dal = new DataAccessLogic(this.getContext());
        read(tag);
    }
    public Patient(Context context, long id) {
        this.setContext(context);
        this.dal = new DataAccessLogic(this.getContext());
        read(id);
    }
    public Patient(Context context, long now, long fact) {
        this.setContext(context);
        this.dal = new DataAccessLogic(this.getContext());
        this.setList(read(now, fact));
    }

    public void set(Cursor cursor) {
        this.setId(cursor.getInt(0));
        this.setIdentity(cursor.getInt(1));
        this.setDevice(cursor.getString(2));
        this.setKey(cursor.getString(3));
        this.setName(cursor.getString(4));
        this.setGender(cursor.getString(5));
        this.setIdTemplate(cursor.getInt(6));
        this.setDateBirth(cursor.getLong(7));
        this.setPhone(cursor.getString(8));
        this.setEmail(cursor.getString(9));
        this.setIsAppointment(cursor.getInt(10));
        this.setAppointment(cursor.getLong(11));
        this.setAppointmentNotice(cursor.getInt(12));
        this.setIsRepeatTime(cursor.getString(13));
        this.setRepeatTimes(cursor.getInt(14));
        this.setSkipAppointment(cursor.getString(15));
        this.setCommentAppointment(cursor.getString(16));
        this.setIdUser(cursor.getInt(17));
        this.setLastUpdate(cursor.getLong(18));
        this.setCreateDate(cursor.getLong(19));
        this.setServerSync(cursor.getLong(20));
    }
    public ArrayList<Patient>  read(Patient.QueryType queryType, String key) {
        String query = "";
        query += "SELECT id, identity, device, key, name, gender, id_template, dateBirth, phone, email, isAppointment, appointment, appointmentNotice, isRepeat, repeatTimes, skipAppointment, comentAppointment,id_user, lastupdate, createdate, serverSync, deleted FROM patient";
        query += " WHERE key = '" + key + "'";
        query += "   AND deleted = 0";
        query += " ORDER BY name";
        switch (queryType) {
            case today:
                break;
            case week:
                break;
            case month:
                break;
            case all:
            default:
                //do nothing
                break;
        }
        ArrayList<Patient>  list = new ArrayList<>();
        Cursor cursor = this.dal.read(query);
        String _letterLast = "";
        while(cursor.moveToNext()) {
            Patient patient = new Patient(cursor);
            if (!patient.getName().equals("")) {
                char _letter = patient.getName().toUpperCase().charAt(0);
                if (!_letterLast.equals(String.valueOf(_letter))) {
                    _letterLast = String.valueOf(_letter);
                    Patient patientLetter = new Patient(_letterLast);
                    list.add(patientLetter);
                }

                list.add(patient);
            }
        }
        cursor.close();

        return list;
    }
    public ArrayList<Patient> read(String key, String name) {
        String query = "SELECT id, identity, device, key, name, gender, id_template, dateBirth, phone, email, isAppointment, appointment, appointmentNotice, isRepeat, repeatTimes, skipAppointment, comentAppointment,id_user, lastupdate, createdate, serverSync, deleted FROM patient";
        query += " WHERE key = '" + key + "'";
        query += "   AND name LIKE '%" + name + "%'";
        query += "   AND deleted = 0";
        query += " ORDER BY name";

        ArrayList<Patient>  list = new ArrayList<>();
        Cursor cursor = this.dal.read(query);
        String _letterLast = "";
        while(cursor.moveToNext()) {
            Patient patient = new Patient(cursor);
            if (!patient.getName().equals("")) {
                char _letter = patient.getName().toUpperCase().charAt(0);
                if (!_letterLast.equals(String.valueOf(_letter))) {
                    _letterLast = String.valueOf(_letter);
                    Patient patientLetter = new Patient(_letterLast);
                    list.add(patientLetter);
                }

                list.add(patient);
            }
        }
        cursor.close();

        return list;
    }
    public void read(String tag) {
        String identity[] = tag.split(",");
        String query = "SELECT id, identity, device, key, name, gender, id_template, dateBirth, phone, email, isAppointment, appointment, appointmentNotice, isRepeat, repeatTimes, skipAppointment, comentAppointment,id_user, lastupdate, createdate, serverSync, deleted FROM patient";
        query += " WHERE key = '" + identity[0] + "'";
        query += "   AND device = '" + identity[1] + "'";
        query += "   AND identity = " + identity[2];

        Cursor cursor = this.dal.read(query);
        if(cursor.moveToNext()) {
            this.set(cursor);
        }
        cursor.close();
    }
    public void read(long id) {
        String query = "SELECT id, identity, device, key, name, gender, id_template, dateBirth, phone, email, isAppointment, appointment, appointmentNotice, isRepeat, repeatTimes, skipAppointment, comentAppointment,id_user, lastupdate, createdate, serverSync, deleted FROM patient";
        query += " WHERE id = " + id;

        Cursor cursor = this.dal.read(query);
        if(cursor.moveToNext()) {
            this.set(cursor);
        }
        cursor.close();
    }
    public ArrayList<Patient> read(long now, long fact) {
        String query  = "";
        query += "SELECT id, identity, device, key, name, gender, id_template, dateBirth, phone, email, isAppointment, appointment, appointmentNotice, isRepeat, repeatTimes, skipAppointment, comentAppointment,id_user, lastupdate, createdate, serverSync, deleted FROM patient";
        query += " WHERE isAppointment     = 1";
        query += "   AND appointmentNotice = 1";
        query += "   AND (appointment - " + fact + ") < " + now;
        query += "   AND deleted = 0";
        query += " ORDER BY appointment";

        ArrayList<Patient>  list = new ArrayList<>();
        Cursor cursor = this.dal.read(query);

        while(cursor.moveToNext()) {
            Patient patient = new Patient(cursor);
            if (!patient.getName().equals("")) {
                list.add(patient);
            }
        }
        cursor.close();

        return list;
    }
    public ArrayList<Patient>  read(List<Long> values, String key) {
        String query  = "";
        query += "SELECT id, identity, device, key, name, gender, id_template, dateBirth, phone, email, isAppointment, appointment, appointmentNotice, isRepeat, repeatTimes, skipAppointment, comentAppointment,id_user, lastupdate, createdate, serverSync, deleted FROM patient";
        query += " WHERE key = '" + key +"'";
        query += "   AND appointment BETWEEN " + values.get(0).toString() + " AND " + values.get(1).toString();
        query += "   AND deleted = 0";
        query += " ORDER BY appointment";

        ArrayList<Patient>  list = new ArrayList<>();
        Cursor cursor = this.dal.read(query);
        while(cursor.moveToNext()) {
            Patient patient = new Patient(cursor);
            if (!patient.getName().equals("")) {
                list.add(patient);
            }
        }
        cursor.close();

        return list;
    }
    public ArrayList<Patient>  read(List<Long> values, String key, String name) {
        String query  = "";
        query += "SELECT id, identity, device, key, name, gender, id_template, dateBirth, phone, email, isAppointment, appointment, appointmentNotice, isRepeat, repeatTimes, skipAppointment, comentAppointment,id_user, lastupdate, createdate, serverSync, deleted FROM patient";
        query += " WHERE key = '" + key +"'";
        query += "   AND name LIKE '%" + name + "%'";
        query += "   AND appointment BETWEEN " + values.get(0).toString() + " AND " + values.get(1).toString();
        query += "   AND deleted = 0";
        query += " ORDER BY appointment";

        ArrayList<Patient>  list = new ArrayList<>();
        Cursor cursor = this.dal.read(query);
        while(cursor.moveToNext()) {
            Patient patient = new Patient(cursor);
            if (!patient.getName().equals("")) {
                list.add(patient);
            }
        }
        cursor.close();

        return list;
    }
    public static ArrayList<Patient> getDirtyRecords(Context context) {
        String query = "SELECT id, identity, device, key, name, gender, id_template, dateBirth, phone, email, isAppointment, appointment, appointmentNotice, isRepeat, repeatTimes, skipAppointment, comentAppointment,id_user, lastupdate, createdate, serverSync, deleted FROM patient WHERE serverSync = 0 AND identity <> 0";
        DataAccessLogic dal = new DataAccessLogic(context);
        Cursor cursor = dal.read(query);
        ArrayList<Patient> patients = new ArrayList<>();
        while (cursor.moveToNext()) {
            Patient patient = new Patient(cursor);
            patients.add(patient);
        }
        cursor.close();

        return patients;
    }
    public static ArrayList<PatientDojo> getAll(Context context) {
        String query = "SELECT id, identity, device, key, name, gender, id_template, dateBirth, phone, email, isAppointment, appointment, appointmentNotice, isRepeat, repeatTimes, skipAppointment, comentAppointment,id_user, lastupdate, createdate, serverSync, deleted FROM patient WHERE identity <> 0";
        DataAccessLogic dal = new DataAccessLogic(context);
        Cursor cursor = dal.read(query);
        ArrayList<PatientDojo> patients = new ArrayList<>();
        while (cursor.moveToNext()) {
            PatientDojo patient = new PatientDojo(cursor);
            patients.add(patient);
        }
        cursor.close();

        return patients;
    }
    public long update() {
        long id = 0 ;
        if(this.exists()) {
            this.save();
        }
        else {
            id=this.insert();
        }
        return id;
    }
    public long updateDevice() {
        long id = 0 ;
        if(this.exists()) {
            this.save();
        }
        else {
            id=this.insertDevice();
        }
        return id;
    }
    public long insertDevice() {
        ContentValues values =  new ContentValues();
        values.put("id",this.getIdentity());
        values.put("identity",this.getIdentity());
        values.put("device",this.getDevice());
        values.put("key",this.getKey());
        values.put("name", this.getName());
        values.put("gender", this.getGender());
        values.put("id_template",this.getIdTemplate());
        values.put("dateBirth", this.getDateBirth());
        values.put("phone", this.getPhone());
        values.put("email", this.getEmail());
        values.put("isAppointment", this.getIsAppointment());
        values.put("appointment", this.getAppointment());
        values.put("appointmentNotice", this.getAppointmentNotice());
        values.put("isRepeat",this.getIsRepeatTime());
        values.put("repeatTimes", this.getRepeatTimes());
        values.put("skipAppointment", this.getSkipAppointment());
        values.put("comentAppointment", this.getCommentAppointment());
        values.put("id_user", this.getIdUser());
        values.put("lastupdate", this.getLastUpdate());
        values.put("createdate", this.getCreateDate());
        values.put("serverSync", this.getServerSync());
        values.put("deleted", this.getDeleted());
        long id = this.dal.insert("patient", values);
        //this.generateIdentity();
        return id;
    }
    public boolean exists() {
        boolean exists = false;
        String query = "";
        query += "SELECT id, identity, device, key, name, gender, id_template, dateBirth, phone, email, isAppointment, appointment, appointmentNotice, isRepeat, repeatTimes, skipAppointment, comentAppointment,id_user, lastupdate, createdate, serverSync FROM patient ";
        query += " WHERE key = '" + this.getKey()+ "'";
        query += "   AND device = '" + this.getDevice() + "'";
        query += "   AND identity = " + this.getIdentity();
        DataAccessLogic dal = new DataAccessLogic(context);
        Cursor cursor = dal.read(query);
        ArrayList<Patient> patients = new ArrayList<>();
        if (cursor.moveToNext()) {
            exists = true;
        }
        cursor.close();

        return exists;
    }
    public void insert2() {
        String query = "INSERT INTO patient (identity, device, key, name, gender, id_template, dateBirth, phone, email, isAppointment, appointment, appointmentNotice, isRepeat, repeatTimes, skipAppointment, comentAppointment, id_user, lastupdate, createdate, serverSync, deleted) VALUES (" +
                this.getIdentity() + ",'" +
                this.getDevice() + "','" +
                this.getKey() + "','" +
                this.getName().replace("'","´") + "','" +
                this.getGender() + "'," +
                this.getIdTemplate() + "," +
                this.getDateBirth() + ",'" +
                this.getPhone() + "','" +
                this.getEmail().replace("'", "´") + "'," +
                this.getIsAppointment() + "," +
                this.getAppointment() + "," +
                this.getAppointmentNotice() + ",'" +
                this.getIsRepeatTime() + "'," +
                this.getRepeatTimes() + ",'" +
                this.getSkipAppointment() + "','" +
                this.getCommentAppointment().replace("'", "´") + "'," +
                this.getIdUser() + "," +
                this.getLastUpdate() + "," +
                this.getCreateDate() + "," +
                this.getServerSync() + "," +
                this.getDeleted() + ")";
        this.dal.query(query);
        this.generateIdentity();
    }
    public long insert() {
        ContentValues values =  new ContentValues();
        values.put("identity",this.getIdentity());
        values.put("device",this.getDevice());
        values.put("key",this.getKey());
        values.put("name", this.getName().replace("'", "´"));
        values.put("gender", this.getGender());
        values.put("id_template",this.getIdTemplate());
        values.put("dateBirth", this.getDateBirth());
        values.put("phone", this.getPhone());
        values.put("email", this.getEmail().replace("'", "´"));
        values.put("isAppointment", this.getIsAppointment());
        values.put("appointment", this.getAppointment());
        values.put("appointmentNotice", this.getAppointmentNotice());
        values.put("isRepeat",this.getIsRepeatTime());
        values.put("repeatTimes", this.getRepeatTimes());
        values.put("skipAppointment", this.getSkipAppointment());
        values.put("comentAppointment", this.getCommentAppointment().replace("'", "´"));
        values.put("id_user", this.getIdUser());
        values.put("lastupdate", this.getLastUpdate());
        values.put("createdate", this.getCreateDate());
        values.put("serverSync", this.getServerSync());
        values.put("deleted", this.getDeleted());
        long id = this.dal.insert("patient", values);
        this.generateIdentity();
        return id;
    }
    public void insert3() {
        String query = "INSERT INTO patient (identity, device, key, name, gender, id_template, dateBirth, phone, email, isAppointment, appointment, appointmentNotice, isRepeat, repeatTimes, skipAppointment, comentAppointment, id_user, lastupdate, createdate, serverSync, deleted) VALUES (" +
                this.getIdentity() + ",'" +
                this.getDevice() + "','" +
                this.getKey() + "','" +
                this.getName().replace("'","´") + "','" +
                this.getGender() + "'," +
                this.getIdTemplate() + "," +
                this.getDateBirth() + ",'" +
                this.getPhone() + "','" +
                this.getEmail().replace("'", "´") + "'," +
                this.getIsAppointment() + "," +
                this.getAppointment() + "," +
                this.getAppointmentNotice() + ",'" +
                this.getIsRepeatTime() + "'," +
                this.getRepeatTimes() + ",'" +
                this.getSkipAppointment() + "','" +
                this.getCommentAppointment().replace("'", "´") + "'," +
                this.getIdUser() + "," +
                this.getLastUpdate() + "," +
                this.getCreateDate() + "," +
                this.getServerSync() + "," +
                this.getDeleted() + ")";
        this.dal.query(query);
    }
    public void save() {
        String query = "";
        query += " UPDATE patient SET ";
        query += " name = '" + this.getName().replace("'", "´") + "',";
        query += " gender = '" + this.getGender() + "',";
        query += " id_template = " + this.getIdTemplate()  + ",";
        query += " dateBirth = " + this.getDateBirth() + ",";
        query += " phone = '" + this.getPhone() + "',";
        query += " email = '" + this.getEmail().replace("'", "´")  +  "',";
        query += " isAppointment = " + this.getIsAppointment() +  ",";
        query += " appointment = " + this.getAppointment() +  ",";
        query += " appointmentNotice = " + this.getAppointmentNotice() +  ",";
        query += " isRepeat = '" + this.getIsRepeatTime() +  "',";
        query += " repeatTimes = " + this.getRepeatTimes() +  ",";
        query += " skipAppointment = '" + this.getSkipAppointment() +  "',";
        query += " comentAppointment = '" + this.getCommentAppointment().replace("'","´")  +  "',";
        query += " id_user = " + this.getIdUser() +  ",";
        query += " lastupdate = " + this.getLastUpdate() +  ",";
        query += " createdate = " + this.getCreateDate() +  ",";
        query += " serverSync = " + this.getServerSync() +  ",";
        query += " deleted = " + this.getDeleted();
        query += " WHERE key = '" + this.getKey() + "'";
        query += "   AND device = '" + this.getDevice() + "'";
        query += "   AND identity = " + this.getIdentity();
        this.dal.query(query);
    }
    public void delete() {
        String query = "";
        query += "UPDATE patient SET ";
        query += " deleted = 1,";
        query += " serverSync = 0 ";
        query += "  WHERE key = '" + this.getKey() + "'";
        query += "    AND device = '" + this.getDevice() + "'";
        query += "    AND identity = " + this.getIdentity();
        this.dal.query(query);
        Visit.delete(this.getContext(), this);
    }
    public void checkAppointmetNotify(Patient patient) {
        String query = "";
        query += "UPDATE patient SET ";
        query += " appointmentNotice = " + patient.getAppointmentNotice() + ",";
        query += " serverSync = 0 ";
        query += " WHERE key = '" + patient.getKey() + "'";
        query += "   AND device = '" + patient.getDevice() + "'";
        query += "   AND identity = " + patient.getIdentity();
        this.dal.query(query);
    }
    private void generateIdentity() {
        String query = "";
        query += "UPDATE patient SET ";
        query += " identity = id";
        query += " WHERE identity = " + IDENTITY_NEW;
        this.dal.query(query);
    }

    public ArrayList<Patient>  getList() {
        return list;
    }

    public void setList(ArrayList<Patient>  list) {
        this.list = list;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getAppointmentNotice() {
        return appointmentNotice;
    }

    public void setAppointmentNotice(int appointmentNotice) {
        this.appointmentNotice = appointmentNotice;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name.replace('\'', '\"');
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender.replace('\'', '\"');
    }

    public int getIdTemplate() {
        return idTemplate;
    }

    public void setIdTemplate(int idTemplate) {
        this.idTemplate = idTemplate;
    }

    public long getDateBirth() {
        return dateBirth;
    }

    public void setDateBirth(long dateBirth) {
        this.dateBirth = dateBirth;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone.replace('\'', '\"');
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email.replace('\'', '\"');
    }

    public int getIsAppointment() {
        return isAppointment;
    }

    public void setIsAppointment(int isAppointment) {
        this.isAppointment = isAppointment;
    }

    public long getAppointment() {
        return appointment;
    }

    public void setAppointment(long appointment) {
        this.appointment = appointment;
    }

    public String getIsRepeatTime() {
        return isRepeatTime;
    }

    public void setIsRepeatTime(String isRepeatTime) {
        this.isRepeatTime = isRepeatTime;
    }

    public int getRepeatTimes() {
        return repeatTimes;
    }

    public void setRepeatTimes(int repeatTimes) {
        this.repeatTimes = repeatTimes;
    }

    public String getSkipAppointment() {
        return skipAppointment;
    }

    public void setSkipAppointment(String skipAppointment) {
        this.skipAppointment = skipAppointment;
    }

    public String getCommentAppointment() {
        return commentAppointment;
    }

    public void setCommentAppointment(String commentAppointment) {
        this.commentAppointment = commentAppointment.replace('\'', '\"');
    }

    public int getIdUser() {
        return idUser;
    }

    public void setIdUser(int idUser) {
        this.idUser = idUser;
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

    public long getLastUpdate() {
        return lastUpdate;
    }

    public void setLastUpdate(long lastUpdate) {
        this.lastUpdate = lastUpdate;
    }

    public Context getContext() {
        return context;
    }

    protected void setContext(Context context) {
        this.context = context;
    }

    public String getTag() {
        return this.getKey() + "," + this.getDevice() + "," + this.getIdentity();
    }

    public String getAppointmentText() {
        Date dAppointment = new Date(this.getAppointment());
        SimpleDateFormat sdf = new SimpleDateFormat(this.getContext().getResources().getString(R.string.dateTimeFormat));
        return sdf.format(dAppointment.getTime());
    }
    public String getDateBirthToString() {
        Date dDateBirth= new Date(this.getDateBirth());
        SimpleDateFormat sdf = new SimpleDateFormat(this.getContext().getResources().getString(R.string.dateTimeFormatShort));
        return sdf.format(dDateBirth.getTime());
    }
    public String getTemplate() {

        Template template = new Template(this.getContext(), this, this.getIdTemplate());

        return template.getName();
    }
    public String getGenderToString() {
        Resources resources = this.getContext().getResources();
        String[] genderArray = resources.getStringArray(R.array.gender);
        switch (this.getGender()) {
            case "f":
                return genderArray[0];
            case "m":
                return genderArray[1];
        }
        return "";
    }
    public void setStatusSync(long serverSync) {

        String query="";

        query+="UPDATE patient";
        query+="   SET serverSync = "+ serverSync;
        query+=" WHERE identity = " + this.getIdentity();
        query +="   AND device = '"+this.getDevice() + "'";
        query+="   AND key = '"+this.getKey() + "'";

        this.dal.query(query);
    }
    public static void clearAll(Context context) {
        DataAccessLogic dal = new DataAccessLogic(context);
        String query = "DELETE FROM patient";
        dal.query(query);
    }
    public static void insertBackup(Context context, PatientDojo patientDojo) {
        Patient patient = new Patient(context, patientDojo);
        patient.insert3();
    }
    public Patient(Context context, PatientDojo patientDojo) {
        this.setContext(context);
        this.dal = new DataAccessLogic(this.getContext());
        this.setIdentity(patientDojo.getIdentity());
        this.setDevice(patientDojo.getDevice());
        this.setKey(patientDojo.getKey());
        this.setName(patientDojo.getName());
        this.setGender(patientDojo.getGender());
        this.setIdTemplate(patientDojo.getIdTemplate());
        this.setDateBirth(patientDojo.getDateBirth());
        this.setPhone(patientDojo.getPhone());
        this.setEmail(patientDojo.getEmail());
        this.setIsAppointment(patientDojo.getIsAppointment());
        this.setAppointment(patientDojo.getAppointment());
        this.setAppointmentNotice(patientDojo.getAppointmentNotice());
        this.setIsRepeatTime(patientDojo.getIsRepeatTime());
        this.setRepeatTimes(patientDojo.getRepeatTimes());
        this.setSkipAppointment(patientDojo.getSkipAppointment());
        this.setCommentAppointment(patientDojo.getCommentAppointment());
        this.setIdUser(patientDojo.getIdUser());
        this.setLastUpdate(patientDojo.getLastUpdate());
        this.setCreateDate(patientDojo.getCreateDate());
        this.setServerSync(patientDojo.getServerSync());
        this.setDeleted(patientDojo.getDeleted());
    }
}
