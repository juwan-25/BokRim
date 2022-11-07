package com.mirim.bokrim;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.mirim.bokrim.Datas.StoreList;
import com.mirim.bokrim.ListView.ListViewSearchAdapter;
import com.mirim.bokrim.ListView.ListViewSearchItem;

import java.util.Map;

public class MapSearchFragment extends Fragment {
    FragmentListener fragmentListener;
    static String strSearch = "초기값"; //검색어 string 타입
    EditText editSearch; //검색어 editText 타입
    ListView listData; //검색 결과 보이는 리스트뷰
    static Button btnFilterCheck;
    static boolean isSearch = false;
    int storeId;

    public MapSearchFragment() {}

    public static MapSearchFragment newInstance() {
        return new MapSearchFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_map_search, container, false);
        Log.d("텍스트 입력", "SearchFragment 진입");

        //가게 검색 필터링 기능(listview) 구현
        ListViewSearchAdapter adapter;
        adapter = new ListViewSearchAdapter() ;

        listData = (ListView) v.findViewById(R.id.list_search);
        listData.setAdapter(adapter);

        // TODO: 실제 가게 정보 데이터 추가
        //  리스트뷰에 전체 데이터 추가
        for(int i = 0; i< StoreList.storeList.size(); i++)
            adapter.addItem(StoreList.storeList.get(i).title, StoreList.storeList.get(i).address, StoreList.storeList.get(i).id);

        //  ParentFragment에서 검색어값 받아오기
        LayoutInflater layoutInflater = getLayoutInflater();
        View layout = layoutInflater.inflate(R.layout.fragment_map_parent, v.findViewById(R.id.editTextFilter));
        editSearch = layout.findViewById(R.id.editTextFilter);
        editSearch.setText(strSearch); //displayMessage()로 strSearch에 값이 들어있음

        // TODO: 검색어 입력시 필터링 시작
        //  ListView 필터링
        btnFilterCheck = v.findViewById(R.id.btn_filter_check);
        btnFilterCheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isSearch) ((ListViewSearchAdapter) listData.getAdapter()).getFilter().filter(strSearch);
                Log.d("텍스트 입력", "헉 드디어 이게 보인다니 지민아 수고했써"+Boolean.toString(isSearch)+" "+strSearch);

            }
        });

        //  ListView에 item이 없을때 안내 text
        TextView textEmpty = v.findViewById(R.id.text_empty);
        listData.setEmptyView(textEmpty);

        //  ListView item 온클릭
        listData.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ListViewSearchItem obj = (ListViewSearchItem) parent.getAdapter().getItem(position);
                storeId = obj.getId();

                Log.d("슬라이딩", "올라오세유");


                // 검색 결과 프래그먼트로 이동
                MapParentFragment.btnListCheck.performClick();

                new Handler().post(new Runnable() {
                    @Override
                    public void run() {
                        MapFragment.listData.performItemClick(
                                listData.getChildAt(storeId),
                                storeId,
                                listData.getAdapter().getItemId(storeId));
                    }
                });

                // 가게 아이디 다른 프래그먼트로 보내고
                //fragmentListener.onCommand(1, Integer.toString(storeId));

            }
        });

        return v;
    }

    //리스트뷰 필터링을 시작할 것인지의 boolean값 ParentFragment에서 받아오기
    public void isSearch(boolean b){
        isSearch = b;
        btnFilterCheck.performClick();
    }

    //ParentFragment에서 검색어 값 받아오기
    public void displayMessage(String message){
        strSearch = message;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if(context instanceof FragmentListener) fragmentListener = (FragmentListener) context;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        if(fragmentListener != null) fragmentListener = null;
    }
}