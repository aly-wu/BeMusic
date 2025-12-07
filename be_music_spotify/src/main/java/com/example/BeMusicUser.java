package com.example;

// TODO: complete/rewrite methods for this class 
import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;

public class BeMusicUser {
    // TODO: INSTANCE VARIABLES
    String username; // TODO: final?
    ListeningHistory listeningHistory;
    BeMusicDatabase allUsers;

    /**
     * Creates a BeMusic user and adds them to the allUsers database
     */
    public BeMusicUser(String username, BeMusicDatabase allUsers) {
        // TODO: CONSTRUCTOR
        this.username = username;
        this.allUsers = allUsers;
        allUsers.addVertex(this);
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
     * TODO: No error is thrown if friend does not exist in BeMusicDatabase.
     * 
     * @pre assumes newFriend is in the same BeMusicDatabase
     */
    public void addFriend(BeMusicUser newFriend) {
        allUsers.addEdge(this, newFriend);
    }

    /**
     * Remove edge between current user and formerFriend
     * TODO: No error is thrown if friend does not exist in BeMusicDatabase.
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
     * 
     * @param month
     * @param year
     * @param chronological
     * @return
     */
    public ArrayList<Song> getSongHistory(int month, int year, boolean chronological) {
        return listeningHistory.getMonthSongHistory(month, year, chronological);
    }

    /**
     * 
     * @param song
     */
    public void addSong(Song song) {
        listeningHistory.addSong(song);
    }

    /**
     * 
     * @param month
     * @param year
     * @return
     */
    public List<Entry<String, Integer>> getTopArtist(int month, int year) {
        return listeningHistory.getMonthTopArtist(month, year);
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
     * TODO: TEST!
     *
     * @param args the command-line arguments
     */
    public static void main(String[] args) {
        System.out.println("-------testing constructors-------");
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
    }
}