package com.example.exsecutum;

import android.os.Build;
import android.os.Bundle;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.time.*;
//TODO: add separation between the different days for visual clarity
//TODO: Make it so that the circles only turn red if there's a task on that day
//TODO: Make it says the actual month instead of "Month"
//TODO: Make the month starts on 1 instead of 0, and make it so that the month starts on the proper day of the week
//TODO: Add week day names to top of page under the month name
//TODO: Make it so that the days are buttons which launch a dayview with the day that was pressed
//TODO: Add arrows at the top to navigate months
public class MonthlyView extends AppCompatActivity {
    //this class is for the month view. It uses the gridlayoutmanager in recyclerview
    RecyclerView monthView;
    YearMonth yearMonth;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.calendar_month);

        monthView = findViewById(R.id.monthView);

        monthViewAdapter adapter = new monthViewAdapter(this, taskMaker.tasks, YearMonth.now());

        monthView.setAdapter(adapter);
        monthView.setLayoutManager(new GridLayoutManager(this, 7));
    }
}
