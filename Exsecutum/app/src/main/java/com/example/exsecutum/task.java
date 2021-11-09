package com.example.exsecutum;

import java.util.ArrayList;

//This class is a representation of a singular task in the app

public class task {
    String name;
    //these variables will need to be translated for the calendar view
    ArrayList<Character> dayOfWeek;
    String date;

    //times will automatically be in military time, can be converted for the cal. view
    int startTime;
    int endTime;

    //priority is rated 1-4: 1 being high, 4 being low
    int priority;

    public task(String nam, ArrayList<Character> dow, String dt, int start, int end, int pri){
        name = nam;
        dayOfWeek = dow;
        date= dt;
        startTime = start;
        endTime = end;
        priority = pri;
    }

    public ArrayList<Character> getDayOfWeek(){
        return dayOfWeek;
    }
    public String getDate(){
        return date;
    }

    public int getStartTime() {
        return startTime;
    }

    public int getEndTime() {
        return endTime;
    }

    public int getPriority() {
        return priority;
    }
}
