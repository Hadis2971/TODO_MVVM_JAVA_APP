package com.practice.android_java_todo_app_mvvm.Dialogs;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.practice.android_java_todo_app_mvvm.Activities.TodoAdditionalInfoInterface;
import com.practice.android_java_todo_app_mvvm.Activities.TodosList;
import com.practice.android_java_todo_app_mvvm.R;

import java.text.ParseException;

public class AddTodoAdditionalInfoDialog extends DialogFragment implements View.OnClickListener, CalendarDialog.ConveyDate {

    private static final int LOW_PRIORITY = 1, MEDIUM_PRIORITY = 2, HIGH_PRIORITY = 3, CRITICAL_PRIORITY = 4;

    private EditText descriptionInput;
    private Button openCalendar;
    private ImageButton lowPriorityBtn, mediumPriorityBtn, highPriorityBtn, criticalPriorityBtn;
    private int priority;

    private String date, newDescription;
    private TodoAdditionalInfoInterface listener;
    private boolean isUpdating;


    public AddTodoAdditionalInfoDialog(TodoAdditionalInfoInterface listener) {
        this.listener = listener;
        this.isUpdating = false;
    }
    public AddTodoAdditionalInfoDialog(TodoAdditionalInfoInterface listener, String newDescription, int priority) {
        this.listener = listener;
        this.newDescription = newDescription;
        this.priority = priority;
        this.isUpdating = true;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext(), R.style.AlertDialogTheme);
        LayoutInflater inflater = requireActivity().getLayoutInflater();

        View dialogView = inflater.inflate(R.layout.add_todo_additiona_info_dialog, null);


        builder.setTitle("Additional Information")
                .setView(R.layout.add_todo_additiona_info_dialog)
                .setPositiveButton("Add", (dialog, which) -> {
                    if (this.isUpdating) {
                        try {
                            listener.updateTodo(this.descriptionInput.getText().toString(), this.priority, this.date);
                        } catch (ParseException e) {
                            throw new RuntimeException(e);
                        }
                    } else {
                        listener.getAdditionalTodoInfo(this.date, this.priority, this.descriptionInput.getText().toString());
                    }

                })
                .setNegativeButton("Cancel", (dialog, which) -> {
                    listener.closeDialog();
                });


        descriptionInput = dialogView.findViewById(R.id.description_input);
        openCalendar = dialogView.findViewById(R.id.open_calendar);
        lowPriorityBtn = dialogView.findViewById(R.id.low_priority_button);
        mediumPriorityBtn = dialogView.findViewById(R.id.medium_priority_button);
        highPriorityBtn = dialogView.findViewById(R.id.high_priority_button);
        criticalPriorityBtn = dialogView.findViewById(R.id.critical_priority_button);

        openCalendar.setOnClickListener(this);
        lowPriorityBtn.setOnClickListener(this);
        mediumPriorityBtn.setOnClickListener(this);
        highPriorityBtn.setOnClickListener(this);
        criticalPriorityBtn.setOnClickListener(this);

        if (this.isUpdating) {
            descriptionInput.setText(this.newDescription);
        }

        builder.setView(dialogView);
        AlertDialog alertDialog = builder.create();

        return alertDialog;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.low_priority_button:
                priority = LOW_PRIORITY;
                break;
            case R.id.medium_priority_button:
                priority = MEDIUM_PRIORITY;
                break;
            case R.id.high_priority_button:
                priority = HIGH_PRIORITY;
                break;
            case R.id.critical_priority_button:
                priority = CRITICAL_PRIORITY;
                break;
            case R.id.open_calendar:
                DialogFragment calendarDialog = new CalendarDialog(AddTodoAdditionalInfoDialog.this);
                calendarDialog.show(getActivity().getSupportFragmentManager(), "Calendar Fragment Manager");
                break;
        }
    }

    @Override
    public void conveyDate(String date) {
        this.date = date;
    }
}
