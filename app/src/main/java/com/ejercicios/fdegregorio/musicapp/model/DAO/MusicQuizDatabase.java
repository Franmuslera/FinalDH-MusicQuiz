package com.ejercicios.fdegregorio.musicapp.model.DAO;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import com.ejercicios.fdegregorio.musicapp.model.POJO.Album;
import com.ejercicios.fdegregorio.musicapp.model.POJO.Artist;
import com.ejercicios.fdegregorio.musicapp.model.POJO.Track;

@Database(entities = {Artist.class, Track.class, Album.class}, version = 1, exportSchema = false)
public abstract class MusicQuizDatabase extends RoomDatabase {

    private static final String DBNAME = "musicquiz_database";
    private static MusicQuizDatabase DBINSTANCE;
    public abstract ArtistDBDAO artistDBDAO();
    public abstract TrackDBDAO trackDBDAO();
    public abstract AlbumDBDAO albumDBDAO();

    public static MusicQuizDatabase getDBINSTANCE(Context context){

        if (DBINSTANCE == null){
            DBINSTANCE = Room.databaseBuilder(context.getApplicationContext(), MusicQuizDatabase.class, DBNAME)
                    .allowMainThreadQueries()
                    .build();
        }

        return DBINSTANCE;
    }
}
