package com.capetisoft.patients.services.io.sync;

import android.app.Activity;

import com.capetisoft.patients.Login;
import com.capetisoft.patients.R;
import com.capetisoft.patients.dal.DataAccessLogic;
import com.capetisoft.patients.model.Person;
import com.capetisoft.patients.services.io.SyncUpAPIAdapter;
import com.capetisoft.patients.services.io.model.ResultServicePerson;
import com.capetisoft.patients.services.sync.SyncNotifications;

import java.util.ArrayList;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by carlospedroza on 05/12/15.
 */
public class SyncLog implements Callback<ResultServicePerson> {
    private DataAccessLogic dal;
    private Activity context;

    public SyncLog(Activity context) {
        this.context = context;
        this.dal = new DataAccessLogic(context);
    }

    public void init(Person person) {

        SyncUpAPIAdapter.getApiService().logService(person.getEmail(), person.getPassword(), this);

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
    public void success(ResultServicePerson resultService, Response response) {
        SyncNotifications syncNotifications = new SyncNotifications(this.getContext());
        if (resultService.getResult().equals(resultService.getOK())) {
            ArrayList<ResultServicePerson.Person> persons = resultService.getData();
            if (persons.size() > 0) {
                String msg = this.getContext().getResources().getString(R.string.syncLoginProcess) + resultService.getMsg();
                syncNotifications.AddSyncNotification(SyncNotifications.SyncStatus.ok, msg);
                Person person = new Person(this.getContext(), persons.get(0));
                person.insertCloud();
                SyncDwPatientValueLog syncDwPatientValueLog = new SyncDwPatientValueLog(this.context);
                syncDwPatientValueLog.init(person);
            }
            else {
                String msg2 = context.getResources().getString(R.string.syncLoginNoExists) + resultService.getMsg();
                syncNotifications.AddSyncNotification(SyncNotifications.SyncStatus.warning, msg2);
                Login login = (Login) this.getContext();
                login.noLogin(context.getResources().getString(R.string.syncLoginNoExists));
            }
        }
        else if (resultService.getResult().equals(resultService.getWARNING())) {
            Login login = (Login) this.getContext();
            login.noLogin(context.getResources().getString(R.string.syncLoginError));
            String msg = context.getResources().getString(R.string.syncLoginError) + resultService.getMsg();
            syncNotifications.AddSyncNotification(SyncNotifications.SyncStatus.error, msg);
        } else {
            Login login = (Login) this.getContext();
            login.noLogin(context.getResources().getString(R.string.syncLoginError));
            String msg = context.getResources().getString(R.string.syncLoginError) + resultService.getMsg();
            syncNotifications.AddSyncNotification(SyncNotifications.SyncStatus.error, msg);
        }
    }

    @Override
    public void failure(RetrofitError error) {
        Login login = (Login) this.getContext();
        login.noLoginError(context.getResources().getString(R.string.syncLoginError));
        SyncNotifications syncNotifications = new SyncNotifications(this.getContext());
        String msg = this.getContext().getResources().getString(R.string.syncLoginError);
        syncNotifications.AddSyncNotification(SyncNotifications.SyncStatus.error, msg);
    }
}
