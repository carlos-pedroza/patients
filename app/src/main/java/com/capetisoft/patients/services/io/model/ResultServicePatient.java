package com.capetisoft.patients.services.io.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by carlospedroza on 28/11/15.
 */
public class ResultServicePatient {
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
    public ArrayList<Patient> data;
    @SerializedName("tag")
    private String tag;

    public static final String OK_CONS = "OK";
    public static final String ERROR_CONS = "ERROR";

    public ArrayList<Patient>  getData() {
        return data;
    }

    public void setData(ArrayList<Patient>  data) {
        this.data = data;
    }

    public class Patient {
        @SerializedName("id")
        private int id;
        @SerializedName("identity")
        private int identity;
        @SerializedName("device")
        private String device;
        @SerializedName("key")
        private String key;
        @SerializedName("name")
        private String name;
        @SerializedName("gender")
        private String gender;
        @SerializedName("id_template")
        private int idTemplate;
        @SerializedName("dateBirth")
        private long dateBirth;
        @SerializedName("phone")
        private String phone;
        @SerializedName("email")
        private String email;
        @SerializedName("isAppointment")
        private int isAppointment;
        @SerializedName("appointment")
        private long appointment;
        @SerializedName("appointmentNotice")
        private int appointmentNotice;
        @SerializedName("isRepeat")
        private String isRepeatTime;
        @SerializedName("repeatTimes")
        private int repeatTimes;
        @SerializedName("skipAppointment")
        private String skipAppointment;
        @SerializedName("commentAppointment")
        private String commentAppointment;
        @SerializedName("id_user")
        private int idUser;
        @SerializedName("lastupdate")
        private long lastUpdate;
        @SerializedName("createDate")
        private long createDate;
        @SerializedName("serverSync")
        private long serverSync;
        @SerializedName("serverSyncDevice")
        private String serverSyncDevice;
        @SerializedName("deleted")
        private int deleted;

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

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getGender() {
            return gender;
        }

        public void setGender(String gender) {
            this.gender = gender;
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
            this.phone = phone;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
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

        public int getAppointmentNotice() {
            return appointmentNotice;
        }

        public void setAppointmentNotice(int appointmentNotice) {
            this.appointmentNotice = appointmentNotice;
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
            this.commentAppointment = commentAppointment;
        }

        public int getIdUser() {
            return idUser;
        }

        public void setIdUser(int idUser) {
            this.idUser = idUser;
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
    }

    public ResultServicePatient() {
    }

    public ResultServicePatient(String result) {
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
