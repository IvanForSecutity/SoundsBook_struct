package com.stepsotf.soundsbook_struct;

import android.app.Activity;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.AnimationUtils;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import static com.stepsotf.soundsbook_struct.MainActivity.uiOptions;
import static com.stepsotf.soundsbook_struct.SpeechRec.results_speech;
import static java.lang.Thread.sleep;

public class Alph_active extends Activity {
    Resources res;
    Handler h,voice_handler;
    int con_num=0;//number of connections
    final int CONNECTED_1_2 = 1;
    final int ALP_LVL_NUM = 1;
    final int CONNECTED_1_3 = 2;
    final int CONNECTED_2_3 = 3;
    final int CONNECTED_ALL = 4;
    DisplayMetrics metricsB;
    String [] Alphabeth = {"Error","А"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alph_active);
        getWindow().getDecorView().setSystemUiVisibility(uiOptions);
        res =this.getResources();
        final ImageView[] iv = new ImageView[4];
        final  Math_calculations mc = new Math_calculations();
        Display display = getWindowManager().getDefaultDisplay();
        metricsB = new DisplayMetrics();
        display.getMetrics(metricsB);
        float PicsXY[] = mc.getCoords(metricsB.widthPixels - metricsB.heightPixels / 4, metricsB.heightPixels - metricsB.heightPixels / 7, metricsB.heightPixels / 4);
        iv[0] = (ImageView) findViewById(R.id.alph_pic1);
        iv[1] = (ImageView) findViewById(R.id.alph_pic2);
        iv[2] = (ImageView) findViewById(R.id.alph_pic3);
        iv[3] = (ImageView) findViewById(R.id.alph_pic4);
        for(int i=0;i<4;i++){iv[i].setX(PicsXY[i*2]);iv[i].setY(PicsXY[i*2+1]);}
        setListener(iv);
        h = new Handler() {
            public void handleMessage(android.os.Message msg) {
                switch (msg.what) {//switch connection
                    case CONNECTED_1_2:
                        MotionListener ml1 = new MotionListener();
                        iv[1].setVisibility(View.GONE);
                        iv[0].setImageBitmap(SumBmpBmp(GetBitmapFP(0),GetBitmapFP(1)));
                        iv[0].setOnTouchListener(ml1.GetOnTouchListener());
                        iv[0].invalidate();
                        break;
                    case CONNECTED_1_3:
                        MotionListener ml2 = new MotionListener();
                        iv[2].setVisibility(View.GONE);
                        iv[0].setImageBitmap(SumBmpBmp(GetBitmapFP(0),GetBitmapFP(2)));
                        iv[0].setOnTouchListener(ml2.GetOnTouchListener());
                        iv[0].invalidate();
                        break;
                    case CONNECTED_2_3:
                        MotionListener ml3 = new MotionListener();
                        iv[2].setVisibility(View.GONE);
                        iv[1].setImageBitmap(SumBmpBmp(GetBitmapFP(1),GetBitmapFP(2)));
                        iv[1].setOnTouchListener(ml3.GetOnTouchListener());
                        iv[1].invalidate();
                        break;
                    case CONNECTED_ALL://base is 0
                    case CONNECTED_ALL+1:                    //base is 1

                        iv[2].setVisibility(View.GONE);
                        iv[CONNECTED_ALL+1-msg.what].setVisibility(View.GONE);
                        iv[3].setVisibility(View.GONE);
                        iv[msg.what-CONNECTED_ALL].setImageBitmap(SumBmpBmp(SumBmpBmp(GetBitmapFP(0),GetBitmapFP(1)),GetBitmapFP(2)));
                        iv[msg.what-CONNECTED_ALL].setClickable(false);
                        iv[msg.what-CONNECTED_ALL].invalidate();
                        moveViewToScreenCenter(iv[msg.what-CONNECTED_ALL]);
                        ShowLetter();

                        voice_handler = new Handler() {
                            public void handleMessage(android.os.Message msg) {
                                if (msg.what == 223)//code 123 means that recognition is ended
                                    if(ListenAndCheck()){//correct recognition
                                        Log.d("AA","GO Next LVL1");
                                        findViewById(R.id.alph_pic_micro).setVisibility(View.GONE);
                                    }
                            }
                        };
                        AskToSay();
                        break;
                }
            }
        };
        new Thread(new Runnable() {

            public void run() {
                try {
                    sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                int f_s_check=1;
                int f_t_check=1;
                int s_t_check=1;
                int last_id=0;//id of base of last connected pic
                while(con_num!=2)//connection checker
                {
                   /* try {
                        sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }*/
                    int err=20;

                    double f_s = Math.abs(iv[0].getX() - iv[1].getX()) + Math.abs(iv[0].getY() - iv[1].getY());//first and second
                    f_s*=f_s_check;
                    double f_t = Math.abs(iv[0].getX() - iv[2].getX()) + Math.abs(iv[0].getY() - iv[2].getY());;//first and third
                    f_t*=f_t_check;
                    double s_t = Math.abs(iv[1].getX() - iv[2].getX()) + Math.abs(iv[1].getY() - iv[2].getY());;//second and third
                    s_t*=s_t_check;
                    if (f_s < err && f_s != 0 && iv[1].getVisibility() != View.GONE) {//
                        f_s_check=0;
                        h.sendEmptyMessage(CONNECTED_1_2);
                        con_num++;
                        last_id=0;
                        Log.d("CONNECTIONS", "1-2");
                    }
                    if (f_t < err && f_t != 0 && iv[2].getVisibility() != View.GONE) {//
                        f_t_check=0;
                        h.sendEmptyMessage(CONNECTED_1_3);
                        con_num++;
                        last_id=0;

                        Log.d("CONNECTIONS", "1-3");
                    }
                    if (s_t < err && s_t != 0 && iv[2].getVisibility() != View.GONE) {//
                        s_t_check=0;
                        h.sendEmptyMessage(CONNECTED_2_3);
                        con_num++;
                        last_id=1;
                        Log.d("CONNECTIONS", "2-3");
                    }

                }
                h.sendEmptyMessage(CONNECTED_ALL+last_id);
                Log.d("CONNECTIONS", "ALL");
            }
        }).start();

    }


