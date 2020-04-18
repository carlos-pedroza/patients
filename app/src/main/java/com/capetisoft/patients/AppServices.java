package com.capetisoft.patients;

import android.widget.TextView;

/**
 * Created by carlospedroza on 01/10/15.
 */
public class AppServices extends Thread {
    private int timeLapse = 20000;
    public TextView ViewDraw;
    private long lastProcess;

    private int c;

    public AppServices(TextView view)
    {
        this.ViewDraw = view;
        this.lastProcess = System.currentTimeMillis();
        this.c=0;
    }
    @Override
    public void run() {
        while (true) {
            doProcess();
        }
    }
    protected void doProcess() {
        long _now = System.currentTimeMillis();

        if((this.lastProcess + this.timeLapse) > _now) {
            return;
        }
        String _resultado = "Contador: " + this.c++;
        this.ViewDraw.setText("Algo");
        this.lastProcess = _now;
    }
}
