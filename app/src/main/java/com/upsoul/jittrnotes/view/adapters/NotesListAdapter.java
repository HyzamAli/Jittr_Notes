package com.upsoul.jittrnotes.view.adapters;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.upsoul.jittrnotes.R;
import com.upsoul.jittrnotes.data.models.Note;
import com.upsoul.jittrnotes.databinding.RowNotesBinding;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class NotesListAdapter extends RecyclerView.Adapter<NotesListAdapter.ViewHolder> {
    private List<Note> notes;

    public NotesListAdapter(List<Note> notes) {
        this.notes = notes;
    }

    @NonNull
    @Override
    public NotesListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(RowNotesBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull NotesListAdapter.ViewHolder holder, int position) {
        holder.binding.setNote(notes.get(position));
        int[] colorsList = holder.binding.getRoot().getContext().getResources().getIntArray(R.array.colors_list);
        holder.binding.card.setCardBackgroundColor(colorsList[notes.get(position).getColor()]);
    }

    @Override
    public int getItemCount() {
        return notes == null? 0 : notes.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private RowNotesBinding binding;
        public ViewHolder(@NonNull RowNotesBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
