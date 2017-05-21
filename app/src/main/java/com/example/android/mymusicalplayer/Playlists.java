package com.example.android.mymusicalplayer;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class Playlists extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_playlists);

        /**************** Listeners on every TabMenu***********************/
        View tabBar = findViewById(R.id.tab_bar);

        LinearLayout artistsTab = (LinearLayout) tabBar.findViewById(R.id.artists_tab);
        artistsTab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent tabIntent = new Intent(getApplicationContext(), MainActivity.class);
                finish();
                startActivity(tabIntent);
            }
        });

        LinearLayout albumsTab = (LinearLayout) tabBar.findViewById(R.id.albums_tab);
        albumsTab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent tabIntent = new Intent(getApplicationContext(), Albums.class);
                finish();
                startActivity(tabIntent);
            }
        });

        LinearLayout songsTab = (LinearLayout) tabBar.findViewById(R.id.songs_tab);
        songsTab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent tabIntent = new Intent(getApplicationContext(), Songs.class);
                finish();
                startActivity(tabIntent);

            }
        });

        LinearLayout playlistsTab = (LinearLayout) tabBar.findViewById(R.id.playlists_tab);
        playlistsTab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent tabIntent = new Intent(getApplicationContext(), Playlists.class);
                finish();
                startActivity(tabIntent);
            }
        });

       /*---------------------------Format Tab Menu------------------------------------------*/

        artistsTab.setBackgroundColor(ContextCompat.getColor(this, R.color.colorPrimaryDark));
        albumsTab.setBackgroundColor(ContextCompat.getColor(this, R.color.colorPrimaryDark));
        songsTab.setBackgroundColor(ContextCompat.getColor(this, R.color.colorPrimaryDark));
        playlistsTab.setBackgroundColor(ContextCompat.getColor(this, R.color.colorAccent));

        ImageView artistIcon = (ImageView) findViewById(R.id.artists_icon);
        ImageView albumsIcon = (ImageView) findViewById(R.id.albums_icon);
        ImageView songsIcon = (ImageView) findViewById(R.id.songs_icon);
        ImageView playlistsIcon = (ImageView) findViewById(R.id.playlists_icon);

        artistIcon.setImageResource(R.drawable.ic_artists);
        albumsIcon.setImageResource(R.drawable.ic_album);
        songsIcon.setImageResource(R.drawable.ic_songs);
        playlistsIcon.setImageResource(R.drawable.ic_playlist_selected);

        /*----------------------- Add the info description for this activity --------------------*/
        final View infoLayout = findViewById(R.id.info_about_activity);
        TextView info_activity_description = (TextView) infoLayout.findViewById(R.id.activity_info_description);
        info_activity_description.setText(getString(R.string.playlists_activity_info));
        //When touch Info description, paragraph will disappear
        infoLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((LinearLayout) findViewById(R.id.root)).removeView(infoLayout);
            }
        });
    }
}