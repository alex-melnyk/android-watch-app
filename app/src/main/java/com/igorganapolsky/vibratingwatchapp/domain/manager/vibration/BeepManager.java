package com.igorganapolsky.vibratingwatchapp.domain.manager.vibration;

import com.igorganapolsky.vibratingwatchapp.domain.model.BuzzSetup;

/**
 * Base vibration manager, that uses {@link BuzzSetup} from timer to
 * prepare and run vibration;
 * Note, that this manager hasn't any mechanism to notify API consumers about
 * situation, when current devise hasn't vibrator "on board";
 */
public interface BeepManager {

    /**
     * Fetches and prepares vibrator;
     *
     * @param setup from {@link com.igorganapolsky.vibratingwatchapp.domain.model.TimerModel}
     */
    void setup(BuzzSetup setup);

    /**
     * Start vibration  that was prepared previously;
     */
    void start();

    /**
     * Cancels current vibrator.
     */
    void cancel();
}
