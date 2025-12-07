package com.example;

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
    Date date; // the same song listened to on different days IS a different song object
    private String[] dateSplit;
    // TODO: other things we may want to store like album cover, nicheness score,
    // etc.

    /**
     * 
     * @param title
     * @param artist
     * @param date
     */
    public Song(String title, String artist, String date) {
        this.title = title;
        this.artist = artist;

        // Date in format from excel as mm/d(d)/yyyy, need to standardize as mm/dd/yyyy
        this.dateSplit = date.split("/");
        String day = dateSplit[1];
        if (day.length() == 1) {
            dateSplit[1] = "0" + day;
        }
        String formattedDate = String.join("-", dateSplit);
        SimpleDateFormat formatter = new SimpleDateFormat("MM-dd-yyyy", Locale.ENGLISH);
        try {
            Date dateObj = formatter.parse(formattedDate);
            this.date = dateObj;
        } catch (ParseException e) {
            System.out.println("error parsing date" + e);
        }
    }

    public int getMonth() {
        return Integer.parseInt(dateSplit[0]);
    }

    public int getDay() {
        return Integer.parseInt(dateSplit[1]);
    }

    public int getYear() {
        return Integer.parseInt(dateSplit[2]);
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
        priorityQueue.add(new Song("EoO", "Bad Bunny", "11/29/2025"));
        priorityQueue.add(new Song("Heroine", "Azamiah", "10/15/2025"));
        priorityQueue.add(new Song("Fall In Love (Your Funeral)", "Erykah Badu", "11/11/2025"));
        priorityQueue.add(new Song("Care for You", "The Marias", "10/31/2025"));

        // Should return songs starting from most recently listened
        while (!priorityQueue.isEmpty()) {
            System.out.print(priorityQueue.poll() + ", ");
        }

        System.out.println("\n-------testing get methods-------");
        Song test = new Song("Waiting In Vain", "Daniel Caesar", "11/29/2025");
        System.out.print("November 29th, 2025...month " + test.getMonth() + ", day " + test.getDay() + ", year "
                + test.getYear());
    }
}
