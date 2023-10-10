package com.woo.calendarapp.schedule

import androidx.room.TypeConverter
import org.joda.time.DateTime

class Converter {
    @TypeConverter
    fun dateToString(date: DateTime):String{
        return date.toString()
    }

    @TypeConverter
    fun stringToDate(date:String):DateTime{
        return DateTime(date)
    }
}