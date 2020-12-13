package com.example.albumanh;

import java.util.Date;

public class MediaItem {
  private String path;
  private String name;
  private String dateAdded;
  private String album;
  private long size;
  private boolean isCheck;
  private int type; // type = 1 => image, type = 2 => video

  public MediaItem(String path, String name, String dateAdded, String album, long size, int type) {
    this.path = path;
    this.name = name;
    this.dateAdded = dateAdded;
    this.album = album;
    this.size = size;
    this.isCheck = false;
    this.type = type;
  }

  //get
  public String getPath() {
    return path;
  }

  public String getName() {
    return name;
  }

  public String getDateAdded() {
    return dateAdded;
  }

  public String getAlbum() {
    return album;
  }

  public long getSize() {
    return size;
  }

  public boolean isCheck() {
    return isCheck;
  }

  public int getType() {
    return type;
  }

  public String getDateAddedString(){
    return (new Date(Long.parseLong(dateAdded + "000"))).toString();
  }

  //set
  public void setPath(String path) {
    this.path = path;
  }

  public void setName(String name) {
    this.name = name;
  }

  public void setDateAdded(String dateAdded) {
    this.dateAdded = dateAdded;
  }

  public void setAlbum(String album) {
    this.album = album;
  }

  public void setSize(long size) {
    this.size = size;
  }

  public void setCheck(boolean check) {
    isCheck = check;
  }

}
