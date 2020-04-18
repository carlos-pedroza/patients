package com.capetisoft.patients.services.io;

import retrofit.RestAdapter;

/**
 * Created by carlospedroza on 01/12/15.
 */
public class PersonExistsAdapter {
    private static PersonExistsService API_SERVICE;

    public static PersonExistsService getApiService() {
        if(API_SERVICE==null) {
            RestAdapter adapter = new RestAdapter.Builder()
                    .setEndpoint(ConfigServices.SERVER)
                    .setLogLevel(RestAdapter.LogLevel.BASIC)
                    .build();
            API_SERVICE = adapter.create(PersonExistsService.class);
        }
        return API_SERVICE;
    }
}
