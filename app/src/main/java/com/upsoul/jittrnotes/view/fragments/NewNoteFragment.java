package com.upsoul.jittrnotes.view.fragments;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;

import com.upsoul.jittrnotes.R;
import com.upsoul.jittrnotes.data.models.Note;
import com.upsoul.jittrnotes.databinding.FragmentNewNoteBinding;

import java.util.Random;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

public class NewNoteFragment extends Fragment {
    FragmentNewNoteBinding binding;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentNewNoteBinding.inflate(getLayoutInflater(), container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Note note = new Note(generateColorIndex());
        binding.setNote(note);

        binding.toolBar.setNavigationOnClickListener(view1 -> NavHostFragment.findNavController(this).popBackStack());
        binding.btnAddNote.setOnClickListener(view1 -> hideKeyboard(requireActivity()));
    }

    private int generateColorIndex() {
        Random random = new Random();
        int colorIndex = random.nextInt(10);
        int[] colorsList = getResources().getIntArray(R.array.colors_list);
        setColors(colorsList[colorIndex]);
        return colorIndex;
    }

    public static void hideKeyboard(Activity activity) {
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        View view = activity.getCurrentFocus();
        if (view == null) {
            view = new View(activity);
        }
        //noinspection ConstantConditions
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    @SuppressWarnings("ConstantConditions")
    private void setColors(int color) {
        binding.getRoot().setBackgroundColor(color);
        binding.btnAddNote.setColorFilter(color);
        binding.toolBar.getNavigationIcon().setTint(color);
    }


    @Override
    public void onDestroyView() {
        binding = null;
        super.onDestroyView();
    }
}