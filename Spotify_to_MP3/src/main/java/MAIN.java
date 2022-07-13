import javax.swing.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class MAIN {
    SpotifyMain spotifyMain = new SpotifyMain();
    DownloadVideos downloadVideos = new DownloadVideos();


    public void downloadPlaylist(String playlist_id, String path) throws IOException {
        playlist_id = playlist_id.substring(playlist_id.indexOf("/playlist/")+10,playlist_id.indexOf("?"));
        ArrayList<Track> tracks = spotifyMain.fetchPlaylist(playlist_id);
        path = path + "/" + spotifyMain.playlist_name;
        new File(path).mkdirs();


        for (int i = 0; i< tracks.size(); i++) {
            String id = downloadVideos.search(tracks.get(i).getArtist() + tracks.get(i).getTitle());
            if (id == null){
                continue;
            }
            downloadVideos.download(id, path,tracks.get(i).getArtist() +" "+ tracks.get(i).getTitle());
        }
    }

    public static void main(String[] args) throws IOException {
        new MAIN().downloadPlaylist("https://open.spotify.com/playlist/4IUroLTsXjfZIG7SpoYBZJ?si=9ee72129b1134cbb","/Users/janvanderlinde/Desktop/Musik");
    }

}
