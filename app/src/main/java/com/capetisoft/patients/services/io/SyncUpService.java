package com.capetisoft.patients.services.io;

/**
 * Created by carlospedroza on 29/11/15.
 */

import com.capetisoft.patients.services.io.model.ResultService;
import com.capetisoft.patients.services.io.model.ResultServicePatient;
import com.capetisoft.patients.services.io.model.ResultServicePatientValue;
import com.capetisoft.patients.services.io.model.ResultServicePerson;
import com.capetisoft.patients.services.io.model.ResultServiceVisit;

import retrofit.Callback;
import retrofit.http.GET;
import retrofit.http.Query;

/**
 * Created by carlospedroza on 24/11/15.
 */
public interface SyncUpService {
    @GET(ConfigServices.UDVI_1_0)
    void visitUpService(@Query("i") int identity, @Query("d") String device, @Query("k") String key, @Query("ip") int identityPatient, @Query("dv") long dateVisit,  @Query("di") String diagnostic, @Query("dr") String drugs, @Query("c") String comments, @Query("ssd") String serverSyncDevice, @Query("dl") int deleted, Callback<ResultService> resultServiceCallback);

    @GET(ConfigServices.UDPV_1_0)
    void patientValueUpService(@Query("i") int identity, @Query("d") String device, @Query("k") String key, @Query("itd") int idTemplateData, @Query("v") String value, @Query("ssd") String serverSyncDevice, Callback<ResultService> resultServiceCallback);

    @GET(ConfigServices.UDPA_1_0)
    void patientUpService(@Query("i") int identity, @Query("d") String device, @Query("k") String key, @Query("n") String name, @Query("g") String gender,  @Query("it") int idTemplate, @Query("db") long dateBirth, @Query("p") String phone, @Query("e") String email, @Query("ia") int isAppointment, @Query("a") long appointment, @Query("an") int appointmentNotice, @Query("ir") String isRepeat, @Query("rp") int repeatTimes, @Query("sa") String skipAppointment, @Query("ca") String comentAppointment, @Query("iu") int id_user, @Query("ssd") String serverSyncDevice, @Query("dl") int deleted, Callback<ResultService> resultServiceCallback);

    @GET(ConfigServices.DWPA_1_0)
    void patientDwService(@Query("k") String key, @Query("d") String device, @Query("s") long lastServerSync, Callback<ResultServicePatient> resultServicePatientCallback);

    @GET(ConfigServices.DWPV_1_0)
    void patientValueDwService(@Query("k") String key, @Query("d") String device, @Query("s") long lastServerSync, Callback<ResultServicePatientValue> resultServicePatientValueCallback);

    @GET(ConfigServices.DWVI_1_0)
    void visitDwService(@Query("k") String key, @Query("d") String device, @Query("s") long lastServerSync, Callback<ResultServiceVisit> resultServiceVisitCallback);

    @GET(ConfigServices.LOPA_1_0)
    void patientDwLogService(@Query("k") String key, @Query("d") String device, @Query("s") long lastServerSync, Callback<ResultServicePatient> resultServicePatientCallback);

    @GET(ConfigServices.LOPD_1_0)
    void patientDeviceDwLogService(@Query("k") String key, @Query("d") String device, @Query("s") long lastServerSync, Callback<ResultServicePatient> resultServicePatientCallback);

    @GET(ConfigServices.LOPV_1_0)
    void patientValueLogDwService(@Query("k") String key, @Query("d") String device, @Query("s") long lastServerSync, Callback<ResultServicePatientValue> resultServicePatientValueCallback);

    @GET(ConfigServices.LOVI_1_0)
    void visitDwLogService(@Query("k") String key, @Query("d") String device, @Query("s") long lastServerSync, Callback<ResultServiceVisit> resultServiceVisitCallback);

    @GET(ConfigServices.LOVD_1_0)
    void visitDeviceDwLogService(@Query("k") String key, @Query("d") String device, @Query("s") long lastServerSync, Callback<ResultServiceVisit> resultServiceVisitCallback);

    @GET(ConfigServices.LOG_1_0)
    void logService(@Query("e") String email, @Query("p") String password, Callback<ResultServicePerson> resultServicePersonCallback);

    @GET(ConfigServices.GPSS_1_0)
    void recoverPasswordService(@Query("e") String email, @Query("d") String device, @Query("n") String passwordNoEncode, @Query("p") String password, @Query("l") String language, Callback<ResultService> resultServiceCallback);

    @GET(ConfigServices.DWPE_1_0)
    void personDWService(@Query("e") String email, @Query("d") String device, @Query("p") String password, @Query("l") String language, Callback<ResultServicePerson> resultServicePersonCallback);

}
