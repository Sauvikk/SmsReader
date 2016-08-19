package com.example.souvik.pfmUi;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.telephony.SmsMessage;
import android.util.Log;


public class IncomingSmsReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {

//        TODO : Parse sms notification and update transactions

        Log.d("Intent received",  intent.getAction());
        if(intent.getAction().equals("android.provider.Telephony.SMS_RECEIVED")){
            Bundle bundle = intent.getExtras();
            SmsMessage[] messages = null;
            String strMessage = "";
            if (bundle != null){
                try{
                    Object[] pdus = (Object[]) bundle.get("pdus");
                    messages = new SmsMessage[pdus.length];
                    for(int i=0; i<messages.length; i++){
                        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                            String format = bundle.getString("format");
                            messages[i] = SmsMessage.createFromPdu((byte[]) pdus[i], format);
                            Log.e("Format 30", strMessage);
                        }
                        messages[i] = SmsMessage.createFromPdu((byte[])pdus[i]);
                        strMessage += "SMS From: " + messages[i].getOriginatingAddress();
                        strMessage += " : ";
                        strMessage += messages[i].getMessageBody();
                        strMessage += "\n";
                        Log.d("SMS 37", strMessage);
                    }
                }catch(Exception e){
                    Log.d("onReceive 24 : ", e.toString());
                }
                Log.d("SMS 41", strMessage);
            }
        }
    }
}
