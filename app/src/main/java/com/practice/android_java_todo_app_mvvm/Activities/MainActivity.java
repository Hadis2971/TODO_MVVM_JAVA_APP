package com.practice.android_java_todo_app_mvvm.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.practice.android_java_todo_app_mvvm.R;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    Button registerBtn, loginBtn;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        registerBtn = findViewById(R.id.app_register_btn);
        loginBtn = findViewById(R.id.app_login_btn);

        registerBtn.setOnClickListener(this);
        loginBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.app_register_btn:
                startActivity(new Intent(MainActivity.this, AuthRegister.class));
                break;
            case R.id.app_login_btn:
                startActivity(new Intent(MainActivity.this, AuthLogin.class));
        }
    }
}