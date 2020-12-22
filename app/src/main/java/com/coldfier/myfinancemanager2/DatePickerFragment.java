package com.coldfier.myfinancemanager2;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.DatePicker;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DatePickerFragment extends DialogFragment {

    public static final String EXTRA_DATE_DIALOG = "com.coldfier.myfinancemanager2.extraDateDialog";

    private int dateYear;
    private int dateMonth;
    private int dateDay;

    @SuppressLint("SimpleDateFormat")
    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {

        dateYear = Integer.parseInt(new SimpleDateFormat("yyyy").format(new Date(System.currentTimeMillis())));
        dateMonth = Integer.parseInt(new SimpleDateFormat("MM").format(new Date(System.currentTimeMillis()))) - 1;
        dateDay = Integer.parseInt(new SimpleDateFormat("dd").format(new Date(System.currentTimeMillis())));

        return new DatePickerDialog(getContext(), dateSetListener, dateYear, dateMonth, dateDay);
    }

    DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
            dateYear = year;
            dateMonth = month + 1;
            dateDay = dayOfMonth;

            String resultDate;

            if (Integer.toString(dateMonth).length() < 2) {
                if (Integer.toString(dateDay).length() < 2) {
                    resultDate = dateYear + ".0" + dateMonth + ".0" + dateDay;
                } else {
                    resultDate = dateYear + ".0" + dateMonth + "." + dateDay;
                }
            } else if (Integer.toString(dateDay).length() < 2) {
                resultDate = dateYear + "." + dateMonth + ".0" + dateDay;
            } else {
                resultDate = dateYear + "." + dateMonth + "." + dateDay;
            }

            Intent intent = new Intent();
            intent.putExtra(EXTRA_DATE_DIALOG, resultDate);
            getTargetFragment().onActivityResult(getTargetRequestCode(), Activity.RESULT_OK, intent);
        }
    };
}
