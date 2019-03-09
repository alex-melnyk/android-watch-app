package com.igorganapolsky.vibratingwatchapp.presentation.main.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import com.igorganapolsky.vibratingwatchapp.R;
import com.igorganapolsky.vibratingwatchapp.domain.model.TimerValue;
import com.igorganapolsky.vibratingwatchapp.util.TimerTransform;

import java.util.List;
import java.util.Locale;

public class TimerListAdapter extends RecyclerView.Adapter<TimerListAdapter.TimerItemViewHolder> {

    private List<TimerValue> data;
    private OnItemClickListener itemClickListener;

    public TimerListAdapter() {
        setHasStableIds(true);
    }

    @Override
    public long getItemId(int position) {
        return data.get(position).getId();
    }

    @NonNull
    @Override
    public TimerItemViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int index) {
        return new TimerItemViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.timer_list_item, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull TimerItemViewHolder timerItemViewHolder, int index) {
        TimerValue timerModel = data.get(index);
        timerItemViewHolder.bind(timerModel);

        timerItemViewHolder.itemView.setOnClickListener((view) -> {
            if (itemClickListener != null) {
                itemClickListener.onItemClick(timerModel.getId());
            }
        });
    }

    @Override
    public int getItemCount() {
        return data == null ? 0 : data.size();
    }

    public void setData(List<TimerValue> data) {
        this.data = data;
        notifyDataSetChanged();
    }

    public void setItemClickListener(OnItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    public interface OnItemClickListener {
        void onItemClick(int id);
    }

    static class TimerItemViewHolder extends RecyclerView.ViewHolder {
        private TimerValue model;

        private ProgressBar pbProgress;
        private ImageView ivStatus;
        private TextView tvTime;
        private TextView tvVibration;
        private TextView tvRepeat;

        TimerItemViewHolder(@NonNull View itemView) {
            super(itemView);

            pbProgress = itemView.findViewById(R.id.pbProgress);
            pbProgress.setVisibility(View.GONE);

            ivStatus = itemView.findViewById(R.id.ivStatus);

            tvTime = itemView.findViewById(R.id.tvTime);
            tvVibration = itemView.findViewById(R.id.tvVibration);
            tvRepeat = itemView.findViewById(R.id.tvRepeat);
        }

        public TimerValue getModel() {
            return model;
        }

        void bind(TimerValue value) {
            this.model = value;
            int vibration = model.getBuzz() + 1;

            tvTime.setText(TimerTransform.millistToTimerModel(model));
            pbProgress.setProgress((int) (Math.random() * 100));
            tvVibration.setText(String.format(Locale.ENGLISH, "%d", vibration));
            tvRepeat.setText(String.format(Locale.ENGLISH, "%d", model.getRepeat() + 1));
        }
    }
}

