package com.capetisoft.patients.services.io.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by carlospedroza on 03/12/15.
 */
public class ResultServiceExPe {
    @SerializedName("OK")
    public String OK;
    @SerializedName("ERROR")
    public String ERROR;
    @SerializedName("WARNING")
    public String WARNING;
    @SerializedName("result")
    public
    String result;
    @SerializedName("msg")
    public
    String msg;
    @SerializedName("syncServer")
    public long syncServer;
    @SerializedName("data")
    private ArrayList<ResultExistsService> data;

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


    public ArrayList<ResultExistsService> getData() {
        return data;
    }

    public void setData(ArrayList<ResultExistsService> data) {
        this.data = data;
    }
}
