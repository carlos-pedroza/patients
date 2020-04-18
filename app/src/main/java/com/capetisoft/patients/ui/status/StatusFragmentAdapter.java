package com.capetisoft.patients.ui.status;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.capetisoft.patients.R;
import com.capetisoft.patients.model.GlobalRepository;
import com.capetisoft.patients.services.sync.SyncNotifications;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by carlospedroza on 27/11/15.
 */
public class StatusFragmentAdapter extends RecyclerView.Adapter<StatusFragmentAdapter.StatusViewHolder> {

    Context context;
    ArrayList<SyncNotifications.ProcessSync> processSyncs;
    SyncNotifications.ProcessSync processSync;
    GlobalRepository globalRepository;

    public StatusFragmentAdapter(Context context) {
        this.context = context;
        processSyncs = new ArrayList<>();
        globalRepository =  new GlobalRepository(context);

        changeList();
    }

    public void changeList() {
        processSyncs.clear();
        SyncNotifications syncNotifications = new SyncNotifications(this.context);
        String key = syncNotifications.getcurrentKey();


        processSyncs = syncNotifications.getSyncStatusList();

        this.addAll(processSyncs);

    }

    @Override
    public StatusViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(context).inflate(R.layout.framgment_status_item, parent, false);
        return new StatusViewHolder(itemView);
    }


    @Override
    public void onBindViewHolder(StatusViewHolder holder, int position) {
        SyncNotifications.ProcessSync processSync = processSyncs.get(position);
        holder.setStatusInfo(context, processSync);
    }

    @Override
    public int getItemCount() {
        return processSyncs.size();
    }

    public void addAll(@NonNull ArrayList<SyncNotifications.ProcessSync> processSyncs) {
        if(processSyncs==null)
            throw new NullPointerException("Item cannot be null");

        this.processSyncs.addAll(processSyncs);
        notifyDataSetChanged();

    }

    public class StatusViewHolder extends RecyclerView.ViewHolder {

        LinearLayout statusItemGreen;
        TextView statusDateGreen;
        TextView statusMsgGreen;

        LinearLayout statusItemYellow;
        TextView statusDateYellow;
        TextView statusMsgYellow;

        LinearLayout statusItemRed;
        TextView statusDateRed;
        TextView statusMsgRed;

        public StatusViewHolder(View view) {
            super(view);
            statusItemGreen = (LinearLayout) view.findViewById(R.id.statusItemGreen);
            statusDateGreen =  (TextView) view.findViewById(R.id.statusDateGreen);
            statusMsgGreen =  (TextView) view.findViewById(R.id.statusMsgGreen);

            statusItemYellow = (LinearLayout) view.findViewById(R.id.statusItemYellow);
            statusDateYellow =  (TextView) view.findViewById(R.id.statusDateYellow);
            statusMsgYellow =  (TextView) view.findViewById(R.id.statusMsgYellow);

            statusItemRed = (LinearLayout) view.findViewById(R.id.statusItemRed);
            statusDateRed =  (TextView) view.findViewById(R.id.statusDateRed);
            statusMsgRed =  (TextView) view.findViewById(R.id.statusMsgRed);
        }

        public void setStatusInfo(Context context, SyncNotifications.ProcessSync processSync ) {
            Date createTime = new Date(processSync.getCreateTime());
            SimpleDateFormat sdf = new SimpleDateFormat(context.getString(R.string.dateTimeFormatStatus));
            String createTimeStr = sdf.format(createTime.getTime());
            switch (processSync.getSyncStatus()) {
                case ok:
                    statusItemGreen.setVisibility(View.VISIBLE);
                    statusItemYellow.setVisibility(View.GONE);
                    statusItemRed.setVisibility(View.GONE);

                    statusDateGreen.setText(createTimeStr);
                    statusMsgGreen.setText(processSync.getMsg());

                    break;
                case warning:
                    statusItemGreen.setVisibility(View.GONE);
                    statusItemYellow.setVisibility(View.VISIBLE);
                    statusItemRed.setVisibility(View.GONE);

                    statusDateYellow.setText(createTimeStr);
                    statusMsgYellow.setText(processSync.getMsg());
                    break;
                case error:
                    statusItemGreen.setVisibility(View.GONE);
                    statusItemYellow.setVisibility(View.GONE);
                    statusItemRed.setVisibility(View.VISIBLE);

                    statusDateRed.setText(createTimeStr);
                    statusMsgRed.setText(processSync.getMsg());
                    break;
            }

        }
    }
}
