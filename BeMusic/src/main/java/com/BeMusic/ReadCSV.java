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

    /**
     * Reads in .csv where ALL song data of one user is grouped together
     * @return
     */
    public BeMusicDatabase generateDatabase(){
        try(CSVReader reader = new CSVReader(new FileReader(filePath))) {
            // Read in first line
            String[] line1 = reader.readNext();
            String currentUsername = line1[0];
            String title = line1[1];
            String artist = line1[2];
            String date = line1[3];
            String imageURL = line1[4];
            String nicheness = line1[5];

            String[] line;
            BeMusicUser currentUser = new BeMusicUser(currentUsername, database);
            
            while ((line = reader.readNext()) != null) {
                // line format = [Username,Song Title,Artist,Day Listened To]
                String username = line[0];
                title = line[1];
                artist = line[2];
                date = line[3];
                imageURL = line[4];
                nicheness = line[5];
            
                if (!username.equals(currentUsername)){ // we are at a new user
                    currentUsername = username; // overwrite currentUsername,
                    currentUser = new BeMusicUser(username, database); // create new user
                    // Add song info from that line
                    Song song = new Song(title, artist, date, imageURL, nicheness); 
                    currentUser.addSong(song);
    
                } else {
                    Song song = new Song(title, artist, date, imageURL, nicheness);
                    currentUser.addSong(song);
                }
            }


            } catch (IOException | CsvValidationException e) {
                System.err.println(e.getMessage());
            }
            return database;
        } 

        public static void main(String[] args){
            String testFile = "listening_data_test_processed.csv";
            BeMusicDatabase testDatabase = new BeMusicDatabase();
            ReadCSV r = new ReadCSV(testFile, testDatabase);
            r.generateDatabase();

            /** Since we know alyssa, pj, and cris are in this database, creating new users won't add 
             * duplicates, but will function as "logging in" */
       
            System.out.println("-------did it read the .csv correctly?-------");
            System.out.println(testDatabase);
        }
    }
