package com.example.exsecutum;

import android.os.Build;

import androidx.annotation.RequiresApi;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.ArrayList;

public class WeekHelper {

    //this function is meant to help get the first day of a week since the time api doesn't do that
    @RequiresApi(api = Build.VERSION_CODES.O)
    public static LocalDate getWeek(LocalDate date){
        DayOfWeek day = date.getDayOfWeek();
        LocalDate firstDate;
        if (day == DayOfWeek.SUNDAY){
            firstDate = date;
        }
        else if (day == DayOfWeek.MONDAY){
            firstDate = date.minusDays(1);
        }
        else if (day == DayOfWeek.TUESDAY){
            firstDate = date.minusDays(2);
        }
        else if (day == DayOfWeek.WEDNESDAY){
            firstDate = date.minusDays(3);
        }
        else if (day == DayOfWeek.THURSDAY){
            firstDate = date.minusDays(4);
        }
        else if (day == DayOfWeek.FRIDAY) {
            firstDate = date.minusDays(5);
        }
        else {
            firstDate = date.minusDays(6);
        }
        return firstDate;
    }
}
