package com.capetisoft.patients.model;

import android.content.Context;
import android.database.Cursor;
import android.provider.Settings;

import com.capetisoft.patients.R;
import com.capetisoft.patients.dal.DataAccessLogic;
import com.capetisoft.patients.services.io.model.ResultService;
import com.capetisoft.patients.services.io.model.ResultServicePerson;
import com.capetisoft.patients.services.sync.SyncNotifications;

import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.UUID;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by carlospedroza on 03/10/15.
 */
public class Person implements Callback<ResultService> {
    private DataAccessLogic dal;

    private int id;
    private String email;
    private String key;
    private String name;
    private String password;
    private String passwordNoEncode;
    private long lastupdate;
    private long createdate;
    private Context context;
    private Boolean isLog;
    private Long timeLog;
    private long serverSync;


    public void load() {

    }

    public Person(String email, String password) {
        this.setEmail(email);
        this.setPassword(password);
    }

    public Person(Context context) {
        this.setContext(context);
        this.setDal(new DataAccessLogic(this.getContext()));
        this.load();
    }
    public Person(Context context, String name, String email, String password) {
        this.setContext(context);
        this.setName(name);
        this.setEmail(email.trim());
        this.setPassword(password);
        long time = System.currentTimeMillis();
        this.setCreatedate(time);
        this.setLastupdate(time);
        this.setIsLog(false);
        this.setServerSync(0);
    }
    public Person(Context context, PersonDojo personDojo) {
        this.setContext(context);
        this.setDal(new DataAccessLogic(this.getContext()));
        this.setEmail(personDojo.getEmail());
        this.setKey(personDojo.getKey());
        this.setName(personDojo.getName());
        this.setPassword(personDojo.getPassword());
        this.setLastupdate(personDojo.getLastupdate());
        this.setCreatedate(personDojo.getCreatedate());
        this.setServerSync(personDojo.getServerSync());
    }
    public Person(Context context, String email, String password) {
        this.setContext(context);
        this.setDal(new DataAccessLogic(this.getContext()));
        this.setEmail(email.trim());
        this.setPassword(password);

        String query = "SELECT id, email, key, name, password, lastupdate, createdate FROM person WHERE email = '" + this.getEmail() + "' AND password = '" + this.getPassword() + "'";

        Cursor cursor = this.getDal().read(query);
        this.setIsLog(false);
        if(cursor.moveToNext()) {
            this.setId(cursor.getInt(0));
            this.setKey(cursor.getString(2));
            this.setName(cursor.getString(3));
            this.setLastupdate(5);
            this.setCreatedate(6);
            this.setIsLog(true);
            this.setServerSync(0);
        }
        cursor.close();
    }
    public ArrayList<Person> getDirtyRecords() {

        String query = "SELECT id, email, key, name, password, lastupdate, createdate FROM person WHERE serverSync = 0";
        this.setDal(new DataAccessLogic(this.getContext()));
        Cursor cursor = getDal().read(query);
        ArrayList<Person> persons = new ArrayList<>();
        while (cursor.moveToNext()) {
            Person person = new Person(this.getContext(), this.getDal(), cursor);
            persons.add(person);
        }
        cursor.close();

        return persons;
    }
    public static PersonDojo getAll(Context context) {
        DataAccessLogic setDal = new DataAccessLogic(context);
        String query = "SELECT id, email, key, name, password, lastupdate, createdate, serverSync FROM person ";
        Cursor cursor = setDal.read(query);
        PersonDojo person=null;
        if(cursor.moveToNext()) {
            person  = new PersonDojo(cursor);
        }
        cursor.close();
        return person;
    }
    public Person(Context context, String key) {
        this.setContext(context);
        this.setDal(new DataAccessLogic(this.getContext()));
        this.setKey(key);

        String query = "SELECT id, email, key, name, password, lastupdate, createdate FROM person WHERE key = '" + this.getKey() + "'";
        this.setDal(new DataAccessLogic(this.getContext()));
        Cursor cursor = getDal().read(query);
        this.setIsLog(false);
        if(cursor.moveToNext()) {
            this.setContext(context);
            this.setId(cursor.getInt(0));
            this.setEmail(cursor.getString(1));
            this.setKey(cursor.getString(2));
            this.setName(cursor.getString(3));
            this.setPassword(cursor.getString(4));
            this.setLastupdate(cursor.getLong(5));
            this.setCreatedate(cursor.getLong(6));
            this.setIsLog(true);
            this.setServerSync(0);
        }
        cursor.close();
    }
    public static Person readKey(Context context) {
        String key;
        Person person = null;
        String query = "SELECT key FROM person ";
        DataAccessLogic dal = new DataAccessLogic(context);
        Cursor cursor = dal.read(query);
        if(cursor.moveToNext()) {
            key = cursor.getString(0);
            person = new Person(context, key);
        }
        cursor.close();
        return person;
    }
    public boolean exists() {
        boolean result = false;
        String query = "SELECT id FROM person WHERE email = '" + this.email + "'";
        this.setDal(new DataAccessLogic(this.getContext()));
        Cursor cursor = getDal().read(query);
        this.setIsLog(false);
        if(cursor.moveToNext()) {
            result = true;
        }
        cursor.close();
        return result;
    }
    public Person (Context context, ResultServicePerson.Person person) {
        this.setContext(context);
        this.setDal(new DataAccessLogic(this.getContext()));
        this.setEmail(person.getEmail());
        this.setKey(person.getKey());
        this.password = person.getPassword();
        this.setName(person.getName());
        this.setLastupdate(person.getLastupdate());
        this.setCreatedate(person.getCreatedate());
        this.setServerSync(person.getServersync());
    }

