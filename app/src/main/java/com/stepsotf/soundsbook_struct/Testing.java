package com.stepsotf.soundsbook_struct;

import android.app.Activity;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import static com.stepsotf.soundsbook_struct.MainActivity.uiOptions;

/**
 * Created by v0090 on 11.02.2017.
 */

public class Testing extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        getWindow().getDecorView().setSystemUiVisibility(uiOptions);

        //voice says "name highlighted picture"
        //start recognition
        //check
        //if correct - change picture if not - try again

        //just for fun: push on balloon
        findViewById(R.id.baloon).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((TextView)findViewById(R.id.Counter)).setText(
                        Integer.parseInt(((TextView)findViewById(R.id.Counter)).getText().toString())+1
                );
            }
        });

    }



    void makePicDark(int id)
    {

    }
    void setCloudContent(int Resource,int id)
    {

    }
    void changeCounter(boolean up)
    {

    }



    @Override
    protected void onPause() {
        super.onPause();
        BackgroundSoundService.pause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        BackgroundSoundService.start(this, R.raw.wordapp);
    }
}
