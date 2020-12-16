package com.example.albumanh;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.albumanh.Static.MediaModel;

import java.util.ArrayList;

public class PhotosFragment extends Fragment {
    private View RootView;
    private ListView listAllMediaItem;
    private AllMediaItemAdapter allMediaItemAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        RootView = inflater.inflate(R.layout.fragment_photos,container,false);

//        RootView.setOnTouchListener(new View.OnTouchListener() {
//            @Override
//            public boolean onTouch(View view, MotionEvent motionEvent) {
//                if (motionEvent.getPointerCount() == 2) return false;
//                else return true;
//            }
//        });


        //===================================================================================
//        RootView.setOnTouchListener(new View.OnTouchListener() { //set sự kiện khi dùng 2 ngón để zoom in, zoom out trong khu vực layout này mà thôi (trong khu vực ViewPager thứ 1)
//            int baseDist;
//            @Override
//            public boolean onTouch(View v, MotionEvent event) {
//                if (event.getPointerCount() == 2) { //bắt sự kiện 2 ngón chạm vào
//                    int action = event.getAction(); //biến action này sẽ là gốm 2 thành phần: ID của Action, và loại Action
//                    int mainaction = action&MotionEvent.ACTION_MASK; //dùng biểu thức "&" để chỉ lấy action
//                    if (mainaction == MotionEvent.ACTION_POINTER_DOWN) { //khi bắt đầu đặt ngón tay xuống màn hình, lấy tọa độ vị trí đặt ngón tay rồi gán vào baseDist
//                        baseDist = getDistance(event); //hàm tính toán khoảng cách getDistance định nghĩa ở phía dưới
//                    } else { //ngược lại là sự kiện move 2 ngón tay hoặc sự kiện khác thì tính khoảng cách mới trừ đi khoảng cách cũ nếu vượt một khoảng nhất định thì thực hiện gì đó (ở ví dụ này là 200dp)
//                        if (getDistance(event)-baseDist > 200 ) {
//                            Toast.makeText(getActivity(),"Đã zoom in đủ khoảng cách nhất định",Toast.LENGTH_SHORT).show();
//                            //làm gì đó ví dụ chuyển sang fragment khác
//                        }
//                        if (getDistance(event)-baseDist < -200 ) {
//                            Toast.makeText(getActivity(),"Đã zoom out đủ khoảng cách nhất định",Toast.LENGTH_SHORT).show();
//                            //làm gì đó ví dụ chuyển sang fragment khác
//                        }
//                    }
//                }
//                return true;
//            }
////            @Override
////            public boolean onTouchEvent(MotionEvent me) {
////                return true;
////            }
//            private int getDistance(MotionEvent event){
//                int dx = (int) (event.getX(0)-event.getX(1));
//                int dy = (int) (event.getY(0)-event.getY(1));
//                return (int) (Math.sqrt(dx*dx+dy*dy));
//            }
//        });

        //===================================================================================
        listAllMediaItem = (ListView) RootView.findViewById(R.id.listAllMediaItem);
        System.out.println("size he: " + MediaModel.allMediaItems.get(0).size());
        allMediaItemAdapter = new AllMediaItemAdapter(getActivity(),R.layout.custom_media_item_with_title, R.layout.custom_images_layout, R.layout.custom_videos_layout, MediaModel.allMediaItems);
        System.out.println("adapter: " + allMediaItemAdapter);
        listAllMediaItem.setAdapter(allMediaItemAdapter);

//        listAllMediaItem.setOnTouchListener(new View.OnTouchListener() {
//            @Override
//            public boolean onTouch(View view, MotionEvent motionEvent) {
//                if (motionEvent.getPointerCount() == 2) return false;
//                else return true;
//            }
//        });

        //===================================================================================

        return RootView;
    }
}