    public Person(Context context,DataAccessLogic dal, Cursor cursor) {
        this.setContext(context);
        this.setDal(dal);
        this.setId(cursor.getInt(0));
        this.setEmail(cursor.getString(1));
        this.setKey(cursor.getString(2));
        this.setName(cursor.getString(3));
        this.setPassword(cursor.getString(4));
        this.setLastupdate(cursor.getLong(5));
        this.setCreatedate(cursor.getLong(6));
        this.setIsLog(false);
        this.setServerSync(0);
    }


    public void insert() {
        long time = System.currentTimeMillis();
        this.setCreatedate(time);
        this.setLastupdate(time);
        this.setIsLog(false);
        String key = this.generateGUI();
        String query = "INSERT INTO person (email, key, name, password, lastupdate, createdate, serverSync) VALUES ('" +
                this.getEmail().replace("'", "´") + "','" +
                key + "','" +
                this.getName().replace("'","´") + "','"  +
                this.getPassword() + "'," +
                this.getLastupdate() + "," +
                this.getCreatedate() + "," +
                this.getServerSync() + ")";
        this.getDal().query(query);
        this.setKey(key);
        //InsertPersonAPIAdapter.getApiService().insertPersonService(this.getEmail(), key, this.getName(), this.getPassword(), this.getDevice(), this);
    }
    public void insert2() {
        long time = System.currentTimeMillis();
        this.setCreatedate(time);
        this.setLastupdate(time);
        this.setIsLog(false);
        String key = this.generateGUI();
        String query = "INSERT INTO person (email, key, name, password, lastupdate, createdate, serverSync) VALUES ('" +
                this.getEmail().replace("'", "´") + "','" +
                key + "','" +
                this.getName().replace("'","´") + "','"  +
                this.getPassword() + "'," +
                this.getLastupdate() + "," +
                this.getCreatedate() + "," +
                this.getServerSync() + ")";
        this.getDal().query(query);
    }
    public void insertBack() {
        this.setIsLog(false);
        String query = "INSERT INTO person (email, key, name, password, lastupdate, createdate, serverSync) VALUES ('" +
                this.getEmail().replace("'", "´") + "','" +
                this.getKey() + "','" +
                this.getName().replace("'","´") + "','"  +
                this.getPassword() + "'," +
                this.getLastupdate() + "," +
                this.getCreatedate() + "," +
                this.getServerSync() + ")";
        this.getDal().query(query);
        //InsertPersonAPIAdapter.getApiService().insertPersonService(this.getEmail(), key, this.getName(), this.getPassword(), this.getDevice(), this);
    }
    public void save() {
        long time = System.currentTimeMillis();
        this.setCreatedate(time);
        this.setLastupdate(time);
        this.setIsLog(false);
        String query = "";
        query += "UPDATE person SET ";
        query += "       email = '" + this.getEmail().replace("'", "´") + "',";
        query += "         key = '" + this.getKey() + "',";
        query += "        name = '" + this.getName().replace("'", "´") + "',";
        query += "    password = '" + this.getPassword() + "',";
        query += "  lastupdate = " + this.getLastupdate() + ",";
        query += "  serverSync = " + this.getServerSync();
        query += " WHERE email = '" + this.getEmail() +"'";

        this.getDal().query(query);
    }
    public void insertCloud() {
        String query = "INSERT INTO person (email, key, name, password, lastupdate, createdate, serverSync) VALUES ('" +
                this.getEmail().replace("'", "´") + "','" +
                this.getKey() + "','" +
                this.getName().replace("'","´") + "','"  +
                this.getPassword() + "'," +
                this.getLastupdate() + "," +
                this.getCreatedate() + "," +
                this.getServerSync() + ")";
        this.getDal().query(query);
    }

