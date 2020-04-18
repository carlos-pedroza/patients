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

import com.capetisoft.patients.model.Patient;
import com.capetisoft.patients.model.template.Template;
import com.capetisoft.patients.util.MessageView;
import com.capetisoft.patients.util.Utils;

import java.util.Calendar;

/**
 * Created by carlospedroza on 09/10/15.
 */
public class AddPatient extends Activity {

    MessageView msgBox;
    EditText name;
    Spinner gender;
    Spinner template;
    DatePicker birthDate;
    EditText phonePatient;
    EditText emailPatient;
    TextView ageLabel;

    Patient patient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_patient);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onSavePatient(null);
            }
        });
        name = (EditText) findViewById(R.id.name);
        gender = (Spinner) findViewById(R.id.gender);
        template = (Spinner) findViewById(R.id.template);
        birthDate = (DatePicker) findViewById(R.id.birthDate);
        phonePatient = (EditText) findViewById(R.id.phonePatient);
        emailPatient = (EditText) findViewById(R.id.emailPatient);
        ageLabel = (TextView) findViewById(R.id.ageLabel);

        Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);
        birthDate.init(year, month, day, dateSetListener);

        this.msgBox = new MessageView(findViewById(R.id.contentMainView));

        String _ageLabel = getResources().getString(R.string.ageLabel) + " 0";
        ageLabel.setText(_ageLabel);

    }

    @Override
    public void onBackPressed() {
        goMain();
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


    /*
    void initGender() {

        spinner = (Spinner) findViewById(R.id.gender);
        ArrayAdapter<String> SpinerAdapter;
        String[] arrayItems = new String[2];
        //arrayItems[0] = getResources().getString(R.string.genderFemale);
        //arrayItems[1] = getResources().getString(R.string.genderFemale);;
        final String[] actualValues={"m","f"};

        SpinerAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_dropdown_item, arrayItems);
        spinner.setAdapter(SpinerAdapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1,
                                       int arg2, long arg3) {
                setGender(actualValues[arg2]);
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
                // TODO Auto-generated method stub

            }
        });
    }
    */

    public void onSavePatient(View view) {
        if(validForm()) {
            Patient patient = new Patient(this);
            patient.setName(name.getText().toString());
            String[] arrayGender = getResources().getStringArray(R.array.gender);
            String _gender = this.gender.getSelectedItem().toString();
            String genderKey = "f";
            if(_gender.equals(arrayGender[1])) {
                genderKey = "m";
            }
            patient.setGender(genderKey);
            int language = Integer.valueOf(getResources().getString(R.string.language));
            Template template = new Template(this, language);
            int idTemplate=1;
            if(template.getList().size()>0) {
                idTemplate = template.getList().get(0).getId();
            }
            patient.setIdTemplate(idTemplate);

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
            long id = patient.insert();
            this.patient = new Patient(this, id);

            Toast.makeText(this, Utils.getResString(this, R.string.addPatientOK), Toast.LENGTH_SHORT).show();
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
    public void onBack(View view) {
        goMain();
    }
    private void goMain() {
        Intent i = new Intent(this, Main.class);
        startActivity(i);
    }
    public void onTemplateInfo(View view) {
        Intent i = new Intent(this, TemplateInfo.class);
        i.putExtra("activityReturn", "addPatient");
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
                    //GlobalRepository.setTemplate(new Template(AddPatient.this, AddPatient.this.patient, AddPatient.this.patient.getIdTemplate()));
                    Intent intent = new Intent(AddPatient.this, EditTemplate.class);
                    intent.putExtra("patientTag", AddPatient.this.patient.getTag());
                    intent.putExtra("returnActivity", "main");
                    startActivity(intent);
                    break;

                case DialogInterface.BUTTON_NEGATIVE:
                    Toast.makeText(AddPatient.this, Utils.getResString(AddPatient.this, R.string.dialogRecordNo), Toast.LENGTH_SHORT).show();
                    Intent intentNo = new Intent(AddPatient.this, Main.class);
                    startActivity(intentNo);
                    break;
            }
        }
    };
}
