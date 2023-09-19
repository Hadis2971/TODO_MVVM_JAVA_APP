package com.practice.android_java_todo_app_mvvm.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.gson.Gson;
import com.practice.android_java_todo_app_mvvm.R;
import com.practice.android_java_todo_app_mvvm.ViewModels.UserViewModel;

public class AuthLogin extends AppCompatActivity implements View.OnClickListener {

    UserViewModel userViewModel;
    EditText usernameInput, passwordInput;
    Button loginBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auth_login);

        userViewModel = new ViewModelProvider(this ).get(UserViewModel.class);

        usernameInput = findViewById(R.id.username_input);
        passwordInput = findViewById(R.id.password_input);
        loginBtn = findViewById(R.id.app_login_btn);

        loginBtn.setOnClickListener(this);

        userViewModel.getLoggedInUser().observe(this, user -> {
            Gson gson = new Gson();

            Intent intent = new Intent(AuthLogin.this, TodosList.class);
            intent.putExtra("userID", user.getId());
            startActivity(intent);
        });
    }

    private void loginUser () {
        userViewModel.loginUser(usernameInput.getText().toString(), passwordInput.getText().toString());

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.app_login_btn:
                loginUser();
                break;
        }
    }
}