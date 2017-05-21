package com.example.android.mymusicalplayer;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.Arrays;
import java.util.HashSet;

public class MainActivity extends AppCompatActivity {

    String[] artists;
    String[] albums;
    String[] tracks;
    String[] duration;
    String[] artistsList;
    String[] albumsList;
    LinearLayout layoutAlbumRows;
    Boolean layoutCreated = false;
    Boolean arrowChecked;
    CheckBox[] arrow;
    int arrowId;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        /**************** Listeners on every TabMenu***********************/
        View tabBar = findViewById(R.id.tab_bar);
        LinearLayout albumsTab = (LinearLayout) tabBar.findViewById(R.id.albums_tab);
        albumsTab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent tabIntent = new Intent(getApplicationContext(), Albums.class);
                finish();
                startActivity(tabIntent);
            }
        });

        LinearLayout artistsTab = (LinearLayout) tabBar.findViewById(R.id.artists_tab);
        artistsTab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

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

        artistsTab.setBackgroundColor(ContextCompat.getColor(this, R.color.colorAccent));
        albumsTab.setBackgroundColor(ContextCompat.getColor(this, R.color.colorPrimaryDark));
        songsTab.setBackgroundColor(ContextCompat.getColor(this, R.color.colorPrimaryDark));
        playlistsTab.setBackgroundColor(ContextCompat.getColor(this, R.color.colorPrimaryDark));

        ImageView artistIcon = (ImageView) findViewById(R.id.artists_icon);
        ImageView albumsIcon = (ImageView) findViewById(R.id.albums_icon);
        ImageView songsIcon = (ImageView) findViewById(R.id.songs_icon);
        ImageView playlistsIcon = (ImageView) findViewById(R.id.playlists_icon);

        artistIcon.setImageResource(R.drawable.ic_artists_selected);
        albumsIcon.setImageResource(R.drawable.ic_album);
        songsIcon.setImageResource(R.drawable.ic_songs);
        playlistsIcon.setImageResource(R.drawable.ic_playlist);

        /*----------------------- Add the info description for this activity --------------------*/
        final View infoLayout = findViewById(R.id.info_about_activity);
        TextView info_activity_description = (TextView) infoLayout.findViewById(R.id.activity_info_description);
        info_activity_description.setText(getString(R.string.artists_activity_info));

        //When touch Info description, paragraph will disappear
        infoLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((LinearLayout) findViewById(R.id.root)).removeView(infoLayout);
            }
        });

        /*--------------- call the method for creating the content of this activity --------------*/
        showArtistsRows();
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

        /*-------------------------------------------------------------------------------
        create the artist list by selecting only different names
        this is the hard way. i didn't knew there is an much easier way
         */
        String[] extractArtist = new String[artists.length];
        extractArtist[0] = artists[0];
        int j = 1;
        boolean artistNew = false;

        for (int i = 1; i < artists.length; i++) {
            for (int k = 0; k < j; k++)
                if (!artists[i].equals(extractArtist[k])) artistNew = true;
                else artistNew = false;
            if (artistNew) {
                extractArtist[j] = artists[i];
                j++;
            }
        }
        //remove null elements by creating artistList array without null elements
        artistsList = new String[j];
        for (int i = 0; i < j; i++) {
            artistsList[i] = extractArtist[i];
        }
        Arrays.sort(artistsList);

        /*----------------------------------------------------------------------------
        create the album List in the easy way
         */
        albumsList = new HashSet<>(Arrays.asList(albums)).toArray(new String[0]);
        Arrays.sort(albumsList);


    }

    public void showArtistsRows() {
        int totalSongs;
        int totalAlbums;
        final LinearLayout rowLayout;

        createLibrary();

        arrow = new CheckBox[artistsList.length];

        rowLayout = (LinearLayout) findViewById(R.id.rowLayout);
        for (int i = 0; i < artistsList.length; i++) {
            //inflate layout artist_row.xml
            View rowArtist = getLayoutInflater().inflate(R.layout.artist_row, null);
            rowLayout.addView(rowArtist, i);

            //setting artist name on every row
            TextView artistName = (TextView) rowArtist.findViewById(R.id.artist_name);
            artistName.setText(artistsList[i]);

            //counting and setting total albums of artist
            totalAlbums = 0;
            for (int j = 0; j < albumsList.length; j++)
                for (int k = 0; k < albums.length; k++)
                    if (albumsList[j].equals(albums[k]))
                        if (artistsList[i].equals(artists[k])) {
                            totalAlbums += 1;
                            break;
                        }

            TextView totalAlbumsView = (TextView) rowArtist.findViewById(R.id.total_albums);
            totalAlbumsView.setText(totalAlbums + " albums");

            //counting and setting total songs of artist
            totalSongs = 0;
            for (int j = 0; j < artists.length; j++)
                if (artistsList[i].equals(artists[j])) totalSongs += 1;

            TextView totalSongsView = (TextView) rowArtist.findViewById(R.id.total_songs);
            totalSongsView.setText(totalSongs + " songs");

            /*-----------Creating a connection between artist, his albums and songs------------- */
            arrow[i] = (CheckBox) rowArtist.findViewById(R.id.arrow);
            arrow[i].setOnCheckedChangeListener(arrowListener);

            final int iPosition = i;

            arrow[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (arrowChecked) {
                        if (layoutCreated) {
                            //remove the old layout when another artist is checked
                            ((CheckBox) findViewById(arrowId)).setChecked(false);
                            rowLayout.removeView(layoutAlbumRows);
                        }
                        arrowId = iPosition;
                        arrow[iPosition].setId(arrowId);

                        //create new layout for albums rows
                        layoutAlbumRows = new LinearLayout(MainActivity.this);
                        layoutAlbumRows.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                                LinearLayout.LayoutParams.WRAP_CONTENT));
                        layoutAlbumRows.setOrientation(LinearLayout.VERTICAL);
                        rowLayout.addView(layoutAlbumRows, iPosition + 1);
                        layoutCreated = true;

                        int rowPosition = 0;
                        //setting albums of artist
                        for (int j = 0; j < albumsList.length; j++)
                            for (int k = 0; k < albums.length; k++)
                                if (albumsList[j].equals(albums[k]))
                                    if (artistsList[iPosition].equals(artists[k])) {

                                        //inflate layout albums_row.xml
                                        View rowAlbum = getLayoutInflater().inflate(R.layout.albums_row, null);
                                        layoutAlbumRows.addView(rowAlbum, rowPosition);
                                        rowPosition += 1;

                                        //sets album picture on every row
                                        ImageView albumPicture = (ImageView) rowAlbum.findViewById(R.id.cover_image);

                                        String albumNameResource = artistsList[iPosition].replaceAll(" ", "").toLowerCase() + "_" + albumsList[j].replaceAll(" ", "").toLowerCase();
                                        int drawableResourceId = MainActivity.this.getResources().getIdentifier(albumNameResource, "drawable", MainActivity.this.getPackageName());
                                        if (drawableResourceId > 0)
                                            albumPicture.setImageResource(drawableResourceId);

                                        //setting album name on every row
                                        final TextView albumName = (TextView) rowAlbum.findViewById(R.id.albums_name);
                                        albumName.setText(albumsList[j]);

                                        //setting artist name on every row
                                        final TextView artistName = (TextView) rowAlbum.findViewById(R.id.artist_name);
                                        artistName.setText(artistsList[iPosition]);

                                        //listener for every album row that sends to AlbumSongs activity
                                        rowAlbum.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View v) {
                                                Intent albumSongsIntent = new Intent(MainActivity.this, AlbumSongs.class);
                                                albumSongsIntent.putExtra("ARTIST", artistName.getText());
                                                albumSongsIntent.putExtra("ALBUM", albumName.getText());
                                                startActivity(albumSongsIntent);
                                            }
                                        });
                                        break;
                                    }

                    } else {
                        rowLayout.removeView(layoutAlbumRows);
                        layoutCreated = false;
                    }
                }
            });
        }
    }

    //i did this because it can't be done inside the OnClickListener
    private CompoundButton.OnCheckedChangeListener arrowListener = new CompoundButton.OnCheckedChangeListener() {
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            if (isChecked) {
                arrowChecked = true;
            } else arrowChecked = false;
        }
    };

}
