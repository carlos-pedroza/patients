package com.capetisoft.patients.services.io.sync;

import android.content.Context;
import android.os.SystemClock;

import com.capetisoft.patients.R;
import com.capetisoft.patients.dal.DataAccessLogic;
import com.capetisoft.patients.model.Patient;
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
public class SyncPatient implements Callback<ResultService> {
    private DataAccessLogic dal;
    private Context context;
    private SyncPatientValue syncPatientValue;

    public SyncPatient(Context context) {
        this.context = context;
        this.dal = new DataAccessLogic(context);
    }

    public void init(SyncPatientValue syncPatientValue) {
        this.setSyncPatientValue(syncPatientValue);
        ArrayList<Patient> patients = Patient.getDirtyRecords(this.getContext());
        String device = Utils.getDevice(this.context);
        for ( Patient patient : patients) {
            SyncUpAPIAdapter.getApiService().patientUpService(patient.getIdentity(), patient.getDevice(), patient.getKey(), "_12345" + patient.getName() + "_12345", patient.getGender(), patient.getIdTemplate(), patient.getDateBirth(), patient.getPhone(), "_12345" + patient.getEmail() + "_12345", patient.getIsAppointment(), patient.getAppointment(), patient.getAppointmentNotice(), patient.getIsRepeatTime(), patient.getRepeatTimes(), patient.getSkipAppointment(), "_12345" + patient.getCommentAppointment() + "_12345", patient.getIdUser(), device, patient.getDeleted(), this);
            SystemClock.sleep(1000);
        }
        if(patients.size()==0) {
            this.getSyncPatientValue().setIsProcess(false);
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
            Patient patient = new Patient(this.getContext(), resultService.getTag());
            patient.setStatusSync(resultService.getSyncServer());
        }
        else if(resultService.getResult().equals(resultService.getWARNING())) {
            String msg = context.getResources().getString(R.string.syncNoCorrect) + resultService.getMsg();
            syncNotifications.AddSyncNotification(SyncNotifications.SyncStatus.error, msg);
        }
        else {
            String msg = context.getResources().getString(R.string.syncNoCorrect) + resultService.getMsg();
            syncNotifications.AddSyncNotification(SyncNotifications.SyncStatus.error, msg);
        }
        this.getSyncPatientValue().setIsProcess(false);
    }

    @Override
    public void failure(RetrofitError error) {
        SyncNotifications syncNotifications = new SyncNotifications(this.getContext());
        String msg = this.getContext().getResources().getString(R.string.syncNoCorrect) + " Patient";
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
