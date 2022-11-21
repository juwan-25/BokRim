package com.mirim.bokrim;

//Fragment간 데이터 송수신을 위한 인터페이스
//MainActivity에서 구현
public interface FragmentListener {
    public void onCommand(int index, String data);

    //0: 가게 아이디
    //1: 혜택 - 행사 아이디
    //2: 혜택 - 장소 아이디
    //3: 역사 아이디
}
