package com.capetisoft.patients.services.io.sync;

import android.content.Context;

import com.capetisoft.patients.R;
import com.capetisoft.patients.dal.DataAccessLogic;
import com.capetisoft.patients.model.Visit;
import com.capetisoft.patients.services.io.SyncUpAPIAdapter;
import com.capetisoft.patients.services.io.model.ResultServiceVisit;
import com.capetisoft.patients.services.sync.SyncNotifications;
import com.capetisoft.patients.util.Utils;

import java.util.ArrayList;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by carlospedroza on 05/12/15.
 */
public class SyncDwVisit implements Callback<ResultServiceVisit> {
    private DataAccessLogic dal;
    private Context context;
    SyncDwPatientValue syncDwPatientValue;

    public SyncDwVisit(Context context) {
        this.context = context;
        this.dal = new DataAccessLogic(context);
    }

    public void init(SyncDwPatientValue syncDwPatientValue) {
        this.syncDwPatientValue  = syncDwPatientValue;
        SyncNotifications syncNotifications = new SyncNotifications(this.getContext());
        String key = syncNotifications.getcurrentKey();
        if(!key.isEmpty()) {
            String device = Utils.getDevice(this.context);
            long lastSyncServer = syncNotifications.getLastSyncServerVI(key);

            SyncUpAPIAdapter.getApiService().visitDwService(key, device, lastSyncServer, this);
        }
    }

    public DataAccessLogic getDal() {
        return dal;
    }

    public void setDal(DataAccessLogic dal) {
        this.dal = dal;
    }

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    @Override
    public void success(ResultServiceVisit resultService, Response response) {
        SyncNotifications syncNotifications = new SyncNotifications(this.getContext());
        if(resultService.getResult().equals(resultService.getOK())) {
            String msg = this.getContext().getResources().getString(R.string.syncCorrect) + resultService.getMsg();
            syncNotifications.AddSyncNotification(SyncNotifications.SyncStatus.ok, msg);
            ArrayList<ResultServiceVisit.Visit> visits = resultService.getData();
            for (ResultServiceVisit.Visit visit : visits  ) {

                Visit visitNew = new Visit(this.getContext(), visit);
                visitNew.update();

                String msg2 = this.getContext().getResources().getString(R.string.syncInsertVisit) + " " + visitNew.getPatient().getName() + " : " + visitNew.getIdentity();
                syncNotifications.AddSyncNotification(SyncNotifications.SyncStatus.ok, msg2);
            }
            if(visits.size()>0) {
                String key = syncNotifications.getcurrentKey();
                syncNotifications.setLastSyncServerVI(key, visits.get(visits.size() - 1).getServerSync());
            }
        }
        else if(resultService.getResult().equals(resultService.getWARNING())) {
            String msg = context.getResources().getString(R.string.syncNoCorrect) + resultService.getMsg();
            syncNotifications.AddSyncNotification(SyncNotifications.SyncStatus.error, msg);
        }
        else {
            String msg = context.getResources().getString(R.string.syncNoCorrect) + resultService.getMsg();
            syncNotifications.AddSyncNotification(SyncNotifications.SyncStatus.error, msg);
        }
        SyncDwPatient syncDwPatient = new SyncDwPatient(this.getContext());
        syncDwPatient.init(this.syncDwPatientValue);
    }

    @Override
    public void failure(RetrofitError error) {
        SyncNotifications syncNotifications = new SyncNotifications(this.getContext());
        String msg = this.getContext().getResources().getString(R.string.syncNoCorrect);
        syncNotifications.AddSyncNotification(SyncNotifications.SyncStatus.error, msg);
        this.syncDwPatientValue.setIsProcess(false);
    }

}
