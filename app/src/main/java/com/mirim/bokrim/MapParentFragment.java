package com.mirim.bokrim;

import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
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

        //즐겨찾기 ListView 화면으로 이동
        Fragment fg = MapSearchFragment.newInstance();
        setChildFragment(fg);


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