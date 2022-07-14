import com.google.gson.JsonParser;
import org.apache.commons.io.IOUtils;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

    public class SpotifyToken {
        public String accessToken = "";
        public String expiresIn = "";


        public void get() throws IOException{
            URL url = new URL("https://accounts.spotify.com/api/token");
            HttpURLConnection http = (HttpURLConnection)url.openConnection();
            http.setRequestMethod("POST");
            http.setDoOutput(true);
            http.setRequestProperty("content-type", "application/x-www-form-urlencoded");



            String data = "grant_type=client_credentials&client_id=ab4794f633e34c7c856667bfb4101592&client_secret=bafe3e4bfc7443fe84105423aa52cb21";

            byte[] out = data.getBytes(StandardCharsets.UTF_8);

            OutputStream stream = http.getOutputStream();
            stream.write(out);

            InputStream in = http.getInputStream();
            String encoding = http.getContentEncoding();
            encoding = encoding == null ? "UTF-8" : encoding;
            String body = IOUtils.toString(in, encoding);

            accessToken = body.substring(body.lastIndexOf("\"access_token\":\"") + 16, body.indexOf("\",\"token_type\""));

            http.disconnect();
        }
}
