/**
 * @author Pulled from Michael Thelin : https://github.com/spotify-web-api-java/spotify-web-api-java
 * Modified by PJ James, Cris Ovalle, Alyssa Wu.
 */


package com.BeMusic;

import se.michaelthelin.spotify.SpotifyApi;
import se.michaelthelin.spotify.exceptions.SpotifyWebApiException;
import se.michaelthelin.spotify.model_objects.specification.Paging;
import se.michaelthelin.spotify.model_objects.specification.Track;
import se.michaelthelin.spotify.requests.data.search.simplified.SearchTracksRequest;
import org.apache.hc.core5.http.ParseException;
import se.michaelthelin.spotify.model_objects.credentials.ClientCredentials;
import se.michaelthelin.spotify.requests.authorization.client_credentials.ClientCredentialsRequest;
import java.io.IOException;
import java.util.concurrent.CancellationException;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionException;

public class SearchItemExample {
    // private static final String q = "Abba";

    private static final SpotifyApi spotifyApi = ClientCredentialsExample.clientCredentials_Sync();
    private static SearchTracksRequest searchTracksRequest;
    private static String title;
    private static String artist;

    /**
     * Search for a song on Spotify and return an array of relevant info:
     * [track name, artist name, album cover url, track popularity]
     * 
     * @param searchSong
     * @return String[]
     */
    public static String[] search(Song searchSong) {
        // searchTerm = searchSong;
        return search(searchSong.getTitle(), searchSong.getArtist());

    }

    /**
     * Search for a song on Spotify and return an array of relevant info:
     * [track name, artist name, album cover url, track popularity]
     * 
     * @param search
     * @return String[]
     */
    public static String[] search(String t, String a) {
        artist = a;
        title = t;
        searchTracksRequest = spotifyApi.searchTracks(title + " " + artist)
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
    private static String[] searchTracks_Sync() {
        try {
            final Paging<Track> trackPaging = searchTracksRequest.execute();

            if (trackPaging.getItems().length == 0) {
                // System.out.println();
                return new String[] { title, artist,
                        "https://nftcalendar.io/storage/uploads/2022/02/21/image-not-found_0221202211372462137974b6c1a.png",
                        "100" };
            } else {
                Track track = trackPaging.getItems()[0];
                // System.out.println(track.getName());

                return new String[] { track.getName(), track.getArtists()[0].getName(),
                        track.getAlbum().getImages()[0].getUrl(), "" + track.getPopularity() };
                // System.out.println(title);
                // System.out.println(track.getName());
                // System.out.println(artist);
                // System.out.println(track.getArtists()[0].getName());

            }

        } catch (IOException | SpotifyWebApiException |

                ParseException e) {
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

        // ArrayList<String> searches = new ArrayList<>();
        // searches.add("HER");
        search("First Rate Town", "Good Kid");
        // searches.add("MF GROOVE, Smino, Ravyn Lenae");

        // for (String search : searches) {
        // System.out.println(search(search, "MINNIE")[0]);
        // System.out.println(search(search, "Good Kid")[1]);
        // // System.out.println(search(search)[2]);
        // // System.out.println(search(search)[3]);
        // }
    }
}

class ClientCredentialsExample {
    private static final String clientId = "3a93b7c866ce441cb84f000ff0c39f5d";
    private static final String clientSecret = "910d00db4e054d1dadaadabe3859c3e0";

    public static final SpotifyApi spotifyApi = new SpotifyApi.Builder()
            .setClientId(clientId)
            .setClientSecret(clientSecret)
            .build();
    private static final ClientCredentialsRequest clientCredentialsRequest = spotifyApi.clientCredentials()
            .build();

    public static SpotifyApi clientCredentials_Sync() {
        try {
            final ClientCredentials clientCredentials = clientCredentialsRequest.execute();

            // Set access token for further "spotifyApi" object usage
            spotifyApi.setAccessToken(clientCredentials.getAccessToken());

            System.out.println("Expires in: " + clientCredentials.getExpiresIn());
            return spotifyApi;
        } catch (IOException | SpotifyWebApiException | ParseException e) {
            System.out.println("Error: " + e.getMessage());
            return null;
        }
    }

    public static SpotifyApi clientCredentials_Async() {
        try {
            final CompletableFuture<ClientCredentials> clientCredentialsFuture = clientCredentialsRequest
                    .executeAsync();

            // Thread free to do other tasks...

            // Example Only. Never block in production code.
            final ClientCredentials clientCredentials = clientCredentialsFuture.join();

            // Set access token for further "spotifyApi" object usage
            spotifyApi.setAccessToken(clientCredentials.getAccessToken());

            System.out.println("Expires in: " + clientCredentials.getExpiresIn());
            return spotifyApi;
        } catch (CompletionException e) {
            System.out.println("Error: " + e.getCause().getMessage());
            return null;
        } catch (CancellationException e) {
            System.out.println("Async operation cancelled.");
            return null;
        }
    }
}