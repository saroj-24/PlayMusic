package com.example.playmusic;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import java.util.ArrayList;

public class playeractivity<FILE> extends AppCompatActivity {
     ImageView playbutton,nextbutton,prviousbutton,pausebutton,forwarbtn,backwarbtn;
     TextView  txtsongstart,txtsongend,txtsongname;
     SeekBar seebar1;
     ImageView image;
     String songName;

     public static final String EXTRA_NAME = "song_name";
     static MediaPlayer mediaplayer;
     int position;

     ArrayList<FILE> mysongs;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_playeractivity);

        playbutton = findViewById(R.id.playbtn);
        nextbutton = findViewById(R.id.nxtbutton);
        prviousbutton = findViewById(R.id.perviousbutton);
        forwarbtn = findViewById(R.id.btnfastforward);
        backwarbtn = findViewById(R.id.btnfastbackward);

        txtsongname = findViewById(R.id.textsong);
        txtsongstart = findViewById(R.id.textsongstart);
        txtsongend = findViewById(R.id.textsongend);

        seebar1 = findViewById(R.id.seekbar);

        image = findViewById(R.id.imgview);

        if(mediaplayer != null)
        {
            mediaplayer.start();
            mediaplayer.release();
        }
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();

        mysongs = (ArrayList)bundle.getParcelableArrayList("songs");
        String sname = intent.getStringExtra("songname");
        position = bundle.getInt("pos",0);
        txtsongname.setSelected(true);
        Uri uri = Uri.parse(mysongs.get(position).toString());
        songName = mysongs.get(position).toString();
        txtsongname.setText(songName);

        mediaplayer = MediaPlayer.create(getApplicationContext(),uri);
        mediaplayer.start();

        playbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mediaplayer.isPlaying())
                {
                    playbutton.setBackgroundResource(R.drawable.pausebtn);
                    mediaplayer.pause();
                }
                else
                {
                   playbutton.setBackgroundResource(R.drawable.pausebtn);
                   mediaplayer.start();

                    TranslateAnimation moveani = new TranslateAnimation(-25,25,-25,25);
                    moveani.setDuration(900);
                    moveani.setInterpolator(new AccelerateInterpolator());
                    moveani.setFillEnabled(true);
                    moveani.setFillAfter(true);
                    moveani.setRepeatMode(Animation.REVERSE);
                    moveani.setRepeatCount(1);
                    image.startAnimation(moveani);

                }
            }
        });

        nextbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        prviousbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        forwarbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        backwarbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }
}