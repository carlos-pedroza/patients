package com.capetisoft.patients.services.sync;

import android.content.Context;
import android.database.Cursor;

import com.capetisoft.patients.dal.DataAccessLogic;
import com.capetisoft.patients.util.Utils;

import java.util.ArrayList;

/**
 * Created by carlospedroza on 30/11/15.
 */
public class SyncNotifications  {
    private Context context;
    private DataAccessLogic dal;

    public static final String OK = "OK";
    public static final String ERROR = "ERROR";
    public static final String WARNING = "WARNING";

    public SyncNotifications(Context context) {
        this.context = context;
        this.dal = new DataAccessLogic(this.getContext());
    }

    public class ProcessSync {
        private int id;
        private SyncStatus syncStatus;
        private String msg;
        private long createTime;

        public ProcessSync(Cursor cursor) {
            this.setId(cursor.getInt(0));
            this.setSyncStatus(cursor.getInt(1));
            this.setMsg(cursor.getString(2));
            this.setCreateTime(cursor.getLong(3));
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public SyncStatus getSyncStatus() {
            return syncStatus;
        }

        public void setSyncStatus(SyncStatus syncStatus) {
            this.syncStatus = syncStatus;
        }
        public void setSyncStatus(int syncStatus) {
            this.syncStatus = this.getSyncStatus(syncStatus);
        }

        public String getMsg() {
            return msg;
        }

        public void setMsg(String msg) {
            this.msg = msg;
        }

        public long getCreateTime() {
            return createTime;
        }

        public void setCreateTime(long createTime) {
            this.createTime = createTime;
        }

        public SyncStatus getSyncStatus(int syncStatus) {
            switch (syncStatus) {
                case 1:
                    return SyncStatus.ok;
                case 2:
                    return SyncStatus.error;
                case 3:
                    return SyncStatus.warning;
            }
            return SyncStatus.error;
        }
    }

    public ArrayList<ProcessSync> getSyncStatusList() {
        String query  = "";
        query += "SELECT  id, status, msg, createtime FROM processsync";
        query += " ORDER BY createtime DESC";
        query += " LIMIT 100";

        ArrayList<ProcessSync> list = new ArrayList<>();
        Cursor cursor = this.dal.read(query);
        while(cursor.moveToNext()) {
            ProcessSync processSync = new ProcessSync(cursor);
            list.add(processSync);
        }
        cursor.close();
        return list;
    }

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public DataAccessLogic getDal() {
        return dal;
    }

    public void setDal(DataAccessLogic dal) {
        this.dal = dal;
    }

    public void AddSyncNotification(SyncNotifications.SyncStatus syncStatus, String msg) {
        String query = String.format("INSERT INTO processsync (status, msg, createtime ) VALUES(%s,'%s',%s )",
                this.getSyncStatus(syncStatus),
                msg,
                System.currentTimeMillis());
        this.dal.query(query);
    }

    public void clearSyncNotification() {
        long timeDelete = System.currentTimeMillis();
        timeDelete = timeDelete - Utils.getTimeMillis(Utils.MILLIS_2_DAY);
        String query = String.format("DELETE FROM processsync WHERE createtime <= %s",
                timeDelete);
        this.dal.query(query);
    }
    public void setcurrentKey(String key) {
        String query = String.format("UPDATE syncData SET currentKey = '%s'",
                key);
        this.dal.query(query);
    }
    public String getcurrentKey() {
        String currentKey="";
        String query = "SELECT currentKey FROM syncData";
        Cursor cursor = dal.read(query);
        if (cursor.moveToNext()) {
            currentKey = cursor.getString(0);
        }
        cursor.close();

        return currentKey;
    }
    private boolean existsLastSyncServer(String key) {
        String query = "SELECT key FROM syncDataKey WHERE key = '" + key + "'";
        Cursor cursor = dal.read(query);
        if (cursor.moveToNext()) {
            return true;
        }
        cursor.close();

        return false;
    }
    public void setLastSyncServer(String key, long lastSyncServer) {
        String query;
        if(existsLastSyncServer(key)) {
            query = String.format("UPDATE syncDataKey SET lastSyncServer = %s WHERE key = '%s'",
                    lastSyncServer, key);
        }
        else {
            query = String.format("INSERT INTO syncDataKey (key, lastSyncServer, lastSyncServerPV, lastSyncServerVI) VALUES ('%s', %s, 0, 0)",
                    key, lastSyncServer);
        }
        this.dal.query(query);
    }
    public long getLastSyncServer(String key) {
        long lastSyncServer=0;
        String query = "SELECT lastSyncServer FROM syncDataKey WHERE key = '" + key + "'";
        Cursor cursor = dal.read(query);
        if (cursor.moveToNext()) {
            lastSyncServer = cursor.getLong(0);
        }
        cursor.close();

        return lastSyncServer;
    }
    public void setLastSyncServerPV(String key, long lastSyncServer) {
        String query;
        if(existsLastSyncServer(key)) {
            query = String.format("UPDATE syncDataKey SET lastSyncServerPV = %s WHERE key = '%s'",
                    lastSyncServer, key);
        }
        else {
            query = String.format("INSERT INTO syncDataKey (key, lastSyncServer, lastSyncServerPV, lastSyncServerVI) VALUES ('%s', 0, %s, 0)",
                    key, lastSyncServer);
        }
        this.dal.query(query);
    }
    public long getLastSyncServerPV(String key) {
        long lastSyncServer=0;
        String query = "SELECT lastSyncServerPV FROM syncDataKey WHERE key = '" + key + "'";
        Cursor cursor = dal.read(query);
        if (cursor.moveToNext()) {
            lastSyncServer = cursor.getLong(0);
        }
        cursor.close();

        return lastSyncServer;
    }
    public void setLastSyncServerVI(String key, long lastSyncServer) {
        String query;
        if(existsLastSyncServer(key)) {
            query = String.format("UPDATE syncDataKey SET lastSyncServerVI = %s WHERE key = '%s'",
                    lastSyncServer, key);
        }
        else {
            query = String.format("INSERT INTO syncDataKey (key, lastSyncServer, lastSyncServerPV, lastSyncServerVI) VALUES ('%s', 0, 0, %s)",
                    key, lastSyncServer);
        }
        this.dal.query(query);
    }
    public long getLastSyncServerVI(String key) {
        long lastSyncServer=0;
        String query = "SELECT lastSyncServerVI FROM syncDataKey WHERE key = '" + key + "'";
        Cursor cursor = dal.read(query);
        if (cursor.moveToNext()) {
            lastSyncServer = cursor.getLong(0);
        }
        cursor.close();

        return lastSyncServer;
    }

    public int getSyncStatus(SyncNotifications.SyncStatus syncStatus) {
        int res = 2;
        switch (syncStatus) {
            case ok:
                res = 1;
                break;
            case error:
                res = 2;
                break;
            case warning:
                res = 3;
                break;
            default:
                res = 2;
                break;
        }
        return res;
    }

    public enum SyncStatus {
        ok,
        error,
        warning
    }


}
