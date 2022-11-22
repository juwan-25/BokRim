package com.mirim.bokrim;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.mirim.bokrim.Datas.Benefit;
import com.mirim.bokrim.Datas.BenefitDetail;
import com.mirim.bokrim.Datas.EventDetailList;
import com.mirim.bokrim.Datas.EventList;
import com.mirim.bokrim.Datas.PlaceDetailList;
import com.mirim.bokrim.Datas.PlaceList;

import java.util.ArrayList;

public class PlaceFragment extends Fragment {

    ImageView img1, img2, img3;
    ArrayList<BenefitDetail> list;
    BenefitDetail benefitDetail;
    TextView title;
    static int num;
    FragmentListener fragmentListener;

    public PlaceFragment() {}

    public static PlaceFragment newInstance() {

        return new PlaceFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_place, container, false);

        img1 = v.findViewById(R.id.benefit_detail_img1);
        img2 = v.findViewById(R.id.benefit_detail_img2);
        img3 = v.findViewById(R.id.benefit_detail_img3);
        title = v.findViewById(R.id.place_title);
        list = new ArrayList<BenefitDetail>() {{
            for(int i = 0; i< PlaceDetailList.placedetailList.size(); i++){
                benefitDetail = (BenefitDetail) (PlaceDetailList.placedetailList.get(i));
                add(new BenefitDetail(benefitDetail.id, benefitDetail.title, benefitDetail.img1, benefitDetail.img2, benefitDetail.img3));
            }
        }};

        title.setText(list.get(num).title);
        img1.setImageResource(list.get(num).img1);
        img2.setImageResource(list.get(num).img2);
        img3.setImageResource(list.get(num).img3);


        return v;
    }


    public void displayMessage(String message){
        num = Integer.parseInt(message);
        Log.d("값", message);
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