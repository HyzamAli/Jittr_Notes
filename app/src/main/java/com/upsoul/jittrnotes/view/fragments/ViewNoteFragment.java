package com.upsoul.jittrnotes.view.fragments;

import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.upsoul.jittrnotes.R;
import com.upsoul.jittrnotes.data.models.Note;
import com.upsoul.jittrnotes.data.models.STATUS;
import com.upsoul.jittrnotes.databinding.FragmentViewNoteBinding;
import com.upsoul.jittrnotes.viewmodel.NotesViewModel;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;


public class ViewNoteFragment extends Fragment {
    private FragmentViewNoteBinding binding;
    private NotesViewModel viewModel;
    private Note note;

    @Override
    @SuppressWarnings("ConstantConditions")
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requireActivity().getOnBackPressedDispatcher().addCallback(new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                String newTitle = binding.titleText.getText().toString().trim();
                String newDescription = binding.descriptionText.getText().toString().trim();
                if (isUpdated(newTitle, newDescription)) {
                    note.setTitle(newTitle);
                    note.setDescription(newDescription);
                    note.setTime_stamp(System.currentTimeMillis());
                    note.setDate(new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(Calendar.getInstance().getTime()));
                    viewModel.updateNote(note);
                }
                NavHostFragment.findNavController(getParentFragment()).popBackStack();
            }
        });
    }

    //TODO: add logic to check if title and description is empty
    @SuppressWarnings("RedundantIfStatement")
    private boolean isUpdated(String newTitle, String newDescription) {

        if (newTitle.isEmpty() && newDescription.isEmpty()) {
            deleteNote();
            return false;
        }
        if (newTitle.equals(note.getTitle()) && newDescription.equals(note.getDescription())) {
            return false;
        }
        return true;
    }

    private void deleteNote() {
        viewModel.deleteNote(note).observe(getViewLifecycleOwner(), response -> {
            if (response.getStatus() == STATUS.FAIL) {
                Toast.makeText(requireContext(),"Failed To Delete", Toast.LENGTH_LONG).show();
            }
        });
    }


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
        binding.toolBar.setNavigationOnClickListener(view1 -> requireActivity().onBackPressed());
        binding.toolBar.inflateMenu(R.menu.menu_add_note);
        setupMenu(binding.toolBar.getMenu());
        binding.toolBar.setOnMenuItemClickListener(this::onOptionsItemSelected);
        setColors(getResources().getIntArray(R.array.colors_list)[note.getColor()]);
        binding.setNote(note);
    }

    @SuppressWarnings("ConstantConditions")
    private void setColors(int color) {
        binding.getRoot().setBackgroundColor(color);
        binding.toolBar.getNavigationIcon().setTint(color);

        //TODO: undo commenting when adding refresh button
        MenuItem favoriteItem = binding.toolBar.getMenu().findItem(R.id.menu_delete);
        Drawable newIcon = favoriteItem.getIcon();
        newIcon.mutate().setColorFilter(color, PorterDuff.Mode.SRC_ATOP);
        favoriteItem.setIcon(newIcon);
    }

    private void setupMenu(Menu menu){
        menu.findItem(R.id.menu_refresh_color).setVisible(false);
        if (note.getPriority() == 1) {
            menu.findItem(R.id.menu_favourite).setIcon(R.drawable.ic_star_fill);
        }
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
        } else if (item.getItemId() == R.id.menu_delete) {
            ViewNoteFragmentDirections.ActionToDialog action = ViewNoteFragmentDirections.actionToDialog().setColor(note.getColor());
            NavHostFragment.findNavController(this).navigate(action);
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}