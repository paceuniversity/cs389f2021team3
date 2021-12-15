package com.example.exsecutum;

import android.content.Context;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import java.time.LocalDate;
import java.util.ArrayList;


//the purpose of this class is to extend recyclerview so I can actually use it with the tasks
public class taskViewAdapter extends RecyclerView.Adapter<taskViewAdapter.taskHolder>{
    private ArrayList<Task> tasks;
    Context context;
    private char type;
    @RequiresApi(api = Build.VERSION_CODES.O)

    public taskViewAdapter(Context context, ArrayList<Task> tasks, char type){
        this.context = context;
        this.type = type;

        for (int i = 0; i < tasks.size(); ++i){//checking for overdue tasks
            if (tasks.get(i).getDate().compareTo(LocalDate.now()) < 0){
                System.out.println("Overdue Task detected: " + tasks.get(i).getDate().toString());
                tasks.get(i).setPriority(5);//we set the task to a priority level higher than the user can set so it's always on top
            }
        }

        tasks.sort((t1, t2) -> {        //'compareTo' method did not want to work so Im manually comparing .-.
            if(t1.getPriority() < t2.getPriority())
                return 1;
            else
                return -1;
        });

        //tasks is sorted by priority now, I created a 'new' list so the
        //completion of tasks doesn't interfere with the database

        if (tasks.size() > 0) {
            if (type == 'D') {
                this.tasks = new ArrayList(viewDay(tasks));
            } else if (type == 'W') {
                this.tasks = new ArrayList(viewWeek(tasks));
            } else {
                this.tasks = new ArrayList(viewMonth(tasks));
            }
        }
        else
            this.tasks = new ArrayList(tasks);
    }

    //this will handle if the view is a day
    @RequiresApi(api = Build.VERSION_CODES.O)
    public ArrayList<Task> viewDay(ArrayList<Task> tasks){
        ArrayList<Task> temp = new ArrayList<>();
        System.out.println("Day");
        System.out.println(LocalDate.now().toString());
        for (int i = 0; i < tasks.size(); ++i){
            if (tasks.get(i).getDate().compareTo(LocalDate.now()) == 0 || tasks.get(i).getPriority() == 5){
                System.out.println(tasks.get(i).getDate().toString());
                System.out.println(LocalDate.now().toString());
                temp.add(tasks.get(i));
            }
        }
        return temp;
    }
    @RequiresApi(api = Build.VERSION_CODES.O)
    public ArrayList<Task> viewWeek(ArrayList<Task> tasks){
        ArrayList<Task> temp = new ArrayList<>();
        System.out.println("Week");
        for (int i = 0; i < tasks.size(); ++i){
            if (WeekHelper.getWeek(tasks.get(i).getDate()).compareTo(WeekHelper.getWeek(LocalDate.now())) == 0 || tasks.get(i).getPriority() == 5){
                temp.add(tasks.get(i));
            }
        }
        return temp;
    }
    @RequiresApi(api = Build.VERSION_CODES.O)
    public ArrayList<Task> viewMonth(ArrayList<Task> tasks){
        ArrayList<Task> temp = new ArrayList<>();
        System.out.println("Month");
        for (int i = 0; i < tasks.size(); ++i){
            if (tasks.get(i).getDate().getMonth().compareTo(LocalDate.now().getMonth()) == 0 || tasks.get(i).getPriority() == 5){
                temp.add(tasks.get(i));
            }
        }
        return temp;
    }
    @NonNull
    @Override
    public taskHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.task_display, parent, false);
        return new taskHolder(view);
    }

    @Override
    //this is essentially feeding the data into each individual task item
    public void onBindViewHolder(@NonNull taskHolder holder, int position) {
        holder.taskName.setText(tasks.get(position).getName());
        holder.dueDate.setText(tasks.get(position).getDate().toString());
        holder.taskHold.setBackgroundColor(tasks.get(position).getColor());

        //task completion functionality TODO: make sure accidental checks can be undone
        int pos = position;
        holder.taskComplete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (((CompoundButton) view).isChecked()){
                    tasks.get(pos).setComplete(true);
                    //it's necessary to delete the task from the list so an empty taskHolder
                    //does not display on the view (why I made a copy of the ArrayList)
                    tasks.remove(pos);
                    notifyItemRemoved(pos);
                    notifyItemRangeChanged(pos, tasks.size());
                }
                else
                    tasks.get(pos).setComplete(false);
            }
        });
    }

    @Override
    public int getItemCount() {
        return tasks.size();
    }

    //this is the actual contents of each individual displayed task
    //TODO: need to add more data members (due date especially)
    public class taskHolder extends RecyclerView.ViewHolder {

        TextView taskName, dueDate;
        View taskHold;
        CheckBox taskComplete;
        public taskHolder(@NonNull View itemView) {
            super(itemView);
            taskName = itemView.findViewById(R.id.taskName);
            dueDate = itemView.findViewById(R.id.dueDate);
            taskHold = itemView.findViewById(R.id.taskHolder);
            taskComplete = itemView.findViewById(R.id.complete);
        }

    }
}