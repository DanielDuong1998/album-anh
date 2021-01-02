package com.example.group14_album_anh.activities;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ListActivity;
import android.content.ContentResolver;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.ActionMode;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import com.example.albumanh.MediaItem;
import com.example.albumanh.MediaItemAdapter;
import com.example.group14_album_anh.R;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ListMediaActivity extends AppCompatActivity {
  private Uri uri;
  private GridView gridView;
  public static ArrayList<MediaItem> listMedia;
  private MediaItemAdapter adapter;
  private String albumName;
  private int type;

  @Override
  protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_image__list);

    getWidget();
    final Intent intent = getIntent();
    albumName = intent.getStringExtra("album_name");
    type = intent.getIntExtra("type", 1);
    getListMediaInAlbum();
    adapter = new MediaItemAdapter(ListMediaActivity.this, R.layout.custom_images_layout, R.layout.custom_videos_layout, listMedia);
    gridView.setAdapter(adapter);
    gridView.setChoiceMode(AbsListView.CHOICE_MODE_MULTIPLE_MODAL);
    gridView.setMultiChoiceModeListener(new AbsListView.MultiChoiceModeListener() {
      int countCheck;
      ArrayList<String> positionList = new ArrayList<>();

      @Override
      public void onItemCheckedStateChanged(ActionMode mode, int position, long id, boolean checked) {
        if(checked){
          listMedia.get(position).setCheck(true);
          adapter.notifyDataSetChanged();
          countCheck++;
        }
        else{
          listMedia.get(position).setCheck(false);
          adapter.notifyDataSetChanged();
          countCheck--;
        }
        mode.setTitle(countCheck + "");
      }

      @Override
      public boolean onCreateActionMode(ActionMode mode, Menu menu) {
        MenuInflater menuInflater = (ListMediaActivity.this).getMenuInflater();
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
            for(int i = 0; i < listMedia.size(); i++)
            {
              if(listMedia.get(i).isCheck())
              {
                positionList.add(listMedia.get(i).getPath());
              }
            }
            dialogWarning(positionList);
            mode.finish();
            return true;
        }
        return false;
      }

      @Override
      public void onDestroyActionMode(ActionMode mode) {
        System.out.println("onDestroy");

        countCheck = 0;
        for(int i = 0; i < listMedia.size(); i++) {
          if(listMedia.get(i).isCheck()){
            listMedia.get(i).setCheck(false);
          }
        }
      }
    });

    gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
      @Override
      public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        System.out.println("path: " + listMedia.get(position).getPath());

        if(listMedia.get(position).getType() == 1){
          Intent intent1 = new Intent(ListMediaActivity.this, ImageActivityInAlbum.class);
          intent1.putExtra("position", position);
          intent1.putExtra("albumName", albumName);
          startActivity(intent1);
        }
        else {
          Intent intent1 = new Intent(ListMediaActivity.this, VideoInAlbumActivity.class);
          intent1.putExtra("position", position);
          intent1.putExtra("albumName", albumName);
          startActivity(intent1);
        }

      }
    });
  }

  private void getWidget(){
    gridView = (GridView) findViewById(R.id.gridImage);
    listMedia = new ArrayList<>();
  }

  private void getListMediaInAlbum(){
    uri = type == 1 ? MediaStore.Images.Media.EXTERNAL_CONTENT_URI : MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
    ContentResolver contentResolver = getContentResolver();
    String[] selectionArg = {"%" + albumName + "%"};
    String selection = MediaStore.Images.Media.DATA + " LIKE ?";
    String  [] projectionBucket = {MediaStore.MediaColumns.DATA,
            MediaStore.Images.Media.BUCKET_DISPLAY_NAME,
            MediaStore.Images.Media.DATE_TAKEN,
            MediaStore.Images.Media.DISPLAY_NAME,
            MediaStore.Images.Media.SIZE};


    Cursor cursor = contentResolver.query(uri, projectionBucket, selection, selectionArg, null);
    while (cursor.moveToNext())
    {
      String path = cursor.getString(cursor.getColumnIndex(MediaStore.MediaColumns.DATA));
      File filepath = new File(path);
      Date dateAdded = new Date(filepath.lastModified());
      long filesize = filepath.length();

      listMedia.add(new MediaItem(cursor.getString(cursor.getColumnIndex(MediaStore.MediaColumns.DATA)),
        cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DISPLAY_NAME)),
        dateAdded,
        cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.BUCKET_DISPLAY_NAME)),
        filesize, type));
    }
  }

  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    MenuInflater menuInflater = getMenuInflater();
    menuInflater.inflate(R.menu.image_menu, menu);
    return true;
  }

  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
    switch (item.getItemId())
    {
      case android.R.id.home:
        this.finish();
        return true;
      case R.id.addPhotos:
//        Intent intent = new Intent(ListMediaActivity.this,ImagePicker.class);
//        intent.putExtra("ALBUM_NAME",albumName);
//        startActivity(intent);
        break;
    }
    return super.onOptionsItemSelected(item);
  }

  private void dialogWarning(final List<String> list) {
    AlertDialog.Builder dialog = new AlertDialog.Builder(this, R.style.Theme_Dialog);
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

          MediaScannerConnection.scanFile(ListMediaActivity.this, new String[]{file.toString()},
                  null,
                  new MediaScannerConnection.OnScanCompletedListener() {
                    @Override
                    public void onScanCompleted(String path, Uri uri) {

                    }
                  }
          );
        }

        Toast.makeText(ListMediaActivity.this, "Deleted success!", Toast.LENGTH_SHORT).show();
        dialog.dismiss();
        for(int i=0;i<list.size();i++)
        {
          for(int j=0;j<listMedia.size();j++)
          {
            if(listMedia.get(j).getPath().equals(list.get(i)))
            {
              listMedia.remove(j);
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

  @Override
  protected void onResume() {
    super.onResume();
    adapter.notifyDataSetChanged();
  }
}
