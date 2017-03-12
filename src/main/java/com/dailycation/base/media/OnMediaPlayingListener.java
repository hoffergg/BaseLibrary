package com.dailycation.base.media;

/**
 * Created by hoffer on 17/3/12.
 */

public interface OnMediaPlayingListener {
    /**
     * duration for the media file
     * @param duration milliseconds
     */
    void onStart(int duration);

    void onComplete();

    /**
     * media play progress
     * @param currentPosition milliseconds
     */
    void onProgress(int currentPosition);
}
