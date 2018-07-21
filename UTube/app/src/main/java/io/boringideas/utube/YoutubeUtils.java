package io.boringideas.utube;

import android.util.Log;

import com.google.api.client.http.HttpRequest;
import com.google.api.client.http.HttpRequestInitializer;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.youtube.YouTube;
import com.google.api.services.youtube.model.SearchListResponse;
import com.google.api.services.youtube.model.SearchResult;
import com.google.api.services.youtube.model.Video;
import com.google.api.services.youtube.model.VideoListResponse;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class YoutubeUtils {

    private YouTube youtube;
    private YouTube.Search.List searchQuery;
    private YouTube.Videos.List videoQuery;
    public static final String KEY = "AIzaSyCLzuccgmUlLYl9OfTBarme5edotixWijU";
    private ArrayList<Videos> videosList;

    public ArrayList<Videos> getVideos(String query){

        youtube = new YouTube.Builder(new NetHttpTransport(), new JacksonFactory(), new HttpRequestInitializer() {
            @Override
            public void initialize(HttpRequest hr) {
            }
        }).setApplicationName("uTube").build();

        try {
            searchQuery = youtube.search().list("id,snippet");
            searchQuery.setKey(KEY);
            searchQuery.setType("video");
            searchQuery.setMaxResults((long) 25);
            searchQuery.setFields("items(id/videoId,snippet/title,snippet/publishedAt,snippet/channelTitle,snippet/thumbnails/medium/url)");
            searchQuery.setQ("lions");

            SearchListResponse searchResponse = searchQuery.execute();
            List<SearchResult> searchResults = searchResponse.getItems();

            for (SearchResult searchResult : searchResults) {

                String videoId = searchResult.getId().getVideoId();

                videoQuery = youtube.videos().list("statistics");
                videoQuery.setKey(KEY);
                videoQuery.setId(videoId);

                VideoListResponse videoResponse = videoQuery.execute();

                Video video = videoResponse.getItems().get(0);

                String title = searchResult.getSnippet().getTitle();
                String description = searchResult.getSnippet().getDescription();
                String publishedAt = searchResult.getSnippet().getPublishedAt().toString().split("T")[0];
                String img = searchResult.getSnippet().getThumbnails().getMedium().getUrl();
                String views = String.valueOf(video.getStatistics().getViewCount());
                String likes = String.valueOf(video.getStatistics().getLikeCount());

                Videos videoItem = new Videos(videoId,title, description, publishedAt, views, likes, img, "https://youtu.be/" + videoId);
                videosList.add(videoItem);
            }

            return videosList;

        } catch (IOException e) {
            Log.d("YC", "Could not initialize: " + e);
            return null;
        }
    }
}
