package com.example.tablayoutvaviewpager2;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.PopupMenu;
import android.widget.Toast;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

public class MainActivity extends FragmentActivity {
    private TabLayout tl_Main; //đây là Tablayout
    private ViewPager2 vp_Main; //đây là ViewPager phiên bản 2

    private ImageButton imgbtnMenuMain; //ImageButton dấu 3 chấm

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tl_Main = (TabLayout) findViewById(R.id.tl_Main); //bind View từ JAVA đến file XML
        vp_Main = (ViewPager2) findViewById(R.id.vp_Main); //bind View từ JAVA đến file XML

        //Bước 1. khởi tạo Adapter của ViewPager và đổ vào ViewPager
        MyViewPagerAdapter myViewPagerAdapter = new MyViewPagerAdapter(this); //tạo Adapter đổ dữ liệu lên ViewPager ở đây có Photos và Albums nên dữ liệu (dataset) chỉ có 2 cái, lướt qua lướt lại
                                                                              //ViewPager là dạng View Component lướt ngang như kiểu slider quảng cáo trượt ngang trong web
        vp_Main.setAdapter(myViewPagerAdapter); //tiến hành đổ Adapter vào ViewPager

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

                popup.inflate(R.menu.popup_menu); //inflate là biến giao diện file XML thành giao diện JAVA code, từ file popup_menu.xml gộp bỏ trong thư mục res/menu thành giao diện một View ở JAVA code
                popup.show(); //hiển thị popup menu lên khi người dùng click vào nút 3 chấm

            }
        });
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
