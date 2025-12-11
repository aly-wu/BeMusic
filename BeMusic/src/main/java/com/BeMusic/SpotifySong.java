package com.BeMusic;

public class SpotifySong {
    String title;
    String artist;
    String imageURL;
    int popularity;

    public SpotifySong(String t, String a, String i, int p) {
        artist = a;
        title = t;
        imageURL = i;
        popularity = p;
    }

    public String getTitle() {
        return title;
    }

    public String getArtist() {
        return artist;
    }

    public String getImageURL() {
        return imageURL;
    }

    public int getPopularity() {
        return popularity;
    }

    public String toString() {
        String out = "";

        out = "\nSong Name: " + title + " \nArtist: " + artist + " \nImage URL: " + imageURL + " \nPopularity: "
                + popularity;

        return out;
    }

}
