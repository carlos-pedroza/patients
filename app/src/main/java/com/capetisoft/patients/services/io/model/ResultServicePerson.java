package com.capetisoft.patients.services.io.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by carlospedroza on 28/11/15.
 */
public class ResultServicePerson {
    @SerializedName("OK")
    public String OK;
    @SerializedName("ERROR")
    public String ERROR;
    @SerializedName("WARNING")
    public String WARNING;
    @SerializedName("result")
    public String result;
    @SerializedName("msg")
    public String msg;
    @SerializedName("syncServer")
    public long syncServer;
    @SerializedName("data")
    public ArrayList<Person> data;
    @SerializedName("tag")
    private String tag;

    public static final String OK_CONS = "OK";
    public static final String ERROR_CONS = "ERROR";

    public ArrayList<Person>  getData() {
        return data;
    }

    public void setData(ArrayList<Person>  data) {
        this.data = data;
    }

    public class Person {
        @SerializedName("id")
        private
        int id;
        @SerializedName("email")
        private
        String email;
        @SerializedName("key")
        private
        String key;
        @SerializedName("name")
        private
        String name;
        @SerializedName("password")
        private
        String password;
        @SerializedName("lastupdate")
        private
        long lastupdate;
        @SerializedName("createdate")
        private
        long createdate;
        @SerializedName("serversync")
        private
        long serversync;
        @SerializedName("device")
        private
        String device;

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

        public long getServersync() {
            return serversync;
        }

        public void setServersync(long serversync) {
            this.serversync = serversync;
        }

        public String getDevice() {
            return device;
        }

        public void setDevice(String device) {
            this.device = device;
        }
    }

    public ResultServicePerson() {
    }

    public ResultServicePerson(String result) {
        this.setSyncServer(0);
        this.result = result;
        this.setOK(OK_CONS);
        this.setERROR(ERROR_CONS);
    }

    public long getSyncServer() {
        return syncServer;
    }

    public void setSyncServer(long syncServer) {
        this.syncServer = syncServer;
    }

    public String getOK() {
        return OK;
    }

    public void setOK(String OK) {
        this.OK = OK;
    }

    public String getERROR() {
        return ERROR;
    }

    public void setERROR(String ERROR) {
        this.ERROR = ERROR;
    }

    public String getWARNING() {
        return WARNING;
    }

    public void setWARNING(String WARNING) {
        this.WARNING = WARNING;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }


    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }
}
