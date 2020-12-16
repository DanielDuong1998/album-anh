package com.example.albumanh;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;

import java.util.ArrayList;

public class AlbumAdapter extends BaseAdapter {
  private Context context;
  private int imageLayout, videoLayout;
  private ArrayList<Album> listAlbum;

  public AlbumAdapter(Context context, int imageLayout, int videoLayout, ArrayList<Album> listAlbum){
    this.context = context;
    this.imageLayout = imageLayout;
    this.videoLayout = videoLayout;
    this.listAlbum = listAlbum;
  }

  @Override
  public int getCount() {
    return listAlbum.size();
  }

  @Override
  public Object getItem(int position) {
    return null;
  }

  @Override
  public long getItemId(int position) {
    return 0;
  }

  public class ViewHolder{
    ImageView imgAvatar;
    TextView txtNameAlbum;
    ImageView imgCheck;
  }

  @Override
  public View getView(int position, View convertView, ViewGroup parent) {
    Album album = listAlbum.get(position);
    int layout = album.getType() == 1 ? imageLayout : videoLayout;
    int imgAvatarId = album.getType() == 1 ? R.id.imgAvatarAlbum : R.id.imgAvatarFolder;
    int txtNameAlbumId = album.getType() == 1 ? R.id.txtNameAlbum : R.id.txtNameFolder;
    int imgCheckId = album.getType() == 1 ? R.id.imgCheck : R.id.vdCheck;

    ViewHolder holder;
    if(convertView == null){
      holder = new ViewHolder();
      LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
      convertView = inflater.inflate(layout, null);
      holder.imgAvatar = (ImageView) convertView.findViewById(imgAvatarId);
      holder.txtNameAlbum = (TextView) convertView.findViewById(txtNameAlbumId);
      holder.imgCheck = (ImageView) convertView.findViewById(imgCheckId);
      convertView.setTag(holder);
    }
    else {
      holder = (ViewHolder) convertView.getTag();
    }


    Glide.with(context)
            .load(album.getPathAvatar())
            .apply(RequestOptions.diskCacheStrategyOf(DiskCacheStrategy.RESOURCE))
            .into(holder.imgAvatar);
    holder.txtNameAlbum.setText(album.getName() + " (" + album.getMediaItemSize() + ")");
    if(album.isCheckbox()){
      holder.imgCheck.setVisibility(View.VISIBLE);
    }else{
      holder.imgCheck.setVisibility(View.INVISIBLE);
    }

    return convertView;
  }
}
