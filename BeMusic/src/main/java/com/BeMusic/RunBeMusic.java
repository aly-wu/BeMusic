package com.BeMusic;

// Generates BeMusic Database and runs BeMusic.

public class RunBeMusic {
    // Instance variables
    
    // Constructor
    public RunBeMusic(){}

    public static void main(String[] args){
        //initializing database
        String testFile = "listening_data_processed.csv";
        BeMusicDatabase database = new BeMusicDatabase();
        ReadCSV r = new ReadCSV(testFile, database);
        database = r.generateDatabase();

        //friendships
        database.getUser("PJ").addFriend(database.getUser("Alyssa"));
        database.getUser("PJ").addFriend(database.getUser("Cris"));
        database.getUser("Alyssa").addFriend(database.getUser("Jo"));
        database.getUser("Alyssa").addFriend(database.getUser("Emma"));
        database.getUser("Emma").addFriend(database.getUser("Ethan"));
        database.getUser("Alyssa").addFriend(database.getUser("Aniyah"));
        database.getUser("Alyssa").addFriend(database.getUser("Andrea"));
        database.getUser("Alyssa").addFriend(database.getUser("Hasseit"));
        database.getUser("Alyssa").addFriend(database.getUser("Vanessa"));
        database.getUser("Hasseit").addFriend(database.getUser("Vanessa"));
        database.getUser("Andrea").addFriend(database.getUser("Aniyah"));
        database.getUser("Cris").addFriend(database.getUser("Nelson"));
        database.getUser("Cris").addFriend(database.getUser("Liz"));
        database.getUser("Cris").addFriend(database.getUser("Tomy"));
        database.getUser("Cris").addFriend(database.getUser("Julia"));
        database.getUser("Cris").addFriend(database.getUser("Carmen"));
        database.getUser("Cris").addFriend(database.getUser("Gwyn"));
        database.getUser("Cris").addFriend(database.getUser("Amri"));
        database.getUser("Julia").addFriend(database.getUser("Carmen"));
        database.getUser("Cris").addFriend(database.getUser("Lili"));
        database.getUser("Lili").addFriend(database.getUser("Amri"));
        database.getUser("Nelson").addFriend(database.getUser("Tomy"));
        database.getUser("PJ").addFriend(database.getUser("Aami"));
        database.getUser("PJ").addFriend(database.getUser("Julia"));
        database.getUser("PJ").addFriend(database.getUser("Carmen"));
        database.getUser("PJ").addFriend(database.getUser("Jaden"));
        database.getUser("PJ").addFriend(database.getUser("Rhea"));
        database.getUser("PJ").addFriend(database.getUser("Kieran"));
        database.getUser("PJ").addFriend(database.getUser("Maela"));
        database.getUser("Maela").addFriend(database.getUser("Kieran"));
        database.getUser("Jaden").addFriend(database.getUser("Aami"));
        database.getUser("Jaden").addFriend(database.getUser("Rhea"));
        

        LoginFrame.bootUp(database);
    }

    
    
    
}
