package com.igorganapolsky.vibratingwatchapp.ui;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.LinearSnapHelper;
import android.support.v7.widget.RecyclerView;
import android.view.View;

public class RecyclerViewSnapLayoutManager extends LinearLayoutManager {
    private RecyclerView recyclerView;
    private LinearSnapHelper helper;
    private OnItemSelectListener itemSelectListener;

    public RecyclerViewSnapLayoutManager(Context context) {
        this(context, VERTICAL, false);
    }

    public RecyclerViewSnapLayoutManager(Context context, int orientation) {
        this(context, orientation, false);
    }

    public RecyclerViewSnapLayoutManager(Context context, int orientation, boolean reverseLayout) {
        super(context, orientation, reverseLayout);
    }

    @Override
    public void onAttachedToWindow(RecyclerView recyclerView) {
        super.onAttachedToWindow(recyclerView);

        this.recyclerView = recyclerView;

        helper = new LinearSnapHelper();
        helper.attachToRecyclerView(recyclerView);
    }

    @Override
    public void onScrollStateChanged(int state) {
        super.onScrollStateChanged(state);

        if (state == RecyclerView.SCROLL_STATE_IDLE) {
            recyclerView.dispatchSetSelected(false);

            View snapView = helper.findSnapView(this);

            if (snapView != null) {
                snapView.setSelected(true);

                if (itemSelectListener != null) {
                    itemSelectListener.onItemSelected(recyclerView.getChildAdapterPosition(snapView));
                }
            }
        }
    }

    public void setItemSelectListener(OnItemSelectListener itemSelectListener) {
        this.itemSelectListener = itemSelectListener;
    }

    public interface OnItemSelectListener {
        void onItemSelected(int position);
    }
}
