package com.capetisoft.patients;

import android.app.backup.BackupAgent;
import android.app.backup.BackupDataInput;
import android.app.backup.BackupDataOutput;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.ParcelFileDescriptor;
import android.preference.PreferenceManager;

import com.capetisoft.patients.util.Utils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by carlospedroza on 14/05/16.
 */
public class TheBackupAgent extends BackupAgent {
    // The name of the SharedPreferences file
    Context context;
    static final String BD_FILENAME = "/data/data/com.capetisoft.patients/databases/patients";

    static final String PATIENT_BACKUP_KEY ="PATIENTS_TABLE";
    static final String PROPERTIES_BACKUP_KEY="PATIENTS_PROPERTIES";

    // A key to uniquely identify the set of backup data
    static final String FILES_BACKUP_KEY = "myfiles";

    static final String PREFS_DISPLAY = "preferences";
    static final String PREFS_SCORES = "highscores";
    static final String MY_PREFS_BACKUP_KEY = "myprefs";
    

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public void onBackup(ParcelFileDescriptor oldState, BackupDataOutput data, ParcelFileDescriptor newState) throws IOException {
        SharedPreferences preferencesDefault = PreferenceManager.getDefaultSharedPreferences(this.getBaseContext());
        String preferenceNotification = preferencesDefault.getString("notifications", Utils.MILLIS_10_MINUT);
        String preferenceSynchronization = preferencesDefault.getBoolean("synchronization", true) ? "true" : "false";

        SharedPreferences preferences = this.getBaseContext().getSharedPreferences("PatientPreferences", this.getBaseContext().MODE_PRIVATE);

        String currentIdPatient = preferences.getString("currentIdPatient", "0");
        String currentIdTemplate = preferences.getString("currentIdTemplate", "0");
        String currentKey = preferences.getString("currentKey", "");
        String currentPatientQueryType = preferences.getString("currentPatientQueryType", "");
        String currentPatientSearchQuery = preferences.getString("currentPatientSearchQuery", "");
        String person_key = preferences.getString("person_key", "");
        String email = preferences.getString("email", "");
        String password = preferences.getString("password", "");

        // Get the oldState input stream
        FileInputStream instream = new FileInputStream(oldState.getFileDescriptor());
        DataInputStream in = new DataInputStream(instream);

        try {
            // Get the last modified timestamp from the state file and data file
            long stateModified = in.readLong();
            long fileModified = System.currentTimeMillis();

            if (stateModified != fileModified) {
                ByteArrayOutputStream bufStream = new ByteArrayOutputStream();
                DataOutputStream outWriter = new DataOutputStream(bufStream);
                // Write structured data
                outWriter.writeUTF(preferenceNotification);
                outWriter.writeUTF(preferenceSynchronization);
                outWriter.writeUTF(currentIdPatient);
                outWriter.writeUTF(currentIdTemplate);
                outWriter.writeUTF(currentKey);
                outWriter.writeUTF(currentPatientQueryType);
                outWriter.writeUTF(currentPatientSearchQuery);
                outWriter.writeUTF(person_key);
                outWriter.writeUTF(email);
                outWriter.writeUTF(password);
                // Send the data to the Backup Manager via the BackupDataOutput
                byte[] buffer = bufStream.toByteArray();
                int len = buffer.length;
                data.writeEntityHeader(PROPERTIES_BACKUP_KEY, len);
                data.writeEntityData(buffer, len);

                FileOutputStream outstream = new FileOutputStream(newState.getFileDescriptor());
                DataOutputStream out = new DataOutputStream(outstream);

                out.writeLong(fileModified);
            } else {
                // Don't back up because the file hasn't changed
                return;
            }
        } catch (IOException e) {
            // Unable to read state file... be safe and do a backup
        }
    }

    @Override
    public void onRestore(BackupDataInput data, int appVersionCode, ParcelFileDescriptor newState) throws IOException {
        // There should be only one entity, but the safest
        // way to consume it is using a while loop
        while (data.readNextHeader()) {
            String key = data.getKey();
            int dataSize = data.getDataSize();

            // If the key is ours (for saving top score). Note this key was used when
            // we wrote the backup entity header
            if (PROPERTIES_BACKUP_KEY.equals(key)) {
                // Create an input stream for the BackupDataInput
                byte[] dataBuf = new byte[dataSize];
                data.readEntityData(dataBuf, 0, dataSize);
                ByteArrayInputStream baStream = new ByteArrayInputStream(dataBuf);
                DataInputStream in = new DataInputStream(baStream);

                String preferenceNotification = in.readUTF();
                String preferenceSynchronization = in.readUTF();

                String currentIdPatient = in.readUTF();
                String currentIdTemplate = in.readUTF();
                String currentKey = in.readUTF();
                String currentPatientQueryType = in.readUTF();
                String currentPatientSearchQuery = in.readUTF();
                String person_key = in.readUTF();
                String email = in.readUTF();
                String password =in.readUTF();

                SharedPreferences preferencesDefault = PreferenceManager.getDefaultSharedPreferences(this.getBaseContext());
                SharedPreferences preferences = this.getBaseContext().getSharedPreferences("PatientPreferences", this.getBaseContext().MODE_PRIVATE);

                SharedPreferences.Editor editorDefault = preferencesDefault.edit();
                editorDefault.putString("preferenceNotification", String.valueOf(preferenceNotification));
                editorDefault.putString("preferenceSynchronization", String.valueOf(preferenceSynchronization));
                editorDefault.commit();

                SharedPreferences.Editor editor = preferences.edit();
                editor.putString("currentIdPatient", String.valueOf(currentIdPatient));
                editor.putString("currentIdTemplate", String.valueOf(currentIdTemplate));
                editor.putString("currentKey", String.valueOf(currentKey));
                editor.putString("currentPatientQueryType", String.valueOf(currentPatientQueryType));
                editor.putString("currentPatientSearchQuery", String.valueOf(currentPatientSearchQuery));
                editor.putString("person_key", String.valueOf(person_key));
                editor.putString("email", String.valueOf(email));
                editor.putString("password", String.valueOf(password));
                editor.commit();

            } else {
                // We don't know this entity key. Skip it. (Shouldn't happen.)
                data.skipEntityData();
            }
        }

        // Finally, write to the state blob (newState) that describes the restored data
        FileOutputStream outstream = new FileOutputStream(newState.getFileDescriptor());
        DataOutputStream out = new DataOutputStream(outstream);
        out.writeLong(System.currentTimeMillis());
    }

    // Allocate a helper and add it to the backup agent

}
