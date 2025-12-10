package main.java.com.example.BeMusic;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.time.YearMonth;
import java.util.Map.Entry; // for Map.Entry
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.PriorityQueue;

// An interface for a data structure that stores a given BeMusic user's listening history

/**
 * Two underlying data structures 
 *    1) songHistory: a dictionary with month/year keys and priority queue values of songs sorted by date (most recent song listened to at top of PQ)
 *    2) artistHistory: a nested dictionary with month/year keys and HashMap<String, Integer> values to track number of times listened to a given artist
*/
public class ListeningHistory {
    // Instance variables

    // TODO : add rating!
    // method: rate also in user, all time or 7 days, peek into last 7, 
    //OR: rate just person's profile
    // IMPLEMENT: just add the rating on your profile / avg of all ratings
    HashMap<YearMonth, Queue<Song>> songHistory;
    HashMap<YearMonth, HashMap<String, Integer>> artistHistory;

    // Constructor
    public ListeningHistory(){
        this.songHistory = new HashMap<YearMonth, Queue<Song>>();
        this.artistHistory = new HashMap<YearMonth, HashMap<String, Integer>>();
    }


    /**
     * @pre all songs are distinct objects (since same songs listened on different days are still "different songs")
     * @pre songs with multiple artists assumes artists are comma + space seperated Ex) "Ravyn Lenae, Smino"
     * @param song
     */
    public void addSong(Song song){
        // Extract and format date song was listened to from Date to YearMonth
        YearMonth yyyyMM = YearMonth.of(song.getYear(), song.getMonth());         

        // Add song to songHistory
        if (!songHistory.containsKey(yyyyMM)){ // beginning of a new month
            Queue<Song> pq = new PriorityQueue<>();
            pq.add(song);
            songHistory.put(yyyyMM, pq);
        } else { // mid-month
            Queue<Song> pq = songHistory.get(yyyyMM);
            pq.add(song);
            songHistory.put(yyyyMM, pq);
        }

        // Add song's artist(s) to artistHistory
        String[] artists = song.artist.split(", ");
        if (!artistHistory.containsKey(yyyyMM)){ // beginning of a new month
            HashMap<String, Integer> map = new HashMap<String, Integer>();
            for (String artist:artists){
                if (!map.containsKey(artist)){ // new artists
                    map.put(artist,1);
                } else { // old artist, increment by 1
                    map.put(artist, map.get(artist) + 1);
                }
            }
            artistHistory.put(yyyyMM, map);
        } else { // mid-month
            HashMap<String, Integer> map = artistHistory.get(yyyyMM);
            for (String artist:artists){
                if (!map.containsKey(artist)){ // new artists
                    map.put(artist,1);
                } else { // old artist, increment by 1
                    map.put(artist, map.get(artist) + 1);
                }
            }
            artistHistory.put(yyyyMM, map);
        }
    }

    /**
     * Return the top artist for a given month, year. Includes tied artists.
     * Returns null if there is no info for that given month, year. // TODO: should we throw error instead?
     * 
     * @param month
     * @param year
     * @return
     */
    public List<Entry<String, Integer>> getMonthTopArtist(int month, int year){
        YearMonth yyyyMM = YearMonth.of(year, month);
        HashMap<String, Integer> map = artistHistory.get(yyyyMM);
        
        if (map == null || map.isEmpty()) {return null;}
        
        // Find max listening count
        Entry<String, Integer> maxEntry = Collections.max(map.entrySet(), Comparator.comparing(Entry::getValue));

        // Find possible ties
        List<Entry<String, Integer>> ties = new ArrayList<Entry<String, Integer>>();
        Integer maxValue = maxEntry.getValue();  
        for (Entry<String, Integer> entry : map.entrySet()) {
            if (entry.getValue() == maxValue) {
                ties.add(entry);
            }
        }
        return ties;  
    }

    /**
     * Return the song history for a given month, year.
     * Returns null if there is no info for that given month, year. // TODO: should we throw error instead?
     * @param month
     * @param year
     * @param chronological 
     * // TODO: added chronological parameter for importing data to build the calender,
     * but ideally if adding songs day by day, natural order of reverse chronology is better
     * to add songs day by day to calender + feed  and profile in reverse chronology
     */
    public ArrayList<Song> getMonthSongHistory(int month, int year, boolean chronological){
        YearMonth yyyyMM = YearMonth.of(year,month); 
        Queue<Song> pq = songHistory.get(yyyyMM);
        
        if (pq == null || pq.isEmpty()) {return null;}

        ArrayList<Song> list = new ArrayList<>(pq);
        if (chronological){
            Collections.sort(list, songAscendingOrderComparator);
        } else{
            Collections.sort(list); // "natural ordering" is reverse-chronological
        }
        return list; 
    }

    /**
     * Reverse of the "natural ordering" implemented in Song.java.
     * Now, the beginning of the month is the "smallest" --> returns in chronological order
     */
    Comparator<Song> songAscendingOrderComparator = new Comparator<Song>() {
        @Override
        public int compare(Song s1, Song s2) {
            // If after, then more recent --> "bigger"
            if (s1.date.after(s2.date)){
                return 1;
            
            // If before, then earlier in month --> "smaller"
            } if (s1.date.before(s2.date)){
                return -1;

            // Otherwise, same date
            } return 0;
        }
    };

    /**
     * Tests the ListeningHistory class. 
     *
     * @param args the command-line arguments
     */
    public static void main(String[] args) {
        ListeningHistory test = new ListeningHistory();

        System.out.println("-------testing addSong()-------");
        test.addSong(new Song("EoO", "Bad Bunny", "11/29/2025"));
        test.addSong(new Song("Heroine", "Azamiah", "10/15/2025"));
        test.addSong(new Song("Fall In Love (Your Funeral)", "Erykah Badu", "11/11/2025"));
        test.addSong(new Song("Back in the Day", "Erykah Badu", "11/20/2025"));
        test.addSong(new Song("Care for You", "The Marias", "10/31/2025"));
        test.addSong(new Song("Time Machine", "Willow", "11/13/2025"));
        test.addSong(new Song("Otro Atarfecer", "Bad Bunny, The Marias", "11/23/2025"));
        System.out.println("songHistory: " + test.songHistory);
        System.out.println("artistHistory: " + test.artistHistory);
        
        System.out.println("\n-------testing getMonthSongHistory-------");
        System.out.println("Chronological:" + test.getMonthSongHistory(11, 2025, true));
        System.out.println("Reverse-Chronological:" + test.getMonthSongHistory(11, 2025, false));

        System.out.println("\n-------testing getmonthTopArtist-------");
        System.out.println("Top Artist November: " + test.getMonthTopArtist(11, 2025));
        System.out.println("Top Artist October: " + test.getMonthTopArtist(10, 2025));
    }
}
