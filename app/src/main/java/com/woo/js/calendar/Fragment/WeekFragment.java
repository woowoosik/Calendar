package com.woo.js.calendar.Fragment;


import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.graphics.Color;
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
import android.widget.TextView;

import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.CalendarMode;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.prolificinteractive.materialcalendarview.OnDateSelectedListener;
import com.prolificinteractive.materialcalendarview.OnMonthChangedListener;
import com.woo.js.calendar.Adapter.RecyclerViewAdapter;
import com.woo.js.calendar.DBHelper;
import com.woo.js.calendar.Day;
import com.woo.js.calendar.R;
import com.woo.js.calendar.decorators.OneDayDecorator;
import com.woo.js.calendar.decorators.SaturdayDecorator;
import com.woo.js.calendar.decorators.ScheduleDecorator;
import com.woo.js.calendar.decorators.SundayDecorator;


import org.threeten.bp.DayOfWeek;
import org.threeten.bp.LocalDate;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.Locale;


public class WeekFragment  extends Fragment implements  OnMonthChangedListener, OnDateSelectedListener {

    View view;
    TextView textView;
    TextView tv_date;
    MaterialCalendarView materialCalendarView;

    Day day ;
    private ArrayList<String> result = new ArrayList<>();
    private ArrayList<Day> select_result = new ArrayList<>();

    String dbName = "Calendar.db";
    int dbVersion = 1;
    private DBHelper helper;
    private SQLiteDatabase db;
    String tableName = "schedule";


    RecyclerView recyclerView;
    private RecyclerViewAdapter adapter;
    ArrayList<Day> weekList;


    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.week_fragment, container, false);
        setHasOptionsMenu(true);

        DBHelper();

        tv_date = view.findViewById(R.id.tv_date);
        CalendarDay date = CalendarDay.from(LocalDate.now());
        tv_date.setText(date.getYear() + "년 " + date.getMonth() + "월" );

        select(String.valueOf(date.getYear()), String.valueOf(date.getMonth()),String.valueOf(date.getDay()));

        materialCalendarView = (MaterialCalendarView) view.findViewById(R.id.calendarView);
        materialCalendarView.setOnDateChangedListener(this);
        materialCalendarView.setOnMonthChangedListener(this);
        materialCalendarView.setTopbarVisible(false);   // 상단 날짜바 안보이기

        materialCalendarView.setSelectedDate(LocalDate.now());


        textView = view.findViewById(R.id.text);


        materialCalendarView.state().edit()
                .isCacheCalendarPositionEnabled(false)
                .setFirstDayOfWeek(DayOfWeek.SUNDAY)
                .setMinimumDate(CalendarDay.from(2000, 1, 1))
                .setMaximumDate(CalendarDay.from(2030, 12, 31))
                .setCalendarDisplayMode(CalendarMode.WEEKS)
                .commit();

        materialCalendarView.addDecorators(new SaturdayDecorator(), new SundayDecorator(), new OneDayDecorator());

        materialCalendarView.setDynamicHeightEnabled(false);
        materialCalendarView.setPadding(0, -20, 0, 30);


        select(String.valueOf(date.getYear()), String.valueOf(date.getMonth()),String.valueOf(date.getDay()));


        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);

        recyclerView.setHasFixedSize(true);
        adapter = new RecyclerViewAdapter(getActivity(), result);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(adapter);


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

    @Override
    public void onMonthChanged(MaterialCalendarView widget, CalendarDay date) {
        tv_date.setText(date.getYear() + "년 " + date.getMonth() + "월");

        Calendar cal = Calendar.getInstance(Locale.KOREA);
        cal.set(date.getYear(), date.getMonth()-1, date.getDay());

        String start_year = String.valueOf(cal.get(Calendar.YEAR));

        cal.set(Calendar.DAY_OF_WEEK, Calendar.SATURDAY);
        String end_year = String.valueOf(cal.get(Calendar.YEAR));

        weekList = new ArrayList<Day>();
        Date week_date = null;
        HashSet<CalendarDay> days = new HashSet<CalendarDay>();

            weekSelect(start_year, end_year);
            if (weekList != null) {
                for (int i=0; i<weekList.size(); i++) {
                    Calendar calendar = Calendar.getInstance();
                    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

                    String dates = weekList.get(i).getYear()+"-"+weekList.get(i).getMonth()+"-"+weekList.get(i).getDay();

                    try {
                        week_date =  dateFormat.parse(dates);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    calendar.setTime(week_date);
                    LocalDate temp = LocalDate.of(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH) + 1, calendar.get(Calendar.DAY_OF_MONTH));

                    days.add(CalendarDay.from(temp));

                }

                materialCalendarView.addDecorator(new ScheduleDecorator(Color.RED, days));

            }

    }

    @Override
    public void onDateSelected(MaterialCalendarView widget, CalendarDay date, boolean selected) {

       select(String.valueOf(date.getYear()), String.valueOf(date.getMonth()),String.valueOf(date.getDay()));

        recyclerView.setAdapter(adapter);

    }



    private void select (String select_year, String select_month, String select_day) {

        String sql = "select * from " + tableName + " where " + "year='" + select_year + "' AND month='" + select_month + "' AND day='" + select_day + "';";

        Cursor cursor = db.rawQuery(sql, null);

         result.clear();
        while (cursor.moveToNext()) {
            if (cursor != null) {
                String select_result_title = cursor.getString(4);
               result.add(select_result_title);
            }
        }


    }
    public void weekSelect(String start_year, String end_year) {


        String sql = "SELECT * FROM "+ tableName+ " WHERE year BETWEEN '"+ start_year + "' AND '"+ end_year + "';";

        Cursor cursor = db.rawQuery(sql, null);
        while (cursor.moveToNext()) {
            Day day = new Day();

            day.setYear( cursor.getInt(1));
            day.setMonth(cursor.getInt(2));
            day.setDay(cursor.getString(3));

            weekList.add(day);

        }
    }
    @Override
    public void onResume() {
        super.onResume();
        Log.e("@week_onResume","onResume");
        materialCalendarView.state().edit()
                .isCacheCalendarPositionEnabled(false)
                .setFirstDayOfWeek(DayOfWeek.SUNDAY)
                .setMinimumDate(CalendarDay.from(2000, 1, 1))
                .setMaximumDate(CalendarDay.from(2030, 12, 31))
                .setCalendarDisplayMode(CalendarMode.WEEKS)
                .commit();
    }

    @Override
    public void onStart() {
        super.onStart();
        Log.e("@week_onStart","onStart");

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();

    }

    @Override
    public void onPause() {
        super.onPause();

        Log.e("@week_onPause","onPause");
    }

    @Override
    public void onStop() {
        super.onStop();
        Log.e("@week_onStop","__onStop");



    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.e("@week_onDestory","__onDestory");

    }

}
