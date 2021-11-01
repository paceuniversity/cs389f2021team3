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

public class MainActivity extends AppCompatActivity {

    //Creating instance of main activity
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    //TODO use this function for any fragment that's going to open up the calendar!
    public void showDatePicker(View view) {
        DialogFragment newFragment = new DatePickerFragment();
        newFragment.show(getSupportFragmentManager(), getString(R.string.calendar_label));
    }

    //This function tracks what date the user has selected
    public void processDatePickerResult(int y, int m, int d) {
        //TODO
    }
}