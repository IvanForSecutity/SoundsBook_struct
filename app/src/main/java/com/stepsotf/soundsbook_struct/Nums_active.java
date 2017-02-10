package com.stepsotf.soundsbook_struct;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Picture;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import java.util.Random;

import static com.stepsotf.soundsbook_struct.MainActivity.uiOptions;
import static com.stepsotf.soundsbook_struct.SpeechRec.results_speech;

/**
 * Created by v0090 on 04.12.2016.
 */

public class Nums_active extends Activity {
    int NUMS_LVL_NUM = 1;
    String[] nums = {"Error","одну","две","три","четыре","пять","шесть","семь","восемь","девять"};
    ImageView[][] pics = new ImageView[3][3];
    Handler voice_handler;
    boolean second_try=false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nums_active);
        NUMS_LVL_NUM = getIntent().getIntExtra("lvl_nums",1);
        pics[0][0] = (ImageView) findViewById(R.id.imageView7);
        pics[0][1] = (ImageView) findViewById(R.id.imageView8);
        pics[0][2] = (ImageView) findViewById(R.id.imageView6);
        pics[1][0] = (ImageView) findViewById(R.id.imageView9);
        pics[1][1] = (ImageView) findViewById(R.id.imageView10);
        pics[1][2] = (ImageView) findViewById(R.id.imageView11);
        pics[2][0] = (ImageView) findViewById(R.id.imageView12);
        pics[2][1] = (ImageView) findViewById(R.id.imageView13);
        pics[2][2] = (ImageView) findViewById(R.id.imageView14);
        uiOptions = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_FULLSCREEN
                | View.SYSTEM_UI_FLAG_LOW_PROFILE
                | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;
        getWindow().getDecorView().setSystemUiVisibility(uiOptions);
        SetVisiblePics();

        findViewById(R.id.nums_a_back).setOnClickListener(new View.OnClickListener() {//go to slider when press back
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        voice_handler = new Handler() {
            public void handleMessage(android.os.Message msg) {
                if (msg.what == 223)//code 123 means that recognition is ended
                    if(ListenAndCheck()){//correct recognition
                        Log.d("NA","GO Next LVL");
                        if(!second_try) {
                            Random good_rnd = new Random();
                            switch (good_rnd.nextInt(6)) {
                                case 0:
                                    (MediaPlayer.create(Nums_active.this, R.raw.molodec)).start();
                                    break;
                                case 1:
                                    (MediaPlayer.create(Nums_active.this, R.raw.sssmolode)).start();
                                    break;
                                case 2:
                                    (MediaPlayer.create(Nums_active.this, R.raw.takderzhat)).start();
                                    break;
                                case 3:
                                    (MediaPlayer.create(Nums_active.this, R.raw.sound_vtomzheduhe)).start();
                                    break;
                                case 4:
                                    (MediaPlayer.create(Nums_active.this, R.raw.umnica)).start();
                                    break;
                                case 5:
                                    (MediaPlayer.create(Nums_active.this, R.raw.sound_umnica2)).start();
                                    break;
                                default:
                                    break;
                            }//Saying "You are good!"
                        }
                        else (MediaPlayer.create(Nums_active.this,R.raw.umnica)).start();

                        findViewById(R.id.nums_pic_micro).setVisibility(View.GONE);
                   }
            }
        };
        AskToCount();
    }

    void AskToCount()
    {
        MediaPlayer mp = MediaPlayer.create(Nums_active.this,R.raw.ask_to_count);
        mp.setVolume((float)0.2,(float)0.5);
        mp.start();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                findViewById(R.id.nums_pic_micro).setVisibility(View.VISIBLE);
                findViewById(R.id.nums_pic_micro).bringToFront();
                new SpeechRec(Nums_active.this,voice_handler);
            }
        },3000);
    }
    boolean ListenAndCheck()
    {
        Log.d("nums","" + results_speech);
        boolean res =  Integer.toString(NUMS_LVL_NUM).equals(results_speech) || results_speech.toLowerCase().equals(nums[NUMS_LVL_NUM].toLowerCase());
        if(second_try|| res)
            startActivity(new Intent(Nums_active.this,Next_Lvl_Stub.class).putExtra("lvl_nums",NUMS_LVL_NUM+1));
        else {
            (MediaPlayer.create(Nums_active.this,R.raw.sound_eshe)).start();
            voice_handler = new Handler() {
                public void handleMessage(android.os.Message msg) {
                    if (msg.what == 223)//code 123 means that recognition is ended
                        if(ListenAndCheck()){//correct recognition
                            Log.d("AA","GO Next LVL_second_try");
                            findViewById(R.id.nums_pic_micro).setVisibility(View.GONE);
                        }
                }
            };
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run(){
                    AskToCount();
                }
            } ,1000);
            second_try=true;
        }
        return res;
    }
    public void GoNextLvl_nums(View view)//its for button on xml
    {
        if(NUMS_LVL_NUM>8)finish();
        else {
            (MediaPlayer.create(Nums_active.this, R.raw.click)).start();
            finish();
            startActivity(new Intent(Nums_active.this, Nums_active.class).putExtra("lvl_nums", NUMS_LVL_NUM + 1));
        }
    }
    void SetVisiblePics()
    {
        for (ImageView[] asd: pics) {
            for(ImageView as:asd){as.setVisibility(View.INVISIBLE);}
        }
        int[] Images = getRandomPics(NUMS_LVL_NUM);
        switch (NUMS_LVL_NUM)
        {
            case 1:
                pics[1][1].setImageResource(Images[0]);
                pics[1][1].setVisibility(View.VISIBLE);
                break;
            case 2:
                pics[1][0].setImageResource(Images[0]);
                pics[1][2].setImageResource(Images[1]);
                
                pics[1][0].setVisibility(View.VISIBLE);
                pics[1][2].setVisibility(View.VISIBLE);
                break;
            case 3:
                pics[1][0].setImageResource(Images[0]);
                pics[1][1].setImageResource(Images[1]);
                pics[1][2].setImageResource(Images[2]);
                
                pics[1][0].setVisibility(View.VISIBLE);
                pics[1][1].setVisibility(View.VISIBLE);
                pics[1][2].setVisibility(View.VISIBLE);
                break;
            case 4:
                pics[0][1].setImageResource(Images[0]);
                pics[1][2].setImageResource(Images[1]);
                pics[1][0].setImageResource(Images[2]);
                pics[2][1].setImageResource(Images[3]);
                
                pics[0][1].setVisibility(View.VISIBLE);
                pics[1][2].setVisibility(View.VISIBLE);
                pics[1][0].setVisibility(View.VISIBLE);
                pics[2][1].setVisibility(View.VISIBLE);
                break;
            case 5:
                pics[0][1].setImageResource(Images[0]);
                pics[1][2].setImageResource(Images[1]);
                pics[1][0].setImageResource(Images[2]);
                pics[2][1].setImageResource(Images[3]);
                pics[1][1].setImageResource(Images[4]);
                
                pics[0][1].setVisibility(View.VISIBLE);
                pics[1][2].setVisibility(View.VISIBLE);
                pics[1][0].setVisibility(View.VISIBLE);
                pics[2][1].setVisibility(View.VISIBLE);
                pics[1][1].setVisibility(View.VISIBLE);
                break;
            case 6:
                pics[0][0].setImageResource(Images[0]);
                pics[0][2].setImageResource(Images[1]);
                pics[1][0].setImageResource(Images[2]);
                pics[1][2].setImageResource(Images[3]);
                pics[2][0].setImageResource(Images[4]);
                pics[2][2].setImageResource(Images[5]);
                
                pics[0][0].setVisibility(View.VISIBLE);
                pics[0][2].setVisibility(View.VISIBLE);
                pics[1][0].setVisibility(View.VISIBLE);
                pics[1][2].setVisibility(View.VISIBLE);
                pics[2][0].setVisibility(View.VISIBLE);
                pics[2][2].setVisibility(View.VISIBLE);
                break;
            case 7:
                pics[0][0].setImageResource(Images[0]);
                pics[0][2].setImageResource(Images[1]);
                pics[1][0].setImageResource(Images[2]);
                pics[1][1].setImageResource(Images[3]);
                pics[1][2].setImageResource(Images[4]);
                pics[2][0].setImageResource(Images[5]);
                pics[2][2].setImageResource(Images[6]);
                
                pics[0][0].setVisibility(View.VISIBLE);
                pics[0][2].setVisibility(View.VISIBLE);
                pics[1][0].setVisibility(View.VISIBLE);
                pics[1][1].setVisibility(View.VISIBLE);
                pics[1][2].setVisibility(View.VISIBLE);
                pics[2][0].setVisibility(View.VISIBLE);
                pics[2][2].setVisibility(View.VISIBLE);
                break;
            case 8:
                pics[0][0].setImageResource(Images[0]);
                pics[0][1].setImageResource(Images[1]);
                pics[0][2].setImageResource(Images[2]);
                pics[1][0].setImageResource(Images[3]);
                pics[1][2].setImageResource(Images[4]);
                pics[2][0].setImageResource(Images[5]);
                pics[2][1].setImageResource(Images[6]);
                pics[2][2].setImageResource(Images[7]);
                
                pics[0][0].setVisibility(View.VISIBLE);
                pics[0][1].setVisibility(View.VISIBLE);
                pics[0][2].setVisibility(View.VISIBLE);
                pics[1][0].setVisibility(View.VISIBLE);
                pics[1][2].setVisibility(View.VISIBLE);
                pics[2][0].setVisibility(View.VISIBLE);
                pics[2][1].setVisibility(View.VISIBLE);
                pics[2][2].setVisibility(View.VISIBLE);
                break;
            case 9:
                pics[0][0].setImageResource(Images[0]);
                pics[0][1].setImageResource(Images[1]);
                pics[0][2].setImageResource(Images[2]);
                pics[1][0].setImageResource(Images[3]);
                pics[1][1].setImageResource(Images[4]);
                pics[1][2].setImageResource(Images[5]);
                pics[2][0].setImageResource(Images[6]);
                pics[2][1].setImageResource(Images[7]);
                pics[2][2].setImageResource(Images[8]);
                
                pics[0][0].setVisibility(View.VISIBLE);
                pics[0][1].setVisibility(View.VISIBLE);
                pics[0][2].setVisibility(View.VISIBLE);
                pics[1][0].setVisibility(View.VISIBLE);
                pics[1][1].setVisibility(View.VISIBLE);
                pics[1][2].setVisibility(View.VISIBLE);
                pics[2][0].setVisibility(View.VISIBLE);
                pics[2][1].setVisibility(View.VISIBLE);
                pics[2][2].setVisibility(View.VISIBLE);
                break;
        }
    }

    int [] getRandomPics(int size)
    {
        int []pics=new int[size];
        int[] Pictures={R.drawable.babochka,R.drawable.arbuz,R.drawable.caplya,R.drawable.chashka,R.drawable.chainik, R.drawable.dom, R.drawable.elka, R.drawable.eskimo, R.drawable.ezh, R.drawable.fonar, R.drawable.grib, R.drawable.halat, R.drawable.igla, R.drawable.iod, R.drawable.kit, R.drawable.lozhka, R.drawable.nosorog, R.drawable.ochki, R.drawable.plyazh, R.drawable.pugovica, R.drawable.rak, R.drawable.samolet, R.drawable.shapka, R.drawable.shar, R.drawable.shuka, R.drawable.tarelka, R.drawable.utka, R.drawable.varezhki, R.drawable.yabloko, R.drawable.zhuk, R.drawable.zmeya, R.drawable.zont};
        Random r = new Random();
        for(int i=0;i<size;i++)
            pics[i]= Pictures[r.nextInt(32)];
        return pics;
    }
}
