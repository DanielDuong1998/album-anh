package com.example.albumanh.Static;

import android.app.Activity;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;

import com.example.albumanh.Album;
import com.example.albumanh.MediaItem;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;

public class MediaModel {
  public static ArrayList<ArrayList<MediaItem>> allMediaItems;
  public static ArrayList<Album> albums;

  public static void init(Activity activity){
    loadAllMediaItem(activity);

  }

  private static void loadAllMediaItem(Activity activity){
    allMediaItems = new ArrayList<>();
    albums = new ArrayList<>();

    ArrayList<MediaItem> images = loadAllSimpleMediaItem(activity, 1);
    ArrayList<MediaItem> videos = loadAllSimpleMediaItem(activity, 2);

    updateSimpleItemToArray(images);
    updateSimpleItemToArray(videos);


    sortAllMediaItem(1);
  }

  private static void sortAllMediaItem(int type){
    if(type == 1){
      for(int i = 0; i < allMediaItems.size() - 1; i++) {
        for(int j = i + 1; j < allMediaItems.size(); j++){
          if(allMediaItems.get(i).get(0).getDateAdded().getTime() > allMediaItems.get(j).get(0).getDateAdded().getTime()){
            Collections.swap(allMediaItems, i, j);
          }
        }
      }
    }
  }

  // start children function loadAllMediaItem
  private static boolean compareTime(MediaItem item1, MediaItem item2, int type){
    // type = 1 => compare dd mm yyyy, type = 2 => compare mm yyyy, type = 3 => compare yyyy
    Date date1 = item1.getDateAdded();
    Date date2 = item2.getDateAdded();
    Calendar c1 = Calendar.getInstance();
    c1.setTime(date1);
    Calendar c2 = Calendar.getInstance();
    c2.setTime(date2);

    if(type == 1){
      return (c1.get(Calendar.YEAR) == c2.get(Calendar.YEAR) && c1.get(Calendar.MONTH) == c2.get(Calendar.MONTH) && c1.get(Calendar.DATE) == c2.get(Calendar.DATE));
    }
    else if(type == 2){
      return c1.get(Calendar.YEAR) == c2.get(Calendar.YEAR) && c1.get(Calendar.MONTH) == c2.get(Calendar.MONTH);
    }
    else return c1.get(Calendar.YEAR) == c2.get(Calendar.YEAR);
  }

  private static ArrayList<MediaItem> loadAllSimpleMediaItem(Activity activity, int role){
    ArrayList<MediaItem> mediaItems = new ArrayList<>();

    Uri uri = role == 1 ? MediaStore.Images.Media.EXTERNAL_CONTENT_URI : MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
    String[] projection = {MediaStore.MediaColumns.DATA,
            MediaStore.Images.Media.BUCKET_DISPLAY_NAME,
            MediaStore.Images.Media.DATE_MODIFIED,
            MediaStore.Images.Media.DISPLAY_NAME,
            MediaStore.Images.Media.SIZE};

    Cursor cursor = activity.getContentResolver().query(uri, projection, null, null, null);
    while (cursor.moveToNext())
    {
      String path = cursor.getString(cursor.getColumnIndex(MediaStore.MediaColumns.DATA));
      File filepath = new File(path);
      Date dateAdded = new Date(filepath.lastModified());
      long filesize = filepath.length();


//      File file = new File(path);
      loadAlbumByPath(path, role);



      mediaItems.add(new MediaItem(cursor.getString(cursor.getColumnIndex(MediaStore.MediaColumns.DATA)),
              cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DISPLAY_NAME)),
              dateAdded,
              cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.BUCKET_DISPLAY_NAME)),
              filesize, role));
    }
    return mediaItems;
  }

  private static void updateSimpleItemToArray(ArrayList<MediaItem> mediaItemSimple){
    for(MediaItem item : mediaItemSimple){
      if(allMediaItems.size() == 0){
        ArrayList<MediaItem> list = new ArrayList<>();
        list.add(item);
        allMediaItems.add(list);
        System.out.println("khong vao");
      }
      else {
        boolean flag = false;
        for(int i = 0; i < allMediaItems.size(); i++) {
          System.out.println("item: " + item.getDateFormatMMMMddyyyy());
          System.out.println("item1: " + allMediaItems.get(i).get(0).getDateFormatMMMMddyyyy());
          if(compareTime(item, allMediaItems.get(i).get(0), 1)){
            System.out.println("vao");
            allMediaItems.get(i).add(item);
            flag = true;
            break;
          }
        }
        if(!flag){
          System.out.println("khong vbao");
          ArrayList<MediaItem> list = new ArrayList<>();
          list.add(item);
          allMediaItems.add(list);
        }
      }
    }
  }
  // end children function loadAllMediaItem

  private static void loadAlbumByPath(String path, int type){
    String[] listStr = path.split("/");
    String name = listStr[listStr.length - 2];
    if(isExistNameInAlbumsList(name)){
      // album da ton tai
      return;
    }
    else {
      //album chua ton tai
      String pathAlbum = path.substring(0, path.lastIndexOf("/"));
      File file = new File(pathAlbum);
      int imgCount = file.list().length;
      long createDate = file.lastModified();
      albums.add(new Album(name, pathAlbum, path, imgCount, createDate, false,  type));
    }

  }

  private static boolean isExistNameInAlbumsList(String name){
    for(Album album : albums){
      if (album.getName().equals(name))
        return true;
    }
    return false;
  }




}
