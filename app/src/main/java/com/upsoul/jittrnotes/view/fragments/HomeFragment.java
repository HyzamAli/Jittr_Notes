package com.upsoul.jittrnotes.view.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.upsoul.jittrnotes.R;
import com.upsoul.jittrnotes.data.models.Note;
import com.upsoul.jittrnotes.data.models.STATUS;
import com.upsoul.jittrnotes.databinding.FragmentHomeBinding;
import com.upsoul.jittrnotes.view.adapters.NotesListAdapter;
import com.upsoul.jittrnotes.viewmodel.NotesViewModel;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;


public class HomeFragment extends Fragment {
    private FragmentHomeBinding binding;
    private NotesViewModel viewModel;


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentHomeBinding.inflate(getLayoutInflater(), container, false);
        viewModel = new ViewModelProvider(requireActivity()).get(NotesViewModel.class);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getAllNotes();
        binding.btnNew.setOnClickListener(view1 ->  NavHostFragment.findNavController(this).navigate(R.id.action_toNewNote));
    }

    private void getAllNotes() {
        viewModel.getAllNotes().observe(getViewLifecycleOwner(), response -> {
            if (response.getStatus() == STATUS.SUCCESS) {
                List<Note> starredNotes = new ArrayList<>();
                for (Note note: response.getData()) {
                    if (note.getPriority() == 1) {
                        starredNotes.add(note);
                    }
                }

                //TODO: view when no starred notes are found
                binding.allNotesList.setAdapter(new NotesListAdapter(response.getData()));
                binding.starredNotesList.setAdapter(new NotesListAdapter(starredNotes));


            } else {
                Toast.makeText(requireActivity(), "Failure", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onDestroyView() {
        binding = null;
        super.onDestroyView();
    }
}