package com.mirim.bokrim;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.mirim.bokrim.Datas.Store;
import com.mirim.bokrim.Datas.StoreList;
import com.mirim.bokrim.ListView.ListViewMapAdapter;
import com.sothree.slidinguppanel.SlidingUpPanelLayout;

import net.daum.mf.map.api.MapView;

public class MapFragment extends Fragment {
    public MapFragment() {}

    static SlidingUpPanelLayout slidingUpPanelLayout; // 슬라이딩 레이아웃

    public static ListView listData; // 가게 정보들 보이는 리스트뷰

    static LinearLayout linearResult; // 가게 상세 정보 페이지
    FrameLayout frameBtnBack; // 되돌아가기 버튼

    public static TextView textStoreName, textStoreAdd; // 가게 상세 정보

    public static int storeId;

    public static boolean isSlidingDown = true;
    public static boolean isDragList = true;

    public static MapFragment newInstance() {
        return new MapFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_map, container, false);

        listData = v.findViewById(R.id.list_map);
        slidingUpPanelLayout = v.findViewById(R.id.sliding_layout);

        textStoreName = v.findViewById(R.id.text_store_name);
        textStoreAdd = v.findViewById(R.id.text_store_address);

        linearResult = v.findViewById(R.id.linear_result);
        frameBtnBack = v.findViewById(R.id.frame_btn_back);

        EditText editSearch = v.findViewById(R.id.editTextFilter);

        Log.d("드래그뷰 슬라이딩", "MapFragment 입성!!!");
        // 슬라이딩 레이아웃
        Log.d("슬라이딩", "올라오라고 햇는디 그랫는디"+slidingUpPanelLayout.getPanelState());
        if(isSlidingDown) slidingUpPanelLayout.setPanelState(SlidingUpPanelLayout.PanelState.COLLAPSED);
        if(isDragList){
            listData.setVisibility(View.VISIBLE);
            linearResult.setVisibility(View.INVISIBLE);
            frameBtnBack.setVisibility(View.INVISIBLE);
        }

        //카카오맵
        // TODO : 프래그먼트 닫힐때마다 지도 지워주기
        MapView mapView = new MapView(getContext());

        ViewGroup mapViewContainer = (ViewGroup) v.findViewById(R.id.map_view);
        mapViewContainer.addView(mapView);

        //리스트뷰 연결
        ListViewMapAdapter adapter = new ListViewMapAdapter();
        listData.setAdapter(adapter);

        // TODO: 실제 가게 정보 입력
        for(int i = 0; i< StoreList.storeList.size(); i++)
            adapter.addItem(StoreList.storeList.get(i).title, StoreList.storeList.get(i).address, StoreList.storeList.get(i).id);

        // TODO: 리스트뷰 아이템 온클릭 xml 전환 전 ripple 나타나게 하기
        listData.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                frameBtnBack.setVisibility(View.VISIBLE);
                changeDragView(true);
                Log.d("리스트뷰", "실행됏쮸"+Integer.toString(storeId)+Integer.toString(position));

                for(int i = 0; i< StoreList.storeList.size(); i++){
                    if(i==position){
                        Log.d("리스트뷰", "나는 안 되는 것이냐!!!"+StoreList.storeList.get(i).title);

                        textStoreName.setText(StoreList.storeList.get(i).title);
                        textStoreAdd.setText(StoreList.storeList.get(i).address);

                        Log.d("리스트뷰", "if문 안에 "+textStoreName.getText().toString());
                    }
                    Log.d("리스트뷰", "포문 안에 "+textStoreName.getText().toString());
                }
            }
        });


        Log.d("리스트뷰", "온클릭 밖에 "+textStoreName.getText().toString());


        Log.d("드래그뷰", Integer.toString(linearResult.getVisibility())+" "+Integer.toString(listData.getVisibility()));

        // 되돌아가기 버튼
        frameBtnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeDragView(false);
                frameBtnBack.setVisibility(View.INVISIBLE);
            }
        });

        //검색어 입력하기 전 자식 Fragment 이동
        editSearch.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                ((MainActivity)getActivity()).replaceFragment(MapParentFragment.newInstance());
            }
        });
        editSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity)getActivity()).replaceFragment(MapParentFragment.newInstance());
            }
        });

        return v;
    }

    public static void changeDragView(boolean b){
        Log.d("드래그뷰", Integer.toString(linearResult.getVisibility())+" "+Integer.toString(listData.getVisibility()));
        if(b){ //가게 상세 정보: true
            linearResult.setVisibility(View.VISIBLE);
            listData.setVisibility(View.INVISIBLE);
        }else{ //리스트뷰: false
            linearResult.setVisibility(View.INVISIBLE);
            listData.setVisibility(View.VISIBLE);
        }
        Log.d("드래그뷰", Integer.toString(linearResult.getVisibility())+" "+Integer.toString(listData.getVisibility()));
    }

    public void displayMessage(String message){
        storeId = Integer.parseInt(message);
    }

    public static void setTextStore(){
        for(int i = 0; i< StoreList.storeList.size(); i++){
            if(i==storeId){
                textStoreName.setText(StoreList.storeList.get(i).title);
                textStoreAdd.setText(StoreList.storeList.get(i).address);
            }
        }
    }
}