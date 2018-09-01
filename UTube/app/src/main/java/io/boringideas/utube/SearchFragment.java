package io.boringideas.utube;

import android.app.ProgressDialog;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.support.v7.widget.SearchView;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
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

public class SearchFragment extends Fragment {

    public static SearchFragment _activity;
    View rootView;
    ImageView playPauseControl, closeControl;
    TextView title;
    FrameLayout controlPanel;
    SearchView searchView;
    RecyclerView mRecyclerView;
    VideosAdapter mAdapter;
    List<Videos> videosList = new ArrayList<>();
//    BackgroundAudioService backgroundAudioService;
    ProgressDialog progressDialog;
    private MediaPlayer mediaPlayer;
    private LinkExtractionUtil linkExtractUtil;
    private boolean initialStage = true;
    int position;
    private boolean playPause;

    public SearchFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
//        backgroundAudioService = new BackgroundAudioService();
        super.onCreate(savedInstanceState);
        getActivity().setTitle(R.string.title_search);
        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setCancelable(false);
        //initialize class
        initClasses();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        //initialize root view
        rootView = inflater.inflate(R.layout.fragment_search, container, false);

        //initialize views
        initViews();



        //set Recycler View
        setRecycleView();

        //set search view
        setSearchView();

        if(mediaPlayer.isPlaying())
            showControlPanel();

//        backgroundAudioService.callingActivity = this;
//        backgroundAudioService.checkNull();

        //assign this to activity
        _activity = this;

