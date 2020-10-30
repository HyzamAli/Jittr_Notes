package com.upsoul.jittrnotes.view.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.upsoul.jittrnotes.databinding.FragmentSecurityBinding;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

public class SecurityFragment extends Fragment {
    private FragmentSecurityBinding binding;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding= FragmentSecurityBinding.inflate(getLayoutInflater(),container, false);
        return binding.getRoot();
    }
}