package com.coldfier.myfinancemanager2;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.telephony.SmsManager;
import android.telephony.SmsMessage;
import android.util.Log;

public class SMSMonitor extends BroadcastReceiver {

    private static final String ACTION_SMS_RECEIVED = "android.provider.Telephony.SMS_RECEIVED";
    public static final String EXTRA_SMS_TEXT = "com.coldfier.myfinancemanager2.extraSmsText";
    public static final String EXTRA_SMS_SENDER = "com.coldfier.myfinancemanager2.extraSmsSender";

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d("myLogs", "onReceive started");

        String action = intent.getAction();

        if (action != null && action.equals(ACTION_SMS_RECEIVED)) {
            SmsManager smsManager = SmsManager.getDefault();
            Object[] pduArray = (Object[]) intent.getExtras().get("pdus");
            SmsMessage[] messages = new SmsMessage[pduArray.length];
            for (int i = 0; i < pduArray.length; i++) {
                messages[i] = SmsMessage.createFromPdu((byte[]) pduArray[i]);
            }

            //получаем данные отправителя СМС
            String smsAddress = messages[0].getDisplayOriginatingAddress();

            //сравниваем данные отправителя с заданными и если true, то вытягиваем текст сообщения
            if (smsAddress.equals("MTBANK")) {
                StringBuilder messageBuilder = new StringBuilder();
                for (int i = 0; i < messages.length; i++) {
                    messageBuilder.append(messages[i].getMessageBody());
                }

                String messageText = messageBuilder.toString();

                Intent serviceIntent = new Intent(context.getApplicationContext(), SMSService.class);
                serviceIntent.putExtra(EXTRA_SMS_SENDER, smsAddress);
                serviceIntent.putExtra(EXTRA_SMS_TEXT, messageText);
                context.getApplicationContext().startService(serviceIntent);
            }

        }
    }
}
