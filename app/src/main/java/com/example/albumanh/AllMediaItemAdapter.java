package com.example.albumanh;

import android.content.Context;
import android.content.res.Resources;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.TextView;

import java.util.ArrayList;

public class AllMediaItemAdapter extends BaseAdapter {
  private Context context;
  private int layoutOfficial, layoutImage, layoutVideo;
  private ArrayList<ArrayList<MediaItem>> allMediaItemList;

  public AllMediaItemAdapter(Context context, int layoutOfficial, int layoutImage, int layoutVideo, ArrayList<ArrayList<MediaItem>> allMedialItemList){
    this.context = context;
    this.layoutOfficial = layoutOfficial;
    this.layoutImage = layoutImage;
    this.layoutVideo = layoutVideo;
    this.allMediaItemList = allMedialItemList;
  }

  @Override
  public int getCount() {
    return allMediaItemList.size();
  }

  @Override
  public Object getItem(int position) {
    return null;
  }

  @Override
  public long getItemId(int position) {
    return 0;
  }

  @Override
  public View getView(int position, View convertView, ViewGroup parent) {
    LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    convertView = inflater.inflate(layoutOfficial, null);

    TextView txtTitle = (TextView) convertView.findViewById(R.id.txtTitle);
    GridView gridView = (GridView) convertView.findViewById(R.id.grvMdICus);

    txtTitle.setText(allMediaItemList.get(position).get(0).getDateAddedString());
    MediaItemAdapter adapter = new MediaItemAdapter(context, layoutImage, layoutVideo, allMediaItemList.get(position));
    gridView.setAdapter(adapter);

    Resources resources = context.getResources();
    int db = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 124, resources.getDisplayMetrics());

    int bonusRow = allMediaItemList.get(position).size() % 3 == 0 ? 0 : 1;

    ViewGroup.LayoutParams layoutParams = gridView.getLayoutParams();
    layoutParams.height = db*(allMediaItemList.get(position).size()/3 + bonusRow);
    gridView.setLayoutParams(layoutParams);

    return convertView;
  }
}
