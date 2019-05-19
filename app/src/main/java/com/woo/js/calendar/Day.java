package com.woo.js.calendar;

import java.util.Date;

/**
 * 하루의 날짜정보를 저장하는 클래스
 *
 * @author croute
 * @since 2011.03.08
 */
public class Day
{
    private String day;
    private boolean inMonth;
    private int month;
    private int year;
    private  int dayOfMonth;
    private boolean schedule;
    private String title;
    private String content;
    private Date date;

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public boolean isSchedule() {
        return schedule;
    }

    public void setSchedule(boolean schedule) {
        this.schedule = schedule;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getDayOfMonth() {
        return dayOfMonth;
    }

    public void setDayOfMonth(int dayOfMonth) {
        this.dayOfMonth = dayOfMonth;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public String getDay()
    {
        return day;
    }

    public void setDay(String day)
    {
        this.day = day;
    }

    public boolean isInMonth()
    {
        return inMonth;
    }

    public void setInMonth(boolean inMonth)
    {
        this.inMonth = inMonth;
    }

}


