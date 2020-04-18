package com.capetisoft.patients.services.io.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by carlospedroza on 28/11/15.
 */
public class ResultServicePatientValue {
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
    public ArrayList<PatientValue> data;
    @SerializedName("tag")
    private String tag;

    public static final String OK_CONS = "OK";
    public static final String ERROR_CONS = "ERROR";

    public ArrayList<PatientValue> getData() {
        return data;
    }

    public void setData(ArrayList<PatientValue>  data) {
        this.data = data;
    }

    public class PatientValue {
        @SerializedName("id")
        int id;
        @SerializedName("identity")
        int identity;
        @SerializedName("device")
        String device;
        @SerializedName("key")
        String key;
        @SerializedName("id_templateData")
        int id_templateData;
        @SerializedName("value")
        String value;
        @SerializedName("serverSync")
        long serverSync;
        @SerializedName("serverSyncDevice")
        String serverSyncDevice;

        public long getServerSync() {
            return serverSync;
        }

        public void setServerSync(long serverSync) {
            this.serverSync = serverSync;
        }

        public String getServerSyncDevice() {
            return serverSyncDevice;
        }

        public void setServerSyncDevice(String serverSyncDevice) {
            this.serverSyncDevice = serverSyncDevice;
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
    }

    public ResultServicePatientValue() {
    }

    public ResultServicePatientValue(String result) {
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
