package com.stepsotf.soundsbook_struct;

import android.app.Activity;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

public class MainActivity extends Activity {

    public static int uiOptions;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        uiOptions = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_FULLSCREEN
                | View.SYSTEM_UI_FLAG_LOW_PROFILE
                | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;
        getWindow().getDecorView().setSystemUiVisibility(uiOptions);
        BackgroundSoundService.start(this,R.raw.wordapp);
    }
    public void goto_next(View view)
    {
        (MediaPlayer.create(MainActivity.this,R.raw.click)).start();
        Log.d("some","go");
        Intent intent = new Intent(MainActivity.this,Menu.class);
        startActivity(intent);
        finish();
    }
    @Override
    protected void onPause() {
        super.onPause();
        //SaveAll();
        BackgroundSoundService.pause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        //SaveAll();
        BackgroundSoundService.start(this, R.raw.wordapp);
    }
}
