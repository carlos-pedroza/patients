package com.capetisoft.patients;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

import com.capetisoft.patients.model.Patient;
import com.capetisoft.patients.util.Utils;

import java.util.Vector;

/**
 * Created by carlospedroza on 12/10/15.
 */
public class PatientAppointmentAdapter extends BaseAdapter {
    private final Activity actividad;
    private final Vector<Patient> lista;

    public PatientAppointmentAdapter(Activity actividad, Vector<Patient> lista) {
        super();
        this.actividad = actividad;
        this.lista = lista;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflanter = actividad.getLayoutInflater();
        View view;

        String  _key = lista.elementAt(position).getKey();
        if(_key.equals("letter")) {
            view = inflanter.inflate(R.layout.letter_space, null, true);

            TextView letterGroup = (TextView)view.findViewById(R.id.letterGroup);
            letterGroup.setText(lista.elementAt(position).getName());
            letterGroup.setVisibility(View.VISIBLE);
        }
        else {
            view = inflanter.inflate(R.layout.patient_item, null, true);
            TextView namePatient = (TextView)view.findViewById(R.id.namePatient);
            namePatient.setText(lista.elementAt(position).getName());

            TextView phonePatient = (TextView)view.findViewById(R.id.phonePatient);
            phonePatient.setText(lista.elementAt(position).getPhone());

            TextView emailPatient = (TextView)view.findViewById(R.id.emailPatient);
            emailPatient.setText(lista.elementAt(position).getEmail());

            ImageButton phoneCall = (ImageButton)view.findViewById(R.id.phoneCall);
            phoneCall.setTag(lista.elementAt(position).getPhone());

            ImageButton appointmentButton = (ImageButton)view.findViewById(R.id.appointmentButton);
            if(lista.elementAt(position).getIsAppointment()==1) {
                Utils.Compare compare = Utils.compareDates(lista.elementAt(position).getAppointment(), System.currentTimeMillis());
                switch (compare) {
                    case after:
                    case equal:
                        appointmentButton.setImageResource(R.drawable.ic_event_green_36dp);
                        break;
                }
            }
            appointmentButton.setTag(lista.elementAt(position).getTag());
        }

        return view;
    }
    public int getCount() {
        return lista.size();
    }
    public Object getItem(int arg0) {
        return lista.elementAt(arg0);
    }
    public long getItemId(int position) {
        return position;
    }
}
