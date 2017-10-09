package com.example.joseph.weekend2app;

import android.app.Fragment;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by joseph on 10/9/17.
 */

public class TextViewFragment extends Fragment {

    TextView tvFrag;
    Communicator comm;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_textview, container, false);
        tvFrag = view.findViewById(R.id.tvFrag);

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        comm = (Communicator) getActivity();

        Handler handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                Bundle data = msg.getData();
                String counter = data.getString("counter");
                tvFrag.setText(counter);
            }
        };

        comm.handler(handler);

    }
}
