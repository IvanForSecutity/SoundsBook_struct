package com.stepsotf.soundsbook_struct;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.media.MediaPlayer;
import android.os.Handler;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.ViewManager;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import java.util.Random;

import static com.stepsotf.soundsbook_struct.MainActivity.uiOptions;
import static com.stepsotf.soundsbook_struct.SpeechRec.results_speech;
import static java.lang.Thread.sleep;

public class Alph_active extends Activity {
    Resources res;
    Handler h,voice_handler;
    int con_num=0;//number of connections
    final int CONNECTED_1_2 = 1;
     int ALP_LVL_NUM = 1;
    final int CONNECTED_1_3 = 2;
    final int CONNECTED_2_3 = 3;
    final int CONNECTED_ALL = 4;
    DisplayMetrics metricsB;
    boolean second_try = false;//for ListenAndCheck
    String [] Alphabeth = {"Error","А","Б","В","Г","Д","Е","Ё","Ж","З","И","Й","К","Л","М","Н","О","П","Р","С","Т","У","Ф","Х","Ц","Ч","Ш","Щ",/*"Ъ","Ы","Ь",*/"Э","Ю","Я"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alph_active);
        getWindow().getDecorView().setSystemUiVisibility(uiOptions);
        findViewById(R.id.alph_a_back).setOnClickListener(new View.OnClickListener() {//go to slider when press back
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        res =this.getResources();
        BackgroundSoundService.pause();
        ALP_LVL_NUM = getIntent().getIntExtra("lvl",1);
        final ImageView[] iv = new ImageView[4];
        final  Math_calculations mc = new Math_calculations();
        Display display = getWindowManager().getDefaultDisplay();
        metricsB = new DisplayMetrics();
        display.getMetrics(metricsB);
        (MediaPlayer.create(this,R.raw.kartinka)).start();
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
                        iv[0].bringToFront();
                        iv[0].invalidate();
                        break;
                    case CONNECTED_1_3:
                        MotionListener ml2 = new MotionListener();
                        iv[0].bringToFront();
                        ((ViewManager)iv[2].getParent()).removeView(iv[2]);
                        iv[0].setImageBitmap(SumBmpBmp(GetBitmapFP(0),GetBitmapFP(2)));
                        iv[0].setOnTouchListener(ml2.GetOnTouchListener());
                        iv[0].invalidate();
                        break;
                    case CONNECTED_2_3:
                        MotionListener ml3 = new MotionListener();
                        iv[2].setVisibility(View.GONE);
                        iv[1].setImageBitmap(SumBmpBmp(GetBitmapFP(1),GetBitmapFP(2)));
                        iv[1].setOnTouchListener(ml3.GetOnTouchListener());
                        iv[1].bringToFront();
                        iv[1].invalidate();
                        break;
                    case CONNECTED_ALL://base is 0
                    case CONNECTED_ALL+1://base is 1
                        iv[msg.what-CONNECTED_ALL].setClickable(false);
                        iv[2].setVisibility(View.GONE);
                        iv[CONNECTED_ALL+1-msg.what].setVisibility(View.GONE);
                        iv[3].setVisibility(View.GONE);
                        iv[msg.what-CONNECTED_ALL].setImageBitmap(SumBmpBmp(SumBmpBmp(GetBitmapFP(0),GetBitmapFP(1)),GetBitmapFP(2)));
                        iv[msg.what-CONNECTED_ALL].setClickable(false);
                        iv[msg.what-CONNECTED_ALL].invalidate();
                        MoveVtoScreenCtrAndAway(iv[msg.what-CONNECTED_ALL]);

                        voice_handler = new Handler() {
                            public void handleMessage(android.os.Message msg) {
                                if (msg.what == 223)//code 123 means that recognition is ended
                                    if(ListenAndCheck()){//correct recognition
                                        Log.d("AA","GO Next LVL");
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
        Log.d("results",":"+results_speech+"==="+results_speech.substring(0,1));
        if(second_try || results_speech.substring(0,1).toLowerCase().equals(Alphabeth[ALP_LVL_NUM].toLowerCase()))
            ShowLetter();
        else {
            (MediaPlayer.create(Alph_active.this,R.raw.sound_eshe)).start();
            voice_handler = new Handler() {
                public void handleMessage(android.os.Message msg) {
                    if (msg.what == 223)//code 123 means that recognition is ended
                        if(ListenAndCheck()){//correct recognition
                            Log.d("AA","GO Next LVL_second_try");
                            findViewById(R.id.alph_pic_micro).setVisibility(View.GONE);
                        }
                }
            };
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run(){
                    AskToSay();
                }
            } ,1000);
            second_try=true;
        }
        return results_speech.substring(0,1).toLowerCase().equals(Alphabeth[ALP_LVL_NUM].toLowerCase());
    }
    void ShowLetter()//TODO: GOOD
    {
        if(!second_try) {
            Random good_rnd = new Random();
            switch (good_rnd.nextInt(6)) {
                case 0:
                    (MediaPlayer.create(Alph_active.this, R.raw.molodec)).start();
                    break;
                case 1:
                    (MediaPlayer.create(Alph_active.this, R.raw.sssmolode)).start();
                    break;
                case 2:
                    (MediaPlayer.create(Alph_active.this, R.raw.takderzhat)).start();
                    break;
                case 3:
                    (MediaPlayer.create(Alph_active.this, R.raw.sound_vtomzheduhe)).start();
                    break;
                case 4:
                    (MediaPlayer.create(Alph_active.this, R.raw.umnica)).start();
                    break;
                case 5:
                    (MediaPlayer.create(Alph_active.this, R.raw.sound_umnica2)).start();
                    break;
                default:
                    break;
            }//Saying "You are good!"
        }
        else (MediaPlayer.create(Alph_active.this,R.raw.umnica)).start();

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
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {//Show next button
                startActivity(new Intent(Alph_active.this,Next_Lvl_Stub.class).putExtra("lvl",ALP_LVL_NUM+1));

                finish();
            }
        },3000);

    }
    private void MoveVtoScreenCtrAndAway(final  View view )//TODO: GOOD
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

