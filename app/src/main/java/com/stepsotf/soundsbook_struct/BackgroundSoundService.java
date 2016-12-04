package com.stepsotf.soundsbook_struct;


import android.content.Context;
import android.media.MediaPlayer;
import android.os.Handler;
import android.util.Log;

public class BackgroundSoundService
{
    static MediaPlayer mp;
    private static final String TAG = "MusicManager";
    static final int MUSIC_PREVIOUS = -1;

    private static int currentMusic = -1;
    private static int previousMusic = -1;


    public static void start(Context context, int music)
    {
        start(context, music, false);
    }

    public static void start(Context context, int music, boolean force) {
        if (!force && currentMusic > -1) {
// already playing some music and not forced to change
            return;
        }

        if (music == MUSIC_PREVIOUS) {
            Log.d(TAG, "Using previous music [" + previousMusic + "]");
            music = previousMusic;
        }
        if (currentMusic == music) {
// already playing this music
            return;
        }
        if (currentMusic != -1) {
            previousMusic = currentMusic;
            Log.d(TAG, "Previous music was [" + previousMusic + "]");
// playing some other music, pause it and change
            pause();
        }
        currentMusic = music;
        Log.d(TAG, "Current music is now [" + currentMusic + "]");
        if (mp != null) {
            if (!mp.isPlaying()) {
                mp.start();
                mp.setVolume(1.0f,1.0f);
            }
        }
        else
        {
            mp=MediaPlayer.create(context, R.raw.wordapp); //Ur BackGround Music
        }

        if (mp == null)
        {
            Log.e(TAG, "player was not created successfully");
        }
        else {
            try {
                mp.setLooping(true);
                mp.start();
            } catch (Exception e) {
                Log.e(TAG, e.getMessage(), e);
            }
        }
    }

    public static void pause() {
        if(mp!=null)
        {
            if (mp.isPlaying())
            {
                mp.pause();
            }
        }

// previousMusic should always be something valid
        if (currentMusic != -1)
        {{
            previousMusic = currentMusic;
            Log.d(TAG, "Previous music was [" + previousMusic + "]");
        }
            currentMusic = -1;
            Log.d(TAG, "Current music is now [" + currentMusic + "]");
        }
    }
    public static void pause2()
    {
        Handler localHandler = new Handler();
        Runnable local1 = new Runnable() {
            public void run() {

                mp.setVolume(0.90f, 0.90f);
            }
        };

        Runnable local2 = new Runnable() {
            public void run() {

                mp.setVolume(0.80f, 0.80f);
            }
        };

        Runnable local3 = new Runnable() {
            public void run() {

                mp.setVolume(0.70f, 0.70f);
            }
        };

        Runnable local4 = new Runnable() {
            public void run() {

                mp.setVolume(0.60f, 0.60f);
            }
        };
        Runnable local5 = new Runnable() {
            public void run() {

                mp.setVolume(0.50f, 0.50f);
            }
        };
        Runnable local6 = new Runnable() {
            public void run() {

                mp.setVolume(0.40f, 0.40f);
            }
        };
        Runnable local7 = new Runnable() {
            public void run() {

                mp.setVolume(0.30f, 0.30f);
            }
        };
        Runnable local8 = new Runnable() {
            public void run() {

                mp.setVolume(0.20f, 0.20f);
            }
        };
        Runnable local9 = new Runnable() {
            public void run() {

                mp.setVolume(0.10f, 0.10f);
            }
        };
        Runnable local10 = new Runnable() {
            public void run() {

                mp.setVolume(0.00f, 0.00f);
            }
        };

        localHandler.postDelayed(local1, 100L);
        localHandler.postDelayed(local2, 200L);
        localHandler.postDelayed(local3, 300L);
        localHandler.postDelayed(local4, 400L);
        localHandler.postDelayed(local5, 500L);
        localHandler.postDelayed(local6, 600L);
        localHandler.postDelayed(local7, 700L);
        localHandler.postDelayed(local8, 800L);
        localHandler.postDelayed(local9, 900L);
        localHandler.postDelayed(local10, 1000L);
    }

    public static void release()
    {
        Log.d(TAG, "Releasing media players");
        try {
            if (mp != null) {
                if (mp.isPlaying()) {
                    mp.stop();
                }
                mp.release();
            }
        } catch (Exception e) {
            Log.e(TAG, e.getMessage(), e);
        }

        if (currentMusic != -1) {
            previousMusic = currentMusic;
            Log.d(TAG, "Previous music was [" + previousMusic + "]");
        }
        currentMusic = -1;
        Log.d(TAG, "Current music is now [" + currentMusic + "]");
    }
}