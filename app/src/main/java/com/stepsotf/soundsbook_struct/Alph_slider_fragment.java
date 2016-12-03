package com.stepsotf.soundsbook_struct;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;


public class Alph_slider_fragment extends Fragment {

    Context ctx;
    /**
     * The argument key for the page number this fragment represents.
     */
    public static final String ARG_PAGE = "page";

    /**
     * The fragment's page number, which is set to the argument value for {@link #ARG_PAGE}.
     */
    private int mPageNumber;

    /**
     * Factory method for this fragment class. Constructs a new fragment for the given page number.
     */
    public static Alph_slider_fragment create(int pageNumber) {
        Alph_slider_fragment fragment = new Alph_slider_fragment();
        Bundle args = new Bundle();
        args.putInt(ARG_PAGE, pageNumber);
        fragment.setArguments(args);
        return fragment;
    }

    public Alph_slider_fragment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPageNumber = getArguments().getInt(ARG_PAGE);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout containing a title and body text.
        ViewGroup rootView = (ViewGroup) inflater
                .inflate(R.layout.activity_slider_fragment, container, false);
        ctx = getActivity();
        setButtons(mPageNumber,rootView);
        return rootView;
    }

    /**
     * Returns the page number represented by this fragment object.
     */
    public int getPageNumber() {
        return mPageNumber;
    }
    void setButtons(int number,View v)
    {
        switch(number)
        {
            case 0:

                ((ImageButton)v.findViewById(R.id.button1_1)).setImageResource(R.drawable.letter1);
                ((ImageButton)v.findViewById(R.id.button1_1)).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent it = new Intent(ctx,Alph_active.class);
                        Log.d("Alph_sl","first");
                        startActivity(it);
                    }
                });
                ((ImageButton)v.findViewById(R.id.button1_2)).setImageResource(R.drawable.letter2);
                ((ImageButton)v.findViewById(R.id.button1_3)).setImageResource(R.drawable.letter3);
                ((ImageButton)v.findViewById(R.id.button2_1)).setImageResource(R.drawable.letter4);
                ((ImageButton)v.findViewById(R.id.button2_2)).setImageResource(R.drawable.letter5);
                ((ImageButton)v.findViewById(R.id.button2_3)).setImageResource(R.drawable.letter6);
            break;
            case 1:

                ((ImageButton)v.findViewById(R.id.button1_1)).setImageResource(R.drawable.letter7);
                ((ImageButton)v.findViewById(R.id.button1_2)).setImageResource(R.drawable.letter8);
                ((ImageButton)v.findViewById(R.id.button1_3)).setImageResource(R.drawable.letter9);
                ((ImageButton)v.findViewById(R.id.button2_1)).setImageResource(R.drawable.letter10);
                ((ImageButton)v.findViewById(R.id.button2_2)).setImageResource(R.drawable.letter11);
                ((ImageButton)v.findViewById(R.id.button2_3)).setImageResource(R.drawable.letter12);
                break;
            case 2:
                ((ImageButton)v.findViewById(R.id.button1_1)).setImageResource(R.drawable.letter13);
                ((ImageButton)v.findViewById(R.id.button1_2)).setImageResource(R.drawable.letter14);
                ((ImageButton)v.findViewById(R.id.button1_3)).setImageResource(R.drawable.letter15);
                ((ImageButton)v.findViewById(R.id.button2_1)).setImageResource(R.drawable.letter16);
                ((ImageButton)v.findViewById(R.id.button2_2)).setImageResource(R.drawable.letter17);
                ((ImageButton)v.findViewById(R.id.button2_3)).setImageResource(R.drawable.letter18);
                break;
            case 3:
                ((ImageButton)v.findViewById(R.id.button1_1)).setImageResource(R.drawable.letter19);
                ((ImageButton)v.findViewById(R.id.button1_2)).setImageResource(R.drawable.letter20);
                ((ImageButton)v.findViewById(R.id.button1_3)).setImageResource(R.drawable.letter21);
                ((ImageButton)v.findViewById(R.id.button2_1)).setImageResource(R.drawable.letter22);
                ((ImageButton)v.findViewById(R.id.button2_2)).setImageResource(R.drawable.letter23);
                ((ImageButton)v.findViewById(R.id.button2_3)).setImageResource(R.drawable.letter24);
                break;
            case 4:
                ((ImageButton)v.findViewById(R.id.button1_1)).setImageResource(R.drawable.letter25);
                ((ImageButton)v.findViewById(R.id.button1_2)).setImageResource(R.drawable.letter26);
                ((ImageButton)v.findViewById(R.id.button1_3)).setImageResource(R.drawable.letter27);
                ((ImageButton)v.findViewById(R.id.button2_1)).setImageResource(R.drawable.letter28);
                ((ImageButton)v.findViewById(R.id.button2_2)).setImageResource(R.drawable.letter29);
                ((ImageButton)v.findViewById(R.id.button2_3)).setImageResource(R.drawable.letter30);
                break;
            case 5:
                ((ImageButton)v.findViewById(R.id.button1_1)).setImageResource(R.drawable.letter31);
                ((ImageButton)v.findViewById(R.id.button1_2)).setImageResource(R.drawable.letter32);
                ((ImageButton)v.findViewById(R.id.button1_3)).setImageResource(R.drawable.letter33);
                ((ImageButton)v.findViewById(R.id.button2_1)).setVisibility(View.INVISIBLE);
                ((ImageButton)v.findViewById(R.id.button2_2)).setVisibility(View.INVISIBLE);
                ((ImageButton)v.findViewById(R.id.button2_3)).setVisibility(View.INVISIBLE);;
                break;

        }
    }
}
