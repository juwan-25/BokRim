package com.mirim.bokrim;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.mirim.bokrim.Datas.Benefit;
import com.mirim.bokrim.Datas.PlaceList;
import com.mirim.bokrim.RecyclerView.RecyclerBenefitAdapter;
import com.mirim.bokrim.RecyclerView.RecyclerHistoryAdapter;

import java.util.ArrayList;

public class Benefit2Fragment extends Fragment {
    Button btnToEvent, btnToPlace;
    MainActivity activity;
    Benefit benefit;
    ArrayList<Benefit> list;
    RecyclerView recyclerView;
    RecyclerBenefitAdapter adapter;
    FragmentListener fragmentListener;

    public static Benefit2Fragment newInstance() {

        return new Benefit2Fragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_benefit2, container, false);

        btnToEvent = v.findViewById(R.id.btn_event);
        btnToEvent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((MainActivity)getActivity()).replaceFragment(BenefitFragment.newInstance());
            }
        });
        btnToPlace = v.findViewById(R.id.btn_place);
        btnToPlace.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((MainActivity)getActivity()).replaceFragment(Benefit2Fragment.newInstance());
            }
        });

        list = new ArrayList<Benefit>() {{
            for(int i = 0; i< PlaceList.placeList.size(); i++){
                benefit = (Benefit) (PlaceList.placeList.get(i));
                add(new Benefit(benefit.id, benefit.title, benefit.cont1, benefit.cont2, benefit.link));
            }
        }};

        //리사이클러 아이템 클릭 리스너
        RecyclerBenefitAdapter.setOnItemClickListener(new RecyclerHistoryAdapter.OnItemClickListener()
        {
            @Override
            public void onItemClick(View v, int pos)
            {
                String id = String.valueOf(pos);
                fragmentListener.onCommand(2, id);
                ((MainActivity)getActivity()).replaceFragment(PlaceFragment.newInstance());
            }
        });

        recyclerView = (RecyclerView)v.findViewById(R.id.place_recycler);
        adapter = new RecyclerBenefitAdapter(getActivity().getApplicationContext(), list, false);

        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(adapter);

        return v;
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