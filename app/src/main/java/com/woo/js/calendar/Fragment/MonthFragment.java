package com.woo.js.calendar.Fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;


import com.woo.js.calendar.Adapter.CalendarAdapter;
import com.woo.js.calendar.DBHelper;
import com.woo.js.calendar.Day;
import com.woo.js.calendar.R;

import java.util.ArrayList;
import java.util.Calendar;

public class MonthFragment  extends Fragment implements AdapterView.OnItemClickListener, View.OnClickListener{

    View view;

    public static int SUNDAY        = 1;
    public static int MONDAY        = 2;
    public static int TUESDAY       = 3;
    public static int WEDNSESDAY    = 4;
    public static int THURSDAY      = 5;
    public static int FRIDAY        = 6;
    public static int SATURDAY      = 7;
    private String[] days={"일","월","화","수","목","금","토"};
    int move_month;
    int move_year;
    private boolean result;

    private TextView mTvCalendarTitle;
    private GridView mGvCalendar;
    private ImageView left, right;

    private ArrayList<Day> mDayList;
    private CalendarAdapter mCalendarAdapter;

    Calendar mLastMonthCalendar;
    Calendar mThisMonthCalendar;
    Calendar mNextMonthCalendar;


    String dbName = "Calendar.db";
    int dbVersion = 1;
    private DBHelper helper;
    private SQLiteDatabase db;
    String tableName = "schedule";


    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.month_fragment, container, false);
        setHasOptionsMenu(true);


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



        left = view.findViewById(R.id.left_month);
        right = view.findViewById(R.id.right_month);

        mTvCalendarTitle = (TextView)view.findViewById(R.id.tv_date);
        mGvCalendar = (GridView)view.findViewById(R.id.gridview);


        left.setOnClickListener(this);
        right.setOnClickListener(this);
        mGvCalendar.setOnItemClickListener(this);

        mDayList = new ArrayList<Day>();

        mThisMonthCalendar = Calendar.getInstance();
        mThisMonthCalendar.set(Calendar.DAY_OF_MONTH, 1);
        getCalendar(mThisMonthCalendar);



        return view;
    }

    private void getCalendar(Calendar calendar)
    {
        int lastMonthStartDay;
        int dayOfMonth;
        int thisMonthLastDay;

        mDayList.clear();


        // 이번달 시작일의 요일을 구한다. 시작일이 일요일인 경우 인덱스를 1(일요일)에서 8(다음주 일요일)로 바꾼다.)
        dayOfMonth = calendar.get(Calendar.DAY_OF_WEEK);

        thisMonthLastDay = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);

        calendar.add(Calendar.MONTH, -1);
        Log.e("지난달 마지막일", calendar.get(Calendar.DAY_OF_MONTH)+"");

        // 지난달의 마지막 일자를 구한다.
        lastMonthStartDay = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);

        calendar.add(Calendar.MONTH, 1);
        Log.e("이번달 시작일", calendar.get(Calendar.DAY_OF_MONTH)+"");

        if(dayOfMonth == SUNDAY)
        {
            dayOfMonth += 0;
        }

        lastMonthStartDay -= (dayOfMonth-1)-1;

        // 캘린더 타이틀(년월 표시)을 세팅한다.
        mTvCalendarTitle.setText(mThisMonthCalendar.get(Calendar.YEAR) + "년 "
                + (mThisMonthCalendar.get(Calendar.MONTH) + 1) + "월");
        move_month = mThisMonthCalendar.get(Calendar.MONTH) + 1;
        move_year = mThisMonthCalendar.get(Calendar.YEAR) ;

        Day day;

        for(int i=0; i<days.length; i++){
            day = new Day();
            day.setDay(days[i]);
            day.setInMonth(false);

            mDayList.add(day);
        }

       for(int i=0; i<dayOfMonth-1; i++)
        {
            int date = lastMonthStartDay+i;
            day = new Day();
            day.setDay(Integer.toString(date));
            day.setInMonth(false);

            mDayList.add(day);
        }

        for(int i=1; i <= thisMonthLastDay; i++)
        {
            day = new Day();
            day.setDay(Integer.toString(i));
            day.setInMonth(true);
            day.setMonth(move_month);
            day.setYear(move_year);

            result = select(String.valueOf(move_year),String.valueOf(move_month),String.valueOf(i));

            day.setSchedule(result);
            day.setDayOfMonth(dayOfMonth);

            mDayList.add(day);

        }


        for(int i=1; i<42-(thisMonthLastDay+dayOfMonth-1)+1; i++)
        {
            day = new Day();
            day.setDay(Integer.toString(i));
            day.setInMonth(false);
            mDayList.add(day);
        }

        initCalendarAdapter();
    }

    // 네비게이션 뷰 ( 하단 탭 )
    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_month:
                    return true;
                case R.id.navigation_week:
                    return true;
                case R.id.navigation_day:
                    return true;
            }
            return false;
        }

    };

    private Calendar getLastMonth(Calendar calendar)
    {
        calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), 1);
        calendar.add(Calendar.MONTH, -1);
        mTvCalendarTitle.setText(mThisMonthCalendar.get(Calendar.YEAR) + "년 "
                + (mThisMonthCalendar.get(Calendar.MONTH) + 1) + "월");

        return calendar;
    }

    private Calendar getNextMonth(Calendar calendar)
    {
        calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), 1);
        calendar.add(Calendar.MONTH, +1);
        mTvCalendarTitle.setText(mThisMonthCalendar.get(Calendar.YEAR) + "년 "
                + (mThisMonthCalendar.get(Calendar.MONTH) + 1) + "월");

        return calendar;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View v, int position, long arg3)
    {

    }

    @Override
    public void onClick(View v)
    {
        switch(v.getId())
        {
            case R.id.left_month:
                mThisMonthCalendar = getLastMonth(mThisMonthCalendar);
                getCalendar(mThisMonthCalendar);
                break;
            case R.id.right_month:
                mThisMonthCalendar = getNextMonth(mThisMonthCalendar);
                getCalendar(mThisMonthCalendar);
                break;
        }
    }

    private void initCalendarAdapter()
    {
        mCalendarAdapter = new CalendarAdapter(getContext(), R.layout.item_calendar_gridview, mDayList);
        mGvCalendar.setAdapter(mCalendarAdapter);

    }

    boolean select (String select_year, String select_month, String select_day) {
        boolean select_result = false;
        String sql = "select * from " + tableName + " where " + "year='" + select_year + "' AND month='" + select_month + "' AND day='" + select_day + "';";

        Cursor cursor = db.rawQuery(sql, null);

        while (cursor.moveToNext()) {
            if (cursor != null) {
                select_result = true;
            }
        }

        return select_result;
    }

    @Override
    public void onStart() {
        super.onStart();
        Log.e("@month_onStart","onStart");

    }
    @Override
    public void onResume() {
        super.onResume();
        Log.e("@month_onResume","onResume");
        getCalendar(mThisMonthCalendar);
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        Log.e("@month_onDestoryView","onDestoryView");

    }

    @Override
    public void onPause() {
        super.onPause();

        Log.e("@month_onPause","onPause");
    }

    @Override
    public void onStop() {
        super.onStop();
        Log.e("@month_onStop","__onStop");


        SharedPreferences prefs = this.getActivity().getSharedPreferences("pref_tab", Context.MODE_PRIVATE);
        SharedPreferences.Editor edit = prefs.edit();
        String num="0";
        edit.putString("pref_tab", num);
        System.out.println(" month_tab :"+ num);
        edit.apply();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.e("@month_onDestory","__onDestory");

    }

    @Override
    public void onDetach() {
        super.onDetach();
        Log.e("@month_onDestach","onDestach");
    }

}
