package com.igorganapolsky.vibratingwatchapp.ui.timerlist;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import com.igorganapolsky.vibratingwatchapp.R;
import com.igorganapolsky.vibratingwatchapp.model.TimerModel;

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
        return position;
    }

    @NonNull
    @Override
    public TimerItemViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int index) {
        View itemView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.timer_list_item, viewGroup, false);

        return new TimerItemViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull TimerItemViewHolder timerItemViewHolder, int index) {
        TimerModel timerModel = data.get(index);
        timerItemViewHolder.setModel(timerModel);

        timerItemViewHolder.itemView.setOnClickListener((view) -> {
            if (itemClickListener != null) {
                itemClickListener.onItemClick(timerItemViewHolder.getAdapterPosition());
            }
        });
    }

    @Override
    public int getItemCount() {
        if (data == null) {
            return 0;
        }

        return data.size();
    }

    public void setData(List<TimerModel> data) {
        this.data = data;

        notifyDataSetChanged();
    }

    public void setItemClickListener(OnItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    public static interface OnItemClickListener {
        void onItemClick(int position);
    }

    static class TimerItemViewHolder extends RecyclerView.ViewHolder {
        private TimerModel model;

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

        public TimerModel getModel() {
            return model;
        }

        public void setModel(TimerModel model) {
            this.model = model;

            tvTime.setText(String.format(Locale.ENGLISH, "%02d : %02d : %02d", model.getHours(), model.getMinutes(), model.getSeconds()));
            tvVibration.setText(String.format(Locale.ENGLISH, "%d", model.getBuzzMode()));
            tvRepeat.setText(String.format(Locale.ENGLISH, "%d", model.getRepeatMode()));

            pbProgress.setProgress((int) (Math.random() * 100));
        }
    }
}

