package com.woo.calendarapp.repository

import androidx.lifecycle.LiveData
import com.woo.calendarapp.schedule.Schedule
import org.joda.time.DateTime

interface Repository {

    suspend fun setRoomData(schedule: Schedule)

    suspend fun getRoomAllData() : List<Schedule>

    suspend fun getRoomMonthDate(st: DateTime, en: DateTime) : List<Schedule>

    suspend fun getRoomDayDate(date:DateTime):List<Schedule>

    suspend fun setDeleteDate(id:Int)

    suspend fun setUpdateData(id: Int, schedule: Schedule)

/*

    suspend fun setSchedule(title:String, content:String)

    suspend fun getSchedule(id:Int) : LiveData<Schedule>

    suspend fun getAllData() : List<Schedule>

    fun getTestList() : List<Schedule>
*/

}