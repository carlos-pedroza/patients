package com.capetisoft.patients.model;

import android.database.Cursor;

/**
 * Created by carlospedroza on 27/05/16.
 */
public class PersonDojo {
    private int id;
    private String email;
    private String key;
    private String name;
    private String password;
    private long lastupdate;
    private long createdate;
    private long serverSync;

    public PersonDojo() {

    }

    public PersonDojo(Cursor cursor) {
        this.id = cursor.getInt(0);
        this.email = cursor.getString(1);
        this.key = cursor.getString(2);
        this.name = cursor.getString(3);
        this.password = cursor.getString(4);
        this.lastupdate = cursor.getLong(5);
        this.createdate = cursor.getLong(6);
        this.serverSync = cursor.getLong(7);
    }

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
        this.email = email;
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
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
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

    public long getServerSync() {
        return serverSync;
    }

    public void setServerSync(long serverSync) {
        this.serverSync = serverSync;
    }
}
