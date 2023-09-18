package com.practice.android_java_todo_app_mvvm;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.practice.android_java_todo_app_mvvm.DataAccess.TodoDAO;
import com.practice.android_java_todo_app_mvvm.DataAccess.UserDAO;
import com.practice.android_java_todo_app_mvvm.Entities.Todo;
import com.practice.android_java_todo_app_mvvm.Entities.User;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = {User.class, Todo.class}, version = 2, exportSchema = false)
public abstract class TodoDatabase extends RoomDatabase {

    private static final int NUMBER_OF_THREADS = 4;
    public static final ExecutorService databaseWriteExecutor = Executors.newFixedThreadPool(NUMBER_OF_THREADS);
    private static volatile TodoDatabase INSTANCE;

    public static TodoDatabase getInstance(final Context context) {
        if (INSTANCE == null) {
            synchronized (TodoDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(), TodoDatabase.class, "Todo_Database").fallbackToDestructiveMigration().build();
                }
            }
        }

        return INSTANCE;
    }

    public abstract UserDAO userDAO();

    public abstract TodoDAO todoDAO();
}
