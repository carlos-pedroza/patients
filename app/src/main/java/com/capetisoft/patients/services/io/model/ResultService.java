package com.capetisoft.patients.services.io.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by carlospedroza on 28/11/15.
 */
public class ResultService {
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
    public String data;
    @SerializedName("tag")
    private String tag;

    public static final String OK_CONS = "OK";
    public static final String ERROR_CONS = "ERROR";

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public ResultService() {
    }

    public ResultService(String result) {
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
