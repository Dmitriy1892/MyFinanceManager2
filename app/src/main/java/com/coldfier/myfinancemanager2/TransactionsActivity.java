package com.coldfier.myfinancemanager2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

public class TransactionsActivity extends AppCompatActivity {

    private String cardId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transactions);

        if (savedInstanceState != null) {
            cardId = savedInstanceState.getString("cardIdSaver");
        } else {
            Intent intent = getIntent();
            cardId = intent.getStringExtra(CardsCollectionFragment.EXTRA_CARD_ID);
        }
        TransactionsFragment transactionsFragment = new TransactionsFragment(cardId);
        getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout_transactions_container, transactionsFragment).commit();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("cardIdSaver", cardId);
    }
}