        return rootView;
    }

    private void initClasses() {
        mAdapter = new VideosAdapter(videosList);

        getActivity().setVolumeControlStream(AudioManager.STREAM_MUSIC);
        mediaPlayer = new MediaPlayer();
        mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);

        linkExtractUtil = new LinkExtractionUtil();
        linkExtractUtil._activity = this;


    }

    private void initViews() {
        searchView = rootView.findViewById(R.id.searchView);
        mRecyclerView = rootView.findViewById(R.id.content_view);
        controlPanel = rootView.findViewById(R.id.controlPanel);
        playPauseControl = rootView.findViewById(R.id.playPause);
        closeControl = rootView.findViewById(R.id.close);
        title = rootView.findViewById(R.id.title);

        playPauseControl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onControlClick(view);
            }
        });

        closeControl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onControlClick(view);
            }
        });

    }

    private void setSearchView() {
        searchView.setQueryHint("Enter search");
        searchView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                searchView.setIconified(false);
            }
        });

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
//                Toast.makeText(getActivity(),"SEARCH : "+query,Toast.LENGTH_SHORT).show();
                searchView.clearFocus();
                if(videosList.size() > 0) {
                    videosList.clear();
                    mAdapter.notifyDataSetChanged();
                }
                searchForVideos(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });

    }

    private void setRecycleView() {
        mRecyclerView.setHasFixedSize(true);

        // vertical RecyclerView
        // keep movie_list_row.xml width to `match_parent`
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(mLayoutManager);

        // adding inbuilt divider line
        mRecyclerView.addItemDecoration(new DividerItemDecoration(getContext(), LinearLayoutManager.VERTICAL));

        mRecyclerView.setItemAnimator(new DefaultItemAnimator());

        mRecyclerView.setAdapter(mAdapter);


        //to observe click listener
        mRecyclerView.addOnItemTouchListener(new RecyclerTouchListener(getContext(), mRecyclerView, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {
//                Toast.makeText(getActivity(),"VIEW CLICKED : "+position,Toast.LENGTH_SHORT).show();
                SearchFragment.this.position = position;
                if(mediaPlayer.isPlaying())
                    mediaPlayer.reset();
                if(controlPanel.getVisibility() == View.VISIBLE)
                    closeControl();
                playVideoClicked(videosList.get(position));
//                playPause(videosList.get(position));
            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));
    }

//    private void playInBack(Videos video) {
//        progressDialog.setMessage("Streaming...");
//        progressDialog.show();
//
//        linkExtractUtil.getUrl(video.getId());
////        Intent serviceIntent = new Intent(getContext(), backgroundAudioService.getClass());
////        serviceIntent.setAction(backgroundAudioService.ACTION_PLAY);
////        serviceIntent.putExtra(Config.YOUTUBE_TYPE, ItemType.YOUTUBE_MEDIA_TYPE_VIDEO);
////        serviceIntent.putExtra(Config.YOUTUBE_TYPE_VIDEO, video);
////        getActivity().startService(serviceIntent);
//    }

//    public void minimizeApp() {
//        if(progressDialog.isShowing())
//            progressDialog.cancel();
////        addVideoToHistory(videosList.get(0));
//        Intent startMain = new Intent(Intent.ACTION_MAIN);
//        startMain.addCategory(Intent.CATEGORY_HOME);
//        startMain.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//        startActivity(startMain);
//    }

    private void searchForVideos(String query) {
        new GetVideos().execute(query);
    }


    public void startPlaying(String url) {
        if (progressDialog.isShowing()) {
            progressDialog.cancel();
        }

        if(url != null) {
            try {
                mediaPlayer.setDataSource(url);
                mediaPlayer.prepareAsync();
                mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                    @Override
                    public void onPrepared(MediaPlayer mp) {

                        mediaPlayer.start();
                        initialStage = false;
                        showControlPanel();
                    }
                });

                mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                    @Override
                    public void onCompletion(MediaPlayer mediaPlayer) {
                        initialStage = true;
                        playPause = false;
                        mediaPlayer.stop();
                        mediaPlayer.reset();
                    }
                });


            } catch (IOException e) {
                e.printStackTrace();
            }
        }else
            Toast.makeText(getActivity(),"Unable to play this video. Try again",Toast.LENGTH_SHORT).show();

    }

    public void playVideoClicked(Videos video){
        progressDialog.setMessage("Buffering...");
        progressDialog.show();
        linkExtractUtil.getUrl(video.getId());
    }

    public void playPause(Videos video) {
        if (!playPause) {

            if (initialStage) {

            } else {
                if (!mediaPlayer.isPlaying()) {
                    mediaPlayer.start();
                    playPauseControl.setImageDrawable(getActivity().getDrawable(R.drawable.ic_pause));
                }
            }

            playPause = true;

        } else {
            if (mediaPlayer.isPlaying()) {
                playPauseControl.setImageDrawable(getActivity().getDrawable(R.drawable.ic_play));
                mediaPlayer.pause();
            }

            playPause = false;
        }
    }

    private void showControlPanel() {
        title.setText(videosList.get(position).getTitle());
        Animation slideUp = AnimationUtils.loadAnimation(getContext(), R.anim.slide_up);

        if (controlPanel.getVisibility() == View.GONE) {

            controlPanel.startAnimation(slideUp);
            controlPanel.setVisibility(View.VISIBLE);
            playPauseControl.setImageDrawable(getActivity().getDrawable(R.drawable.ic_pause));
        }
    }

    public void onControlClick(View view) {
        switch (view.getId()) {
            case R.id.playPause:
                playPause(videosList.get(position));
                break;
            case R.id.close:
                closeControl();
                break;
        }
    }

    public void closeControl() {
        initialStage = true;
        playPause = false;
        mediaPlayer.stop();
        mediaPlayer.reset();

        Animation slideDown = AnimationUtils.loadAnimation(getContext(), R.anim.slide_down);
        if (controlPanel.getVisibility() == View.VISIBLE) {

            controlPanel.startAnimation(slideDown);
            controlPanel.setVisibility(View.GONE);
        }
    }


    class GetVideos extends AsyncTask<String, Integer, ArrayList<Videos>> {

        private YouTube youtube;
        private YouTube.Search.List searchQuery;
        private YouTube.Videos.List videoQuery;
        public static final String KEY = "YOUR API KEY";
        private ArrayList<Videos> videosList;

        @Override
        protected void onPreExecute() {

            youtube = new YouTube.Builder(new NetHttpTransport(), new JacksonFactory(), new HttpRequestInitializer() {
                @Override
                public void initialize(HttpRequest hr) {
                }
            }).setApplicationName("uTube").build();

            videosList = new ArrayList<>();

            progressDialog.setMessage("Loading...");
            progressDialog.show();
        }

        @Override
        protected ArrayList<Videos> doInBackground(String... strings) {
            try {
                searchQuery = youtube.search().list("id,snippet");
                searchQuery.setKey(KEY);
                searchQuery.setType("video");
                searchQuery.setMaxResults((long) 25);
                searchQuery.setFields("items(id/videoId,snippet/title,snippet/publishedAt,snippet/channelTitle,snippet/thumbnails/medium/url)");
                searchQuery.setQ(strings[0]);

                SearchListResponse searchResponse = searchQuery.execute();
                List<SearchResult> searchResults = searchResponse.getItems();

                for (SearchResult searchResult : searchResults) {

                    String videoId = searchResult.getId().getVideoId();

                    videoQuery = youtube.videos().list("statistics");
                    videoQuery.setKey(KEY);
                    videoQuery.setId(videoId);

                    VideoListResponse videoResponse = videoQuery.execute();

                    if (videoResponse.size() > 0 && videoResponse.getItems().size() > 0) {
                        Video video = videoResponse.getItems().get(0);

                        String title = searchResult.getSnippet().getTitle();
                        String description = searchResult.getSnippet().getDescription();
                        String publishedAt = searchResult.getSnippet().getPublishedAt().toString().split("T")[0];
                        String img = searchResult.getSnippet().getThumbnails().getMedium().getUrl();
                        String views = String.valueOf(video.getStatistics().getViewCount());
                        String likes = String.valueOf(video.getStatistics().getLikeCount());

                        Videos videoItem = new Videos(videoId, title, description, publishedAt, views, likes, img, "https://youtu.be/" + videoId);
                        videosList.add(videoItem);
                    }
                }

                return videosList;

            } catch (IOException e) {
                Log.d("YC", "Could not initialize: " + e);
                return null;
            }
        }

        @Override
        protected void onPostExecute(ArrayList<Videos> searchResults) {

//            prepareData();
            showVideoList(searchResults);
        }

    }

    private void showVideoList(ArrayList<Videos> searchResults) {
        if (searchResults != null) {
            for (Videos video :
                    searchResults) {
                videosList.add(video);
            }
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if (progressDialog.isShowing()) {
                        progressDialog.cancel();
                    }
                    mAdapter.notifyDataSetChanged();
                }
            });
        } else {
            Toast.makeText(getActivity(), "No search results", Toast.LENGTH_SHORT).show();
        }
    }


}
