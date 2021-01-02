package com.example.group14_album_anh.adapters;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.group14_album_anh.fragments.AlbumsFragment;
import com.example.group14_album_anh.fragments.PhotosFragment;

public class FragmentAdapter extends FragmentPagerAdapter {
  private String listTab[] = {"Photos", "Albums"};
  private PhotosFragment photosFragment;
  private AlbumsFragment albumFragment;


  public FragmentAdapter(FragmentManager fm) {
    super(fm);
    photosFragment = new PhotosFragment();
    albumFragment = new AlbumsFragment();
  }


  @Override
  public Fragment getItem(int position) {

    switch(position)
    {
      case 0:
        return photosFragment;
      case 1:
        return albumFragment;
      default:
        return null;
    }
  }

  @Override
  public int getCount() {
    return listTab.length;
  }

  @Nullable
  @Override
  public CharSequence getPageTitle(int position) {
    return listTab[position];
  }
}
