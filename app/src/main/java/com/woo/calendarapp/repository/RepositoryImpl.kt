package com.woo.calendarapp.repository

import com.woo.calendarapp.KakaoRetrofit
import com.woo.calendarapp.schedule.Schedule
import com.woo.calendarapp.schedule.ScheduleDao
import org.joda.time.DateTime
import javax.inject.Inject

class RepositoryImpl @Inject constructor(
    private val dao: ScheduleDao,
    private val kakaoRetrofit: KakaoRetrofit
) : Repository  {

    override suspend fun setRoomData(schedule: Schedule) = dao.setInsertData(
        schedule.startDate,
        schedule.endDate,
        schedule.title,
        schedule.content,
        schedule.scheduleBarColor,
        schedule.scheduleTextColor,
        schedule.x,
        schedule.y,
        schedule.isChecked
    )
    override suspend fun getRoomAllData() : List<Schedule> = dao.getAllDate()

    override suspend fun getRoomMonthDate(st: DateTime, en: DateTime): List<Schedule> = dao.getMonthDate(st, en)

    override suspend fun getRoomDayDate(date: DateTime): List<Schedule> = dao.getDayDate(date)

    override suspend fun setDeleteDate(id: Int) = dao.setDeleteData(id)

    override suspend fun setUpdateData(id: Int, schedule: Schedule) = dao.setUpdateData(
        id,
        schedule.startDate,
        schedule.endDate,
        schedule.title,
        schedule.content,
        schedule.scheduleBarColor,
        schedule.scheduleTextColor,
        schedule.x,
        schedule.y,
        schedule.isChecked
    )

    override suspend fun getSearchKeyword(keyword: String):
            KakaoRetrofit.ResultSearchKeyword = kakaoRetrofit.searchKeyword(keyword)


 /*   override suspend fun getSearchKeyword(keyword: String): KakaoRetrofit.ResultSearchKeyword {
        val kakaoRetrofit = KakaoRetrofit()
        return kakaoRetrofit.searchKeyword(keyword)
    }
*/

}