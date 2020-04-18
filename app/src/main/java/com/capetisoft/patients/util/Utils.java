package com.capetisoft.patients.util;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.IBinder;
import android.provider.Settings;
import android.view.inputmethod.InputMethodManager;

import com.capetisoft.patients.model.Patient;
import com.capetisoft.patients.model.PreferencesDojo;
import com.capetisoft.patients.model.template.Template;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by carlospedroza on 03/10/15.
 */
public class Utils {
    public static final String MILLIS_5_MINUT = "5m";
    public static final String MILLIS_10_MINUT = "10m";
    public static final String MILLIS_15_MINUT = "15m";
    public static final String MILLIS_30_MINUT = "30m";
    public static final String MILLIS_1_HOUR = "1h";
    public static final String MILLIS_2_HOUR = "2h";
    public static final String MILLIS_1_DAY = "1d";
    public static final String MILLIS_2_DAY = "2d";
    public static final String MILLIS_1_WEEK = "2w";
    public static final String MILLIS_1_MONTH = "1mo";
    public static final String NO_NOTIFICATE= "no";

    public static final String PREFERENCES_EMAIL = "email";
    public static final String PREFERENCES_REMEMBER_PASSWORD = "rememberPassword";
    public static final String PREFERENCES_KEY= "person_key";
    public static final String PREFERENCES_PASSWORD= "password";
    public static final String PREFERENCES_CURRENT_PATIENT_QUERY_TYPE= "currentPatientQueryType";
    public static final String PREFERENCES_CURRENT_ID_TEMPLATE = "currentIdTemplate";
    public static final String PREFERENCES_PATIENT_SEARCH_QUERY = "currentPatientSearchQuery";
    public static final String PREFERENCES_CURRENT_KEY="currentKey";
    public static final String PREFERENCES_CURRENT_ID_PATIENT = "currentIdPatient";

    public static final String PREFERENCES_LAST_SYNC_SERVER= "lastSyncServer";
    public static final String PREFERENCES_LAST_SYNC_SERVER_PV= "lastSyncServerPV";
    public static final String PREFERENCES_LAST_SYNC_SERVER_VI= "lastSyncServerVI";

    public enum ListType {
        all, today, week, month
    }
    public static class DateStructure {
        private int day;
        private int month;
        private int year;
        private int hour;
        private int minute;
        private int second;
        private List<Date> values;
        private List<Long> valuesMillis;

        public enum TypeDate {
            day_month_year, day_month_year_night, day_month_year_hour_minute, day_month_year_hour_minute_second
        }

        public int getDay() {
            return day;
        }

        public void setDay(int day) {
            this.day = day;
        }

        public int getMonth() {
            return month;
        }

        public void setMonth(int month) {
            this.month = month;
        }

        public int getYear() {
            return year;
        }

        public void setYear(int year) {
            this.year = year;
        }

        public int getHour() {
            return hour;
        }

        public void setHour(int hour) {
            this.hour = hour;
        }

        public int getMinute() {
            return minute;
        }

        public void setMinute(int minute) {
            this.minute = minute;
        }

        public int getSecond() {
            return second;
        }

        public void setSecond(int second) {
            this.second = second;
        }
        public List<Date> getValues() {
            return values;
        }

