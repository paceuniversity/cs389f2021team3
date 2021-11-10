package com.example.exsecutum;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import java.util.ArrayList;

public class DailyView extends AppCompatActivity {
    //this class is the actual scrolling list of tasks using recyclerview
    //TODO: implement the actual date based display, for now it's just displaying all tasks (this is fine for sprint 1)
    RecyclerView taskView;
    ArrayList<Task> tasks = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daily_view);

        taskView = findViewById(R.id.taskView);

        taskViewAdapter adapter = new taskViewAdapter(this, taskMaker.tasks);

        taskView.setAdapter(adapter);
        taskView.setLayoutManager(new LinearLayoutManager(this));
    }
}