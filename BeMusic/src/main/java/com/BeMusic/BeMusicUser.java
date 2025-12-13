package com.BeMusic;

import java.io.BufferedReader;
import java.io.File;

import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
// Class for BeMusic user, storing their username, listening history, and ratinings
import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;
import java.util.Collections;

public class BeMusicUser {
    // TESTING GUI

    // INSTANCE VARIABLES
    private String username;
    private ListeningHistory listeningHistory = new ListeningHistory();
    private BeMusicDatabase allUsers;
    private int totalNichescore = 0;
    private double ratingAggregate;
    private int nbRatings;

    /**
     * Creates a BeMusic user and adds them to the allUsers database
     */
    public BeMusicUser(String username, BeMusicDatabase allUsers) {
        // CONSTRUCTOR
        this.username = username;
        this.allUsers = allUsers;
        allUsers.addVertex(this); // if username already taken, then the user is not added

    }

    /**
     * Retrieve the adjacency list for the given user (aka list of friends)
     */
    public ArrayList<BeMusicUser> friends() {
        return allUsers.adj(this);
    }

    /**
     * Retrieve the degree of given user (aka number of friends)
     */
    public int numFriends() {
        return allUsers.degree(this);
    }

    /**
     * Add edge between current user and friend.
     * No error is thrown if friend does not exist in BeMusicDatabase.
     * 
     * @pre assumes newFriend is in the same BeMusicDatabase
     */
    public void addFriend(BeMusicUser newFriend) {
        allUsers.addEdge(this, newFriend);
    }

    /**
     * Remove edge between current user and formerFriend
     * No error is thrown if friend does not exist in BeMusicDatabase.
     * 
     * @pre assumes newFriend is in the same BeMusicDatabase
     */
    public void removeFriend(BeMusicUser formerFriend) {
        allUsers.removeEdge(this, formerFriend);
    }

    /**
     * Delete vertex from allUsers graph.
     */
    public void deleteAccount() {
        allUsers.removeVertex(this);
    }

    /**
     * get username
     * 
     * @return username
     */
    public String getUsername() {
        return this.username;
    }

    /**
     * You can either get a user's song history for a given month and year
     * 
     * @param month
     * @param year
     * @param chronological
     * @return songHistory for a given month and year
     */
    public ArrayList<Song> getSongHistory(int month, int year) {
        return listeningHistory.getMonthSongHistory(month, year);
    }

    /**
     * Or get a user's entire song history.
     * 
     * @return entire songHistory
     */
    public ArrayList<Song> getSongHistory() {
        return listeningHistory.getSongHistory();
    }

    /**
     * Adds song to listeningHistory + adds username to Song object
     * 
     * @param song
     */
    public void addSong(Song song) {
        totalNichescore += song.getNichness();
        listeningHistory.addSong(song);
        song.setUser(this.username);
    }

    /**
     * Return the top artist for a given month and year
     * 
     * @param month
     * @param year
     * @return
     */
    public List<Entry<String, Integer>> getTopArtist(int month, int year) {
        return listeningHistory.getMonthTopArtist(month, year);
    }

    /**
     * @return user's niche score (0-100)
     */
    public int getNicheScore() {
        return totalNichescore / listeningHistory.getSongHistory().size();
    }

    /**
     * Get's the ENTIRE song histories of all added friends and formats into
     * an ArrayList of String[] with hardcoded order [date, username, song title,
     * artist, image-url, nicheness score]
     * 
     * @return friend's listening history in reverse-chronological order.
     */
    public ArrayList<String[]> getFeed() {
        ArrayList<Song> songs = new ArrayList<Song>(); // to store all of the songs listenend to by all friends
        for (BeMusicUser friend : this.friends()) {
            songs.addAll(friend.getSongHistory());
        }
        Collections.sort(songs); // sort songs in reverse-chronological order

        ArrayList<String[]> feed = new ArrayList<String[]>();
        for (Song song : songs) {
            // Create list entry for each song
            String[] list = new String[6];
            list[0] = song.getDate();
            list[1] = song.getUser();
            list[2] = song.getTitle();
            list[3] = song.getArtist();
            list[4] = song.getImageURL();
            list[5] = song.getNichness() + "";

            // Add list entry to ArrayList feed
            feed.add(list);
        }
        return feed;
    }

