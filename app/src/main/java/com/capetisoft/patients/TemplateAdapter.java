package com.capetisoft.patients;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;

import com.capetisoft.patients.model.template.TemplateData;

import java.util.Vector;

/**
 * Created by carlospedroza on 14/11/15.
 */
public class TemplateAdapter extends BaseAdapter {
    private final Activity actividad;
    private final Vector<TemplateData> list;

    public TemplateAdapter(Activity actividad, Vector<TemplateData> list) {
        super();
        this.actividad = actividad;
        this.list = list;
    }
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflanter = actividad.getLayoutInflater();
        View view;

        switch (list.elementAt(position).getTemplateDataType()) {
            case 0:
                view = inflanter.inflate(R.layout.templategroup, null, true);
                TextView groupName = (TextView) view.findViewById(R.id.groupName);
                groupName.setText(this.list.elementAt(position).getTitle());
                break;
            case 1:
                view = inflanter.inflate(R.layout.templatedata_1, null, true);
                TextView templateLabel1 = (TextView) view.findViewById(R.id.templateLabel);
                templateLabel1.setText(this.list.elementAt(position).getTitle());
                if(list.elementAt(position).getPatientValue()!=null) {
                    EditText templateValue1 = (EditText) view.findViewById(R.id.templateValue);
                    templateValue1.setText(list.elementAt(position).getPatientValue().getValue());
                }
                break;
            case 2:
            case 3:
                view = inflanter.inflate(R.layout.templatedata_2, null, true);
                TextView templateLabel2 = (TextView) view.findViewById(R.id.templateLabel);
                templateLabel2.setText(this.list.elementAt(position).getTitle());
                if(list.elementAt(position).getPatientValue()!=null) {
                    EditText templateValue2 = (EditText) view.findViewById(R.id.templateValue);
                    templateValue2.setText(list.elementAt(position).getPatientValue().getValue());
                }
                break;
            case 4:
                view = inflanter.inflate(R.layout.templatedata_4, null, true);
                TextView templateLabel3 = (TextView) view.findViewById(R.id.templateLabel);
                templateLabel3.setText(this.list.elementAt(position).getTitle());
                if(list.elementAt(position).getPatientValue()!=null) {
                    EditText templateValue3 = (EditText) view.findViewById(R.id.templateValue);
                    templateValue3.setText(list.elementAt(position).getPatientValue().getValue());
                }
                break;
            case 5:
                view = inflanter.inflate(R.layout.templatedata_5, null, true);
                TextView templateLabel4 = (TextView) view.findViewById(R.id.templateLabel);
                templateLabel4.setText(this.list.elementAt(position).getTitle());
                if(list.elementAt(position).getPatientValue()!=null) {

                }
                break;
            case 6:
                view = inflanter.inflate(R.layout.templatedata_6, null, true);
                TextView templateLabel5 = (TextView) view.findViewById(R.id.templateLabel);
                templateLabel5.setText(this.list.elementAt(position).getTitle());
                if(list.elementAt(position).getPatientValue()!=null) {

                }
                break;
            case 7:
                view = inflanter.inflate(R.layout.templatedata_7, null, true);
                Switch switch7 = (Switch) view.findViewById(R.id.templateValue);
                switch7.setText(this.list.elementAt(position).getTitle());
                if(list.elementAt(position).getPatientValue()!=null) {

                }
                break;
            case 8:
                view = inflanter.inflate(R.layout.templatedata_8, null, true);
                TextView templateLabel8 = (TextView) view.findViewById(R.id.templateLabel);
                templateLabel8.setText(this.list.elementAt(position).getTitle());
                if(list.elementAt(position).getPatientValue()!=null) {

                }
                break;
            case 13:
                view = inflanter.inflate(R.layout.templatedata_13, null, true);
                break;
            default:
                view = null;
                break;
        }
        return view;
    }
    public int getCount() {
        return list.size();
    }
    public Object getItem(int arg0) {
        return list.elementAt(arg0);
    }
    public long getItemId(int position) {
        return position;
    }
}
