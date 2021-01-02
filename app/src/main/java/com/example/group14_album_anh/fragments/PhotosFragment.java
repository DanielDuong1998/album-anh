package com.example.group14_album_anh.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import com.example.albumanh.AllMediaItemAdapter;
import com.example.group14_album_anh.R;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import com.example.albumanh.Static.MediaModel;

public class PhotosFragment extends Fragment {
  private View RootView;
  private ListView listAllMediaItem;
  private AllMediaItemAdapter allMediaItemAdapter;

  @Nullable
  @Override
  public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
    RootView = inflater.inflate(R.layout.fragment_photos,container,false);

    listAllMediaItem = (ListView) RootView.findViewById(R.id.listAllMediaItem);
    System.out.println("size he: " + MediaModel.allMediaItems.get(0).size());
    allMediaItemAdapter = new AllMediaItemAdapter(getActivity(), R.layout.custom_media_item_with_title, R.layout.custom_images_layout, R.layout.custom_videos_layout, MediaModel.allMediaItems);
    System.out.println("adapter: " + allMediaItemAdapter);
    listAllMediaItem.setAdapter(allMediaItemAdapter);

    return RootView;
  }

  @Override
  public void onResume() {
    super.onResume();
    allMediaItemAdapter.notifyDataSetChanged();
  }
}