    /**
     * Given a
     * 
     * @param month
     * @param year
     * @return a list of size 31 where each index correponds to the image-url of the
     *         song listened to that day
     *         size 32 as max length of a month is 31 (and we need an index of 31)
     */
    public String[] getMusicCalendar(int month, int year) {
        ArrayList<Song> songs = getSongHistory(month, year);
        String[] musicCalendar = new String[32];

        int counter = 0;
        Song song = songs.get(counter); // intialize to get first song in array list
        for (int date = 31; date >= 0; date--) { // remember, songs sorted in REVERSE chronological
            if (song.getDay() == date) {
                musicCalendar[date] = song.getImageURL();
                if (counter != songs.size() - 1) {
                    song = songs.get(++counter); // iterate counter and get next song
                }
            } else {
                musicCalendar[date] = ""; // set to empty string
            }
        }
        return musicCalendar;
    }

    /**
     * Being rated by someone viewing your profile
     * 
     * @param rating
     */
    public void beRated(int rating) {
        if (rating <= 5 && rating >= 0) {
            rate(rating);
        }
    }

    public void rate(int rating) {
        boolean exists = false;

        try {
            Path filePath = Paths.get("ratings.txt");
            List<String> ratingLinesIN = Files.readAllLines(filePath);
            List<String> ratingLinesOUT = new ArrayList<String>();

            for (String line : ratingLinesIN) {
                if (line.equals("")) {
                    break;
                } else {
                    String[] splitLine = line.split(",");
                    String name = splitLine[0];
                    double aggRating = Double.parseDouble(splitLine[1]);
                    int timesRated = Integer.parseInt(splitLine[2]);

                    if (name.equals(username)) {
                        ratingLinesOUT.add(name + "," + (aggRating + rating) + "," + ++timesRated);
                        exists = true;
                    } else {
                        ratingLinesOUT.add(line);
                    }
                }
            }
            if (!exists) {
                ratingLinesOUT.add(this.username + "," + rating + "," + "1");
            }

            Files.write(filePath, ratingLinesOUT);
        } catch (IOException e) {
            System.out.println("File Not Found");
        }
    }

    /**
     * 
     * @return view rating of this user
     *         rounded to 2 decimals
     */
    public double getRating() {
        try {
            Path filePath = Paths.get("ratings.txt");
            List<String> ratingLinesIN = Files.readAllLines(filePath);

            for (String line : ratingLinesIN) {
                if (line.equals("")) {
                    break;
                } else {
                    String[] splitLine = line.split(",");
                    String name = splitLine[0];
                    double aggRating = Double.parseDouble(splitLine[1]);
                    int timesRated = Integer.parseInt(splitLine[2]);

                    if (name.equals(username)) {
                        double scale = Math.pow(10, 2);
                        return Math.round(aggRating / timesRated * scale) / scale;
                    }
                }
            }
        } catch (IOException e) {
            System.out.println("File Not Found");
        }
        return 0.0;
    }

    /**
     * Print user's username
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("@");
        sb.append(username);
        String print = sb.toString();
        return print;
    }

    // FOR HASHING BEMUSIC USERS
    /**
     * Determines whether two BeMusicUsers are equal based on if they share a
     * username
     * 
     * @return boolean, equal or not
     */
    @Override
    public boolean equals(Object that) {
        if (that == this) {
            return true;
        }
        if (that == null) {
            return false;
        }
        if (that.getClass() != this.getClass()) {
            return false;
        }
        BeMusicUser y = (BeMusicUser) that;
        return username.equals(y.username);
    }

    /**
     * Custom hashCode() for hashing BeMusicUser objects
     */
    @Override
    public int hashCode() {
        return username.hashCode(); // hashing the username as a string
    }

