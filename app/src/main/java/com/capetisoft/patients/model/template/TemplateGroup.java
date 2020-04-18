package com.capetisoft.patients.model.template;

import android.content.Context;
import android.database.Cursor;

import com.capetisoft.patients.dal.DataAccessLogic;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by carlospedroza on 10/11/15.
 */
public class TemplateGroup {
    private Context context;
    private int id;
    private String name;
    private int orderItem;
    private Template template;
    private int multiple;
    private List<TemplateData> templateDatas;
    private DataAccessLogic dal;

    public TemplateGroup(Context context, Template template, Cursor cursor) {
        this.setContext(context);
        this.dal = new DataAccessLogic(context);
        this.setTemplate(template);
        this.setId(cursor.getInt(0));
        this.setName(cursor.getString(1));
        this.setOrderItem(cursor.getInt(2));
        this.setMultiple(cursor.getInt(3));
    }
    public void setTemplateDatas() {
        String query  = "";
        query += "SELECT id, id_templateDataType, title, required, orderItem FROM templateData";
        query += " WHERE id_templateGroup = " + this.getId();
        query += " ORDER BY orderItem";

        List<TemplateData> list = new ArrayList<>();
        Cursor cursor = this.getDal().read(query);
        while(cursor.moveToNext()) {
            TemplateData templateData = new TemplateData(this.getContext(), this, cursor);
            list.add(templateData);
        }
        cursor.close();
        for(TemplateData templateData : list) {
            templateData.setPatientValue();
        }
        this.setTemplateDatas(list);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getOrderItem() {
        return orderItem;
    }

    public void setOrderItem(int orderItem) {
        this.orderItem = orderItem;
    }

    public Template getTemplate() {
        return template;
    }

    public void setTemplate(Template template) {
        this.template = template;
    }

    public int getMultiple() {
        return multiple;
    }

    public void setMultiple(int multiple) {
        this.multiple = multiple;
    }

    public List<TemplateData> getTemplateDatas() {
        return templateDatas;
    }

    public void setTemplateDatas(List<TemplateData> templateDatas) {
        this.templateDatas = templateDatas;
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
}
