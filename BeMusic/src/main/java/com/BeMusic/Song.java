package com.BeMusic;

/** A song which stores relevant information like title, artist, the day it was listened to, the albumn cover, and its nicheness.
    The same song listened to on different days/by a different person IS a different song object. */

import java.util.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Locale;
import java.util.PriorityQueue;
import java.util.Queue;

public class Song implements Comparable<Song> {
    // Instance variables
    final String title;
    final String artist;
    private Date date;
    private int nicheness;
    private String dateString;
    private String[] dateSplit;
    private String username; // set not when song is created, but when user adds song to their listnening history
    private String imageURL;
  

    /**
     * 
     * @param title
     * @param artist
     * @param date
     */
    public Song(String title, String artist, String date, String imageURL, String nicheness) {

        this.title = title;
        this.artist = artist;
        this.imageURL = imageURL;
        this.nicheness = Integer.parseInt(nicheness);

        // Date in format from excel as mm/d(d)/yyyy, need to standardize as mm/dd/yyyy
        this.dateSplit = date.split("/");
        String day = dateSplit[1];
        if (day.length() == 1) {
            dateSplit[1] = "0" + day;
        }
        String formattedDate = String.join("-", dateSplit);
        this.dateString = formattedDate;
        SimpleDateFormat formatter = new SimpleDateFormat("MM-dd-yyyy", Locale.ENGLISH);
        try {
            Date dateObj = formatter.parse(formattedDate);
            this.date = dateObj;
        } catch (ParseException e) {
            System.out.println("error parsing date" + e);
        }
    }

    /**
     * @return year as int
     */
    public Integer getYear() {
        return Integer.parseInt(dateSplit[2]);
    }

    /**
     * @return month as int
     */
    public Integer getMonth() {
        return Integer.parseInt(dateSplit[0]);
    }

    public Integer getDay() {
        return Integer.parseInt(dateSplit[1]);
    }

    /**
     * @return date as string mm/dd/yyyy
     */
    public String getDate() {
        return dateString;
    }

    /**
     * @return song title
     */
    public String getTitle() {
        return title;
    }

    /**
     * @return song artists
     */
    public String getArtist() {
        return artist;
    }

    /**
     * @return image url of song's album cover
     */
    public String getImageURL() {
        return imageURL;
    }

    /**
     * @return nicheness of song (0-100)
     */
    public int getNichness() {
        return nicheness;
    }

    /**
     * @return username of user who listened to this song
     */
    public String getUser() {
        return username;
    }

    /**
     * Links the username of the user who listened to the song as an object.
     * Needed for keeping track of users when generating feed
     */
    public void setUser(String username) {
        this.username = username;
    }

    /**
     * NOTE: In Java's PriorityQueue with natural ordering/comparator, the head is
     * the smallest (minimal) element
     */
    @Override
    public int compareTo(Song that) {
        // If after, then more recent --> "smaller"
        if (this.date.after(that.date)) {
            return -1;

            // If before, then older --> "bigger"
        }
        if (this.date.before(that.date)) {
            return 1;

            // Otherwise, same date
        }
        return 0;
    }

    /**
     * "Song Title" by Song Artist
     */
    @Override
    public String toString() {
        return "\"" + this.title + "\" by " + this.artist;
    }

    /**
     * Tests the Song class.
     *
     * @param args the command-line arguments
     */
    public static void main(String[] args) {

        System.out.println("-------testing compareTo()-------");
        Queue<Song> priorityQueue = new PriorityQueue<>();

        // insert elements in the queue
        priorityQueue.add(new Song("EoO", "Bad Bunny", "11/29/2025", "fakeurl", "20"));
        priorityQueue.add(new Song("Heroine", "Azamiah", "10/15/2025", "fakeurl", "20"));
        priorityQueue.add(new Song("Fall In Love (Your Funeral)", "Erykah Badu", "11/11/2025", "fakeurl", "20"));
        priorityQueue.add(new Song("Care for You", "The Marias", "10/31/2025", "fakeurl", "20"));

        // Should return songs starting from most recently listened
        while (!priorityQueue.isEmpty()) {
            System.out.print(priorityQueue.poll() + ", ");
        }

        System.out.println("\n-------testing get methods-------");
        Song test = new Song("Waiting In Vain", "Daniel Caesar", "11/29/2025", "fakeurl", "20");
        System.out.print("November 29th, 2025...month " + test.getMonth() + ", day " + test.getDay() + ", year "
                + test.getYear());
    }
}
