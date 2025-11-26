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
     */
    public ArrayList<User> friends(UserDatabase allUsers){

    }

    /**
     * Add edge between current user and friend
     * 
     * @pre: friend exists in allUsers database
     */
    public void addFriend(User friend, UserDatabase allUsers){

    }
    
    /**
     * Remove edge between current user and formerFriend
     * 
     *  @pre: formerFriend exists in allUsers database AND is currently a friend of user 
     */
    public void removeFriend(User formerFriend, UserDatabase allUsers){

    }

    /**
     * return array list that is in order (most recent til oldest). 
     * this way, to get the last week's listening history, we simply index [0, 6].
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
     * Determines whether two string pairs are equal based on if they share a username
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
     * Custom hashCode() for hashing StringPair objects
     */
    public int hashCode(){
        // using 31x + y method
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