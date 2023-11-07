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
import com.example.timemanager.adapters.UnitAdapter;
import com.example.timemanager.databinding.FragmentUnitsBinding;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class UnitsFragment extends Fragment {
    FragmentUnitsBinding binding;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentUnitsBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        List<String> unit = Arrays.asList(getResources().getStringArray(R.array.units));
        UnitAdapter.OnSelectClickListener onSelectClickListener = (unit1, position) -> {
            Bundle result = new Bundle();
            result.putString("bundleKey", unit1);
            getParentFragmentManager().setFragmentResult("unit", result);
            Fragment goalAdd = getParentFragmentManager().findFragmentByTag("AddFrag");
            getParentFragmentManager().beginTransaction().replace(R.id.fragmentContainerView, goalAdd).commit();
        };
        UnitAdapter adapter = new UnitAdapter(getContext(), unit, onSelectClickListener);
        binding.recyclerViewUnits.setAdapter(adapter);
    }
}