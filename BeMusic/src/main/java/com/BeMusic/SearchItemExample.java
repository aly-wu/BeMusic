package com.BeMusic;

import se.michaelthelin.spotify.SpotifyApi;
import se.michaelthelin.spotify.exceptions.SpotifyWebApiException;
import se.michaelthelin.spotify.model_objects.specification.Paging;
import se.michaelthelin.spotify.model_objects.specification.Track;
import se.michaelthelin.spotify.model_objects.specification.Image;
import se.michaelthelin.spotify.requests.data.search.simplified.SearchTracksRequest;
import org.apache.hc.core5.http.ParseException;
import se.michaelthelin.spotify.model_objects.specification.AudioFeatures;
import se.michaelthelin.spotify.requests.data.tracks.GetAudioFeaturesForTrackRequest;

import se.michaelthelin.spotify.model_objects.credentials.ClientCredentials;
import se.michaelthelin.spotify.requests.authorization.client_credentials.ClientCredentialsRequest;

import java.io.IOException;
import java.lang.module.ModuleDescriptor.Builder;
import java.util.ArrayList;
import java.util.concurrent.CancellationException;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionException;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;

import java.io.FileReader;
import java.io.IOException;

public class SearchItemExample {
    // private static final String q = "Abba";

    private static final SpotifyApi spotifyApi = ClientCredentialsExample.clientCredentials_Sync();
    private static SearchTracksRequest searchTracksRequest;

    /**
     * Search for a song on Spotify and return an array of relevant info:
     * [track name, artist name, album cover url, track popularity]
     * 
     * @param searchSong
     * @return String[]
     */
    public static String[] search(Song searchSong) {
        return search(searchSong.getTitle() + " " + searchSong.getArtist());

    }

    /**
     * Search for a song on Spotify and return an array of relevant info:
     * [track name, artist name, album cover url, track popularity]
     * 
     * @param search
     * @return String[]
     */
    public static String[] search(String search) {
        searchTracksRequest = spotifyApi.searchTracks(search)
                // .market(CountryCode.SE)
                .limit(1)
                // .offset(0)
                // .includeExternal("audio")
                .build();

        return searchTracks_Sync();
    }

    /**
     * Search for a track synchronously
     * 
     * @return String[] {track name, artist name, album cover url, track popularity}
     */
    public static String[] searchTracks_Sync() {
        try {
            final Paging<Track> trackPaging = searchTracksRequest.execute();

            Track track = trackPaging.getItems()[0];

            return new String[] { track.getName(), track.getArtists()[0].getName(),
                    track.getAlbum().getImages()[0].getUrl(), "" + track.getPopularity() };

        } catch (IOException | SpotifyWebApiException | ParseException e) {
            System.out.println("Error: " + e.getMessage());
            return null;
        }
    }

    // Not used
    /*
     * public static void searchTracks_Async() {
     * try {
     * final CompletableFuture<Paging<Track>> pagingFuture =
     * searchTracksRequest.executeAsync();
     * 
     * // Thread free to do other tasks...
     * 
     * // Example Only. Never block in production code.
     * final Paging<Track> trackPaging = pagingFuture.join();
     * 
     * // System.out.println("Total: " + trackPaging.getTotal());
     * 
     * // Track track = trackPaging.getItems()[0];
     * // System.out.println("track name: " + track.getName());
     * 
     * // // other features
     * // String albumCoverUrl = track.getAlbum().getImages()[0].getUrl();
     * // System.out.println("image url: " + albumCoverUrl);
     * } catch (CompletionException e) {
     * System.out.println("Error: " + e.getCause().getMessage());
     * } catch (CancellationException e) {
     * System.out.println("Async operation cancelled.");
     * }
     * }
     */

    public static void main(String[] args) {

        ArrayList<String> searches = new ArrayList<>();
        searches.add("4 Leaf Clover Ravyn Lenae, Steve Lacy");
        searches.add("First Rate Town");
        searches.add("MF GROOVE, Smino, Ravyn Lenae");

        for (String search : searches) {
            System.out.println(search(search));
        }
    }
}
