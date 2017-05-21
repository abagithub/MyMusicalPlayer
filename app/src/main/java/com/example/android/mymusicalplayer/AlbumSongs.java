package com.example.android.mymusicalplayer;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

public class AlbumSongs extends AppCompatActivity {
    String[] artists;
    String[] albums;
    String[] tracks;
    String[] duration;
    String artist;
    String album;
    String checkArtist;
    String checkAlbum;
    int arrayLength = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_album_songs);

        Intent songsIntent = getIntent();
        artist = songsIntent.getStringExtra("ARTIST");
        album = songsIntent.getStringExtra("ALBUM");
        ((TextView) findViewById(R.id.album_name)).setText(album + " - album\n" + artist);

         /*----------------------- Add the info description for this activity --------------------*/
        final View infoLayout = findViewById(R.id.info_about_activity);
        TextView info_activity_description = (TextView) infoLayout.findViewById(R.id.activity_info_description);
        info_activity_description.setText(getString(R.string.album_songs_activity_info));

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
        int j = 0;

        String[] library = getResources().getStringArray(R.array.library);

        for (int i = 0; i < library.length; i++) {
            if (library[i].contains(artist + '/' + album))
                arrayLength += 1;
        }

        artists = new String[arrayLength];
        albums = new String[arrayLength];
        tracks = new String[arrayLength];
        duration = new String[arrayLength];

        for (int i = 0; i < library.length; i++) {

            checkArtist = library[i].substring(0, library[i].indexOf("/"));
            library[i] = library[i].substring(checkArtist.length() + 1);
            checkAlbum = library[i].substring(0, library[i].indexOf("/"));

            if (checkArtist.equals(artist) && checkAlbum.equals(album)) {
                artists[j] = checkArtist;
                albums[j] = checkAlbum;
                library[i] = library[i].substring(albums[j].length() + 1);

                tracks[j] = library[i].substring(0, library[i].indexOf("/"));
                library[i] = library[i].substring(tracks[j].length() + 1);

                duration[j] = library[i];
                j++;
            }
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

            final int j = i;
            row.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent nowPlayingIntent = new Intent(AlbumSongs.this, NowPlaying.class);
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
