package com.example.timemanager.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.timemanager.R;

import java.util.List;

public class UnitAdapter extends RecyclerView.Adapter<UnitAdapter.ViewHolder> {

    public interface OnSelectClickListener {
        void onSelectClick(String unit,int position);
    }

    private List<String> units;
    private LayoutInflater inflater;
    private final OnSelectClickListener onSelectClickListener;

    public UnitAdapter(Context context,List<String> units,OnSelectClickListener onSelectClickListener){
        inflater = LayoutInflater.from(context);
        this.units = units;
        this.onSelectClickListener = onSelectClickListener;

    }

    @NonNull
    @Override
    public UnitAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.unit_item,parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UnitAdapter.ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        String unit = units.get(position);
        holder.unit.setText(unit);
        holder.itemView.setOnClickListener(view -> onSelectClickListener.onSelectClick(unit,position));
    }
    @Override
    public int getItemCount() {
        return units.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView unit;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            unit = itemView.findViewById(R.id.textViewUnitSelected);
        }
    }
}
