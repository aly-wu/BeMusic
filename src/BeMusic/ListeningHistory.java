package BeMusic;

// An interface for a data structure that stores a given BeMusic user's listening history
/**
 * Any objecct that implements listening history should have two underying search trees/dictionaries
 *      1) one for song history with date keys and song values
 *      2) one for artist history with artist keys and number of times listened to said artist values
*/
public interface ListeningHistory {
    public void addSong(String title, String artist, Date date);
}
