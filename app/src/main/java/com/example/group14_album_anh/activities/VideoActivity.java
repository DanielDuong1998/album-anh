package com.example.group14_album_anh.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.MediaController;
import android.widget.VideoView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.albumanh.Static.MediaModel;
import com.example.group14_album_anh.R;
import com.example.albumanh.MediaItem;

import java.util.ArrayList;

public class VideoActivity extends AppCompatActivity {
  private VideoView videoView;
  private ImageButton playBtn;
  private MediaController mediaController;
  private int mode;
  private String albumName;
  private ArrayList<MediaItem> oMediaItems;

  @Override
  protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_play_video);
    getWidget();

    Intent intent = getIntent();
    int position = intent.getIntExtra("position", -1);
    int position1 = intent.getIntExtra("position1", -1);
    mode = intent.getIntExtra("mode", 1);
    albumName = intent.getStringExtra("albumName");
//    if(mode == 1){
//      oMediaItems = getListMediaInAlbum();
//    }
//    else oMediaItems = ListMediaActivity.listMedia;

    videoView.setVideoPath(MediaModel.allMediaItems.get(position).get(position1).getPath());

    videoView.seekTo(1);
    playBtn.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        playBtn.setVisibility(View.INVISIBLE);
        videoView.setMediaController(mediaController);
        mediaController.setAnchorView(videoView);
        videoView.start();
      }
    });
  }

  private void getWidget(){
    videoView = (VideoView) findViewById(R.id.videoView);
    playBtn = (ImageButton) findViewById(R.id.btnPlay);
    mediaController = new MediaController(this);
  }
}
