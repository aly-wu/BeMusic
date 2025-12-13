

/** 
 * Class that preprocesses listening_data_processed.csv permanently.
 * 
 * RUNS ONCE. Reads in out listening_data.csv and makes all the Spotify API calls to obtain 
 * the album cover and nicheness score. Writes them to another csv to be read in to generate 
 * the database 
 * 
 * @author PJ James, Cris Wu, Alyssa Wu
 */


package com.BeMusic;

import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;
import com.opencsv.exceptions.CsvValidationException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ReadCSVPreprocess {
    String filePath;
    String outPath;

    public ReadCSVPreprocess(String filePath, String outPath) {
        this.filePath = filePath;
        this.outPath = outPath;
    }

    public void preprocess() {
        try (CSVReader reader = new CSVReader(new FileReader(filePath))) {
            CSVWriter writer = new CSVWriter(new FileWriter(outPath));

            // adding header to csv
            String[] header = { "Name", "Song Title", "Artist", "Day Listened To", "Image URL", "Nicheness" };
            writer.writeNext(header);

            String[] line; // line being read
            List<String[]> data = new ArrayList<String[]>(); // list of all data to be written
            while ((line = reader.readNext()) != null) {
                // line format = [Username,Song Title,Artist,Day Listened To]
                String username = line[0];
                String title = line[1];
                String artist = line[2];
                String date = line[3];

                // Skip header line
                if (title.equals("Song Title")) {
                    continue;
                } else {
                    // Get Spotify info
                    String[] spotifyInfo = SearchItemExample.search(title, artist);
                    String imageURL = spotifyInfo[2];
                    String nicheness = (100 - Integer.parseInt(spotifyInfo[3])) + ""; // spotify search returns
                                                                                      // popularity, we want nicheness

                    System.out.println("Searched on Spotify API: \"" + title + "\" by " +
                            artist + " with nicheness " + nicheness);

                    // Add line to data
                    data.add(new String[] { username, title, artist, date, imageURL, nicheness });
                }
            }

            writer.writeAll(data);
            // closing writer connection
            writer.close();
        } catch (IOException | CsvValidationException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        System.out.println("\n-------testing preprocess()-------");
        String in = "listening_data_test.csv";
        String out = "listening_data_test_processed.csv";
        ReadCSVPreprocess prep = new ReadCSVPreprocess(in, out);

        prep.preprocess();

        System.out.println("\n-------preprocess() on main data-------");

        String in2 = "listening_data.csv";
        String out2 = "listening_data_processed.csv";
        ReadCSVPreprocess prep2 = new ReadCSVPreprocess(in2, out2);
        prep2.preprocess();
    }
}