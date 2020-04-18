package com.capetisoft.patients;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.TimePicker;

import com.capetisoft.patients.model.Patient;
import com.capetisoft.patients.util.Utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by carlospedroza on 17/10/15.
 */
public class Appointment extends Activity {

    TextView lPatientName;
    EditText appointmentComments;
    DatePicker appointmentDate;
    TimePicker appointmentTime;
    LinearLayout labelAppointmentLayout;
    TextView labelAppointment;
    Patient patient;
    Utils.Compare appointmentCompare;
    String backAppointment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.appointment);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onSetAppointment(null);
            }
        });

        Bundle extra = getIntent().getExtras();
        String patientTag = extra.getString("patientTag");
        this.backAppointment = extra.getString("backAppointment");
        patient = new Patient(this, patientTag);
        lPatientName = (TextView) findViewById(R.id.lPatientName);
        lPatientName.setText(patient.getName());

        labelAppointmentLayout = (LinearLayout) findViewById(R.id.labelAppointmentLayout);
        labelAppointment = (TextView) findViewById(R.id.labelAppointment);
        appointmentDate = (DatePicker) findViewById(R.id.appointmentDate);
        appointmentTime = (TimePicker) findViewById(R.id.appointmentTime);
        appointmentComments = (EditText) findViewById(R.id.appointmentComments);
        appointmentCompare = Utils.compareDates(patient.getAppointment(), System.currentTimeMillis());

        if(patient.getIsAppointment()==1&&!(appointmentCompare== Utils.Compare.before)) {
            Date dAppointment = new Date(patient.getAppointment());
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy");
            int year = Integer.parseInt(sdf.format(dAppointment.getTime()));
            sdf = new SimpleDateFormat("MM");
            int month = Integer.parseInt(sdf.format(dAppointment.getTime()));
            sdf = new SimpleDateFormat("dd");
            int day = Integer.parseInt(sdf.format(dAppointment.getTime()));
            appointmentDate.updateDate(year,--month, day);
            sdf = new SimpleDateFormat("HH");
            int hours = Integer.parseInt(sdf.format(dAppointment.getTime()));
            appointmentTime.setCurrentHour(hours);
            sdf = new SimpleDateFormat("mm");
            int minute = Integer.parseInt(sdf.format(dAppointment.getTime()));
            appointmentTime.setCurrentMinute(minute);
            appointmentComments.setText(patient.getCommentAppointment());
        }

        showAppointment();

    }
    void showAppointment() {
        if(patient.getIsAppointment()==1) {
            switch (appointmentCompare) {
                case before:
                    labelAppointmentLayout.setVisibility(View.GONE);
                    break;
                case after:
                    labelAppointmentLayout.setVisibility(View.VISIBLE);
                    Date dAppointment = new Date(patient.getAppointment());
                    SimpleDateFormat sdf = new SimpleDateFormat(getResources().getString(R.string.dateTimeFormat));
                    String dateTimeString = sdf.format(dAppointment.getTime());
                    String _appointmentLabel = getResources().getString(R.string.labelAppointment) + " " + dateTimeString;
                    labelAppointment.setText(_appointmentLabel);
                    break;
                case equal:
                    labelAppointmentLayout.setVisibility(View.VISIBLE);
                    Date dAppointment2 = new Date(patient.getAppointment());
                    SimpleDateFormat sdf2 = new SimpleDateFormat(getResources().getString(R.string.timeFormat));
                    String timeString = sdf2.format(dAppointment2.getTime());
                    String _appointmentLabel2 = getResources().getString(R.string.labelAppointment) + " " + timeString;
                    labelAppointment.setText(_appointmentLabel2);
                    break;
            }
        }
        else {
            labelAppointmentLayout.setVisibility(View.GONE);
        }
    }
    public void onSetAppointment(View view) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(appointmentDate.getYear(), appointmentDate.getMonth(), appointmentDate.getDayOfMonth(), appointmentTime.getCurrentHour(), appointmentTime.getCurrentMinute(), 0);
        long appointment = calendar.getTimeInMillis();
        patient.setIsAppointment(1);
        patient.setAppointment(appointment);
        patient.setAppointmentNotice(1);
        patient.setCommentAppointment(appointmentComments.getText().toString());
        patient.setServerSync(0);
        patient.save();

        goBack();
    }
    public void onDelAppointment(View view) {
        patient.setIsAppointment(0);
        patient.setAppointment(0);
        patient.setAppointmentNotice(0);
        patient.setCommentAppointment("");
        patient.setServerSync(0);
        patient.save();
        goBack();
    }
    public void onBack(View view) {
        goBack();
    }
    private void goBack() {
        if(this.backAppointment.equals("main")) {
            goMain();
        }
        else {
            goPatientShow();
        }
    }
    private void goMain() {
        Intent i = new Intent(this, Main.class);
        startActivity(i);
    }
    private void goPatientShow() {
        Intent i = new Intent(this, PatientShow.class);
        i.putExtra("patientTag", this.patient.getTag().toString());
        startActivity(i);
    }
}
