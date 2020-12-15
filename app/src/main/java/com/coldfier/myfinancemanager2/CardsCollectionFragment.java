package com.coldfier.myfinancemanager2;

import android.content.Intent;
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

public class CardsCollectionFragment extends Fragment {

    public static final String EXTRA_CARD_ID = "com.coldfier.myfinancemanager2.card_id";

    private RecyclerView recyclerViewContainer;
    private CardAdapter cardAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_cards_collection, container, false);

        recyclerViewContainer = view.findViewById(R.id.recycler_view_container);
        recyclerViewContainer.setLayoutManager(new LinearLayoutManager(getContext()));

        updateUI();

        setHasOptionsMenu(true);

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        updateUI();
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.card_collection_menu, menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_add_card:
                Intent intent = new Intent(getActivity(), CreateCardActivity.class);
                startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }

    private void updateUI() {
        CardsCollectionDB db = new CardsCollectionDB(getContext());
        List<Card> cardList = db.getCardsCollection();

        cardAdapter = new CardAdapter(cardList);
        recyclerViewContainer.setAdapter(cardAdapter);
    }

    private class CardHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView tvCardName;
        private TextView tvCardNumber;
        private TextView tvCardCurrency;
        private TextView tvCardBalance;

        private Card card;

        public CardHolder(@NonNull View itemView) {
            super(itemView);

            itemView.setOnClickListener(this);

            tvCardName = itemView.findViewById(R.id.card_name);
            tvCardNumber  = itemView.findViewById(R.id.card_number);
            tvCardCurrency = itemView.findViewById(R.id.card_currency);
            tvCardBalance = itemView.findViewById(R.id.card_balance);
        }

        private void bindCard(Card card) {
            this.card = card;

            tvCardName.setText(card.getCardName());
            tvCardNumber.setText("" + card.getCardNumber());
            tvCardCurrency.setText(card.getCardCurrency());
            tvCardBalance.setText("" + card.getCardBalance());
        }

        @Override
        public void onClick(View v) {
            Intent intent = new Intent(getActivity(), TransactionsActivity.class);
            intent.putExtra(EXTRA_CARD_ID, card.getCardID());
            startActivity(intent);
        }
    }

    private class CardAdapter extends RecyclerView.Adapter<CardHolder> {

        List<Card> cardList;

        public CardAdapter(List<Card> cardList) {
            this.cardList = cardList;
        }

        @NonNull
        @Override
        public CardHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(getContext());
            return new CardHolder(layoutInflater.inflate(R.layout.item_card, parent, false));
        }

        @Override
        public void onBindViewHolder(@NonNull CardHolder holder, int position) {
            holder.bindCard(cardList.get(position));
        }

        @Override
        public int getItemCount() {
            return cardList.size();
        }
    }
}