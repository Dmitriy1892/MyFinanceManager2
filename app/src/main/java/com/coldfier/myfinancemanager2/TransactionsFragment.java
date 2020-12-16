package com.coldfier.myfinancemanager2;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class TransactionsFragment extends Fragment {

    private static final String EXTRA_SAVE_INSTANCE = "com.coldfier.myfinancemanager2.extraSaveInstance";

    private RecyclerView recyclerViewTransactions;
    private TextView tvCardName;
    private TextView tvCardNumber;
    private TextView tvCardCurrency;
    private TextView tvCardBalance;

    private EditText etDate;
    private EditText etPayment;
    private EditText etBalance;
    private EditText etLocation;
    private Spinner spinnerCategory;

    private FloatingActionButton fabAddTransaction;

    private Card card;
    private String cardId;

    public TransactionsFragment() {}

    public TransactionsFragment(String cardId) {
        this.cardId = cardId;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        if (savedInstanceState != null) {
            cardId = savedInstanceState.getString(EXTRA_SAVE_INSTANCE);
        }

        if (cardId != null) {
            CardsCollectionDB db = new CardsCollectionDB(getContext());
            card = db.getCard(cardId);
        }

        View view = inflater.inflate(R.layout.fragment_transactions, container, false);

        tvCardName = (TextView) view.findViewById(R.id.card_name);
        tvCardNumber = (TextView) view.findViewById(R.id.card_number);
        tvCardCurrency = (TextView) view.findViewById(R.id.card_currency);
        tvCardBalance = (TextView) view.findViewById(R.id.card_balance);

        fabAddTransaction = (FloatingActionButton) view.findViewById(R.id.fab_add_transaction);
        fabAddTransaction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setTitle(R.string.dialog_title);

                View dialogView = getLayoutInflater().inflate(R.layout.fragment_new_transaction_dialog, null);
                builder.setView(dialogView);

                etDate = (EditText) dialogView.findViewById(R.id.dialog_date);
                etPayment = (EditText) dialogView.findViewById(R.id.dialog_payment);
                etBalance = (EditText) dialogView.findViewById(R.id.dialog_balance);
                etLocation = (EditText) dialogView.findViewById(R.id.dialog_location);
                spinnerCategory = (Spinner) dialogView.findViewById(R.id.dialog_category);

                builder.setPositiveButton(R.string.dialog_add_button, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (!(etDate.getText().toString().isEmpty() && etPayment.getText().toString().isEmpty()
                                && etBalance.getText().toString().isEmpty() && etLocation.getText().toString().isEmpty())) {
                            Transaction transaction = new Transaction(
                                    etDate.getText().toString(),
                                    (double) Double.parseDouble(etPayment.getText().toString()),
                                    (double) Double.parseDouble(etBalance.getText().toString()),
                                    etLocation.getText().toString(),
                                    spinnerCategory.getSelectedItem().toString()
                            );

                            TransactionsDB db = new TransactionsDB(getContext());
                            db.addTransaction(card, transaction);
                            updateUI();
                        }
                    }
                });
                builder.create().show();
            }
        });

        recyclerViewTransactions = view.findViewById(R.id.recycler_view_transactions_container);
        recyclerViewTransactions.setLayoutManager(new LinearLayoutManager(getContext()));

        updateUI();

        setHasOptionsMenu(true);
        
        return view;
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.transactions_menu, menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.delete_card:
                CardsCollectionDB db = new CardsCollectionDB(getContext());
                db.deleteCard(card);
                getActivity().finish();
                break;

            case R.id.edit_card:
                //create code for edit card
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(EXTRA_SAVE_INSTANCE, card.getCardID());
    }

    private void updateUI() {
        tvCardName.setText(card.getCardName());
        tvCardNumber.setText("" + card.getCardNumber());
        tvCardCurrency.setText("" + card.getCardCurrency());
        tvCardBalance.setText("" + card.getCardBalance());

        TransactionsDB db = new TransactionsDB(getContext());
        List<Transaction> transactionsList = db.getTransactionsCollection(card);
        recyclerViewTransactions.setAdapter(new TransactionAdapter(transactionsList));
    }

    private class TransactionHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView transactionDate;
        private TextView transactionPayment;
        private TextView transactionBalance;
        private TextView transactionLocation;
        private TextView transactionCategory;

        private Transaction transaction;

        public TransactionHolder(@NonNull View itemView) {
            super(itemView);

            itemView.setOnClickListener(this);

            transactionDate = (TextView) itemView.findViewById(R.id.transaction_date);
            transactionPayment = (TextView) itemView.findViewById(R.id.transaction_payment);
            transactionBalance = (TextView) itemView.findViewById(R.id.transaction_balance);
            transactionLocation = (TextView) itemView.findViewById(R.id.transaction_location);
            transactionCategory = (TextView) itemView.findViewById(R.id.transaction_category);
        }

        private void bindTransaction(Transaction transaction) {
            this.transaction = transaction;

            transactionDate.setText(transaction.getDate());
            transactionPayment.setText("" + transaction.getPayment());
            transactionBalance.setText("" + transaction.getBalance());
            transactionLocation.setText(transaction.getLocation());
            transactionCategory.setText(transaction.getCategory());
        }

        @Override
        public void onClick(View v) {
            //create code for edit transaction
        }
    }

    private class TransactionAdapter extends RecyclerView.Adapter<TransactionHolder> {

        List<Transaction> transactionsList;

        public TransactionAdapter(List<Transaction> transactionsList) {
            this.transactionsList = transactionsList;
        }

        @NonNull
        @Override
        public TransactionHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = getLayoutInflater();
            return new TransactionHolder(layoutInflater.inflate(R.layout.item_transaction, parent, false));
        }

        @Override
        public void onBindViewHolder(@NonNull TransactionHolder holder, int position) {
            holder.bindTransaction(transactionsList.get(position));
        }

        @Override
        public int getItemCount() {
            return transactionsList.size();
        }
    }

}