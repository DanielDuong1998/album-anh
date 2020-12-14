package com.example.albumanh;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;

import java.util.ArrayList;

public class MediaItemAdapter extends BaseAdapter {
  private Context context;
  private int layoutImage, layoutVideo;
  private ArrayList<MediaItem> mediaItemList;

  public MediaItemAdapter(Context context, int layoutImage, int layoutVideo, ArrayList<MediaItem> mediaItemList){
    this.context = context;
    this.layoutImage = layoutImage;
    this.layoutVideo = layoutVideo;
    this.mediaItemList = mediaItemList;
  }

  @Override
  public int getCount() {
    return mediaItemList.size();
  }

  @Override
  public Object getItem(int position) {
    return null;
  }

  @Override
  public long getItemId(int position) {
    return 0;
  }

  private class ViewHolder {
    ImageView shapeView;
    ImageView shapeCheck;
  }

  @Override
  public View getView(int position, View convertView, ViewGroup parent) {
    int OLayout = mediaItemList.get(position).getType() == 1 ? layoutImage : layoutVideo;
    int idShapeView = mediaItemList.get(position).getType() == 1 ? R.id.imgImage : R.id.imgVideo;
    int idShapeCheck = mediaItemList.get(position).getType() == 1 ? R.id.photoCheck : R.id.checked;

    ViewHolder viewHolder;

    if(convertView == null){
      viewHolder = new ViewHolder();
      LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
      convertView = inflater.inflate(OLayout, null);
      viewHolder.shapeView = (ImageView) convertView.findViewById(idShapeView);
      viewHolder.shapeCheck = (ImageView) convertView.findViewById(idShapeCheck);
      convertView.setTag(viewHolder);
    }
    else {
      viewHolder = (ViewHolder) convertView.getTag();
    }

    final MediaItem item = mediaItemList.get(position);
    Glide.with(context)
            .load(item.getPath())
            .apply(RequestOptions.diskCacheStrategyOf(DiskCacheStrategy.RESOURCE))
            .into(viewHolder.shapeView);

    if(item.isCheck()) {
      viewHolder.shapeCheck.setVisibility(View.VISIBLE);
    } else {
      viewHolder.shapeCheck.setVisibility(View.INVISIBLE);
    }

    //thêm chức năng click vào một ảnh để mở màn hình xem full ảnh
    convertView.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        Toast.makeText(context,"Khi bấm vào ảnh thì sẽ chuyển sang màn hình xem full ảnh",Toast.LENGTH_SHORT).show();
        //chức năng mở màn hình khác viết ở đây
      }
    });

    //thêm chức năng long click (nhấn giữ) để thực hiện chọn
    convertView.setOnLongClickListener(new View.OnLongClickListener() {
      @Override
      public boolean onLongClick(View v) {
        if (item.isCheck()) item.setCheck(false);
        else item.setCheck(true);
        notifyDataSetChanged();
        return true;
      }
    });

    return convertView;
  }
}
