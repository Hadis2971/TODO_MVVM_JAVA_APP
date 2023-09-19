package com.practice.android_java_todo_app_mvvm.Activities;

import java.text.ParseException;

public interface TodoAdditionalInfoInterface {
    public void updateTodo(String description, int priority, String date) throws ParseException;

    public void getAdditionalTodoInfo(String date, int priority, String description);

    public void closeDialog();
}
