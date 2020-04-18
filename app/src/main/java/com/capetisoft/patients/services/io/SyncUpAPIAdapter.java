package com.capetisoft.patients.services.io;

import com.squareup.okhttp.OkHttpClient;

import java.util.concurrent.TimeUnit;

import retrofit.RestAdapter;
import retrofit.client.OkClient;

/**
 * Created by carlospedroza on 30/11/15.
 */
public class SyncUpAPIAdapter {
    private static SyncUpService API_SERVICE;

    public static SyncUpService getApiService() {
        final OkHttpClient okHttpClient = new OkHttpClient();
        okHttpClient.setReadTimeout(180, TimeUnit.SECONDS);
        okHttpClient.setConnectTimeout(180, TimeUnit.SECONDS);
        if(API_SERVICE==null) {
            RestAdapter adapter = new RestAdapter.Builder()
                    .setEndpoint(ConfigServices.SERVER)
                    .setLogLevel(RestAdapter.LogLevel.BASIC)
                    .setClient(new OkClient(okHttpClient))
                    .build();
            API_SERVICE = adapter.create(SyncUpService.class);
        }
        return API_SERVICE;
    }
}
