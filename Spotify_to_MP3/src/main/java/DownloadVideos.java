import com.github.kiulian.downloader.YoutubeDownloader;
import com.github.kiulian.downloader.downloader.YoutubeProgressCallback;
import com.github.kiulian.downloader.downloader.request.RequestSearchResult;
import com.github.kiulian.downloader.downloader.request.RequestSearchable;
import com.github.kiulian.downloader.downloader.request.RequestVideoFileDownload;
import com.github.kiulian.downloader.downloader.request.RequestVideoInfo;
import com.github.kiulian.downloader.downloader.response.Response;
import com.github.kiulian.downloader.model.search.SearchResult;
import com.github.kiulian.downloader.model.videos.VideoInfo;
import com.github.kiulian.downloader.model.videos.formats.Format;
import com.github.kiulian.downloader.model.videos.formats.VideoFormat;
import com.github.kiulian.downloader.parser.*;


import java.io.File;
import java.io.IOException;
import java.util.List;

public class DownloadVideos {


    public void download(String videoId,String path,String name){
        YoutubeDownloader downloader = new YoutubeDownloader();
        RequestVideoInfo request = new RequestVideoInfo(videoId);
        Response<com.github.kiulian.downloader.model.videos.VideoInfo> response = downloader.getVideoInfo(request);
        VideoInfo video = response.data();

        if (video == null){
            return;
        }


        List<VideoFormat> videoFormats = video.videoFormats();
        File outputDir = new File(path);
        Format format = videoFormats.get(1);
        RequestVideoFileDownload requestVideoFileDownload = new RequestVideoFileDownload(format)
                .callback(new YoutubeProgressCallback<File>() {
                    @Override
                    public void onDownloading(int progress) {
                        System.out.printf("Downloaded %d%%\n", progress);
                    }

                    @Override
                    public void onFinished(File videoInfo) {
                        System.out.println("Finished file: " + videoInfo);
                    }

                    @Override
                    public void onError(Throwable throwable) {
                        System.out.println("Error: " + throwable.getLocalizedMessage());
                    }
                })
                .async()
                .saveTo(outputDir)
                .renameTo(name);

        Response<File> responseDownload = downloader.downloadVideoFile(requestVideoFileDownload);
        File data = responseDownload.data();

    }

    public String search(String query){
        YoutubeDownloader downloader = new YoutubeDownloader();
        RequestSearchResult request = new RequestSearchResult(query);
        SearchResult result = downloader.search(request).data();
        if (result.videos().isEmpty()){
            return null;
        }
        return result.videos().get(0).videoId();
    }

}
