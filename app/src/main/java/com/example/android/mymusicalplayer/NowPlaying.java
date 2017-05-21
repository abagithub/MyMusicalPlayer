package com.example.android.mymusicalplayer;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class NowPlaying extends AppCompatActivity {

    String artist;
    String album;
    String song;
    int coverId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_now_playing);

        //get the information sent via intent
        Intent songsIntent = getIntent();
        artist = songsIntent.getStringExtra("ARTIST");
        album = songsIntent.getStringExtra("ALBUM");
        song = songsIntent.getStringExtra("SONG");
        coverId = getIntent().getIntExtra("COVER_ID", 0);

        ImageView coverImage = (ImageView) findViewById(R.id.cover_image);
        TextView artistName = (TextView) findViewById(R.id.artist_name);
        TextView songName = (TextView) findViewById(R.id.song_name);
        TextView albumName = (TextView) findViewById(R.id.album_name);

        //sets another album cover. if it is not available shows the default cover
        //covers need to be written like artist_album.extension without any space in order to be showed
        if (coverId > 0) coverImage.setImageResource(coverId);
        artistName.setText(artist);
        albumName.setText(album);
        songName.setText(song);

        /*----------------------- Add the info description for this activity --------------------*/
        final View infoLayout = findViewById(R.id.info_about_activity);
        TextView info_activity_description = (TextView) infoLayout.findViewById(R.id.activity_info_description);
        info_activity_description.setText(getString(R.string.now_playing_activity_info));

        //When touch Info description, paragraph will disappear
        infoLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((LinearLayout) findViewById(R.id.root)).removeView(infoLayout);
            }
        });

        //making some of the buttons more real :)
        final CheckBox surround = (CheckBox) findViewById(R.id.surround);
        surround.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (surround.isChecked())
                    Toast.makeText(NowPlaying.this, getString(R.string.surround_on), Toast.LENGTH_SHORT).show();
                else
                    Toast.makeText(NowPlaying.this, getString(R.string.surround_off), Toast.LENGTH_SHORT).show();
            }
        });

        final CheckBox repeat = (CheckBox) findViewById(R.id.repeat);
        repeat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (repeat.isChecked())
                    Toast.makeText(NowPlaying.this, getString(R.string.repeat_on), Toast.LENGTH_SHORT).show();
                else
                    Toast.makeText(NowPlaying.this, getString(R.string.repeat_off), Toast.LENGTH_SHORT).show();
            }
        });

        final CheckBox play = (CheckBox) findViewById(R.id.play);
        final CheckBox stop = (CheckBox) findViewById(R.id.stop);
        stop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (play.isChecked())
                    play.setChecked(false);
            }
        });
    }
}