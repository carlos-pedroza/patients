package com.capetisoft.patients.services.io.sync;

import android.app.Activity;

import com.capetisoft.patients.Login;
import com.capetisoft.patients.R;
import com.capetisoft.patients.dal.DataAccessLogic;
import com.capetisoft.patients.model.Person;
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
public class SyncDwPatientValueLog implements Callback<ResultServicePatientValue> {
    private DataAccessLogic dal;
    private Activity context;
    Person person;

    public SyncDwPatientValueLog(Activity context) {
        this.context = context;
        this.dal = new DataAccessLogic(context);
    }

    public void init(Person person) {
        this.person = person;
        String key = person.getKey();
        if (!key.isEmpty()) {
            String device = Utils.getDevice(this.context);
            long lastSyncServer = 0;

            person.deleteAllLog();
            SyncUpAPIAdapter.getApiService().patientValueLogDwService(key, device, lastSyncServer, this);
        }

    }

    public DataAccessLogic getDal() {
        return dal;
    }

    public void setDal(DataAccessLogic dal) {
        this.dal = dal;
    }

    public Activity getContext() {
        return context;
    }

    public void setContext(Activity context) {
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
                String key = this.person.getKey();
                syncNotifications.setLastSyncServerPV(key, patientValues.get(patientValues.size() - 1).getServerSync());
            }
        } else if (resultService.getResult().equals(resultService.getWARNING())) {
            String msg = context.getResources().getString(R.string.syncNoCorrect) + resultService.getMsg();
            syncNotifications.AddSyncNotification(SyncNotifications.SyncStatus.error, msg);
            Login login = (Login) this.getContext();
            login.noLogin(context.getResources().getString(R.string.syncLoginError));
        } else {
            String msg = context.getResources().getString(R.string.syncNoCorrect) + resultService.getMsg();
            syncNotifications.AddSyncNotification(SyncNotifications.SyncStatus.error, msg);
            Login login = (Login) this.getContext();
            login.noLogin(context.getResources().getString(R.string.syncLoginError));
        }

        SyncDwVisitDeviceLog syncDwVisitDeviceLog = new SyncDwVisitDeviceLog(this.getContext());
        syncDwVisitDeviceLog.init(this.person);
    }

    @Override
    public void failure(RetrofitError error) {
        SyncNotifications syncNotifications = new SyncNotifications(this.getContext());
        String msg = this.getContext().getResources().getString(R.string.syncNoCorrect);
        syncNotifications.AddSyncNotification(SyncNotifications.SyncStatus.error, msg);
        Login login = (Login) this.getContext();
        login.noLogin(context.getResources().getString(R.string.syncLoginError));
    }

}
