/*
Program Name: DatePickerFragment
Programmer Name: Robert S. Zecchini
Version: 1.0
Purpose: This fragment sets up the current date for our calendar app.
*/

package com.example.exsecutum;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.widget.DatePicker;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;

import java.util.Calendar;

public class DatePickerFragment extends DialogFragment implements DatePickerDialog.OnDateSetListener {

    //Overriding Dialog creation.
    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        //Get the current date and set it as the default date for the datepicker.
        final Calendar c = Calendar.getInstance();
        int y = c.get(Calendar.YEAR);
        int m = c.get(Calendar.MONTH);
        int d = c.get(Calendar.DAY_OF_MONTH);

        //Creating and returning a new instance of DatePickerDialog.
        return new DatePickerDialog(getActivity(), this, y, m, d);
    }

    //Overriding onDateSet to retrieve data on what date the user has selected.
    @Override
    public void onDateSet(DatePicker datePicker, int y, int m, int d) {
        //Getting the main activity that where we'll set the current date.
        taskMaker activity = (taskMaker) getActivity();
        assert activity != null;
        activity.processDatePickerResult(y, m, d);
    }


}
