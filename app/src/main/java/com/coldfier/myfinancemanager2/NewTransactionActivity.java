package com.coldfier.myfinancemanager2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

public class NewTransactionActivity extends AppCompatActivity {

    private static final String EXTRA_SAVE_NTA = "com.coldfier.myfinancemanager2.extraSaveNTA";

    private String cardId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_transaction);

        if (savedInstanceState != null) {
            cardId = savedInstanceState.getString(EXTRA_SAVE_NTA);
        } else {
            Intent intent = getIntent();
            cardId = intent.getStringExtra(TransactionsFragment.EXTRA_INTENT_NEW_TRANSACTION);
        }

        CardsCollectionDB db = new CardsCollectionDB(getApplicationContext());
        NewTransactionFragment newTransactionFragment = new NewTransactionFragment(db.getCard(cardId));
        getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout_new_transaction_activity_container,newTransactionFragment).commit();
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(EXTRA_SAVE_NTA, cardId);
    }
}