package com.example.exsecutum;

import android.os.Build;
import android.os.Bundle;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class MonthlyView extends AppCompatActivity {
    //this class is the actual scrolling list of tasks using recyclerview
    RecyclerView taskView;
    //ArrayList<Task> tasks = new ArrayList<>();
    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daily_view);

        taskView = findViewById(R.id.taskView);

        taskViewAdapter adapter = new taskViewAdapter(this, taskMaker.tasks, 'M');

        taskView.setAdapter(adapter);
        taskView.setLayoutManager(new LinearLayoutManager(this));
    }
}