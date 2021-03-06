package com.example.exsecutum;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;

//This class is a representation of a singular task in the app. We need to implement Serializable in
//order to convert a task into byte data.
public class Task implements Serializable {
    //This sets the name of the task.
    String name;

    //This creates a container to store sub-tasks within this task.
    ArrayList<Task> subTask;

    //Data member for if the task is complete.
    Boolean complete;

    //These variables will need to be translated for the calendar view.
    int dayOfWeek;
    LocalDate date;

    //Times will automatically be in military time, can be converted for the calendar view.
    int startTime;
    int endTime;

    //Priority is rated 1-4: 1 being high, 4 being low.
    int priority;

    //This determines if the task is repeatable.
    boolean repeat;

    //This determines the color of the task.
    int clr;

    //ID of task.
    int tId;

    //Constructor Class.
    public Task(String nam, LocalDate dt, int id) {
        this.name = nam;
        this.date= dt;
        this.tId = id;
        this.dayOfWeek = -1;
        this.startTime = 0;
        this.endTime = 0;
        this.priority = 0;
        this.repeat = false;
        this.clr = 0;
        this.complete = false;
        this.subTask = new ArrayList<Task>();
    }

    //These functions set info for out Task object.
    public void setName(String nam) { this.name = nam; }
    public void setDate(LocalDate dt) { this.date = dt; }
    public void setDoW(int dow) { this.dayOfWeek = dow; }
    public void setStartTime(int start) { this.startTime = start; }
    public void setEndTime(int end) { this.endTime = end; }
    public void setPriority(int pri) { this.priority = pri; }
    public void setRepeat(boolean rp) { this.repeat = rp; }
    public void setColor(int c) { this.clr = c; }
    public void setComplete(boolean cmplt) { this.complete = cmplt; }
    public void setSubTasks(ArrayList<Task> stasks) { this.subTask = stasks; }

    //These functions get info from our Task object.
    public String getName() { return this.name; }
    public LocalDate getDate() { return this.date; }
    public int getDayOfWeek() { return this.dayOfWeek; }
    public int getStartTime() { return this.startTime; }
    public int getEndTime() { return this.endTime; }
    public int getPriority() { return this.priority; }
    public boolean isRepeatable() { return this.repeat; }
    public int getColor() { return this.clr; }
    public int getID() { return this.tId; }
    public Boolean getComplete() { return this.complete; }
    public ArrayList<Task> getSubTasks() { return this.subTask; }


    //This converts the task into byte data.
    public static byte[] makebyte(Task t) throws IOException {
        try(ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
            try(ObjectOutputStream oos = new ObjectOutputStream(baos)) {
                oos.writeObject(t);
            }
            return baos.toByteArray();
        }
    }

    //This converts byte data into a task.
    public static Task readbyte(byte[] data) throws IOException, ClassNotFoundException {
        try(ByteArrayInputStream baip = new ByteArrayInputStream(data)) {
            try(ObjectInputStream ois = new ObjectInputStream(baip)) {
                return (Task)ois.readObject();
            }
        }
    }
}