        Runnable runnable = new Runnable() {
            public void run() {
                int xDest = dm.widthPixels/2;
                xDest -= (view.getMeasuredWidth()/2);
                xDest+=100;
                int yDest = dm.heightPixels/2 - (view.getMeasuredHeight()/2) - statusBarOffset;
                TranslateAnimation to_left_top = new TranslateAnimation(xDest - originalPos[0],- originalPos[0] , yDest - originalPos[1],- originalPos[1]);//отсчет от текущего места
                to_left_top.setDuration(1000);
                to_left_top.setFillAfter(true);

                ScaleAnimation reduce = new ScaleAnimation((float)1,(float)0.4,(float)1,(float)0.4, Animation.RELATIVE_TO_SELF,(float)0.5,Animation.RELATIVE_TO_SELF,(float)0.5);
                reduce.setDuration(1000);
                reduce.setFillAfter(true);

                AnimationSet anim_to_lt_reduce = new AnimationSet(true) ;

                anim_to_lt_reduce.addAnimation(to_left_top);
                anim_to_lt_reduce.addAnimation(reduce);
                anim_to_lt_reduce.setDuration(1000);
                anim_to_lt_reduce.setFillAfter(true);
                view.startAnimation(anim_to_lt_reduce);
            }
        };
        localHandler.postDelayed(runnable, 3600L);
    }
    int GetRaw()//returns needed sound(letter)
    {
        int[] raw_by_lvl= new int [33];
        raw_by_lvl[1]= R.raw.a;
        raw_by_lvl[2]= R.raw.b;
        raw_by_lvl[3]= R.raw.v;
        raw_by_lvl[4]= R.raw.g;
        raw_by_lvl[5]= R.raw.d;
        raw_by_lvl[6]= R.raw.e;
        raw_by_lvl[7]= R.raw.yo;
        raw_by_lvl[8]= R.raw.zh;
        raw_by_lvl[9]= R.raw.z;
        raw_by_lvl[10]= R.raw.i;
        raw_by_lvl[11]= R.raw.i;
        raw_by_lvl[12]= R.raw.k;
        raw_by_lvl[13]= R.raw.l;
        raw_by_lvl[14]= R.raw.m;
        raw_by_lvl[15]= R.raw.n;
        raw_by_lvl[16]= R.raw.o;
        raw_by_lvl[17]= R.raw.p;
        raw_by_lvl[18]= R.raw.r;
        raw_by_lvl[19]= R.raw.s;
        raw_by_lvl[20]= R.raw.t;
        raw_by_lvl[21]= R.raw.u;
        raw_by_lvl[22]= R.raw.sound_f;
        raw_by_lvl[23]= R.raw.h;
        raw_by_lvl[24]= R.raw.c;
        raw_by_lvl[25]= R.raw.ch;
        raw_by_lvl[26]= R.raw.sh;
        raw_by_lvl[27]= R.raw.shi;
        raw_by_lvl[28]= R.raw.eu;
        raw_by_lvl[29]= R.raw.iu;
        raw_by_lvl[10]= R.raw.ya;
        return raw_by_lvl[ALP_LVL_NUM];
    }
    void AskToSay()
    {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                (MediaPlayer.create(Alph_active.this,R.raw.say_letter)).start();
            }
        },2000);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                (MediaPlayer.create(Alph_active.this,GetRaw())).start();
            }
        },4000);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                ImageView iv1 = (ImageView)findViewById(R.id.alph_pic_micro);
                iv1.setVisibility(View.VISIBLE);
                new SpeechRec(Alph_active.this,voice_handler);
            }
        },5000);
    }
    public void GoNextLvl(View view)//its for button on xml
    {
        if(ALP_LVL_NUM>Alphabeth.length-2)finish();
        else {
            (MediaPlayer.create(Alph_active.this, R.raw.click)).start();
            finish();
            System.gc();//TODO Check this shit
            startActivity(new Intent(Alph_active.this, Alph_active.class).putExtra("lvl", ALP_LVL_NUM + 1));
        }
    }
    int GetPicture(int part_num)//return drawable of the part of current picture
    {
        int[][] vid = new int[33][4];
        vid[0][0]=R.drawable.arbuz1;
        vid[0][1]=R.drawable.arbuz2;
        vid[0][2]=R.drawable.arbuz3;
        vid[0][3]=R.drawable.grib1;
        vid[1][0]=R.drawable.babochka1;
        vid[1][1]=R.drawable.babochka2;
        vid[1][2]=R.drawable.babochka3;
        vid[1][3]=R.drawable.yabloko1;
        vid[2][0]=R.drawable.varezhka1;
        vid[2][1]=R.drawable.varezhka2;
        vid[2][2]=R.drawable.varezhka3;
        vid[2][3]=R.drawable.fonar2;
        vid[3][0]=R.drawable.grib1;
        vid[3][1]=R.drawable.grib2;
        vid[3][2]=R.drawable.grib3;
        vid[3][3]=R.drawable.zont3;
        vid[4][0]=R.drawable.dom1;
        vid[4][1]=R.drawable.dom2;
        vid[4][2]=R.drawable.dom3;
        vid[4][3]=R.drawable.el2;
        vid[5][0]=R.drawable.el1;
        vid[5][1]=R.drawable.el2;
        vid[5][2]=R.drawable.el3;
        vid[5][3]=R.drawable.iozh3;
        vid[6][0]=R.drawable.iozh1;
        vid[6][1]=R.drawable.iozh2;
        vid[6][2]=R.drawable.iozh3;
        vid[6][3]=R.drawable.zhuk1;
        vid[7][0]=R.drawable.zhuk1;
        vid[7][1]=R.drawable.zhuk2;
        vid[7][2]=R.drawable.zhuk3;
        vid[7][3]=R.drawable.zont2;
        vid[8][0]=R.drawable.zont1;
        vid[8][1]=R.drawable.zont2;
        vid[8][2]=R.drawable.zont3;
        vid[8][3]=R.drawable.igla3;
        vid[9][0]=R.drawable.igla1;
        vid[9][1]=R.drawable.igla2;
        vid[9][2]=R.drawable.igla3;
        vid[9][3]=R.drawable.kit1;
        vid[10][0]=R.drawable.iod1;
        vid[10][1]=R.drawable.iod2;
        vid[10][2]=R.drawable.iod3;
        vid[10][3]=R.drawable.el3;
        vid[11][0]=R.drawable.kit1;
        vid[11][1]=R.drawable.kit2;
        vid[11][2]=R.drawable.kit3;
        vid[11][3]=R.drawable.lozhka2;
        vid[12][0]=R.drawable.lozhka1;
        vid[12][1]=R.drawable.lozhka2;
        vid[12][2]=R.drawable.lozhka3;
        vid[12][3]=R.drawable.miach3;
        vid[13][0]=R.drawable.miach1;
        vid[13][1]=R.drawable.miach2;
        vid[13][2]=R.drawable.miach3;
        vid[13][3]=R.drawable.nosorog1;
        vid[14][0]=R.drawable.nosorog1;
        vid[14][1]=R.drawable.nosorog2;
        vid[14][2]=R.drawable.nosorog3;
        vid[14][3]=R.drawable.ochki2;
        vid[15][0]=R.drawable.ochki1;
        vid[15][1]=R.drawable.ochki2;
        vid[15][2]=R.drawable.ochki3;
        vid[15][3]=R.drawable.pugovica3;
        vid[16][0]=R.drawable.pugovica1;
        vid[16][1]=R.drawable.pugovica2;
        vid[16][2]=R.drawable.pugovica3;
        vid[16][3]=R.drawable.rak1;
        vid[17][0]=R.drawable.rak1;
        vid[17][1]=R.drawable.rak2;
        vid[17][2]=R.drawable.rak3;
        vid[17][3]=R.drawable.samolet2;
        vid[18][0]=R.drawable.samolet1;
        vid[18][1]=R.drawable.samolet2;
        vid[18][2]=R.drawable.samolet3;
        vid[18][3]=R.drawable.tarelka3;
        vid[19][0]=R.drawable.tarelka1;
        vid[19][1]=R.drawable.tarelka2;
        vid[19][2]=R.drawable.tarelka3;
        vid[19][3]=R.drawable.utka1;
        vid[20][0]=R.drawable.utka1;
        vid[20][1]=R.drawable.utka2;
        vid[20][2]=R.drawable.utka3;
        vid[29][3]=R.drawable.fonar2;
        vid[21][0]=R.drawable.fonar1;
        vid[21][1]=R.drawable.fonar2;
        vid[21][2]=R.drawable.fonar3;
        vid[21][3]=R.drawable.halat3;
        vid[22][0]=R.drawable.halat1;
        vid[22][1]=R.drawable.halat2;
        vid[22][2]=R.drawable.halat3;
        vid[22][3]=R.drawable.caplia1;
        vid[23][0]=R.drawable.caplia1;
        vid[23][1]=R.drawable.caplia2;
        vid[23][2]=R.drawable.caplia3;
        vid[23][3]=R.drawable.chashka2;
        vid[24][0]=R.drawable.chashka1;
        vid[24][1]=R.drawable.chashka2;
        vid[24][2]=R.drawable.chashka3;
        vid[24][3]=R.drawable.shapka3;
        vid[25][0]=R.drawable.shapka1;
        vid[25][1]=R.drawable.shapka2;
        vid[25][2]=R.drawable.shapka3;
        vid[25][3]=R.drawable.shuka1;
        vid[26][0]=R.drawable.shuka1;
        vid[26][1]=R.drawable.shuka2;
        vid[26][2]=R.drawable.shuka3;
        vid[26][3]=R.drawable.eskimo2;
        vid[27][0]=R.drawable.eskimo1;
        vid[27][1]=R.drawable.eskimo2;
        vid[27][2]=R.drawable.eskimo3;
        vid[27][3]=R.drawable.iug3;
        vid[28][0]=R.drawable.iug1;
        vid[28][1]=R.drawable.iug2;
        vid[28][2]=R.drawable.iug3;
        vid[28][3]=R.drawable.yabloko1;
        vid[29][0]=R.drawable.yabloko1;
        vid[29][1]=R.drawable.yabloko2;
        vid[29][2]=R.drawable.yabloko3;
        vid[29][3]=R.drawable.arbuz3;
        return vid[ALP_LVL_NUM-1][part_num];
    }

}
