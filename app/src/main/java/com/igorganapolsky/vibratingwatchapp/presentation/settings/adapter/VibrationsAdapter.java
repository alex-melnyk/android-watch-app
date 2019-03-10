package com.igorganapolsky.vibratingwatchapp.presentation.settings.adapter;

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

    private HolderClickListener holderClickListener;

    public VibrationsAdapter(HolderClickListener holderClickListener) {
        this.holderClickListener = holderClickListener;
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
        vibrationsRecyclerViewHolder.bind(index + 1, holderClickListener);

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

        void bind(int index, HolderClickListener holderClickListener) {
            tvIndex.setText(String.format(Locale.ENGLISH, "%d", index));

            if (holderClickListener != null) {
                itemView.setOnClickListener(view -> {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION) {
                        holderClickListener.onHolderClick(position);
                    }
                });
            }
        }

        void setBuzzAmount(String label) {
            buzzAmount.setText(label);
        }

        void setTimeAmount(String label) {
            timeAmount.setText(label);
        }
    }
}

