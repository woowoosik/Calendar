package com.woo.js.calendar.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.woo.js.calendar.Day;
import com.woo.js.calendar.R;


import java.util.ArrayList;

public class RecyclerViewVerticalAdapter extends RecyclerView.Adapter<RecyclerViewVerticalAdapter.ItemRowHolder> {

    private ArrayList<Day> dataList;
    private Context mContext;

    public RecyclerViewVerticalAdapter(Context context, ArrayList<Day> dataList) {
        this.dataList = dataList;
        this.mContext = context;
    }

    @Override
    public ItemRowHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_calendar_recyclerview_vertical, null);
        ItemRowHolder holder = new ItemRowHolder(v);
        return holder;
    }

    @Override
    public void onBindViewHolder(ItemRowHolder holder, int i) {

        for(int h=0; h<dataList.size(); h++){
            holder.title_vertical.setText(dataList.get(i).getTitle());
            Log.e("adapter_title",dataList.get(i).getTitle());
            Log.e("adapter_content",dataList.get(i).getContent());
            holder.content_vertical.setText(dataList.get(i).getContent());

        }
    }

    @Override
    public int getItemCount() {
        return (null != dataList ? dataList.size() : 0);
    }

    public class ItemRowHolder extends RecyclerView.ViewHolder {

        private TextView title_vertical, content_vertical;
        private ImageView Image_vertical;
        private RecyclerView recyclerview_vertical;

        public ItemRowHolder(View view) {
            super(view);
            recyclerview_vertical =  (RecyclerView) view.findViewById(R.id.recyclerview_vertical );
            content_vertical = (TextView) view.findViewById(R.id.content_vertical);
            title_vertical = (TextView) view.findViewById(R.id.title_vertical);
            Image_vertical = view.findViewById(R.id.Image_vertical);



        }

    }

}