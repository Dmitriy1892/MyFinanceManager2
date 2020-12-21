package com.coldfier.myfinancemanager2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;

public class TransactionsActivity extends AppCompatActivity implements TransactionsFragment.StartNewTransactionFragment{

    private static final String BACK_STACK_TAG = "com.coldfier.myfinancemanager2.backStackTag";

    private TransactionsFragment transactionsFragment;
    private NewTransactionFragment newTransactionFragment;

    private FragmentManager fragmentManager;
    private FragmentTransaction fragmentTransaction;

    private Intent intent;
    private String cardId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transactions);

        if (savedInstanceState != null) {
            cardId = savedInstanceState.getString("cardIdSaver");
        } else {
            intent = getIntent();
            cardId = intent.getStringExtra(CardsCollectionFragment.EXTRA_CARD_ID);
            firstTransaction();
            fragmentTransaction.commit();
        }
    }

    public void firstTransaction() {
        transactionsFragment = new TransactionsFragment(cardId);
        fragmentManager = getSupportFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction().add(R.id.frame_layout_transactions_container, transactionsFragment);

    }

    @Override
    public void startNewTransaction(String cardId) {
        CardsCollectionDB db = new CardsCollectionDB(getApplicationContext());
        newTransactionFragment = new NewTransactionFragment(db.getCard(cardId));
        fragmentManager.beginTransaction().addToBackStack(BACK_STACK_TAG).add(R.id.frame_layout_transactions_container, newTransactionFragment).hide(transactionsFragment).commit();
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("cardIdSaver", cardId);
    }
}