package com.example.android.mymusicalplayer;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;


public class Songs extends AppCompatActivity {

    String[] artists;
    String[] albums;
    String[] tracks;
    String[] duration;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_songs);

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
        songsTab.setBackgroundColor(ContextCompat.getColor(this, R.color.colorAccent));
        playlistsTab.setBackgroundColor(ContextCompat.getColor(this, R.color.colorPrimaryDark));

        ImageView artistIcon = (ImageView) findViewById(R.id.artists_icon);
        ImageView albumsIcon = (ImageView) findViewById(R.id.albums_icon);
        ImageView songsIcon = (ImageView) findViewById(R.id.songs_icon);
        ImageView playlistsIcon = (ImageView) findViewById(R.id.playlists_icon);

        artistIcon.setImageResource(R.drawable.ic_artists);
        albumsIcon.setImageResource(R.drawable.ic_album);
        songsIcon.setImageResource(R.drawable.ic_songs_selected);
        playlistsIcon.setImageResource(R.drawable.ic_playlist);

        /*----------------------- Add the info description for this activity --------------------*/
        final View infoLayout = findViewById(R.id.info_about_activity);
        TextView info_activity_description = (TextView) infoLayout.findViewById(R.id.activity_info_description);
        info_activity_description.setText(getString(R.string.songs_activity_info));

        //When touch Info description, paragraph will disappear
        infoLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((LinearLayout) findViewById(R.id.root)).removeView(infoLayout);
            }
        });

        /*--------------- call the method for creating the content of this activity --------------*/
        showTracksRow();
    }

    public void createLibrary() {
        // create arrays from resources
        String[] library = getResources().getStringArray(R.array.library);
        artists = new String[library.length];
        albums = new String[library.length];
        tracks = new String[library.length];
        duration = new String[library.length];

        for (int i = 0; i < library.length; i++) {
            artists[i] = library[i].substring(0, library[i].indexOf("/"));
            library[i] = library[i].substring(artists[i].length() + 1);

            albums[i] = library[i].substring(0, library[i].indexOf("/"));
            library[i] = library[i].substring(albums[i].length() + 1);

            tracks[i] = library[i].substring(0, library[i].indexOf("/"));
            library[i] = library[i].substring(tracks[i].length() + 1);

            duration[i] = library[i];
        }

    }

    public void showTracksRow() {
        String makeShift;
        String albumNameResource;

        createLibrary();

        //sorting tracks
        for (int i = 0; i < tracks.length; i++)
            for (int j = i + 1; j < tracks.length; j++)
                if (tracks[i].compareToIgnoreCase(tracks[j]) > 0) {
                    makeShift = tracks[i];
                    tracks[i] = tracks[j];
                    tracks[j] = makeShift;

                    makeShift = artists[i];
                    artists[i] = artists[j];
                    artists[j] = makeShift;

                    makeShift = albums[i];
                    albums[i] = albums[j];
                    albums[j] = makeShift;

                    makeShift = duration[i];
                    duration[i] = duration[j];
                    duration[j] = makeShift;
                }

        LinearLayout rowLayout = (LinearLayout) findViewById(R.id.rowLayout);
        for (int i = 0; i < tracks.length; i++) {
            //inflate layout albums_row.xml
            View row = getLayoutInflater().inflate(R.layout.song_row, null);
            rowLayout.addView(row, i);

            //setting song name on every row
            TextView songName = (TextView) row.findViewById(R.id.song_name);
            songName.setText(tracks[i]);
            //setting artist name on every row
            TextView artistName = (TextView) row.findViewById(R.id.artist_name);
            artistName.setText(artists[i]);
            //setting album name on every row
            TextView albumName = (TextView) row.findViewById(R.id.album_name);
            albumName.setText(albums[i]);
            //setting song duration on every row
            TextView songDuration = (TextView) row.findViewById(R.id.song_duration);
            songDuration.setText(duration[i]);

            albumNameResource = artists[i].replaceAll(" ", "").toLowerCase() + "_" + albums[i].replaceAll(" ", "").toLowerCase();
            final int drawableResourceId = this.getResources().getIdentifier(albumNameResource, "drawable", this.getPackageName());

            //listener on every track and start NowPlaying activity
            final int j = i;
            row.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent nowPlayingIntent = new Intent(Songs.this, NowPlaying.class);
                    nowPlayingIntent.putExtra("ARTIST", artists[j]);
                    nowPlayingIntent.putExtra("ALBUM", albums[j]);
                    nowPlayingIntent.putExtra("SONG", tracks[j]);
                    nowPlayingIntent.putExtra("COVER_ID", drawableResourceId);
                    startActivity(nowPlayingIntent);
                }
            });
        }
    }
}