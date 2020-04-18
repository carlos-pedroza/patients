package com.capetisoft.patients;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.capetisoft.patients.model.GlobalRepository;
import com.capetisoft.patients.model.Patient;
import com.capetisoft.patients.model.template.Template;
import com.capetisoft.patients.util.MessageView;
import com.capetisoft.patients.util.Utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Created by carlospedroza on 03/11/15.
 */
public class EditPatient extends Activity {
    MessageView msgBox;
    EditText name;
    Spinner gender;
    TextView templateLabel;
    DatePicker birthDate;
    EditText phonePatient;
    EditText emailPatient;
    TextView ageLabel;
    Patient patient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_patient);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onSavePatient(null);
            }
        });
        name = (EditText) findViewById(R.id.name);
        gender = (Spinner) findViewById(R.id.gender);
        templateLabel = (TextView) findViewById(R.id.templateLabel);
        birthDate = (DatePicker) findViewById(R.id.birthDate);
        phonePatient = (EditText) findViewById(R.id.phonePatient);
        emailPatient = (EditText) findViewById(R.id.emailPatient);
        ageLabel = (TextView) findViewById(R.id.ageLabel);
        Calendar c = Calendar.getInstance();

        Bundle extra = getIntent().getExtras();
        String patientTag = extra.getString("patientTag");
        patient = new Patient(this, patientTag);

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy");
        int year = Integer.parseInt(sdf.format(this.patient.getDateBirth()));
        sdf = new SimpleDateFormat("MM");
        int month = Integer.parseInt(sdf.format(this.patient.getDateBirth())) - 1;
        sdf = new SimpleDateFormat("dd");
        int day = Integer.parseInt(sdf.format(this.patient.getDateBirth()));

        birthDate.init(year, month, day, dateSetListener);

        this.name.setText(this.patient.getName());

        switch (this.patient.getGender()) {
            case "f":
                this.gender.setSelection(0);
                break;
            case "m":
                this.gender.setSelection(1);
        }

        this.templateLabel.setText(this.patient.getTemplate());
        this.phonePatient.setText(this.patient.getPhone());
        this.emailPatient.setText(this.patient.getEmail());

        this.msgBox = new MessageView(findViewById(R.id.contentMainView));
        
        calculateAge();

    }
    private DatePicker.OnDateChangedListener dateSetListener = new DatePicker.OnDateChangedListener() {

        public void onDateChanged(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                Calendar calendar = Calendar.getInstance();
                calendar.set(birthDate.getYear(), birthDate.getMonth(), birthDate.getDayOfMonth(), 0, 0, 0);
                long startTime = calendar.getTimeInMillis();
                int age = Utils.age(startTime);
                if(age<0) {
                    age=0;
                }
                String _ageLabel = getResources().getString(R.string.ageLabel) + " " + String.valueOf(age);
                ageLabel.setText(_ageLabel);
        }
    };
    public void onSavePatient(View view) {
        if(validForm()) {
            patient.setName(name.getText().toString());
            String[] arrayGender = getResources().getStringArray(R.array.gender);
            String _gender = this.gender.getSelectedItem().toString();
            String genderKey = "f";
            if(_gender.equals(arrayGender[1])) {
                genderKey = "m";
            }
            patient.setGender(genderKey);

            //calendar.set(birthDate.getYear(), birthDate.getMonth(), birthDate.getDayOfMonth(),
            //        timePicker.getCurrentHour(), timePicker.getCurrentMinute(), 0);
            Calendar calendar = Calendar.getInstance();
            calendar.set(birthDate.getYear(), birthDate.getMonth(), birthDate.getDayOfMonth(), 0, 0, 0);
            long startTime = calendar.getTimeInMillis();
            patient.setDateBirth(startTime);
            patient.setPhone(phonePatient.getText().toString());
            patient.setEmail(emailPatient.getText().toString());
            patient.setServerSync(0);
            patient.setDeleted(0);
            patient.save();

            Toast.makeText(this, Utils.getResString(this, R.string.editPatientOK), Toast.LENGTH_SHORT).show();
            this.onOpenTemplate();
        }
    }
    private Boolean validForm() {
        if(name.getText().toString().equals("")) {
            this.msgBox.setMessage(Utils.getResString(this, R.string.patientNameRequired), MessageView.MessageType.ERROR);
            return false;
        }
        else if(!emailPatient.getText().toString().equals("")){
            if(!Utils.isEmailValid(emailPatient.getText().toString())) {
                this.msgBox.setMessage(Utils.getResString(this, R.string.emailNoValid), MessageView.MessageType.ERROR);
                return false;
            }
        }
        return true;
    }
    @Override
    public void onBackPressed() {
        goMain();
    }
    public void onBack(View view) {
        goMain();
    }
    private void goMain() {
        Intent i = new Intent(this, PatientShow.class);
        i.putExtra("patientTag", EditPatient.this.patient.getTag());
        startActivity(i);
    }
    private void calculateAge() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(birthDate.getYear(), birthDate.getMonth(), birthDate.getDayOfMonth(), 0, 0, 0);
        long startTime = calendar.getTimeInMillis();
        int age = Utils.age(startTime);
        if(age<0) {
            age=0;
        }
        String _ageLabel = getResources().getString(R.string.ageLabel) + " " + String.valueOf(age);
        ageLabel.setText(_ageLabel);
    }
    public void onTemplateInfo(View view) {
        Intent i = new Intent(this, TemplateInfo.class);
        i.putExtra("activityReturn", "editPatient");
        i.putExtra("patientTag", EditPatient.this.patient.getTag());
        startActivity(i);
    }
    public void onOpenTemplate() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(getResources().getString(R.string.dialogRecord)).setPositiveButton(getResources().getString(R.string.yes), dialogClickListener)
                .setNegativeButton(getResources().getString(R.string.no), dialogClickListener).show();
    }
    DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialog, int which) {
            switch (which){
                case DialogInterface.BUTTON_POSITIVE:
                    GlobalRepository.setTemplate(new Template(EditPatient.this, EditPatient.this.patient, EditPatient.this.patient.getIdTemplate()));
                    Intent intent = new Intent(EditPatient.this, EditTemplate.class);
                    intent.putExtra("patientTag", EditPatient.this.patient.getTag());
                    intent.putExtra("returnActivity", "patientShow");
                    startActivity(intent);
                    break;

                case DialogInterface.BUTTON_NEGATIVE:
                    Toast.makeText(EditPatient.this, Utils.getResString(EditPatient.this, R.string.dialogRecordNo), Toast.LENGTH_SHORT).show();
                    Intent intentNo = new Intent(EditPatient.this, PatientShow.class);
                    intentNo.putExtra("patientTag", EditPatient.this.patient.getTag());
                    startActivity(intentNo);
                    break;
            }
        }
    };
}
