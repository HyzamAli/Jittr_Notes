package com.upsoul.jittrnotes.view.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.upsoul.jittrnotes.data.models.Note;
import com.upsoul.jittrnotes.databinding.FragmentViewNoteBinding;
import com.upsoul.jittrnotes.viewmodel.NotesViewModel;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;


public class ViewNoteFragment extends Fragment {
    private FragmentViewNoteBinding binding;
    private NotesViewModel viewModel;
    private Note note;


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        viewModel = new ViewModelProvider(requireActivity()).get(NotesViewModel.class);
        note = viewModel.getCurrentNote();
        binding = FragmentViewNoteBinding.inflate(getLayoutInflater(), container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}