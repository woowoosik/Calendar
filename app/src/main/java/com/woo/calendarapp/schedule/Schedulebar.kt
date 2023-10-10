package com.woo.calendarapp.schedule

import org.joda.time.DateTime

data class Schedulebar (
    var date : Int,
    var startDate : DateTime,
    var endDate : DateTime,
    var title : String,
    var content : String,
    var scheduleBarColor : Int,
    var scheduleTextColor : Int
)