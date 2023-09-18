package com.practice.android_java_todo_app_mvvm.Repositories;

import android.app.Application;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.MutableLiveData;

import com.google.gson.Gson;
import com.practice.android_java_todo_app_mvvm.DataAccess.TodoDAO;
import com.practice.android_java_todo_app_mvvm.Entities.Todo;
import com.practice.android_java_todo_app_mvvm.TodoDatabase;

import java.util.List;

public class TodoRepo {

    private TodoDAO todoDAO;


    public TodoRepo(Application application) {
        TodoDatabase todoDatabase = TodoDatabase.getInstance(application);
        todoDAO = todoDatabase.todoDAO();
    }

    public LiveData<List<Todo>> getAllTodos(int userID) {
        MutableLiveData<List<Todo>> resultLiveData = new MutableLiveData<>();

        TodoDatabase.databaseWriteExecutor.execute(() -> {

            resultLiveData.postValue(todoDAO.getAllTodos(userID));
        });
        Log.d("getAllTodos", new Gson().toJson(resultLiveData));
        return resultLiveData;
    }

    public void insert(Todo todo) {
        TodoDatabase.databaseWriteExecutor.execute(() -> {
            todoDAO.insert(todo);
            LiveData<List<Todo>> todos = getAllTodos(todo.getUserID());

            Log.d("INSERT ALL TODOS", new Gson().toJson(todos));
        });
    }

    public void update(Todo todo) {
        TodoDatabase.databaseWriteExecutor.execute(() -> todoDAO.update(todo));
    }

    public void delete(Todo todo) {
        TodoDatabase.databaseWriteExecutor.execute(() -> todoDAO.delete(todo));
    }

}
