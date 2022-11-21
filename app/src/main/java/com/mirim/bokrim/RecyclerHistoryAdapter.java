package com.mirim.bokrim;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.mirim.bokrim.Datas.History;
import com.mirim.bokrim.Datas.HistoryList;

import java.util.ArrayList;

public class RecyclerHistoryAdapter extends RecyclerView.Adapter<RecyclerHistoryAdapter.MyViewHolder> {

    History history;
    Context context;
    ArrayList<History> list;

    // 리스너 객체 참조를 저장하는 변수
    private static OnItemClickListener mListener = null;

    // OnItemClickListener 객체 참조를 어댑터에 전달하는 메서드
    public static void setOnItemClickListener(OnItemClickListener listener)
    {
        mListener = listener;
    }

    public interface OnItemClickListener
    {
        void onItemClick(View v, int pos);
    }

    public RecyclerHistoryAdapter(Context context, ArrayList<History> list) {
        super();
        this.context = context;
        this.list = list;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        holder.title.setText(list.get(position).title);
        holder.subtit.setText(list.get(position).subtit);

    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.recycler_history_item, parent, false);
        return new MyViewHolder(view);
    }



    @Override
    public int getItemCount() {
        return list.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{

        public TextView title;
        public TextView subtit;


        public MyViewHolder(View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.history_title);
            subtit = itemView.findViewById(R.id.history_subtit);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int pos = getAdapterPosition();
                    // 리스너 객체의 메서드 호출
                    if (pos != RecyclerView.NO_POSITION)
                    {
                        mListener.onItemClick(view, pos);
                    }
                }
            });
        }
    }
}
