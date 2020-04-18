package com.capetisoft.patients;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.capetisoft.patients.model.GlobalRepository;
import com.capetisoft.patients.model.Patient;
import com.capetisoft.patients.model.PatientDojo;
import com.capetisoft.patients.model.Person;
import com.capetisoft.patients.model.PersonDojo;
import com.capetisoft.patients.model.PreferencesDojo;
import com.capetisoft.patients.model.Visit;
import com.capetisoft.patients.model.VisitDojo;
import com.capetisoft.patients.model.template.PatientValue;
import com.capetisoft.patients.model.template.PatientValueDojo;
import com.capetisoft.patients.services.sync.SyncNotifications;
import com.capetisoft.patients.util.Utils;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.Date;

public class BackupRestore extends AppCompatActivity {
    private FirebaseAuth mFirebaseAuth;
    private FirebaseUser mFirebaseUser;

    private TextView mUsername;
    private TextView txtBackupTime;
    private static final String TAG = "BackupRestore";
    ProgressDialog dialog;
    PersonDojo person;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_backup_restore);

        mUsername = (TextView) findViewById(R.id.mUsername);
        txtBackupTime =  (TextView) findViewById(R.id.backupTime);
        // Initialize Firebase Auth
        mFirebaseAuth = FirebaseAuth.getInstance();
        mFirebaseUser = mFirebaseAuth.getCurrentUser();
        if (mFirebaseUser == null) {
            // Not signed in, launch the Sign In activity
            startActivity(new Intent(this, BackupLogin.class));
            finish();
            return;
        } else {
            mUsername.setText(mFirebaseUser.getDisplayName());
        }
        readBackupTime();
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog = ProgressDialog.show(BackupRestore.this, "",
                        getString(R.string.waitLog), true);
                doRestorePreferences();
            }
        });
    }
    @Override
    public void onBackPressed() {
        goMain();
    }
    public void onBack(View view) {
        goMain();
    }

    private void goMain() {
        Intent i = new Intent(this, BackupActivity.class);
        startActivity(i);
    }

    private void doRestorePreferences() {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference dbBackup = database.getReference(mFirebaseUser.getUid());
        Query queryRef = dbBackup.child("preferences").limitToFirst(1);
        queryRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot child : dataSnapshot.getChildren()) {
                    PreferencesDojo preferencesDojo = child.getValue(PreferencesDojo.class);
                    if(preferencesDojo==null)
                    {
                        return;
                    }
                    Utils.setPreferences(BackupRestore.this, preferencesDojo);
                }
                doRestorePerson();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }
    private void doRestorePerson() {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference dbBackup = database.getReference(mFirebaseUser.getUid());
        Query queryRef = dbBackup.child("person");
        queryRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                boolean bnd = true;
                for (DataSnapshot child : dataSnapshot.getChildren()) {
                    if(bnd) {
                        Person.clearAll(BackupRestore.this);
                        bnd=false;
                    }
                    PersonDojo personDojo = child.getValue(PersonDojo.class);
                    if(personDojo==null) {
                        return;
                    }
                    Person.insertBackup(BackupRestore.this, personDojo);
                    person = personDojo;
                }
                if(!bnd) {
                    doRestorePatientValue();
                }
                else {
                    finishRestore(true);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
    private void doRestorePatientValue() {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference dbBackup = database.getReference(mFirebaseUser.getUid());
        Query queryRef = dbBackup.child("patientValue");
        queryRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                boolean bnd = true;
                for (DataSnapshot child : dataSnapshot.getChildren()) {
                    if(bnd) {
                        PatientValue.clearAll(BackupRestore.this);
                        bnd= false;
                    }
                    PatientValueDojo patientValueDojo = child.getValue(PatientValueDojo.class);
                    if(patientValueDojo==null) {
                        return;
                    }
                    PatientValue.insertBackup(BackupRestore.this, patientValueDojo);
                }
                doRestoreVisit();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
    private void doRestoreVisit() {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference dbBackup = database.getReference(mFirebaseUser.getUid());
        Query queryRef = dbBackup.child("visits");
        queryRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                boolean bnd = true;
                for (DataSnapshot child : dataSnapshot.getChildren()) {
                    if(bnd) {
                        Visit.clearAll(BackupRestore.this);
                        bnd=false;
                    }
                    VisitDojo visitDojo = child.getValue(VisitDojo.class);
                    if(visitDojo==null) {
                        return;
                    }
                    Visit.insertBackup(BackupRestore.this, visitDojo);
                }
                doRestorePatient();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
    private void doRestorePatient() {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference dbBackup = database.getReference(mFirebaseUser.getUid());
        Query queryRef = dbBackup.child("patients");
        queryRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                boolean bnd = true;
                for (DataSnapshot child : dataSnapshot.getChildren()) {
                    if(bnd) {
                        Patient.clearAll(BackupRestore.this);
                        bnd=false;
                    }
                    PatientDojo patientDojo = child.getValue(PatientDojo.class);
                    if(patientDojo==null) {
                        return;
                    }
                    Patient.insertBackup(BackupRestore.this, patientDojo);
                }
                finishRestore();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
    private void readBackupTime() {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference dbBackup = database.getReference(mFirebaseUser.getUid());
        Query queryRef = dbBackup.child("backupTime").limitToFirst(1);
        queryRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot snapshot, String previousChild) {
                if(snapshot==null) {
                    return;
                }
                long backupTime = snapshot.getValue(Long.class);
                txtBackupTime.setText(getDateBirthToString(backupTime));
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
    public String getDateBirthToString(long _date) {
        Date dDate= new Date(_date);
        SimpleDateFormat sdf = new SimpleDateFormat(getResources().getString(R.string.dateTimeFormat));
        return sdf.format(dDate.getTime());
    }
    private void finishRestore() {
        finishRestore(false);
    }
    private void finishRestore(boolean error) {
        if(!error) {
            SharedPreferences preferences = this.getSharedPreferences("PatientPreferences", this.MODE_PRIVATE);
            SharedPreferences.Editor editor = preferences.edit();
            editor.putString(Utils.PREFERENCES_EMAIL, person.getEmail());
            SyncNotifications syncNotifications = new SyncNotifications(this);
            syncNotifications.setcurrentKey(person.getKey());
            editor.putString(Utils.PREFERENCES_KEY, person.getKey());
            editor.putString(Utils.PREFERENCES_PASSWORD, "");
            editor.commit();
            GlobalRepository.PersonLog = Person.readKey(this);
            dialog.hide();
            Toast.makeText(this, getString(R.string.restoreOk), Toast.LENGTH_LONG).show();
            Intent i = new Intent(this, Main.class);
            startActivity(i);
        }
        else {
            dialog.hide();
            Toast.makeText(this, getString(R.string.restoreError), Toast.LENGTH_LONG).show();
        }
    }
}
