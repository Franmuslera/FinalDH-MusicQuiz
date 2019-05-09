package com.ejercicios.fdegregorio.musicapp.controller;

import android.media.MediaPlayer;

import java.io.IOException;

public class MediaPlayerController {

    private static MediaPlayer mediaPlayer;

    public static MediaPlayer getMediaPlayer() {

        if (mediaPlayer == null) {
            mediaPlayer = new MediaPlayer();

            mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                @Override
                public void onPrepared(MediaPlayer mp) {
                    mediaPlayer = mp;
                    mediaPlayer.start();
                }
            });
        }

        return mediaPlayer;
    }

    public static MediaPlayer getInstance() {

        if (mediaPlayer == null) {
            mediaPlayer = new MediaPlayer();
        }

        return mediaPlayer;
    }

    public static void playTrack(String url) throws IOException{

        try {
            mediaPlayer.setDataSource(url);
            mediaPlayer.prepareAsync();

        } catch (IOException e) {
            throw new IOException(e.getMessage());
        }
    }

    public static void pauseTrack(){
        mediaPlayer.pause();
    }

    public static void stopTrack(){

        if (mediaPlayer != null){
            mediaPlayer.release();
        }

        mediaPlayer = null;
    }

    public static void resumeTrack() { mediaPlayer.start(); }
}
