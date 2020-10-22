package com.upsoul.jittrnotes.view.fragments;

import android.app.Activity;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import com.upsoul.jittrnotes.R;
import com.upsoul.jittrnotes.data.models.Note;
import com.upsoul.jittrnotes.data.models.STATUS;
import com.upsoul.jittrnotes.databinding.FragmentNewNoteBinding;
import com.upsoul.jittrnotes.viewmodel.NotesViewModel;

import java.util.Random;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;

public class NewNoteFragment extends Fragment {
    private FragmentNewNoteBinding binding;
    private NotesViewModel viewModel;
    private Note note;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        viewModel = new ViewModelProvider(requireActivity()).get(NotesViewModel.class);
        binding = FragmentNewNoteBinding.inflate(getLayoutInflater(), container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding.toolBar.inflateMenu(R.menu.menu_add_note);
        note = new Note(generateColorIndex());
        binding.setNote(note);

        binding.toolBar.setNavigationOnClickListener(view1 -> NavHostFragment.findNavController(this).popBackStack());
        binding.toolBar.setOnMenuItemClickListener(this::onOptionsItemSelected);
        binding.btnAddNote.setOnClickListener(view1 -> {
            hideKeyboard(requireActivity());
            addNote();
        });
    }

    @SuppressWarnings("ConstantConditions")
    private boolean validNote() {
        // if title or description is empty return false else return true
        if (binding.titleText.getText().toString().trim().isEmpty() && binding.descriptionText.getText().toString().trim().isEmpty()) {
            return false;
        }
        note.setTitle(binding.titleText.getText().toString().trim());
        note.setDescription(binding.descriptionText.getText().toString().trim());
        return true;
    }

    private void addNote() {
        if (!validNote()) return;
        viewModel.insertNewNote(note).observe(getViewLifecycleOwner(), response -> {
            if (response.getStatus() == STATUS.SUCCESS) Toast.makeText(requireActivity(), "Successful", Toast.LENGTH_SHORT).show();
            if (response.getStatus() == STATUS.FAIL) Toast.makeText(requireActivity(), "Failed", Toast.LENGTH_SHORT).show();
        });
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

        MenuItem favoriteItem = binding.toolBar.getMenu().findItem(R.id.menu_refresh_color);
        Drawable newIcon = favoriteItem.getIcon();
        newIcon.mutate().setColorFilter(color, PorterDuff.Mode.SRC_ATOP);
        favoriteItem.setIcon(newIcon);
    }


    @Override
    public void onDestroyView() {
        binding = null;
        super.onDestroyView();
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.menu_favourite) {
            if (note.getPriority() == 0) {
                note.setPriority(1);
                item.setIcon(R.drawable.ic_star_fill);
            } else {
                note.setPriority(0);
                item.setIcon(R.drawable.ic_star);
            }
            return true;
        } else if (item.getItemId() == R.id.menu_refresh_color) {
            note.setColor(generateColorIndex());
        }
        return super.onOptionsItemSelected(item);
    }
}