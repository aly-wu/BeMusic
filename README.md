# BeMusic

Summary of BeMusic and what it does!
Music is a great peek into a person’s aesthetic, and people often take great pride in the artists they listen to. While music is a great way to connect, music streaming apps like Spotify or Apple Music lack a social aspect. Airbuds, an app that tells you all the songs your friend is listening to, often holds too much information, and the feed is easily bombarded by one person if they are continuously listening to music for any extended period of time. We wanted to create something that allows people to journal snippets of their music listening history and share that information with friends.

Inspired by BeReal, the daily photo journal that goes off at random points in the day, we are making BeMusic. BeMusic will contain two main displays: 1) your personal profile that works as your music journal, and includes your music recaps by month, and 2) your feed, which includes the most recent posts from your friends in chronological order, newest posts on top.

## Demo

[(click here for a video demo of BeMusic)](youtubeLink)
![Music Calendar](image-1.png)

Desciption of image.

![User Feed](image-2.png)

Description of image.

## External Libraries:

We used the SpotifyAPI to take our users song history and get deatils from Spotify, such as track title, artist(s), url to the track's album cover, and the track's "popularity score", which is a Spotify-made score from 0 (not popular) to 100 (very popular).

The OpenCSV library is used to simplfy parsing in data from listening_data.csv to populate our users and their listening histories.

Apache NetBeans is used to format and display the GUI.

These APIs and libraries are managed through the pom.xml file that keeps track of dependencies.

## Use Instructions:

Instructions on how to call your public methods. That is, you are now writing an API in your README. This could be the same thing as your JavaDocs: What is the method called? What are its inputs and outputs? What is a verbal description of what it does? Don’t forget about constructors!

For each of your public methods, also include usage examples. What do expected inputs and outputs look like in practice? For example, a usage example for add(x,y) would be add(2, 3) = 5.


To run the BeMusic application, run the RunBeMusic main class file.

RunBeMusic

    main - Initializes database and loads GUI on Login window.
    

Below is a list of the classes avalaible, their public methods (including usage examples), though to use the intended program, only the RunBeMusic.java file is needed to start the program.

BeMusicDatabase

    Behaves like a normal Graph data structure, able to add and remove vertices and edges.

    getUser(String username) - returns the user associated with the given username (no duplicate usernames are allowed)
        Ex: getUser("pj") returns BeMusicUser("pj")

BeMusicGUI

    Magic, witchcraft, unexplainable

BeMusicUser

    friends() - returns an ArrayList of the users friends.
        Ex: pj.friends() returns [BeMusicUser("alyssa"), BeMusicUser("cris")]

    addFriend(BeMusicUser newFriend) - adds a BeMusicUser object to the adjacency list of the current user.

    removeFriend(BeMusicUser newFriend) - removes a BeMusicUser object to the adjacency list of the current user, if that friend exists.

    addSong(Song song) -

ListeningHistory

    getMonthTopArtist(int month, int year) - returns a user's top artist for a given month and year
        Ex: cris.getMonthTopArtist(11, 2025) returns "Tyler, the Creator"

    getSongHistory(int month, int year) - returns a user's song history for a specified month and year as an ArrayList of Song objects.
        Ex: cris.getSongHistory(11, 2025) returns ["By the Sea by Wendy & Bonnie", "Beautiful Soul by Katy J Pearson", "The Wreck of the Edmund Fitzgerald by Gordon Lightfoot", ...]

    getSongHistory() - returns a user's entire song history as an ArrayList of Song objects.
        Ex: cris.getSongHistory() returns ["By the Sea by Wendy & Bonnie", "Beautiful Soul by Katy J Pearson", "The Wreck of the Edmund Fitzgerald by Gordon Lightfoot", ...]

    addSong(Song song) - adds Song object to listening history, formats the date that the song was added, and adds song's artist(s) to the artists history. Does not return anything.

ReadCSV:

    generateDatabase() - reads in a CSV file, creates users, adds in song and date history. Does not return anything

Song:

    getYear() - returns year as an int.
        Ex: getYear() returns 2025

    getMonth() - returns month as an int.
        Ex: getMonth() returns 12

    getDay() - returns day as an int.
        Ex: getDay() returns 11

    getDate() - returns full date as a String.
        Ex: getDate() returns "12-11-2025"

    getTitle() - returns title of song as a String.
        Ex: getTitle() returns "First Rate Town"

    getArtist() - returns artist of song as a String.
        Ex: getArtist() returns "Good Kid"

    getImageURL() - returns url link to the image of album as a String.
        Ex: getImageURL() returns "https://i.scdn.co/image/ab67616d0000b2739e466f8262ef856bc5b70260"

    getPopularity() - returns Spotify's "popularity score" for the song.
        Ex: getPopularity() returns "47"

    getUser() - returns the user that listened to this instace of the song.
        Ex: getUser() returns "Cris"

    setUser(String user) - sets the user that listened to this instace of the song. Does not return anything.

    compareTo(Song that) - compares two songs based on their date, returning -1 if the song was listened to after 'that' and 1 if it was listened to before.
        new Song(Chapter Six,Kendrick Lamar,12/6/2025).compareTo(new Song(Katana,Samara Cyn,12/5/2025)) returns -1

SearchItemExample:

    search(String search) - Makes a call to the SpotifyAPI to get a song's [title, artist, album cover url, popularity score] as an array of Strings.
        Example: SearchItemExample.search("First Rate Town Good Kid") returns [First Rate Town, Good Kid, https://i.scdn.co/image/ab67616d0000b2739e466f8262ef856bc5b70260, 54]

LoginFrame: 
    Creates the GUI window to 'log in'. Enter a valid username from the preexisting database in order to view that user's timeline.

    bootUp(BeMusicDatabase database) - Initializes Login window when called.  

    loading() - Loads image displayed at Login menu.

    confirmActionPerformed(java.awt.event.ActionEvent evt) - Action that occurs when the 'Confirm' button is clicked. Checks if username is valid. If not, prompts user to enter a different username through a Dialog box. If yes, then opens TimelineFrame window.

TimelineFrame:

    Creates the GUI window for the timeline. 



## Contributors

**PJ James:** \
&emsp; Student at Pomona College, Class of '28 \
&emsp; contact: pjmandelaj@gmail.com | [linkedin](https://www.linkedin.com/in/pj-james-70341533b/)

**Cris Ovalle:** \
&emsp; Student at Pomona College, Class of '28 \
&emsp; contact: @crissovalle2010@gmail.com | [linkedin](linkedin)

**Alyssa Wu:** \
&emsp; Student at Pomona College, Class of '28 \
&emsp; contact: zjwualyssa@gmail.com | [linkedin](https://www.linkedin.com/in/zjwualyssa/)
