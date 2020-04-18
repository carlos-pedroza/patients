package com.capetisoft.patients.services.io.sync;

import android.content.Context;
import android.os.SystemClock;

import com.capetisoft.patients.R;
import com.capetisoft.patients.dal.DataAccessLogic;
import com.capetisoft.patients.model.Visit;
import com.capetisoft.patients.services.io.SyncUpAPIAdapter;
import com.capetisoft.patients.services.io.model.ResultService;
import com.capetisoft.patients.services.sync.SyncNotifications;
import com.capetisoft.patients.util.Utils;

import java.util.ArrayList;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by carlospedroza on 05/12/15.
 */
public class SyncVisit implements Callback<ResultService> {
    private DataAccessLogic dal;
    private Context context;
    private SyncPatientValue syncPatientValue;
    private boolean syncContinue;

    public SyncVisit(Context context) {
        this.context = context;
        this.dal = new DataAccessLogic(context);
        this.syncContinue = false;
    }

    public void init(SyncPatientValue syncPatientValue) {
        this.syncContinue = true;
        this.setSyncPatientValue(syncPatientValue);
        ArrayList<Visit> visits =  Visit.getDirtyRecords(this.getContext());
        String device = Utils.getDevice(this.context);
        for ( Visit visit : visits) {
            SyncUpAPIAdapter.getApiService().visitUpService(visit.getIdentity(), visit.getPatient().getDevice(), visit.getPatient().getKey(), visit.getPatient().getIdentity(), visit.getDateVisit(), "_12345" + visit.getDiagnostic() + "_12345", "_12345" + visit.getDrugs() + "_12345", "_12345" + visit.getComment() + "_12345", device, visit.getDeleted(), this);
            SystemClock.sleep(1000);
        }
        if(visits.size()==0) {
            this.syncContinue = false;
            SyncPatient syncPatient = new SyncPatient(this.getContext());
            syncPatient.init(this.getSyncPatientValue());
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
    public void success(ResultService resultService, Response response) {
        SyncNotifications syncNotifications = new SyncNotifications(this.getContext());
        if(resultService.getResult().equals(resultService.getOK())) {
            String msg = this.getContext().getResources().getString(R.string.syncCorrect) + resultService.getMsg();
            syncNotifications.AddSyncNotification(SyncNotifications.SyncStatus.ok, msg);
            Visit visit = new Visit(this.getContext(), resultService.getTag());
            visit.setStatusSync(resultService.getSyncServer());
        }
        else if(resultService.getResult().equals(resultService.getWARNING())) {
            String msg = context.getResources().getString(R.string.syncNoCorrect) + resultService.getMsg();
            syncNotifications.AddSyncNotification(SyncNotifications.SyncStatus.error, msg);
        }
        else {
            String msg = context.getResources().getString(R.string.syncNoCorrect) + resultService.getMsg();
            syncNotifications.AddSyncNotification(SyncNotifications.SyncStatus.error, msg);
        }
        if(this.syncContinue) {
            this.syncContinue = false;
            SyncPatient syncPatient = new SyncPatient(this.getContext());
            syncPatient.init(this.getSyncPatientValue());
        }
    }

    @Override
    public void failure(RetrofitError error) {
        SyncNotifications syncNotifications = new SyncNotifications(this.getContext());
        String msg = this.getContext().getResources().getString(R.string.syncNoCorrect) + "Visit";
        syncNotifications.AddSyncNotification(SyncNotifications.SyncStatus.error, msg);
        this.getSyncPatientValue().setIsProcess(false);
    }

    public SyncPatientValue getSyncPatientValue() {
        return syncPatientValue;
    }

    public void setSyncPatientValue(SyncPatientValue syncPatientValue) {
        this.syncPatientValue = syncPatientValue;
    }
}
