package com.igorganapolsky.vibratingwatchapp.ui.settimer;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.igorganapolsky.vibratingwatchapp.R;

import java.util.Locale;

public class VibrationsRecyclerViewAdapter extends RecyclerView.Adapter<VibrationsRecyclerViewAdapter.VibrationsRecyclerViewHolder> {
    private String[] vibrations = {
            "1 buzz - 5 seconds",
            "3 buzz - 3 seconds each",
            "5 buzzes - 5 seconds each",
            "1 long buzz - 20 seconds"
    };

    public VibrationsRecyclerViewAdapter() {
        setHasStableIds(true);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @NonNull
    @Override
    public VibrationsRecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.set_timer_vibrations_line, viewGroup, false);

        return new VibrationsRecyclerViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull VibrationsRecyclerViewHolder vibrationsRecyclerViewHolder, int index) {
        vibrationsRecyclerViewHolder.setIndex(index + 1);
        vibrationsRecyclerViewHolder.setLabel(vibrations[index]);
    }

    @Override
    public int getItemCount() {
        return vibrations.length;
    }

    static class VibrationsRecyclerViewHolder extends RecyclerView.ViewHolder {
        private TextView tvIndex;
        private TextView tvLabel;

        VibrationsRecyclerViewHolder(@NonNull View itemView) {
            super(itemView);

            tvIndex = itemView.findViewById(R.id.index);
            tvLabel = itemView.findViewById(R.id.label);
        }

        void setIndex(int index) {
            tvIndex.setText(String.format(Locale.ENGLISH, "%d", index));
        }

        void setLabel(String label) {
            tvLabel.setText(label);
        }
    }
}

