package com.capetisoft.patients;

import android.os.Bundle;
import android.preference.PreferenceActivity;

/**
 * Created by carlospedroza on 20/10/15.
 */
public class Preferences extends PreferenceActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.preferences);
    }
}
