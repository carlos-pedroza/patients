package com.capetisoft.patients;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.capetisoft.patients.model.Backup;
import com.capetisoft.patients.model.Patient;
import com.capetisoft.patients.model.PatientDojo;
import com.capetisoft.patients.model.Person;
import com.capetisoft.patients.model.PersonDojo;
import com.capetisoft.patients.model.PreferencesDojo;
import com.capetisoft.patients.model.Visit;
import com.capetisoft.patients.model.template.PatientValue;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class BackupActivity extends AppCompatActivity implements
        GoogleApiClient.OnConnectionFailedListener, View.OnClickListener{

    // Firebase instance variables
    private FirebaseAuth mFirebaseAuth;
    private FirebaseUser mFirebaseUser;
    private TextView mUsername;
    private TextView txtBackupTime;
    private static final String TAG = "BackupActivity";

    private GoogleApiClient mGoogleApiClient;

    @Override
    public void onBackPressed() {
        goMain();
    }
    public void onBack(View view) {
        goMain();
    }

    private void goMain() {
        Intent i = new Intent(this, Main.class);
        startActivity(i);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_backup);

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this /* FragmentActivity */, this /* OnConnectionFailedListener */)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();

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
                requestBackup();
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

    public void requestBackup() {
        DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which){
                    case DialogInterface.BUTTON_POSITIVE:
                        //Yes button clicked
                        doBackup();
                        break;

                    case DialogInterface.BUTTON_NEGATIVE:
                        //No button clicked
                        break;
                }
            }
        };

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(getString(R.string.do_backup)).setPositiveButton(getString(R.string.yes), dialogClickListener)
                .setNegativeButton(getString(R.string.no), dialogClickListener).show();
    }

    public void onSignOut(View view) {
        mFirebaseAuth.signOut();
        Auth.GoogleSignInApi.signOut(mGoogleApiClient);
        startActivity(new Intent(this, BackupLogin.class));
    }

    @Override
    public void onClick(View v) {

    }

    public void onRestoreBckup(View view) {
        startActivity(new Intent(this, BackupRestore.class));
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        // An unresolvable error has occurred and Google APIs (including Sign-In) will not
        // be available.
        Log.d(TAG, "onConnectionFailed:" + connectionResult);
        Toast.makeText(this, "Google Play Services error.", Toast.LENGTH_SHORT).show();
    }

    private void doBackup() {
        ProgressDialog dialog = ProgressDialog.show(this, "",
                getString(R.string.waitLog), true);
        Backup backup = new Backup(System.currentTimeMillis());
        // Write a message to the database
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference dbBackup = database.getReference(mFirebaseUser.getUid());

        List<PatientDojo> patients = Patient.getAll(this);
        PersonDojo person = Person.getAll(this);
        if(person!=null) {
            backup.setPerson(person);
            backup.setPatients(patients);
            backup.setPreferences(new PreferencesDojo(this));

            backup.setVisits(Visit.getAll(this));
            backup.setPatientValue(PatientValue.getAll(this));

            dbBackup.setValue(backup);

            dialog.hide();
            txtBackupTime.setText(getDateBirthToString(backup.getBackupTime().getValue()));
            Toast.makeText(this, getString(R.string.backupSuccess), Toast.LENGTH_SHORT).show();
        }
        else {
            Toast.makeText(this, getString(R.string.backupError), Toast.LENGTH_SHORT).show();
        }

    }
    public String getDateBirthToString(long _date) {
        Date dDate= new Date(_date);
        SimpleDateFormat sdf = new SimpleDateFormat(getResources().getString(R.string.dateTimeFormat));
        return sdf.format(dDate.getTime());
    }
}