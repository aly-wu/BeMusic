package com.BeMusic;

// Generates BeMusic Database and runs BeMusic.

public class RunBeMusic {
    // Instance variables
    
    // Constructor
    public RunBeMusic(){
        
    }

    public static void main(String[] args){
        String testFile = "listening_data_test_processed.csv";
        BeMusicDatabase database = new BeMusicDatabase();
        ReadCSV r = new ReadCSV(testFile, database);
        database = r.generateDatabase();

        LoginFrame.bootUp(database);
    }

    
    
    
}
