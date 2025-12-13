package com.BeMusic;

// Class for the BeMusicDatabase, storing users and their friendships.

import java.util.NoSuchElementException;
import java.util.ArrayList;
import java.util.HashMap;

 
public class BeMusicDatabase {
    private int V; // number of users
    private int E; // number of connections across BeMusic
    private HashMap<BeMusicUser, ArrayList<BeMusicUser>> adjList; // adjacency list, keys with users, values with friends

    /**
     * Constructor. Initializes a graph with no users
     *
     * @param  V number of vertices
     */
    public BeMusicDatabase() {
        V = 0;
        E = 0;
        adjList = new HashMap<BeMusicUser, ArrayList<BeMusicUser>>();
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
            adjList.put(v, new ArrayList<BeMusicUser>()); 
        } else {
            StringBuilder sb = new StringBuilder("username ");
            sb.append(v).append(" already taken.");
            String print = sb.toString();
            System.out.println(print);
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
     * Adds the undirected edge v-w to this graph so long as it does not already exist.
     * Synonymous with two users becoming friends.
     *
     * @param  v one vertex in the edge
     * @param  w the other vertex in the edge
     */
    public void addEdge(BeMusicUser v, BeMusicUser w) {
        if (validateVertex(v) && validateVertex(w) && !adjList.get(v).contains(w)){
            E++;
            adjList.get(v).add(w);
            adjList.get(w).add(v);
             // NOTE: do not need to re-put into adjList when adding to exisitng list of friends as ArrayLists are mutable objects
        }
    }

    /**
     * Removes the undirected edge v-w to this graph.
     * Synonymous with two users removing each other.
     * 
     * NOTE: No error is thrown if v and w are not friends first. Just does nothing.
     *
     * @param  v one vertex in the edge
     * @param  w the other vertex in the edge
     */
    public void removeEdge(BeMusicUser v, BeMusicUser w) {
        if (validateVertex(v) && validateVertex(w)){
            E--;
            adjList.get(v).remove(w); // NOTE: do not need to reput since arraylists are mutable
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
            ArrayList<BeMusicUser> friendsClone =  (ArrayList<BeMusicUser>) adjList.get(v).clone();  
            for (BeMusicUser friend:friendsClone){
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
     * Returns a string representation of this graph, based on friendship connections.
     *
     * @return the number of vertices <em>V</em>, followed by the number of edges <em>E</em>,
     *         followed by the <em>V</em> adjacency lists
     */
    public String toString() {
        return adjList.toString();
    }

    /**
     * get the Object User from the database according to a string username
     * aka "logging in"
     * @param username
     * @return bemusicuser corresponding
     */
    public BeMusicUser getUser(String username){
        for (BeMusicUser u : this.adjList.keySet()) {
            if (u.getUsername().equals(username)) {
                return u;
            }
        }
        return null;
        }
    
    // THIS CLASS IS TESTED IN BEMUSICUSER.
}
