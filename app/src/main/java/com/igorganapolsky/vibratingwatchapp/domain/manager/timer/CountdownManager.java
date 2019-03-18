package com.igorganapolsky.vibratingwatchapp.domain.manager.timer;

import android.arch.lifecycle.LiveData;
import com.igorganapolsky.vibratingwatchapp.domain.model.TimerModel;

/**
 * Base countdown manager , witch provides all data about current {@link TimerModel}
 */
public interface CountdownManager {

    /**
     * Prepares {@link TimerModel} before launch. It's data will use to calculate
     * time to display and laps to replay.
     *
     * @param model user's selected timer;
     */
    void setupTimer(TimerModel model);

    /**
     * Starts previously prepared {@link TimerModel} and changes state to @{@link TimerModel.State#RUN}.
     * Under the hood, every invocation creates new instance of countdown timer.
     *
     * @see android.os.CountDownTimer
     */
    void onStart();

    /**
     * Cancels current active {@link TimerModel} and changes state to @{@link TimerModel.State#PAUSE}.
     * After {@link #onStart()} will invoke, timers continue from place, where it was paused.
     *
     * @see android.os.CountDownTimer
     */
    void onPause();

    /**
     * Cancels current active {@link TimerModel} and changes state to @{@link TimerModel.State#FINISH}.
     * All inner state are clearing (e.g repeat's count).
     *
     * @see android.os.CountDownTimer
     */
    void onStop();

    /**
     * Start previously prepared {@link TimerModel} and changes state to @{@link TimerModel.State#RUN}.
     * This method is same to {@link #onStart()}, but before invocation, it resets time left.
     */
    void onRestart();

    /**
     * Tries to start active {@link TimerModel} again.
     *
     * @return true, if timer has more laps to repeat, otherwise false;
     */
    boolean onNextLap();


    /**
     * Gets possibility to observe active {@link TimerModel}. It needs, particularly,
     * to observe timer's state outside {@link com.igorganapolsky.vibratingwatchapp.presentation.details.TimerDetailsActivity}
     * and store it state to database.
     *
     * @return live data with timer "on board";
     */
    LiveData<TimerModel> observeActiveModel();

    /**
     * Add listener to be aware of time changes.
     *
     * @param listener to be notify in changes;
     * @see TimerModel
     */
    void setTickListener(TickListener listener);

    /**
     * Gets current active {@link TimerModel}.
     *
     * @return active timer model or null, if it wasn't setup yet;
     */
    TimerModel getActive();

    /**
     * Gets current active timer's id.
     *
     * @return int value of id or {@link TimerModel#UNDEFINE_ID}, if timer wasn't setup yet;
     */
    int getActiveId();

    /**
     * Gives insight about timer's active state.
     *
     * @return true , if time's current state is {@link TimerModel.State#RUN} or {@link TimerModel.State#PAUSE}
     */
    boolean isActive();

    /**
     * Gets current active timer time left, before it stops.
     *
     * @return time value in millis;
     * @see TimerModel
     */
    long getActiveTimeLeft();

    /**
     * Gets current active timer progress within one repeat circle.
     *
     * @return progress value in range from 100 to 1;
     * @see TimerModel
     */
    int getActiveProgress();
}
