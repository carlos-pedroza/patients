package com.capetisoft.patients.model.template;

import android.content.Context;
import android.database.Cursor;

import com.capetisoft.patients.dal.DataAccessLogic;
import com.capetisoft.patients.model.Patient;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by carlospedroza on 09/11/15.
 */
public class Template {
    private Context context;
    private int id;
    private String name;
    private int orderItem;
    private int idLanguage;
    private ArrayList<TemplateGroup> templateGroups;
    private long serverSync;
    private ArrayList<Template> list;
    private DataAccessLogic dal;
    private Patient patient;

    public Template(Context context, int idLanguage) {
        this.setContext(context);
        this.dal = new DataAccessLogic(this.getContext());
        this.read(idLanguage);
    }
    public void loadTemplates() {
        for(Template template : this.list) {
            template.setTemplateGroups();
        }
    }
    public Template(Context context, Cursor cursor) {
        this.setContext(context);
        this.dal = new DataAccessLogic(this.getContext());
        this.setId(cursor.getInt(0));
        this.setName(cursor.getString(1));
        this.setOrderItem(cursor.getInt(2));
        this.setIdLanguage(cursor.getInt(3));
        this.setServerSync(cursor.getInt(4));
    }
    public Template(Context context,Patient patient, int id) {
        this.setContext(context);
        this.dal = new DataAccessLogic(this.getContext());
        this.setPatient(patient);
        this.setId(id);
        this.read();
    }
    private void read() {
        boolean bnd = false;
        String query  = "";
        query += "SELECT  id, name, orderItem, id_language, serverSync FROM template";
        query += " WHERE id = " + this.getId();
        query += " ORDER BY orderItem";

        Cursor cursor = this.dal.read(query);
        if(cursor.moveToNext()) {
            this.set(cursor);
            bnd = true;
        }
        cursor.close();
        if(bnd) {
            this.setTemplateGroups();
        }
    }
    public void set(Cursor cursor) {
        this.setName(cursor.getString(1));
        this.setOrderItem(cursor.getInt(2));
        this.setIdLanguage(cursor.getInt(3));
        this.setServerSync(cursor.getInt(4));
    }
    public void setTemplateGroups() {
        String query  = "";
        query += "SELECT id, name, orderItem, id_template, multiple FROM templateGroup";
        query += " WHERE id_template = " + this.getId();
        query += " ORDER BY orderItem";

        ArrayList<TemplateGroup> list = new ArrayList<>();
        Cursor cursor = this.dal.read(query);
        while(cursor.moveToNext()) {
            TemplateGroup templateGroup = new TemplateGroup(this.getContext(), this, cursor);
            list.add(templateGroup);
        }
        cursor.close();
        for(TemplateGroup templateGroup : list) {
            templateGroup.setTemplateDatas();
        }
        this.setTemplateGroups(list);
    }
    private void read(int idLanguage) {
        String query  = "";
        query += "SELECT  id, name, orderItem, id_language, serverSync FROM template";
        query += " WHERE id_language = " + idLanguage;
        query += " ORDER BY orderItem";

        ArrayList<Template> list = new ArrayList<>();
        Cursor cursor = this.dal.read(query);
        while(cursor.moveToNext()) {
            Template template = new Template(this.getContext(), cursor);
            list.add(template);
        }
        cursor.close();
        this.setList(list);
    }
    public static boolean isGenerate(Context context) {
        DataAccessLogic dal = new DataAccessLogic(context);
        String query  = "";
        query += "SELECT  COUNT(*) AS count FROM template";

        int c=0;
        Cursor cursor = dal.read(query);
        if(cursor.moveToNext()) {
            c = cursor.getInt(0);
        }
        cursor.close();
        if(c>0) {
            return true;
        }
        else {
            return false;
        }
    }

    public Template searchTemplate(Patient patient) {
        for(Template template : this.list) {
            if(template.getPatient().equals(patient)) {
                if(template.getId()==patient.getIdTemplate()) {
                    return template;
                }
            }
        }
        return null;
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

    public int getIdLanguage() {
        return idLanguage;
    }

    public void setIdLanguage(int id_language) {
        this.idLanguage = id_language;
    }

    public ArrayList<TemplateGroup> getTemplateGroups() {
        return templateGroups;
    }

    public void setTemplateGroups(ArrayList<TemplateGroup> templateGroups) {
        this.templateGroups = templateGroups;
    }

    public static boolean generate(Context context) {
        DataAccessLogic dal = new DataAccessLogic(context);
        if(!Template.isGenerate(context)) {
            BufferedReader reader = null;
            try {
                reader = new BufferedReader(
                        new InputStreamReader(context.getAssets().open("templateinit.sql")));

                // do reading, usually loop until end of file reading
                String query;
                while ((query = reader.readLine()) != null) {
                    dal.query(query);
                }
            } catch (IOException e) {
                return false;
            }
            catch (Exception ex) {
                return false;
            }
            finally {
                if (reader != null) {
                    try {
                        reader.close();
                    } catch (IOException e) {
                        return false;
                    }
                }
            }
        }
        return true;
    }

    public long getServerSync() {
        return serverSync;
    }

    public void setServerSync(long serverSync) {
        this.serverSync = serverSync;
    }

    public ArrayList<Template> getList() {
        return list;
    }

    public void setList(ArrayList<Template> list) {
        this.list = list;
    }

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public Patient getPatient() {
        return patient;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
    }

    public static String DateStringToString(String longDate, String format)
    {
        Date dValue = new Date(Long.valueOf(longDate));
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        return sdf.format(dValue.getTime());
    }
}
