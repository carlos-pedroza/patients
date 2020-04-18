package com.capetisoft.patients;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.capetisoft.patients.model.Visit;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Vector;

/**
 * Created by carlospedroza on 07/11/15.
 */
public class VisitListAdapter extends BaseAdapter {
    private final Activity activity;
    private final Vector<Visit> list;

    public VisitListAdapter(Activity activity, Vector<Visit> list) {
        super();
        this.activity = activity;
        this.list = list;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflanter = activity.getLayoutInflater();
        View view;


        view = inflanter.inflate(R.layout.visit_item, null, true);

        LinearLayout visitItem = (LinearLayout) view.findViewById(R.id.visitItem);
        visitItem.setTag(list.elementAt(position).getTag());

        TextView visitDateList = (TextView) view.findViewById(R.id.visitDateList);
        Date dvisit = new Date(list.elementAt(position).getDateVisit());
        SimpleDateFormat sdf = new SimpleDateFormat(this.activity.getResources().getString(R.string.dateTimeFormat));
        String dateTimeString = sdf.format(dvisit.getTime());
        visitDateList.setText(dateTimeString);

        ImageButton visitItemDelete = (ImageButton) view.findViewById(R.id.deleteVisit);
        visitItemDelete.setTag(list.elementAt(position).getTag());

        return view;
    }
    public int getCount() {
        return list.size();
    }
    public Object getItem(int arg0) {
        return list.elementAt(arg0);
    }
    public long getItemId(int position) {
        return position;
    }
}
