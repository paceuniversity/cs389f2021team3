/*
Program Name: Exsecutum
Programmer Names: Joseph Newbury, Cassandra Thomas, Robert S. Zecchini
Version: 1.0
Purpose: TBA
*/

package com.example.exsecutum;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import android.os.Bundle;
import android.view.View;
import android.widget.CalendarView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    //Creating instance of main activity
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //This variable attaches the calendarView ID from the .xml file.
        //Copy this to some other segment of code if you want to adjust the appearance
        //of the calendar or if you want the calendar to do something.
        CalendarView mainCalendarView = (CalendarView) findViewById(R.id.calendarView);
    }

    //TODO use this function for any fragment that's going to open up a datepicker!
    public void showDatePicker(View view) {
        DialogFragment newFragment = new DatePickerFragment();
        newFragment.show(getSupportFragmentManager(), getString(R.string.calendar_label));
    }

    //This function tracks what date the user has selected
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
}