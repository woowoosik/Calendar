package com.woo.js.calendar.Fragment;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.TextView;

import com.woo.js.calendar.Adapter.RecyclerViewVerticalAdapter;
import com.woo.js.calendar.DBHelper;
import com.woo.js.calendar.Day;
import com.woo.js.calendar.R;


import java.util.ArrayList;
import java.util.Calendar;

public class DayFragment extends Fragment implements View.OnClickListener {

    String dbName = "Calendar.db";
    int dbVersion = 1;
    private DBHelper helper;
    private SQLiteDatabase db;
    String tableName = "schedule";
    private ImageView day_left, day_right;
    View view;
    ImageView select_day;

    RecyclerViewVerticalAdapter adapter;

    private TextView mTvCalendarTitle;
    private Calendar mThisDayCalendar;
    private ArrayList<Day> select_List;
    private RecyclerView recyclerview_vertical;


    private String year;
    private String month;
    private String date;

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.day_fragment, container, false);
        setHasOptionsMenu(true);

        DBHelper();

        day_left = view.findViewById(R.id.left_day);
        day_right = view.findViewById(R.id.right_day);
        mTvCalendarTitle = view.findViewById(R.id.day_tv_date);
        select_day = view.findViewById(R.id.select_day);

        mThisDayCalendar = Calendar.getInstance();

        day_left.setOnClickListener(this);
        day_right.setOnClickListener(this);
        select_List = new ArrayList<Day>();


        recyclerview_vertical = (RecyclerView) view.findViewById(R.id.recyclerview_vertical);

        recyclerview_vertical.setHasFixedSize(true);
        adapter = new RecyclerViewVerticalAdapter(getActivity(), select_List);
        recyclerview_vertical.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
        recyclerview_vertical.setAdapter(adapter);

        getCalendar(mThisDayCalendar);


        return view;
    }

    private void DBHelper(){
        helper = new DBHelper(
                getContext(),  // 현재 화면의 제어권자
                dbName,  // 데이터베이스 이름
                null, // 커서팩토리 - null 이면 표준 커서가 사용됨
                dbVersion);  // 데이터베이스 버전
        try {
            db = helper.getWritableDatabase();
        } catch (SQLiteException e) {
            e.printStackTrace();
            Log.e("@","데이터 베이스를 열수 없음");

        }

    }


    private void getCalendar(Calendar calendar)
    {
        mTvCalendarTitle.setText(mThisDayCalendar.get(Calendar.YEAR) + "년 "
                + (mThisDayCalendar.get(Calendar.MONTH) + 1) + "월" + (mThisDayCalendar.get(Calendar.DAY_OF_MONTH)+ "일"));

        select_day.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog dialog = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int picker_year, int picker_month, int picker_date) {
                        mThisDayCalendar.set(picker_year, picker_month, picker_date);

                        mTvCalendarTitle.setText(picker_year + "년 "
                                +  picker_month + "월" + picker_date + "일");
                        getCalendar(mThisDayCalendar);
                        System.out.println("@픽커"+ year+"@나오기"+ month+"@전"+ date);


                    }
                }, mThisDayCalendar.get(Calendar.YEAR), mThisDayCalendar.get(Calendar.MONTH), mThisDayCalendar.get(Calendar.DATE));
                //dialog.getDatePicker().setMaxDate(new Date().getTime());    //입력한 날짜 이후로 클릭 안되게 옵션
                dialog.show();
            }
        });

        year = String.valueOf((mThisDayCalendar.get(Calendar.YEAR)));
        month = String.valueOf((mThisDayCalendar.get(Calendar.MONTH) + 1));
        date = String.valueOf((mThisDayCalendar.get(Calendar.DAY_OF_MONTH)));

        select(year,month,date);
        recyclerview_vertical.setAdapter(adapter);
    }

    private Calendar getLastDay(Calendar calendar)
    {
        calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
        calendar.add(Calendar.DAY_OF_MONTH, -1);

        return calendar;
    }

    private Calendar getNextDay(Calendar calendar)
    {
        calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
        calendar.add(Calendar.DAY_OF_MONTH, +1);

        return calendar;
    }


    @Override
    public void onClick(View v)
    {
        switch(v.getId())
        {
            case R.id.left_day:
                mThisDayCalendar = getLastDay(mThisDayCalendar);
                getCalendar(mThisDayCalendar);

                break;
            case R.id.right_day:
                mThisDayCalendar = getNextDay(mThisDayCalendar);
                getCalendar(mThisDayCalendar);
                break;

        }
    }

    private void select (String select_year, String select_month, String select_day) {
        Day day;

        String sql = "select * from " + tableName + " where " + "year='" + select_year + "' AND month='" + select_month + "' AND day='" + select_day + "';";
        Cursor cursor = db.rawQuery(sql, null);

        select_List.clear();
        while (cursor.moveToNext()) {
            if (cursor != null) {

                day = new Day();
                int select_result_year = cursor.getInt(1);
                int select_result_month = cursor.getInt(2);
                String select_result_day = cursor.getString(3);
                String select_result_title = cursor.getString(4);
                String select_result_content = cursor.getString(5);

                day.setYear(select_result_year);
                day.setMonth(select_result_month);
                day.setDay(select_result_day);
                day.setTitle(select_result_title);
                day.setContent(select_result_content);


                select_List.add(day);
            }
        }
    }


    @Override
    public void onResume() {
        super.onResume();
        Log.e("@Day_onResume","onResume");
        getCalendar(mThisDayCalendar);
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.e("@day_onPause","onPause");
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        Log.e("@day_onDestoryView","onDestoryView");

    }

    @Override
    public void onStop() {
        super.onStop();
        Log.e("@day_onStop","onStop");


        SharedPreferences prefs = this.getActivity().getSharedPreferences("pref_tab", Context.MODE_PRIVATE);
        SharedPreferences.Editor edit = prefs.edit();
        String num="2";
        edit.putString("pref_tab", num);
        System.out.println(" day_tab :"+ num);
        edit.apply();
    }

    @Override
    public void onStart() {
        super.onStart();
        Log.e("@Day_onStart","onStart");
    }
}
