//package io.boringideas.utube;
//
//import android.content.Intent;
//import android.media.MediaPlayer;
//import android.net.Uri;
//import android.support.v7.app.AppCompatActivity;
//import android.os.Bundle;
//import android.util.Log;
//import android.view.View;
//import android.widget.Toast;
//import android.widget.VideoView;
//
//import com.google.android.youtube.player.YouTubeBaseActivity;
//import com.google.android.youtube.player.YouTubeInitializationResult;
//import com.google.android.youtube.player.YouTubePlayer;
//import com.google.android.youtube.player.YouTubePlayerView;
//
//import java.util.ArrayList;
//
//import io.boringideas.utube.model.ItemType;
//
//public class VideoPlayerActivity extends YouTubeBaseActivity implements
//        YouTubePlayer.OnInitializedListener, YouTubePlayer.pla {
//
//    Videos video;
//    //    VideoView mVideoView;
//    YouTubePlayerView youTubeView;
//    YouTubePlayer youTubePlayer;
//    BackgroundAudioService backgroundAudioService;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_video_player);
//
//        backgroundAudioService = new BackgroundAudioService(this);
//
//        if (getIntent() != null) {
//            video = (Videos) getIntent().getSerializableExtra("VIDEO");
//        }
//
////        mVideoView = findViewById(R.id.video_view);
//        youTubeView = (YouTubePlayerView) findViewById(R.id.video_view);
//        youTubeView.initialize(DeveloperKey.DEVELOPER_KEY, this);
//
////        if (video != null) {
////            mVideoView.setVideoURI(Uri.parse(video.getVideoUrl()));
////        }
////
////        mVideoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
////            @Override
////            public void onPrepared(MediaPlayer mediaPlayer) {
////                mVideoView.start();
////            }
////        });
//
//
//    }
//
//    public void onClickVideoPlayerActivity(View view) {
//        switch (view.getId()) {
//            case R.id.close_image_view:
//                finish();
//                break;
////            case R.id.play_button:
////                if(!mVideoView.isPlaying())
////                    mVideoView.start();
////                if ()
////                    break;
//            case R.id.play_in_background:
//                playInBack();
////                minimizeApp();
//                break;
//        }
//    }
//
//    private void playInBack() {
//        if(youTubePlayer.isPlaying())
//            youTubePlayer.release();
//        Intent serviceIntent = new Intent(this, BackgroundAudioService.class);
//        serviceIntent.setAction(BackgroundAudioService.ACTION_PLAY);
//        serviceIntent.putExtra(Config.YOUTUBE_TYPE, ItemType.YOUTUBE_MEDIA_TYPE_VIDEO);
//        serviceIntent.putExtra(Config.YOUTUBE_TYPE_VIDEO, video);
//        startService(serviceIntent);
//    }
//
//    public void minimizeApp() {
//
//        Intent startMain = new Intent(Intent.ACTION_MAIN);
//        startMain.addCategory(Intent.CATEGORY_HOME);
//        startMain.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//        startActivity(startMain);
//    }
//
//    @Override
//    public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer player, boolean b) {
//        if (!b) {
//            youTubePlayer = player;
////            youTubePlayer.setPlaybackEventListener(this);
//            player.cueVideo(video.getId());
//            youTubePlayer.cueVideo(video.getId());
//        }
//    }
//
//    @Override
//    public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {
//        Toast.makeText(this, "COULD NOT LOAD VIDEO TRY AGAIN LATER", Toast.LENGTH_SHORT).show();
//    }
//}
