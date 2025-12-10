package com.BeMusic;

import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;

// An interface for any BeMusic user
public interface User
{
    /**
     * Retrieve the adjacency list for the given user
     */
    public ArrayList<BeMusicUser> friends();

    /**
     * Add edge between current user and friend
     * 
     * @pre: friend exists in allUsers database
     */
    public void addFriend(BeMusicUser newFriend); 
    
    /**
     * Remove edge between current user and formerFriend
     * 
     *  @pre: formerFriend exists in allUsers database AND is currently a friend of user 
     */
    public void removeFriend(BeMusicUser formerFriend); 

    /**
     * return array list that is in order (most recent til oldest). 
     * this way, to get the last week's listening history, we simply index [0, 6].
     */
    public ArrayList<Song> getSongHistory(int month, int year, boolean chronological);

    /**
     * 
     */
    public void addSong(Song song);

    /**
     * 
     */
    public List<Entry<String, Integer>> getTopArtist (int month, int year);


    

}
    