        public DateStructure() {
            this.values = new ArrayList<>();
            this.valuesMillis =  new ArrayList<>();
        }
        public DateStructure(long millis, TypeDate typeDate) {
            this.values = new ArrayList<>();
            this.valuesMillis =  new ArrayList<>();
            newDateStructure(millis, typeDate);
        }
        public DateStructure(int day, int month, int year) {
            this.values = new ArrayList<>();
            this.valuesMillis =  new ArrayList<>();
            this.newDateStructure(day, month, year, 0, 0, 0);
        }
        public DateStructure(int day, int month, int year, int hour, int minute) {
            this.values = new ArrayList<>();
            this.valuesMillis =  new ArrayList<>();
            this.newDateStructure(day, month, year, hour, minute, 0);
        }
        public DateStructure(int day, int month, int year, int hour, int minute, int second) {
            this.values = new ArrayList<>();
            this.valuesMillis =  new ArrayList<>();
            this.newDateStructure(day, month, year, hour, minute, second);
        }
        public void now(TypeDate typeDate) {
            long value = System.currentTimeMillis();
            this.valuesMillis.add(value);
            this.newDateStructure(value, typeDate);
        }
        public void today() {
            long now = System.currentTimeMillis();
            this.newDateStructure(now, TypeDate.day_month_year);
            this.newDateStructure(now, TypeDate.day_month_year_night);
        }
        public void week() {
            long now = System.currentTimeMillis();
            long plus = Utils.getTimeMillis(MILLIS_1_WEEK);

            this.valuesMillis.add(now);
            this.valuesMillis.add(now+plus);
            this.newDateStructure(now, TypeDate.day_month_year_hour_minute_second);
            this.newDateStructure(now + plus, TypeDate.day_month_year_hour_minute_second);
        }
        public void month() {
            long now = System.currentTimeMillis();
            long plus = Utils.getTimeMillis(MILLIS_1_MONTH);

            this.valuesMillis.add(now);
            this.valuesMillis.add(now+plus);
            this.newDateStructure(now, TypeDate.day_month_year_hour_minute_second);
            this.newDateStructure(now+plus, TypeDate.day_month_year_hour_minute_second);
        }
        public void newDateStructure(long millis, TypeDate typeDate) {
            Date date = new Date(millis);
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy");
            int year = Integer.parseInt(sdf.format(date.getTime()));
            sdf = new SimpleDateFormat("MM");
            int month = Integer.parseInt(sdf.format(date.getTime()));
            sdf = new SimpleDateFormat("dd");
            int day = Integer.parseInt(sdf.format(date.getTime()));
            sdf = new SimpleDateFormat("HH");
            int hour = Integer.parseInt(sdf.format(date.getTime()));
            sdf = new SimpleDateFormat("mm");
            int minute = Integer.parseInt(sdf.format(date.getTime()));
            sdf = new SimpleDateFormat("ss");
            int second = Integer.parseInt(sdf.format(date.getTime()));
            switch (typeDate) {
                case day_month_year:
                    this.newDateStructure(day, month, year, 0, 0, 0);
                    break;
                case day_month_year_hour_minute:
                    this.newDateStructure(day, month, year, hour, minute, 0);
                    break;
                case day_month_year_hour_minute_second:
                    this.newDateStructure(day, month, year, hour, minute, second);
                    break;
                case day_month_year_night:
                    this.newDateStructure(day, month, year, 23, 59, 59);
                    break;
            }
        }
        public void newDateStructure(int day, int month, int year, int hour, int minute, int second) {
            this.setDay(day);
            this.setMonth(month);
            this.setYear(year);
            this.setHour(hour);
            this.setMinute(minute);
            this.setSecond(second);
            String strThatDay = year + "-" + month + "-" + day + " " + hour + ":" + minute + ":" + second;
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            try {
                Date value = formatter.parse(strThatDay);//catch exception
                this.values.add(value);
            } catch (ParseException e) {

            }
        }
        public List<Long> getValuesMillis() {
            if(this.valuesMillis.size()<=0) {
                for ( Date value : this.values ) {
                    this.valuesMillis.add(value.getTime());
                }
            }
            return this.valuesMillis;
        }
    }

    public enum Compare {
        after, equal, before, error
    }
    public static String getResString(Context context, int resStringNum) {
        Resources res = context.getResources();
        return res.getString(resStringNum);
    }
    public static boolean CompareEdits(String edit1, String edit2) {
        return edit1.trim().equals(edit2.trim());
    }
    public static void hideKeyboard(Context context, IBinder i) {
        InputMethodManager inputManager = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);

