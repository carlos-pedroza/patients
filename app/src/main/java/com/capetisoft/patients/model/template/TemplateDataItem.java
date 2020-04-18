package com.capetisoft.patients.model.template;

import android.content.Context;
import android.database.Cursor;

import com.capetisoft.patients.dal.DataAccessLogic;

/**
 * Created by carlospedroza on 10/11/15.
 */
public class TemplateDataItem {
    private Context context;
    private int id;
    private TemplateData templateData;
    private String itemName;
    private int orderItem;
    private DataAccessLogic dal;

    public TemplateDataItem(Context context, TemplateData templateData, Cursor cursor) {
        this.setContext(context);
        this.setDal(new DataAccessLogic(context));
        this.setTemplateData(templateData);
        this.setId(cursor.getInt(0));
        this.setItemName(cursor.getString(1));
        this.setOrderItem(cursor.getInt(2));
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public TemplateData getTemplateData() {
        return templateData;
    }

    public void setTemplateData(TemplateData templateData) {
        this.templateData = templateData;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public int getOrderItem() {
        return orderItem;
    }

    public void setOrderItem(int orderItem) {
        this.orderItem = orderItem;
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
