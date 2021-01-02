package com.example.albumanh;


import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.ActionMode;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.albumanh.MediaItem;
import com.example.albumanh.MediaItemAdapter;
import com.example.albumanh.Static.MediaModel;
import com.example.group14_album_anh.MainActivity;
import com.example.group14_album_anh.R;
import com.example.group14_album_anh.activities.ImageActivity;
import com.example.group14_album_anh.activities.ListMediaActivity;
import com.example.group14_album_anh.activities.VideoActivity;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


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
  public View getView(final int position, View convertView, ViewGroup parent) {
    LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    convertView = inflater.inflate(layoutOfficial, null);


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

    final MediaItemAdapter adapter = new MediaItemAdapter(context, layoutImage, layoutVideo, allMediaItemList.get(position));
    gridView.setAdapter(adapter);
    gridView.setChoiceMode(AbsListView.CHOICE_MODE_MULTIPLE_MODAL);
    gridView.setMultiChoiceModeListener(new AbsListView.MultiChoiceModeListener() {
      int countCheck;
      ArrayList<String> positionList = new ArrayList<>();


      @Override
      public void onItemCheckedStateChanged(ActionMode mode, int position1, long id, boolean checked) {
        if(checked){
          allMediaItemList.get(position).get(position1).setCheck(true);
          adapter.notifyDataSetChanged();
          countCheck++;
        }
        else{
          allMediaItemList.get(position).get(position1).setCheck(false);
          adapter.notifyDataSetChanged();
          countCheck--;
        }
        mode.setTitle(countCheck + "");
      }

      @Override
      public boolean onCreateActionMode(ActionMode mode, Menu menu) {
        MenuInflater menuInflater = ((AppCompatActivity)context).getMenuInflater();
        menuInflater.inflate(R.menu.action_mode_toolbar, menu);
        MenuItem item = menu.findItem(R.id.menuEdit);
        item.setVisible(false);
        return true;
      }

      @Override
      public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
        System.out.println("onPrepareAction");
        return false;
      }

      @Override
      public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
        System.out.println("onActionItemClicked");
        switch (item.getItemId())
        {
          case R.id.menuDelete:
            for(int i = 0; i < allMediaItemList.get(position).size(); i++)
            {
              if(allMediaItemList.get(position).get(i).isCheck())
              {
                positionList.add(allMediaItemList.get(position).get(i).getPath());
              }
            }
            dialogWarning(positionList, adapter, position);
            mode.finish();
            return true;
        }
        return false;
      }

      @Override
      public void onDestroyActionMode(ActionMode mode) {
        System.out.println("onDestroy");

        countCheck = 0;
        for(int i = 0; i < allMediaItemList.get(position).size(); i++) {
          if(allMediaItemList.get(position).get(i).isCheck()){
            allMediaItemList.get(position).get(i).setCheck(false);
          }
        }
      }
    });

    gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
      @Override
      public void onItemClick(AdapterView<?> parent, View view, int position1, long id) {
        System.out.println("path: " + allMediaItemList.get(position).get(position1).getPath());

        if(allMediaItemList.get(position).get(position1).getType() == 1){
          Intent intent1 = new Intent((AppCompatActivity) context, ImageActivity.class);
          intent1.putExtra("position", position);
          intent1.putExtra("position1", position1);
          ((AppCompatActivity)context).startActivity(intent1);
        }
        else {
          Intent intent1 = new Intent((AppCompatActivity) context, VideoActivity.class);
          intent1.putExtra("position", position);
          intent1.putExtra("position1", position1);
          ((AppCompatActivity)context).startActivity(intent1);
        }

      }
    });


    int bonusRow = allMediaItemList.get(position).size() % 3 == 0 ? 0 : 1;

    ViewGroup.LayoutParams layoutParams = gridView.getLayoutParams();
    layoutParams.height = db*(allMediaItemList.get(position).size()/3 + bonusRow);
    gridView.setLayoutParams(layoutParams);

    return convertView;
  }

  private void dialogWarning(final List<String> list, final MediaItemAdapter adapter,final int pos) {
    AlertDialog.Builder dialog = new AlertDialog.Builder(context, R.style.Theme_Dialog);
    dialog.setTitle("Warning!");
    dialog.setMessage("Are you sure want to delete this album ?");
    dialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
      @Override
      public void onClick(DialogInterface dialog, int which) {
        File file;
        for(int i=0;i<list.size();i++)
        {
          file = new File(list.get(i));
          file.delete();

          MediaScannerConnection.scanFile(context, new String[]{file.toString()},
                  null,
                  new MediaScannerConnection.OnScanCompletedListener() {
                    @Override
                    public void onScanCompleted(String path, Uri uri) {

                    }
                  }
          );
        }

        Toast.makeText(context, "Deleted success!", Toast.LENGTH_SHORT).show();
        dialog.dismiss();
        for(int i=0;i<list.size();i++)
        {
          for(int j=0;j<allMediaItemList.get(pos).size();j++)
          {
            if(allMediaItemList.get(pos).get(j).getPath().equals(list.get(i)))
            {
              allMediaItemList.get(pos).remove(j);
            }
          }
        }
        adapter.notifyDataSetChanged();
      }
    });


    dialog.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
      @Override
      public void onClick(DialogInterface dialog, int which) {
        dialog.dismiss();
      }
    });

    dialog.show();

  }

}
