package com.example.albumanh;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class MediaItem {
  private String path;
  private String name;
  private Date dateAdded;
  private String album;
  private long size;
  private boolean isCheck;
  private int type; // type = 1 => image, type = 2 => video

  public MediaItem(String path, String name, Date dateAdded, String album, long size, int type) {
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

  public Date getDateAdded() {
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
//    return (new Date(Long.parseLong(dateAdded + "000"))).toString();
    return dateAdded.toString();
  }

  public String getDateFormatMMMMddyyyy(){ //September 25, 2020
    SimpleDateFormat simpleDate =  new SimpleDateFormat("MMMM dd, yyyy");;
    Date CurrentDate = new Date();
    Date ThisDate = dateAdded;

    if ((CurrentDate.getYear()==ThisDate.getYear())&&(CurrentDate.getMonth()==ThisDate.getMonth())&&(CurrentDate.getDate()==ThisDate.getDate())) return "Today";
    if ((CurrentDate.getYear()==ThisDate.getYear())&&(CurrentDate.getMonth()==ThisDate.getMonth())&&(CurrentDate.getDate()==ThisDate.getDate()+1)) return "Yesterday";
//    return simpleDate.format(Long.parseLong(dateAdded + "000"));
    return simpleDate.format(dateAdded);
  }

  public String getDateFormatMMMMyyyy(){ //September 2020
    SimpleDateFormat simpleDate =  new SimpleDateFormat("MMMM yyyy");;
//    return simpleDate.format(Long.parseLong(dateAdded + "000"));
    return simpleDate.format(dateAdded);
  }

  public String getDateFormatyyyy(){ //September 2020
    SimpleDateFormat simpleDate =  new SimpleDateFormat("yyyy");;
//    return simpleDate.format(Long.parseLong(dateAdded + "000"));
    return simpleDate.format(dateAdded);
  }

  public void setPath(String path) {
    this.path = path;
  }

  public void setName(String name) {
    this.name = name;
  }

  public void setDateAdded(Date dateAdded) {
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
