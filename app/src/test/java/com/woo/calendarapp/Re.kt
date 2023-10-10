package com.woo.calendarapp

import com.woo.calendarapp.schedule.Schedule
import com.woo.calendarapp.schedule.Schedulebar
import kotlinx.coroutines.runBlocking
import org.joda.time.DateTime
import org.junit.Test

class Re {
    private lateinit var dataList : Array<Array<Array<Schedulebar>>>

    private var monthScheduleList : MutableList<Schedule> = mutableListOf()

    @Test
    fun getSchedulebarData() {
        monthScheduleList.add(
            Schedule(
                DateTime(2023,8,27,0,0),
                DateTime(2023,9,1,0,0),
                "27",
                "contnet2701",
                -14423855,
                -15072252
            )
        )
        monthScheduleList.add(
            Schedule(
                DateTime(2023,8,27,0,0),
                DateTime(2023,9,1,0,0),
                "27",
                "contnet2701",
                -14423855,
                -15072252
            )
        )
        monthScheduleList.add(
            Schedule(
                DateTime(2023,8,27,0,0),
                DateTime(2023,8,31,0,0),
                "27",
                "contnet2731",
                -14423855,
                -15072252
            )
        )
        monthScheduleList.add(
            Schedule(
                DateTime(2023,8,27,0,0),
                DateTime(2023,8,30,0,0),
                "27",
                "contnet2730",
                -14423855,
                -15072252
            )
        )
        monthScheduleList.add(
            Schedule(
                DateTime(2023,8,27,0,0),
                DateTime(2023,8,29,0,0),
                "27",
                "contnet2729",
                -14423855,
                -15072252
            )
        )
        monthScheduleList.add(
            Schedule(
                DateTime(2023,8,27,0,0),
                DateTime(2023,8,28,0,0),
                "27",
                "etDate_2728",
                -14423855,
                -15072252
            )
        )
        monthScheduleList.add(
            Schedule(
                DateTime(2023,8,28,0,0),
                DateTime(2023,9,2,0,0),
                "28",
                "contnet2829",
                -14423855,
                -15072252
            )
        )
        monthScheduleList.add(
            Schedule(
                DateTime(2023,8,28,0,0),
                DateTime(2023,9,1,0,0),
                "28",
                "contnet2828",
                -14423855,
                -15072252
            )
        )
        monthScheduleList.add(
            Schedule(
                DateTime(2023,8,28,0,0),
                DateTime(2023,8,30,0,0),
                "28",
                "contnet2828",
                -14423855,
                -15072252
            )
        )
        monthScheduleList.add(
            Schedule(
                DateTime(2023,8,30,0,0),
                DateTime(2023,9,2,0,0),
                "30",
                "contnet2828",
                -14423855,
                -15072252
            )
        )
        monthScheduleList.add(
            Schedule(
                DateTime(2023,8,31,0,0),
                DateTime(2023,9,1,0,0),
                "31",
                "contnet2828",
                -14423855,
                -15072252
            )
        )
        monthScheduleList.add(
            Schedule(
                DateTime(2023,9,1,0,0),
                DateTime(2023,9,2,0,0),
                "1",
                "contnet2828",
                -14423855,
                -15072252
            )
        )
        monthScheduleList.add(
            Schedule(
                DateTime(2023,9,2,0,0),
                DateTime(2023,9,2,0,0),
                "2",
                "contnet2828",
                -14423855,
                -15072252
            )
        )
        monthScheduleList.add(
            Schedule(
                DateTime(2023,9,2,0,0),
                DateTime(2023,9,5,0,0),
                "2",
                "contnet2828",
                -14423855,
                -15072252
            )
        )






        monthScheduleList.add(
            Schedule(
                DateTime(2023,9,2,0,0),
                DateTime(2023,9,4,0,0),
                "2",
                "contnet2828",
                -14423855,
                -15072252
            )
        )

        monthScheduleList.add(
            Schedule(
                DateTime(2023,9,3,0,0),
                DateTime(2023,9,6,0,0),
                "3",
                "contnet2828",
                -14423855,
                -15072252
            )
        )
        monthScheduleList.add(
            Schedule(
                DateTime(2023,9,3,0,0),
                DateTime(2023,9,4,0,0),
                "3",
                "contnet2828",
                -14423855,
                -15072252
            )
        )
        monthScheduleList.add(
            Schedule(
                DateTime(2023,9,3,0,0),
                DateTime(2023,9,3,0,0),
                "3",
                "contnet2828",
                -14423855,
                -15072252
            )
        )
        monthScheduleList.add(
            Schedule(
                DateTime(2023,9,5,0,0),
                DateTime(2023,9,10,0,0),
                "5",
                "contnet2828",
                -14423855,
                -15072252
            )
        )



        monthScheduleList.add(
            Schedule(
                DateTime(2023,9,9,0,0),
                DateTime(2023,9,17,0,0),
                "9",
                "contnet2828",
                -14423855,
                -15072252
            )
        )

        monthScheduleList.add(
            Schedule(
                DateTime(2023,9,9,0,0),
                DateTime(2023,9,15,0,0),
                "9",
                "contnet2828",
                -14423855,
                -15072252
            )
        )
        monthScheduleList.add(
            Schedule(
                DateTime(2023,9,9,0,0),
                DateTime(2023,9,13,0,0),
                "9",
                "contnet2828",
                -14423855,
                -15072252
            )
        )
        monthScheduleList.add(
            Schedule(
                DateTime(2023,9,9,0,0),
                DateTime(2023,9,12,0,0),
                "9",
                "contnet2828",
                -14423855,
                -15072252
            )
        )

        monthScheduleList.add(
            Schedule(
                DateTime(2023,9,11,0,0),
                DateTime(2023,9,11,0,0),
                "11",
                "contnet2828",
                -14423855,
                -15072252
            )
        )
        monthScheduleList.add(
            Schedule(
                DateTime(2023,9,11,0,0),
                DateTime(2023,9,11,0,0),
                "11",
                "contnet2828",
                -14423855,
                -15072252
            )
        )

















        dataList = Array(6) {
            kotlin.Array(5) {
                kotlin.Array(7) {
                    Schedulebar(
                        0,
                        DateTime(1, 1, 1, 0, 0, 0),
                        DateTime(1, 1, 1, 0, 0, 0),
                        "00",
                        "",
                        -1,
                        -1
                    )
                }
            }
        }

        val monthList = arrayListOf<DateTime>(
            DateTime(2023,8,27,0,0,0,),
            DateTime(2023,8,28,0,0,0,),
            DateTime(2023,8,29,0,0,0,),
            DateTime(2023,8,30,0,0,0,),
            DateTime(2023,8,31,0,0,0,),
            DateTime(2023,9,1,0,0,0,),
            DateTime(2023,9,2,0,0,0,),


            DateTime(2023,9,3,0,0,0,),
            DateTime(2023,9,4,0,0,0,),
            DateTime(2023,9,5,0,0,0,),
            DateTime(2023,9,6,0,0,0,),
            DateTime(2023,9,7,0,0,0,),
            DateTime(2023,9,8,0,0,0,),
            DateTime(2023,9,9,0,0,0,),


            DateTime(2023,9,10,0,0,0,),
            DateTime(2023,9,11,0,0,0,),
            DateTime(2023,9,12,0,0,0,),
            DateTime(2023,9,13,0,0,0,),
            DateTime(2023,9,14,0,0,0,),
            DateTime(2023,9,15,0,0,0,),
            DateTime(2023,9,16,0,0,0,),


            DateTime(2023,9,17,0,0,0,),
            DateTime(2023,9,18,0,0,0,),
            DateTime(2023,9,19,0,0,0,),
            DateTime(2023,9,20,0,0,0,),
            DateTime(2023,9,21,0,0,0,),
            DateTime(2023,9,22,0,0,0,),
            DateTime(2023,9,23,0,0,0,),


            DateTime(2023,9,24,0,0,0,),
            DateTime(2023,9,25,0,0,0,),
            DateTime(2023,9,26,0,0,0,),
            DateTime(2023,9,27,0,0,0,),
            DateTime(2023,9,28,0,0,0,),
            DateTime(2023,9,29,0,0,0,),
            DateTime(2023,9,30,0,0,0,),


            )
        /// getMonthDate(month)
        runBlocking {







        }









    }



}