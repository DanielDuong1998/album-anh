package com.example.albumanh;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.Toast;

import com.example.albumanh.Static.MediaModel;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

public class MainActivity extends FragmentActivity implements View.OnTouchListener {
  private final int REQUEST_CODE_STORAGE = 567;
  private final int REQUEST_CODE_CAMERA = 123;

  private TabLayout tl_Main; //đây là Tablayout
  private ViewPager2 vp_Main; //đây là ViewPager phiên bản 2
  private ImageButton imgbtnMenuMain; //ImageButton dấu 3 chấm
  int baseDist;

//  private ListView listAllMediaItem;
//  private AllMediaItemAdapter allMediaItemAdapter;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    ActivityCompat.requestPermissions(MainActivity.this, new String[] {Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE}, REQUEST_CODE_STORAGE);
  }

  @Override
  public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
    if(requestCode == REQUEST_CODE_STORAGE && grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
      MediaModel.init(this);
      getView();

    } else if(requestCode == REQUEST_CODE_CAMERA && grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){

    } else{
      Toast.makeText(MainActivity.this, "Permission denied !", Toast.LENGTH_SHORT).show();
    }
    super.onRequestPermissionsResult(requestCode, permissions, grantResults);
  }


  @Override
  public boolean onTouch(View view, MotionEvent motionEvent) {
    return true; //return true là thực hiện event này, nếu false là chuyển event này lên cho view tiếp theo mà ko thực hiện nó
  }


  private void getView(){
    tl_Main = (TabLayout) findViewById(R.id.tl_Main); //bind View từ JAVA đến file XML
    vp_Main = (ViewPager2) findViewById(R.id.vp_Main); //bind View từ JAVA đến file XML

    //Bước 1. khởi tạo Adapter của ViewPager và đổ vào ViewPager
    MyViewPagerAdapter myViewPagerAdapter = new MyViewPagerAdapter(this); //tạo Adapter đổ dữ liệu lên ViewPager ở đây có Photos và Albums nên dữ liệu (dataset) chỉ có 2 cái, lướt qua lướt lại
    //ViewPager là dạng View Component lướt ngang như kiểu slider quảng cáo trượt ngang trong web
    vp_Main.setAdapter(myViewPagerAdapter); //tiến hành đổ Adapter vào ViewPager



//    vp_Main.setOnTouchListener(new View.OnTouchListener() { //set sự kiện khi dùng 2 ngón để zoom in, zoom out trong khu vực layout này mà thôi (trong khu vực ViewPager thứ 1)
//      int baseDist;
//
//      @Override
//      public boolean onTouch(View v, MotionEvent event) {
//        if (event.getPointerCount() == 2) { //bắt sự kiện 2 ngón chạm vào
//          int action = event.getAction(); //biến action này sẽ là gốm 2 thành phần: ID của Action, và loại Action
//          int mainaction = action&MotionEvent.ACTION_MASK; //dùng biểu thức "&" để chỉ lấy action
//          if (mainaction == MotionEvent.ACTION_POINTER_DOWN) { //khi bắt đầu đặt ngón tay xuống màn hình, lấy tọa độ vị trí đặt ngón tay rồi gán vào baseDist
//            baseDist = getDistance(event); //hàm tính toán khoảng cách getDistance định nghĩa ở phía dưới
//          } else { //ngược lại là sự kiện move 2 ngón tay hoặc sự kiện khác thì tính khoảng cách mới trừ đi khoảng cách cũ nếu vượt một khoảng nhất định thì thực hiện gì đó (ở ví dụ này là 200dp)
//            if (getDistance(event)-baseDist > 200 ) {
//              Toast.makeText(MainActivity.this,"Đã zoom in đủ khoảng cách nhất định",Toast.LENGTH_SHORT).show();
//              //làm gì đó ví dụ chuyển sang fragment khác
//            }
//            if (getDistance(event)-baseDist < -200 ) {
//              Toast.makeText(MainActivity.this,"Đã zoom out đủ khoảng cách nhất định",Toast.LENGTH_SHORT).show();
//              //làm gì đó ví dụ chuyển sang fragment khác
//            }
//          }
//        }
//        return true;
//      }
//      private int getDistance(MotionEvent event){
//        int dx = (int) (event.getX(0)-event.getX(1));
//        int dy = (int) (event.getY(0)-event.getY(1));
//        return (int) (Math.sqrt(dx*dx+dy*dy));
//      }
//    });

    //Bước 2. khởi tạo TabLayout và liên kết mỗi Tab tương ứng với mỗi ViewPager, sau đó gắn lên màn hình

    TabLayoutMediator objTabLayoutMediator = new TabLayoutMediator(tl_Main, vp_Main, new TabLayoutMediator.TabConfigurationStrategy() { //khởi tạo TabLayout (tl_Main) và gắn kết tương ứng với ViewPager (vp_Main)
      @Override
      public void onConfigureTab(@NonNull TabLayout.Tab tab, int position) {
        switch (position)
        {
          case 0: {
            tab.setText("Photos"); //đặt tab thứ 1: tên Photos
            break;
          }
          case 1: {
            tab.setText("albums"); //đặt tab thứ 1: tên albums
            break;
          }
        }
      }
    });
    objTabLayoutMediator.attach(); //gắn nó lên màn hình giao diện
    //vậy là đã xong phần TabLayout và ViewPager, ở ViewPager đầu là hiển thị tất cả hình ảnh (quản lý bởi fragment "PhotosFragment.java" và layout "fragment_photos.xml")
    //còn ở ViewPager thứ 2 là hiển thị tất cả albums (quản lý bởi fragment "AlbumsFragment.java" và layout "fragment_albums.xml")

    //đoạn code sau là xử lý khi bấm vào hình ảnh dấu 3 chấm sẽ hiện thị một popup menu
    imgbtnMenuMain = (ImageButton) findViewById(R.id.imgbtnMenuMain);
    imgbtnMenuMain.setOnClickListener(new View.OnClickListener() { //set sự kiện khi ai đó bâm vào nút menu 3 châm thì sẽ thực hiện đoạn dưới
      @Override
      public void onClick(View v) {

        PopupMenu popup = new PopupMenu(MainActivity.this,v); //tạo popup menu và hiển thị popup ngay phía dưới v (v là tham số thứ 2)

        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() { //gán bắt sự kiện khi người dùng click vào các item con trong menu
          @Override
          public boolean onMenuItemClick(MenuItem item) {
            switch (item.getItemId()) {
              case R.id.item_Select: {
                Toast.makeText(MainActivity.this, "đã chọn", Toast.LENGTH_SHORT).show();
                return true;
              }
              case R.id.item_Setting:
                Toast.makeText(MainActivity.this,"đã cài đặt",Toast.LENGTH_SHORT).show();
                return true;
              default:
                return false;
            }
          }
        });

        popup.inflate(R.menu.popup_menu_three_dot_main); //inflate là biến giao diện file XML thành giao diện JAVA code, từ file popup_menu.xml gộp bỏ trong thư mục res/menu thành giao diện một View ở JAVA code
        popup.show(); //hiển thị popup menu lên khi người dùng click vào nút 3 chấm

      }
    });

//    listAllMediaItem = (ListView) findViewById(R.id.listAllMediaItem);
//    System.out.println("size he: " + MediaModel.allMediaItems.get(0).size());
//    allMediaItemAdapter = new AllMediaItemAdapter(this,R.layout.custom_media_item_with_title, R.layout.custom_images_layout, R.layout.custom_videos_layout, MediaModel.allMediaItems);
//    System.out.println("adapter: " + allMediaItemAdapter);
//    listAllMediaItem.setAdapter(allMediaItemAdapter);
  }

}

//đoạn này mình có thể tách thành một file AdapterABCXYZ.java
class MyViewPagerAdapter extends FragmentStateAdapter {
  private String listTab[] = {"Photos","Albums"};

  public MyViewPagerAdapter(@NonNull FragmentActivity fragmentActivity) {
    super(fragmentActivity);
  }


  @NonNull
  @Override
  public Fragment createFragment(int position) {
    if (position == 0) return new PhotosFragment();
    if (position == 1) return new AlbumsFragment();
    return null;
  }

  @Override
  public int getItemCount() {
    return listTab.length;
  }
}