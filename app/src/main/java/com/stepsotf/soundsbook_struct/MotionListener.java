package com.stepsotf.soundsbook_struct;


import android.graphics.Bitmap;
import android.graphics.Color;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ваня on 04.11.2016.
 */
/*
        USAGE:
           *in source class we should have handled array of imageviews
           * Something like://below operations should be in some method
        myView= (ImageView)findViewById(R.id.imageView);
        Bitmap bmpt = BitmapFactory.decodeResource(MainActivity.this.getResources(),R.drawable.pic1);//use this func because createBitmap sometimes doesn`t work correctly
        myView.setImageBitmap(bmpt);
        myView.setDrawingCacheEnabled(true);//otherwise bitmap operations returns null

           - To use it in your source code write
             MotionListener ml = new MotionListener();
             myView.setOnTouchListener(ml.getOTL());
 */
class MotionListener {
    private boolean drag = false;
    private List<Picture> pic_list=new ArrayList<>();
    private int fingers=0;//number of fingers on screen

    /*
    class Pictures describes touching pics
     */
    private class Picture
    {
        View v;
        MotionEvent ev;
        float x,y,dx,dy;
        Picture(View v_,
                MotionEvent ev_)
        {
            this.v = v_;
            this.ev=ev_;
            //getX() and getY(), should return you coordinates, relative to the View, that dispatched them
            //that`s why we use getRawX
            this.x=ev_.getRawX();
            this.y=ev_.getRawY();
            this.dx = this.x - v_.getX();
            this.dy = this.y - v_.getY();
        }
    }
    private boolean IsViewPixelVisibile(View v, float x,float y)
    {
        Bitmap bmp = Bitmap.createBitmap(v.getDrawingCache());
        int color = 0;
        try {
            color = bmp.getPixel((int) x, (int) y);
        } catch (Exception e) {
            //Log.d("coords", "exc" + e);
        }
        return (color != Color.TRANSPARENT);
    }

    /*
    GetPicFromView return Picture that has view v
     */
    private Picture GetPicFromView(View v)
    {
        for(int i=0;i<fingers;i++)
            if(v==pic_list.get(i).v)return pic_list.get(i);
        return null;
    }
    OnTouchListener GetOnTouchListener() {
        return new OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                //use this(!drag) to avoid stopping dragging image because of exception(touch became out of view)
                if(!drag &&!IsViewPixelVisibile(v,event.getX(),event.getY()))return false;

                switch (event.getActionMasked()) {
                    case MotionEvent.ACTION_DOWN:
                        pic_list.add(new Picture(v,event));
                        drag=true;
                        fingers++;
                        v.bringToFront();
                        break;
                    case MotionEvent.ACTION_MOVE: {
                        Picture tmp = GetPicFromView(v);
                        if(tmp!=null) {
                            tmp.v.setX(tmp.ev.getRawX() - tmp.dx);
                            tmp.v.setY(tmp.ev.getRawY() - tmp.dy);
                        }
                    }
                    break;
                    case MotionEvent.ACTION_UP: {
                        drag=false;
                        fingers--;
                        if(fingers==0) pic_list.clear();
                        else pic_list.remove(GetPicFromView(v));
                    }
                    break;
                }
                return true;
            }
        };
    }
}