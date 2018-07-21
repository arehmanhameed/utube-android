package io.boringideas.utube.RecyclerUtils;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

import io.boringideas.utube.R;
import io.boringideas.utube.Videos;

public class VideosAdapter extends RecyclerView.Adapter<VideosAdapter.VideosViewHolder> {

    List<Videos> videosList;


    public class VideosViewHolder extends RecyclerView.ViewHolder {
        TextView title, publisher, views, date;
        ImageView coverImg;

        public VideosViewHolder(View view) {
            super(view);
            title = view.findViewById(R.id.title);
//            publisher = view.findViewById(R.id.publisher);
            views = view.findViewById(R.id.views);
            date = view.findViewById(R.id.publish_date);
            coverImg = view.findViewById(R.id.cover_img);
        }
    }


    public VideosAdapter(List<Videos> videosList) {
        this.videosList = videosList;
    }

    @NonNull
    @Override
    public VideosAdapter.VideosViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.content_list_item, parent, false);

        return new VideosViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull VideosAdapter.VideosViewHolder holder, int position) {
        Videos video = videosList.get(position);

//        setCoverImage(holder.coverImg, video.getImgUrl());
//        holder.coverImg.setImageBitmap(setCoverImage(video.getVideoUrl()));
        Picasso.get().load(video.getImgUrl()).into(holder.coverImg);
        holder.title.setText(video.getTitle());
//        holder.publisher.setText(video.getPublisher());
        holder.views.setText(video.getViews());
        holder.date.setText(video.getPublishDate());
    }

//    private void setCoverImage(ImageView img, String videoUrl) {
//
////        String img_url = "https://i.stack.imgur.com/s9XgQ.png";
//        String img_url = "https://img.youtube.com/vi/" + extractYoutubeId(videoUrl) + "/0.jpg";
//        Picasso.get().load(img_url).into(img);
//
////        return ThumbnailUtils.createVideoThumbnail(url, MediaStore.Video.Thumbnails.MICRO_KIND);
//    }

    @Override
    public int getItemCount() {
        return videosList.size();
    }


//    // extract youtube video id and return that id
//    // ex--> "http://www.youtube.com/watch?v=t7UxjpUaL3Y"
//    // videoid is-->t7UxjpUaL3Y
//    public String extractYoutubeId(String url) {
//        String id = null;
//        try {
//            String query = new URL(url).getQuery();
//            if (url != null) {
//                if (url.contains("?")) {
//                    String[] temp = url.split("v=");
//                    id = temp[temp.length - 1];
//                } else {
//                    String[] temp = url.split("/");
//                    id = temp[temp.length - 1];
//                }
//            }
////            String[] param = query.split("&");
////            for (String row : param) {
////                String[] param1 = row.split("=");
////                if (param1[0].equals("v")) {
////                    id = param1[1];
////                }
////            }
//            return id;
//        } catch (MalformedURLException e) {
//            e.printStackTrace();
//            return id;
//        }
//    }
}
