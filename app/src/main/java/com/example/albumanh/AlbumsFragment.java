package com.example.albumanh;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.albumanh.Static.MediaModel;

public class AlbumsFragment extends Fragment {
    private View RootView;


    private GridView gridViewAlbum;
    private AlbumAdapter albumAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        RootView = inflater.inflate(R.layout.fragment_albums,container,false);

        gridViewAlbum = (GridView) RootView.findViewById(R.id.gridviewAlbum);
        albumAdapter = new AlbumAdapter(getActivity(), R.layout.custom_fragment_image_layout, R.layout.custom_fragment_video_layout, MediaModel.albums);
        gridViewAlbum.setAdapter(albumAdapter);

        return RootView;
    }
}
