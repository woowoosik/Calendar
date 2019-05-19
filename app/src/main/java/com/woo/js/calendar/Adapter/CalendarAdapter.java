package com.woo.js.calendar.Adapter;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;


import com.woo.js.calendar.Day;
import com.woo.js.calendar.R;

import java.util.ArrayList;
import java.util.Calendar;

public class CalendarAdapter extends BaseAdapter
{
    private ArrayList<Day> mDayList;
    private Context mContext;
    private int mResource;
    private LayoutInflater mLiInflater;
    private int look_month;
    private  int real_month;
    private int real_year;
    private  Day day;

    public CalendarAdapter(Context context, int textResource, ArrayList<Day> dayList)
    {
        this.mContext = context;
        this.mDayList = dayList;
        this.mResource = textResource;
        this.mLiInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount()
    {
        // TODO Auto-generated method stub
        return mDayList.size();
    }

    @Override
    public Object getItem(int position)
    {
        // TODO Auto-generated method stub
        return mDayList.get(position);
    }

    @Override
    public long getItemId(int position)
    {
        return 0;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {

        day = mDayList.get(position);

        DayViewHolde dayViewHolder;


        int today = Calendar.getInstance().get(Calendar.DAY_OF_MONTH);
        real_month = Calendar.getInstance().get(Calendar.MONTH)+1;
        real_year = Calendar.getInstance().get(Calendar.YEAR);
        look_month = day.getDayOfMonth()-1;

        Log.e("month", String.valueOf(real_month));
        Log.e("dayOfMonth", String.valueOf(look_month));
        int last_days = position - look_month -6;

        if(convertView == null)
        {
            convertView = mLiInflater.inflate(mResource, null);

            dayViewHolder = new DayViewHolde();


            dayViewHolder.tvDay = (TextView) convertView.findViewById(R.id.tv_item_gridview);
            dayViewHolder.schedule_image = convertView.findViewById(R.id.schedule_image);

            convertView.setTag(dayViewHolder);
        }
        else
        {
            dayViewHolder = (DayViewHolde) convertView.getTag();
        }

        if(day != null)
        {
            dayViewHolder.tvDay.setText(day.getDay());

            if(day.isInMonth())
            {

                if(day.isSchedule()){
                    dayViewHolder.schedule_image.setVisibility(View.VISIBLE);
                }
                if(today == last_days && real_month == day.getMonth()  && real_year == day.getYear())
                {
                    dayViewHolder.tvDay.setTextColor(Color.GREEN);
                }
                else if(position % 7 == 0)
                {
                    dayViewHolder.tvDay.setTextColor(Color.RED);
                }
                else if(position % 7 == 6)
                {
                    dayViewHolder.tvDay.setTextColor(Color.BLUE);
                }
                else
                {
                    dayViewHolder.tvDay.setTextColor(Color.BLACK);
                }

                dayViewHolder.tvDay.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                    }
                });

            }
            else
            {
                dayViewHolder.tvDay.setTextColor(Color.GRAY);
            }

        }

        return convertView;
    }

    public class DayViewHolde
    {
        public LinearLayout llBackground;
        public TextView tvDay;
        public ImageView schedule_image;


    }

}


