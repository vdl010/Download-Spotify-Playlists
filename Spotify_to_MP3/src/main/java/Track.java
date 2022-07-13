public class Track {
    private String artist ="";
    private String Title="";
    private String Album="";
    private String popularity="";

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public void setAlbum(String album) {
        Album = album;
    }

    public void setPopularity(String popularity) {
        this.popularity = popularity;
    }

    public Track() {
    }

    public String getArtist() {
        return artist;
    }

    public String getTitle() {
        return Title;
    }

    public String getAlbum() {
        return Album;
    }
}
