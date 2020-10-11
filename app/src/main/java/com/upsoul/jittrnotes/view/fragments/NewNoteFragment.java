package com.upsoul.jittrnotes.view.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.upsoul.jittrnotes.data.models.Note;
import com.upsoul.jittrnotes.databinding.FragmentNewNoteBinding;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class NewNoteFragment extends Fragment {
    FragmentNewNoteBinding binding;
    private Note note;
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentNewNoteBinding.inflate(getLayoutInflater(), container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //TODO: set random color and pass it as argument;
        note = new Note(1);
        binding.setNote(note);
    }

    @Override
    public void onDestroyView() {
        binding = null;
        super.onDestroyView();
    }
}