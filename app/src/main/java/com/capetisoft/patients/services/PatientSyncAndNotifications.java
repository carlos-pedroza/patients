package com.capetisoft.patients.services;

import android.app.IntentService;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.IBinder;
import android.os.SystemClock;
import android.preference.PreferenceManager;

import com.capetisoft.patients.model.AppointmentNotification;
import com.capetisoft.patients.model.template.Template;

/**
 * Created by carlospedroza on 22/10/15.
 */
public class PatientSyncAndNotifications extends IntentService {

    public PatientSyncAndNotifications() {
        super("PatientSyncAndNotifications");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        onStart(intent, startId);
        //return super.onStartCommand(intent, flags, startId);
        return START_STICKY;
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        //android.os.Debug.waitForDebugger();
        //SyncNotifications syncNotifications = new SyncNotifications(this.getBaseContext());
        //SyncDwPatientValue syncDwPatientValue = new SyncDwPatientValue(this.getBaseContext());
        //SyncPerson syncPerson = new SyncPerson(this.getBaseContext());
        //SyncPatientValue syncPatientValue = new SyncPatientValue(this.getBaseContext());
        while(true) {
            generateNotificationAppointment(intent);
            generateTemplate(intent);
            /*if(this.isSynchronize()) {
                if (Utils.conectWifi(this.getBaseContext()) || Utils.conectMovilNet(this.getBaseContext())) {
                    syncPerson.init();
                    syncPatientValue.init();
                    syncDwPatientValue.init();
                    syncNotifications.clearSyncNotification();
                } else {
                    String msg = this.getBaseContext().getResources().getString(R.string.syncNoConnection);
                    syncNotifications.AddSyncNotification(SyncNotifications.SyncStatus.error, msg);
                }
            }
            else {
                String msg = this.getBaseContext().getResources().getString(R.string.syncOff);
                syncNotifications.AddSyncNotification(SyncNotifications.SyncStatus.warning, msg);
            }*/
            SystemClock.sleep(60000);
        }
    }
    private void generateNotificationAppointment(Intent intent) {
        AppointmentNotification appointmentNotification = new AppointmentNotification(this.getBaseContext());
        appointmentNotification.send();
    }
    private void generateTemplate(Intent intent) {
        Template.generate(this.getBaseContext());
    }

    private boolean isSynchronize() {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this.getBaseContext());
        boolean preferenceSynchronization = preferences.getBoolean("synchronization", true);
        return preferenceSynchronization;
    }
}
