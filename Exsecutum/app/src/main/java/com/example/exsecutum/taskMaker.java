package com.example.exsecutum;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Switch;
import android.widget.Toast;
import com.example.exsecutum.Task;
import java.util.ArrayList;

/*
this class is meant to collect data from the users interaction with the UI
and then create a task object. This object will be sent to the main activity
to be displayed on the calendar.
 */

public class taskMaker extends AppCompatActivity {
    //Declaring variables.
    static ArrayList<Task> tasks = new ArrayList<Task>();
    ArrayList<CheckBox> dowcb = new ArrayList<CheckBox>();
    Button createTask;
    EditText taskName, startTime, endTime;
    RadioButton selectedRadioButton, selectedColor;
    RadioGroup pGroup, colorGroup;
    Switch meridiemSwitch, repeatSwitch;
    public static final String TASK_NAME = "com.example.exsecutum.TASK_NAME";
    private int tid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_maker);

        //Setting variables to .xml components.
        taskName = (EditText)findViewById(R.id.editTextTaskName);
        createTask = (Button)findViewById(R.id.button_CreateTask);
        dowcb.add((CheckBox) findViewById(R.id.dow_m));
        dowcb.add((CheckBox) findViewById(R.id.dow_t));
        dowcb.add((CheckBox) findViewById(R.id.dow_w));
        dowcb.add((CheckBox) findViewById(R.id.dow_r));
        dowcb.add((CheckBox) findViewById(R.id.dow_f));
        dowcb.add((CheckBox) findViewById(R.id.dow_s));
        dowcb.add((CheckBox) findViewById(R.id.dow_c));

        createTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) { sendTask(); }
        });
    }

    //This creates a task once the user presses "Create Task."
    public void sendTask() {
        Intent mainPage = new Intent(this, MainActivity.class);

        //TODO add proper implementation of data for the tasks!
        //Declaring variables.
        String name = taskName.getText().toString();

        //TODO
        //Use datepicker for this.
        String dt = "TODO";

        //Setting up day of week.
        int dow = 0;

        //Checking to see if the user has selected any of the checkboxes for the day of week.
        for(int i = 0; i < dowcb.size(); ++i) {
            //If we have a checked box, add an additional digit to the left of our dow int. This is
            //so that 0 doesn't get overwritten.
            if(dowcb.get(i).isChecked())
                dow = dow + i * (int)Math.pow(10, i);
        }

        //Setting dow to -1 if no checkboxes were checked.
        if(dow == 0 && !dowcb.get(0).isChecked())
            dow = -1;

        //Declaring variables.
        startTime = (EditText) findViewById(R.id.start_time);
        endTime = (EditText) findViewById(R.id.end_time);
        String hour = "";
        String minutes = "";
        String startstr = startTime.getText().toString();
        String endstr = endTime.getText().toString();
        meridiemSwitch = (Switch) findViewById(R.id.start_switch);

        //int start & int end key:
        //-1 = Invalid value
        //0  = empty
        //1 - 24 = time
        int start = 0;
        int end = 0;

        //Getting the start and end date of the task if there is one.
        //Are the start and end times valid?
        if(startstr.contains(":") && endstr.contains(":")) {
            //////////////////////
            //****START TIME****//
            //////////////////////

            //Getting hour value of start time.
            for(int i = 0; i < startstr.indexOf(":"); ++i) {
                //If we have more than two digits before the colon, break from the loop and set the
                //start time to -1.
                if(i >= 2) {
                    start = -1;
                    break;
                }

                //If the character at this index a not a number, break from the loop and set the
                //start time to -1.
                if(!Character.isDigit(startstr.charAt(i))) {
                    start = -1;
                    break;
                }

                //Is our colon position is at 1?
                if(startstr.indexOf(":") == 1) {
                    //If the first number for our start time is less than the ASCII ID for 0 or
                    //greater than the ASCII ID for 9, break from the loop and set the start time
                    //to -1.
                    if((startstr.charAt(0) < 48 || startstr.charAt(0) > 57)) {
                        start = -1;
                        break;
                    }
                }
                else {
                    //If the first number for our start time is less than the ASCII ID for 0 or
                    //greater than the ASCII ID for 1, or if the second number for our start time
                    //is less than the ASCII ID for 0 or greater than the ASCII ID for 9, break
                    //from the loop and set the start time to -1.
                    if((startstr.charAt(0) < 48 || startstr.charAt(0) > 49) || (startstr.charAt(1) < 48 || startstr.charAt(1) > 57)) {
                        start = -1;
                        break;
                    }
                }

                //Adding digits for the hour for start time.
                hour += startstr.charAt(i);
            }

            //Do we have anything for hours? If not, set start time to -1.
            if(hour == null || hour.equals(""))
                start = -1;

            //If hours ends up equaling to 0, set the start time to -1.
            if(start != -1) {
                int check = Integer.parseInt(hour);
                if (check == 0)
                    start = -1;
            }

            //If start time isn't -1 we can add minutes to our start time.
            if(start != -1) {
                //Converting string to int.
                start = Integer.parseInt(hour);

                //Setting hours based on meridiem.
                //1  = 12AM
                //13 = 12PM
                //24 = 11PM
                //Setting hours to PM.
                if(!meridiemSwitch.isChecked()) {
                    if(start == 12)
                        start = 13;
                    else
                        start += 13;
                }

                //Setting hours to AM.
                else {
                    if(start == 12)
                        start = 1;
                    else
                        start += 1;
                }

                //Getting minute value of start time.
                for(int i = startstr.indexOf(":") + 1; i < startstr.length(); ++i) {
                    //Do we have more than one digit for minutes? If not, break from the loop and
                    //set the start time to -1.
                    if(startstr.indexOf(":") + 2 >= startstr.length()) {
                        start = -1;
                        break;
                    }

                    //If we have more than two digits after the colon, break from the loop and set the
                    //start time to -1.
                    if(i >= 5) {
                        start = -1;
                        break;
                    }

                    //If the character at this index a not a number, break from the loop and set the
                    //start time to -1.
                    if(!Character.isDigit(startstr.charAt(i))) {
                        start = -1;
                        break;
                    }

                    //Are we at the first digit for minutes?
                    if(i == startstr.indexOf(":") + 1) {
                        //If the first digit for our start time is less than the ASCII ID for 0 or
                        //greater than the ASCII ID for 5, or if the second digit for our start
                        //time is less than the ASCII ID for 0 or greater than the ASCII ID for 9
                        //break from the loop and set the start time to -1.
                        if((startstr.charAt(i) < 48 || startstr.charAt(i) > 53) || (startstr.charAt(i + 1) < 48 || startstr.charAt(i + 1) > 57)) {
                            start = -1;
                            break;
                        }
                    }

                    //Adding digits for the minutes for start time.
                    minutes += startstr.charAt(i);
                }

                //Do we have two digits for minutes? If not, set start time to -1.
                if(minutes.length() != 2)
                    start = -1;

                //Skip this if our start time is -1.
                if(start != -1) {
                    //Multiplying start time by 100 so that we can add minutes to start time.
                    start *= 100;

                    //Converting and adding string to start time.
                    start += Integer.parseInt(minutes);
                }
            }

            //////////////////////
            //*****END TIME*****//
            //////////////////////

            //Changing and resetting variables.
            meridiemSwitch = (Switch) findViewById(R.id.end_switch);
            hour = "";
            minutes = "";

            //Getting hour value of end time.
            for(int i = 0; i < endstr.indexOf(":"); ++i) {
                //If we have more than two digits before the colon, break from the loop and set the
                //end time to -1.
                if(i >= 2) {
                    end = -1;
                    break;
                }

                //If the character at this index a not a number, break from the loop and set the
                //end time to -1.
                if(!Character.isDigit(endstr.charAt(i))) {
                    end = -1;
                    break;
                }

                //Is our colon position is at 1?
                if(endstr.indexOf(":") == 1) {
                    //If the first number for our end time is less than the ASCII ID for 0 or
                    //greater than the ASCII ID for 9, break from the loop and set the end time
                    //to -1.
                    if((endstr.charAt(0) < 48 || endstr.charAt(0) > 57)) {
                        end = -1;
                        break;
                    }
                }
                else {
                    //If the first number for our end time is less than the ASCII ID for 0 or
                    //greater than the ASCII ID for 1, or if the second number for our end time is
                    //less than the ASCII ID for 0 or greater than the ASCII ID for 9, break from
                    //the loop and set the end time to -1.
                    if((endstr.charAt(0) < 48 || endstr.charAt(0) > 49) || (endstr.charAt(1) < 48 || endstr.charAt(1) > 57)) {
                        end = -1;
                        break;
                    }
                }

                //Adding digits for the hour for end time.
                hour += endstr.charAt(i);
            }

            //Do we have anything for hours? If not, set end time to -1.
            if(hour == null || hour.equals(""))
                end = -1;

            //If hours ends up equaling to 0, set the end time to -1.
            if(end != -1) {
                int check = Integer.parseInt(hour);
                if (check == 0)
                    end = -1;
            }

            //If end time isn't -1 we can add minutes to our end time.
            if(end != -1) {
                //Converting string to int.
                end = Integer.parseInt(hour);

                //Setting hours based on meridiem.
                //1  = 12AM
                //13 = 12PM
                //24 = 11PM
                //Setting hours to PM.
                if(!meridiemSwitch.isChecked()) {
                    if(end == 12)
                        end = 13;
                    else
                        end += 13;
                }

                //Setting hours to AM.
                else {
                    if(end == 12)
                        end = 1;
                    else
                        end += 1;
                }

                //Getting minute value of end time.
                for(int i = endstr.indexOf(":") + 1; i < endstr.length(); ++i) {
                    //Do we have more than one digit for minutes? If not, break from the loop and
                    //set the end time to -1.
                    if(endstr.indexOf(":") + 2 >= endstr.length()) {
                        end = -1;
                        break;
                    }

                    //If we have more than two digits after the colon, break from the loop and set the
                    //end time to -1.
                    if(i >= 5) {
                        end = -1;
                        break;
                    }

                    //If the character at this index a not a number, break from the loop and set the
                    //end time to -1.
                    if(!Character.isDigit(endstr.charAt(i))) {
                        end = -1;
                        break;
                    }

                    //Are we at the first digit for minutes?
                    if(i == endstr.indexOf(":") + 1) {
                        //If the first digit for our end time is less than the ASCII ID for 0 or
                        //greater than the ASCII ID for 5, or if the second digit for our end time
                        //is less than the ASCII ID for 0 or greater than the ASCII ID for 9, break
                        //from the loop and set the end time to -1.
                        if((endstr.charAt(i) < 48 || endstr.charAt(i) > 53) || (endstr.charAt(i + 1) < 48 || endstr.charAt(i + 1) > 57)) {
                            end = -1;
                            break;
                        }
                    }

                    //Adding digits for the minutes for end time.
                    minutes += endstr.charAt(i);
                }

                //Do we have two digits for minutes? If not, set end time to -1.
                if(minutes.length() != 2)
                    end = -1;

                //Skip this if our end time is -1.
                if(end != -1) {
                    //Multiplying end time by 100 so that we can add minutes to end time.
                    end *= 100;

                    //Converting and adding string to end time.
                    end += Integer.parseInt(minutes);
                }
            }
        }

        //Set the start time and end time to 0 if the text boxes are empty.
        else if((startstr == null || startstr.equals("")) && (endstr == null || endstr.equals(""))) {
            start = 0;
            end = 0;
        }

        //Set start time and end time to an invalid number if no of the other conditions were
        //satisfied.
        else {
           start = -1;
           end = -1;
        }

        //Getting priority of task.
        int pri = 0;
        pGroup = (RadioGroup) findViewById(R.id.priority);

        //If the user didn't select any priority set priority to -1.
        if(pGroup.getCheckedRadioButtonId() == -1)
            pri = -1;

        //Set the priority to what the user has selected.
        else {
            int selectedId = pGroup.getCheckedRadioButtonId();
            selectedRadioButton = (RadioButton) findViewById(selectedId);

            //Determining what button the user has selected in terms of priority.
            switch(selectedId) {
                //Lowest priority.
                case R.id.low:
                    if(selectedRadioButton.isChecked())
                        pri = 0;
                    break;

                //Semi-low priority.
                case R.id.semi_low:
                    if(selectedRadioButton.isChecked())
                        pri = 1;
                    break;

                //Semi-high priority.
                case R.id.semi_high:
                    if(selectedRadioButton.isChecked())
                        pri = 2;
                    break;

                //High priority.
                case R.id.high:
                    if(selectedRadioButton.isChecked())
                        pri = 3;
                    break;

                //Do nothing as default.
                default:
                    break;
            }
        }

        //Getting repeatSwitch value.
        repeatSwitch = (Switch) findViewById(R.id.repeat);
        boolean rp = false;

        if(repeatSwitch.isChecked())
            rp = true;


        //Assigning color of task.
        int c = 0;
        colorGroup = (RadioGroup) findViewById(R.id.color_select);

        //By default the color will be white if no color is chosen.
        if (colorGroup.getCheckedRadioButtonId() == -1) {
            c = Color.WHITE;
        }

        //Color assignment by selection.
        else {
            int colorId =  colorGroup.getCheckedRadioButtonId();
            selectedColor = (RadioButton) findViewById(colorId);

            switch (colorId) {
                //Red Task.
                case R.id.color_red:
                    if(selectedColor.isChecked())
                        c = Color.rgb(209, 15,15);
                    break;

                //Blue Task.
                case R.id.color_blue:
                    if(selectedColor.isChecked())
                        c = Color.rgb(13, 121,209);
                    break;

                //Green Task.
                case R.id.color_green:
                    if(selectedColor.isChecked())
                        c = Color.rgb(23,138,54);
                    break;

                //Orange Task.
                case R.id.color_orange:
                    if(selectedColor.isChecked())
                        c = Color.rgb(255,125, 25);
                    break;

                //Purple Task.
                case R.id.color_purple:
                    if(selectedColor.isChecked())
                        c = Color.rgb(157, 15, 209);
                    break;

                //Do nothing as default.
                default:
                    break;
            }
        }

        //Assigning id of task.
        int id = tid;

        //Increment id.
        ++tid;

        //If the there's no name for the task, don't create the task and display the why the task
        //wasn't created.
        if(name == null || name.equals(""))
            Toast.makeText(getApplicationContext(), "Please name your task", Toast.LENGTH_SHORT).show();

        //If the there's no date for the task, don't create the task and display the why the task
        //wasn't created.
        else if(dt == null || dt.equals(""))
            Toast.makeText(getApplicationContext(), "Please enter a date for your task", Toast.LENGTH_SHORT).show();

        //If the start or end time is -1 or if the start time is grater than or equal to the end
        //time, don't create the task and display the why the task wasn't created.
        else if((start == -1 || end == -1) || (start != 0 && end != 0) && (start >= end))
            Toast.makeText(getApplicationContext(), "Improper time for Start Time and/or End Time", Toast.LENGTH_SHORT).show();

        //If the user hasn't selected any priority, don't create the task and display why the task
        //wasn't created.
        else if(pri == -1)
            Toast.makeText(getApplicationContext(), "Please select a priority", Toast.LENGTH_SHORT).show();

        //If the user has made the task repeatable but hasn't selected any days for the task to be
        //assigned to, don't create the task and display why the task wasn't created.
        else if(rp == true && dow == -1)
            Toast.makeText(getApplicationContext(), "Repeatable is enabled but no days have been selected", Toast.LENGTH_SHORT).show();

        //Creating a task object.
        else {
            Task task = new Task(name, dt, id);

            //Assigning values to our newly created task.
            task.setDoW(dow);
            task.setStartTime(start);
            task.setEndTime(end);
            task.setPriority(pri);
            task.setRepeat(rp);
            task.setColor(c);

            //Assigning the task to the tasks ArrayList.
            tasks.add(task);

            //Opening up Main Activity.
            mainPage.putExtra(TASK_NAME, name);
            startActivity(mainPage);
        }
    }
}