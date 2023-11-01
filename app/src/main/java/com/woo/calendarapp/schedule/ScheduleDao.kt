package com.woo.calendarapp.schedule

import androidx.room.*
import org.joda.time.DateTime

@Dao
interface ScheduleDao {

    @Query("SELECT * FROM SCHEDULE ORDER BY startDate ASC, endDate DESC")
    fun getAllDate():List<Schedule>

    @Query("SELECT * FROM SCHEDULE WHERE  " +
            "startDate between :start and :end OR "+
            "endDate between :start and :end OR "+
            "(startDate <= :start AND :end <= endDate) "+
            "ORDER BY startDate ASC, endDate DESC")
    fun getMonthDate(start: DateTime, end:DateTime):List<Schedule>


    @Query("INSERT INTO SCHEDULE(startDate, endDate, title, content, scheduleBarColor ,scheduleTextColor, x, y, isChecked) VALUES(:sd,:ed,:t,:c,:sb,:st, :x, :y, :isChecked)")
    fun setInsertData(sd:DateTime, ed:DateTime, t:String, c:String, sb:Int, st:Int, x:Double, y:Double, isChecked : Boolean)

    @Query("SELECT * FROM SCHEDULE WHERE  " +
            "(startDate <= :date AND :date <= endDate) "+
            "ORDER BY startDate ASC, endDate DESC")
    fun getDayDate(date: DateTime):List<Schedule>

    @Query("Delete From SCHEDULE where id = :id")
    fun setDeleteData(id :Int)

    @Query("UPDATE SCHEDULE SET startDate = :sd, endDate = :ed, title = :t, content = :c, scheduleBarColor = :sb, scheduleTextColor = :st, x= :x, y = :y, isChecked = :isChecked WHERE id=:id")
    fun setUpdateData(id: Int, sd:DateTime, ed:DateTime, t:String, c:String, sb:Int, st:Int, x:Double, y:Double, isChecked:Boolean)





    /*
    @Insert
    fun insert(schedule: Schedule)

     @Update
     fun update(schedule: Schedule)

     @Delete
     fun delete(schedule: Schedule)

     @Query("INSERT INTO SCHEDULE(title, content) VALUES('테스트제목2','테스트내용2')")
     fun setData()

     @Query("INSERT INTO SCHEDULE(title, content) VALUES(:t,:c)")
     fun setInsertData(t:String, c:String)

     @Query("SELECT * FROM SCHEDULE")
     fun getAllDate():List<Schedule>
 */




}