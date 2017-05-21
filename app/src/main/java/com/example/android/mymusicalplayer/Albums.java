package com.example.android.mymusicalplayer;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.Arrays;
import java.util.HashSet;

public class Albums extends AppCompatActivity {

    String[] artists;
    String[] albums;
    String[] tracks;
    String[] duration;
    String[] albumsList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_albums);

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
        albumsTab.setBackgroundColor(ContextCompat.getColor(this, R.color.colorAccent));
        songsTab.setBackgroundColor(ContextCompat.getColor(this, R.color.colorPrimaryDark));
        playlistsTab.setBackgroundColor(ContextCompat.getColor(this, R.color.colorPrimaryDark));

        ImageView artistIcon = (ImageView) findViewById(R.id.artists_icon);
        ImageView albumsIcon = (ImageView) findViewById(R.id.albums_icon);
        ImageView songsIcon = (ImageView) findViewById(R.id.songs_icon);
        ImageView playlistsIcon = (ImageView) findViewById(R.id.playlists_icon);

        artistIcon.setImageResource(R.drawable.ic_artists);
        albumsIcon.setImageResource(R.drawable.ic_album_selected);
        songsIcon.setImageResource(R.drawable.ic_songs);
        playlistsIcon.setImageResource(R.drawable.ic_playlist);

        /*----------------------- Add the info description for this activity --------------------*/
        final View infoLayout = findViewById(R.id.info_about_activity);
        TextView info_activity_description = (TextView) infoLayout.findViewById(R.id.activity_info_description);
        info_activity_description.setText(getString(R.string.albums_activity_info));

        //When touch Info description, paragraph will disappear
        infoLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((LinearLayout) findViewById(R.id.root)).removeView(infoLayout);
            }
        });

        /*--------------- call the method for creating the content of this activity --------------*/
        showAlbumsRow();
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

        /*----------------------------------------------------------------------------
        creates the album List by selecting only different names
         */
        albumsList = new HashSet<>(Arrays.asList(albums)).toArray(new String[0]);
        Arrays.sort(albumsList);
    }

    public void showAlbumsRow() {
        String albumNameResource = "";
        int drawableResourceId;

        createLibrary();

        LinearLayout rowLayout = (LinearLayout) findViewById(R.id.rowLayout);
        for (int i = 0; i < albumsList.length; i++) {
            //inflate layout albums_row.xml
            View row = getLayoutInflater().inflate(R.layout.albums_row, null);
            rowLayout.addView(row, i);

            //sets album picture on every row
            ImageView albumPicture = (ImageView) row.findViewById(R.id.cover_image);
            for (int j = 0; j < albums.length; j++)
                if (albums[j].equals(albumsList[i])) {
                    albumNameResource = artists[j].replaceAll(" ", "").toLowerCase() + "_" + albumsList[i].replaceAll(" ", "").toLowerCase();
                    break;
                }
            drawableResourceId = this.getResources().getIdentifier(albumNameResource, "drawable", this.getPackageName());
            if (drawableResourceId > 0) albumPicture.setImageResource(drawableResourceId);

            //setting album name on every row
            TextView albumName = (TextView) row.findViewById(R.id.albums_name);
            albumName.setText(albumsList[i]);
            //setting artist name on every row
            final TextView artistName = (TextView) row.findViewById(R.id.artist_name);
            for (int j = 0; j < albums.length; j++)
                if (albumsList[i].equals(albums[j])) {
                    artistName.setText(artists[j]);
                    break;
                }
            //sets clicklistener on every row and sends artist and album name to AlbumSongs activity
            final int k = i;
            row.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent albumSongsIntent = new Intent(Albums.this, AlbumSongs.class);
                    albumSongsIntent.putExtra("ARTIST", artistName.getText());
                    albumSongsIntent.putExtra("ALBUM", albumsList[k]);
                    startActivity(albumSongsIntent);
                }
            });
        }
    }
}