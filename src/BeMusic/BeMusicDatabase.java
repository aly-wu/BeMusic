/**
 * TODO: fix formatting of title, also should we maintain normal graph method
 * naming conventions, or change it to correpond to the "add user/remove user"
 */
package BeMusic; 

import java.util.NoSuchElementException;
import java.util.ArrayList;
import java.util.HashMap;

 
public class BeMusicDatabase implements UserDatabase {
    private static final String NEWLINE = System.getProperty("line.separator");

    private int V; // number of users
    private int E; // number of connections across BeMusic
    private HashMap<BeMusicUser, ArrayList<BeMusicUser>> adjList; // adjacency list, keys with users, values with friends
    // TODO: perhaps we do a cusotm hashing thing that hashes BeMusicUsers based on their username, not just if they are distinct objects which all users are

    /**
     * Initializes a graph from a list of users
     * with V vertices (the number of users in the ArrayList) and 0 edges.
     *
     * @param  V number of vertices
     * @throws IllegalArgumentException if list of users is empty
     */
    public BeMusicDatabase(ArrayList<BeMusicUser> users) {
        if (users.isEmpty()){
            throw new IllegalArgumentException("user list is empty");
        }

        V = users.size();
        E = 0;
        adjList = new HashMap<BeMusicUser, ArrayList<BeMusicUser>>();

        for (BeMusicUser user:users) {
            adjList.put(user, null); 
        }
    }

     /**
     * Initializes a graph from a single user
     *
     * @param  V number of vertices
     * @throws IllegalArgumentException if list of users is empty
     */
    public BeMusicDatabase(BeMusicUser user) {
        if (user == null){
            throw new IllegalArgumentException("user is null");
        }

        V = 1;
        E = 0;
        adjList = new HashMap<BeMusicUser, ArrayList<BeMusicUser>>();
        adjList.put(user, null);
    }

    /**
     * Returns the number of vertices in this graph.
     * Synonymous with returning the number of users.
     *
     * @return the number of vertices in this graph
     */
    public int V() {
        return V;
    }

    /**
     * Returns the number of edges in this graph.
     * Synonymous with returning the number of friend connections across BeUser.
     * TODO: we kinda dont need this lol
     *
     * @return the number of edges in this graph
     */
    public int E() {
        return E;
    }

    /**
     * Checks if v is a user in the database. 
     * 
     * @returns true if an existing BeMusic user, false otherwise
     */
    private boolean validateVertex(BeMusicUser v) {
        return adjList.containsKey(v);
        // TODO: must check if it works based on the username hashing!
    }

    /**
     * Adds a vertex that has no adjacency list, making 0 new edges.
     * Synonymous with adding a new user with 0 friends :(
     * 
     * Only adds new user if pre-existing user does not exist ()
     *
     * @param  v vertex to be added
     */
    public void addVertex(BeMusicUser v) {
        if (!validateVertex(v)){
            V++;
            adjList.put(v, null); 
        }   
    }

    /**
     * Adds a vertex that has an adjacency list, making new edges.
     * Synonymous with adding a new user that has existing users as friends.
     * 
     * An edge can only be made if their list of friends exists in the database. Simply 
     * skips friend requests for users not in the database. 
     *
     * @param  v vertex to be added
     * @param  adj adjacency list of vertex
     */
    public void addVertex(BeMusicUser v, ArrayList<BeMusicUser> adj) {
        // If v is not a pre-exisiting user
        if (!validateVertex(v)){
            addVertex(v); // just to add the key and intialize the value as null
            V++;
            ArrayList<BeMusicUser> validFriends = new ArrayList<>();
            // Check that each possible friend to add is a BeMusic user
            for (BeMusicUser friend:adj){
                if (validateVertex(friend)){
                    validFriends.add(friend);
                    addEdge(v, friend);
                }
            }
        }   
    }

    /**
     * Adds the undirected edge v-w to this graph.
     * Synonymous with two users becoming friends.
     *
     * @param  v one vertex in the edge
     * @param  w the other vertex in the edge
     */
    public void addEdge(BeMusicUser v, BeMusicUser w) {
        if (validateVertex(v) && validateVertex(w)){
            E++;
            adjList.get(v).add(w); // TODO: do i need to reput, since arraylists are mutable?
            adjList.get(w).add(v);
        }
    }

    /**
     * Removes the undirected edge v-w to this graph.
     * Synonymous with two users removing each other.
     * 
     * TODO: No error is thrown if v and w are not friends first.  
     *
     * @param  v one vertex in the edge
     * @param  w the other vertex in the edge
     */
    public void removeEdge(BeMusicUser v, BeMusicUser w) {
        if (validateVertex(v) && validateVertex(w)){
            E--;
            adjList.get(v).remove(w); // TODO: do i need to reput, since arraylists are mutable?
            adjList.get(w).remove(v);
        }
    }

    /**
     * Remove a vertex v, removing all edges to v as well. Does nothing if v is not a real user.
     * Synonymous with a user deleting their profile. 
     *
     * @param  v vertex to be removed
     */
    public void removeVertex(BeMusicUser v) {
        // If v is an exisiting user
        if (validateVertex(v)){
            // Remove v from the adj of adjacent vertices
            for (BeMusicUser friend:adjList.get(v)){
                removeEdge(v, friend);
            }
            // Remove v from the adjList of the map
            adjList.remove(v);
            V--;
        }
    }   

    /**
     * Returns the vertices adjacent with vertex v. 
     * Synonymous with getting the list of friends of a given user.
     *
     * @param  v the vertex
     * @return the vertices adjacent with vertex v as an ArrayList.
     */
    public ArrayList<BeMusicUser> adj(BeMusicUser v) {
        return adjList.get(v);
    }

    /**
     * Returns the degree of vertex v. 
     * Synonymous with returning the number of friends og a given user.
     *
     * @param  v the vertex
     * @return the degree of vertex v
     */
    public int degree(BeMusicUser v) {
        return adjList.get(v).size();
    }

    /**
     * Returns a string representation of this graph.
     * TODO: fix toString()
     *
     * @return the number of vertices <em>V</em>, followed by the number of edges <em>E</em>,
     *         followed by the <em>V</em> adjacency lists
     */
    public String toString() {
        StringBuilder s = new StringBuilder();
        s.append(V + " vertices, " + E + " edges " + NEWLINE);
        for (int v = 0; v < V; v++) {
            s.append(v + ": ");
            for (int w : adj[v]) {
                s.append(w + " ");
            }
            s.append(NEWLINE);
        }
        return s.toString();
    }

    /**
     * Tests the BeMusicDatabase class.
     * 
     * TODO: TEST!
     *
     * @param args the command-line arguments
     */
    public static void main(String[] args) {
       
    }

}
