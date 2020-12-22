package com.coldfier.myfinancemanager2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
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
            transactionsFragment = (TransactionsFragment) getSupportFragmentManager().getPrimaryNavigationFragment();
            getSupportFragmentManager().beginTransaction().setPrimaryNavigationFragment(transactionsFragment);
        } else {
            intent = getIntent();
            cardId = intent.getStringExtra(CardsCollectionFragment.EXTRA_CARD_ID);
            transactionsFragment = new TransactionsFragment(cardId);
            fragmentTransaction = getSupportFragmentManager().beginTransaction().add(R.id.frame_layout_transactions_container, transactionsFragment);
            fragmentTransaction.commit();
        }
    }

    @Override
    public void startNewTransaction(String cardId) {
        CardsCollectionDB db = new CardsCollectionDB(getApplicationContext());
        newTransactionFragment = new NewTransactionFragment(db.getCard(cardId));
        getSupportFragmentManager().beginTransaction().addToBackStack(BACK_STACK_TAG).add(R.id.frame_layout_transactions_container, newTransactionFragment).hide(transactionsFragment).commit();
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("cardIdSaver", cardId);
    }
}