    int GetPicture(int part_num)//return drawable of the part of current picture
    {
        int[][] vid = new int[33][4];
        vid[0][0]=R.drawable.arbuz1;
        vid[0][1]=R.drawable.arbuz2;
        vid[0][2]=R.drawable.arbuz3;
        vid[0][3]=R.drawable.grib1;
        return vid[ALP_LVL_NUM-1][part_num];
    }


    private void moveViewToScreenCenter(final  View view )
    {
            RelativeLayout root = (RelativeLayout) findViewById( R.id.activity_alph_active );
            final DisplayMetrics dm = new DisplayMetrics();
            this.getWindowManager().getDefaultDisplay().getMetrics( dm );
            final int statusBarOffset = dm.heightPixels - root.getMeasuredHeight();

            final int originalPos[] = new int[2];
            view.getLocationOnScreen(originalPos);

            int xDest = dm.widthPixels/2;
            xDest -= (view.getMeasuredWidth()/2);
            xDest+=100;
            int yDest = dm.heightPixels/2 - (view.getMeasuredHeight()/2) - statusBarOffset;

            TranslateAnimation anim = new TranslateAnimation( 0, xDest - originalPos[0] , 0, yDest - originalPos[1] );
            anim.setDuration(1000);
            anim.setFillAfter( true );
            view.startAnimation(anim);
        Handler localHandler = new Handler();

        Runnable recFin = new Runnable() {
            public void run() {
                int xDest = dm.widthPixels/2;
                xDest -= (view.getMeasuredWidth()/2);
                xDest+=100;
                int yDest = dm.heightPixels/2 - (view.getMeasuredHeight()/2) - statusBarOffset;
                TranslateAnimation to_left_top = new TranslateAnimation(xDest - originalPos[0],- originalPos[0] , yDest - originalPos[1],- originalPos[1]);//отсчет от текущего места
                to_left_top.setDuration(1000);
                to_left_top.setFillAfter(true);

                AnimationSet anim_to_lt_reduce = (AnimationSet) AnimationUtils.loadAnimation(Alph_active.this,R.anim.reduce_pic);
                anim_to_lt_reduce.addAnimation(to_left_top);
                anim_to_lt_reduce.setDuration(1000);
                anim_to_lt_reduce.setFillAfter(true);
                view.startAnimation(anim_to_lt_reduce);
            }
        };
        localHandler.postDelayed(recFin, 3600L);
    }

