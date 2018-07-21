package io.boringideas.utube;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

public class Videos implements Serializable{
    private String title, publisher, publishDate, views, likes, imgUrl, videoUrl, id;
    Videos _instance;

    public Videos(){}
    public Videos getInstance(){
        if(_instance == null){
            _instance = new Videos();
        }

        return _instance;
    }

    public Videos(String id, String title, String publisher, String publishDate, String views, String likes, String imgUrl, String videoUrl){
        this.id = id;
        this.title = title;
        this.publisher = publisher;
        this.publishDate = publishDate;
        this.views = views;
        this.likes = likes;
        this.imgUrl = imgUrl;
        this.videoUrl = videoUrl;
    }

//    public Videos(Parcel parcel) {
//        this.id = parcel.readString();
//        this.title       = parcel.readString();
//        this.publisher   = parcel.readString();
//        this.publishDate = parcel.readString();
//        this.views       = parcel.readString();
//        this.likes       = parcel.readString();
//        this.imgUrl      = parcel.readString();
//        this.videoUrl    = parcel.readString();
//    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public String getPublishDate() {
        return publishDate;
    }

    public void setPublishDate(String publishDate) {
        this.publishDate = publishDate;
    }

    public String getViews() {
        return views;
    }

    public void setViews(String views) {
        this.views = views;
    }

    public String getLikes() {
        return likes;
    }

    public void setLikes(String likes) {
        this.likes = likes;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getVideoUrl() {
        return videoUrl;
    }

    public void setVideoUrl(String videoUrl) {
        this.videoUrl = videoUrl;
    }

//    @Override
//    public int describeContents() {
//        return 0;
//    }
//
//    @Override
//    public void writeToParcel(Parcel parcel, int i) {
//        parcel.writeString(title);
//        parcel.writeString(publisher);
//        parcel.writeString(publishDate);
//        parcel.writeString(views);
//        parcel.writeString(likes);
//        parcel.writeString(imgUrl);
//        parcel.writeString(videoUrl);
//    }

//    public static final Parcelable.Creator<Videos> CREATOR = new Parcelable.Creator<Videos>(){
//
//        @Override
//        public Videos createFromParcel(Parcel parcel) {
//            return new Videos(parcel);
//        }
//
//        @Override
//        public Videos[] newArray(int i) {
//            return new Videos[0];
//        }
//    };
}
