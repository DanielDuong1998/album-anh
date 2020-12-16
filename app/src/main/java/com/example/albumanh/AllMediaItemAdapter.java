package com.example.albumanh;


import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

import static java.security.AccessController.getContext;

public class AllMediaItemAdapter extends BaseAdapter {
  private Context context;
  private int layoutOfficial, layoutImage, layoutVideo;
  private ArrayList<ArrayList<MediaItem>> allMediaItemList;

  private int total_checked;

  public AllMediaItemAdapter(Context context, int layoutOfficial, int layoutImage, int layoutVideo, ArrayList<ArrayList<MediaItem>> allMedialItemList){
    this.context = context;
    this.layoutOfficial = layoutOfficial;
    this.layoutImage = layoutImage;
    this.layoutVideo = layoutVideo;
    this.allMediaItemList = allMedialItemList;
    Collections.reverse(this.allMediaItemList); // December 25, 2020 -> Yesterday -> Today thành Today -> Yesterday -> December 25, 2020 trong listview

    this.total_checked = 0;
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


//    convertView.setOnTouchListener(new View.OnTouchListener() {
//      @Override
//      public boolean onTouch(View view, MotionEvent motionEvent) {
//        if (motionEvent.getPointerCount() == 2) return false;
//        else return true;
//      }
//    });

    TextView txtTitle = (TextView) convertView.findViewById(R.id.txtTitle);
    GridView gridView = (GridView) convertView.findViewById(R.id.grvMdICus);

    txtTitle.setText(allMediaItemList.get(position).get(0).getDateFormatMMMMddyyyy());

    DisplayMetrics displayMetrics = new DisplayMetrics();
    WindowManager wm = (WindowManager)context.getSystemService(Context.WINDOW_SERVICE);
    Display display = wm.getDefaultDisplay();
    display.getMetrics(displayMetrics);
    int mywidth = displayMetrics.widthPixels;

    Resources resources = context.getResources();
    int db = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 135, resources.getDisplayMetrics());


    gridView.setVerticalSpacing((mywidth-db*3)/3);

    MediaItemAdapter adapter = new MediaItemAdapter(context, layoutImage, layoutVideo, allMediaItemList.get(position));
    gridView.setAdapter(adapter);



//    gridView.setOnTouchListener(new View.OnTouchListener() {
//      @Override
//      public boolean onTouch(View view, MotionEvent motionEvent) {
//        if (motionEvent.getPointerCount() == 2) return false;
//        else return true;
//      }
//    });

    int bonusRow = allMediaItemList.get(position).size() % 3 == 0 ? 0 : 1;

    ViewGroup.LayoutParams layoutParams = gridView.getLayoutParams();
    layoutParams.height = db*(allMediaItemList.get(position).size()/3 + bonusRow);
    gridView.setLayoutParams(layoutParams);

    return convertView;
  }
}
