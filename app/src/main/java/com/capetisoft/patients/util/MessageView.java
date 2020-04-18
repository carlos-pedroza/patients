package com.capetisoft.patients.util;

import android.content.Context;
import android.graphics.Color;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.widget.TextView;

/**
 * Created by carlospedroza on 03/10/15.
 */
public class MessageView {
    Context context;
    View messageBox;
    public enum MessageType {
        OK,WARNING,ERROR

    }
    public MessageView(View msgView) {
        context = msgView.getContext();
        messageBox = msgView;
    }

    public void setMessage(String msg, MessageType messageType) {
        Snackbar snackbar = Snackbar.make(messageBox, msg, Snackbar.LENGTH_LONG);
        View view2 = snackbar.getView();
        TextView tv = (TextView) view2.findViewById(android.support.design.R.id.snackbar_text);
        tv.setTextColor(Color.WHITE);
        switch (messageType) {
            case OK:
                tv.setBackgroundColor(Color.GREEN);
                break;
            case WARNING:
                tv.setBackgroundColor(Color.YELLOW);
                break;
            case ERROR:
                tv.setBackgroundColor(Color.parseColor("#ff8c80"));
                break;
        }
        snackbar.setAction("Action", null).show();
        //this.messageBox.setText(this.messageBox.getText() + "," + msg);
    }
    public void hide() {

    }
}