    void AskToSay()
    {
        ImageView iv = (ImageView)findViewById(R.id.alph_pic_let);
        iv.setVisibility(View.GONE);
        iv.invalidate();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                ImageView iv1 = (ImageView)findViewById(R.id.alph_pic_micro);
                findViewById(R.id.alph_pic_let).setVisibility(View.GONE);
                iv1.setVisibility(View.VISIBLE);
                new SpeechRec(Alph_active.this,voice_handler);
            }
        },5000);

    }

    //-------------------------------------------------------------------------------------------------------------------------
    //TODO вынести методы в отдельный класс?
    int GetDrawableLetter()//TODO: GOOD
    {
        int drawable=0;
        switch (ALP_LVL_NUM)
        {
            case 1:
                drawable=R.drawable.letter1;
                break;
            case 2:
                drawable=R.drawable.letter2;
                break;
            case 3:
                drawable=R.drawable.letter3;
                break;
            case 4:
                drawable=R.drawable.letter4;
                break;
            case 5:
                drawable=R.drawable.letter5;
                break;
            case 6:
                drawable=R.drawable.letter6;
                break;
            case 7:
                drawable=R.drawable.letter7;
                break;
            case 8:
                drawable=R.drawable.letter8;
                break;
            case 9:
                drawable=R.drawable.letter9;
                break;
            case 10:
                drawable=R.drawable.letter10;
                break;
            case 11:
                drawable=R.drawable.letter11;
                break;
            case 12:
                drawable=R.drawable.letter12;
                break;
            case 13:
                drawable=R.drawable.letter13;
                break;
            case 14:
                drawable=R.drawable.letter14;
                break;
            case 15:
                drawable=R.drawable.letter15;
                break;
            case 16:
                drawable=R.drawable.letter16;
                break;
            case 17:
                drawable=R.drawable.letter17;
                break;
            case 18:
                drawable=R.drawable.letter18;
                break;
            case 19:
                drawable=R.drawable.letter19;
                break;
            case 20:
                drawable=R.drawable.letter20;
                break;
            case 21:
                drawable=R.drawable.letter21;
                break;
            case 22:
                drawable=R.drawable.letter22;
                break;
            case 23:
                drawable=R.drawable.letter23;
                break;
            case 24:
                drawable=R.drawable.letter24;
                break;
            case 25:
                drawable=R.drawable.letter25;
                break;
            case 26:
                drawable=R.drawable.letter26;
                break;
            case 27:
                drawable=R.drawable.letter27;
                break;
            case 28:
                drawable=R.drawable.letter28;
                break;
            case 29:
                drawable=R.drawable.letter29;
                break;
            case 30:
                drawable=R.drawable.letter30;
                break;
            case 31:
                drawable=R.drawable.letter31;
                break;
            case 32:
                drawable=R.drawable.letter32;
                break;//etc
            case 33:
                drawable=R.drawable.letter33;
                break;
        }
        return drawable;
    }
    void setListener(ImageView[] iv)//make pictures move//TODO: GOOD
    {
        for(int i=0;i<4;i++)
        {
            Bitmap bmpt = BitmapFactory.decodeResource(res, GetPicture(i));//use this func because createBitmap sometimes doesn`t work correctly
            iv[i].setImageBitmap(bmpt);
            iv[i].setDrawingCacheEnabled(true);
            MotionListener ml = new MotionListener();
            iv[i].setOnTouchListener(ml.GetOnTouchListener());
        }
    }
    Bitmap GetBitmapFP(int part_num)//Get Bitmap From pic_num//TODO: GOOD
    {
        return BitmapFactory.decodeResource(res, GetPicture(part_num));
    }
    Bitmap SumBmpBmp(Bitmap b1,Bitmap b2)//TODO: GOOD
    {
        int width = b1.getWidth();
        int height = b1.getHeight();
        int[] srcPixels1 = new int[width * height];
        b1.getPixels(srcPixels1, 0, width, 0, 0, width, height);
        int[] srcPixels2 = new int[width * height];
        b2.getPixels(srcPixels2, 0, width, 0, 0, width, height);
        Bitmap bmDublicated2 = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        //TO DO: copy one bm to another
        for(int i=0;i<width*height;i++)
            srcPixels1[i]|=srcPixels2[i];
        bmDublicated2.setPixels(srcPixels1, 0, width, 0, 0, width, height);
        return bmDublicated2;
    }
    boolean ListenAndCheck()//TODO: GOOD
    {
        Log.d("results_AA","END");
        Log.d("results",":"+results_speech);
        return results_speech.toLowerCase().equals(Alphabeth[ALP_LVL_NUM].toLowerCase());
    }
    void ShowLetter()
    {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {//Show Letter
                ImageView iv = (ImageView)findViewById(R.id.alph_pic_let);
                iv.setImageResource(GetDrawableLetter());
                iv.setVisibility(View.VISIBLE);
                ScaleAnimation scaleAnimation = new ScaleAnimation((float)0.4,(float)1.2,(float)0.4,(float)1.2, Animation.RELATIVE_TO_SELF,(float)0.5,Animation.RELATIVE_TO_SELF,(float)0.5);
                scaleAnimation.setDuration(1000);
                scaleAnimation.setFillAfter(true);
                iv.startAnimation(scaleAnimation);
            }
        },2000);
        //TO DO: DELETE CENTral pic
    }
}
