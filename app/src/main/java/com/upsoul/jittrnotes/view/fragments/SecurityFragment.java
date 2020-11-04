package com.upsoul.jittrnotes.view.fragments;

import android.app.Activity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.CompoundButton;

import com.google.android.material.snackbar.Snackbar;
import com.upsoul.jittrnotes.databinding.FragmentSecurityBinding;
import com.upsoul.jittrnotes.viewmodel.NotesViewModel;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

public class SecurityFragment extends Fragment implements CompoundButton.OnCheckedChangeListener {
    private FragmentSecurityBinding binding;
    private NotesViewModel viewModel;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding= FragmentSecurityBinding.inflate(getLayoutInflater(),container, false);
        viewModel = new ViewModelProvider(requireActivity()).get(NotesViewModel.class);
        return binding.getRoot();
    }

    @SuppressWarnings("ConstantConditions")
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        binding.toolBar.setNavigationOnClickListener(view1 ->requireActivity().onBackPressed());
        setupPage(viewModel.getPinFromSP());

        binding.switchEnabled.setOnCheckedChangeListener(this);
        binding.btnDone.setOnClickListener(view1 -> {
            String passwordText = binding.passwordBox.getEditText().getText().toString().trim();
            String confirmPasswordText = binding.confirmPasswordBox.getEditText().getText().toString().trim();
            validateAndSetPin(passwordText, confirmPasswordText);
        });

        binding.confirmPasswordBox.getEditText().setOnFocusChangeListener((view12, b) -> {
            if (b) {
                binding.confirmPasswordBox.setHelperTextEnabled(false);
            }
        });

        binding.passwordBox.getEditText().setOnFocusChangeListener((view12, b) -> {
            if (b) {
                binding.passwordBox.setHelperTextEnabled(false);
            }
        });
    }

    @SuppressWarnings("ConstantConditions")
    private void setupPage(String pin) {
        if (pin == null) {
            binding.switchEnabled.setChecked(false);
            binding.passwordBox.setEnabled(false);
            binding.confirmPasswordBox.setEnabled(false);
        } else {
            binding.switchEnabled.setChecked(true);
            binding.passwordBox.getEditText().setText(pin);
            binding.confirmPasswordBox.getEditText().setText(pin);
        }
    }

    private void validateAndSetPin(String password, String confirmPassword) {
        if (password.isEmpty()) {
            binding.passwordBox.setHelperTextEnabled(true);
            binding.passwordBox.setHelperText("Please enter a valid 6 digit PIN");
            return;
        }

        if (password.length() != 6) {
            binding.passwordBox.setHelperTextEnabled(true);
            binding.passwordBox.setHelperText("PIN needs to be 6 digit");
            return;
        }

        if (!TextUtils.isDigitsOnly(password)) {
            binding.passwordBox.setHelperTextEnabled(true);
            binding.passwordBox.setHelperText("Only numbers allowed");
            return;
        }

        if (!password.equals(confirmPassword)) {
            binding.confirmPasswordBox.setHelperTextEnabled(true);
            binding.confirmPasswordBox.setHelperText("PIN entered doesn't match");
            return;
        }

        hideKeyboard(requireActivity());
        if (viewModel.setPinToSP(password)) {
            Snackbar.make(binding.getRoot(),"PIN Successfully set", Snackbar.LENGTH_SHORT).show();
        } else {
            Snackbar.make(binding.getRoot(),"PIN setup failed", Snackbar.LENGTH_SHORT).show();
        }
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

    @Override
    public void onDestroyView() {
        binding = null;
        super.onDestroyView();
    }

    @SuppressWarnings("ConstantConditions")
    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
        if (!b) {
            viewModel.removePIN();
            binding.passwordBox.getEditText().setText("");
            binding.confirmPasswordBox.getEditText().setText("");
            binding.passwordBox.setEnabled(false);
            binding.confirmPasswordBox.setEnabled(false);
        } else {
            binding.passwordBox.setEnabled(true);
            binding.confirmPasswordBox.setEnabled(true);
        }
    }
}