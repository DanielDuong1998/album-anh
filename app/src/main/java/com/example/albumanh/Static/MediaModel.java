package com.example.albumanh.Static;

import android.app.Activity;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;

import com.example.albumanh.Album;
import com.example.albumanh.MediaItem;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;

public class MediaModel {
  public static ArrayList<ArrayList<MediaItem>> allMediaItems;
  public static ArrayList<Album> albums;

  public static void init(Activity activity){
    loadAllMediaItem(activity);

    for(int i = 0; i < albums.size(); i++) {
      System.out.println("album thu: " + (i + 1));
      System.out.println("ten: " + albums.get(i).getName());
      System.out.println("so luong: " + albums.get(i).getMediaItemSize());
      System.out.println("ngay thang: " + (new Date(albums.get(i).getDateCreate())).toString());
    }
  }

  private static void loadAllMediaItem(Activity activity){
    allMediaItems = new ArrayList<>();
    albums = new ArrayList<>();

    ArrayList<MediaItem> images = loadAllSimpleMediaItem(activity, 1);
    ArrayList<MediaItem> videos = loadAllSimpleMediaItem(activity, 2);

    updateSimpleItemToArray(images);
    updateSimpleItemToArray(videos);

    for(ArrayList<MediaItem> mdis : allMediaItems){
      System.out.println("title: " + new Date(Long.parseLong(mdis.get(0).getDateAdded() + "000")).toString());
      System.out.println("size: " + mdis.size());
      System.out.println("=======================>");
    }
  }

  // start children function loadAllMediaItem
  private static boolean compareTime(MediaItem item1, MediaItem item2, int type){
    // type = 1 => compare dd mm yyyy, type = 2 => compare mm yyyy, type = 3 => compare yyyy
    String dateStr1 = item1.getDateAdded() + "000";
    String dateStr2 = item2.getDateAdded() + "000";
    Date date1 = new Date(Long.parseLong(dateStr1));
    Date date2 = new Date(Long.parseLong(dateStr2));

    if(type == 1){
      return (date1.getYear() == date2.getYear() && date1.getMonth() == date2.getMonth() && date1.getDay() == date2.getDay());
    }
    else if(type == 2){
      return date1.getYear() == date2.getYear() && date1.getMonth() == date2.getMonth();
    }
    else return date1.getYear() == date2.getYear();
  }

  private static ArrayList<MediaItem> loadAllSimpleMediaItem(Activity activity, int role){
    ArrayList<MediaItem> mediaItems = new ArrayList<>();

    Uri uri = role == 1 ? MediaStore.Images.Media.EXTERNAL_CONTENT_URI : MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
    String[] projection = {MediaStore.MediaColumns.DATA,
            MediaStore.Images.Media.BUCKET_DISPLAY_NAME,
            MediaStore.Images.Media.DATE_ADDED,
            MediaStore.Images.Media.DISPLAY_NAME,
            MediaStore.Images.Media.SIZE};

    Cursor cursor = activity.getContentResolver().query(uri, projection, null, null, null);
    while (cursor.moveToNext())
    {
      String path = cursor.getString(cursor.getColumnIndex(MediaStore.MediaColumns.DATA));
      File filepath = new File(path);
      long filesize = filepath.length();


//      File file = new File(path);
      loadAlbumByPath(path, role);



      mediaItems.add(new MediaItem(cursor.getString(cursor.getColumnIndex(MediaStore.MediaColumns.DATA)),
              cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DISPLAY_NAME)),
              cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATE_ADDED)),
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
      }
      else {
        boolean flag = false;
        for(int i = 0; i < allMediaItems.size(); i++) {
          if(compareTime(item, allMediaItems.get(i).get(0), 1)){
            allMediaItems.get(i).add(item);
            flag = true;
            break;
          }
        }
        if(!flag){
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

//    File file = new File(path.substring(0, path.lastIndexOf("/")));
//    System.out.println("path folder: " + path.substring(0, path.lastIndexOf("/")));
//    System.out.println("file name: " + file.getName());
//    String[] list = file.list();
//    System.out.println("list..: " + list.length);
//
//    String[] ar = path.split("/");
//    System.out.println("ar...: " + ar[ar.length - 2]);
  }

  private static boolean isExistNameInAlbumsList(String name){
    for(Album album : albums){
      if (album.getName().equals(name))
        return true;
    }
    return false;
  }




}
