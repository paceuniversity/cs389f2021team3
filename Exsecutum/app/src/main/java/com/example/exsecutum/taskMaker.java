package com.example.exsecutum;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

/*
this class is meant to collect data from the users interaction with the UI
and then create a task object. This object will be sent to the main activity
to be displayed on the calendar.
 */

public class taskMaker extends AppCompatActivity {
    EditText taskName;
    Button createTask;
    public static final String TASK_NAME = "com.example.exsecutum.TASK_NAME";

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

    public void sendTask() {
        Intent mainPage = new Intent(this, MainActivity.class);
        String name = taskName.getText().toString();
        mainPage.putExtra(TASK_NAME, name);
        startActivity(mainPage);
    }
}