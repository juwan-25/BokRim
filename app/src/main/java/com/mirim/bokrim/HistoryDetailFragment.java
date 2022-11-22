package com.mirim.bokrim;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.mirim.bokrim.Datas.History;
import com.mirim.bokrim.Datas.HistoryList;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class HistoryDetailFragment extends Fragment {
    MainActivity activity;
    FragmentListener fragmentListener;

    FirebaseStorage storage = FirebaseStorage.getInstance("gs://bokrim-202c6.appspot.com/");
    StorageReference storageRef = storage.getReference();

    History history;
    ArrayList<History> list;
    ImageView backBtn, img;
    TextView title, cont;
    static int id;

    public static HistoryDetailFragment newInstance() {

        return new HistoryDetailFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_history_detail, container, false);
        backBtn = v.findViewById(R.id.history_backbtn);
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getFragmentManager().popBackStack();
            }
        });

        title = v.findViewById(R.id.history_detail_title);
        cont = v.findViewById(R.id.history_detail_cont);
        img = v.findViewById(R.id.history_detail_img);

        list = new ArrayList<History>() {{
            for(int i=0; i<HistoryList.historyList.size(); i++){
                history = (History) (HistoryList.historyList.get(i));
                add(new History(history.id, history.title, history.subtit, history.cont));
            }
        }};

        title.setText(list.get(id).title);
        cont.setText(list.get(id).cont);
        int file = id+1;
        storageRef.child("History/id"+file+".png").getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                //이미지 로드 성공시
                Glide.with(getActivity())
                        .load(uri)
                        .into(img);

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                //이미지 로드 실패시
                Toast.makeText(getActivity(), "실패", Toast.LENGTH_SHORT).show();
            }
        });
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

    //ParentFragment에서 검색어 값 받아오기
    public void displayMessage(String message){
        id = Integer.parseInt(message);
    }

}
