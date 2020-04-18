package com.capetisoft.patients.ui.status;

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

/**
 * Created by carlospedroza on 27/11/15.
 */
public class StatusFragment extends Fragment {
    public static final int NUM_COLUMNS = 1;

    private RecyclerView mStatusList;
    private StatusFragmentAdapter statusFragmentAdapter;
    SwipeRefreshLayout refreshLayout;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        statusFragmentAdapter = new StatusFragmentAdapter(getActivity());

    }

    public void changeList() {
        statusFragmentAdapter.changeList();
        refreshLayout.setRefreshing(false);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_status, container, false);
        mStatusList = (RecyclerView) root.findViewById(R.id.status_list);
        mStatusList.setLayoutManager( new GridLayoutManager(getActivity(), NUM_COLUMNS));
        mStatusList.setAdapter(statusFragmentAdapter);

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
