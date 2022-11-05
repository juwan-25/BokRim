package com.mirim.bokrim;

import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.WindowInsetsCompat;
import androidx.core.view.WindowInsetsControllerCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

public class MapParentFragment extends Fragment implements View.OnClickListener {
    // 검색창 + 자식 프래그먼트 띄우는 창
    // 으로 구성되어있음

    FragmentListener fragmentListener;

    public static EditText editSearch; //검색어
    Toolbar toolSearch; //검색창
    ImageButton imgSearch; //검색창 아이콘
    public static Button btnListCheck, btnFavoriteCheck; //다른 자식 프래그먼트로 이동. 자식 프래그먼트에서 호출하는 용도
    public static int storeId; //가게 아이디

    public static MapParentFragment newInstance() {
        return new MapParentFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_map_parent, container, false);

        //ListView 화면으로 이동
        Fragment fg = MapSearchFragment.newInstance();
        setChildFragment(fg);

        editSearch = v.findViewById(R.id.editTextFilter);
        toolSearch = v.findViewById(R.id.toolbar_search);
        imgSearch = v.findViewById(R.id.img_search_icon);
        btnListCheck = v.findViewById(R.id.btn_listview_check);

        //리스트뷰(자식 프래그먼트)에서 아이템 클릭
        btnListCheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                keyBordHide();

                // TODO : Listview 아이템 클릭시 화면 이동
                //지도 검색 결과 프래그먼트로 이동
                Log.d("리스트뷰", "아이템 클릭");
                ((MainActivity)getActivity()).replaceFragment(MapFragment.newInstance());

//                //검색 아이콘 => X 아이콘
//                imgSearch.setImageResource(R.drawable.x);
//                imgSearch.setTag("x");

                keyBordHide();
            }
        });

        //검색어 클릭
        editSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //즐겨찾기 ListView 화면으로 이동
                Fragment fg = MapSearchFragment.newInstance();
                setChildFragment(fg);
            }
        });

        //검색어 입력시
        editSearch.addTextChangedListener(new TextWatcher() {
            MapSearchFragment mapSearchFragment = new MapSearchFragment();
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                Log.d("텍스트 입력", "언제");
                fragmentListener.onCommand(0, editSearch.getText().toString());
                mapSearchFragment.isSearch(true);
                setChildFragment(fg);
            }
            @Override
            public void afterTextChanged(Editable s) {
                Log.d("텍스트 입력", "실행될래");
                //검색어값 다른 프래그먼트로 이동
                fragmentListener.onCommand(0, editSearch.getText().toString());
                mapSearchFragment.isSearch(true);
                setChildFragment(fg);

            }
        });

        //검색어 입력 후 키보드의 완료 버튼 눌렀을때
        editSearch.setOnEditorActionListener(new TextView.OnEditorActionListener()
        {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event)
            {
                if(actionId == EditorInfo.IME_ACTION_DONE)
                {
//                    imgSearch.setImageResource(R.drawable.x); //검색창 아이콘 이미지 변경
                    keyBordHide(); //키보드 숨기기
                    return true;
                }
                return false;
            }
        });

        return v;
    }

    @Override
    public void onClick(View view) {}

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

    //자식 프래그먼트 이동
    public void setChildFragment(Fragment child) {
        FragmentTransaction childFt = getChildFragmentManager().beginTransaction();

        if (!child.isAdded()) {
            childFt.replace(R.id.child_fragment_container, child);
            childFt.addToBackStack(null);
            childFt.commitAllowingStateLoss();
        }
    }

    //ParentFragment에서 가게 아이디값 받아오기
    public void displayMessage(String message){
        storeId = Integer.parseInt(message);
    }

    //키보드 숨기기
    void keyBordHide() {
        Window window = getActivity().getWindow();
        new WindowInsetsControllerCompat(window, window.getDecorView()).hide(WindowInsetsCompat.Type.ime());
    }

    //키보드 보이게 하기
    void keyBordShow() {
        Window window = getActivity().getWindow();
        new WindowInsetsControllerCompat(window, window.getDecorView()).show(WindowInsetsCompat.Type.ime());
    }

}