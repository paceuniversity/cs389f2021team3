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

public class MainActivity extends AppCompatActivity {
    //Declaring variables.
    private Button daily;
    private Button weekly;
    private Button newTask;
    private mySQLiteDBHandler dbHandler;
    private String selectedDate;
    private SQLiteDatabase sqLiteDatabase;

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
            }
        });
        //Creating the database for our tasks.
        try {
            dbHandler = new mySQLiteDBHandler(this, "CalendarDatabase", null, 1);
            sqLiteDatabase = dbHandler.getWritableDatabase();
            sqLiteDatabase.execSQL("CREATE TABLE TaskCalendar(Date TEXT, Task TEXT)");
        }
        //Send an error if we can't create a database.
        catch (Exception e) {
            e.printStackTrace();
        }

        //Setting up a 'new task' button to switch to the task maker page.
        newTask = (Button)findViewById(R.id.buttonNewTask);
        newTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                launchNewTask();
            }
        });

        //making button handler for viewing a day
        daily = (Button)findViewById(R.id.ButtonDay);
        daily.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //go to day screen
            }
        });
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
    public void InsertDatabase(View view) {
        ContentValues contentValues = new ContentValues();

        //TODO if you move mainCalendarView.setOnDateChangeListener() to a different activity, this must be edited!
        contentValues.put("Date", selectedDate);

        //TODO replace [COMPONENT_NAME] with the name of the component that takes the name of the task!
        contentValues.put("Task", [COMPONENT_NAME].getText().toString());
        sqLiteDatabase.insert("TaskCalendar", null, contentValues);
    }

    //This function reads all of the tasks that are stored within our database.
    public void ReadDatabase(View view) {
        //TODO once again, selectedDate would have to be edited if you move mainCalendarView.setOnDateChangeListener() to a different activity!
        String query = "Select Task from TaskCalendar where Date=" + selectedDate;
        try {
            Cursor cursor = sqLiteDatabase.rawQuery(query, null);
            cursor.moveToFirst();

            //TODO once again, replace [COMPONENT_NAME] with the name of the component that takes the name of the task!
            [COMPONENT_NAME].setText(cursor.getString(0));
        }
        //Send an error if we can't read a task from the database.
        catch (Exception e) {
            e.printStackTrace();

            //TODO once again, replace [COMPONENT_NAME] with the name of the component that takes the name of the task!
            [COMPONENT_NAME].setText("");
        }
    }
}