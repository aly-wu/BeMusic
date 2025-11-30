package BeMusic;

import java.util.ArrayList;

// An interface for any BeMusic user
public interface User
{
    /**
     * Retrieve the adjacency list for the given user
     */
    public ArrayList<User> friends(UserDatabase allUsers);

    /**
     * Add edge between current user and friend
     * 
     * @pre: friend exists in allUsers database
     */
    public void addFriend(User friend, UserDatabase allUsers); 
    
    /**
     * Remove edge between current user and formerFriend
     * 
     *  @pre: formerFriend exists in allUsers database AND is currently a friend of user 
     */
    public void removeFriend(User formerFriend, UserDatabase allUsers); 

    /**
     * return array list that is in order (most recent til oldest). 
     * this way, to get the last week's listening history, we simply index [0, 6].
     */
    public ArrayList<Song> getSongHistory();

    /**
     * 
     */
    public void addSong(ListeningHistory songHistory);

    /**
     * 
     */
    public ArrayList<String> getTopArtist (ListeningHistory songHistory);


    

}
    