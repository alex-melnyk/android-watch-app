package com.igorganapolsky.vibratingwatchapp.ui.adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.igorganapolsky.vibratingwatchapp.R;

import java.util.Locale;

public class VibrationsAdapter extends RecyclerView.Adapter<VibrationsAdapter.VibrationsRecyclerViewHolder> {
    private String[] vibrations = {
            "1 buzz,5 seconds",
            "3 buzz,3 seconds each",
            "5 buzzes,5 seconds each",
            "1 long buzz,20 seconds"
    };

    public VibrationsAdapter() {
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

        String[] spittedLine = vibrations[index].split(",");

        vibrationsRecyclerViewHolder.setBuzzAmount(spittedLine[0]);
        vibrationsRecyclerViewHolder.setTimeAmount(spittedLine[1]);
    }

    @Override
    public int getItemCount() {
        return vibrations.length;
    }

    static class VibrationsRecyclerViewHolder extends RecyclerView.ViewHolder {
        private TextView tvIndex;
        private TextView buzzAmount;
        private TextView timeAmount;

        VibrationsRecyclerViewHolder(@NonNull View itemView) {
            super(itemView);

            tvIndex = itemView.findViewById(R.id.index);
            buzzAmount = itemView.findViewById(R.id.buzzAmount);
            timeAmount = itemView.findViewById(R.id.timeAmount);
        }

        void setIndex(int index) {
            tvIndex.setText(String.format(Locale.ENGLISH, "%d", index));
        }

        void setBuzzAmount(String label) {
            buzzAmount.setText(label);
        }

        void setTimeAmount(String label) {
            timeAmount.setText(label);
        }
    }
}

