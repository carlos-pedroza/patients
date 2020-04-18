package com.capetisoft.patients;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.capetisoft.patients.model.GlobalRepository;
import com.capetisoft.patients.model.Patient;
import com.capetisoft.patients.services.sync.SyncNotifications;
import com.capetisoft.patients.ui.PatientsFragment;
import com.capetisoft.patients.ui.status.StatusFragment;
import com.capetisoft.patients.util.Utils;

/**
 * Created by carlospedroza on 08/10/15.
 */
public class Main extends AppCompatActivity {
    //MessageView msgBox;

    FrameLayout mainContent;
    ImageButton buttonSearch;
    ImageButton buttonSearchCancel;
    private EditText searchEdit;
    Boolean searchOn;
    ImageButton btnAll;
    ImageButton btnToday;
    ImageButton btnWeek;
    ImageButton btnMonth;
    //ImageButton btnSyncStatus;
    TextView messageList;

    GlobalRepository globalRepository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        try {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.main);
            Utils.CopyToSharedPreferences(this);

            FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
            fab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onAddPatient(null);
                }
            });

            SyncNotifications syncNotifications = new SyncNotifications(this);
            String key = syncNotifications.getcurrentKey();

            if(key.isEmpty()) {
                Intent i = new Intent(this, Login.class);
                startActivity(i);
            }

            globalRepository =  new GlobalRepository(this);
            globalRepository.writeCurrentKey(key);
            Utils.writeCurrentPatientQueryType(Patient.QueryType.all, this);
            Utils.writeCurrentPatientSearchQuery("", this);


            mainContent = (FrameLayout) findViewById(R.id.main_container);
            buttonSearch = (ImageButton) findViewById(R.id.searchButton);
            buttonSearchCancel = (ImageButton) findViewById(R.id.searchCancelButton);
            this.btnAll = (ImageButton) findViewById(R.id.btnAll);
            this.btnToday = (ImageButton) findViewById(R.id.btnToday);
            this.btnWeek = (ImageButton) findViewById(R.id.btnWeek);
            this.btnMonth = (ImageButton) findViewById(R.id.btnMonth);
            //this.btnSyncStatus = (ImageButton) findViewById(R.id.btnSyncStatus);
            this.messageList = (TextView) findViewById(R.id.messageList);

            GlobalRepository.cs = "";
            if (savedInstanceState == null) {
                this.showList();
            }

            setSearchEdit((EditText) findViewById(R.id.searchEdit));
            searchOn = false;

            getSearchEdit().addTextChangedListener(new CustomTextWatcher(this, searchEdit));
        }
        catch (Exception ex) {
            ex.printStackTrace();
            Intent i = new Intent(this, Login.class);
            startActivity(i);
        }
    }
    private class CustomTextWatcher implements TextWatcher {
        private EditText mEditText;
        private Activity activity;

        public CustomTextWatcher(Activity activity, EditText e) {
            mEditText = e;
            this.activity = activity;
        }

        @Override
        public void onTextChanged(CharSequence cs, int arg1, int arg2, int arg3) {
            if (cs.toString().length() != 0) {
                Utils.writeCurrentPatientSearchQuery(cs.toString(), this.activity);
                // setListAdapter(new PatientAdapter(((Activity) searchEdit.getContext()), new Patient(searchEdit.getContext(), GlobalRepository.QueryType, GlobalRepository.PersonLog.getKey(), cs.toString()).getList()));
            } else {
                Utils.writeCurrentPatientSearchQuery("", this.activity);
                // setListAdapter(new PatientAdapter(((Activity) searchEdit.getContext()), new Patient(searchEdit.getContext(), GlobalRepository.QueryType, GlobalRepository.PersonLog.getKey()).getList()));
            }
            showList();
        }

        @Override
        public void beforeTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
        }

        @Override
        public void afterTextChanged(Editable arg0) {
        }
    }
    @Override
    public void onBackPressed() {
        //super.onBackPressed();
        Intent i = new Intent();
        i.setAction(Intent.ACTION_MAIN);
        i.addCategory(Intent.CATEGORY_HOME);
        this.startActivity(i);
    }
    public void onAddPatient(View view) {
        //this.msgBox.hide();
        Intent i = new Intent(this, AddPatient.class);
        startActivity(i);
    }

    public void onSearchButton(View view) {
        getSearchEdit().setVisibility(View.VISIBLE);
        buttonSearchCancel.setVisibility(View.VISIBLE);
        searchOn  = true;
        getSearchEdit().setFocusable(true);
        getSearchEdit().setFocusableInTouchMode(true);
        getSearchEdit().requestFocus();
        final InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
        inputMethodManager.showSoftInput(getSearchEdit(), InputMethodManager.SHOW_IMPLICIT);
    }

    public void onSearchCancelButton(View view) {
        this.searchButtonCancel();
        final InputMethodManager imm = (InputMethodManager)getSystemService(INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.HIDE_IMPLICIT_ONLY);
    }

    void searchButtonCancel() {
        getSearchEdit().setVisibility(View.INVISIBLE);
        getSearchEdit().setText("");
        buttonSearchCancel.setVisibility(View.INVISIBLE);
        searchOn = false;
    }
    public void onCallPhone(View view) {
        if(!view.getTag().toString().equals("")) {
            String phoneNumber = "tel:" + view.getTag();
            //Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse(phoneNumber));
            //startActivity(intent);
            Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + phoneNumber));
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            this.startActivity(intent);
        }
        else {
            Toast.makeText(this, Utils.getResString(this, R.string.phoneNumberInvalid), Toast.LENGTH_LONG).show();
        }
    }
    public void onApoointment(View view) {
        Intent i = new Intent(this, Appointment.class);
        i.putExtra("backAppointment", "main");
        i.putExtra("patientTag", view.getTag().toString());
        startActivity(i);
    }
    public void onShowPatient(View view) {
        Intent i = new Intent(this, PatientShow.class);
        i.putExtra("patientTag", view.getTag().toString());
        startActivity(i);
    }
    public void onPreferencesButton(View view) {
        Intent i = new Intent(this, Preferences.class);
        startActivity(i);
    }
    public void onAll(View view) {
        if(this.searchOn) {
            this.searchButtonCancel();
            InputMethodManager imm = (InputMethodManager)getSystemService(INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
        Utils.writeCurrentPatientQueryType(Patient.QueryType.all, this);
        this.showList();
    }
    public void onToday(View view) {
        if(this.searchOn) {
            this.searchButtonCancel();
            InputMethodManager imm = (InputMethodManager)getSystemService(INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
        Utils.writeCurrentPatientQueryType(Patient.QueryType.today, this);
        this.showList();
    }
    public void onWeek(View view) {
        if(this.searchOn) {
            this.searchButtonCancel();
            InputMethodManager imm = (InputMethodManager)getSystemService(INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
        Utils.writeCurrentPatientQueryType(Patient.QueryType.week, this);
        this.showList();
    }
    public void onMonth(View view) {
        if(this.searchOn) {
            this.searchButtonCancel();
            InputMethodManager imm = (InputMethodManager)getSystemService(INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
        Utils.writeCurrentPatientQueryType(Patient.QueryType.month, this);
        this.showList();
    }
    public void onSync(View view) {
        Intent i = new Intent(this, BackupActivity.class);
        startActivity(i);
    }
    public void showList()
    {
        Patient.QueryType queryType = Utils.readCurrentPatientQueryType(this);
        switch (queryType) {
            case all:
                this.messageList.setText(getResources().getString(R.string.messageListAll));
                this.btnAll.setBackgroundColor(getResources().getColor(R.color.button));
                this.btnToday.setBackgroundColor(getResources().getColor(R.color.backgroudappse));
                this.btnWeek.setBackgroundColor(getResources().getColor(R.color.backgroudappse));
                this.btnMonth.setBackgroundColor(getResources().getColor(R.color.backgroudappse));
                //this.btnSyncStatus.setBackgroundColor(getResources().getColor(R.color.backgroudappse));
                this.createList();
                break;
            case today:
                this.messageList.setText(getResources().getString(R.string.messageListToday));
                this.btnAll.setBackgroundColor(getResources().getColor(R.color.backgroudappse));
                this.btnToday.setBackgroundColor(getResources().getColor(R.color.button));
                this.btnWeek.setBackgroundColor(getResources().getColor(R.color.backgroudappse));
                this.btnMonth.setBackgroundColor(getResources().getColor(R.color.backgroudappse));
                //this.btnSyncStatus.setBackgroundColor(getResources().getColor(R.color.backgroudappse));
                this.createList();
                break;
            case week:
                this.messageList.setText(getResources().getString(R.string.messageListWeek));
                this.btnAll.setBackgroundColor(getResources().getColor(R.color.backgroudappse));
                this.btnToday.setBackgroundColor(getResources().getColor(R.color.backgroudappse));
                this.btnWeek.setBackgroundColor(getResources().getColor(R.color.button));
                this.btnMonth.setBackgroundColor(getResources().getColor(R.color.backgroudappse));
                //this.btnSyncStatus.setBackgroundColor(getResources().getColor(R.color.backgroudappse));
                this.createList();
                break;
            case month:
                this.messageList.setText(getResources().getString(R.string.messageListMonth));
                this.btnAll.setBackgroundColor(getResources().getColor(R.color.backgroudappse));
                this.btnToday.setBackgroundColor(getResources().getColor(R.color.backgroudappse));
                this.btnWeek.setBackgroundColor(getResources().getColor(R.color.backgroudappse));
                this.btnMonth.setBackgroundColor(getResources().getColor(R.color.button));
                //this.btnSyncStatus.setBackgroundColor(getResources().getColor(R.color.backgroudappse));
                this.createList();
                break;
            case sync:
                this.messageList.setText(getResources().getString(R.string.messageListSync));
                this.btnAll.setBackgroundColor(getResources().getColor(R.color.backgroudappse));
                this.btnToday.setBackgroundColor(getResources().getColor(R.color.backgroudappse));
                this.btnWeek.setBackgroundColor(getResources().getColor(R.color.backgroudappse));
                this.btnMonth.setBackgroundColor(getResources().getColor(R.color.backgroudappse));
                //this.btnSyncStatus.setBackgroundColor(getResources().getColor(R.color.button));
                this.createstatusList();
                break;
        }
    }
    public void createList()
    {
        mainContent.removeAllViews();
        getSupportFragmentManager().beginTransaction().add(R.id.main_container,  new PatientsFragment()).commit();
        //setListAdapter(new PatientAdapter(this, new Patient(this, GlobalRepository.QueryType, GlobalRepository.PersonLog.getKey()).getList()));
    }
    public void createstatusList()
    {
        mainContent.removeAllViews();
        getSupportFragmentManager().beginTransaction().add(R.id.main_container,  new StatusFragment()).commit();
        //setListAdapter(new PatientAdapter(this, new Patient(this, GlobalRepository.QueryType, GlobalRepository.PersonLog.getKey()).getList()));
    }

    public EditText getSearchEdit() {
        return searchEdit;
    }

    public void setSearchEdit(EditText searchEdit) {
        this.searchEdit = searchEdit;
    }

}
