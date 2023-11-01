package com.woo.calendarapp.schedule

import androidx.room.*

@Database(entities = [Schedule::class], version = 3)
@TypeConverters(Converter::class)
abstract class ScheduleDatabase : RoomDatabase() {
    abstract fun ScheduleDao() : ScheduleDao


}