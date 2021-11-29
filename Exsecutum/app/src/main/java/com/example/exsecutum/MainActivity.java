/*
Program Name: Exsecutum
Programmer Names: Joseph Newbury, Cassandra Thomas, Robert S. Zecchini
Version: 1.0
Purpose: TBA
*/

package com.example.exsecutum;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
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


    //Creating instance of main activity.
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //This variable attaches the calendarView ID from the .xml file.
        //Copy this to some other segment of code if you want to adjust the appearance
        //of the calendar or if you want the calendar to do something.
        CalendarView mainCalendarView = (CalendarView) findViewById(R.id.calendarView);

        //Use this calendar listener in order to set the tasks to the right date! If needed, you
        //can move this segment of code into the taskMaker in order to collect the date the user
        //setup for a particular task.
        mainCalendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            //This function collects the current date that the user has selected and converts it
            //into a String.
            @Override
            public void onSelectedDayChange(@NonNull CalendarView calendarView, int y, int m, int d) {
                selectedDate = Integer.toString(y) + Integer.toString(m) + Integer.toString(d);
                dateInt = y + m + d;
            }
        });
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

    }

    //This function launches the monthView Activity
    private void launchMonthPage() {
        Intent monthPage = new Intent(this, MonthlyView.class);
        startActivity(monthPage);
    }

    //This function launches the taskView activity
    private void launchDayPage() {
        Intent dayPage = new Intent(this, DailyView.class);
        startActivity(dayPage);
    }

    //This function launches the taskMaker activity.
    private void launchNewTask() {
        Intent taskPage = new Intent(this, taskMaker.class);
        startActivity(taskPage);
    }

    //function to switch to daily view


    //TODO use this function for any fragment that's going to open up a datepicker!
    public void showDatePicker(View view) {
        DialogFragment newFragment = new DatePickerFragment();
        newFragment.show(getSupportFragmentManager(), getString(R.string.calendar_label));
    }

    //This function tracks what date the user has selected.
    public void processDatePickerResult(int y, int m, int d) {
        //TODO
        //Debug stuff (This shows the date the user has picked).
        String month_string = Integer.toString(m+1);
        String day_string = Integer.toString(d);
        String year_string = Integer.toString(y);
        String dateMessage = (month_string + "/" + day_string + "/" + year_string);
        Toast.makeText(this, getString(R.string.date_label) + ": " + dateMessage, Toast.LENGTH_SHORT).show();
        //End of debug stuff.
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