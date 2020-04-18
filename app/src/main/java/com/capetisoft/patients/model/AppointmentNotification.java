package com.capetisoft.patients.model;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.NotificationCompat;

import com.capetisoft.patients.Main;
import com.capetisoft.patients.R;
import com.capetisoft.patients.util.Utils;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by carlospedroza on 22/10/15.
 */
public class AppointmentNotification {
    Context context;
    NotificationManager nm;

    public AppointmentNotification(Context _context) {
        this.context = _context;
        nm = (NotificationManager)_context.getSystemService(_context.NOTIFICATION_SERVICE);
    }
    public void send() {
        try {
            SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this.context);
            String preferenceNotification = preferences.getString("notifications", Utils.MILLIS_10_MINUT);
            if (!preferenceNotification.equals(Utils.NO_NOTIFICATE)) {
                Patient patients = new Patient(this.context, System.currentTimeMillis(), Utils.getTimeMillis(preferenceNotification));
                for(Patient patient : patients.getList()) {

                    long _appointment = patient.getAppointment();
                    Date dAppointment = new Date(patient.getAppointment());
                    SimpleDateFormat sdf = new SimpleDateFormat(this.context.getResources().getString(R.string.dateTimeFormat));
                    String dateTimeString = sdf.format(dAppointment.getTime());
                    String textNotificatonTitle = Utils.getResString(this.context, R.string.appointmentNotificationTitle) + " " +
                            patient.getName();
                    String textNotification = "( " + dateTimeString + " )";


                    //Notification _notification = new Notification(R.drawable.ic_event_green_24dp, textNotification, _appointment);
                    Notification notification = new NotificationCompat.Builder(this.context)
                            .setContentTitle(textNotificatonTitle)
                            .setContentText(textNotification)
                            .setSmallIcon(R.drawable.ic_event_green_24dp)
                            .setWhen(_appointment)
                            .build();
                    notification.contentIntent = PendingIntent.getActivity(this.context, 0, new Intent(this.context, Main.class), 0);

                    long[] vibrate = {0, 700, 200, 400, 50, 400};
                    notification.vibrate = vibrate;
                    notification.defaults |= Notification.DEFAULT_SOUND;
                    notification.defaults |= Notification.DEFAULT_LIGHTS;
                    nm.notify(patient.getId(), notification);
                    patient.setAppointmentNotice(0);
                    patients.checkAppointmetNotify(patient);

                }

            }
        }
        catch(Exception ex) {

        }
    }
}
