package com.stepsotf.soundsbook_struct;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
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
        SetButtons(mPageNumber,rootView);
        return rootView;
    }

    /**
     * Returns the page number represented by this fragment object.
     */
    public int getPageNumber() {
        return mPageNumber;
    }

    void SOCL(final int number, View V, int v)//SetOnClickListener
    {
        V.findViewById(v).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                (MediaPlayer.create(getActivity(), R.raw.click)).start();
                startActivity(new Intent(ctx, Alph_active.class).putExtra("lvl", number ));
            }
        });
        V.findViewById(v).startAnimation(AnimationUtils.loadAnimation(getActivity(),
                R.anim.dzhiga_driga));
    }
    void SetButtons(int number, View v)
    {
        switch(number)
        {
            case 0:
                ((ImageButton)v.findViewById(R.id.button1_1)).setImageResource(R.drawable.letter1);
                SOCL(1,v,R.id.button1_1);
                ((ImageButton)v.findViewById(R.id.button1_2)).setImageResource(R.drawable.letter2);
                SOCL(2,v,R.id.button1_2);
                ((ImageButton)v.findViewById(R.id.button1_3)).setImageResource(R.drawable.letter3);
                SOCL(3,v,R.id.button1_3);
                ((ImageButton)v.findViewById(R.id.button2_1)).setImageResource(R.drawable.letter4);
                SOCL(4,v,R.id.button2_1);
                ((ImageButton)v.findViewById(R.id.button2_2)).setImageResource(R.drawable.letter5);
                SOCL(5,v,R.id.button2_2);
                ((ImageButton)v.findViewById(R.id.button2_3)).setImageResource(R.drawable.letter6);
                SOCL(6,v,R.id.button2_3);
                break;
            case 1:

                ((ImageButton)v.findViewById(R.id.button1_1)).setImageResource(R.drawable.letter7);
                SOCL(7,v,R.id.button1_1);
                ((ImageButton)v.findViewById(R.id.button1_2)).setImageResource(R.drawable.letter8);
                SOCL(8,v,R.id.button1_2);
                ((ImageButton)v.findViewById(R.id.button1_3)).setImageResource(R.drawable.letter9);
                SOCL(9,v,R.id.button1_3);
                ((ImageButton)v.findViewById(R.id.button2_1)).setImageResource(R.drawable.letter10);
                SOCL(10,v,R.id.button2_1);
                ((ImageButton)v.findViewById(R.id.button2_2)).setImageResource(R.drawable.letter11);
                SOCL(11,v,R.id.button2_2);
                ((ImageButton)v.findViewById(R.id.button2_3)).setImageResource(R.drawable.letter12);
                SOCL(12,v,R.id.button2_3);
                break;
            case 2:
                ((ImageButton)v.findViewById(R.id.button1_1)).setImageResource(R.drawable.letter13);
                SOCL(13,v,R.id.button1_1);
                ((ImageButton)v.findViewById(R.id.button1_2)).setImageResource(R.drawable.letter14);
                SOCL(14,v,R.id.button1_2);
                ((ImageButton)v.findViewById(R.id.button1_3)).setImageResource(R.drawable.letter15);
                SOCL(15,v,R.id.button1_3);
                ((ImageButton)v.findViewById(R.id.button2_1)).setImageResource(R.drawable.letter16);
                SOCL(16,v,R.id.button2_1);
                ((ImageButton)v.findViewById(R.id.button2_2)).setImageResource(R.drawable.letter17);
                SOCL(17,v,R.id.button2_2);
                ((ImageButton)v.findViewById(R.id.button2_3)).setImageResource(R.drawable.letter18);
                SOCL(18,v,R.id.button2_3);
                break;
            case 3:
                ((ImageButton)v.findViewById(R.id.button1_1)).setImageResource(R.drawable.letter19);
                SOCL(19,v,R.id.button1_1);
                ((ImageButton)v.findViewById(R.id.button1_2)).setImageResource(R.drawable.letter20);
                SOCL(20,v,R.id.button1_2);
                ((ImageButton)v.findViewById(R.id.button1_3)).setImageResource(R.drawable.letter21);
                SOCL(21,v,R.id.button1_3);
                ((ImageButton)v.findViewById(R.id.button2_1)).setImageResource(R.drawable.letter22);
                SOCL(22,v,R.id.button2_1);
                ((ImageButton)v.findViewById(R.id.button2_2)).setImageResource(R.drawable.letter23);
                SOCL(23,v,R.id.button2_2);
                ((ImageButton)v.findViewById(R.id.button2_3)).setImageResource(R.drawable.letter24);
                SOCL(24,v,R.id.button2_3);
                break;
            case 4:
                ((ImageButton)v.findViewById(R.id.button1_1)).setImageResource(R.drawable.letter25);
                SOCL(25,v,R.id.button1_1);
                ((ImageButton)v.findViewById(R.id.button1_2)).setImageResource(R.drawable.letter26);
                SOCL(26,v,R.id.button1_2);
                ((ImageButton)v.findViewById(R.id.button1_3)).setImageResource(R.drawable.letter27);
                SOCL(27,v,R.id.button1_3);
                ((ImageButton)v.findViewById(R.id.button2_1)).setImageResource(R.drawable.letter31);
                SOCL(28,v,R.id.button2_1);
                ((ImageButton)v.findViewById(R.id.button2_2)).setImageResource(R.drawable.letter32);
                SOCL(29,v,R.id.button2_2);
                ((ImageButton)v.findViewById(R.id.button2_3)).setImageResource(R.drawable.letter33);
                SOCL(30,v,R.id.button2_3);
                break;
           /* case 5:
                ((ImageButton)v.findViewById(R.id.button1_1)).setImageResource(R.drawable.letter33);
                SOCL(31,v,R.id.button1_1);
                ((ImageButton)v.findViewById(R.id.button1_2)).setImageResource(R.drawable.letter32);
                SOCL(32,v,R.id.button1_2);
                ((ImageButton)v.findViewById(R.id.button1_3)).setImageResource(R.drawable.letter33);
                SOCL(33,v,R.id.button1_3);
                (v.findViewById(R.id.button2_1)).setVisibility(View.INVISIBLE);
                (v.findViewById(R.id.button2_2)).setVisibility(View.INVISIBLE);
                (v.findViewById(R.id.button2_3)).setVisibility(View.INVISIBLE);
                break;*/

        }
    }
}
