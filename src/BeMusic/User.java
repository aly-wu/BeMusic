package BeMusic;

// An interface for any BeMusic user
public interface User 
{
    /**
     * Retrieve the adjacency matrix for the given user
     */
    public ArrayList<User> friends(UserDatabse allUsers);

    /**
     * Add edge between current user and friend
     * 
     * @pre: friend exists in allUsers database
     */
    public void addFriend(User friend, UserDatabse allUsers); 
    
    /**
     * Remove edge between current user and formerFriend
     * 
     *  @pre: formerFriend exists in allUsers database AND is currently a friend of user 
     */
    public void removeFriend(User formerFriend, UserDatabse allUsers); 

    /**
     * return array list that is in order (most recent til oldest). 
     * this way, to get the last week's listening history, we simply index [0, 6].
     */
    public ArrayList<Songs> getSongHistory();

    /**
     * 
     */
    public void addSong(SongHistory songHistory);

    /**
     * 
     */
    public ArrayList<String> getTopArtist (SongHistory songHistory)


    

}
    