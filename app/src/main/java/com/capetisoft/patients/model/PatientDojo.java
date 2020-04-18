package com.capetisoft.patients.model;

import android.database.Cursor;

/**
 * Created by carlospedroza on 26/05/16.
 */
public class PatientDojo {
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

    public PatientDojo() {

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

    public int getDeleted() {
        return deleted;
    }

    public void setDeleted(int deleted) {
        this.deleted = deleted;
    }

    public String getServerSyncDevice() {
        return serverSyncDevice;
    }

    public void setServerSyncDevice(String serverSyncDevice) {
        this.serverSyncDevice = serverSyncDevice;
    }

    public PatientDojo(Cursor cursor) {
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
}
