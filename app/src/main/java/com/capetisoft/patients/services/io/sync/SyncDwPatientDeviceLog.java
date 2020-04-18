package com.capetisoft.patients.services.io.sync;

import android.app.Activity;

import com.capetisoft.patients.Login;
import com.capetisoft.patients.R;
import com.capetisoft.patients.dal.DataAccessLogic;
import com.capetisoft.patients.model.Patient;
import com.capetisoft.patients.model.Person;
import com.capetisoft.patients.services.io.SyncUpAPIAdapter;
import com.capetisoft.patients.services.io.model.ResultServicePatient;
import com.capetisoft.patients.services.sync.SyncNotifications;
import com.capetisoft.patients.util.Utils;

import java.util.ArrayList;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by carlospedroza on 05/12/15.
 */
public class SyncDwPatientDeviceLog implements Callback<ResultServicePatient> {
    private DataAccessLogic dal;
    private Activity context;
    private Person person;

    public SyncDwPatientDeviceLog(Activity context) {
        this.context = context;
        this.dal = new DataAccessLogic(context);
    }

    public void init(Person person) {
        this.person = person;
        String key = person.getKey();
        if(!key.isEmpty()) {
            String device = Utils.getDevice(this.context);
            long lastSyncServer = 0;

            SyncUpAPIAdapter.getApiService().patientDeviceDwLogService(key, device, lastSyncServer, this);
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
    public void success(ResultServicePatient resultService, Response response) {
        SyncNotifications syncNotifications = new SyncNotifications(this.getContext());
        if(resultService.getResult().equals(resultService.getOK())) {
            String msg = this.getContext().getResources().getString(R.string.syncCorrect) + resultService.getMsg();
            syncNotifications.AddSyncNotification(SyncNotifications.SyncStatus.ok, msg);
            ArrayList<ResultServicePatient.Patient> patients = resultService.getData();
            for (ResultServicePatient.Patient patient : patients  ) {
                Patient patientNew = new Patient(this.getContext(), patient);
                //patientNew.setAppointmentNotice(1);
                long id = patientNew.updateDevice();
                String msg2 = this.getContext().getResources().getString(R.string.syncInsertPatient) + " " + patient.getName() + " : " + patient.getIdentity() + " - " + id;
                syncNotifications.AddSyncNotification(SyncNotifications.SyncStatus.ok, msg2);
            }
            if(patients.size()>0) {
                String key = this.person.getKey();
                syncNotifications.setLastSyncServer(key, patients.get(patients.size() - 1).getServerSync());
            }
            String msg2 = context.getResources().getString(R.string.syncLogin) + resultService.getMsg();
            syncNotifications.AddSyncNotification(SyncNotifications.SyncStatus.warning, msg2);
            Login login = (Login) this.getContext();
            login.doLogin(this.person);
        }
        else if(resultService.getResult().equals(resultService.getWARNING())) {
            Login login = (Login) this.getContext();
            login.noLogin(context.getResources().getString(R.string.syncLoginError));
            String msg = context.getResources().getString(R.string.syncNoCorrect) + resultService.getMsg();
            syncNotifications.AddSyncNotification(SyncNotifications.SyncStatus.error, msg);
        }
        else {
            Login login = (Login) this.getContext();
            login.noLogin(context.getResources().getString(R.string.syncLoginError));
            String msg = context.getResources().getString(R.string.syncNoCorrect) + resultService.getMsg();
            syncNotifications.AddSyncNotification(SyncNotifications.SyncStatus.error, msg);
        }
        SyncDwPatientLog syncDwPatientLog = new SyncDwPatientLog(this.getContext());
        syncDwPatientLog.init(this.person);
    }

    @Override
    public void failure(RetrofitError error) {
        Login login = (Login) this.getContext();
        login.noLogin(context.getResources().getString(R.string.syncLoginError));
        SyncNotifications syncNotifications = new SyncNotifications(this.getContext());
        String msg = this.getContext().getResources().getString(R.string.syncNoCorrect);
        syncNotifications.AddSyncNotification(SyncNotifications.SyncStatus.error, msg);
    }

}
