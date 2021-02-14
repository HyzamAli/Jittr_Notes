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
import com.upsoul.jittrnotes.services.HideKeyboardService;
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


public class ViewNoteFragment extends Fragment implements HideKeyboardService {
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
                hideKeyboard(requireActivity());
                note.setTitle(binding.titleText.getText().toString().trim());
                note.setDescription(binding.descriptionText.getText().toString().trim());

                if (note.getTitle().isEmpty() && note.getDescription().isEmpty()) {
                    deleteNote();
                } else if (!note.equals(viewModel.getCurrentNote())) {
                    note.setId(viewModel.getCurrentNote().getId());
                    note.setTime_stamp(System.currentTimeMillis());
                    note.setDate(new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(Calendar.getInstance().getTime()));
                    viewModel.updateNote(note).observe(getViewLifecycleOwner(), response -> {
                        if (response.getStatus() != STATUS.SUCCESS) {
                            Toast.makeText(requireContext(),"Failed To Update", Toast.LENGTH_LONG).show();
                        }
                    });
                }
                NavHostFragment.findNavController(getParentFragment()).popBackStack();
            }
        });
    }

    private void deleteNote() {
        viewModel.deleteNote(viewModel.getCurrentNote()).observe(getViewLifecycleOwner(), response -> {
            if (response.getStatus() == STATUS.FAIL) {
                Toast.makeText(requireContext(),"Failed To Delete", Toast.LENGTH_LONG).show();
            } else {
                viewModel.setCurrentNote(null);
            }
        });
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        viewModel = new ViewModelProvider(requireActivity()).get(NotesViewModel.class);
        note = new Note(viewModel.getCurrentNote().getColor());
        note.setPriority(viewModel.getCurrentNote().getPriority());
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
        binding.setNote(viewModel.getCurrentNote());
    }

    @SuppressWarnings("ConstantConditions")
    private void setColors(int color) {
        binding.getRoot().setBackgroundColor(color);
        binding.toolBar.getNavigationIcon().setTint(color);

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