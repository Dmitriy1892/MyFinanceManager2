package com.coldfier.myfinancemanager2;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class CardsCollectionFragment extends Fragment {

    private RecyclerView recyclerViewContainer;
    private CardAdapter cardAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_cards_collection, container, false);

        recyclerViewContainer = view.findViewById(R.id.recycler_view_container);
        recyclerViewContainer.setLayoutManager(new GridLayoutManager(getContext(), 2));

        updateUI();

        return view;
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
        private Card cardItem;

        public CardHolder(@NonNull View itemView) {
            super(itemView);

            tvCardName = itemView.findViewById(R.id.card_name);
            tvCardNumber  = itemView.findViewById(R.id.card_number);
            tvCardCurrency = itemView.findViewById(R.id.card_currency);
            tvCardBalance = itemView.findViewById(R.id.card_balance);
        }

        private void bindCard(Card card) {
            cardItem = card;

            tvCardName.setText(cardItem.getCardName());
            tvCardNumber.setText(cardItem.getCardNumber());
            tvCardCurrency.setText(cardItem.getCardCurrency());
            tvCardBalance.setText(Double.toString(cardItem.getCardBalance()));
        }

        @Override
        public void onClick(View v) {
            //create code to go to card transactions
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