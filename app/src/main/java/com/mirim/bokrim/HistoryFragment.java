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

import com.mirim.bokrim.Datas.History;
import com.mirim.bokrim.Datas.HistoryList;
import com.mirim.bokrim.RecyclerView.RecyclerHistoryAdapter;

import java.util.ArrayList;

public class HistoryFragment extends Fragment {
    MainActivity activity;
    History history;
    ArrayList<History> list;
    RecyclerView recyclerView;
    RecyclerHistoryAdapter adapter;
    FragmentListener fragmentListener;

    public static HistoryFragment newInstance(String param1, String param2) {
        return new HistoryFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_history, container, false);

        list = new ArrayList<History>() {{
            for(int i=0; i<HistoryList.historyList.size(); i++){
                history = (History) (HistoryList.historyList.get(i));
                add(new History(history.id, history.title, history.subtit, history.cont));
            }
        }};

        //리사이클러 아이템 클릭 리스너
        RecyclerHistoryAdapter.setOnItemClickListener(new RecyclerHistoryAdapter.OnItemClickListener()
        {
            @Override
            public void onItemClick(View v, int pos)
            {
                String id = String.valueOf(pos);
                fragmentListener.onCommand(3, id);
                ((MainActivity)getActivity()).replaceFragment(HistoryDetailFragment.newInstance());
            }
        });

        recyclerView = (RecyclerView)v.findViewById(R.id.history_recycler);
        adapter = new RecyclerHistoryAdapter(getActivity().getApplicationContext(), list);

        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(adapter);

        return v;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        activity = (MainActivity) getActivity();
        if(context instanceof FragmentListener) fragmentListener = (FragmentListener) context;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        if(fragmentListener != null) fragmentListener = null;
        activity = null;
    }

}
