package com.zhibo.sanjilienv.util;

import com.zhibo.sanjilienv.SanjilienvApplication;

public class UIThread extends Thread {

    private int time = 0;

    private TimeOverListener timeOverListener;

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }

    public TimeOverListener getTimeOverListener() {
        return timeOverListener;
    }

    public void setTimeOverListener(TimeOverListener timeOverListener) {
        this.timeOverListener = timeOverListener;
    }

    @Override
    public void run() {
        while (SanjilienvApplication.RUNNING){
            try {
                Thread.sleep(time * 1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            if(null != timeOverListener){
                timeOverListener.timeOver();
            }
        }
    }

    public interface TimeOverListener{
        void timeOver();
    }
}