        inputManager.hideSoftInputFromWindow( i , InputMethodManager.HIDE_NOT_ALWAYS);

    }
    public static int age(long dateFrom) {

        Calendar dateInit = Calendar.getInstance();
        dateInit.setTimeInMillis(dateFrom);

        int ano = dateInit.get(Calendar.YEAR);
        int mes = dateInit.get(Calendar.MONTH)+1;
        int dia = dateInit.get(Calendar.DAY_OF_MONTH);

        Calendar now = Calendar.getInstance();
        now.setTimeInMillis(System.currentTimeMillis());

        int anoA = now.get(Calendar.YEAR);
        int mesA = now.get(Calendar.MONTH)+1;
        int diaA = now.get(Calendar.DAY_OF_MONTH);

        int edad = anoA - ano;
        if(mes>=mesA)
        {
            if(mes==mesA)
            {
                if(dia>diaA)
                {
                    edad -= 1;
                }
            }
            else
            {
                edad -= 1;
            }
        }
        return edad;
    }
    public static boolean isEmailValid(String email) {
        boolean isValid = false;

        String expression = "^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$";
        CharSequence inputStr = email;

        Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(inputStr);
        if (matcher.matches()) {
            isValid = true;
        }
        return isValid;
    }
    public static Compare compareDates(long firstDate, long lastDate) {
        try {
            Date dFirstDate = new Date(firstDate);
            Date dLastDate = new Date(lastDate);

            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            dFirstDate = sdf.parse(sdf.format(dFirstDate.getTime()));
            dLastDate = sdf.parse(sdf.format(dLastDate.getTime()));

            if(dFirstDate.compareTo(dLastDate)>0){
                return Compare.after;
            }else if(dFirstDate.compareTo(dLastDate)<0){
                return Compare.before;
            }else if(dFirstDate.compareTo(dLastDate)==0){
                return Compare.equal;
            }else{
                return Compare.error;
            }
        }catch(ParseException ex){
            ex.printStackTrace();
            return Compare.error;
        }
    }
    public static long getTimeMillis(String m) {
        long millis = 0;
        switch (m) {
            case MILLIS_5_MINUT:
                millis = (5l * 60l) * 1000l;
                break;
            case MILLIS_10_MINUT:
                millis = (10l * 60l) * 1000l;
                break;
            case MILLIS_15_MINUT:
                millis = (15l * 60l) * 1000l;
                break;
            case MILLIS_30_MINUT:
                millis = (30l * 60l) * 1000l;
                break;
            case MILLIS_1_HOUR:
                millis = (60l * 60l) * 1000l;
                break;
            case MILLIS_2_HOUR:
                millis = (120l * 60l) * 1000l;
                break;
            case MILLIS_1_DAY:
                millis = ((60l * 60l) * 24l) * 1000l;
                break;
            case MILLIS_2_DAY:
                millis = ((60l * 60l) * 48l) * 1000l;
                break;
            case MILLIS_1_WEEK:
                millis = (((60l * 60l) * 24l) * 7l) * 1000l;
                break;
            case MILLIS_1_MONTH:
                millis = (((60l * 60l) * 24l) * 30l) * 1000l;
                break;
        }
        return millis;
    }
    public static Boolean conectWifi(Context context){
        ConnectivityManager connectivity = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivity != null) {
            NetworkInfo info = connectivity.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
            if (info != null) {
                if (info.isConnected()) {
                    return true;
                }
            }
        }
        return false;
    }
    public static Boolean conectMovilNet(Context context){
        ConnectivityManager connectivity = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivity != null) {
            NetworkInfo info = connectivity.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
            if (info != null) {
                if (info.isConnected()) {
                    return true;
                }
            }
        }
        return false;
    }

    public static String getDevice(Context context) {
        String android_id = Settings.Secure.getString(context.getContentResolver(),
                Settings.Secure.ANDROID_ID);
        return android_id;
    }
    public static Patient.QueryType readCurrentPatientQueryType(Context context) {
        SharedPreferences preferences2 = context.getSharedPreferences("PatientPreferences", context.MODE_PRIVATE);
        SharedPreferences preferences = ((Activity)context).getPreferences(Context.MODE_PRIVATE);
        String valueItem = preferences2.getString("currentPatientQueryType", "");

        if(valueItem.equals("")) {
            valueItem = preferences.getString("currentPatientQueryType", "");
            SharedPreferences.Editor editor = preferences2.edit();
            editor.putString("currentPatientQueryType", valueItem);
            editor.commit();
        }

        return  Patient.getQueryType(valueItem);
    }
    public  static String readCurrentPatientSearchQuery(Context context) {
        SharedPreferences preferences2 = context.getSharedPreferences("PatientPreferences", context.MODE_PRIVATE);
        SharedPreferences preferences = ((Activity)context).getPreferences(Context.MODE_PRIVATE);

        String valueItem = preferences2.getString("currentPatientSearchQuery", "");
        if(valueItem.equals("")) {
            valueItem = preferences.getString("currentPatientSearchQuery", "");
            SharedPreferences.Editor editor = preferences2.edit();
            editor.putString("currentPatientSearchQuery", valueItem);
            editor.commit();
        }

        return valueItem;

    }

    public static void writeCurrentPatientQueryType(Patient.QueryType queryType, Context context) {
        SharedPreferences preferences = context.getSharedPreferences("PatientPreferences", context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("currentPatientQueryType", Patient.QueryTypeToString(queryType));
        editor.commit();
    }

    public static void writeCurrentPatientSearchQuery(String searchQuery, Context context) {
        SharedPreferences preferences = context.getSharedPreferences("PatientPreferences", context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("currentPatientSearchQuery", searchQuery);
        editor.commit();
    }
    public  static Template getTemplate(Context context) {
        SharedPreferences preferences2 = context.getSharedPreferences("PatientPreferences", context.MODE_PRIVATE);
        SharedPreferences preferences = ((Activity)context).getPreferences(Context.MODE_PRIVATE);
        int idTemplate = preferences2.getInt("currentIdTemplate", 0);
        long idPatient = preferences2.getInt("currentIdPatient", 0);
        if(idTemplate==0) {
            idTemplate = Integer.valueOf(preferences.getString("currentIdTemplate", "0"));
            SharedPreferences.Editor editor = preferences2.edit();
            editor.putString("currentIdTemplate", String.valueOf(idTemplate));
            editor.commit();
            if(idTemplate==0) {
                return null;
            }
        }
        if(idPatient==0) {
            idPatient = Long.valueOf(preferences.getString("currentIdPatient", "0"));
            SharedPreferences.Editor editor = preferences2.edit();
            editor.putString("currentIdPatient", String.valueOf(idPatient));
            editor.commit();
            if(idPatient==0) {
                return null;
            }
        }
        Patient patient = new Patient(context, idPatient);
        Template template = new Template(context,patient, idTemplate);

        return template;
    }
    public  static Template getTemplate(Patient patient, Context context) {
        Template template = new Template(context,patient, patient.getIdTemplate());

        return template;
    }
    public static void setTemplate(Template template, Context context) {
        SharedPreferences preferences = context.getSharedPreferences("PatientPreferences", context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("currentIdTemplate", String.valueOf(template.getId()));
        editor.putString("currentIdPatient", String.valueOf(template.getPatient().getId()));
        editor.commit();
    }
    public static void CopyToSharedPreferences(Context context) {
        SharedPreferences preferences2 = context.getSharedPreferences("PatientPreferences", context.MODE_PRIVATE);
        SharedPreferences preferences = ((Activity)context).getPreferences(Context.MODE_PRIVATE);
        Map<String,?> keys = preferences.getAll();
        for(Map.Entry<String,?> entry : keys.entrySet()){
            SharedPreferences.Editor editor = preferences2.edit();
            editor.putString(entry.getKey(), entry.getValue().toString());
            editor.commit();
        }
    }
    public static void setPreferences(Context context, PreferencesDojo preferencesDojo) {
        SharedPreferences preferences = context.getSharedPreferences("PatientPreferences", context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("currentPatientQueryType", preferencesDojo.getCurrentPatientQueryType());
        editor.putString("currentIdTemplate", preferencesDojo.getCurrentIdTemplate());
        editor.putString("email", preferencesDojo.getEmail());
        editor.putString("currentPatientSearchQuery", preferencesDojo.getCurrentPatientSearchQuery());
        editor.putString("currentKey", preferencesDojo.getCurrentKey());
        editor.putString("person_key", preferencesDojo.getPerson_key());
        editor.putString("currentIdPatient", preferencesDojo.getCurrentIdPatient());
        editor.putString("password", preferencesDojo.getPassword());
        editor.commit();
    }
}
