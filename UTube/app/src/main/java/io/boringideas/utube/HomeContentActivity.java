package io.boringideas.utube;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

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

import io.boringideas.utube.RecyclerUtils.RecyclerTouchListener;
import io.boringideas.utube.RecyclerUtils.VideosAdapter;

public class HomeContentActivity extends AppCompatActivity {

    String category;
    List<Videos> videosList = new ArrayList<>();
    RecyclerView mRecyclerView;
    VideosAdapter mAdapter;
    Intent mIntent;

    private YouTube youtube;
    private YouTube.Search.List searchQuery;
    private YouTube.Videos.List videoQuery;

    // Your developer key goes here
    public static final String KEY
            = "YOUR API KEY";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_content);

        mRecyclerView = (RecyclerView) findViewById(R.id.content_view);
        mAdapter = new VideosAdapter(videosList);

        mRecyclerView.setHasFixedSize(true);

        // vertical RecyclerView
        // keep movie_list_row.xml width to `match_parent`
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());

        // horizontal RecyclerView
        // keep movie_list_row.xml width to `wrap_content`
        // RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.HORIZONTAL, false);

        mRecyclerView.setLayoutManager(mLayoutManager);

        // adding inbuilt divider line
        mRecyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));

        // adding custom divider line with padding 16dp
//        mRecyclerView.addItemDecoration(new MyDividerItemDecoration(this, LinearLayoutManager.HORIZONTAL, 16));
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());

        mRecyclerView.setAdapter(mAdapter);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Intent intent = getIntent();

        if (intent.hasExtra("CATEGORY")) {
            category = intent.getExtras().getString("CATEGORY");
            this.setTitle(category);
        }

        //to observe click listener
        mRecyclerView.addOnItemTouchListener(new RecyclerTouchListener(getApplicationContext(), mRecyclerView, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {
                Videos video = videosList.get(position);
                Toast.makeText(getApplicationContext(), video.getTitle() + " is selected!", Toast.LENGTH_SHORT).show();
//                mIntent = new Intent(HomeContentActivity.this, VideoPlayerActivity.class);
//                mIntent.putExtra("VIDEO", video);
//                startActivity(mIntent);

            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));

        getData();
    }

    private void getData() {
        if (!category.isEmpty()) {
            if (category.equals("TOP-100")) {
                getTop100Items();
            } else if (category.equals("TRENDING")) {
                getTrendingItems();
            } else if (category.equals("RECOMMENDED")) {
                getRecommendedItems();
            }
        }
    }

    private void getRecommendedItems() {
        Toast.makeText(this, "getting recommended items", Toast.LENGTH_SHORT).show();
        new Thread(){
            @Override
            public void run() {

        youtube = new YouTube.Builder(new NetHttpTransport(), new JacksonFactory(), new HttpRequestInitializer() {
            @Override
            public void initialize(HttpRequest hr) {
            }
        }).setApplicationName(getApplicationContext().getString(R.string.app_name)).build();

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

                prepareData(title, description, publishedAt, views, likes, img,videoId);
            }

        } catch (IOException e) {
            Log.d("YC", "Could not initialize: " + e);
        }
//        prepareData();

                super.run();
            }
        }.start();
    }

    private void getTrendingItems() {
        Toast.makeText(this, "getting trending items", Toast.LENGTH_SHORT).show();
//        prepareData();
    }

    private void getTop100Items() {
        Toast.makeText(this, "getting top 100 items", Toast.LENGTH_SHORT).show();
//        prepareData();
    }


    private void prepareData(String title, String publisher, String publishData, String views, String likes, String imgUrl, String videoId) {

        Videos video = new Videos(videoId,title, publisher, publishData, views, likes, imgUrl, "https://youtu.be/" + videoId);
        videosList.add(video);

        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mAdapter.notifyDataSetChanged();
            }
        });
    }


    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }
}
