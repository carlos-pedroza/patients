package com.capetisoft.patients.model;

/**
 * Created by carlospedroza on 30/05/16.
 */
public class BackupTime {
    public BackupTime() {

    }
    public BackupTime(long value) {
        this.value = value;
    }

    public long getValue() {
        return value;
    }

    public void setValue(long value) {
        this.value = value;
    }

    long value;
}
