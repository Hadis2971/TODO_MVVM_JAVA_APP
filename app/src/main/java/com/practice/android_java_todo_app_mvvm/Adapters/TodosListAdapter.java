package com.practice.android_java_todo_app_mvvm.Adapters;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.practice.android_java_todo_app_mvvm.Activities.TodoListAdapterInterface;
import com.practice.android_java_todo_app_mvvm.Activities.TodosList;
import com.practice.android_java_todo_app_mvvm.Entities.Todo;
import com.practice.android_java_todo_app_mvvm.R;
import com.practice.android_java_todo_app_mvvm.Utility.DateConvertor;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class TodosListAdapter extends RecyclerView.Adapter<TodosListAdapter.ItemViewHolder> {

    private static final int LOW_PRIORITY = 1, MEDIUM_PRIORITY = 2, HIGH_PRIORITY = 3, CRITICAL_PRIORITY = 4;

    private List<Todo> todos;
    private TodoListAdapterInterface listener;
    private ItemViewHolder holder;
    private int currentTodoIdx;

    public TodosListAdapter(List<Todo> todos, TodoListAdapterInterface listener) {
        this.todos = todos;
        this.listener = listener;
    }


    public void updateList (Todo todo) {
        //todos.add(todo);

        this.notifyItemInserted(todos.size() - 1);

    }

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View todoView = LayoutInflater.from(parent.getContext()).inflate(R.layout.todo_item, parent, false);

        return new ItemViewHolder(todoView);
    }

    private int getTodoPriorityImageResource(int priority) {
        int drawableResourceId = R.drawable.baseline_priority_low;

        switch (priority) {
            case LOW_PRIORITY:
                drawableResourceId = R.drawable.baseline_priority_low;
                break;
            case MEDIUM_PRIORITY:
                drawableResourceId = R.drawable.baseline_priority_medium;
                break;
            case HIGH_PRIORITY:
                drawableResourceId = R.drawable.baseline_priority_high;
                break;
            case CRITICAL_PRIORITY:
                drawableResourceId = R.drawable.baseline_priority_critical;
                break;
        }

        return drawableResourceId;
    }

    private int getTodoCompletedPendingImageResource(int status) {
        int drawableResourceId = 1;

        switch (status) {
            case 0:
                drawableResourceId = R.drawable.baseline_pending_low_24;
                break;
            case 1:
                drawableResourceId = R.drawable.baseline_check_circle_outline_24;
        }

        return drawableResourceId;
    }

    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder itemViewHolder, int position) {
        holder = itemViewHolder;

        Todo todo = this.todos.get(position);

        String pattern = "dd.MM.yyyy";
        DateConvertor dateConvertor = new DateConvertor(pattern);
        String dateAsString = dateConvertor.LongToDate(todo.getDueDate());

        holder.title.setText(todo.getTitle());
        holder.description.setText(todo.getDescription());
        holder.dueDate.setText(dateAsString);
        holder.priority.setImageResource(this.getTodoPriorityImageResource(todo.getPriority()));
        holder.completed.setImageResource(this.getTodoCompletedPendingImageResource(todo.getCompleted()));

        holder.deleteTodoBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteTodo(holder);
            }
        });

        holder.openUpdateTodoDialogBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                currentTodoIdx = holder.getAdapterPosition();
                Todo todo = todos.get(holder.getAdapterPosition());
                listener.openUpdateTodoDialog(todo);
            }
        });

        holder.completed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Todo todo = todos.get(holder.getAdapterPosition());
                if (todo.getCompleted() == 1) {
                    todo.setCompleted(0);
                } else {
                    todo.setCompleted(1);
                }

                listener.updateCompletionStatus(todo);

                todos.set(holder.getAdapterPosition(), todo);

                notifyDataSetChanged();
            }
        });
    }

    private void deleteTodo (ItemViewHolder holder) {
        Todo todo = todos.get(holder.getAdapterPosition());
        listener.deleteTodo(todo);
        todos.remove(holder.getAdapterPosition());

        notifyDataSetChanged();
    }

    public Todo updateTodo (String description, int priority, Long date) {
        Todo todo = todos.get(currentTodoIdx);

        if (description != null) {
            todo.setDescription(description);
        }

        if (priority != 0) {
            todo.setPriority(priority);
        }

        if (date != 0L) {
            todo.setDueDate(date);
        }

        todos.set(currentTodoIdx, todo);

        notifyDataSetChanged();

        return todo;
    }

    @Override
    public int getItemCount() {
        return this.todos.size();
    }



    static class ItemViewHolder extends RecyclerView.ViewHolder {
        TextView title, description, dueDate;
        ImageView priority, completed;
        Button deleteTodoBtn, openUpdateTodoDialogBtn;

        public ItemViewHolder(@NonNull View itemView) {
            super(itemView);
            this.title = itemView.findViewById(R.id.todo_title);
            this.description = itemView.findViewById(R.id.todo_description);
            this.dueDate = itemView.findViewById(R.id.todo_due_date);
            this.priority = itemView.findViewById(R.id.todo_priority);
            this.completed = itemView.findViewById(R.id.todo_completed);
            this.deleteTodoBtn = itemView.findViewById(R.id.delete_todo_btn);
            this.openUpdateTodoDialogBtn = itemView.findViewById(R.id.open_update_todo_dialog_btn);
        }
    }
}
