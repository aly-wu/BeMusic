package com.example;

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
            String[] line;
            String currentUsername;
            BeMusicUser currentUser;
            
            while ((line = reader.readNext()) != null) {
                // line format = [Username,Song Title,Artist,Day Listened To]
                String username = line[0];
                String title = line[1];
                String artist = line[2];
                String date = line[3];
            
                if (!username.equals(currentUsername)){ // we are at a new user
                    currentUsername = username; // overwrite currentUsername,
                    currentUser = new BeMusicUser(username, currentUser); // create new user
                    // Add song info from that line
                    Song song = new Song(title, artist, date);
                    currentUser.addSong(song);
                } else {
                    Song song = new Song(title, artist, date);
                    currentUser.addSong(song);
                }


                BeMusicUser user = new BeMusicUser(username, database);
                Song song = new Song(title, artist, date);

                }
                System.out.println();
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
            BeMusicUser pj = new BeMusicUser("alyssa", testDatabase);
            BeMusicUser cris = new BeMusicUser("alyssa", testDatabase);

            System.out.println("-------did it read the .csv correctly?-------");
            System.out.printlnt("alyssa's listening history: " + alyssa.listeningHistory);
            System.out.printlnt("pj's listening history: " + pj.listeningHistory);
            System.out.printlnt("cris's listening history: " + cris.listeningHistory);
        }
    }
