package com.woo.calendarapp.schedule

import androidx.room.Entity
import androidx.room.PrimaryKey
import org.joda.time.DateTime
import java.sql.Date

@Entity
data class Schedule(
    var startDate : DateTime,
    var endDate : DateTime,
    var title : String,
    var content : String,
    var scheduleBarColor : Int,
    var scheduleTextColor : Int
){
    @PrimaryKey(autoGenerate = true) var id: Int = 0
}
