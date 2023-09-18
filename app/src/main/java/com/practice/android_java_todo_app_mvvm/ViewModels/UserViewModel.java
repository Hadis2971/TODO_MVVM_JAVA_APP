package com.practice.android_java_todo_app_mvvm.ViewModels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.practice.android_java_todo_app_mvvm.Entities.User;
import com.practice.android_java_todo_app_mvvm.Repositories.UserRepo;

import java.util.List;

public class UserViewModel extends AndroidViewModel implements SetLoggedInUser{

    private UserRepo userRepo;
    private MutableLiveData<User> user = new MutableLiveData<>();

    public UserViewModel(@NonNull Application application) {
        super(application);
        userRepo = new UserRepo(application);
    }

    public void setUSer(User userToSet) {
        user.setValue(userToSet);
    }

    public void loginUser(String username, String password) {
        userRepo.getLoginUser(username, password, this);
    }

    public LiveData<User> getLoggedInUser() {
        return user;
    }

    public LiveData<List<User>> getAllUsers() {
        LiveData<List<User>> users = userRepo.getAllUsers();
        return users;
    }

    public User getUser(int userID) {
        return userRepo.getUser(userID);
    }

    public void insert(User registerUser) {
        userRepo.insert(registerUser);
    }

    public void update(User user) {
        userRepo.update(user);
    }

    public void delete(User user) {
        userRepo.delete(user);
    }
}
