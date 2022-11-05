package com.mirim.bokrim;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.mirim.ListView.ListViewMapAdapter;

import net.daum.mf.map.api.MapView;

public class MapFragment extends Fragment {
    public MapFragment() {}

    ListView listData; // 가게 정보들 보이는 리스트뷰
    LinearLayout linearResult;
    FrameLayout frameBtnBack;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_map, container, false);

        //카카오맵
        MapView mapView = new MapView(getContext());

        ViewGroup mapViewContainer = (ViewGroup) v.findViewById(R.id.map_view);
        mapViewContainer.addView(mapView);

        //리스트뷰 연결
        ListViewMapAdapter adapter = new ListViewMapAdapter();
        listData = v.findViewById(R.id.list_map);
        listData.setAdapter(adapter);
        // TODO: 실제 가게 정보 입력
        adapter.addItem("첫번째 가게", "첫번째 주소", 0);
        adapter.addItem("두번째 가게", "두번째 주소", 1);

        adapter.addItem("첫번째 가게", "첫번째 주소", 0);
        adapter.addItem("두번째 가게", "두번째 주소", 1);

        adapter.addItem("첫번째 가게", "첫번째 주소", 0);
        adapter.addItem("두번째 가게", "두번째 주소", 1);

        adapter.addItem("첫번째 가게", "첫번째 주소", 0);
        adapter.addItem("두번째 가게", "두번째 주소", 1);

        // TODO: 리스트뷰 아이템 온클릭
        linearResult = v.findViewById(R.id.linear_result);
        listData.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                changeDragView(true);
                frameBtnBack.setVisibility(View.VISIBLE);
            }
        });

        // 되돌아가기 버튼
        frameBtnBack = v.findViewById(R.id.frame_btn_back);
        frameBtnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeDragView(false);
                frameBtnBack.setVisibility(View.INVISIBLE);
            }
        });

        //검색어 입력하기 전 자식 Fragment 이동
        EditText editSearch = v.findViewById(R.id.editTextFilter);
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

    public void changeDragView(boolean b){
        if(b){ //가게 상세 정보: true
            linearResult.setVisibility(View.VISIBLE);
            listData.setVisibility(View.INVISIBLE);
        }else{ //리스트뷰: false
            linearResult.setVisibility(View.INVISIBLE);
            listData.setVisibility(View.VISIBLE);
        }
    }
}