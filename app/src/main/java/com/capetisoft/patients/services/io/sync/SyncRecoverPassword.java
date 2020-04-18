package com.capetisoft.patients.services.io.sync;

import android.content.Context;

import com.capetisoft.patients.Login;
import com.capetisoft.patients.R;
import com.capetisoft.patients.dal.DataAccessLogic;
import com.capetisoft.patients.model.Person;
import com.capetisoft.patients.services.io.SyncUpAPIAdapter;
import com.capetisoft.patients.services.io.model.ResultService;
import com.capetisoft.patients.services.sync.SyncNotifications;
import com.capetisoft.patients.util.Utils;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by carlospedroza on 05/12/15.
 */
public class SyncRecoverPassword implements Callback<ResultService> {
    private DataAccessLogic dal;
    private Context context;
    private String email;

    public SyncRecoverPassword(Context context, String email) {
        this.context = context;
        this.dal = new DataAccessLogic(context);
        this.email =  email;
    }

    public void init() {

        Person person = new Person(this.getContext());
        person.setEmail(this.email);
        String password = person.generatePassword(8,false);
        person.setPassword(password);
        String device = Utils.getDevice(this.context);

        String language = this.context.getResources().getString(R.string.language);

        SyncUpAPIAdapter.getApiService().recoverPasswordService(person.getEmail(), device, person.getPasswordNoEncode(), person.getPassword(), language, this);
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
        boolean isOk = false;
        String msg = "";
        String data = resultService.getTag();
        if(resultService.getResult().equals(resultService.getOK())) {
            msg = this.context.getResources().getString(R.string.recoverOk) + "," + data;
            syncNotifications.AddSyncNotification(SyncNotifications.SyncStatus.ok, msg);
            isOk = true;
        }
        else {
            if(data.equals("NOT_EXISTS")) {
                msg = this.context.getResources().getString(R.string.recoverErrorNotExists);
            }
            else {
                msg = this.context.getResources().getString(R.string.recoverErrorDB);
            }
            syncNotifications.AddSyncNotification(SyncNotifications.SyncStatus.error, msg);
        }
        Login login = (Login) this.context;
        login.onRecoverPassword(msg, isOk);
    }

    @Override
    public void failure(RetrofitError error) {
        String msg = this.context.getResources().getString(R.string.recoverErrorSync);
        SyncNotifications syncNotifications = new SyncNotifications(this.getContext());
        syncNotifications.AddSyncNotification(SyncNotifications.SyncStatus.error, msg);
        Login login = (Login) this.context;
        login.onRecoverPassword(msg, false);
    }

}
