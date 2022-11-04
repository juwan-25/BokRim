package com.mirim.bokrim;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ListView;

import com.mirim.ListView.ListViewMapAdapter;

import net.daum.mf.map.api.MapView;

public class MapFragment extends Fragment {
    public MapFragment() {}

    ListView listData; // 가게 정보들 보이는 리스트뷰

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

        // TODO: 리스트뷰 아이템 온클릭
        //리스트뷰 연결
        ListViewMapAdapter adapter = new ListViewMapAdapter();
        listData = v.findViewById(R.id.list_map);
        listData.setAdapter(adapter);
        // TODO: 실제 가게 정보 입력
        adapter.addItem("첫번째 가게", "첫번째 주소", 0);
        adapter.addItem("두번째 가게", "두번째 주소", 1);

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
}