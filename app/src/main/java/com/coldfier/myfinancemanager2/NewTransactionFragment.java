package com.coldfier.myfinancemanager2;

import android.app.Activity;
import android.content.Intent;
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
    private static final String TIME_DIALOG_TAG = "com.coldfier.myfinancemanager2.timeDialogTag";
    private static final String SAVE_INSTANCE_TAG = "com.coldfier.myfinancemanager2.saveInstanceTag";
    private static final int EXTRA_DATE_REQUEST_CODE = 228;
    private static final int EXTRA_TIME_REQUEST_CODE = 282;

    private Button btnDate;
    private Button btnTime;
    private EditText etPayment;
    private EditText etBalance;
    private EditText etLocation;
    private Spinner spinnerCategory;
    private Button btnSubmitTransaction;

    private SimpleDateFormat sdf;
    private Date date;
    private Card card;

    public NewTransactionFragment() {}

    public NewTransactionFragment(Card card) {
        this.card = card;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        if (savedInstanceState != null) {
            CardsCollectionDB db = new CardsCollectionDB(getContext());
            card = db.getCard(savedInstanceState.getString(SAVE_INSTANCE_TAG));
        }

        View view = inflater.inflate(R.layout.fragment_new_transaction, container, false);

        btnDate = (Button) view.findViewById(R.id.new_transaction_date);
        sdf = new SimpleDateFormat("dd.MM.yyyy");
        date = new Date(System.currentTimeMillis());
        btnDate.setText(sdf.format(date));
        DatePickerFragment dateDialog = new DatePickerFragment();
        dateDialog.setTargetFragment(this, EXTRA_DATE_REQUEST_CODE);
        btnDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dateDialog.show(getParentFragmentManager(), DATE_DIALOG_TAG);
            }
        });

        btnTime = (Button) view.findViewById(R.id.new_transaction_time);
        sdf = new SimpleDateFormat("hh:mm");
        btnTime.setText(sdf.format(date));
        TimePickerFragment timeDialog = new TimePickerFragment();
        timeDialog.setTargetFragment(this, EXTRA_TIME_REQUEST_CODE);
        btnTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                timeDialog.show(getParentFragmentManager(), TIME_DIALOG_TAG);
            }
        });


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



                    String dateTime = btnDate.getText().toString()+ " " + getResources().getString(R.string.new_transaction_at) + " " +btnTime.getText().toString();

                    Transaction transaction = new Transaction(
                            dateTime,
                            (double) Double.parseDouble(etPayment.getText().toString()),
                            (double) Double.parseDouble(etBalance.getText().toString()),
                            etLocation.getText().toString(),
                            spinnerCategory.getSelectedItem().toString()
                    );

                    card.setCardBalance(Double.parseDouble(etBalance.getText().toString()));
                    CardsCollectionDB cardDB = new CardsCollectionDB(getContext());
                    cardDB.updateCard(card);

                    TransactionsDB db = new TransactionsDB(getContext());
                    db.addTransaction(card, transaction);
                    getActivity().finish();

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
        outState.putString(SAVE_INSTANCE_TAG,  card.getCardID());
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            switch (requestCode) {
                case EXTRA_DATE_REQUEST_CODE:
                    btnDate.setText(data.getStringExtra(DatePickerFragment.EXTRA_DATE_DIALOG));
                    break;
                case EXTRA_TIME_REQUEST_CODE:
                    btnTime.setText(data.getStringExtra(TimePickerFragment.EXTRA_TIME_DIALOG));
                    break;
            }
        }
    }
}
