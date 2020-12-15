package com.coldfier.myfinancemanager2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

public class TransactionsActivity extends AppCompatActivity {

    private TransactionsFragment transactionsFragment;

    private Intent intent;

    private String cardId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transactions);

        intent = getIntent();
        cardId = intent.getStringExtra(CardsCollectionFragment.EXTRA_CARD_ID);

        transactionsFragment = new TransactionsFragment(cardId);
        getSupportFragmentManager().beginTransaction().add(R.id.frame_layout_transactions_container, transactionsFragment).commit();
    }


}