package com.mirim.bokrim;

//Fragment간 데이터 송수신을 위한 인터페이스
//MainActivity에서 구현
public interface FragmentListener {
    public void onCommand(int index, String data);
}
