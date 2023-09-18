package com.practice.android_java_todo_app_mvvm.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.google.gson.Gson;
import com.practice.android_java_todo_app_mvvm.Entities.User;
import com.practice.android_java_todo_app_mvvm.R;
import com.practice.android_java_todo_app_mvvm.Repositories.UserRepo;
import com.practice.android_java_todo_app_mvvm.ViewModels.UserViewModel;

public class AuthRegister extends AppCompatActivity implements View.OnClickListener {

    UserViewModel userViewModel;
    EditText firstNameInput, lastNameInput, usernameInput, passwordInput;
    Button registerUserBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auth_register);

        userViewModel = new ViewModelProvider(this).get(UserViewModel.class);
        firstNameInput = findViewById(R.id.firstname_input);
        lastNameInput = findViewById(R.id.lastname_input);
        usernameInput = findViewById(R.id.username_input);
        passwordInput = findViewById(R.id.password_input);

        registerUserBtn = findViewById(R.id.app_register_btn);
        registerUserBtn.setOnClickListener(this);
    }

    private void registerUser() {
        Toast.makeText(this, "Hello World", Toast.LENGTH_LONG).show();
        System.out.println("Hello World!!!");

        User user = new User(firstNameInput.getText().toString(), lastNameInput.getText().toString(), usernameInput.getText().toString(), passwordInput.getText().toString());
        userViewModel.insert(user);
        startActivity(new Intent(this, AuthLogin.class));
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.app_register_btn:
                registerUser();
                break;
        }
    }
}