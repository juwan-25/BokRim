package com.mirim.bokrim.RecyclerView;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.mirim.bokrim.Datas.Benefit;
import com.mirim.bokrim.R;

import java.util.ArrayList;

public class RecyclerBenefitAdapter extends RecyclerView.Adapter<RecyclerBenefitAdapter.MyViewHolder>{
    Benefit benefit;
    Context context;
    ArrayList<Benefit> list;
    int[] eventImg = new int[]{R.drawable.event1};
    int[] placeImg = new int[]{R.drawable.place1, R.drawable.place2};
    static boolean isEvent;

    // 리스너 객체 참조를 저장하는 변수
    private static RecyclerHistoryAdapter.OnItemClickListener mListener = null;

    // OnItemClickListener 객체 참조를 어댑터에 전달하는 메서드
    public static void setOnItemClickListener(RecyclerHistoryAdapter.OnItemClickListener listener)
    {
        mListener = listener;
    }

    public interface OnItemClickListener
    {
        void onItemClick(View v, int pos);
    }

    public RecyclerBenefitAdapter(Context context, ArrayList<Benefit> list, boolean isEvent) {
        super();
        this.context = context;
        this.list = list;
        this.isEvent = isEvent;
    }

    @Override
    public void onBindViewHolder(RecyclerBenefitAdapter.MyViewHolder holder, int position) {
        if(isEvent)
            holder.img.setImageResource(eventImg[list.get(position).id]);
        else
            holder.img.setImageResource(placeImg[list.get(position).id]);
        holder.title.setText(list.get(position).title);
        holder.cont1.setText(list.get(position).cont1);
        holder.cont2.setText(list.get(position).cont2);
    }

    @Override
    public RecyclerBenefitAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.recycler_benefit_item, parent, false);
        return new RecyclerBenefitAdapter.MyViewHolder(view);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{

        public TextView title;
        public TextView cont1;
        public TextView cont2;
        public ImageView img;

        public MyViewHolder(View itemView) {
            super(itemView);
            img = itemView.findViewById(R.id.benefit_img);
            title = itemView.findViewById(R.id.benefit_title);
            cont1 = itemView.findViewById(R.id.benefit_cont1);
            cont2 = itemView.findViewById(R.id.benefit_cont2);

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
