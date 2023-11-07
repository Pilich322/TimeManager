package com.example.timemanager.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.timemanager.R;
import com.example.timemanager.data.Goals;
import com.example.timemanager.database.DBManager;

import java.io.InputStream;
import java.util.List;

public class GoalsAdapter extends RecyclerView.Adapter<GoalsAdapter.ViewHolder> {

    private LayoutInflater layoutInflater;
    Context context;
    private List<Goals> goalsList;
    private DBManager dbManager;

    public GoalsAdapter(Context context, List<Goals> goalsList) {
        dbManager = new DBManager(context);
        this.context = context;
        layoutInflater = LayoutInflater.from(context);
        this.goalsList = goalsList;
    }

    @NonNull
    @Override
    public GoalsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.goal_item, parent, false);
        return new ViewHolder(view);
    }

    @SuppressLint("NotifyDataSetChanged")
    @Override
    public void onBindViewHolder(@NonNull GoalsAdapter.ViewHolder holder, int position) {
        Goals goal = goalsList.get(position);
        if (goal.getDate() == null)
            holder.timeLeft.setVisibility(View.INVISIBLE);
        holder.name.setText(goal.getName());
        holder.description.setText(goal.getDescription());
        holder.timeLeft.setText(goal.getDate().toString());
        double pr = goal.getCountNow() == 0 ? 0.0 : goal.getCountNow() / goal.getCount() * 100;
        Log.d("LOH", goal.getCountNow() / goal.getCount() + "");
        holder.count.setText(String.format(goal.getCountNow() + " из %.0f " + goal.getUnit() + " %.2f %%", goal.getCount(), pr));
        holder.counter.setProgress(Integer.parseInt(String.valueOf((int) pr)));
        Glide.with(context)
                .load(goal.getImage())
                .into(holder.imageView);
        holder.click.setOnClickListener(view -> {
            if (holder.count.getVisibility() == View.VISIBLE) {
                holder.countAdd.setVisibility(View.VISIBLE);
                holder.countAdd.requestFocus();
                holder.count.setVisibility(View.INVISIBLE);
                holder.click.setImageResource(R.drawable.baseline_check_24);
            } else {
                holder.countAdd.setVisibility(View.INVISIBLE);
                holder.count.setVisibility(View.VISIBLE);
                goal.setCountNow(goal.getCountNow() + Integer.parseInt(holder.countAdd.getText().toString()));
                dbManager.openDb();
                dbManager.updateGoal(goal);
                dbManager.closeDb();
                holder.countAdd.clearFocus();
                holder.countAdd.setText("");
                notifyDataSetChanged();
                holder.click.setImageResource(R.drawable.baseline_add_24);
            }
        });
    }

    @Override
    public int getItemCount() {
        return goalsList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView timeLeft, name, description, count;
        ImageView click;
        ProgressBar counter;
        EditText countAdd;
        ImageView imageView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            timeLeft = itemView.findViewById(R.id.textViewTimeLeft);
            name = itemView.findViewById(R.id.textViewGoalName);
            description = itemView.findViewById(R.id.textViewGoalDescription);
            count = itemView.findViewById(R.id.textViewGoalCount);
            click = itemView.findViewById(R.id.imageView);
            counter = itemView.findViewById(R.id.progressBar);
            countAdd = itemView.findViewById(R.id.editTextCountNow);
            imageView = itemView.findViewById(R.id.imageViewGoalImage);
        }
    }
}
