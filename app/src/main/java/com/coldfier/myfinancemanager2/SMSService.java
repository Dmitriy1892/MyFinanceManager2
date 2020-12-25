package com.coldfier.myfinancemanager2;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.Nullable;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SMSService extends Service {

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d("myLogs", "Service onCreate");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        switch (intent.getStringExtra(SMSMonitor.EXTRA_SMS_SENDER)) {
            case "MTBANK":
                mtbankParser(intent.getStringExtra(SMSMonitor.EXTRA_SMS_TEXT));
                break;
        }
        return super.onStartCommand(intent, flags, startId);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        Log.d("myLogs", "Service onBind started");

        switch (intent.getStringExtra(SMSMonitor.EXTRA_SMS_SENDER)) {
            case "MTBANK":
                mtbankParser(intent.getStringExtra(SMSMonitor.EXTRA_SMS_TEXT));
                break;
        }

        return null;
    }

    private void mtbankParser(String sms) {

        CardsCollectionDB cardDB = new CardsCollectionDB(getApplicationContext());
        Card card = null;

        TransactionsDB transactionsDB = new TransactionsDB(getApplicationContext());
        Transaction transaction = new Transaction();

        Pattern cardNumberPattern = Pattern.compile("\\*\\d{4}");
        Matcher cardNumberMatcher = cardNumberPattern.matcher(sms);
        String cardNumber;
        if (cardNumberMatcher.find()) {
            cardNumber = cardNumberMatcher.group().replace("*", "");
            card = cardDB.getCard((Integer.parseInt(cardNumber)));
        }


        Pattern dateTimePattern = Pattern.compile("\\d{4}-\\d{2}-\\d{2} \\d{2}:\\d{2}:\\d{2}");
        Matcher dateTimeMatcher = dateTimePattern.matcher(sms);
        String dateTime;
        if (dateTimeMatcher.find()) {
            dateTime = dateTimeMatcher.group().replace("-", ".").replace(" ", " AT ");
            transaction.setDate(dateTime);
        }

        Pattern paymentPattern = Pattern.compile("\\d*\\.\\d{2}");
        Matcher paymentMatcher = paymentPattern.matcher(sms);
        String payment;
        String balance;
        String buffer = null;
        if (paymentMatcher.find()) {
            payment = paymentMatcher.group();
            transaction.setPayment(Double.parseDouble(payment));
            buffer = sms.substring(paymentMatcher.end()).replace(" BYN", "");

            if (paymentMatcher.find()) {
                balance = paymentMatcher.group();
                transaction.setBalance(Double.parseDouble(balance));
                card.setCardBalance(Double.parseDouble(balance));
                cardDB.updateCard(card);
            }
        }


        Pattern locationPattern = Pattern.compile("\\.*[ a-zA-Z0-9]*/\\.*[ a-zA-Z0-9]*/\\D{3}");
        Matcher locationMatcher = locationPattern.matcher(buffer);
        String location;
        if (locationMatcher.find()) {
            location = locationMatcher.group().replace(" OSTATOK", "");
            transaction.setLocation(location);
        }
        transaction.setCategory("Other");
        transactionsDB.addTransaction(card, transaction);
    }
}
