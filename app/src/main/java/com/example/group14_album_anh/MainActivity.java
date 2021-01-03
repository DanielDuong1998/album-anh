package com.example.group14_album_anh;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.viewpager.widget.ViewPager;

import com.ToxicBakery.viewpager.transforms.DefaultTransformer;
import com.example.group14_album_anh.adapters.FragmentAdapter;
import com.google.android.material.tabs.TabLayout;

public class MainActivity extends AppCompatActivity {
  private final int REQUEST_CODE_STORAGE = 567;
  private final int REQUEST_CODE_CAMERA = 123;

  private ViewPager viewPager;
  private TabLayout tabLayout;
  FragmentAdapter adapter;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    ActivityCompat.requestPermissions(MainActivity.this, new String[] {Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE}, REQUEST_CODE_STORAGE);
  }

  @Override
  public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
    if(requestCode == REQUEST_CODE_STORAGE && grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
      com.example.albumanh.Static.MediaModel.init(this);
      getView();

    } else if(requestCode == REQUEST_CODE_CAMERA && grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){

    } else{
      Toast.makeText(MainActivity.this, "Permission denied !", Toast.LENGTH_SHORT).show();
    }
    super.onRequestPermissionsResult(requestCode, permissions, grantResults);
  }

  private void getView(){
    initAdapter();

    viewPager = (ViewPager) findViewById(R.id.pageViewMain);
    tabLayout = (TabLayout) findViewById(R.id.tabLayoutMain);

    viewPager.setAdapter(adapter);

    tabLayout.setupWithViewPager(viewPager);
    viewPager.setPageTransformer(true, new DefaultTransformer());
  }

  private void initAdapter(){
    adapter = new FragmentAdapter(getSupportFragmentManager());
  }
}