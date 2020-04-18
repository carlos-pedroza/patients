package com.capetisoft.patients.ui;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.TimePicker;

import com.capetisoft.patients.EditTemplate;
import com.capetisoft.patients.R;
import com.capetisoft.patients.model.template.PatientValue;
import com.capetisoft.patients.model.template.Template;
import com.capetisoft.patients.model.template.TemplateData;
import com.capetisoft.patients.model.template.TemplateDataItem;
import com.capetisoft.patients.model.template.TemplateGroup;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by carlospedroza on 17/11/15.
 */
public class TemplatesFragment extends Fragment {
    Context context;
    Template template;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.template = ((EditTemplate) context).getTemplate();
        this.context = context;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflanter, ViewGroup container, Bundle savedInstanceState) {
        View root = inflanter.inflate(R.layout.template_fragment, container, false);
        this.showGroups((LinearLayout) root, inflanter);

        return root;
    }
    private void showGroups(LinearLayout contentView, LayoutInflater inflanter) {
        //if(contentView.getChildCount() > 0)
        //    contentView.removeAllViews();
        for(TemplateGroup templateGroup : this.template.getTemplateGroups() ) {
            TemplateData templateDataGroup = new TemplateData(this.context, templateGroup);
            View viewGroup = this.createView(templateDataGroup, inflanter);
            contentView.addView(viewGroup);
            for(TemplateData templateData : templateGroup.getTemplateDatas()) {
                View view = this.createView(templateData, inflanter);
                contentView.addView(view);
            }
        }
        contentView.addView(this.downSpace(inflanter));
    }
    public View createView(TemplateData templateData, LayoutInflater inflanter) {
        View view;

        switch (templateData.getTemplateDataType()) {
            case 0:
                view = inflanter.inflate(R.layout.templategroup, null, true);
                TextView groupName = (TextView) view.findViewById(R.id.groupName);
                groupName.setText(templateData.getTitle());
                break;
            case 101:
            case 1:
                view = inflanter.inflate(R.layout.templatedata_1, null, true);
                TextView templateLabel1 = (TextView) view.findViewById(R.id.templateLabel);
                templateLabel1.setText(templateData.getTitle());
                EditText templateValue1 = (EditText) view.findViewById(R.id.templateValue);
                templateValue1.setId(templateData.getComponentNumber());
                PatientValue patientValue = templateData.getPatientValue();
                if(patientValue!=null) {
                    templateValue1.setText(patientValue.getValue());
                }
                break;
            case 102:
            case 103:
            case 2:
            case 3:
                view = inflanter.inflate(R.layout.templatedata_2, null, true);
                TextView templateLabel2 = (TextView) view.findViewById(R.id.templateLabel);
                templateLabel2.setText(templateData.getTitle());
                EditText templateValue2 = (EditText) view.findViewById(R.id.templateValue);
                templateValue2.setId(templateData.getComponentNumber());
                PatientValue patientValue2 = templateData.getPatientValue();
                if(patientValue2!=null) {
                    templateValue2.setText(patientValue2.getValue());
                }
                break;
            case 104:
            case 4:
                view = inflanter.inflate(R.layout.templatedata_4, null, true);
                TextView templateLabel3 = (TextView) view.findViewById(R.id.templateLabel);
                templateLabel3.setText(templateData.getTitle());
                EditText templateValue3 = (EditText) view.findViewById(R.id.templateValue);
                templateValue3.setId(templateData.getComponentNumber());
                PatientValue patientValue3 = templateData.getPatientValue();
                if(patientValue3!=null) {
                    templateValue3.setText(patientValue3.getValue());
                }
                break;
            case 105:
            case 5:
                view = inflanter.inflate(R.layout.templatedata_5, null, true);
                TextView templateLabel4 = (TextView) view.findViewById(R.id.templateLabel);
                templateLabel4.setText(templateData.getTitle());
                DatePicker templateValueDate5 = (DatePicker) view.findViewById(R.id.templateValue);
                templateValueDate5.setId(templateData.getComponentNumber());
                PatientValue patientValue5 = templateData.getPatientValue();
                if(patientValue5!=null) {
                    Date dValue5 = new Date(Long.valueOf(patientValue5.getValue()));
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy");
                    int year = Integer.parseInt(sdf.format(dValue5.getTime()));
                    sdf = new SimpleDateFormat("MM");
                    int month = Integer.parseInt(sdf.format(dValue5.getTime()));
                    sdf = new SimpleDateFormat("dd");
                    int day = Integer.parseInt(sdf.format(dValue5.getTime()));
                    templateValueDate5.updateDate(year, --month, day);
                }
                break;
            case 106:
            case 6:
                view = inflanter.inflate(R.layout.templatedata_6, null, true);
                TextView templateLabel5 = (TextView) view.findViewById(R.id.templateLabel);
                templateLabel5.setText(templateData.getTitle());
                DatePicker templateValueDate = (DatePicker) view.findViewById(R.id.templateValueDate);
                templateValueDate.setId(templateData.getComponentNumber());
                TimePicker templateValueTime = (TimePicker) view.findViewById(R.id.templateValueTime);
                templateValueTime.setId(templateData.getComponentNumber()+templateData.COMPONENT_NUMBER);
                PatientValue patientValue6 = templateData.getPatientValue();
                if(patientValue6!=null) {
                    Date dValue6 = new Date(Long.valueOf(patientValue6.getValue()));
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy");
                    int year = Integer.parseInt(sdf.format(dValue6.getTime()));
                    sdf = new SimpleDateFormat("MM");
                    int month = Integer.parseInt(sdf.format(dValue6.getTime()));
                    sdf = new SimpleDateFormat("dd");
                    int day = Integer.parseInt(sdf.format(dValue6.getTime()));
                    templateValueDate.updateDate(year,--month, day);
                    sdf = new SimpleDateFormat("HH");
                    int hours = Integer.parseInt(sdf.format(dValue6.getTime()));
                    templateValueTime.setCurrentHour(hours);
                    sdf = new SimpleDateFormat("mm");
                    int minute = Integer.parseInt(sdf.format(dValue6.getTime()));
                    templateValueTime.setCurrentMinute(minute);
                }
                break;
            case 107:
            case 7:
                view = inflanter.inflate(R.layout.templatedata_7, null, true);
                Switch switch7 = (Switch) view.findViewById(R.id.templateValue);
                switch7.setText(templateData.getTitle());
                Switch templateValue8 = (Switch) view.findViewById(R.id.templateValue);
                templateValue8.setId(templateData.getComponentNumber());
                PatientValue patientValue4 = templateData.getPatientValue();
                if(patientValue4!=null) {
                    if(patientValue4.getValue().equals("on")) {
                        templateValue8.setChecked(true);
                    }
                    else {
                        templateValue8.setChecked(false);
                    }
                }
                break;
            case 108:
            case 8:
                view = inflanter.inflate(R.layout.templatedata_8, null, true);
                TextView templateLabel8 = (TextView) view.findViewById(R.id.templateLabel);
                templateLabel8.setText(templateData.getTitle());
                Spinner templateValue9 = (Spinner) view.findViewById(R.id.templateValue);
                PatientValue patientValue8 = templateData.getPatientValue();

                int position = 0;
                int c = 0;
                ArrayList<String> spinnerArray = new ArrayList<>();
                for (TemplateDataItem templateDataItem : templateData.getTemplateDataItems()) {
                    spinnerArray.add(templateDataItem.getItemName());
                    if (patientValue8 != null) {
                        if (templateDataItem.getId() == Integer.valueOf(patientValue8.getValue())) {
                            position = c;
                        }
                    }
                    c++;
                }
                ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<>(this.context, android.R.layout.simple_spinner_item, spinnerArray);
                spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                templateValue9.setAdapter(spinnerArrayAdapter);
                templateValue9.setId(templateData.getComponentNumber());
                templateValue9.setSelection(position);
                break;
            case 109:
            case 13:
                view = inflanter.inflate(R.layout.templatedata_4, null, true);
                TextView templateLabel13 = (TextView) view.findViewById(R.id.templateLabel);
                templateLabel13.setText(templateData.getTitle());
                EditText templateValue13 = (EditText) view.findViewById(R.id.templateValue);
                templateValue13.setId(templateData.getComponentNumber());
                PatientValue patientValue13 = templateData.getPatientValue();
                if(patientValue13!=null) {
                    templateValue13.setText(patientValue13.getValue());
                }
                break;
            default:
                view = inflanter.inflate(R.layout.templategroup, null, true);
                TextView emptyName = (TextView) view.findViewById(R.id.groupName);
                emptyName.setText("ERR!");
                break;
        }
        return view;
    }
    public View downSpace(LayoutInflater inflanter ) {
        View view;

        view = inflanter.inflate(R.layout.down_space, null, true);
        return view;
    }
}
