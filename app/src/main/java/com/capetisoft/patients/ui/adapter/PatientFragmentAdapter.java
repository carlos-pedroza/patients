package com.capetisoft.patients.ui.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.capetisoft.patients.R;
import com.capetisoft.patients.model.GlobalRepository;
import com.capetisoft.patients.model.Patient;
import com.capetisoft.patients.services.sync.SyncNotifications;
import com.capetisoft.patients.util.Utils;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by carlospedroza on 27/11/15.
 */
public class PatientFragmentAdapter extends RecyclerView.Adapter<PatientFragmentAdapter.PatientViewHolder> {

    Context context;
    ArrayList<Patient> patients;
    Patient patient;
    GlobalRepository globalRepository;

    public PatientFragmentAdapter(Context context) {
        this.context = context;
        patients = new ArrayList<>();
        globalRepository =  new GlobalRepository(context);

        changeList();
    }

    public void changeList() {
        patients.clear();
        SyncNotifications syncNotifications = new SyncNotifications(this.context);
        String key = syncNotifications.getcurrentKey();
        Patient.QueryType queryType = Utils.readCurrentPatientQueryType(this.context);
        String cs = Utils.readCurrentPatientSearchQuery(this.context);

        if (cs.equals("")) {
            patient = new Patient(context, queryType, key);
        } else {
            patient = new Patient(context, queryType, key, cs);
        }

        this.addAll(patient.getList());

    }

    @Override
    public PatientViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(context).inflate(R.layout.patient_item, parent, false);
        return new PatientViewHolder(itemView);
    }


    @Override
    public void onBindViewHolder(PatientViewHolder holder, int position) {
        Patient patient = patients.get(position);
        holder.setPatientInfo(context, patient);
    }

    @Override
    public int getItemCount() {
        return patients.size();
    }

    public void addAll(@NonNull ArrayList<Patient> patients) {
        if(patients==null)
            throw new NullPointerException("Item cannot be null");

        this.patients.addAll(patients);
        notifyDataSetChanged();

    }

    public class PatientViewHolder extends RecyclerView.ViewHolder {

        LinearLayout itemLetter;
        TextView letterGroup;
        LinearLayout patientItem;
        ImageButton patientIcon;
        TextView namePatient;
        TextView phonePatient;
        TextView emailPatient;
        ImageButton phoneCall;
        ImageButton appointmentButton;
        TextView appointmentPatient;

        public PatientViewHolder(View view) {
            super(view);
            itemLetter = (LinearLayout) view.findViewById(R.id.itemLetter);
            letterGroup =  (TextView) view.findViewById(R.id.letterGroup);

            patientItem = (LinearLayout) view.findViewById(R.id.patientItem);
            patientIcon = (ImageButton) view.findViewById(R.id.patientIcono);
            namePatient = (TextView) view.findViewById(R.id.namePatient);
            phonePatient = (TextView) view.findViewById(R.id.phonePatient);
            emailPatient = (TextView) view.findViewById(R.id.emailPatient);
            phoneCall = (ImageButton) view.findViewById(R.id.phoneCall);
            appointmentButton = (ImageButton) view.findViewById(R.id.appointmentButton);
            appointmentPatient = (TextView) view.findViewById(R.id.appointmentPatient);
        }

        public void setPatientInfo(Context context, Patient patient) {
            String  _key = patient.getKey();
            if(_key.equals("letter")) {
                patientItem.setVisibility(View.GONE);
                itemLetter.setVisibility(View.VISIBLE);

                letterGroup.setText(patient.getName());
            }
            else {
                patientItem.setVisibility(View.VISIBLE);
                itemLetter.setVisibility(View.GONE);

                patientItem.setTag(patient.getTag());
                patientIcon.setTag(patient.getTag());

                namePatient.setText(patient.getName());
                namePatient.setTag(patient.getTag());

                phonePatient.setText(patient.getPhone());
                phonePatient.setTag(patient.getTag());

                emailPatient.setText(patient.getEmail());
                emailPatient.setTag(patient.getTag());

                phoneCall.setTag(patient.getPhone());

                appointmentButton.setImageResource(R.drawable.ic_event_black_36dp);
                if (patient.getIsAppointment() == 1) {
                    Utils.Compare compare = Utils.compareDates(patient.getAppointment(), System.currentTimeMillis());
                    switch (compare) {
                        case after:
                        case equal:
                            appointmentButton.setImageResource(R.drawable.ic_event_green_36dp);
                            break;
                    }
                }
                appointmentButton.setTag(patient.getTag());

                Utils.Compare appointmentCompare =  Utils.compareDates(patient.getAppointment(), System.currentTimeMillis());
                if (patient.getIsAppointment() == 1) {
                    switch (appointmentCompare) {
                        case before:
                            appointmentPatient.setText("");
                            break;
                        case after:
                            Date dAppointment = new Date(patient.getAppointment());
                            SimpleDateFormat sdf = new SimpleDateFormat(context.getString(R.string.dateTimeFormat));
                            String dateTimeString = sdf.format(dAppointment.getTime());
                            String _appointmentLabel = dateTimeString;
                            appointmentPatient.setText(_appointmentLabel);
                            break;
                        case equal:
                            Date dAppointment2 = new Date(patient.getAppointment());
                            SimpleDateFormat sdf2 = new SimpleDateFormat(context.getResources().getString(R.string.timeFormat));
                            String timeString = sdf2.format(dAppointment2.getTime());
                            String _appointmentLabel2 = timeString;
                            appointmentPatient.setText(_appointmentLabel2);
                            break;
                    }
                } else {
                    appointmentPatient.setText("");
                }

            }
        }
    }
}
