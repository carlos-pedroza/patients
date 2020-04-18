package com.capetisoft.patients.services;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

/**
 * Created by carlospedroza on 22/10/15.
 */
public class PatientReceptor extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        context.startService(new Intent(context, PatientSyncAndNotifications.class));
    }
}
