package com.capetisoft.patients.model;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

import com.capetisoft.patients.model.template.Template;

/**
 * Created by carlospedroza on 03/10/15.
 */
public class GlobalRepository  extends Application{
    public static Person PersonLog=null;
    private static Template template;
    private static boolean isLoadTemplate;
    public static String cs;

    private Context context;

    public GlobalRepository(Context context) {
        this.context = context;
    }

    public static Template getTemplate() {
        return template;
    }

    public static void setTemplate(Template template) {
        GlobalRepository.template = template;
    }

    public static void loadTemplates(Context context, int language) {
        if(isLoadTemplate()) {
            Template template = new Template(context, language);
            template.loadTemplates();
            GlobalRepository.setTemplate(template);
        }
    }

    public static boolean isLoadTemplate() {
        return isLoadTemplate;
    }

    public static void setIsLoadTemplate(boolean isLoadTemplate) {
        GlobalRepository.isLoadTemplate = isLoadTemplate;
    }

    public void writeCurrentKey(String key) {
        Context context = this.getContext();
        SharedPreferences preferences = context.getSharedPreferences("PatientPreferences", context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("currentKey", key);
        editor.commit();
    }

    public String readCurrentKey() {
        Context context = this.getContext();
        SharedPreferences preferences2 = context.getSharedPreferences("PatientPreferences", context.MODE_PRIVATE);
        SharedPreferences preferences = ((Activity)context).getPreferences(MODE_PRIVATE);
        String currentKey = preferences.getString("currentKey", "");
        if(currentKey.equals("")){
            currentKey = preferences2.getString("currentKey", "");
        }
        else {
            SharedPreferences.Editor editor = preferences2.edit();
            editor.putString("currentKey", currentKey);
            editor.commit();
        }
        return currentKey;
    }

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }
    //public static String letter;
}
