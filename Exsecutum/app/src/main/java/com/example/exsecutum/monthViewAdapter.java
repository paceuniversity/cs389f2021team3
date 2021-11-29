package com.example.exsecutum;

import android.content.Context;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TableLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.time.YearMonth;
import java.util.ArrayList;

public class monthViewAdapter extends RecyclerView.Adapter<monthViewAdapter.dayHolder> {
    private final ArrayList<Task> tasks;
    Context context;
    YearMonth yearMonth;
    int[] month;
    @RequiresApi(api = Build.VERSION_CODES.O)
    public monthViewAdapter(Context context, ArrayList<Task> tasks, YearMonth yearMonth) {
        this.context = context;
        this.tasks = tasks;
        this.yearMonth = yearMonth;
    }

    @NonNull
    @Override
    public dayHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.calendar_day, parent, false);
        return new monthViewAdapter.dayHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull dayHolder holder, int position) {
        holder.day.setText(String.valueOf(position));
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public int getItemCount() {
        return yearMonth.lengthOfMonth();
    }

    public class dayHolder extends RecyclerView.ViewHolder {

        TextView day;
        View dayHold;
        public dayHolder(@NonNull View itemView) {
            super(itemView);
            day = itemView.findViewById(R.id.Date);
            dayHold = itemView.findViewById(R.id.dayHolder);
        }
    }
}
