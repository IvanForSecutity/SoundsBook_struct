package com.stepsotf.soundsbook_struct;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.speech.RecognitionListener;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.util.Log;

import java.util.ArrayList;


/**
 * Created by Ваня on 03.11.2016.
 */

/*
        USAGE:
   -//dont do this -> //delete dependencies body in module gradle;set g v to 22
   -in AndroidManifest:
            <uses-permission android:name="android.permission.RECORD_AUDIO" />
   -in class declare:
            static Handler h;
   -in OnCreate method write:
            h = new Handler() {
            public void handleMessage(android.os.Message msg) {
                if (msg.what == 123)//code 123 means that recognition is ended
                    Recognized();//do smth when recognized
            };
        };
   -to start recognition write:
            new SpeechRec(MainActivity.this,h);//send context and handler

 */
public class SpeechRec {
    private SpeechRecognizer speech_recognizer;
    private Intent speech_recognizer_intent;
    static boolean is_ended;
    static String results_speech;

    SpeechRec(Context ctx,final Handler h)
    {
        is_ended=false;
        new Thread(new Runnable() {
            public void run() {
                while(!is_ended){}
                h.sendEmptyMessage(223);
                Log.d("results_SR","END_SR");

            }
        }).start();

        speech_recognizer = SpeechRecognizer.createSpeechRecognizer(ctx);
        speech_recognizer_intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        speech_recognizer_intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        speech_recognizer_intent.putExtra(RecognizerIntent.EXTRA_CALLING_PACKAGE,
                ctx.getPackageName());

        SpeechRecognitionListener listener = new SpeechRecognitionListener();
        speech_recognizer.setRecognitionListener(listener);
        speech_recognizer.startListening(speech_recognizer_intent);


    }
    private class SpeechRecognitionListener implements RecognitionListener
    {
        @Override
        public void onBeginningOfSpeech()
        {
        }

        @Override
        public void onBufferReceived(byte[] buffer)
        {

        }

        @Override
        public void onEndOfSpeech()
        {
        }

        @Override
        public void onError(int error)
        {
            speech_recognizer.startListening(speech_recognizer_intent);
        }

        @Override
        public void onEvent(int eventType, Bundle params)
        {

        }

        @Override
        public void onPartialResults(Bundle partialResults)
        {

        }

        @Override
        public void onReadyForSpeech(Bundle params)
        {
        }

        @Override
        public void onResults(Bundle results)
        {
            ArrayList<String> matches = results.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION);
            String text="Error";
            if(matches!=null)
                text = matches.get(0);
            Log.d("results_in sr",":"+results_speech);

            results_speech = text;
            is_ended=true;
        }

        @Override
        public void onRmsChanged(float rmsdB)
        {
        }
    }
}
