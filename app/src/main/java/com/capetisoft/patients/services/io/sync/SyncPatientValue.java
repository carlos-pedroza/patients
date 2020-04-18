package com.capetisoft.patients.services.io.sync;

import android.content.Context;
import android.os.SystemClock;

import com.capetisoft.patients.R;
import com.capetisoft.patients.dal.DataAccessLogic;
import com.capetisoft.patients.model.template.PatientValue;
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
public class SyncPatientValue implements Callback<ResultService> {
    private DataAccessLogic dal;
    private Context context;
    private boolean isProcess;
    private boolean syncContinue;

    public SyncPatientValue(Context context) {
        this.context = context;
        this.dal = new DataAccessLogic(context);
        this.setIsProcess(false);
        this.syncContinue = false;
    }

    public void init() {
        if(!this.isProcess()) {
            this.syncContinue = true;
            this.setIsProcess(true);
            ArrayList<PatientValue> patientValues = PatientValue.getDirtyRecords(this.getContext());
            String device = Utils.getDevice(this.context);
            for (PatientValue patientValue : patientValues) {
                SyncUpAPIAdapter.getApiService().patientValueUpService(patientValue.getIdentity(), patientValue.getDevice(), patientValue.getKey(), patientValue.getId_templateData(), "_12345" + patientValue.getValue() + "_12345", device, this);
                SystemClock.sleep(1000);
            }
            if(patientValues.size()==0) {
                this.syncContinue = false;
                SyncVisit syncVisit = new SyncVisit(this.getContext());
                syncVisit.init(this);
            }
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
            PatientValue patientValue = new PatientValue(this.getContext(), resultService.getTag());
            patientValue.setStatusSync(resultService.getSyncServer());
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
            SyncVisit syncVisit = new SyncVisit(this.getContext());
            syncVisit.init(this);
        }
    }

    @Override
    public void failure(RetrofitError error) {
        SyncNotifications syncNotifications = new SyncNotifications(this.getContext());
        String msg = this.getContext().getResources().getString(R.string.syncNoCorrect) + "Patient Value";
        syncNotifications.AddSyncNotification(SyncNotifications.SyncStatus.error, msg);
        this.setIsProcess(false);
    }

    public boolean isProcess() {
        return isProcess;
    }

    public void setIsProcess(boolean isProcess) {
        this.isProcess = isProcess;
    }
}
