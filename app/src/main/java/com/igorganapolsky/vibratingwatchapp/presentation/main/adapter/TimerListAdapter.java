package com.igorganapolsky.vibratingwatchapp.presentation.main.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import com.igorganapolsky.vibratingwatchapp.R;
import com.igorganapolsky.vibratingwatchapp.domain.model.TimerModel;
import com.igorganapolsky.vibratingwatchapp.util.TimerTransform;

import java.util.List;
import java.util.Locale;

public class TimerListAdapter extends RecyclerView.Adapter<TimerListAdapter.TimerItemViewHolder> {

    private List<TimerModel> data;
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
        TimerModel timerModel = data.get(index);
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

    public void setData(List<TimerModel> data) {
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
        private TimerModel model;

        private ImageView ivStatus;
        private TextView tvTime;
        private TextView tvVibration;
        private TextView tvRepeat;

        TimerItemViewHolder(@NonNull View itemView) {
            super(itemView);
            ivStatus = itemView.findViewById(R.id.ivStatus);
            tvTime = itemView.findViewById(R.id.tvTime);
            tvVibration = itemView.findViewById(R.id.tvVibration);
            tvRepeat = itemView.findViewById(R.id.tvRepeat);
        }

        public TimerModel getModel() {
            return model;
        }

        void bind(TimerModel value) {
            this.model = value;
            int vibration = model.getBuzz();
            boolean isNotActive = value.getState() == TimerModel.State.FINISH;

            ivStatus.setSelected(!isNotActive);
            tvTime.setText(TimerTransform.millisToString(model));

            tvVibration.setText(String.format(Locale.ENGLISH, "%d", vibration));
            tvRepeat.setText(String.format(Locale.ENGLISH, "%d", model.getRepeat()));
        }
    }
}

