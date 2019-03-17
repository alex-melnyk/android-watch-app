package com.igorganapolsky.vibratingwatchapp.presentation.settings.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.igorganapolsky.vibratingwatchapp.R;
import com.igorganapolsky.vibratingwatchapp.domain.model.RepeatSetup;

import java.util.ArrayList;
import java.util.List;

public class RepeatsAdapter extends RecyclerView.Adapter<RepeatsAdapter.RepeatsRecyclerViewHolder> {

    private final List<RepeatSetup> repeats;

    private HolderClickListener holderClickListener;

    public RepeatsAdapter(HolderClickListener holderClickListener) {
        this.holderClickListener = holderClickListener;
        this.repeats = initRepeatSetup();
        setHasStableIds(true);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @NonNull
    @Override
    public RepeatsRecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.set_timer_repeats_item, viewGroup, false);
        return new RepeatsRecyclerViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull RepeatsRecyclerViewHolder repeatsRecyclerViewHolder, int index) {
        repeatsRecyclerViewHolder.bind(repeats.get(index), holderClickListener);
    }

    @Override
    public int getItemCount() {
        return repeats.size();
    }

    /**
     * Initial method for defining all {@link RepeatSetup}
     *
     * @return setup list;
     */
    private List<RepeatSetup> initRepeatSetup() {
        List<RepeatSetup> setupList = new ArrayList<>(9);
        for (int i = 0; i < 9; i++) {
            setupList.add(i, new RepeatSetup(i + 1));
        }
        return setupList;
    }

    public void selectRepeat(RepeatSetup setup) {
        int selectedIndex = repeats.indexOf(setup);
        if (selectedIndex == -1) {
            selectedIndex = 0;
        }
        repeats.get(selectedIndex).setSelected(true);
        notifyItemChanged(selectedIndex);
    }

    static class RepeatsRecyclerViewHolder extends RecyclerView.ViewHolder {
        private TextView tvLabel;

        RepeatsRecyclerViewHolder(@NonNull View itemView) {
            super(itemView);
            tvLabel = itemView.findViewById(R.id.label);
        }

        void bind(RepeatSetup setup, HolderClickListener holderClickListener) {
            tvLabel.setText(String.valueOf(setup.getCount()));
            itemView.setSelected(setup.isSelected());

            if (holderClickListener != null) {
                itemView.setOnClickListener(view -> {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION) {
                        holderClickListener.onHolderClick(position);
                    }
                });
            }
        }
    }
}

