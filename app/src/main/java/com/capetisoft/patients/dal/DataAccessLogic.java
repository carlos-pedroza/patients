package com.capetisoft.patients.dal;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by carlospedroza on 03/10/15.
 */
public class DataAccessLogic extends SQLiteOpenHelper {

    private String personStructure = "CREATE TABLE person ( id INTEGER PRIMARY KEY AUTOINCREMENT, email TEXT, key TEXT, name TEXT, password TEXT, lastupdate LONG, createdate LONG, serverSync LONG )";

    private String patientStructure = "CREATE TABLE patient (id INTEGER PRIMARY KEY AUTOINCREMENT, identity INTEGER, device TEXT, key TEXT, name TEXT, gender TEXT, id_template INTEGER, dateBirth LONG, phone TEXT, email TEXT, isAppointment INTEGER, appointment LONG, appointmentNotice INTEGER, isRepeat TEXT, repeatTimes INTEGER, skipAppointment TEXT, comentAppointment TEXT, id_user INTEGER, lastupdate LONG, createdate LONG, serverSync LONG, deleted INT )";
    private String visitStructure = "CREATE TABLE visit (id INTEGER PRIMARY KEY AUTOINCREMENT, identity INTEGER, device TEXT, key TEXT, identityPatient INTEGER, dateVisit LONG, diagnostic TEXT, drugs TEXT, comments TEXT,lastupdate LONG, createdate LONG, serverSync LONG, deleted INT)";

    private String templateStructure = "CREATE TABLE template ( id INTEGER PRIMARY KEY, name TEXT, orderItem INTEGER, id_language INTEGER, serverSync LONG, deleted INT )";
    private String templateDataStructure = "CREATE TABLE templateData ( id INTEGER PRIMARY KEY, id_templateDataType INTEGER, title TEXT, id_templateGroup INTEGER, required INTEGER, orderItem INTEGER )";
    private String templateDataItemStructure = "CREATE TABLE templateDataItem ( id INTEGER PRIMARY KEY, id_templateData INTEGER, itemName TEXT, orderItem INTEGER)";
    private String templateDataTypeStructure = "CREATE TABLE templateDataType ( id INTEGER PRIMARY KEY, type TEXT, id_language INTEGER)";
    private String templateGroupStructure = "CREATE TABLE templateGroup ( id INTEGER PRIMARY KEY, name TEXT, orderItem INTEGER, id_template INTEGER, multiple INTEGER )";

    private String patientValueStructure = "CREATE TABLE patientValue ( id INTEGER PRIMARY KEY AUTOINCREMENT, identity INTEGER, device TEXT, key TEXT, id_templateData INTEGER, value TEXT, serverSync LONG)";

    private String processSyncStructure = "CREATE TABLE processsync ( id INTEGER PRIMARY KEY AUTOINCREMENT, status INTEGER, msg TEXT, createtime LONG)";
    private String syncData = "CREATE TABLE syncData (currentKey TEXT)";
    private String syncDatakey = "CREATE TABLE syncDataKey (key TEXT, lastSyncServer LONG, lastSyncServerPV LONG, lastSyncServerVI LONG)";
    private String syncDataInit = "INSERT INTO syncData (currentKey) VALUES ('')";

    public DataAccessLogic(Context context) {
        super(context, "patients", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //Create tables
        db.execSQL(this.personStructure);
        db.execSQL(this.patientStructure);
        db.execSQL(this.visitStructure);

        db.execSQL(this.templateStructure);
        db.execSQL(this.templateDataStructure);
        db.execSQL(this.templateDataItemStructure);
        db.execSQL(this.templateDataTypeStructure);
        db.execSQL(this.templateGroupStructure);
        db.execSQL(this.patientValueStructure);
        db.execSQL(this.processSyncStructure);
        db.execSQL(this.syncData);
        db.execSQL(this.syncDataInit);
        db.execSQL(this.syncDatakey);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public void query(String _query) {
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL(_query);
    }
    public long insert(String table,ContentValues values) {
        SQLiteDatabase db = getWritableDatabase();
        long id = db.insert(table, null, values);
        return id;
    }

    public void query(String _query, Object[] params) {
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL(_query, params);
    }

    public Cursor read(String _query) {

        SQLiteDatabase db = getReadableDatabase();

        Cursor cursor = db.rawQuery(_query, null);

        return cursor;

    }
    public Cursor read(String _query, String[] params) {

        SQLiteDatabase db = getReadableDatabase();

        Cursor cursor = db.rawQuery(_query, params);

        return cursor;

    }
}
