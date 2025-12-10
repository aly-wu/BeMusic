package com.example;

import java.util.HashMap;

public class SpotifySongFullList {
    static HashMap<String, SpotifySong> allSongs = new HashMap<>();;

    public SpotifySongFullList() {

    }

    static void addSong(SpotifySong s) {
        allSongs.put(s.title + " " + s.artist, s);
    }

    static SpotifySong getSong(String title, String artist) {
        return allSongs.get(title + " " + artist);
    }

    public String toString() {
        String out = "";

        return out;

    }

}
