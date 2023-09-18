package com.practice.android_java_todo_app_mvvm.DataAccess;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.practice.android_java_todo_app_mvvm.Entities.Todo;

import java.util.List;

@Dao
public interface TodoDAO {

    @Insert
    void insert(Todo todo);

    @Update
    void update(Todo todo);

    @Delete
    void delete(Todo todo);

    @Query("SELECT * FROM Todo WHERE userID = :userID ORDER BY priority")
    List<Todo> getAllTodos(int userID);
}
