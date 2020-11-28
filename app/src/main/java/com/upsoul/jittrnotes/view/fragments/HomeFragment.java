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
        binding.btnSettings.setOnClickListener(view1 -> NavHostFragment.findNavController(this).navigate(R.id.action_toSettings));
    }

    @Override
    public void onResume() {
        super.onResume();
        setGreetings();
    }

    private void setGreetings() {
        String greetingsText = viewModel.greetingsText();
        binding.greetingsText.setText(greetingsText);
        switch (greetingsText) {
            case "Good\nMorning":
                binding.greetingsIcon.setBackgroundResource(R.drawable.morning);
                break;
            case "Good\nAfternoon":
                binding.greetingsIcon.setBackgroundResource(R.drawable.noon);
                break;
            case "Good\nEvening":
                binding.greetingsIcon.setBackgroundResource(R.drawable.evening);
                break;
            default:
                binding.greetingsIcon.setBackgroundResource(R.drawable.moon);
                break;
        }
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

                if (response.getData().size() == 0) {
                    binding.allNotesTitle.setVisibility(View.GONE);
                    binding.starredTitle.setVisibility(View.GONE);
                    binding.emptyNotesFiller.setVisibility(View.VISIBLE);
                    return;
                }

                binding.allNotesList.setAdapter(new NotesListAdapter(response.getData(), position -> {
                    viewModel.setCurrentNote(response.getData().get(position));
                    NavHostFragment.findNavController(this).navigate(R.id.action_toViewNote);
                }));

                if (starredNotes.size() == 0) {
                    binding.starredTitle.setVisibility(View.GONE);
                } else {
                    binding.starredNotesList.setAdapter(new NotesListAdapter(starredNotes, position -> {
                        viewModel.setCurrentNote(starredNotes.get(position));
                        NavHostFragment.findNavController(this).navigate(R.id.action_toViewNote);
                    }));
                }


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