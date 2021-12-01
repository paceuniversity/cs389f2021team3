/*
Program Name: Exsecutum
Programmer Names: Joseph Newbury, Cassandra Thomas, Robert S. Zecchini
Version: 1.0
Purpose: TBA
*/

package com.example.exsecutum;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity {
    //Declaring variables.
    private Button daily;
    private Button weekly;
    private Button newTask;
    private mySQLiteDBHandler dbHandler;
    private String selectedDate;
    private int dateInt;
    private SQLiteDatabase sqLiteDatabase;
    private String taskName;
    public HashMap<Integer, ArrayList<Task>> tasks;
    RecyclerView taskView;

    //Creating instance of main activity.
    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //display for the day's activities
        taskView = findViewById(R.id.taskView);

        taskViewAdapter adapter = new taskViewAdapter(this, taskMaker.tasks, 'D');

        taskView.setAdapter(adapter);
        taskView.setLayoutManager(new LinearLayoutManager(this));

        //Creating the database for our tasks.
        try {
            dbHandler = new mySQLiteDBHandler(this, "CalendarDatabase", null, 1);
            sqLiteDatabase = dbHandler.getWritableDatabase();
            sqLiteDatabase.execSQL("CREATE TABLE TaskCalendar(ID BLOB)");
        }
        //Send an error if we can't create a database.
        catch (Exception e) {
            e.printStackTrace();
        }

        //recieving the intent from creating a task, then storing the name that was sent over
        Intent fromTaskMaker = getIntent();
        taskName = fromTaskMaker.getStringExtra(taskMaker.TASK_NAME);


        //Setting up a 'new task' button to switch to the task maker page.
        newTask = (Button)findViewById(R.id.buttonNewTask);
        newTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                launchNewTask();
            }
        });

        //making button handler for viewing a day
        daily = findViewById(R.id.ButtonDay);
        daily.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                launchDayPage();
            }
        });

        //button for viewing a month
        daily = findViewById(R.id.ButtonMonth);
        daily.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                launchMonthPage();
            }
        });

        //button for viewing a week
        daily = findViewById(R.id.ButtonWeek);
        daily.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                launchWeekPage();
            }
        });
    }

    //Button to view the month
    private void launchMonthPage() {
        Intent monthPage = new Intent(this, MonthlyView.class);
        startActivity(monthPage);
    }

    //Button to view the day
    private void launchDayPage() {
        Intent dayPage = new Intent(this, DailyView.class);
        startActivity(dayPage);
    }

    //Button to view the week
    private void launchWeekPage() {
        Intent weekPage = new Intent(this, WeeklyView.class);
        startActivity(weekPage);
    }

    //This function launches the taskMaker activity.
    private void launchNewTask() {
        Intent taskPage = new Intent(this, taskMaker.class);
        startActivity(taskPage);
    }

    //This function will insert tasks into our database.
    public void InsertDatabase(byte data) {
        ContentValues contentValue = new ContentValues();
        contentValue.put("ID", data);
        sqLiteDatabase.insert("TaskCalendar", null, contentValue);
    }

    //This function reads all of the tasks that are stored within our database. This returns an
    //array of tasks.
    public ArrayList<Task> ReadDatabase(byte data) {
        String query = "Select * from TaskCalendar where ID=" + data;
        try {
            //This acts as a pointer for our database.
            Cursor cursor = sqLiteDatabase.rawQuery(query, null);

            //Create a byte array to get all of the task names in order.
            ArrayList<Task> results = new ArrayList<Task>();
            Task converter = null;

            //Getting and outputting all tasks that were created on selectedDate.
            if(cursor.moveToFirst()) {
                while(!cursor.isAfterLast()) {
                    //Adding task name to results.
                    byte[] tdata = cursor.getBlob(0);
                    converter = converter.read(tdata);
                    results.add(converter);

                    //Moving to the next task if it's not null.
                    cursor.moveToNext();
                }
            }

            //Returning all relevant task as an array.
            return results;
        }
        //Send an error if we can't read a task from the database.
        catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}