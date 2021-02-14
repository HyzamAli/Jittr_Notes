package com.upsoul.jittrnotes.view.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.upsoul.jittrnotes.databinding.FragmentTasksBinding;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;


public class TasksFragment extends Fragment {
    private FragmentTasksBinding binding;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentTasksBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onDestroyView() {
        binding = null;
        super.onDestroyView();
    }
}