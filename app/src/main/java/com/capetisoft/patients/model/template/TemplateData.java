package com.capetisoft.patients.model.template;

import android.content.Context;
import android.database.Cursor;

import com.capetisoft.patients.dal.DataAccessLogic;
import com.capetisoft.patients.model.Patient;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by carlospedroza on 10/11/15.
 */
public class TemplateData {
    private Context context;
    private int id;
    private int templateDataType;
    private String title;
    private TemplateGroup templateGroup;
    private int required;
    private int orderItem;
    private List<TemplateDataItem> templateDataItems;
    private DataAccessLogic dal;
    private PatientValue patientValue;

    public final int COMPONENT_NUMBER = 1000;

    public TemplateData(Context context, TemplateGroup templateGroup, Cursor cursor) {
        this.setContext(context);
        this.setDal(new DataAccessLogic(context));
        this.setId(cursor.getInt(0));
        this.setTemplateDataType(cursor.getInt(1));
        this.setTitle(cursor.getString(2));
        this.setTemplateGroup(templateGroup);
        this.setRequired(cursor.getInt(3));
        this.setOrderItem(cursor.getInt(4));
        this.setTemplateDataItems();
    }
    public TemplateData(Context context, TemplateGroup templateGroup) {
        this.setContext(context);
        this.setDal(new DataAccessLogic(context));
        this.setId(0);
        this.setTemplateDataType(0);
        this.setTitle(templateGroup.getName());
        this.setTemplateGroup(templateGroup);
    }
    public void setValue(String value) {
        if(this.getPatientValue()!=null) {
            this.getPatientValue().setValue(value);
        }
        else {
            this.setPatientValue(new PatientValue(value, this));
        }
    }
    public void save() {
        if(this.getPatientValue()!=null) {
            String query="";
            Patient patient = this.getTemplateGroup().getTemplate().getPatient();
            if (this.getPatientValue().getId() == 0) {
                query+="INSERT INTO patientValue (identity, device, key, id_templateData, value, serverSync) VALUES (";
                query+=patient.getIdentity() + ",";
                query+="'"+patient.getDevice() + "',";
                query+="'"+patient.getKey() + "',";
                query+=this.getId() + ",";
                query+="'"+this.getPatientValue().getValue().replace("'","´") + "',";
                query+="0)";
            } else {
                query+="UPDATE patientValue";
                query+="   SET value = '"+this.getPatientValue().getValue().replace("'","´") + "',";
                query+="       serverSync = 0 ";
                query+=" WHERE identity = " + patient.getIdentity();
                query+="   AND device = '"+patient.getDevice() + "'";
                query+="   AND key = '"+patient.getKey() + "'";
                query+="   AND id_templateData = " + this.getId();
            }
            this.dal.query(query);
        }
    }

    public void setTemplateDataItems() {
        String query  = "";
        query += "SELECT id, itemName, orderItem FROM templateDataItem";
        query += " WHERE id_templateData = " + this.getId();
        query += " ORDER BY orderItem";

        List<TemplateDataItem> list = new ArrayList<>();
        Cursor cursor = this.getDal().read(query);
        while(cursor.moveToNext()) {
            TemplateDataItem templateDataItem = new TemplateDataItem(this.getContext(), this, cursor);
            list.add(templateDataItem);
        }
        cursor.close();
        this.setTemplateDataItems(list);
    }
    public void setPatientValue() {
        Patient patient = this.getTemplateGroup().getTemplate().getPatient();
        if(patient!=null) {
            String query = "";
            query += "SELECT id, value, serverSync  FROM patientValue";
            query += " WHERE identity = " + patient.getIdentity();
            query += "   AND device = '" + patient.getDevice() + "'";
            query += "   AND key = '" + patient.getKey() + "'";
            query += "   AND id_templateData = " + this.getId();

            List<PatientValue> list = new ArrayList<>();
            Cursor cursor = this.getDal().read(query);
            if (cursor.moveToNext()) {
                PatientValue patientValue = new PatientValue(this.getContext(), this, cursor);
                this.setPatientValue(patientValue);
            }
            cursor.close();
        }
    }
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getTemplateDataType() {
        return templateDataType;
    }

    public void setTemplateDataType(int templateDataType) {
        this.templateDataType = templateDataType;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public TemplateGroup getTemplateGroup() {
        return templateGroup;
    }

    public void setTemplateGroup(TemplateGroup templateGroup) {
        this.templateGroup = templateGroup;
    }

    public int getRequired() {
        return required;
    }

    public void setRequired(int required) {
        this.required = required;
    }

    public int getOrderItem() {
        return orderItem;
    }

    public void setOrderItem(int orderItem) {
        this.orderItem = orderItem;
    }

    public List<TemplateDataItem> getTemplateDataItems() {
        return templateDataItems;
    }

    public void setTemplateDataItems(List<TemplateDataItem> templateDataItems) {
        this.templateDataItems = templateDataItems;
    }

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public DataAccessLogic getDal() {
        return dal;
    }

    public void setDal(DataAccessLogic dal) {
        this.dal = dal;
    }

    public PatientValue getPatientValue() {
        return patientValue;
    }

    public void setPatientValue(PatientValue patientValue) {
        this.patientValue = patientValue;
    }

    public int getComponentNumber() {
        return this.COMPONENT_NUMBER + this.getId();
    }
}
