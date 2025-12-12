/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package com.BeMusic;

import java.util.ArrayList;

/**
 *
 * @author pjman
 */
public class SongHistorytemp {
    

    public ArrayList<String[]> getHistory() {
        return history;
    }

    public void setHistory(ArrayList<String[]> history) {
        this.history = history;
    }
    
    public String getCurrentDate() {
        return currentDate;
    }

    public void setCurrentDate(String currentDate) {
        this.currentDate = currentDate;
    }

    public String getCurrentUsername() {
        return currentUsername;
    }

    public void setCurrentUsername(String currentUsername) {
        this.currentUsername = currentUsername;
    }

    public String getCurrentSongTitle() {
        return currentSongTitle;
    }

    public void setCurrentSongTitle(String currentSongTitle) {
        this.currentSongTitle = currentSongTitle;
    }

    public String getCurrentArtist() {
        return currentArtist;
    }

    public void setCurrentArtist(String currentArtist) {
        this.currentArtist = currentArtist;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
    
    /*
    public void addEntry(String date, String username, String songtitle, String artist, String url){
        String[] entry = {date, username,songtitle,artist,url};
        history.add(entry);
    }
*/
    

    public SongHistorytemp() {
        String[] song1 = {"2025-12-09", "PJ", "Heartbreak", "Minho", "url"};
        String[] song2 = {"2025-12-09", "Alyssa", "Chapter Six", "Kendrick Lamar", "url"};
        String[] song3 = {"2025-12-09", "Cris", "Get Used to It", "Ricky Montgomery", "url"};
    }
    
    public static void main(String[] args){
        SongHistorytemp songhistory = new SongHistorytemp();
        System.out.println(songhistory.history);
    }
    

    //instance vars
    private ArrayList<String[]> history = new ArrayList<String[]>();
    private String currentDate;  //index 0
    private String currentUsername; //index 1
    private String currentSongTitle; //index 2
    private String currentArtist; // index 3
    private String imageUrl; // index 4
    
    
}
