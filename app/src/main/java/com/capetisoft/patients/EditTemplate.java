package com.capetisoft.patients;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TimePicker;

import com.capetisoft.patients.model.Patient;
import com.capetisoft.patients.model.template.Template;
import com.capetisoft.patients.model.template.TemplateData;
import com.capetisoft.patients.model.template.TemplateDataItem;
import com.capetisoft.patients.model.template.TemplateGroup;
import com.capetisoft.patients.ui.TemplatesFragment;
import com.capetisoft.patients.util.Utils;

import java.util.Calendar;

/**
 * Created by carlospedroza on 14/11/15.
 */
public class EditTemplate extends ActionBarActivity {
    Patient patient;
    private Template template;
    String returnActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_template);

        Bundle extra = getIntent().getExtras();
        String patientTag = extra.getString("patientTag");
        this.returnActivity = extra.getString("returnActivity");

        this.patient = new Patient(this, patientTag);
        this.setTemplate(Utils.getTemplate(patient, this));
        if(this.getTemplate() !=null) {
            LinearLayout contentView = (LinearLayout) findViewById(R.id.contentView);
            if (contentView.getChildCount() > 0)
                contentView.removeAllViews();
            getSupportFragmentManager().beginTransaction().add(R.id.contentView, new TemplatesFragment()).commit();
        }
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onSaveButton(null);
            }
        });
    }

    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState) {
        //super.onRestoreInstanceState(savedInstanceState, persistentState);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        //super.onSaveInstanceState(outState, outPersistentState);
        setValues();
    }

    @Override
    public void onBackPressed() {
       this.goBack();
    }
    public void onBackButton(View view) {
        this.goBack();
    }

    public void goBack() {
        if(this.returnActivity.equals("Main")) {
            Intent i = new Intent(this, Main.class);
            startActivity(i);
        }
        else {
            Intent i = new Intent(this, PatientShow.class);
            i.putExtra("patientTag", this.patient.getTag());
            startActivity(i);
        }
    }
    public Template getTemplate() {
        return template;
    }

    public void setTemplate(Template template) {
        this.template = template;
    }

    public void onSaveButton(View view) {
        this.setValues();
        this.save();
        this.goBack();
    }

    public void save() {
        for (TemplateGroup templateGroup : this.getTemplate().getTemplateGroups()) {
            for (TemplateData templateData : templateGroup.getTemplateDatas()) {
                templateData.save();
            }
        }
    }

    public void setValues() {
        if(this.getTemplate()!=null) {
            Calendar calendar;
            for (TemplateGroup templateGroup : this.getTemplate().getTemplateGroups()) {
                for (TemplateData templateData : templateGroup.getTemplateDatas()) {
                    switch (templateData.getTemplateDataType()) {
                        case 101:
                        case 1: //Short Text
                            EditText templateValue1 = (EditText) findViewById(templateData.getComponentNumber());
                            templateData.setValue(templateValue1.getText().toString());
                            break;
                        case 102:
                        case 103:
                        case 2: //Medium Text
                        case 3:
                            EditText templateValue2 = (EditText) findViewById(templateData.getComponentNumber());
                            templateData.setValue(templateValue2.getText().toString());
                            break;
                        case 104:
                        case 4: //Long Text
                            EditText templateValue4 = (EditText) findViewById(templateData.getComponentNumber());
                            templateData.setValue(templateValue4.getText().toString());
                            break;
                        case 105:
                        case 5: //Date
                            DatePicker templateValueDate5 = (DatePicker) findViewById(templateData.getComponentNumber());
                            calendar = Calendar.getInstance();
                            calendar.set(templateValueDate5.getYear(), templateValueDate5.getMonth(), templateValueDate5.getDayOfMonth(), 0, 0, 0);
                            long templateValue5 = calendar.getTimeInMillis();
                            templateData.setValue(String.valueOf(templateValue5));
                            break;
                        case 106:
                        case 6: //Date & Time
                            DatePicker templateValueDate6 = (DatePicker) findViewById(templateData.getComponentNumber());
                            TimePicker templateValueTime6 = (TimePicker) findViewById(templateData.getComponentNumber()+templateData.COMPONENT_NUMBER);
                            calendar = Calendar.getInstance();
                            calendar.set(templateValueDate6.getYear(), templateValueDate6.getMonth(), templateValueDate6.getDayOfMonth(), templateValueTime6.getCurrentHour(), templateValueTime6.getCurrentMinute(), 0);
                            long templateValue6 = calendar.getTimeInMillis();
                            templateData.setValue(String.valueOf(templateValue6));
                            break;
                        case 107:
                        case 7: // Switch
                            Switch templateValue7 = (Switch) findViewById(templateData.getComponentNumber());
                            if (templateValue7.isChecked()) {
                                templateData.setValue("on");
                            } else {
                                templateData.setValue("off");
                            }
                            break;
                        case 108:
                        case 8: // Spinner
                            Spinner templateValue8 = (Spinner) findViewById(templateData.getComponentNumber());
                            String value = (String) templateValue8.getSelectedItem();
                            for(TemplateDataItem templateDataItem : templateData.getTemplateDataItems()) {
                                if(templateDataItem.getItemName().equals(value)) {
                                    templateData.setValue(String.valueOf(templateDataItem.getId()));
                                    break;
                                }
                            }
                            break;
                        case 109:
                        case 13: //List
                            EditText templateValue13 = (EditText) findViewById(templateData.getComponentNumber());
                            templateData.setValue(templateValue13.getText().toString());
                            break;
                    }
                }
            }
        }
    }
}
