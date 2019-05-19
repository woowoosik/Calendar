package com.woo.js.calendar;

import android.app.DatePickerDialog;
import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;

public class ScheduleActivity extends AppCompatActivity {
    String dbName = "Calendar.db";
    int dbVersion = 1;
    private DBHelper helper;
    private SQLiteDatabase db;
    String tableName = "schedule"; // DB의 table 명

    private TextView add_year;
    private TextView add_month;
    private TextView add_day;

    private TextView title;
    private TextView content;

    TextView tv; // 임시 결과
    Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule);

        toolbar = (Toolbar) findViewById(R.id.toolbar); //툴바설정
        setSupportActionBar(toolbar);//액션바와 같게 만들어줌
        getSupportActionBar().setTitle("스케줄 저장하기");

        title = findViewById(R.id.title);
        content = findViewById(R.id.content);

        add_year = findViewById(R.id.add_year);
        add_month = findViewById(R.id.add_month);
        add_day = findViewById(R.id.add_day);


        helper = new DBHelper(
                this,  // 현재 화면의 제어권자
                dbName,  // 데이터베이스 이름
                null, // 커서팩토리 - null 이면 표준 커서가 사용됨
                dbVersion);  // 데이터베이스 버전
        try {
            db = helper.getWritableDatabase();
        } catch (SQLiteException e) {
            e.printStackTrace();
            Log.e("@","데이터 베이스를 열수 없음");
            finish();
        }


        final Calendar cal = Calendar.getInstance();
        ImageView button = findViewById(R.id.add_button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog dialog = new DatePickerDialog(ScheduleActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int month, int date) {
                        add_year.setText(String.valueOf(year));
                        add_month.setText(String.valueOf(month+1));
                        add_day.setText(String.valueOf(date));
                        String msg = String.format("%d 년 %d 월 %d 일", year, month+1, date);
                        Toast.makeText(ScheduleActivity.this, msg, Toast.LENGTH_SHORT).show();
                    }
                }, cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DATE));
                dialog.show();
            }
        });

    }

    void insert (String insert_year, String insert_month, String insert_day, String insert_title, String insert_content) {
        ContentValues values = new ContentValues();

        values.put("year", insert_year);
        values.put("month", insert_month);
        values.put("day", insert_day);
        values.put("title", insert_title);
        values.put("content", insert_content);

        long result = db.insert(tableName, null, values);


    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.toolbar_schedule, menu);

        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.add:
                String insert_year = add_year.getText().toString();
                String insert_month = add_month.getText().toString();
                String insert_day = add_day.getText().toString();
                String insert_title = title.getText().toString();
                String insert_content = content.getText().toString();

                if ("".equals(insert_year)|| "".equals(insert_month)||"".equals(insert_day)){
                    Toast.makeText(ScheduleActivity.this, " 날짜를 입력해주세요.", Toast.LENGTH_SHORT).show();

                }else if ("".equals(insert_title)||"".equals(insert_content)){
                    Toast.makeText(ScheduleActivity.this, " 항목을 전부 입력해주세요.", Toast.LENGTH_SHORT).show();
                }else{
                    insert (insert_year,
                            insert_month,
                            insert_day,
                            insert_title,
                            insert_content);
                    finish();
                }

                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

}
