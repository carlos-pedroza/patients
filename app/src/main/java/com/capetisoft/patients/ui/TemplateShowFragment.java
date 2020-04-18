package com.capetisoft.patients.ui;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.capetisoft.patients.PatientShow;
import com.capetisoft.patients.R;
import com.capetisoft.patients.model.template.PatientValue;
import com.capetisoft.patients.model.template.Template;
import com.capetisoft.patients.model.template.TemplateData;
import com.capetisoft.patients.model.template.TemplateDataItem;
import com.capetisoft.patients.model.template.TemplateGroup;

/**
 * Created by carlospedroza on 20/11/15.
 */
public class TemplateShowFragment extends Fragment {
    Context context;
    Template template;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.template = ((PatientShow) context).getTemplate();
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
        View contentGroup=null;
        for(TemplateGroup templateGroup : this.template.getTemplateGroups() ) {
            contentGroup = inflanter.inflate(R.layout.template_show_group, null, true);
            TextView groupName = (TextView) contentGroup.findViewById(R.id.groupName);
            groupName.setText(templateGroup.getName());
            LinearLayout rowContent = (LinearLayout) contentGroup.findViewById(R.id.contentTemplate);
            for(TemplateData templateData : templateGroup.getTemplateDatas()) {
                View view = this.createView(templateData, inflanter);
                rowContent.addView(view);
            }
            contentView.addView(contentGroup);
        }
        contentView.addView(this.downSpace(inflanter));
    }
    public View createView(TemplateData templateData, LayoutInflater inflanter) {
        View view;

        switch (templateData.getTemplateDataType()) {
            case 101:
            case 1:
                view = inflanter.inflate(R.layout.template_show, null, true);
                TextView templateTitle = (TextView) view.findViewById(R.id.templateTitle);
                templateTitle.setText(templateData.getTitle());
                TextView templateValue = (TextView) view.findViewById(R.id.templateValue);
                templateValue.setId(templateData.getComponentNumber());
                PatientValue patientValue = templateData.getPatientValue();
                if(patientValue!=null) {
                    templateValue.setText(patientValue.getValue());
                }
                break;
            case 102:
            case 103:
            case 2:
            case 3:
                view = inflanter.inflate(R.layout.template_show, null, true);
                TextView templateTitle3 = (TextView) view.findViewById(R.id.templateTitle);
                templateTitle3.setText(templateData.getTitle());
                TextView templateValue3 = (TextView) view.findViewById(R.id.templateValue);
                templateValue3.setId(templateData.getComponentNumber());
                PatientValue patientValue2 = templateData.getPatientValue();
                if(patientValue2!=null) {
                    templateValue3.setText(patientValue2.getValue());
                }
                break;
            case 104:
            case 4:
                view = inflanter.inflate(R.layout.template_show, null, true);
                TextView templateTitle4 = (TextView) view.findViewById(R.id.templateTitle);
                templateTitle4.setText(templateData.getTitle());
                TextView templateValue4 = (TextView) view.findViewById(R.id.templateValue);
                templateValue4.setId(templateData.getComponentNumber());
                PatientValue patientValue4 = templateData.getPatientValue();
                if(patientValue4!=null) {
                    templateValue4.setText(patientValue4.getValue());
                }
                break;
            case 105:
            case 5:
                view = inflanter.inflate(R.layout.template_show, null, true);
                TextView templateTitle5 = (TextView) view.findViewById(R.id.templateTitle);
                templateTitle5.setText(templateData.getTitle());
                TextView templateValue5 = (TextView) view.findViewById(R.id.templateValue);
                templateValue5.setId(templateData.getComponentNumber());
                PatientValue patientValue5 = templateData.getPatientValue();
                if(patientValue5!=null) {
                    String format = getActivity().getResources().getString(R.string.dateTimeFormatShort);
                    templateValue5.setText(Template.DateStringToString(patientValue5.getValue(),format));
                }
                break;
            case 106:
            case 6:
                view = inflanter.inflate(R.layout.template_show, null, true);
                TextView templateTitle6 = (TextView) view.findViewById(R.id.templateTitle);
                templateTitle6.setText(templateData.getTitle());
                TextView templateValue6 = (TextView) view.findViewById(R.id.templateValue);
                templateValue6.setId(templateData.getComponentNumber());
                PatientValue patientValue6 = templateData.getPatientValue();
                if(patientValue6!=null) {
                    String format = getActivity().getResources().getString(R.string.dateTimeFormat);
                    templateValue6.setText(Template.DateStringToString(patientValue6.getValue(),format));
                }
                break;
            case 107:
            case 7:
                PatientValue patientValue7 = templateData.getPatientValue();
                if(patientValue7!=null) {
                    if(patientValue7.getValue().equals("on")) {
                        view = inflanter.inflate(R.layout.template_show_switch_yes, null, true);
                        TextView templateTitle7 = (TextView) view.findViewById(R.id.templateTitle);
                        templateTitle7.setText(templateData.getTitle());
                    }
                    else {
                        view = inflanter.inflate(R.layout.template_show_switch_no, null, true);
                        TextView templateTitle8 = (TextView) view.findViewById(R.id.templateTitle);
                        templateTitle8.setText(templateData.getTitle());
                    }
                }
                else {
                    view = inflanter.inflate(R.layout.template_show_switch_no, null, true);
                    TextView templateTitle8 = (TextView) view.findViewById(R.id.templateTitle);
                    templateTitle8.setText(templateData.getTitle());
                }
                break;
            case 108:
            case 8:
                view = inflanter.inflate(R.layout.template_show, null, true);
                TextView templateTitle8 = (TextView) view.findViewById(R.id.templateTitle);
                templateTitle8.setText(templateData.getTitle());
                TextView templateValue8 = (TextView) view.findViewById(R.id.templateValue);
                templateValue8.setId(templateData.getComponentNumber());
                PatientValue patientValue8 = templateData.getPatientValue();
                if(patientValue8!=null) {
                    for (TemplateDataItem templateDataItem : templateData.getTemplateDataItems()) {
                        if (patientValue8 != null) {
                            if (templateDataItem.getId() == Integer.valueOf(patientValue8.getValue())) {
                                templateValue8.setText(templateDataItem.getItemName());
                            }
                        }
                    }
                }
                break;
            case 109:
            case 13:
                view = inflanter.inflate(R.layout.template_show, null, true);
                TextView templateTitle13 = (TextView) view.findViewById(R.id.templateTitle);
                templateTitle13.setText(templateData.getTitle());
                TextView templateValue13 = (TextView) view.findViewById(R.id.templateValue);
                templateValue13.setId(templateData.getComponentNumber());
                PatientValue patientValue13 = templateData.getPatientValue();
                if(patientValue13!=null) {
                    templateValue13.setText(patientValue13.getValue());
                }
                break;
            default:
                view = inflanter.inflate(R.layout.template_show, null, true);
                TextView templateTitleERR = (TextView) view.findViewById(R.id.templateTitle);
                templateTitleERR.setText("ERR!");
                TextView templateValueERR = (TextView) view.findViewById(R.id.templateValue);
                templateValueERR.setText("ERR!");

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
