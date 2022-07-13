import org.apache.commons.io.IOUtils;
import se.michaelthelin.spotify.SpotifyApi;
import se.michaelthelin.spotify.requests.data.playlists.GetPlaylistRequest;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Objects;

public class SpotifyMain {

    String playlist_name= "Place Holder";
    SpotifyToken token = new SpotifyToken();
    ArrayList<Track> tracks = new ArrayList<>();


    private String freshAccesToken() throws IOException {
        token.get();

        return token.accessToken;
    }

    public ArrayList<Track> fetchPlaylist(String playlist_id) throws IOException {


        URL url = new URL("https://api.spotify.com/v1/playlists/"+playlist_id+"/tracks");
        HttpURLConnection http = (HttpURLConnection)url.openConnection();
        http.setRequestProperty("Authorization", "Bearer " + freshAccesToken());
        http.setRequestProperty("Content-Type", "application/json");
        System.out.println(http.getResponseMessage());


        InputStream in = http.getInputStream();
        String encoding = http.getContentEncoding();
        encoding = encoding == null ? "UTF-8" : encoding;
        String body = IOUtils.toString(in, encoding);

        url = new URL("https://api.spotify.com/v1/playlists/"+playlist_id);
        http = (HttpURLConnection) url.openConnection();
        http.setRequestProperty("Authorization", "Bearer " + freshAccesToken());
        http.setRequestProperty("Content-Type", "application/json");
        System.out.println(http.getResponseMessage());

        in = http.getInputStream();
        encoding = http.getContentEncoding();
        encoding = encoding == null ? "UTF-8" : encoding;
        String body2 = IOUtils.toString(in, encoding);

        playlist_name = playlistName(body2).replace(" ","_");

        http.disconnect();
        return handleBody(body);
    }

    private String playlistName(String body){
        String[] words = body.split("\"");
        for (int i = 0; i<words.length ;i++) {
            if (words[i].contains("name")){
                if (words[i+2].isEmpty()){
                    return "Playlist";
                }
                return words[i+2];
            }
        }
        return "PlaceHolder";
    }

    private ArrayList<Track> handleBody(String body){
        String[] words = body.split("\"");
        int coounter = 0;
        ArrayList<Track> tracks = new ArrayList<>();
        Track temp = new Track();
        for (int i = 0; i<words.length ;i++) {
            if (words[i].equals("artist")) {
                if (!temp.getArtist().contains(words[i-4])) {
                    temp.setArtist(temp.getArtist() + " " + words[i - 4]);
                }
            }
            else if (Objects.equals(words[i], "popularity")){
                temp.setTitle(words[i-2]);
                tracks.add(temp);
                temp.setArtist(temp.getArtist().substring(1));
                temp = new Track();
            }
        }
        return tracks;
    }


}
