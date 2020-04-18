package com.capetisoft.patients;

import android.accounts.AccountManager;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.capetisoft.patients.model.GlobalRepository;
import com.capetisoft.patients.model.Person;
import com.capetisoft.patients.services.PatientSyncAndNotifications;
import com.capetisoft.patients.services.sync.SyncNotifications;
import com.capetisoft.patients.util.MessageView;
import com.capetisoft.patients.util.Utils;

public class Login extends AppCompatActivity {
    //TextView etEmail;
    //TextView etPassword;
    //CheckBox cbRememberPassword;
    Button btnLogin;
    //Button btnRegister;

    //GoogleCredential credential;

    ProgressDialog progress;

    MessageView msgBox;
    static final int SELECT_ACCOUNT_CODE = 1;

    LinearLayout manualAccountType;
    EditText accountManual;

    //TextView labelLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Utils.CopyToSharedPreferences(this);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onLogin(null);
            }
        });

        Intent intent = new Intent(this, PatientSyncAndNotifications.class);
        startService(intent);

        this.msgBox = new MessageView(findViewById(R.id.contentMainView));

        manualAccountType = (LinearLayout) findViewById(R.id.manualAccountType);
        accountManual = (EditText) findViewById(R.id.accountManual);


        //etEmail = (TextView)findViewById(R.id.etEmail);
        //etPassword = (TextView)findViewById(R.id.etPassword);
        //cbRememberPassword = (CheckBox)findViewById(R.id.rememberPassword);

        //btnRegister = (Button) findViewById(R.id.register);
        //labelLogin = (TextView) findViewById(R.id.labelLogin);

        SharedPreferences preferences = getPreferences(MODE_PRIVATE);

        String _password = "";//preferences.getString(Utils.PREFERENCES_PASSWORD,"");
        Boolean _rememberPassword = false;//preferences.getBoolean(Utils.PREFERENCES_REMEMBER_PASSWORD, false);
        //etEmail.setText(_email);
        //etPassword.setText(_password);
        //cbRememberPassword.setChecked(_rememberPassword);

        //borrar en sincronizacion
        //GlobalRepository.setIsLoadTemplate(true);
    }

    void getAccountGmail() {
        /*
        SharedPreferences preferences = this.getSharedPreferences("PatientPreferences", this.MODE_PRIVATE);
        String accountName = preferences.getString("currentEmail", "");
        if(accountName.isEmpty()) {
            if(manualAccountType.getVisibility()==View.INVISIBLE) {
                String possibleEmail = "";
                Pattern emailPattern = Patterns.EMAIL_ADDRESS; // API level 8+
                Account[] accounts = AccountManager.get(this).getAccounts();
                for (Account account : accounts) {
                    if (emailPattern.matcher(account.name).matches()) {
                        possibleEmail = account.name;
                    }
                }
                if (possibleEmail.isEmpty()) {
                    try {
                        Intent googlePicker = AccountPicker.newChooseAccountIntent(null, null, new String[]{GoogleAuthUtil.GOOGLE_ACCOUNT_TYPE}, true, null, null, null, null);
                        startActivityForResult(googlePicker, SELECT_ACCOUNT_CODE);
                    } catch (ActivityNotFoundException ex) {
                        manualAccountType.setVisibility(View.VISIBLE);
                    }
                } else {
                    SharedPreferences.Editor editor = preferences.edit();
                    editor.putString("currentEmail", possibleEmail);
                    editor.commit();
                    Intent i = new Intent(this, Main.class);
                    startActivity(i);
                }
            }
            else {
                accountName = accountManual.getText().toString();
                if(!accountName.isEmpty()) {
                    SharedPreferences.Editor editor = preferences.edit();
                    editor.putString("currentEmail", accountName);
                    editor.commit();
                    Intent i = new Intent(this, Main.class);
                    startActivity(i);
                }
                else {
                    this.msgBox.setMessage(Utils.getResString(this, R.string.mostTypeAccount), MessageView.MessageType.ERROR);
                }
            }
        }
        else {
        */
            Intent i = new Intent(this, Main.class);
            startActivity(i);
        //}
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_login, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    public void onLogin(View view) {
        //if(Utils.isEmailValid(etEmail.getText().toString())) {

        progress = ProgressDialog.show(this, getResources().getString(R.string.loginButton),
                getResources().getString(R.string.loginMsg), true);
        //this.loginMsg(getResources().getString(R.string.loginMsg));

        //Person person = new Person(this, etEmail.getText().toString(), etPassword.getText().toString());
        Person person = Person.readKey(this);
        if (person!=null) {
            doLogin(person);
        }
        else {
            long lastTime = System.currentTimeMillis();
            person = new Person(this);
            person.setName(Utils.getDevice(this));
            person.setEmail(Utils.getDevice(this));
            person.setPassword("");
            person.setLastupdate(lastTime);
            person.setCreatedate(lastTime);
            person.setServerSync(0);
            person.insert();
            doLogin(person);
        }

            /*} else {
                if (Utils.conectWifi(this.getBaseContext()) || Utils.conectMovilNet(this.getBaseContext())) {
                    this.loginMsg(getResources().getString(R.string.syncLoginProcess));
                    Person personCloud = new Person(etEmail.getText().toString(), etPassword.getText().toString());
                    SyncLog syncLog = new SyncLog(this);
                    syncLog.init(personCloud);
                } else {
                    SyncNotifications syncNotifications = new SyncNotifications(this);
                    String msg = getResources().getString(R.string.syncLoginNoExistsNoconnect);
                    syncNotifications.AddSyncNotification(SyncNotifications.SyncStatus.error, msg);
                    this.noLogin(msg);
                }
            }*/
        //}
        //else {
        //    this.noLogin2(getResources().getString(R.string.emailNoValid));
        //}
    }

    public void doLogin(Person person) {
        String welcome =  Utils.getResString(this,R.string.messageWelcomeLog);
        Toast.makeText(this, welcome, Toast.LENGTH_SHORT).show();
        SharedPreferences preferences = this.getSharedPreferences("PatientPreferences", this.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(Utils.PREFERENCES_EMAIL, Utils.getDevice(this));
        //editor.putBoolean(Utils.PREFERENCES_REMEMBER_PASSWORD, cbRememberPassword.isChecked());
        SyncNotifications syncNotifications = new SyncNotifications(this);
        syncNotifications.setcurrentKey(person.getKey());
        editor.putString(Utils.PREFERENCES_KEY, person.getKey());
        //if(cbRememberPassword.isChecked()) {
            //editor.putString(Utils.PREFERENCES_PASSWORD, etPassword.getText().toString());
        //}
        //else {
            editor.putString(Utils.PREFERENCES_PASSWORD, "");
        //}
        editor.commit();
        GlobalRepository.PersonLog = person;
        progress.dismiss();
        this.goMain();
    }

    public void noLogin(String msg) {
        //etPassword.setText("");
        progress.dismiss();
    }
    public void noLogin2(String msg) {
        //etPassword.setText("");
    }
    public void noLoginError(String msg) {
        //etPassword.setText("");
        progress.dismiss();
    }

    public void onRegister(View view) {
        Intent i = new Intent(this, Register.class);
        startActivity(i);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        SharedPreferences preferences = this.getSharedPreferences("PatientPreferences", this.MODE_PRIVATE);
        if (requestCode == SELECT_ACCOUNT_CODE && resultCode == RESULT_OK) {
            String accountName = data.getStringExtra(AccountManager.KEY_ACCOUNT_NAME);
            if(accountName.isEmpty()) {
                this.msgBox.setMessage(Utils.getResString(this, R.string.mostSelectAccount), MessageView.MessageType.ERROR);
            }
            else {
                SharedPreferences.Editor editor = preferences.edit();
                editor.putString("currentEmail", accountName);
                editor.commit();
                Intent i = new Intent(this, Main.class);
                startActivity(i);
            }
        }
        else {
            this.msgBox.setMessage(Utils.getResString(this, R.string.mostSelectAccount), MessageView.MessageType.ERROR);
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

    void goMain() {
        getAccountGmail();
    }

    public void loginMsg(String msg) {

        //labelLogin.setText(msg);
    }

    public void onRecoverPassword(String result, boolean isOk) {
        String results[] = result.split(",");

        String msg = results[0];

        if(isOk) {
            Person person = new Person(this);
            person.setKey(results[1]);
            person.setName(results[2]);
            person.setEmail(results[3]);
            person.setPassword2(results[4]);
            long time = System.currentTimeMillis();
            person.setLastupdate(time);
            person.setCreatedate(time);
            person.setServerSync(time);

            person.update();

            AlertDialog.Builder builder1 = new AlertDialog.Builder(this);
            builder1.setTitle(getResources().getString(R.string.registerExistsTitle));
            builder1.setMessage(msg);
            builder1.setCancelable(true);
            builder1.setNeutralButton(android.R.string.ok,
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            dialog.cancel();
                        }
                    });

            AlertDialog alert11 = builder1.create();
            alert11.show();
        }
        else {
            Toast.makeText(this, msg + " - " +  MessageView.MessageType.ERROR, Toast.LENGTH_SHORT).show();
        }

        progress.dismiss();
    }

    /*public void onBtnRecoverPassword(View view) {
        if(Utils.isEmailValid(this.etEmail.getText().toString())) {
            this.confirmRecoverPassword();
        }
        else {
            this.noLogin2(getResources().getString(R.string.emailNoValid));
        }
    }*/

    public void confirmRecoverPassword() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(getResources().getString(R.string.recoverMsgDialog)).setPositiveButton(getResources().getString(R.string.yes), dialogClickListener)
                .setNegativeButton(getResources().getString(R.string.no), dialogClickListener).show();
    }
    DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialog, int which) {
            switch (which){
                case DialogInterface.BUTTON_POSITIVE:
                    progress = ProgressDialog.show(Login.this, getResources().getString(R.string.recoverProcessTitle),
                            getResources().getString(R.string.recoverProcess), true);

                    //SyncRecoverPassword syncRecoverPassword = new SyncRecoverPassword(Login.this, Login.this.etEmail.getText().toString());
                    //syncRecoverPassword.init();
                    break;

                case DialogInterface.BUTTON_NEGATIVE:

                    break;
            }
        }
    };

/*
    private void startTimerThread() {
        Thread th = new Thread(new Runnable() {
            private long startTime = System.currentTimeMillis();
            public void run() {
                while (true) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            _textOut.setText(""+((System.currentTimeMillis()-startTime)/1000));
                        }
                    });
                    try {
                        Thread.sleep(1000);
                    }
                    catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        th.start();
    }
*/
}
