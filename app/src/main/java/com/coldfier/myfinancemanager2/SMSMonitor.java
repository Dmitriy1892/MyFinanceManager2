package com.coldfier.myfinancemanager2;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.telephony.SmsManager;
import android.telephony.SmsMessage;

public class SMSMonitor extends BroadcastReceiver {

    private static final String ACTION_SMS_RECEIVED = "android.provider.Telephony.SMS_RECEIVED";

    @Override
    public void onReceive(Context context, Intent intent) {

        String action = intent.getAction();

        if (action != null && action.equals(ACTION_SMS_RECEIVED)) {
            SmsManager smsManager = SmsManager.getDefault();
            Object[] pduArray = (Object[]) intent.getExtras().get("pdus");
            //String format = intent.getExtras().getString("format");
            SmsMessage[] messages = new SmsMessage[pduArray.length];
            for (int i = 0; i < pduArray.length; i++) {
                messages[i] = SmsMessage.createFromPdu((byte[]) pduArray[i]);
            }

            //получаем данные отправителя СМС
            String smsAdress = messages[0].getDisplayOriginatingAddress();

            //сравниваем данные отправителя с заданными и если true, то вытягиваем текст сообщения
            if (smsAdress.equals("MTBANK")) {
                StringBuilder messageBuilder = new StringBuilder();
                for (int i = 0; i < messages.length; i++) {
                    messageBuilder.append(messages[i].getMessageBody());
                }

                String messageText = messageBuilder.toString();

                //дописать код для дальнейшей обработки - отпарсить текст, выделить сумму платежа,  баланс, локацию, номер карты

            }

        }
    }
}
