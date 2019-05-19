package com.woo.js.calendar.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


import com.woo.js.calendar.R;

import java.util.ArrayList;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.Holder> {

    private Context context;
    private ArrayList<String> list = new ArrayList<>();


    public RecyclerViewAdapter(Context context, ArrayList<String> list) {
        this.context = context;
        this.list = list;
    }


    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_calendar_recyclerview, viewGroup, false);
        Holder holder = new Holder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(Holder holder, int position) {
        // 각 위치에 문자열 세팅

        holder.title.setText(list.get(position));


    }


    @Override
    public int getItemCount() {
        return list.size(); // RecyclerView의 size return
    }


    public static class Holder extends RecyclerView.ViewHolder{
        public TextView title;
        public CardView cardView;
        public ImageView imageView;

        public Holder(View view){
            super(view);
            title = (TextView) view.findViewById(R.id.title);
            cardView = view.findViewById(R.id.cardView);
            imageView =  view.findViewById(R.id.cardViewImage);
        }
    }
}


