package com.example;

import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
        // ArrayList<String> searches = new ArrayList<>();
        // SpotifySongFullList list = new SpotifySongFullList();
        String testFile = "be_music\\src\\main\\java\\com\\example\\listening_data.csv";
        BeMusicDatabase testDatabase = new BeMusicDatabase();
        ReadCSV r = new ReadCSV(testFile, testDatabase);
        testDatabase = r.generateDatabase();

        BeMusicUser user = testDatabase.getUser("Liz");

        ArrayList<Song> hist = user.getSongHistory(11, 2025, true);
        for (Song song : hist) {
            System.out.println(SearchItemExample.search(song.title + " " + song.artist));
        }

    }
}