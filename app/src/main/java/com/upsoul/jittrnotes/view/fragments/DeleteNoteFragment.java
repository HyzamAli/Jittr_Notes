package com.upsoul.jittrnotes.view.fragments;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Toast;

import com.upsoul.jittrnotes.R;
import com.upsoul.jittrnotes.data.models.STATUS;
import com.upsoul.jittrnotes.databinding.DialogDeleteNoteBinding;
import com.upsoul.jittrnotes.viewmodel.NotesViewModel;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;

public class DeleteNoteFragment extends DialogFragment {
    private DialogDeleteNoteBinding binding;
    private NotesViewModel viewModel;
    private int color;

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        Dialog dialog =  super.onCreateDialog(savedInstanceState);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        return dialog;
    }

    @SuppressWarnings("ConstantConditions")
    @Override
    public void onStart() {
        super.onStart();
        Dialog dialog = getDialog();
        if (dialog != null) {
            dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            color = DeleteNoteFragmentArgs.fromBundle(getArguments()).getColor();
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DialogDeleteNoteBinding.inflate(getLayoutInflater(), container, false);
        viewModel = new ViewModelProvider(requireActivity()).get(NotesViewModel.class);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        setColors(getResources().getIntArray(R.array.colors_list)[color]);
        binding.btnNo.setOnClickListener(view1 -> this.dismiss());
        binding.btnYes.setOnClickListener(view1 -> viewModel.deleteNote(viewModel.getCurrentNote()).observe(getViewLifecycleOwner(),
                response -> {
                        if (response.getStatus() == STATUS.SUCCESS) {
                            viewModel.setCurrentNote(null);
                            NavHostFragment.findNavController(this).popBackStack(R.id.homeFragment, false);
                        } else {
                            this.dismiss();
                            Toast.makeText(requireContext(), "Failed to delete", Toast.LENGTH_SHORT).show();
                        }
        }));
    }

    private void setColors(int color) {
        binding.titleText.setTextColor(color);
        binding.btnYes.setTextColor(color);
        binding.btnNo.setTextColor(color);

    }

    @Override
    public void onDestroyView() {
        binding = null;
        super.onDestroyView();
    }
}
