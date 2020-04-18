package com.capetisoft.patients;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.capetisoft.patients.model.Patient;
import com.capetisoft.patients.util.Utils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Vector;

/**
 * Created by carlospedroza on 12/10/15.
 */
public class PatientAdapter extends BaseAdapter {
    private final Activity actividad;
    private final Vector<Patient> lista;

    public PatientAdapter(Activity actividad, Vector<Patient> lista) {
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

            LinearLayout patientItem = (LinearLayout) view.findViewById(R.id.patientItem);
            patientItem.setTag(lista.elementAt(position).getTag());

            ImageButton patientIcon = (ImageButton) view.findViewById(R.id.patientIcono);
            patientIcon.setTag(lista.elementAt(position).getTag());

            TextView namePatient = (TextView) view.findViewById(R.id.namePatient);
            namePatient.setText(lista.elementAt(position).getName());
            namePatient.setTag(lista.elementAt(position).getTag());

            TextView phonePatient = (TextView) view.findViewById(R.id.phonePatient);
            phonePatient.setText(lista.elementAt(position).getPhone());
            phonePatient.setTag(lista.elementAt(position).getTag());

            TextView emailPatient = (TextView) view.findViewById(R.id.emailPatient);
            emailPatient.setText(lista.elementAt(position).getEmail());
            emailPatient.setTag(lista.elementAt(position).getTag());

            ImageButton phoneCall = (ImageButton) view.findViewById(R.id.phoneCall);
            phoneCall.setTag(lista.elementAt(position).getPhone());

            ImageButton appointmentButton = (ImageButton) view.findViewById(R.id.appointmentButton);
            if (lista.elementAt(position).getIsAppointment() == 1) {
                Utils.Compare compare = Utils.compareDates(lista.elementAt(position).getAppointment(), System.currentTimeMillis());
                switch (compare) {
                    case after:
                    case equal:
                        appointmentButton.setImageResource(R.drawable.ic_event_green_36dp);
                        break;
                }
            }
            appointmentButton.setTag(lista.elementAt(position).getTag());

            TextView appointmentPatient = (TextView) view.findViewById(R.id.appointmentPatient);
            Utils.Compare appointmentCompare =  Utils.compareDates(lista.elementAt(position).getAppointment(), System.currentTimeMillis());
            if (lista.elementAt(position).getIsAppointment() == 1) {
                switch (appointmentCompare) {
                    case before:
                        appointmentPatient.setText("");
                        //showApoointmentButton.setImageResource(R.drawable.ic_event_white_24dp);
                        break;
                    case after:
                        //showApoointmentButton.setImageResource(R.drawable.ic_event_green_36dp);
                        Date dAppointment = new Date(lista.elementAt(position).getAppointment());
                        SimpleDateFormat sdf = new SimpleDateFormat(this.actividad.getResources().getString(R.string.dateTimeFormat));
                        String dateTimeString = sdf.format(dAppointment.getTime());
                        String _appointmentLabel = dateTimeString;
                        appointmentPatient.setText(_appointmentLabel);
                        break;
                    case equal:
                        //showApoointmentButton.setImageResource(R.drawable.ic_event_green_36dp);
                        Date dAppointment2 = new Date(lista.elementAt(position).getAppointment());
                        SimpleDateFormat sdf2 = new SimpleDateFormat(this.actividad.getResources().getString(R.string.timeFormat));
                        String timeString = sdf2.format(dAppointment2.getTime());
                        String _appointmentLabel2 = timeString;
                        appointmentPatient.setText(_appointmentLabel2);
                        break;
                }
            } else {
                appointmentPatient.setText("");
                //showApoointmentButton.setImageResource(R.drawable.ic_event_white_24dp);
            }

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
