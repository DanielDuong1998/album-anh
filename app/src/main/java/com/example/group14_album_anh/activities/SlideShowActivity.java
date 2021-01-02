package com.example.group14_album_anh.activities;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.animation.Animation;
import android.widget.ImageView;
import android.widget.ViewFlipper;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.bumptech.glide.Glide;
import com.example.albumanh.MediaItem;
import com.example.group14_album_anh.R;
import com.example.albumanh.Static.MediaModel;

import java.io.File;
import java.util.ArrayList;

public class SlideShowActivity extends AppCompatActivity {
  private Intent intent;
  private int position;
  private int position1;
  private ArrayList<MediaItem> listMedia;
  String returnUri;
  ViewFlipper viewFlipper;
  private Toolbar toolbar;



  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_slide_show);

    viewFlipper = findViewById(R.id.flipperView);
    toolbar = (Toolbar) findViewById(R.id.toolbarImageView);
    setSupportActionBar(toolbar);
    getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    getSupportActionBar().setDisplayShowHomeEnabled(true);

    intent = getIntent();
    position = intent.getExtras().getInt("id");
    listMedia = MediaModel.allMediaItems.get(position);

    for (int i = position; i < listMedia.size(); i++) {
      returnUri = listMedia.get(i).getPath();
      flipperImage(returnUri);
    }

    viewFlipper.getInAnimation().setAnimationListener(new Animation.AnimationListener() {
      @Override
      public void onAnimationStart(Animation animation) {

      }

      @Override
      public void onAnimationEnd(Animation animation) {
        int displayedChild = viewFlipper.getDisplayedChild();
        int childCount = viewFlipper.getChildCount();
        if (displayedChild == childCount - 1) {
          viewFlipper.stopFlipping();
          finish();
        }
      }


      @Override
      public void onAnimationRepeat(Animation animation) {

      }
    });

  }

  public void flipperImage(String filePath) {
    ImageView ImageFromUri = new ImageView(this);

    Uri path = Uri.fromFile(new File(filePath));

    Glide.with(getApplicationContext())
            .load(path)
            .into(ImageFromUri);

    viewFlipper.addView(ImageFromUri);
    viewFlipper.setFlipInterval(2000);
    viewFlipper.setAutoStart(true);
    viewFlipper.setInAnimation(this, android.R.anim.slide_in_left);
    viewFlipper.setOutAnimation(this, android.R.anim.slide_out_right);
  }

  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
    switch (item.getItemId()) {
      case android.R.id.home: {
        this.finish();
        return true;
      }
    }

    return super.onOptionsItemSelected(item);
  }
}