    public void statusSync(ResultService resultService, String msg) {
        long serverSync = 0;
        SyncNotifications syncNotifications = new SyncNotifications(this.getContext());
        if(resultService.getResult().equals(resultService.getOK())) {
            serverSync = resultService.getSyncServer();
            syncNotifications.AddSyncNotification(SyncNotifications.SyncStatus.ok, msg);

            String query = String.format("UPDATE person SET serverSync = %s WHERE email = '%s'",
                    serverSync,
                    this.getEmail());
            this.getDal().query(query);
        }
        else {
            syncNotifications.AddSyncNotification(SyncNotifications.SyncStatus.error, msg);
        }
    }
    public void recoverPassword() {
        String query = String.format("UPDATE person SET password = '%s', serverSync = %s WHERE email = '%s'",
                this.getPassword(),
                this.getServerSync(),
                this.getEmail());
        this.getDal().query(query);
    }
    public Boolean exist() {
        Boolean result = false;
        String query = "SELECT id, email, key, name, password, lastupdate, createdate FROM person WHERE email = '" + this.getEmail() + "' AND password = '" + this.getPassword() + "'";
        this.setDal(new DataAccessLogic(this.getContext()));
        Cursor cursor = getDal().read(query);
        this.setIsLog(false);
        if(cursor.moveToNext()) {
            result=true;
        }
        cursor.close();
        return result;
    }

    public void delete() {

    }

    private String generateGUI() {
        UUID uuid = UUID.randomUUID();
        long _codet = System.currentTimeMillis();
        String uuidInString = uuid.toString() + String.valueOf(_codet);

        return(uuidInString);
    }

    public String generatePassword(int length, boolean special) {
        int iteration = 0;
        String password = "";
        double randomNumber2;

        while(iteration < length){
            randomNumber2 = (Math.floor((Math.random() * 100)) % 94) + 33;
            int randomNumber = (int)randomNumber2;
            if(!special){
                if ((randomNumber >=33) && (randomNumber <=47)) { continue; }
                if ((randomNumber >=58) && (randomNumber <=64)) { continue; }
                if ((randomNumber >=91) && (randomNumber <=96)) { continue; }
                if ((randomNumber >=123) && (randomNumber <=126)) { continue; }
            }
            iteration++;
            password += fromCharCode(randomNumber);
        }
        return password;
    }
    private static String fromCharCode(int... codePoints) {
        StringBuilder builder = new StringBuilder(codePoints.length);
        for (int codePoint : codePoints) {
            builder.append(Character.toChars(codePoint));
        }
        return builder.toString();
    }
    private String encodePassword(String password) {
        try {
            byte[] bytesOfMessage = password.getBytes("UTF-8");

            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] thedigest = md.digest(bytesOfMessage);
            StringBuffer sb = new StringBuffer();
            for (int i = 0; i < thedigest.length; i++) {
                sb.append(Integer.toString((thedigest[i] & 0xff) + 0x100, 16).substring(1));
            }
            return sb.toString();
        }
        catch (Exception ex) {
            return "";
        }
    }

    //getter / setter

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email.replace('\'', '\"');
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = encodePassword(password);
        this.passwordNoEncode = password;
    }

    public void setPassword2(String password) {
        this.password = password;
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

    protected Context getContext() {
        return context;
    }

    protected void setContext(Context context) {
        this.context = context;
    }

    public Boolean getIsLog() {
        return isLog;
    }

    private void setIsLog(Boolean isLog) {
        this.isLog = isLog;
    }

    private Long getTimeLog() {
        return timeLog;
    }

    private void setTimeLog(Long timeLog) {
        this.timeLog = timeLog;
    }

    public String getDevice() {
        String android_id = Settings.Secure.getString(getContext().getContentResolver(),
                Settings.Secure.ANDROID_ID);
        return android_id;
    }

    @Override
    public void success(ResultService resultService, Response response) {
        if(resultService.getResult().equals(resultService.getOK())) {
            statusSync(resultService,context.getResources().getString(R.string.syncPersonOk));
        }
        else if(resultService.getResult().equals(resultService.getWARNING())) {
            statusSync(resultService, resultService.getMsg());
        }
        else {
            statusSync(resultService,context.getResources().getString(R.string.syncPersonErrorService));
        }
    }

    @Override
    public void failure(RetrofitError error) {
        statusSync(new ResultService(SyncNotifications.ERROR),context.getResources().getString(R.string.syncPersonError));
    }

    public DataAccessLogic getDal() {
        return dal;
    }

    public void setDal(DataAccessLogic dal) {
        this.dal = dal;
    }

    public long getServerSync() {
        return serverSync;
    }

    public void setServerSync(long serverSync) {
        this.serverSync = serverSync;
    }

    public void update() {
        if(this.exists()) {
            this.save();
        }
        else {
            this.insert2();
        }
    }
    public void deleteAllLog() {

        String query="";

        query+="DELETE FROM patientValue";
        query+=" WHERE key = '"+this.getKey() + "'";

        this.dal.query(query);

        query="DELETE FROM visit";
        query+=" WHERE key = '"+ this.getKey() + "'";

        this.dal.query(query);

        query="DELETE FROM patient";
        query+=" WHERE key = '"+ this.getKey() + "'";

        this.dal.query(query);
    }

    public String getPasswordNoEncode() {
        return passwordNoEncode;
    }

    public static void insertBackup(Context context, PersonDojo personDojo) {
        Person person = new Person(context, personDojo);
        person.insertBack();
    }

    public static void clearAll(Context context) {
        DataAccessLogic dal = new DataAccessLogic(context);

        String query="DELETE FROM person";
        dal.query(query);

    }
}
