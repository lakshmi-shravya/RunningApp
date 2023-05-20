package com.example.runningtracker;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

/**
 * Adapter to observe any changes to the database
 */
public class RunAdapter extends RecyclerView.Adapter<RunAdapter.RunViewHolder> {

    private List<Run> data;
    private Context context;
    private LayoutInflater layoutInflater;

    public RunAdapter(Context context) {
        this.data = new ArrayList<>();
        this.context = context;
        this.layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public RunViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = layoutInflater.inflate(R.layout.db_layout_view, parent, false);
        return new RunViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull RunViewHolder holder, int position) {
        holder.bind(data.get(position));

    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public void setData(List<Run> newData) {
        if (data != null) {
            data.clear();
            data.addAll(newData);
            notifyDataSetChanged();
        } else {
            data = newData;
        }
    }

    /**
     * Sub-Class to take care of the info displayed in recycler view
     */
    class RunViewHolder extends RecyclerView.ViewHolder {

        TextView durationView;
        TextView avgSpeedView;
        TextView distanceView;
        TextView dateView;
        TextView notesView;
        TextView ratingView;

        RunViewHolder(View itemView) {
            super(itemView);

            durationView = itemView.findViewById(R.id.finalTimeDB);
            avgSpeedView = itemView.findViewById(R.id.finalSpeedDB);
            distanceView = itemView.findViewById(R.id.finalDistanceDB);
            dateView = itemView.findViewById(R.id.dateDB);
            notesView = itemView.findViewById(R.id.notesDB);
            ratingView = itemView.findViewById(R.id.ratingDB);
        }

        void bind(final Run run) {

            if (run != null) {
                durationView.setText(run.getStringDuration());
                avgSpeedView.setText(run.getStringSpeed());
                distanceView.setText(run.getStringDistance());
                dateView.setText(run.getDate());
                notesView.setText(run.getNotes());
                ratingView.setText(run.getRating());
            }
        }

    }
}

