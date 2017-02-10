package com.stepsotf.soundsbook_struct;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;

/**
 * Created by v0090 on 25.11.2016.
 */

public class Nums_slider_fragment extends Fragment {
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
    public static Nums_slider_fragment create(int pageNumber) {
        Nums_slider_fragment fragment = new Nums_slider_fragment();
        Bundle args = new Bundle();
        args.putInt(ARG_PAGE, pageNumber);
        fragment.setArguments(args);
        return fragment;
    }

    public Nums_slider_fragment() {

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
    void SOCL(final int number, View V, int v)//SetOnClickListener
    {
        V.findViewById(v).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                (MediaPlayer.create(getActivity(), R.raw.click)).start();
                startActivity(new Intent(ctx, Nums_active.class).putExtra("lvl_nums", number ));
            }
        });
        V.findViewById(v).startAnimation(AnimationUtils.loadAnimation(getActivity(),
                R.anim.dzhiga_driga));
    }
    void setButtons(int number,View v)
    {
        switch(number)
        {
            case 0:
                ((ImageButton) v.findViewById(R.id.button1_1)).setImageResource(R.drawable.num1);
                SOCL(1, v, R.id.button1_1);
                ((ImageButton) v.findViewById(R.id.button1_2)).setImageResource(R.drawable.num2);
                SOCL(2, v, R.id.button1_2);
                ((ImageButton) v.findViewById(R.id.button1_3)).setImageResource(R.drawable.num3);
                SOCL(3, v, R.id.button1_3);
                ((ImageButton) v.findViewById(R.id.button2_1)).setImageResource(R.drawable.num4);
                SOCL(4, v, R.id.button2_1);
                ((ImageButton) v.findViewById(R.id.button2_2)).setImageResource(R.drawable.num5);
                SOCL(5, v, R.id.button2_2);
                ((ImageButton) v.findViewById(R.id.button2_3)).setImageResource(R.drawable.num6);
                SOCL(6, v, R.id.button2_3);
                break;
            case 1:

                ((ImageButton)v.findViewById(R.id.button1_1)).setImageResource(R.drawable.num7);
                SOCL(7, v, R.id.button1_1);
                ((ImageButton)v.findViewById(R.id.button1_2)).setImageResource(R.drawable.num8);
                SOCL(8, v, R.id.button1_2);
                ((ImageButton)v.findViewById(R.id.button1_3)).setImageResource(R.drawable.num9);
                SOCL(9, v, R.id.button1_3);
                (v.findViewById(R.id.button2_1)).setVisibility(View.INVISIBLE);
                (v.findViewById(R.id.button2_2)).setVisibility(View.INVISIBLE);
                (v.findViewById(R.id.button2_3)).setVisibility(View.INVISIBLE);
                break;
        }
    }
}
