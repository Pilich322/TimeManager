package com.example.timemanager.frag;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.timemanager.R;
import com.example.timemanager.adapters.GoalsAdapter;
import com.example.timemanager.data.Goals;
import com.example.timemanager.database.DBManager;
import com.example.timemanager.databinding.FragmentGoalsBinding;

public class GoalsFragment extends Fragment {
    FragmentGoalsBinding binding;
    DBManager dbManager;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentGoalsBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding.floatingActionButtonAddGoal.setOnClickListener(view1 -> {
                   getParentFragmentManager().beginTransaction().addToBackStack("goals").replace(R.id.fragmentContainerView,new GoalAddFragment(),"AddFrag").commit();
        });
        dbManager = new DBManager(getContext());
        dbManager.openDb();
        GoalsAdapter adapter = new GoalsAdapter(getContext(),dbManager.getGoals());
        binding.recyclerViewGoals.setAdapter(adapter);
        for(Goals goals : dbManager.getGoals()){
            Log.d("LOH",goals.getId()+"");
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        dbManager.closeDb();
    }

}