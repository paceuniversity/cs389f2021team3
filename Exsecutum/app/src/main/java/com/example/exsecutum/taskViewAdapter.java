package com.example.exsecutum;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
//the purpose of this class is to extend recyclerview so I can actually use it with the tasks
public class taskViewAdapter extends RecyclerView.Adapter<taskViewAdapter.taskHolder>{

    private final ArrayList<Task> tasks;
    Context context;

    public taskViewAdapter(Context context, ArrayList<Task> tasks){
        this.context = context;
        this.tasks = tasks;
    }
    @NonNull
    @Override
    public taskHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.task_display, parent, false);
        return new taskHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull taskHolder holder, int position) {
        holder.taskName.setText(tasks.get(position).getName());
        holder.dueDate.setText(tasks.get(position).getDate());
        holder.color = tasks.get(position).getColor();
    }

    @Override
    public int getItemCount() {
        return tasks.size();
    }
    //this is the actual contents of each individual displayed task
    //TODO: need to add more data members and implement the color display for each task
    public class taskHolder extends RecyclerView.ViewHolder {

        TextView taskName, dueDate;
        int color;
        public taskHolder(@NonNull View itemView) {
            super(itemView);
            taskName = itemView.findViewById(R.id.taskName);
            dueDate = itemView.findViewById(R.id.dueDate);
            color = R.color.task_background;
        }
    }
}
