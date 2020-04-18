package com.capetisoft.patients;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

/**
 * Created by carlospedroza on 24/11/15.
 */
public class TemplateInfo extends Activity {

    String activityReturn;
    String patientTag;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.template_info);

        Bundle extra = getIntent().getExtras();
        activityReturn = extra.getString("activityReturn");
        patientTag = extra.getString("patientTag");
    }

    public void onBackButtonTemplateInfo(View view) {
        if(activityReturn.equals("addPatient")) {
            Intent i = new Intent(this, AddPatient.class);
            startActivity(i);
        }
        else {
            Intent i = new Intent(this, EditPatient.class);
            i.putExtra("patientTag", patientTag);
            startActivity(i);
        }
    }
}
