package com.example.joseph.weekend2app;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import java.util.TimerTask;

/**
 * Created by joseph on 10/9/17.
 */

public class MyTimer extends TimerTask {

    private int counter;
    private Handler handler;

    @Override
    public void run() {
        counter += 1;
        Bundle bundle = new Bundle();
        bundle.putString("counter", String.valueOf(counter));
        Message message = new Message();
        message.setData(bundle);
        handler.sendMessage(message);
    }

    public int getCounter() {
        return counter;
    }

    public void setHandler(Handler handler) {
        this.handler = handler;
    }
}
