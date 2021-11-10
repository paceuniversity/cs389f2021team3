package com.example.exsecutum;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

//This class is a representation of a singular task in the app
public class Task {
    String name;

    //these variables will need to be translated for the calendar view
    int dayOfWeek;
    String date;

    //times will automatically be in military time, can be converted for the cal. view
    int startTime;
    int endTime;

    //priority is rated 1-4: 1 being high, 4 being low
    int priority;

    //ID of task.
    int tId;

    public Task(String nam, String dt, int dow, int start, int end, int pri, int id) {
        this.name = nam;
        this.dayOfWeek = dow;
        this.date= dt;
        this.startTime = start;
        this.endTime = end;
        this.priority = pri;
        this.tId = id;
    }

    //These get info from our Task object.
    public String getName(){
        return this.name;
    }
    public String getDate(){
        return this.date;
    }
    public int getDayOfWeek(){
        return this.dayOfWeek;
    }
    public int getStartTime() {
        return this.startTime;
    }
    public int getEndTime() {
        return this.endTime;
    }
    public int getPriority() {
        return this.priority;
    }

    //This converts the task into byte data.
    public byte[] makebyte(Task t) {
        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream(baos);
            oos.writeObject(t);
            byte[] employeeAsBytes = baos.toByteArray();
            ByteArrayInputStream bais = new ByteArrayInputStream(employeeAsBytes);
            return employeeAsBytes;
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    //This converts byte data into a task.
    public Task read(byte[] data) {
        try {
            ByteArrayInputStream baip = new ByteArrayInputStream(data);
            ObjectInputStream ois = new ObjectInputStream(baip);
            Task dataobj = (Task) ois.readObject();
            return dataobj ;
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }
}
