package com.example.joseph.weekend2app;

import android.Manifest;
import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.os.Handler;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.Toast;

import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity implements Communicator{

    FragmentManager fm;
    BroadcastReceiver smsSentReceiver, smsDeliveredReceiver;
    private Timer timer = new Timer();
    DialogFragment newFragment;
    private ButtonsFragment buttonsFragment;
    private TextViewFragment textViewFragment;
    private android.app.FragmentManager fragmentManager;
    private FragmentTransaction transaction;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // TODO: 10/9/17 this section keeps crashing 
//        buttonsFragment = new ButtonsFragment();
//        textViewFragment = new TextViewFragment();
//        fragmentManager = getFragmentManager();
//        transaction = fragmentManager.beginTransaction();
//        transaction.add(R.id.tvFrag, textViewFragment, "textViewFragment");
//        transaction.add(R.id.buttonsFragment, buttonsFragment, "buttonFragment");
//        transaction.commit();

    }

    public void goToPDF(View view) {

        Intent intent = new Intent(this, PDFActivity.class);
        startActivity(intent);

    }

    public void showDialog(View view) {

        fm = getSupportFragmentManager();


        newFragment = PicDialogFragment.newInstance();
        newFragment.show(fm, "TAG");

        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                newFragment.dismiss();
                timer.cancel();
            }
        }, 4000);


    }

    public void showAlertDialog(View view) {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("alert title").setItems(R.array.list_items, new DialogInterface.OnClickListener(){
            public void onClick(DialogInterface dialog, int which){

            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();

    }

    public void notify(View view) {

        Intent intent = new Intent(this, NotificationActivity.class);
        PendingIntent pIntent = PendingIntent.getActivity(MainActivity.this, 0, intent, 0);
        Notification notification = new Notification.Builder(MainActivity.this)
                .setTicker("TicketTitle")
                .setContentTitle("Go to next activity")
                .setContentText("Will send you to next activity")
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentIntent(pIntent)
                .getNotification();

        notification.flags = Notification.FLAG_AUTO_CANCEL;
        NotificationManager nm = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        nm.notify(0, notification);

    }

    public void sendText(View view) {

        EditText etText = (EditText) findViewById(R.id.etText);
        EditText etPhoneNumber = (EditText) findViewById(R.id.etPhoneNumber);

        String text = etText.getText().toString();
        String phoneNumber = etPhoneNumber.getText().toString();
        PendingIntent sentPI, deliveredPI;

        sentPI = PendingIntent.getBroadcast(this, 0, new Intent("SENT"), 0);
        deliveredPI = PendingIntent.getBroadcast(this, 0, new Intent("DELIVERED"), 0);

        if(ContextCompat.checkSelfPermission(this, Manifest.permission.SEND_SMS) != PackageManager.PERMISSION_GRANTED)
            ActivityCompat.requestPermissions(this, new String[] {Manifest.permission.SEND_SMS}, 1);
        else {
            SmsManager sms = SmsManager.getDefault();
            sms.sendTextMessage(phoneNumber, null, text, sentPI, deliveredPI);
        }

    }

    @Override
    protected void onResume() {
        super.onResume();

        smsSentReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {

                switch(getResultCode()) {

                    case Activity.RESULT_OK:
                        Toast.makeText(MainActivity.this, "SMS sent!", Toast.LENGTH_SHORT).show();
                        break;


                    case SmsManager.RESULT_ERROR_GENERIC_FAILURE:
                        Toast.makeText(MainActivity.this, "Generic Failure", Toast.LENGTH_SHORT).show();
                        break;

                    case SmsManager.RESULT_ERROR_NO_SERVICE:
                        Toast.makeText(MainActivity.this, "No Service!", Toast.LENGTH_SHORT).show();
                        break;

                    case SmsManager.RESULT_ERROR_NULL_PDU:
                        Toast.makeText(MainActivity.this, "Null PDU!", Toast.LENGTH_SHORT).show();
                        break;

                    case SmsManager.RESULT_ERROR_RADIO_OFF:
                        Toast.makeText(MainActivity.this, "Radio off!", Toast.LENGTH_SHORT).show();
                        break;

                }

            }
        };


        smsDeliveredReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {

                switch (getResultCode()) {

                    case Activity.RESULT_OK:
                        Toast.makeText(MainActivity.this, "SMS Delivered!", Toast.LENGTH_SHORT).show();
                        break;

                    case Activity.RESULT_CANCELED:
                        Toast.makeText(MainActivity.this, "SMS not Delivered!", Toast.LENGTH_SHORT).show();
                        break;
                }

            }
        };

        registerReceiver(smsSentReceiver, new IntentFilter("SENT"));
        registerReceiver(smsDeliveredReceiver, new IntentFilter("DELIVERED"));

    }

    @Override
    protected void onPause() {
        super.onPause();

        unregisterReceiver(smsDeliveredReceiver);
        unregisterReceiver(smsSentReceiver);
    }

    @Override
    public void handler(Handler i) {
        buttonsFragment.setTvHandler(i);
    }
}
