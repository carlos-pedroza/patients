package com.capetisoft.patients;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.capetisoft.patients.model.Patient;
import com.capetisoft.patients.model.Visit;
import com.capetisoft.patients.model.template.Template;
import com.capetisoft.patients.ui.TemplateShowFragment;
import com.capetisoft.patients.util.Utils;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by carlospedroza on 27/10/15.
 */
public class PatientShow extends ActionBarActivity {

    TextView nameValue;
    TextView genderValue;
    TextView typeValue;
    TextView ageValue;
    TextView dateBirthValue;
    TextView phoneValue;
    TextView emailValue;
    Patient patient;
    Utils.Compare appointmentCompare;
    TextView appointmentLabel;
    ImageButton showApoointmentButton;
    private Template template;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.patient_show);
        Utils.CopyToSharedPreferences(this);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onEditPatient(null);
            }
        });

        nameValue = (TextView) findViewById(R.id.nameValue);
        genderValue = (TextView) findViewById(R.id.genderValue);
        typeValue = (TextView) findViewById(R.id.typeValue);
        ageValue = (TextView) findViewById(R.id.ageValue);
        dateBirthValue = (TextView) findViewById(R.id.dateBirthValue);
        phoneValue = (TextView) findViewById(R.id.phoneValue);
        emailValue = (TextView) findViewById(R.id.emailValue);
        appointmentLabel = (TextView) findViewById(R.id.appointmentLabel);
        showApoointmentButton = (ImageButton) findViewById(R.id.showApoointmentButton);

        Bundle extras = getIntent().getExtras();
        String tag = extras.getString("patientTag");
        this.patient = new Patient(this, tag);

        this.nameValue.setText(this.patient.getName());
        this.genderValue.setText(this.patient.getGenderToString());

        this.ageValue.setText(String.valueOf(Utils.age(this.patient.getDateBirth())));
        this.dateBirthValue.setText(this.patient.getDateBirthToString());
        this.phoneValue.setText(this.patient.getPhone());
        this.emailValue.setText(this.patient.getEmail());

        appointmentCompare = Utils.compareDates(patient.getAppointment(), System.currentTimeMillis());
        showAppointment();
        Template template = new Template(this, this.patient, this.patient.getIdTemplate());
        Utils.setTemplate(template, this);
        this.setTemplate(template);
        this.typeValue.setText(template.getName());
        getSupportFragmentManager().beginTransaction().add(R.id.layoutPatientShow, new TemplateShowFragment()).commit();
    }

    @Override
    public void onBackPressed() {
        //super.onBackPressed();
        goMain();
    }

    public void onBack(View view) {
        goMain();
    }
    private void goMain() {
        Intent i = new Intent(this, Main.class);
        startActivity(i);
    }
    void showAppointment() {
        if(this.patient.getIsAppointment()==1) {
            switch (appointmentCompare) {
                case before:
                    appointmentLabel.setVisibility(View.GONE);
                    appointmentLabel.setBackgroundResource(R.color.transparent);
                    appointmentLabel.setText("");
                    //showApoointmentButton.setImageResource(R.drawable.ic_event_white_24dp);
                    break;
                case after:
                    appointmentLabel.setVisibility(View.VISIBLE);
                    appointmentLabel.setBackgroundResource(R.color.textGreen);
                    //showApoointmentButton.setImageResource(R.drawable.ic_event_green_36dp);
                    Date dAppointment = new Date(patient.getAppointment());
                    SimpleDateFormat sdf = new SimpleDateFormat(getResources().getString(R.string.dateTimeFormat));
                    String dateTimeString = sdf.format(dAppointment.getTime());
                    String _appointmentLabel = dateTimeString;
                    appointmentLabel.setText(_appointmentLabel);
                    break;
                case equal:
                    appointmentLabel.setVisibility(View.VISIBLE);
                    appointmentLabel.setBackgroundResource(R.color.textGreen);
                    //showApoointmentButton.setImageResource(R.drawable.ic_event_green_36dp);
                    Date dAppointment2 = new Date(patient.getAppointment());
                    SimpleDateFormat sdf2 = new SimpleDateFormat(getResources().getString(R.string.timeFormat));
                    String timeString = sdf2.format(dAppointment2.getTime());
                    String _appointmentLabel2 = timeString;
                    appointmentLabel.setText(_appointmentLabel2);
                    break;
            }
        }
        else {
            appointmentLabel.setBackgroundResource(R.color.transparent);
            appointmentLabel.setText("");
            //showApoointmentButton.setImageResource(R.drawable.ic_event_white_24dp);
        }
    }
    public void onApoointmentGo(View view) {
        Intent i = new Intent(this, Appointment.class);
        i.putExtra("backAppointment", "show");
        i.putExtra("patientTag", this.patient.getTag().toString());
        startActivity(i);
    }
    public void onEditPatient(View view) {
        Intent i = new Intent(this, EditPatient.class);
        i.putExtra("patientTag", this.patient.getTag());
        startActivity(i);
    }
    public void onVisitButton(View view) {
        Intent i = new Intent(this, VisitList.class);
        i.putExtra("patientTag", this.patient.getTag());
        startActivity(i);
    }
    public void onDeletePatient(View view) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(getResources().getString(R.string.deletePatientDialog)).setPositiveButton(getResources().getString(R.string.yes), dialogClickListener)
                .setNegativeButton(getResources().getString(R.string.no), dialogClickListener).show();
    }
    DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialog, int which) {
            switch (which){
                case DialogInterface.BUTTON_POSITIVE:
                    Visit visit = new Visit(PatientShow.this);
                    visit.delete(PatientShow.this.patient);
                    PatientShow.this.patient.delete();
                    Toast.makeText(PatientShow.this, PatientShow.this.getResources().getString(R.string.deletePatientDialogOk), Toast.LENGTH_SHORT).show();
                    Intent i = new Intent(PatientShow.this, Main.class);
                    startActivity(i);
                    break;

                case DialogInterface.BUTTON_NEGATIVE:
                    //
                    break;
            }
        }
    };

    public Template getTemplate() {
        return template;
    }

    public void setTemplate(Template template) {
        this.template = template;
    }

    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState) {
        //super.onRestoreInstanceState(savedInstanceState, persistentState);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        //super.onSaveInstanceState(outState, outPersistentState);
    }
}
