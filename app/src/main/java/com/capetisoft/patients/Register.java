package com.capetisoft.patients;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.capetisoft.patients.model.Person;
import com.capetisoft.patients.services.io.PersonExistsAdapter;
import com.capetisoft.patients.services.io.model.ResultServiceExPe;
import com.capetisoft.patients.util.MessageView;
import com.capetisoft.patients.util.Utils;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by carlospedroza on 02/10/15.
 */
public class Register  extends AppCompatActivity implements Callback<ResultServiceExPe> {

    MessageView msgBox;
    String name;
    String email;
    String emailConfirm;
    String password;
    String passwordConfirm;
    Person person;
    ImageButton registerButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register);

        this.msgBox = new MessageView((TextView) findViewById(R.id.msgBox));

        registerButton = (ImageButton) findViewById(R.id.addPatientButton);

        registerButton.setEnabled(true);

        person = new Person(this);
    }

    public void onRegisterButton(View view) {
        if(validForm())
        {
            registerButton.setEnabled(false);

            Toast.makeText(this,Utils.getResString(this, R.string.messageRegisterDo), Toast.LENGTH_SHORT).show();
            person.setName(name);
            person.setEmail(email.trim());
            person.setPassword(password);
            if(person.exist()) {
                registerButton.setEnabled(true);
                this.msgBox.setMessage(Utils.getResString(this, R.string.personExist), MessageView.MessageType.ERROR);
            }
            else {
                this.msgBox.hide();
                PersonExistsAdapter.getApiService().existsPersonService(person.getEmail(), this);
            }

        }
    }
    boolean validForm() {
        name = ((TextView)findViewById(R.id.name)).getText().toString();
        email = ((TextView)findViewById(R.id.email)).getText().toString();
        emailConfirm = ((TextView)findViewById(R.id.emailConfirm)).getText().toString();;
        password = ((TextView)findViewById(R.id.password)).getText().toString();
        passwordConfirm = ((TextView)findViewById(R.id.passwordConfirm)).getText().toString();

        if(name.trim().equals("")) {
            this.msgBox.setMessage(Utils.getResString(this, R.string.nameEmpty), MessageView.MessageType.ERROR);
            return false;
        }
        else if (email.trim().equals(""))
        {
            this.msgBox.setMessage(Utils.getResString(this, R.string.emailEmpty), MessageView.MessageType.ERROR);
            return false;
        }
        else if (password.trim().equals(""))
        {
            this.msgBox.setMessage(Utils.getResString(this, R.string.passwordEmpty), MessageView.MessageType.ERROR);
            return false;
        }
        else if (!email.trim().equals(emailConfirm.trim()))
        {
            this.msgBox.setMessage(Utils.getResString(this, R.string.emailNotEqual), MessageView.MessageType.ERROR);
            return false;
        }
        else if (!password.trim().equals(passwordConfirm.trim()))
        {
            this.msgBox.setMessage(Utils.getResString(this, R.string.passwordNotEqual), MessageView.MessageType.ERROR);
            return false;
        }
        else  if(!Utils.isEmailValid(email)) {
            this.msgBox.setMessage(Utils.getResString(this, R.string.emailNoValid), MessageView.MessageType.ERROR);
            return false;
        }
        else {
            return true;
        }
    }
    public void onBack(View view) {
        goLogin();
    }
    public void goLogin() {
        Intent i = new Intent(this, Login.class);
        startActivity(i);
    }


    @Override
    public void success(ResultServiceExPe ResultServiceExPe, Response response) {
        if(ResultServiceExPe.getData().size()>0) {
            if (ResultServiceExPe.getData().get(0).getTotal() == 0) {
                person.insert();
                Toast.makeText(this, Utils.getResString(this, R.string.messageRegisterDone), Toast.LENGTH_SHORT).show();
                goLogin();
            } else {
                registerButton.setEnabled(true);
                AlertDialog.Builder builder1 = new AlertDialog.Builder(this);
                builder1.setTitle(getResources().getString(R.string.registerExistsTitle));
                builder1.setMessage(getResources().getString(R.string.registerExistsMsg));
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
        }
    }

    @Override
    public void failure(RetrofitError error) {
        error.printStackTrace();
        person.insert();
        Toast.makeText(this, Utils.getResString(this, R.string.messageRegisterDoneNoSync), Toast.LENGTH_LONG).show();
        goLogin();
    }
}
