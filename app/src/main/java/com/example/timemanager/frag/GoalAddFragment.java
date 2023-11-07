package com.example.timemanager.frag;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentResultListener;

import android.provider.MediaStore;
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
    DBManager dbManager;

    Calendar dateAndTime = Calendar.getInstance();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState != null) {
            binding.editTextDate.setText(savedInstanceState.getString("date"));
            binding.editTextDescription.setText(savedInstanceState.getString("description"));
            binding.editTextGoalName.setText(savedInstanceState.getString("name"));
            binding.editTextCount.setText(String.valueOf(savedInstanceState.getInt("count")));
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        dbManager.closeDb();
    }

    @Override
    public void onSaveInstanceState(final Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("name", binding.editTextGoalName.getText().toString());
        outState.putString("description", binding.editTextDescription.getText().toString());
        outState.putString("date", binding.editTextDate.getText().toString());
        outState.putInt("count", Integer.parseInt(binding.editTextDate.getText().toString()));
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
        getParentFragmentManager().setFragmentResultListener("unit", getViewLifecycleOwner(), (key, bundle) -> {
            String result = bundle.getString("bundleKey");
            binding.textViewUnit.setText(result);
        });
        binding.imageViewGoal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Select Picture"), 1);
            }
        });

        binding.textViewUnit.setOnClickListener(view13 ->
                getParentFragmentManager()
                        .beginTransaction()
                        .addToBackStack(null)
                        .replace(R.id.fragmentContainerView, new UnitsFragment())
                        .commit()
        );

        binding.editTextDate.setOnClickListener(view12 -> new DatePickerDialog(requireContext(), d,
                dateAndTime.get(Calendar.YEAR),
                dateAndTime.get(Calendar.MONTH),
                dateAndTime.get(Calendar.DAY_OF_MONTH))
                .show()
        );

        binding.buttonSave.setOnClickListener(view1 -> {
            goals.setName(binding.editTextGoalName.getText().toString());
            goals.setUnit(binding.textViewUnit.getText().toString());
            goals.setDescription(binding.editTextDescription.getText().toString());
            goals.setCount(Integer.parseInt(binding.editTextCount.getText().toString()));
            dbManager = new DBManager(getContext());
            dbManager.openDb();
            dbManager.addGoal(goals);
            getParentFragmentManager().beginTransaction().replace(R.id.fragmentContainerView, new GoalsFragment()).disallowAddToBackStack().commit();
        });
    }

    DatePickerDialog.OnDateSetListener d = (datePicker, i, i1, i2) -> {
        dateAndTime.set(Calendar.YEAR, i);
        dateAndTime.set(Calendar.MONTH, i1);
        dateAndTime.set(Calendar.DAY_OF_MONTH, i2);
        goals.setDate(LocalDateTime.ofInstant(dateAndTime.toInstant(), dateAndTime.getTimeZone().toZoneId()).toLocalDate());
        binding.editTextDate.setText(goals.getDate().toString());
    };

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1 && resultCode == Activity.RESULT_OK && data != null && data.getData() != null) {
            Uri imageUri = data.getData();
            goals.setImage(imageUri.getPath());
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), imageUri);
                binding.imageViewGoal.setImageBitmap(bitmap);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


}