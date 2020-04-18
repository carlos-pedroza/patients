package com.capetisoft.patients.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.capetisoft.patients.R;
import com.capetisoft.patients.ui.adapter.PatientFragmentAdapter;

/**
 * Created by carlospedroza on 27/11/15.
 */
public class PatientsFragment extends Fragment {
    public static final int NUM_COLUMNS = 1;
    //public static final String LOG_TAG = PatientsFragment.class.getName();

    private RecyclerView mPatientList;
    private PatientFragmentAdapter patientFragmentAdapter;
    SwipeRefreshLayout refreshLayout;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        patientFragmentAdapter = new PatientFragmentAdapter(getActivity());

    }

    public void changeList() {
        patientFragmentAdapter.changeList();
        refreshLayout.setRefreshing(false);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_patient_list, container, false);
        mPatientList = (RecyclerView) root.findViewById(R.id.patient_list);
        mPatientList.setLayoutManager( new GridLayoutManager(getActivity(), NUM_COLUMNS));
        mPatientList.setAdapter(patientFragmentAdapter);

        refreshLayout = (SwipeRefreshLayout) root.findViewById(R.id.swipeRefreshLayout);


        refreshLayout.setOnRefreshListener(
                new SwipeRefreshLayout.OnRefreshListener() {
                    @Override
                    public void onRefresh() {
                        changeList();
                    }
                }
        );


        return root;
    }

}
