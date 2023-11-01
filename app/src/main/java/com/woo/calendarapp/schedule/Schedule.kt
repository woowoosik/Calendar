package com.woo.calendarapp.schedule

import androidx.room.Entity
import androidx.room.PrimaryKey
import org.joda.time.DateTime

@Entity
data class Schedule(
    var startDate : DateTime,
    var endDate : DateTime,
    var title : String,
    var content : String,
    var scheduleBarColor : Int,
    var scheduleTextColor : Int,
    var x : Double,
    var y : Double,
    var isChecked : Boolean
){
    @PrimaryKey(autoGenerate = true) var id: Int = 0
}
