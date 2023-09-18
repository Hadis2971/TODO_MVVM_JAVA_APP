package com.practice.android_java_todo_app_mvvm.Repositories;

import android.app.Application;
import android.os.AsyncTask;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModelProvider;

import com.google.gson.Gson;
import com.practice.android_java_todo_app_mvvm.DataAccess.UserDAO;
import com.practice.android_java_todo_app_mvvm.Entities.User;
import com.practice.android_java_todo_app_mvvm.TodoDatabase;
import com.practice.android_java_todo_app_mvvm.ViewModels.UserViewModel;

import java.util.List;

public class UserRepo {

    private UserDAO userDAO;

    public UserRepo(Application application) {
        TodoDatabase db = TodoDatabase.getInstance(application);
        userDAO = db.userDAO();
    }


    public void getLoginUser(String username, String password, UserViewModel setLoggedInUser) {
        new LoginUserAsyncTask(userDAO, setLoggedInUser).execute(username, password);
    }

    public User getUser(int userID) {
        final User[] user = {null};
        new Thread(() -> user[0] = userDAO.getUser(userID));

        return user[0];
    }

    public LiveData<List<User>> getAllUsers() {
        LiveData<List<User>> users = userDAO.getAllUsers();
        return users;
    }

    public void insert(User user) {
        TodoDatabase.databaseWriteExecutor.execute(() -> userDAO.insert(user));
    }

    public void update(User user) {
        TodoDatabase.databaseWriteExecutor.execute(() -> userDAO.update(user));
    }

    public void delete(User user) {
        TodoDatabase.databaseWriteExecutor.execute(() -> userDAO.delete(user));
    }

    private static class LoginUserAsyncTask extends AsyncTask<String, Void, User> {
        private UserDAO userDAO;
        private UserViewModel setLoggedInUser;

        private LoginUserAsyncTask(UserDAO userDAO, UserViewModel setLoggedInUser) {
            this.userDAO = userDAO;
            this.setLoggedInUser = setLoggedInUser;
        }

        @Override
        protected void onPostExecute(User user) {
            setLoggedInUser.setUSer(user);
        }

        @Override
        protected User doInBackground(String... values) {
            User user = userDAO.getLoginUser(values[0], values[1]);
            return user;
        }
    }
}
