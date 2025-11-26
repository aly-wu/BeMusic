package BeMusic;

import java.util.ArrayList;

public class BeMusicUser implements User {
    // TODO: INSTANCE VARIABLES
    public String username; // TODO: final? 
    public ListeningHistory songHistory;
    
    /**
     * Initializes a BeMusic user with 
     */
    public BeMusicUser(String username){
        // TODO: CONSTRUCTOR
        this.username = username;
    }
   
    /**
     * Retrieve the adjacency list for the given user
     * 
     * @pre this is in allUsers
     */
    public ArrayList<BeMusicUser> friends(BeMusicDatabase allUsers){
        return allUsers.adj(this);
    }

    /**
     * Add edge between current user and friend.
     * 
     * TODO: No error is thrown if friend or user does not exist in BeMusicDatabase.
     */
    public void addFriend(BeMusicUser newFriend, BeMusicDatabase allUsers){
        allUsers.addEdge(this, newFriend);
    }
    
    /**
     * Remove edge between current user and formerFriend
     * TODO: No error is thrown if friend or user does not exist in BeMusicDatabase.
     */
    public void removeFriend(BeMusicUser formerFriend, BeMusicDatabase allUsers){
        allUsers.removeEdge(this, formerFriend);
    }

    /**
     * Return array list that is in order (most recent til oldest). 
     * This way, to get the last week's listening history, we simply get the first element by .get(0)
     */
    public ArrayList<Song> getSongHistory(){

    }

    /**
     * 
     */
    public void addSong(ListeningHistory songHistory){

    }

    /**
     * 
     */
    public ArrayList<String> getTopArtist (ListeningHistory songHistory){

    }

    // FOR HASHING BEMUSIC USERS
    /**
     * Determines whether two BeMusicUsers are equal based on if they share a username
     * @return boolean, equal or not
     */
    public boolean equals(Object that){
        if (that == this){return true;}
        if (that == null){return false;}
        if (that.getClass() != this.getClass()){return false;}
        BeMusicUser y = (BeMusicUser) that;
        return username.equals(y.username);
    }

    /**
     * Custom hashCode() for hashing BeMusicUser objects
     */
    public int hashCode(){
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
       
    }


}