package com.example.albumanh;

public class Album {
  private String name;
  private String path;
  private String pathAvatar;
  private int mediaItemSize;
  private long dateCreate;
  private boolean checkbox;
  private int type; // type = 1 => img, type = 2 => video

  public Album(String name, String path, String pathAvatar, int imgCount, long createDate, boolean checkbox, int type) {
    this.name = name;
    this.path = path;
    this.pathAvatar = pathAvatar;
    this.mediaItemSize = imgCount;
    this.dateCreate = createDate;
    this.checkbox = checkbox;
    this.type = type;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getPathAvatar() {
    return pathAvatar;
  }

  public void setPathAvatar(String path) {
    this.pathAvatar = path;
  }

  public int getMediaItemSize() {
    return mediaItemSize;
  }

  public void setMediaItemSize(int mediaItemSize) {
    this.mediaItemSize = mediaItemSize;
  }

  public long getDateCreate() {
    return dateCreate;
  }

  public void setDateCreate(long dateCreate) {
    this.dateCreate = dateCreate;
  }

  public boolean isCheckbox() {
    return checkbox;
  }

  public void setCheckbox(boolean checkbox) {
    this.checkbox = checkbox;
  }

  public int getType() {
    return type;
  }


}
