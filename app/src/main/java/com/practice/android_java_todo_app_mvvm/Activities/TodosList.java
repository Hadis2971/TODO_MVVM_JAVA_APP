package com.practice.android_java_todo_app_mvvm.Activities;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.practice.android_java_todo_app_mvvm.Adapters.TodosListAdapter;
import com.practice.android_java_todo_app_mvvm.Dialogs.AddTodoAdditionalInfoDialog;
import com.practice.android_java_todo_app_mvvm.Entities.Todo;
import com.practice.android_java_todo_app_mvvm.R;
import com.practice.android_java_todo_app_mvvm.ViewModels.TodoViewModel;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;


public class TodosList extends AppCompatActivity implements View.OnClickListener, TodoAdditionalInfoInterface, TodoListAdapterInterface {

    private RecyclerView todoListRecyclerView;
    private TodosListAdapter todosListAdapter;
    private TodoViewModel todoViewModel;
    private AddTodoAdditionalInfoDialog addTodoAdditionalInfoDialog;
    private EditText addTodoTitleText;
    private Button addTodoBtn, additionalInfoBtn;
    private int userID, priority = 0;
    private String description, date;
    private List<Todo> todosToRender;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_todos_list);

        addTodoTitleText = findViewById(R.id.add_todo_title_input);
        addTodoBtn = findViewById(R.id.add_todo_btn);
        additionalInfoBtn = findViewById(R.id.add_additional_info_todo_btn);

        todoListRecyclerView = findViewById(R.id.todos_list);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        todoListRecyclerView.setLayoutManager(linearLayoutManager);

        addTodoAdditionalInfoDialog = new AddTodoAdditionalInfoDialog(TodosList.this);

        todoViewModel = new ViewModelProvider(this).get(TodoViewModel.class);

        Bundle intentData = getIntent().getExtras();
        userID = intentData.getInt("userID");

        todoViewModel.getAllTodos(userID).observe(this, todos -> {
            todosToRender = todos;

            todosListAdapter = new TodosListAdapter(todosToRender, this);
            todoListRecyclerView.setAdapter(todosListAdapter);
        });


        addTodoBtn.setOnClickListener(this);
        additionalInfoBtn.setOnClickListener(this);
    }


    public void getAdditionalTodoInfo(String date, int priority, String description) {
        this.date = date;
        this.priority = priority;
        this.description = description;
    }

    public void closeDialog() {

    }

    public void deleteTodo (Todo todo) {
        todoViewModel.delete(todo);
    }

    public void openUpdateTodoDialog (Todo todo) {
        this.description = todo.getDescription();
        this.priority = todo.getPriority();


        Date date = new Date(TimeUnit.SECONDS.toMillis(todo.getDueDate()));

        this.date = date.toString();

        addTodoAdditionalInfoDialog = new AddTodoAdditionalInfoDialog(TodosList.this, this.description, this.priority);

        addTodoAdditionalInfoDialog.show(getSupportFragmentManager(), "OPEN UPDATE TODO DIALOG");
    }

    public void updateTodo (String description, int priority, String date) throws ParseException {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy.M.d");
        long dateInMilliseconds = 0L;

        if (date != "" && date != null) {
            dateInMilliseconds = simpleDateFormat.parse(date).getTime();
        }

        Todo todo = this.todosListAdapter.updateTodo(description, priority, dateInMilliseconds);

        todoViewModel.update(todo);
    }

    public void updateCompletionStatus (Todo todo) {
        todoViewModel.update(todo);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.add_todo_btn:
                Todo todo = new Todo(addTodoTitleText.getText().toString(), userID);

                todo.setDescription(this.description);

                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy.M.d");

                long milliseconds;
                try {
                    Date date = simpleDateFormat.parse(this.date);
                    milliseconds = date.getTime();
                } catch (ParseException e) {
                    throw new RuntimeException(e);
                }


                if (this.date != null) todo.setDueDate(milliseconds);
                if (this.priority != 0) todo.setPriority(this.priority);
                todo.setCompleted(0);

                this.todosToRender.add(todo);
                todoViewModel.insert(todo);

                this.todosListAdapter.updateList(todosToRender);
                break;
            case R.id.add_additional_info_todo_btn:
                addTodoAdditionalInfoDialog.show(getSupportFragmentManager(), "OPEN ADD TODO DIALOG");
                break;
        }
    }
}