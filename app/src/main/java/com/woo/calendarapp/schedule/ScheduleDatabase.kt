package com.woo.calendarapp.schedule

import android.content.Context
import androidx.room.*

@Database(entities = [Schedule::class], version = 1)
@TypeConverters(Converter::class)
abstract class ScheduleDatabase : RoomDatabase() {
    abstract fun ScheduleDao() : ScheduleDao

}