    /**
     * Tests the BeMusicUser class.
     *
     * @param args the command-line arguments
     */
    public static void main(String[] args) {
        BeMusicDatabase allUsers = new BeMusicDatabase();

        System.out.println("-------testing constructor-------");
        BeMusicUser cris = new BeMusicUser("cris", allUsers);
        BeMusicUser pj = new BeMusicUser("pj", allUsers);
        BeMusicUser alyssa = new BeMusicUser("alyssa", allUsers);
        System.out.println(allUsers);

        System.out.println("\n-------testing addFriend()-------");
        alyssa.addFriend(pj);
        pj.addFriend(cris);
        cris.addFriend(alyssa);
        System.out.println("everyone is friends: ");
        System.out.println(allUsers);
        System.out.println("alyssa's friends: ");
        System.out.println(alyssa.friends());
        System.out.println("cris's friends: ");
        System.out.println(cris.friends());
        System.out.println("pj's friends: ");
        System.out.println(pj.friends());

        System.out.println("\n-------attempting to add a repeated friend-------");
        pj.addFriend(alyssa);
        System.out.println(allUsers);

        System.out.println("\n-------testing removeFriend()-------");
        alyssa.removeFriend(cris);
        pj.removeFriend(alyssa);
        System.out.println("only cris and pj are friends: ");
        System.out.println(allUsers);

        System.out.println("\n-------testing deleteAccount()-------");
        cris.deleteAccount(); // should remove cris from friend connections as well
        System.out.println("bye bye cris :( ");
        System.out.println(allUsers);

        System.out.println("\n-------testing hashing function-------");
        BeMusicUser pj2 = new BeMusicUser("pj", allUsers); // should not add new user as repeated username
        System.out.println("pj2 ?" + pj2);
        System.out.println(allUsers);

        System.out.println("\n-------testing rating functions-------");
        alyssa.beRated(5);
        alyssa.beRated(4);
        alyssa.beRated(4);
        alyssa.beRated(8); // invalid ratings does nothing
        System.out.println("alyssa's rating: ");
        System.out.println(alyssa.getRating());
        
        
        System.out.println("\n-------adding ListeningHistory to users()-------");
        Song s1 = new Song("EoO", "Bad Bunny", "11/29/2025", "fake url", "47");
        Song s2 = new Song("Heroine", "Azamiah", "11/3/2025", "fake url", "47");
        Song s3 = new Song("Fall In Love (Your Funeral)", "Erykah Badu",
        "11/11/2025", "fake url", "47");
        Song s4 = new Song("Care for You", "The Marias", "10/31/2025", "fake url",
        "47");
        BeMusicUser crisAgain = new BeMusicUser("cris", allUsers);
        alyssa.addSong(s1);
        crisAgain.addSong(s2);
        pj.addSong(s3);
        pj.addSong(s4);
        alyssa.addFriend(crisAgain);
        alyssa.addFriend(pj);
        
        
        System.out.println("\n-------testing getFeed()-------");
        ArrayList<String[]> feed = alyssa.getFeed();
        System.out.println("alyssa's feed...");
        for (String[] post : feed) {
        System.out.println("NEW POST:");
        String string = "";
        for (String info : post) {
        string = string + info + ", ";
        }
        System.out.println(string);
        }
         
        System.out.println("\n-------testing getMusicCalendar()-------");
        pj.addSong(s1);
        pj.addSong(s2);
        String[] calendar = pj.getMusicCalendar(11, 2025);
        int index = 0;
        String string = "";
        for (String entry : calendar) {
        StringBuilder sb = new StringBuilder(string);
        sb.append("index ").append(index).append(": ").append(entry).append(" || ");
        string = sb.toString();
        // string = string + "index " + index + ": " + entry + " || ";
        index++;
        }
        System.out.println("pj's music calendar for november:");
        System.out.println(string);
        
        System.out.println(SearchItemExample.search(pj.listeningHistory.
        getSongHistory().get(0)));
        
        
        System.out.println("\n-------did it read the .csv correctly?-------");
        String testFile = "listening_data_test_processed.csv";
        BeMusicDatabase testDatabase = new BeMusicDatabase();
        ReadCSV r = new ReadCSV(testFile, testDatabase);
        r.generateDatabase();
        /**
         * Since we know alyssa, pj, and cris are in this database, creating new users
         * won't add duplicates, but will function as "logging in" and adding songs to
         * existing users
         */
        System.out.println(testDatabase);

    }
}