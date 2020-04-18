package com.capetisoft.patients.services.io.sync;

import android.content.Context;

import com.capetisoft.patients.R;
import com.capetisoft.patients.dal.DataAccessLogic;
import com.capetisoft.patients.model.template.PatientValue;
import com.capetisoft.patients.services.io.SyncUpAPIAdapter;
import com.capetisoft.patients.services.io.model.ResultServicePatientValue;
import com.capetisoft.patients.services.sync.SyncNotifications;
import com.capetisoft.patients.util.Utils;

import java.util.ArrayList;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by carlospedroza on 05/12/15.
 */
public class SyncDwPatientValue implements Callback<ResultServicePatientValue> {
    private DataAccessLogic dal;
    private Context context;
    private boolean isProcess;
    private boolean syncContinue;

    public boolean isProcess() {
        return isProcess;
    }

    public void setIsProcess(boolean isProcess) {
        this.isProcess = isProcess;
    }

    public SyncDwPatientValue(Context context) {
        this.context = context;
        this.dal = new DataAccessLogic(context);
        this.isProcess = false;
        this.syncContinue = false;
    }

    public void init() {
        if(!this.isProcess()) {
            SyncNotifications syncNotifications = new SyncNotifications(this.getContext());
            String key = syncNotifications.getcurrentKey();
            if (!key.isEmpty()) {
                this.syncContinue = true;
                this.isProcess = true;
                String device = Utils.getDevice(this.context);
                long lastSyncServer = syncNotifications.getLastSyncServerPV(key);

                SyncUpAPIAdapter.getApiService().patientValueDwService(key, device, lastSyncServer, this);
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
    public void success(ResultServicePatientValue resultService, Response response) {
        SyncNotifications syncNotifications = new SyncNotifications(this.getContext());
        if (resultService.getResult().equals(resultService.getOK())) {
            String msg = this.getContext().getResources().getString(R.string.syncCorrect) + resultService.getMsg();
            syncNotifications.AddSyncNotification(SyncNotifications.SyncStatus.ok, msg);
            ArrayList<ResultServicePatientValue.PatientValue> patientValues = resultService.getData();
            for (ResultServicePatientValue.PatientValue patientValue : patientValues) {

                PatientValue patientValueNew = new PatientValue(this.getContext(), patientValue);
                patientValueNew.update();

                String msg2 = this.getContext().getResources().getString(R.string.syncInsertPatientValue) + " " + patientValue.getValue() + " : " + patientValue.getIdentity();
                syncNotifications.AddSyncNotification(SyncNotifications.SyncStatus.ok, msg2);
            }
            if (patientValues.size() > 0) {
                String key = syncNotifications.getcurrentKey();
                syncNotifications.setLastSyncServerPV(key, patientValues.get(patientValues.size() - 1).getServerSync());
            }
        } else if (resultService.getResult().equals(resultService.getWARNING())) {
            String msg = context.getResources().getString(R.string.syncNoCorrect) + resultService.getMsg();
            syncNotifications.AddSyncNotification(SyncNotifications.SyncStatus.error, msg);
        } else {
            String msg = context.getResources().getString(R.string.syncNoCorrect) + resultService.getMsg();
            syncNotifications.AddSyncNotification(SyncNotifications.SyncStatus.error, msg);
        }

        SyncDwVisit syncDwVisit = new SyncDwVisit(this.getContext());
        syncDwVisit.init(this);
    }

    @Override
    public void failure(RetrofitError error) {
        SyncNotifications syncNotifications = new SyncNotifications(this.getContext());
        String msg = this.getContext().getResources().getString(R.string.syncNoCorrect);
        syncNotifications.AddSyncNotification(SyncNotifications.SyncStatus.error, msg);
        this.setIsProcess(false);
    }

}
