package com.capetisoft.patients.services.io;

/**
 * Created by carlospedroza on 29/11/15.
 */

import com.capetisoft.patients.services.io.model.ResultService;

import retrofit.Callback;
import retrofit.http.GET;
import retrofit.http.Query;

/**
 * Created by carlospedroza on 24/11/15.
 */
public interface PersonInsertService {
    @GET(ConfigServices.PEIN_1_0)
    void insertPersonService(@Query("e") String email, @Query("k") String key, @Query("n") String name, @Query("p") String password, @Query("d") String device, Callback<ResultService> resultServiceCallback);
}
