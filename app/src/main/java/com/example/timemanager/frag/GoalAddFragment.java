package com.example.timemanager.frag;

import android.app.DatePickerDialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentResultListener;

import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;

import com.example.timemanager.R;
import com.example.timemanager.data.Goals;
import com.example.timemanager.database.DBManager;
import com.example.timemanager.databinding.FragmentGoalAddBinding;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.Objects;

public class GoalAddFragment extends Fragment {

    Goals goals = new Goals();
    FragmentGoalAddBinding binding;
    Calendar dateAndTime= Calendar.getInstance();
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentGoalAddBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getParentFragmentManager().setFragmentResultListener("unit", getViewLifecycleOwner(), new FragmentResultListener() {
            @Override
            public void onFragmentResult(@NonNull String key, @NonNull Bundle bundle) {
                String result = bundle.getString("bundleKey");
                binding.textViewUnit.setText(result);
            }
        });
        binding.textViewUnit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getParentFragmentManager().beginTransaction().replace(R.id.fragmentContainerView,new UnitsFragment()).commit();
            }
        });
        binding.editTextDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new DatePickerDialog(requireContext(), d,
                        dateAndTime.get(Calendar.YEAR),
                        dateAndTime.get(Calendar.MONTH),
                        dateAndTime.get(Calendar.DAY_OF_MONTH))
                        .show();
            }
        });
        binding.buttonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goals.setName(binding.editTextGoalName.getText().toString());
                goals.setUnit(binding.textViewUnit.getText().toString());
                goals.setDescription(binding.editTextDescription.getText().toString());
                goals.setCount(Integer.parseInt(binding.editTextCount.getText().toString()));
                DBManager dbManager = new DBManager(getContext());
                dbManager.openDb();
                dbManager.addGoal(goals);
                getParentFragmentManager().beginTransaction().replace(R.id.fragmentContainerView,new UnitsFragment()).disallowAddToBackStack().commit();
            }
        });
    }
    DatePickerDialog.OnDateSetListener d= (datePicker, i, i1, i2) -> {
        dateAndTime.set(Calendar.YEAR, i);
        dateAndTime.set(Calendar.MONTH, i1);
        dateAndTime.set(Calendar.DAY_OF_MONTH, i2);
        goals.setDate(LocalDateTime.ofInstant(dateAndTime.toInstant(), dateAndTime.getTimeZone().toZoneId()).toLocalDate());
        binding.editTextDate.setText(goals.getDate().toString());
    };
}