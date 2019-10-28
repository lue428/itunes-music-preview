package com.mcs.luel.itunesmusicpriview.pojoClasses;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Parcel;
import android.os.Parcelable;

public class TrackPojo implements Parcelable {

    private String artistName,
            collectionName, artworkUrl100, trackPrice, previewURL;

    private Drawable art;

    public Drawable getArt() {
        return art;
    }

    public void setArt(Drawable art) {
        this.art = art;
    }



    public TrackPojo() {
    }

    protected TrackPojo(Parcel in) {
        artistName = in.readString();
        collectionName = in.readString();
        artworkUrl100 = in.readString();
        trackPrice = in.readString();
        previewURL = in.readString();
        art= in.readParcelable(Drawable.class.getClassLoader());
    }

    public static final Creator<TrackPojo> CREATOR = new Creator<TrackPojo>() {
        @Override
        public TrackPojo createFromParcel(Parcel in) {
            return new TrackPojo(in);
        }

        @Override
        public TrackPojo[] newArray(int size) {
            return new TrackPojo[size];
        }
    };

    public String getArtistName() {
        return artistName == null?
                " " : artistName;
    }

    public String getPreviewURL() {
        return previewURL;
    }

    public void setPreviewURL(String previewURL) {
        this.previewURL = previewURL;
    }
    public void setArtistName(String artistName) {
        this.artistName = artistName;
    }

    public String getCollectionName() {
        return collectionName == null?
                " ": collectionName;
    }

    public void setCollectionName(String collectionName) {
        this.collectionName = collectionName;
    }

    public String getArtworkUrl100() {
        return artworkUrl100;
    }

    public void setArtworkUrl100(String artworkUrl100) {
        this.artworkUrl100 = artworkUrl100;
    }

    public String getTrackPrice() {
        return trackPrice == null?
                " " : trackPrice;
    }

    public void setTrackPrice(String trackPrice) {
        this.trackPrice = trackPrice;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(artistName);
        dest.writeString(collectionName);
        dest.writeString(artworkUrl100);
        dest.writeString(trackPrice);
        dest.writeString(previewURL);
        dest.writeValue(art);
    }


}
