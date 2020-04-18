package com.capetisoft.patients.services.io;

import retrofit.RestAdapter;

/**
 * Created by carlospedroza on 30/11/15.
 */
public class InsertPersonAPIAdapter {
    private static PersonInsertService API_SERVICE;

    public static PersonInsertService getApiService() {
        if(API_SERVICE==null) {
            RestAdapter adapter = new RestAdapter.Builder()
                    .setEndpoint(ConfigServices.SERVER)
                    .setLogLevel(RestAdapter.LogLevel.BASIC)
                    .build();
            API_SERVICE = adapter.create(PersonInsertService.class);
        }
        return API_SERVICE;
    }
}
