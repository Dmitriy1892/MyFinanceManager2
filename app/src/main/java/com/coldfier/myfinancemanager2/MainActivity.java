package com.coldfier.myfinancemanager2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.provider.Telephony;
import android.telephony.SmsMessage;
import android.util.Log;

public class MainActivity extends AppCompatActivity {

    private int MY_PERMISSIONS_REQUEST_SMS_RECEIVE = 10;

    private CardsCollectionFragment collectionFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        collectionFragment = new CardsCollectionFragment();
        getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout_container, collectionFragment).commit();

        //Код ниже и метод onRequestPermissionsResult - шоб SMSMonitor receiver заработал,
        // т.к. в новых Android права запрашиваются не при установке,
        // а при вызове соотв действия, поэтому надо принудительно дать права в MainActivity.
        // Либо переделать потом в SharedPreferences

        ActivityCompat.requestPermissions(this,
                new String[]{Manifest.permission.RECEIVE_SMS},
                MY_PERMISSIONS_REQUEST_SMS_RECEIVE);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == MY_PERMISSIONS_REQUEST_SMS_RECEIVE) {
            // YES!!
            Log.d("myLogs", "MY_PERMISSIONS_REQUEST_SMS_RECEIVE --> YES");
        }
    }
}