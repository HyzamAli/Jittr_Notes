package com.upsoul.jittrnotes.view.fragments;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;

import com.google.android.material.snackbar.Snackbar;
import com.upsoul.jittrnotes.R;
import com.upsoul.jittrnotes.databinding.FragmentPinBinding;
import com.upsoul.jittrnotes.services.HideKeyboardService;
import com.upsoul.jittrnotes.viewmodel.LauncherViewModel;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;


@SuppressWarnings("ConstantConditions")
public class PinFragment extends Fragment implements HideKeyboardService {
    private FragmentPinBinding binding;
    private LauncherViewModel viewModel;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        viewModel = new ViewModelProvider(requireActivity()).get(LauncherViewModel.class);
        if (viewModel.authenticate()) {
            NavHostFragment.findNavController(this).navigate(R.id.action_toHome);
            requireActivity().finish();
        }
        binding = FragmentPinBinding.inflate(getLayoutInflater(), container, false);
        return binding.getRoot();
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.pinBox.getEditText().setOnEditorActionListener((textView, actionId, event) -> {
            if (actionId == EditorInfo.IME_ACTION_SEARCH
                    || actionId == EditorInfo.IME_ACTION_DONE
                    || event.getAction() == KeyEvent.ACTION_DOWN
                    && event.getKeyCode() == KeyEvent.KEYCODE_ENTER) {
                binding.btnSubmit.performClick();
                return true;
            }
            return false;
        });
        binding.pinBox.getEditText().setOnFocusChangeListener((view1, b) -> {
            if (b) {
                binding.pinBox.setHelperTextEnabled(false);
            }
        });

        binding.btnSubmit.setOnClickListener(view1 -> {
            this.hideKeyboard(requireActivity());
            binding.pinBox.clearFocus();
            String pin = binding.pinBox.getEditText().getText().toString().trim();
            if (TextUtils.isEmpty(pin)) {
                binding.pinBox.setHelperTextEnabled(true);
                binding.pinBox.setHelperText("Empty Field");
                return;
            }
            if (viewModel.authenticate(pin)) {
                NavHostFragment.findNavController(this).navigate(R.id.action_toHome);
                requireActivity().finish();
            } else {
                Snackbar.make(binding.getRoot(),"Wrong PIN", Snackbar.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onDestroyView() {
        binding = null;
        super.onDestroyView();
    }
}