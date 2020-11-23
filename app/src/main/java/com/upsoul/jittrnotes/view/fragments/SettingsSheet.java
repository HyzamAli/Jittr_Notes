package com.upsoul.jittrnotes.view.fragments;

import android.app.Dialog;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.upsoul.jittrnotes.R;
import com.upsoul.jittrnotes.databinding.SheetSettingsBinding;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.navigation.fragment.NavHostFragment;

public class SettingsSheet extends BottomSheetDialogFragment implements View.OnClickListener {
    private SheetSettingsBinding binding;

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
            getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = SheetSettingsBinding.inflate(getLayoutInflater(), container, false);
        binding.btnPin.setOnClickListener(this);
        binding.btnGithub.setOnClickListener(this);
        binding.btnRate.setOnClickListener(this);
        return binding.getRoot();
    }

    @Override
    public void onDestroyView() {
        binding = null;
        super.onDestroyView();
    }

    @Override
    public void onClick(View view) {
        if (view == binding.btnPin) {
            NavHostFragment.findNavController(this).navigate(R.id.action_toPin);
        } else if (view == binding.btnGithub) {
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setData(Uri.parse("https://github.com/HyzamAli/Jittr_Notes"));
            startActivity(intent);
        } else if (view == binding.btnRate) {
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=com.upsoul.jittrnotes"));
            try {
                startActivity(intent);
            } catch (ActivityNotFoundException e) {
                Intent httpIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details"));
                startActivity(httpIntent);
            }
        }
    }
}
