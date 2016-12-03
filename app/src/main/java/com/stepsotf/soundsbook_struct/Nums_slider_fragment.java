package com.stepsotf.soundsbook_struct;

import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

/**
 * Created by v0090 on 25.11.2016.
 */

public class Nums_slider_fragment extends Fragment {

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
                ((ImageButton)v.findViewById(R.id.button1_1)).setImageResource(R.drawable.num0);
                ((ImageButton)v.findViewById(R.id.button1_2)).setImageResource(R.drawable.num1);
                ((ImageButton)v.findViewById(R.id.button1_3)).setImageResource(R.drawable.num2);
                ((ImageButton)v.findViewById(R.id.button2_1)).setImageResource(R.drawable.num3);
                ((ImageButton)v.findViewById(R.id.button2_2)).setImageResource(R.drawable.num4);
                ((ImageButton)v.findViewById(R.id.button2_3)).setImageResource(R.drawable.num5);
                break;
            case 1:

                ((ImageButton)v.findViewById(R.id.button1_1)).setImageResource(R.drawable.num6);
                ((ImageButton)v.findViewById(R.id.button1_2)).setImageResource(R.drawable.num7);
                ((ImageButton)v.findViewById(R.id.button1_3)).setImageResource(R.drawable.num8);
                ((ImageButton)v.findViewById(R.id.button2_1)).setImageResource(R.drawable.num9);
                ((ImageButton)v.findViewById(R.id.button2_2)).setVisibility(View.INVISIBLE);
                ((ImageButton)v.findViewById(R.id.button2_3)).setVisibility(View.INVISIBLE);
                break;
        }
    }
}
