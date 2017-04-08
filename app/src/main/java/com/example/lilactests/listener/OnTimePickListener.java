package com.example.lilactests.listener;

/**
 * Time Pick call-back callback.
 */
public interface OnTimePickListener {
    void onTimePick(int hourOfDay, int minute);

    void onTimePickCancel();
}
