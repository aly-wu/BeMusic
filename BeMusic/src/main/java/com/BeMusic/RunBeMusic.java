package com.BeMusic;

// Generates BeMusic Database and runs BeMusic.

public class RunBeMusic {
    // Instance variables
    private String csvfile = "listening_data_test.csv"; 
    private BeMusicDatabase database = new BeMusicDatabase();
    ReadCSV r = new ReadCSV(csvfile, database);
    database = r.generateDatabase();
    
    // Constructor
    public RunBeMusic(){
        
    }

    public void 

    loginFrame.bootUp(database);
    
    
}
