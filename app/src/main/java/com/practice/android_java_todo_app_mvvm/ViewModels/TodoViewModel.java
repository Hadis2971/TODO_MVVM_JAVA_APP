package com.practice.android_java_todo_app_mvvm.ViewModels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.practice.android_java_todo_app_mvvm.Entities.Todo;
import com.practice.android_java_todo_app_mvvm.Repositories.TodoRepo;

import java.util.List;

public class TodoViewModel extends AndroidViewModel {

    private TodoRepo todoRepo;

    public TodoViewModel(@NonNull Application application) {
        super(application);
        todoRepo = new TodoRepo(application);
    }

    public LiveData<List<Todo>> getAllTodos(int userID) {
        return todoRepo.getAllTodos(userID);
    }

    public void insert(Todo todo) {
        todoRepo.insert(todo);
        this.getAllTodos(todo.getUserID());
    }

    public void update(Todo todo) {
        todoRepo.update(todo);
    }

    public void delete(Todo todo) {
        todoRepo.delete(todo);
    }
}
