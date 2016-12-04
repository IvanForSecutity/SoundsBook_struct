package com.stepsotf.soundsbook_struct;

import android.app.Activity;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import static com.stepsotf.soundsbook_struct.MainActivity.uiOptions;

/**
 * Created by v0090 on 04.12.2016.
 */

public class Next_Lvl_Stub extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.next_stub);
        getWindow().getDecorView().setSystemUiVisibility(uiOptions);
        findViewById(R.id.next_lvl_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                (MediaPlayer.create(Next_Lvl_Stub.this,R.raw.click)).start();
                int lvl = getIntent().getIntExtra("lvl",1);
                int lvl_nums = getIntent().getIntExtra("lvl_nums",0);
                Log.d("sda",""+lvl);
                if(lvl_nums!=0)
                startActivity(new Intent(Next_Lvl_Stub.this,Nums_active.class).putExtra("lvl_nums",lvl_nums));
                else startActivity(new Intent(Next_Lvl_Stub.this,Alph_active.class).putExtra("lvl",lvl));
                finish();
            }
        });
    }
}
