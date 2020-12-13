package com.example.albumanh;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.Toast;

import com.example.albumanh.Static.MediaModel;

public class MainActivity extends AppCompatActivity {
  private final int REQUEST_CODE_STORAGE = 567;
  private final int REQUEST_CODE_CAMERA = 123;

  private ListView listAllMediaItem;
  private AllMediaItemAdapter allMediaItemAdapter;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    ActivityCompat.requestPermissions(MainActivity.this, new String[] {Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE}, REQUEST_CODE_STORAGE);
  }

  @Override
  public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
    if(requestCode == REQUEST_CODE_STORAGE && grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
      MediaModel.init(this);
      getView();

    } else if(requestCode == REQUEST_CODE_CAMERA && grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){

    } else{
      Toast.makeText(MainActivity.this, "Permission denied !", Toast.LENGTH_SHORT).show();
    }
    super.onRequestPermissionsResult(requestCode, permissions, grantResults);
  }

  private void getView(){
    listAllMediaItem = (ListView) findViewById(R.id.listAllMediaItem);
    System.out.println("size he: " + MediaModel.allMediaItems.get(0).size());
    allMediaItemAdapter = new AllMediaItemAdapter(this,R.layout.custom_media_item_with_title, R.layout.custom_images_layout, R.layout.custom_videos_layout, MediaModel.allMediaItems);
    System.out.println("adapter: " + allMediaItemAdapter);
    listAllMediaItem.setAdapter(allMediaItemAdapter);
  }
}