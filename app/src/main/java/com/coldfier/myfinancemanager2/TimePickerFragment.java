package com.coldfier.myfinancemanager2;

import android.app.Activity;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TimePicker;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import java.text.SimpleDateFormat;
import java.util.Date;

public class TimePickerFragment extends DialogFragment {

    public static final String EXTRA_TIME_DIALOG = "com.coldfier.myfinancemanager2.extraTimeDialog";

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {

        int hours = Integer.parseInt(new SimpleDateFormat("hh").format(new Date(System.currentTimeMillis())));
        int minutes = Integer.parseInt(new SimpleDateFormat("mm").format(new Date(System.currentTimeMillis())));

        TimePickerDialog tpd = new TimePickerDialog(getContext(), onTimeSetListener, hours, minutes, true);

        return tpd;
    }

    TimePickerDialog.OnTimeSetListener onTimeSetListener = new TimePickerDialog.OnTimeSetListener() {
        @Override
        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
            String timeString;

            if (Integer.toString(hourOfDay).length() < 2) {
                if (Integer.toString(minute).length() < 2) {
                    timeString = "0" + hourOfDay + ":0" + minute;
                } else {
                    timeString = "0" + hourOfDay + ":" + minute;
                }
            } else if (Integer.toString(minute).length() < 2) {
                timeString = hourOfDay + ":0" + minute;
            } else {
                timeString = hourOfDay + ":" + minute;
            }

            Intent intent = new Intent();
            intent.putExtra(EXTRA_TIME_DIALOG, timeString);
            getTargetFragment().onActivityResult(getTargetRequestCode(), Activity.RESULT_OK, intent);
        }
    };
}
