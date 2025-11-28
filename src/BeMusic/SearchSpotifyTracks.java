/******************************************************************************
 * TODO: pulled code from https://github.com/spotify-web-api-java/spotify-web-api-java?tab=readme-ov-file
 * and modified to our wants/needs...
 * 
 * We can also extract features like danceability, energy, loudness, tempo, valence (happiness), key, mode, acousticness
 * instrumentalness, speechiness, etc using spotifyApi.getAudioFeaturesForTrack(track.getId())
 * IDK WHAT THESE LIBRARIES ARE!!!
 ******************************************************************************/

package BeMusic;

import se.michaelthelin.spotify.SpotifyApi;
import se.michaelthelin.spotify.model_objects.credentials.ClientCredentials;
import se.michaelthelin.spotify.requests.authorization.client_credentials.ClientCredentialsRequest;
import se.michaelthelin.spotify.exceptions.SpotifyWebApiException;
import se.michaelthelin.spotify.model_objects.specification.Paging;
import se.michaelthelin.spotify.model_objects.specification.Track;
import se.michaelthelin.spotify.requests.data.search.simplified.SearchTracksRequest;
import org.apache.hc.core5.http.ParseException;

import java.io.IOException;
import java.util.concurrent.CancellationException;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionException;

public class SearchSpotifyTracks {
  // Instance Variables
  private static final String clientId = "687a86ef54ad4707af29922fdd9de9f4"; // TODO: btw this is my actual spotify api...
  private static final String clientSecret = "b5a5d4b4d9bb42f2b26a49b9a95ebb55";
  
  private static final SpotifyApi spotifyApi = new SpotifyApi.Builder()
    .setClientId(clientId)
    .setClientSecret(clientSecret)
    .build();
  
  private String q;
  private SearchTrackRequest searchTracksRequest;


  // Constructor
  public SearchSpotifyTracks(String query){
    this.q = query;

    clientCredentials_Sync(); // get access token

    // Build a search request using the query
    this.searchTracksRequest = spotifyApi.searchTracks(q)
      .limit(1)
      .includeExternal("audio")
      .build();
  }

  /**
   * Get a Spotify Access Token using Client Credentials
   */
  public static void clientCredentials_Sync() {
    ClientCredentialsRequest clientCredentialsRequest = spotifyApi.clientCredentials().build();

    try {
      final ClientCredentials clientCredentials = clientCredentialsRequest.execute();

      // Set access token for further "spotifyApi" object usage
      spotifyApi.setAccessToken(clientCredentials.getAccessToken());

      System.out.println("Expires in: " + clientCredentials.getExpiresIn());
    } catch (IOException | SpotifyWebApiException | ParseException e) {
      System.out.println("Error: " + e.getMessage());
    }
  }

  /**
   * Search for the track, take the first result, and extract desirable track features.
   */
  public static void searchTracks_Sync() {
    try {
      final Paging<Track> trackPaging = searchTracksRequest.execute(); // pages should include only one item as search limit set to 1
      Track track = trackPaging.getItems()[0]; 
      System.out.println("Found Track: " + track.getName());

      // Extract other important features we might want
      track.getAlbum().getImages();
      track.getPreviewUrl();
      track.getPopularity(); // TODO: if interested in the nicheness score


    } catch (IOException | SpotifyWebApiException | ParseException e) {
      System.out.println("Error: " + e.getMessage());
    }
  }

  // TODO: lowkey idt we need Async so delete? i also dont really know the difference...
  public static void searchTracks_Async() {
    try {
      final CompletableFuture<Paging<Track>> pagingFuture = searchTracksRequest.executeAsync();

      // Thread free to do other tasks...

      // Example Only. Never block in production code.
      final Paging<Track> trackPaging = pagingFuture.join();
      Track track = trackPaging.getItems()[0]; 
      System.out.println("Found Track: " + track.getName());

      // TODO: Extract other important features we might want
      track.getAlbum().getImages()[0]; // TODO: i think images have different sizes? do we have a preference
      track.getPreviewUrl();
      track.getPopularity(); // TODO: if interested in the nicheness score

    } catch (CompletionException e) {
      System.out.println("Error: " + e.getCause().getMessage());
    } catch (CancellationException e) {
      System.out.println("Async operation cancelled.");
    }
  }

  public static void main(String[] args) {
    searchTracks_Sync();
    searchTracks_Async();
  }
}