package com.capetisoft.patients;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.capetisoft.patients.util.Utils;

/**
 * Created by carlospedroza on 10/10/15.
 */
public class setRecordDialog extends Activity {

    String patientTag;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.set_record);

        Bundle extra = getIntent().getExtras();
        patientTag = extra.getString("patientTag");

    }

    @Override
    public void onBackPressed() {
        Toast.makeText(this, Utils.getResString(this, R.string.dialogRecordYes), Toast.LENGTH_SHORT).show();
        Intent i = new Intent(this, Main.class);
        startActivity(i);
    }
    public void onSetYes(View view) {
        Intent i = new Intent(this, EditTemplate.class);
        i.putExtra("patientTag", this.patientTag);
        startActivity(i);
    }
    public void onSetNo(View view) {
        Toast.makeText(this, Utils.getResString(this, R.string.dialogRecordNo), Toast.LENGTH_SHORT).show();
        Intent i = new Intent(this, Main.class);
        startActivity(i);
    }
}
