package com.example.exsecutum;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

/*
this class is meant to collect data from the users interaction with the UI
and then create a task object. This object will be sent to the main activity
to be displayed on the calendar.
 */

public class taskMaker extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_maker);
    }
}