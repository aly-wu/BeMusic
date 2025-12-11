package com.BeMusic;

import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;
import java.util.Collections;

public class BeMusicUser implements User {
    // TESTING GUI
    private Timeline gui;

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
        // TODO: temporarily removed this bc of error in order to work on gui. restore
        // and fix!
    }

    /**
     * Retrieve the adjacency list for the given user (aka list of friends)
     */
    public ArrayList<BeMusicUser> friends() {
        return allUsers.adj(this);
    }

    // get friends listening history method, calls last 2 months of friends
    // listening history

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
        totalNichescore += song.getPopularity();
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
     * a nested ArrayList with harcoded order [date, username, song title, artist,
     * image-url]
     * 
     * @return friend's listening history in reverse-chronological order.
     */
    public ArrayList<ArrayList<String>> getFeed() {
        ArrayList<Song> songs = new ArrayList<Song>(); // to store all of the songs listenend to by all friends
        for (BeMusicUser friend : this.friends()) {
            songs.addAll(friend.getSongHistory());
        }
        Collections.sort(songs); // sort songs in reverse-chronological order

        ArrayList<ArrayList<String>> feed = new ArrayList<ArrayList<String>>();
        for (Song song : songs) {
            // Create list entry for each song
            ArrayList<String> list = new ArrayList<String>();
            list.add(0, song.getDate());
            list.add(1, song.getUser());
            list.add(2, song.getTitle());
            list.add(3, song.getArtist());
            list.add(4, song.getImageURL());

            // Add list entry to ArrayList feed
            feed.add(list);
        }
        return feed;
    }

    /**
     * 
     * being rated by someone viewing your profile
     */
    public void beRated(int rating) {
        if (rating <= 5 && rating >= 0) {
            ratingAggregate = ratingAggregate + rating;
            nbRatings++;
        }
    }

    /**
     * 
     * @return view rating of this user
     *         rounded to 2 decimals
     */
    public double getRating() {
        double scale = Math.pow(10, 2);
        return Math.round(ratingAggregate / nbRatings * scale) / scale;

    }

    /**
     * Print user's username
     */
    @Override
    public String toString() {
        return "@" + username;
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
        System.out.println("everyone is friends: " + allUsers);
        System.out.println("alyssa's friends: " + alyssa.friends());
        System.out.println("cris's friends: " + cris.friends());
        System.out.println("pj's friends: " + pj.friends());

        System.out.println("\n-------attempting to add a repeated friend-------");
        pj.addFriend(alyssa);
        System.out.println(allUsers);

        System.out.println("\n-------testing removeFriend()-------");
        alyssa.removeFriend(cris);
        pj.removeFriend(alyssa);
        System.out.println("only cris and pj are friends: " + allUsers);

        System.out.println("\n-------testing deleteAccount()-------");
        cris.deleteAccount(); // should remove cris from friend connections as well
        System.out.println("bye bye cris :( " + allUsers);

        System.out.println("\n-------testing hashing function-------");
        BeMusicUser pj2 = new BeMusicUser("pj", allUsers); // should not add new user as repeated username
        System.out.println(allUsers);

        System.out.println("\n-------testing rating functions-------");
        alyssa.beRated(5);
        alyssa.beRated(4);
        alyssa.beRated(4);
        alyssa.beRated(8); // invalid ratings do nothing
        System.out.println("alyssa's rating: " + alyssa.getRating());

        System.out.println("\n-------adding ListeningHistory to users()-------");
        System.out.println("SORRY I TEMPORARILY DISABLED THIS BC IT WASNT WORKING AND I NEED TO TEST GUI");
        Song s1 = new Song("EoO", "Bad Bunny", "11/29/2025");
        Song s2 = new Song("Heroine", "Azamiah", "11/3/2025");
        Song s3 = new Song("Fall In Love (Your Funeral)", "Erykah Badu", "11/11/2025");
        Song s4 = new Song("Care for You", "The Marias", "10/31/2025");
        BeMusicUser crisAgain = new BeMusicUser("cris", allUsers);
        alyssa.addSong(s1);
        crisAgain.addSong(s2);
        pj.addSong(s3);
        pj.addSong(s4);
        alyssa.addFriend(crisAgain);
        alyssa.addFriend(pj);

        System.out.println("\n-------testing getFeed()-------");
        System.out.println("alyssa's feed: " + alyssa.getFeed());

        new BeMusicUser("guitest", allUsers).run();
        System.out.println("called run");

        // System.out.println(SearchItemExample.search(pj.listeningHistory.getSongHistory().get(0)));

    }

    /*
     * Initializes the screen. TESTING GUI?
     */
    public void run() {

        gui = new Timeline();
        System.out.println("running timeline?");
    }
}