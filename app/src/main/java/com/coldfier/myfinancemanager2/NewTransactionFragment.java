package com.coldfier.myfinancemanager2;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.text.SimpleDateFormat;
import java.util.Date;

public class NewTransactionFragment extends Fragment {

    private static final String DATE_DIALOG_TAG = "com.coldfier.myfinancemanager2.dateDialogTag";

    private Button btnDate;
    private EditText etPayment;
    private EditText etBalance;
    private EditText etLocation;
    private Spinner spinnerCategory;
    private Button btnSubmitTransaction;

    private SimpleDateFormat sdf;
    private Date date;
    private Card card;
    private String cardId;

    public NewTransactionFragment() {}

    public NewTransactionFragment(Card card) {
        this.card = card;
    }

    

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        if (savedInstanceState != null) {
            //cardId = savedInstanceState.getString("TAG");
            CardsCollectionDB db = new CardsCollectionDB(getContext());
            card = db.getCard(savedInstanceState.getString("TAG"));
        }

        View view = inflater.inflate(R.layout.fragment_new_transaction, container, false);

        btnDate = (Button) view.findViewById(R.id.new_transaction_date);
        btnDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerFragment dialog = new DatePickerFragment();
                dialog.show(getFragmentManager(), DATE_DIALOG_TAG);
            }
        });
        sdf = new SimpleDateFormat("dd.MM.yyyy 'AT' hh:mm:ss");
        date = new Date(System.currentTimeMillis());
        btnDate.setText(sdf.format(date));

        etPayment = (EditText) view.findViewById(R.id.new_transaction_payment);
        etBalance = (EditText) view.findViewById(R.id.new_transaction_balance);
        etLocation = (EditText) view.findViewById(R.id.new_transaction_location);
        spinnerCategory = (Spinner) view.findViewById(R.id.new_transaction_category);
        btnSubmitTransaction = (Button) view.findViewById(R.id.new_transaction_button_submit);
        btnSubmitTransaction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!btnDate.getText().toString().isEmpty() && !etPayment.getText().toString().isEmpty()
                        && !etBalance.getText().toString().isEmpty() && !etLocation.getText().toString().isEmpty()) {
                    Transaction transaction = new Transaction(
                            btnDate.getText().toString(),
                            (double) Double.parseDouble(etPayment.getText().toString()),
                            (double) Double.parseDouble(etBalance.getText().toString()),
                            etLocation.getText().toString(),
                            spinnerCategory.getSelectedItem().toString()
                    );

                    TransactionsDB db = new TransactionsDB(getContext());
                    db.addTransaction(card, transaction);
                    getParentFragmentManager().popBackStack();
                } else {
                    Toast.makeText(getContext(), R.string.new_transaction_empty_toast, Toast.LENGTH_SHORT).show();
                }
            }
        });

        return view;
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("TAG",  card.getCardID());
    }
}
