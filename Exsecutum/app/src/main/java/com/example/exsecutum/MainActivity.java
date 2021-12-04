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
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.Toast;
import com.example.exsecutum.mySQLiteDBHandler;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity {
    //Declaring variables.
    private Button daily;
    private Button weekly;
    private Button newTask;
    private Button toTimer;
    private mySQLiteDBHandler dbHandler;
    private String selectedDate;
    private int dateInt;
    private static SQLiteDatabase sqLiteDatabase;
    private static final String TAG = "MyActivity";
    private String taskName;
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

        //TODO use this to clear the database if it gets weird.
        //Context context = getApplicationContext();
        //context.deleteDatabase("CalendarDatabase");
        //TODO

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

        //Syncing tasks ArrayList with our database.
        taskMaker.tasks = ReadDatabase();

        //Receiving the intent from creating a task, then storing the name that was sent over.
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

        //button to the timer page
        toTimer = findViewById(R.id.buttonTimer);
        toTimer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) { launchTimerPage(); }
        });

    }

    private void launchTimerPage() {
        Intent timerPage = new Intent(this, timer.class);
        startActivity(timerPage);
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
    public static void InsertDatabase(byte [] data) {
        ContentValues contentValue = new ContentValues();
        contentValue.put("ID", data);
        sqLiteDatabase.insert("TaskCalendar", null, contentValue);
        sqLiteDatabase.close();
    }

    //This function reads all of the tasks that are stored within our database. This returns an
    //array of tasks.
    public static ArrayList<Task> ReadDatabase() {
        String query = "Select * from TaskCalendar";
        try {
            //This acts as a pointer for our database.
            Cursor cursor = sqLiteDatabase.rawQuery(query, null);

            //Create a byte array to get all of the tasks in order.
            ArrayList<Task> results = new ArrayList<Task>();
            Task converter = null;

            //Getting and outputting all tasks that were created.
            if(cursor.moveToFirst()) {
                //Moving to the next task if it's not null.
                while(cursor.moveToNext()) {
                    //Adding task to results.
                    byte[] tdata = cursor.getBlob(0);
                    if(tdata != null) {
                        converter = (Task) converter.readbyte(tdata);
                        results.add(converter);
                    }
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

    //This function removes a task from the database.
    public static void RemoveFromDatabase(Task t) {
        byte[] data = null;
        //Converting task into byte data.
        try {
            data = t.makebyte(t);
        } catch (IOException e) {
            e.printStackTrace();
        }

        //Were we able to convert the task into byte data?
        if(data != null) {
            //Tranversing the database until we find a match of our serialized task.
            sqLiteDatabase.delete("TaskCalendar", "BLOB=" + data, null);
            sqLiteDatabase.close();
        }
    }
}