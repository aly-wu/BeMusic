package BeMusic;

import java.util.Date;
import java.util.HashMap;
import java.util.Queue;
import java.util.PriorityQueue;

// An interface for a data structure that stores a given BeMusic user's listening history

/**
 * Two underlying data structures 
 *      1) songHistory: a dictionary with month/year keys and priority queue values of songs sorted by date (most recent song listened to at top of PQ)
 *      2) artistHistory: a dictionary with artist keys and number of times listened to said artist values (every time we add a song, we must search for that artist in their song history --> map to store)
 * 
 * TODO: maybe we keep track of top artist and maxlistens, every day we add a song, we check if that artist's value exceed max listens,
 *      if it does, we change the top artist. this could be expanded to top 3, but would be quite annoying. 
 *      this would work for all time top artist, but what about top artist for the month? 
 *      or, when getting top artist for the month, get the songHistory for the month, iterate through max 30 entries and keep track of artists.
 *      return topArtist
*/
public class ListeningHistory {
    // Instance variables
    HashMap<Date, Queue<Song>> songHistory;
    HashMap<String, Integer> artistHistory;

    // Constructor
    public ListeningHistory(){
        this.songHistory = new HashMap<Date, Queue<Song>>();
        this.artistHistory = new HashMap<String, Integer>();
    }

    /**
     * @pre all songs are distinct objects (since same songs listened on different days are still "different songs")
     * @param song
     */
    public void addSong(Song song, Date month){
        // Add song to songHistory 
        Date MMyyyy = song.date.getMonth() + getYear(); // TODO: figure this out
        if (!songHistory.containsKey(MMyyyy)){ // beginning of a new month
            Queue<Song> pq = new PriorityQueue<>();
            pq.add(song);
            songHistory.put(MMyyyy, pq);
        } else { // mid-month
            Queue<Song> pq = songHistory.get(MMyyyy);
            pq.add(song);
            songHistory.put(MMyyyy, pq);
        }

        // Add song to artistHistory
        String artist = song.artist;
        if (!artistHistory.containsKey(artist)){ // new artists
            artistHistory.put(artist,1);
        } else { // old artist, increase by 1
            artistHistory.put(artist, artistHistory.get(artist) + 1);
        }

    }

}
