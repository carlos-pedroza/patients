package com.capetisoft.patients.services.io.sync;

import android.content.Context;

import com.capetisoft.patients.R;
import com.capetisoft.patients.dal.DataAccessLogic;
import com.capetisoft.patients.model.Person;
import com.capetisoft.patients.services.io.InsertPersonAPIAdapter;
import com.capetisoft.patients.services.io.model.ResultService;

import java.util.ArrayList;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by carlospedroza on 05/12/15.
 */
public class SyncPerson implements Callback<ResultService> {
    private DataAccessLogic dal;
    private Context context;
    boolean isProcess;

    public boolean isProcess() {
        return isProcess;
    }

    public void setIsProcess(boolean isProcess) {
        this.isProcess = isProcess;
    }


    public SyncPerson(Context context) {
        this.context = context;
        this.dal = new DataAccessLogic(context);
        this.setIsProcess(false);
    }

    public void init() {
        if(!this.isProcess()) {
            this.isProcess = true;
            Person personCall = new Person(this.getContext());
            ArrayList<Person> persons = personCall.getDirtyRecords();
            for (Person person : persons) {
                InsertPersonAPIAdapter.getApiService().insertPersonService(person.getEmail(), person.getKey(), person.getName(), person.getPassword(), person.getDevice(), this);
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
        String key = resultService.getTag();
        Person person = new Person(context, key);
        if(resultService.getResult().equals(resultService.getOK())) {
            person.statusSync(resultService,context.getResources().getString(R.string.syncPersonOk));
        }
        else if(resultService.getResult().equals(resultService.getWARNING())) {
            person.statusSync(resultService, resultService.getMsg());
        }
        else {
            person.statusSync(resultService, context.getResources().getString(R.string.syncPersonErrorService));
        }
        this.setIsProcess(false);
    }

    @Override
    public void failure(RetrofitError error) {
        Person person = new Person(this.getContext());
        person.statusSync(new ResultService(ResultService.ERROR_CONS),context.getResources().getString(R.string.syncPersonErrorService));
        this.setIsProcess(false);
    }

}
