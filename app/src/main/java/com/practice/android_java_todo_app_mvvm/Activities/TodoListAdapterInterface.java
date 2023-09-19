package com.practice.android_java_todo_app_mvvm.Activities;

import com.practice.android_java_todo_app_mvvm.Entities.Todo;

public interface TodoListAdapterInterface {
    public void openUpdateTodoDialog(Todo todo);

    public void updateCompletionStatus(Todo todo);

    public void deleteTodo(Todo todo);
}
