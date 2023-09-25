package com.practice.android_java_todo_app_mvvm.Dialogs;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.widget.DatePicker;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import java.util.Calendar;

public class CalendarDialog extends DialogFragment implements DatePickerDialog.OnDateSetListener {

    private ConveyDate conveyDateListener;

    public CalendarDialog (ConveyDate conveyDateListener) {
        this.conveyDateListener = conveyDateListener;
    }


    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        final Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(requireActivity(),
                AlertDialog.THEME_HOLO_DARK, this, year, month, day);

        return  datePickerDialog;
    }

    @Override
    public void onDateSet(DatePicker datePicker, int year, int month, int day) {
        int realMonth = month + 1;
        this.conveyDateListener.conveyDate(day + "." + realMonth + "." + year);
    }

    public interface ConveyDate {
        void conveyDate(String date);
    }
}
