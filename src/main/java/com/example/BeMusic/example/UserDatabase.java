package main.java.com.example.BeMusic;

// An interface for a data structure that stores all BeMusic users
// Any class implementing UserDatabase should be implemented as a graph. 
public interface UserDatabase {

    /**
     * Or, add/create new user.
     */
    public void addVertex(BeMusicUser v);

    /**
     * Or, delete user.
     */
    public void removeVertex(BeMusicUser v);
    
}
