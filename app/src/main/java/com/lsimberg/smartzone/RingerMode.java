package com.lsimberg.smartzone;

import android.content.Context;
import android.media.AudioManager;

/**
 * Created by leo on 4/21/14.
 */
public class RingerMode {
    public static final int RINGER_MODE_SILENT = 0;
    public static final int RINGER_MODE_VIBRATE = 1;
    public static final int RINGER_MODE_NORMAL = 2;

    public static void setRinger2Silent(Context ctx) {
        AudioManager audioManager = (AudioManager) ctx.getSystemService(Context.AUDIO_SERVICE);
        audioManager.setRingerMode(AudioManager.RINGER_MODE_SILENT);
    }

    public static void setRinger2Vibrate(Context ctx) {
        AudioManager audioManager = (AudioManager) ctx.getSystemService(Context.AUDIO_SERVICE);
        audioManager.setRingerMode(AudioManager.RINGER_MODE_VIBRATE);
    }

    public static void setRinger2Normal(Context ctx) {
        AudioManager audioManager = (AudioManager) ctx.getSystemService(Context.AUDIO_SERVICE);
        audioManager.setRingerMode(AudioManager.RINGER_MODE_NORMAL);
    }

    public static boolean isRingerSilent(Context ctx) {
        AudioManager audioManager =
                (AudioManager) ctx.getSystemService(Context.AUDIO_SERVICE);
        return (audioManager.getRingerMode() == AudioManager.RINGER_MODE_SILENT) ? true:false;
    }

    public static boolean isRingerVibrate(Context ctx) {
        AudioManager audioManager =
                (AudioManager) ctx.getSystemService(Context.AUDIO_SERVICE);
        return (audioManager.getRingerMode() == AudioManager.RINGER_MODE_VIBRATE) ? true:false;
    }

    public static boolean isRingerNormal(Context ctx) {
        AudioManager audioManager =
                (AudioManager) ctx.getSystemService(Context.AUDIO_SERVICE);
        return (audioManager.getRingerMode() == AudioManager.RINGER_MODE_NORMAL) ? true:false;
    }

    public static int getRingerMode(Context ctx) {
        AudioManager audioManager =
                (AudioManager) ctx.getSystemService(Context.AUDIO_SERVICE);
        return audioManager.getRingerMode();
    }

    public static void setRingerMode(Context ctx, int ringerMode) {
        assert(ringerMode >= 0 && ringerMode <3);
        AudioManager audioManager =
                (AudioManager) ctx.getSystemService(Context.AUDIO_SERVICE);
        audioManager.setRingerMode(ringerMode);
    }
}
