package com.mcs.luel.itunesmusicpriview;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.mcs.luel.itunesmusicpriview.pojoClasses.TrackPojo;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

public class CustomViewHolder extends RecyclerView.ViewHolder {

    ImageView artWork;
    TextView collectionname, artistName, trackPrice;


    public CustomViewHolder(@NonNull View itemView) {
        super(itemView);
        collectionname = itemView.findViewById(R.id.tv_collection_name);
        artistName = itemView.findViewById(R.id.tv_artist_name);
        trackPrice = itemView.findViewById(R.id.track_price);
        artWork = itemView.findViewById(R.id.iv_artwork);
    }

    public void bindItem(TrackPojo item) throws MalformedURLException {
        collectionname.setText(item.getCollectionName());
        artistName.setText(item.getArtistName());
        trackPrice.setText(item.getTrackPrice());
        artWork.setImageDrawable(item.getArt());
        Uri uri = Uri.parse(item.getPreviewURL());

        itemView.setOnClickListener(v->{
            MediaPlayer mediaPlayer = new MediaPlayer();
            mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
            try {
                mediaPlayer.setDataSource(itemView.getContext(), uri);
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                mediaPlayer.prepare();
            } catch (IOException e) {
                e.printStackTrace();
            }
            mediaPlayer.start();
        });




    }


}
