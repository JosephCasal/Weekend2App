package com.example.joseph.weekend2app;

import android.app.Fragment;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import java.util.Timer;

/**
 * Created by joseph on 10/9/17.
 */

public class ButtonsFragment extends Fragment implements View.OnClickListener {

    private Button btnStart;
    private Button btnStop;
    Timer timer;
    MyTimer myTimer;
    Handler tvHandler;
    int counter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_buttons, container, false);

        btnStart = view.findViewById(R.id.btnStart);
        btnStop = view.findViewById(R.id.btnStop);

        btnStart.setOnClickListener(this);
        btnStop.setOnClickListener(this);

        return view;

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onClick(View view) {



        switch (view.getId()) {

            case R.id.btnStart:
                timer = new Timer();
                myTimer = new MyTimer();
                myTimer.setHandler(tvHandler);
                timer.scheduleAtFixedRate(myTimer, 0, 1000);

                break;

            case R.id.btnStop:
                timer.cancel();
                counter = myTimer.getCounter();
                break;
        }

    }

    public void setTvHandler(Handler tvHandler) {
        this.tvHandler = tvHandler;
    }

}
