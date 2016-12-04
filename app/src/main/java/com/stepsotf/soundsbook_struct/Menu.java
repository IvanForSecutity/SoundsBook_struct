package com.stepsotf.soundsbook_struct;

import android.app.Activity;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import static com.stepsotf.soundsbook_struct.MainActivity.uiOptions;

public class Menu extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu);
        getWindow().getDecorView().setSystemUiVisibility(uiOptions);
    }
    public void goto_alph(View view){
        (MediaPlayer.create(Menu.this,R.raw.click)).start();
        Log.d("some","alph");
        Intent alph_intent = new Intent(Menu.this,Alph_slider.class);
        startActivity(alph_intent);
        finish();
        }
    public void goto_read(View view)
    {
        (MediaPlayer.create(Menu.this,R.raw.click)).start();
        Log.d("some","read");
    }
    public void goto_test(View view)
    {
        (MediaPlayer.create(Menu.this,R.raw.click)).start();
        Log.d("some","test");
    }
    public void goto_count(View view)
    {
        (MediaPlayer.create(Menu.this,R.raw.click)).start();
        Log.d("some","count");
        Intent nums_intent = new Intent(Menu.this,Nums_slider.class);
        startActivity(nums_intent);
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
