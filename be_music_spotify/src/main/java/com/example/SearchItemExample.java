package com.example;

import se.michaelthelin.spotify.SpotifyApi;
import se.michaelthelin.spotify.exceptions.SpotifyWebApiException;
import se.michaelthelin.spotify.model_objects.specification.ArtistSimplified;
import se.michaelthelin.spotify.model_objects.specification.Paging;
import se.michaelthelin.spotify.model_objects.specification.Track;
//import se.michaelthelin.spotify.model_objects.specification.Image;
import se.michaelthelin.spotify.requests.data.search.simplified.SearchTracksRequest;
import org.apache.hc.core5.http.ParseException;
//import se.michaelthelin.spotify.model_objects.specification.AudioFeatures;
//import se.michaelthelin.spotify.requests.data.tracks.GetAudioFeaturesForTrackRequest;

//import se.michaelthelin.spotify.model_objects.credentials.ClientCredentials;
//import se.michaelthelin.spotify.requests.authorization.client_credentials.ClientCredentialsRequest;

import java.io.IOException;
//import java.lang.module.ModuleDescriptor.Builder;
import java.util.concurrent.CancellationException;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionException;

public class SearchItemExample {
    private static String q = "";

    private static final SpotifyApi spotifyApi = ClientCredentialsExample.clientCredentials_Sync();
    private static SearchTracksRequest searchTracksRequest;

    public static void setArtist(String artist) {
        q = artist;
        searchTracksRequest = spotifyApi.searchTracks(q)
                // .market(CountryCode.SE)
                .limit(1)
                // .offset(0)
                // .includeExternal("audio")
                .build();
    }

    public static Song searchSong(String songName) {

        searchTracksRequest = spotifyApi.searchTracks(q).limit(1).build();

        Song song = new Song(songName, songName, songName);

        return null;
    }

    public static void searchTracks_Sync() {
        try {
            final Paging<Track> trackPaging = searchTracksRequest.execute();
            System.out.println("Total: " + trackPaging.getTotal());
            Track track = trackPaging.getItems()[0];
            System.out.println("track name: " + track.getName());

            // other features
            String albumCoverUrl = track.getAlbum().getImages()[0].getUrl();
            System.out.println("image url: " + albumCoverUrl);

            ArtistSimplified[] artists = track.getArtists();
            System.out.println(artists[0].getName());

            Integer popularity = track.getPopularity();
            System.out.println("track popularity: " + popularity);
        } catch (IOException | SpotifyWebApiException | ParseException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    public static void searchTracks_Async() {
        try {
            final CompletableFuture<Paging<Track>> pagingFuture = searchTracksRequest.executeAsync();

            // Thread free to do other tasks...

            // Example Only. Never block in production code.
            // final Paging<Track> trackPaging = pagingFuture.join();

            // System.out.println("Total: " + trackPaging.getTotal());

            // Track track = trackPaging.getItems()[0];
            // System.out.println("track name: " + track.getName());

            // // other features
            // String albumCoverUrl = track.getAlbum().getImages()[0].getUrl();
            // System.out.println("image url: " + albumCoverUrl);
        } catch (CompletionException e) {
            System.out.println("Error: " + e.getCause().getMessage());
        } catch (CancellationException e) {
            System.out.println("Async operation cancelled.");
        }
    }

    public static void main(String[] args) {
        setArtist("Codes and Keys");
        searchTracks_Sync();
        // searchTracks_Async();
    }
}
