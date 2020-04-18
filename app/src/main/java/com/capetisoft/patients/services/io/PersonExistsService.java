package com.capetisoft.patients.services.io;

import com.capetisoft.patients.services.io.model.ResultServiceExPe;

import retrofit.Callback;
import retrofit.http.GET;
import retrofit.http.Query;

/**
 * Created by carlospedroza on 01/12/15.
 */
public interface PersonExistsService {
    @GET(ConfigServices.EXPE_1_0)
    void existsPersonService(@Query("e") String email, Callback<ResultServiceExPe> resultExistsServiceCallback);
}
