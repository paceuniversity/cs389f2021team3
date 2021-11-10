package com.example.exsecutum;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.ArrayList;

/*
this class is meant to collect data from the users interaction with the UI
and then create a task object. This object will be sent to the main activity
to be displayed on the calendar.
 */

public class taskMaker extends AppCompatActivity {
    ArrayList<Task> tasks = new ArrayList<Task>();
    EditText taskName;
    Button createTask;
    public static final String TASK_NAME = "com.example.exsecutum.TASK_NAME";
    private int tid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_maker);
        taskName = (EditText)findViewById(R.id.editTextTaskName);
        createTask = (Button)findViewById(R.id.button_CreateTask);

        createTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendTask();
            }
        });
    }

    //This creates a task once the user presses "Create Task."
    public void sendTask() {
        Intent mainPage = new Intent(this, MainActivity.class);

        //TODO add proper implementation of data for the tasks!
        String name = taskName.getText().toString();

        //TODO
        //Use datepicker for this.
        String dt = "TODO";
        int dow = -1;
        int start = -1;
        int end = -1;
        int pri = -1;

        int id = tid;
        //Increment id
        ++tid;

        //Creating a task object.
        Task task = new Task(name, dt, dow, start, end, pri, id);

        //Assigning the task to the tasks ArrayList.
        tasks.add(task);

        mainPage.putExtra(TASK_NAME, name);
        startActivity(mainPage);
    }
}