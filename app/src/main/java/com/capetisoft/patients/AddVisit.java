package com.capetisoft.patients;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

import com.capetisoft.patients.model.Patient;
import com.capetisoft.patients.model.Visit;
import com.capetisoft.patients.util.Utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by carlospedroza on 05/11/15.
 */
public class AddVisit extends Activity {

    DatePicker visitDate;
    TimePicker VisitTime;
    EditText diagnostic;
    EditText drugsVisit;
    EditText commentVisit;

    Patient patient;
    Visit visit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_visit);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onAddVisit(null);
            }
        });

        this.visitDate = (DatePicker) findViewById(R.id.visitDate);
        this.VisitTime = (TimePicker) findViewById(R.id.visitTime);
        this.diagnostic = (EditText) findViewById(R.id.diagnosticVisit);
        this.drugsVisit = (EditText) findViewById(R.id.drugsVisit);
        this.commentVisit= (EditText) findViewById(R.id.commentsVisit);

        Bundle extras = getIntent().getExtras();
        String patientTag = extras.getString("patientTag");
        this.patient = new Patient(this, patientTag);
        String visitTag = extras.getString("visitTag");
        if(!visitTag.equals("")) {
            this.visit = new Visit(this, this.patient, visitTag);
            this.set();
        }
        else {
            this.visit = null;
        }
    }
    public void set() {
        Date dateVisit = new Date(this.visit.getDateVisit());
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy");
        int year = Integer.parseInt(sdf.format(dateVisit.getTime()));
        sdf = new SimpleDateFormat("MM");
        int month = Integer.parseInt(sdf.format(dateVisit.getTime()));
        sdf = new SimpleDateFormat("dd");
        int day = Integer.parseInt(sdf.format(dateVisit.getTime()));
        visitDate.updateDate(year, --month, day);
        sdf = new SimpleDateFormat("HH");
        int hours = Integer.parseInt(sdf.format(dateVisit.getTime()));
        VisitTime.setCurrentHour(hours);
        sdf = new SimpleDateFormat("mm");
        int minute = Integer.parseInt(sdf.format(dateVisit.getTime()));
        VisitTime.setCurrentMinute(minute);

        this.diagnostic.setText(this.visit.getDiagnostic());
        this.drugsVisit.setText(this.visit.getDrugs());
        this.commentVisit.setText(this.visit.getComment());
    }
    public void onBackVisit(View view) {
        goBackVisit();
    }
    private void goBackVisit() {
        Intent i = new Intent(this, VisitList.class);
        i.putExtra("patientTag", this.patient.getTag());
        startActivity(i);
    }
    public void onAddVisit(View view) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(visitDate.getYear(), visitDate.getMonth(), visitDate.getDayOfMonth(), VisitTime.getCurrentHour(), VisitTime.getCurrentMinute(), 0);
        if(this.visit==null) {
            Visit visit = new Visit(this, 0,this.patient,calendar.getTimeInMillis(), this.diagnostic.getText().toString(), this.drugsVisit.getText().toString(), commentVisit.getText().toString());
            visit.insert();
        }
        else {
            Visit visit = new Visit(this, this.visit.getIdentity(),this.patient,calendar.getTimeInMillis(), this.diagnostic.getText().toString(), this.drugsVisit.getText().toString(), commentVisit.getText().toString());
            visit.save();
        }

        Toast.makeText(this, Utils.getResString(this, R.string.visitOK), Toast.LENGTH_SHORT).show();
        goBackVisit();
    }
}
