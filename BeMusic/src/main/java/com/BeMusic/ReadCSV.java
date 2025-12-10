package com.BeMusic;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;

import java.io.FileReader;
import java.io.IOException;

public class ReadCSV {
    String filePath;
    BeMusicDatabase database;

    public ReadCSV(String filePath, BeMusicDatabase database){
        this.filePath = filePath;
        this.database = database;
    }

    public void generateDatabase(){
        try(CSVReader reader = new CSVReader(new FileReader(filePath))) {
            // Read in first line
            String[] line1 = reader.readNext();
            String currentUsername = line1[0];
            String title = line1[1];
            String artist = line1[2];
            String date = line1[3];

            String[] line;
            BeMusicUser currentUser = new BeMusicUser(currentUsername, database);
            
            while ((line = reader.readNext()) != null) {
                // line format = [Username,Song Title,Artist,Day Listened To]
                String username = line[0];
                title = line[1];
                artist = line[2];
                date = line[3];
            
                if (!username.equals(currentUsername)){ // we are at a new user
                    currentUsername = username; // overwrite currentUsername,
                    currentUser = new BeMusicUser(username, database); // create new user
                    // Add song info from that line
                    System.out.println("new user: " + currentUser);
                    Song song = new Song(title, artist, date);
                    System.out.println("song: " + song);
                    currentUser.addSong(song);
                } else {
                    System.out.println("existing user: " + currentUser);
                    Song song = new Song(title, artist, date);
                    currentUser.addSong(song);
                    System.out.println("song: " + song);
                }
            }


            } catch (IOException | CsvValidationException e) {
                System.err.println(e.getMessage());
            }
        } 

        public static void main(String[] args){
            String testFile = "listening_data_test.csv";
            BeMusicDatabase testDatabase = new BeMusicDatabase();
            ReadCSV r = new ReadCSV(testFile, testDatabase);
            r.generateDatabase();

            System.out.println(testDatabase);

            /** Since we know alyssa, pj, and cris are in this database, creating new users won't add 
             * duplicates, but will function as "logging in" */
            BeMusicUser alyssa = new BeMusicUser("alyssa", testDatabase);
            BeMusicUser pj = new BeMusicUser("pj", testDatabase);
            BeMusicUser cris = new BeMusicUser("cris", testDatabase);

            System.out.println("-------did it read the .csv correctly?-------");
            System.out.println("alyssa's listening history: " + alyssa.listeningHistory);
            System.out.println("pj's listening history: " + pj.listeningHistory);
            System.out.println("cris's listening history: " + cris.listeningHistory);
        }
    }
