package com.mirim.bokrim;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.mirim.bokrim.Datas.Benefit;
import com.mirim.bokrim.Datas.EventList;
import com.mirim.bokrim.Datas.History;
import com.mirim.bokrim.Datas.HistoryList;

import java.util.ArrayList;

public class BenefitFragment extends Fragment {
    Button btnToPlace, btnToEvent;
    MainActivity activity;
    Benefit benefit;
    ArrayList<Benefit> list;
    RecyclerView recyclerView;
    RecyclerBenefitAdapter adapter;
    FragmentListener fragmentListener;

    public BenefitFragment() {}

    public static BenefitFragment newInstance() {

        return new BenefitFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_benefit, container, false);
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
            for(int i = 0; i< EventList.eventList.size(); i++){
                benefit = (Benefit) (EventList.eventList.get(i));
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
                fragmentListener.onCommand(1, id);
                ((MainActivity)getActivity()).replaceFragment(EventFragment.newInstance());
            }
        });

        recyclerView = (RecyclerView)v.findViewById(R.id.event_recycler);
        adapter = new RecyclerBenefitAdapter(getActivity().getApplicationContext(), list);

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