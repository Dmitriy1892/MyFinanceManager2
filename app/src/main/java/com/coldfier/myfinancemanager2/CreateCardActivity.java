package com.coldfier.myfinancemanager2;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class CreateCardActivity extends AppCompatActivity {

    private EditText etCardName;
    private EditText etCardNumber;
    private Button btnCardSmsPath;
    private Spinner spinnerCardCurrency;
    private EditText etCardBalance;
    private Button btnSubmit;

    private Card card;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_card);

        etCardName = (EditText) findViewById(R.id.edit_text_card_name);
        etCardNumber = (EditText) findViewById(R.id.edit_text_card_number);
        btnCardSmsPath = (Button) findViewById(R.id.button_card_sms_path);
        spinnerCardCurrency = (Spinner) findViewById(R.id.spinner_card_currency);
        etCardBalance = (EditText) findViewById(R.id.edit_text_card_balance);
        btnSubmit = (Button) findViewById(R.id.button_submit);


    }

    public void smsPathOnClick(View view) {
        btnCardSmsPath.setClickable(false);
    }

    public void submitOnClick(View view) {
        if (!(etCardName.getText().toString().isEmpty() && etCardNumber.getText().toString().isEmpty() && etCardBalance.getText().toString().isEmpty())) {
            card = new Card(etCardName.getText().toString(),
                    (int) Integer.parseInt(etCardNumber.getText().toString()),
                    spinnerCardCurrency.getSelectedItem().toString(),
                    (double) Double.parseDouble(etCardBalance.getText().toString()));
            CardsCollectionDB db = new CardsCollectionDB(getApplicationContext());
            db.addCard(card);
            Toast.makeText(getApplicationContext(), getString(R.string.toast_card_created), Toast.LENGTH_SHORT).show();
            finish();
        } else {
            Toast.makeText(getApplicationContext(), getString(R.string.toast_card_non_created), Toast.LENGTH_SHORT).show();
        }
    }
}