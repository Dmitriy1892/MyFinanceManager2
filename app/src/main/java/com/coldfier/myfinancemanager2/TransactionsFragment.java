package com.coldfier.myfinancemanager2;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class TransactionsFragment extends Fragment {

    private RecyclerView recyclerViewTransactions;
    private TextView tvCardName;
    private TextView tvCardNumber;
    private TextView tvCardCurrency;
    private TextView tvCardBalance;

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

        if (cardId != null) {
            CardsCollectionDB db = new CardsCollectionDB(getContext());
            card = db.getCard(cardId);
        }

        View view = inflater.inflate(R.layout.fragment_transactions, container, false);

        tvCardName = (TextView) view.findViewById(R.id.card_name);
        tvCardNumber = (TextView) view.findViewById(R.id.card_number);
        tvCardCurrency = (TextView) view.findViewById(R.id.card_currency);
        tvCardBalance = (TextView) view.findViewById(R.id.card_balance);

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