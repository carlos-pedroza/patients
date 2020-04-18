package com.capetisoft.patients;

import android.app.ListActivity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.Toast;

import com.capetisoft.patients.model.Patient;
import com.capetisoft.patients.model.Visit;

/**
 * Created by carlospedroza on 05/11/15.
 */
public class VisitList extends ListActivity {
    Patient patient;
    View viewVisit;
    VisitList activityVisit;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.visit);

        activityVisit = this;

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onAddVisit(null);
            }
        });

        Bundle extras = getIntent().getExtras();
        String tag = extras.getString("patientTag");
        this.patient = new Patient(this, tag);
        setListAdapter(new VisitListAdapter(this, new Visit(this, this.patient).getList()));
    }
    public void onAddVisit(View view) {
        //this.msgBox.hide();
        Intent i = new Intent(this, AddVisit.class);
        i.putExtra("patientTag", this.patient.getTag());
        i.putExtra("visitTag", "");
        startActivity(i);
    }
    public void onBackFromVisit(View view) {
        goBackFromVisit();
    }
    private void goBackFromVisit() {
        Intent i = new Intent(this, PatientShow.class);
        i.putExtra("patientTag", this.patient.getTag());
        startActivity(i);
    }
    @Override
    public void onBackPressed() {
        goBackFromVisit();
    }

    public void onEditVisit(View view) {
        //this.msgBox.hide();
        Intent i = new Intent(activityVisit, AddVisit.class);
        i.putExtra("patientTag", patient.getTag());
        i.putExtra("visitTag", view.getTag().toString());
        startActivity(i);
    }
    public void onDeleteVisit(View view) {
        this.viewVisit = view;
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(getResources().getString(R.string.deleteVisitDialog)).setPositiveButton(getResources().getString(R.string.yes), dialogClickListener)
                .setNegativeButton(getResources().getString(R.string.no), dialogClickListener).show();
    }
    DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialog, int which) {
            switch (which){
                case DialogInterface.BUTTON_POSITIVE:
                    Visit visit = new Visit(VisitList.this, VisitList.this.patient, VisitList.this.viewVisit.getTag().toString());
                    visit.delete();
                    Toast.makeText(VisitList.this, VisitList.this.getResources().getString(R.string.deleteVisitDialogOk), Toast.LENGTH_SHORT).show();
                    setListAdapter(new VisitListAdapter(VisitList.this, new Visit(VisitList.this, VisitList.this.patient).getList()));
                    break;
                case DialogInterface.BUTTON_NEGATIVE:
                    break;
            }
        }
    };
}
