package com.woo.calendarapp

import com.woo.calendarapp.schedule.Schedule
import com.woo.calendarapp.schedule.Schedulebar
import kotlinx.coroutines.runBlocking
import org.joda.time.DateTime
import org.junit.Test

class T {
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
                DateTime(2023,9,9,0,0),
                DateTime(2023,9,10,0,0),
                "9",
                "contnet2828",
                -14423855,
                -15072252
            )
        )
        monthScheduleList.add(
            Schedule(
                DateTime(2023,9,9,0,0),
                DateTime(2023,9,10,0,0),
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


        runBlocking{



            monthScheduleList.forEachIndexed { ind, it ->
                println("${monthScheduleList[ind]}")
            }
            println(" getMonthDate 2 @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@")


            var continueList: MutableList<Schedulebar> = mutableListOf()
            var week = -1
            var ix = 0

            var moveSchedule = Schedulebar(
            0,
            DateTime(1, 1, 1, 0, 0, 0),
            DateTime(1, 1, 1, 0, 0, 0),
            "0",
            "",
            -1,
            -1
            )



            fun overSchedule(date:DateTime, week:Int, index_:Int, ix:Int){
                var cnt = 0
                for(i in 0..4){
                    if(dataList[week][i][index_].date != 0){
                        cnt++
                    }
                }

                println("${cnt != continueList.size}   cnt: $cnt   size: ${continueList.size} ")
                println("${ DateTime(date) <= DateTime(date).plusDays(1)  }   date: ${DateTime(date)}   date+1: ${DateTime(date).plusDays(1)} ")
                if(cnt != continueList.size && DateTime(date) <= DateTime(date).plusDays(1)){
                    if(cnt == 5) cnt -= 1

                    println("overSchedule + 추가 cnt ${cnt}  +${continueList.size-cnt}")

                    moveSchedule =
                        Schedulebar(date.toString("dd").toInt(),
                            DateTime(date).plusDays(1),
                            dataList[week][4][index_].endDate,
                            dataList[week][4][index_].startDate.toString("d"),
                            dataList[week][4][index_].content,
                            dataList[week][4][index_].scheduleBarColor,
                            dataList[week][4][index_].scheduleTextColor
                        )


                    dataList[week][4][index_] =
                        Schedulebar(date.toString("dd").toInt(),
                            monthScheduleList[ix].startDate,
                            monthScheduleList[ix].endDate,
                            "+${continueList.size-cnt}",
                            monthScheduleList[ix].content,
                            com.google.android.material.R.color.mtrl_btn_transparent_bg_color,
                            R.color.black)

                    println("${dataList[week][4][index_].title}")


                    var d = if (date.dayOfWeek == 7) {
                        println("${date.dayOfWeek}  일요일 ")
                        date.dayOfWeek-1
                    } else {
                        println("${date.dayOfWeek}  일요일아님  d= ${6 - date.dayOfWeek} ")
                        6 - date.dayOfWeek
                    }

                    repeat(d){
                        println("$index_  ${it+1}")
                        dataList[week][4][index_+it+1] =
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





            monthList.forEachIndexed lable2@{ index__, date ->
                var index_ = index__ % 7
                if (index_ == 0) week++

                var idx = 0
                println(" @@@@@@@@@@@@  week  : ${week}    ${date.toString("MM-dd")}  @@@@@@@@@@@@  $idx")
                println(" ")
                for(i in 0..4){
                    if(dataList[week][i][index_].date != 0){
                        println("$i ${dataList[week][i][index_]}")
                    }
                }
                println(" ")
                continueList.forEach {
                    println(" @@@@@@@@@@@@   ${it}    @@@@@@@@@@@@ ")
                }

                var n = 0
                var x = 0
                var deleteList = ArrayList<Int>()


                println(" 269 삭제 전 continueList.size ${continueList.size}")

                continueList.forEachIndexed { index, schedulebar ->
                    if (date > schedulebar.endDate) {
                        println("continue: 삭제 들어옴")
                        deleteList.add(index)
                    }
                }
                println("")
                deleteList.forEach {
                    print("$it ")
                }
                println("")
                deleteList.asReversed().forEach {
                    println("continue 지우기 $it")
                    continueList.removeAt(it)
                }
                deleteList.clear()








/*
                println("${date.dayOfWeek} == 7")

                if (date.dayOfWeek == 7 ) {
                    run breaker@{
                        continueList.forEachIndexed label3@{ idx_c, it_c ->

                            println("@continueList@@  ${date}   $idx_c   ${continueList[idx_c]}")
                            // 들어온 날짜가 더 클 경우 리스트에서 지우기
                           *//* if (date > it_c.endDate) {
                                println("continue: 삭제 들어옴")
                                 deleteList.add(idx_c)
                            } else {*//*
                                dataList[week].forEachIndexed { index_1, it_1 ->     // 5번 반복
                                    // dataList[0][index_1].forEachIndexed { index_2, it_2 ->
                                    println(" 전 :  ${index_1 + n}  $index_   ${dataList[0][index_1 + n][index_]}   x: $n")
                                    if (dataList[week][index_1 + n][index_].date == 0) {
                                        // println("   ${viewmodelDate[index].endDate.dayOfMonth }  - $date  반복 횟수 : ${viewmodelDate[index].endDate.dayOfMonth - it_+1}")

                                        println(" ${date.dayOfYear}  ==  ${it_c.endDate.dayOfYear}")
                                        repeat(it_c.endDate.dayOfYear - date.dayOfYear + 1) {

                                            dataList[week][index_1 + n][index_ + it] =


                                                Schedulebar(continueList[idx_c].date,
                                                    continueList[idx_c].startDate, continueList[idx_c].endDate,
                                                    continueList[idx_c].title,continueList[idx_c].content,
                                                    continueList[idx_c].scheduleBarColor, continueList[idx_c].scheduleTextColor)



                                            println(" 들어옴 : ${index_1 + n}  ${index_ + it}  ${dataList[week][index_1 + n][index_ + it]}")

                                            if (index_ + it + 1 == dataList[week][index_1].size) {
                                                println(" 들어옴-나가기 : ${index_ + it + 1}  ${dataList[week][index_1].size}")

                                                println("315 + 추가 con size ${continueList.size}")
                                                return@label3
                                            }
                                        }

                                        n += +index_1
                                        return@label3
                                    } else if (dataList[week].count() == index_1 + n + 1) {
                                        println(" 443 continue  데이터 넣기X V 자리 없으면 return 하는 작업")
                                        // 자리 없으면 return 하는 작업

                                        overSchedule(date, week, index_, ix)

                                        return@breaker
                                    }
                                }
                            // }
                        }
                    }
                }else{


                // 일요일 아님

                    println(" ")
                    for(i in 0..4){
                        if(dataList[week][i][index_].date != 0){
                            println("$i  id: ${dataList[week][i][index_]}  ${dataList[week][i][index_]}")
                        }
                    }
                    println(" ")

                    println("moveSchedule  ${moveSchedule}")
                    if(moveSchedule.date != 0){
                        run label4@{
                            dataList[week].forEachIndexed { index_1, it_1 ->     // 5번 반복
                                // dataList[0][index_1].forEachIndexed { index_2, it_2 ->
                                println(" moveSchedule 전 :  ${index_1 + n}  $index_   ${dataList[0][index_1 + n][index_]}   x: $n")
                                println(" moveSchedule 전 : moveSchedule.date  ${moveSchedule.date}  != 0")
                                if ( dataList[week][index_1 + n][index_].date == 0) {
                                    // println("   ${viewmodelDate[index].endDate.dayOfMonth }  - $date  반복 횟수 : ${viewmodelDate[index].endDate.dayOfMonth - it_+1}")

                                    repeat(moveSchedule.endDate.dayOfYear - date.dayOfYear + 1) {
                                        dataList[week][index_1 + n][index_ + it] =
                                            Schedulebar(moveSchedule.date,
                                                moveSchedule.startDate, moveSchedule.endDate,
                                                moveSchedule.title,moveSchedule.content,
                                                moveSchedule.scheduleBarColor, moveSchedule.scheduleTextColor)

                                        println("moveSchedule 들어옴 : ${index_1 + n}  ${index_ + it}  ${dataList[week][index_1 + n][index_ + it]}")

                                        if (index_ + it + 1 == dataList[week][index_1].size) {
                                            overSchedule(date, week, index_, ix)
                                            return@label4
                                        }
                                    }

                                    n += +index_1
                                    return@label4
                                } else if (dataList[week].count() == index_1 + n + 1) {
                                    println("moveSchedule  continue  데이터 넣기X V 자리 없으면 return 하는 작업")


                                    overSchedule(date, week, index_, ix)

                                    // 자리 없으면 return 하는 작업
                                    return@label4
                                }
                            }
                        }
                    }


                    moveSchedule= Schedulebar(
                        0,
                        DateTime(1, 1, 1, 0, 0, 0),
                        DateTime(1, 1, 1, 0, 0, 0),
                        "00",
                        "",
                        -1,
                        -1
                    )


                    println(" ")
                    for(i in 0..4){
                        if(dataList[week][i][index_].date != 0){
                            println("$i ${dataList[week][i][index_]}")
                        }
                    }
                    println(" ")






                }*/



                run breaker@{
                    var cnt = 0
                    for(i in 0..4){
                        if(dataList[week][i][index_].date != 0){
                            cnt++
                        }
                    }
                    continueList.forEachIndexed label3@{ i, _ ->

                        println(" 전 ")
                        for(i in 0..4){
                            if(dataList[week][i][index_].date != 0){
                                println("$i  id: ${dataList[week][i][index_]}  ${dataList[week][i][index_]}")
                            }
                        }
                        println(" ")

                        var idx_c = i
                        if (date.dayOfWeek != 7) {

                            idx_c = cnt + i

                            println(" i  $i +  cnt: $cnt = idx_c ${idx_c}  >  ${continueList.size-1} ")
                            if(idx_c > continueList.size-1) return@breaker
                        }



                        println("@continueList@@  ${date}   $idx_c   ${continueList[idx_c]}")
                        // 들어온 날짜가 더 클 경우 리스트에서 지우기
                        /* if (date > it_c.endDate) {
                             println("continue: 삭제 들어옴")
                              deleteList.add(idx_c)
                         } else {*/
                        dataList[week].forEachIndexed { index_1, it_1 ->     // 5번 반복
                            // dataList[0][index_1].forEachIndexed { index_2, it_2 ->
                            println(" 전 :  ${index_1 + n}  $index_   ${dataList[0][index_1 + n][index_]}   x: $n")
                            if (dataList[week][index_1 + n][index_].date == 0) {
                                // println("   ${viewmodelDate[index].endDate.dayOfMonth }  - $date  반복 횟수 : ${viewmodelDate[index].endDate.dayOfMonth - it_+1}")

                                println(" ${date.dayOfYear}  ==  ${continueList[idx_c].endDate.dayOfYear}")
                                repeat(continueList[idx_c].endDate.dayOfYear - date.dayOfYear + 1) {

                                    dataList[week][index_1 + n][index_ + it] =


                                        Schedulebar(continueList[idx_c].date,
                                            continueList[idx_c].startDate, continueList[idx_c].endDate,
                                            continueList[idx_c].title,continueList[idx_c].content,
                                            continueList[idx_c].scheduleBarColor, continueList[idx_c].scheduleTextColor)



                                    println(" 들어옴 : ${index_1 + n}  ${index_ + it}  ${dataList[week][index_1 + n][index_ + it]}")

                                    if (index_ + it + 1 == dataList[week][index_1].size) {
                                        println(" 들어옴-나가기 : ${index_ + it + 1}  ${dataList[week][index_1].size}")

                                        println("315 + 추가 con size ${continueList.size}")
                                        return@label3
                                    }
                                }

                                n += +index_1
                                return@label3
                            } else if (dataList[week].count() == index_1 + n + 1) {
                                println(" 443 continue  데이터 넣기X V 자리 없으면 return 하는 작업")
                                // 자리 없으면 return 하는 작업

                                overSchedule(date, week, index_, ix)

                                return@breaker
                            }
                        }
                        println(" 후 ")
                        for(i in 0..4){
                            if(dataList[week][i][index_].date != 0){
                                println("$i  id: ${dataList[week][i][index_]}  ${dataList[week][i][index_]}")
                            }
                        }
                        println(" ")
                        // }
                    }
                }










                println(" 330 삭제 전 continueList.size ${continueList.size}")

                monthScheduleList.forEachIndexed lable@{ index, date_ ->

                    println("@viwmodel@ ${date}   $ix   ${monthScheduleList[ix]}")


                    if (monthScheduleList[ix].startDate > date) {     //현재 날짜가 시작 날짜보다 작을때
                        println("데이터 넣기X 1")

                        println(" 565  index_  $index_   ${index_%6}")
                        if(index_%6 != 0 ){
                            println("512 ${dataList[week][4][index_]}")

                            overSchedule(date, week, index_, ix)
                        }

                        return@lable2
                    } else if (monthScheduleList[ix].startDate == date || index__ == 0) {


                        println(" 644 ${date}    start:  ${monthScheduleList[ix].startDate}    end:  ${monthScheduleList[ix].endDate}")
                        println(" 645 ${date}    date  ${monthScheduleList[ix]}    ")

                        continueList.forEach {
                            println(" 648  @@@@@@@@@@@@   ${it}    @@@@@@@@@@@@ ")
                        }
                        println("${monthScheduleList[ix].startDate <= date}  &&   ${date <= monthScheduleList[ix].endDate}")



                            continueList.add(
                                Schedulebar(date.toString("dd").toInt(),
                                    monthScheduleList[ix].startDate, monthScheduleList[ix].endDate,
                                    monthScheduleList[ix].title,monthScheduleList[ix].content,
                                    monthScheduleList[ix].scheduleBarColor, monthScheduleList[ix].scheduleTextColor)
                            )



                        println("데이터 넣기")
                        dataList[week].forEachIndexed { index_1, it_1 ->
                            // dataList[0][index_1].forEachIndexed { index_2, it_2 ->
                            println(" 전 : $week  ${index_1 + x}  $index_   ${dataList[week][index_1 + x][index_]}   x: $x")
                            if (dataList[week][index_1 + x][index_].date == 0) {
                                // println("   ${viewmodelDate[index].endDate.dayOfMonth }  - $date  반복 횟수 : ${viewmodelDate[index].endDate.dayOfMonth - it_+1}")



                                println(" ${date.dayOfYear}  ==  ${monthScheduleList[ix].endDate.dayOfYear}")
                                repeat(monthScheduleList[ix].endDate.dayOfYear - date.dayOfYear + 1) {
                                    dataList[week][index_1 + x][index_ + it] =
                                        Schedulebar(date.toString("dd").toInt(),
                                            monthScheduleList[ix].startDate, monthScheduleList[ix].endDate,
                                            monthScheduleList[ix].title,monthScheduleList[ix].content,monthScheduleList[ix].scheduleBarColor,monthScheduleList[ix].scheduleTextColor)


                                    println(" 750  들어옴 : $week  ${index_1 + x}  ${index_ + it}  ${dataList[week][index_1 + x][index_ + it]}")

                                    println("750 ${index_ + it + 1}   ${dataList[week][index_1].size}")



                                    if (index_ + it + 1 == dataList[week][index_1].size) {
                                        println(" 들어옴->나가기 : ${index_ + it + 1}  ${dataList[week][index_1].size}")
                                        //overSchedule(date, week, index_, ix)

                                        println("678 + 추가 con size ${continueList.size}")
                                        println("${monthScheduleList.size}  $ix")
                                        if (monthScheduleList.size - 1 != ix) {
                                            ix++
                                            return@lable
                                        } else {
                                            return@lable2
                                        }
                                    }


                                }

                                overSchedule(date, week, index_, ix)

                                x += +index_1

                                if (monthScheduleList.size - 1 != ix) {
                                    ix++
                                    return@lable
                                } else {
                                    return@lable2
                                }
                            } else if (dataList[week].count() == index_1 + x + 1) {
                                println("${dataList[week].count()}  ==  ${ index_1 } + $x + 1  ")
                                println(" 769 데이터 넣기X V 자리 없으면 return 하는 작업")
                                // 자리 없으면 return 하는 작업


                                println("773 ${dataList[week][4][index_]}")

                                overSchedule(date, week, index_, ix)


                                if (monthScheduleList.size - 1 != ix) {
                                    ix++
                                    return@lable
                                } else {
                                    return@lable2
                                }
                            }
                        }

                    } else {
                        println("데이터 넣기X 2")



                        return@lable2
                    }
                }
            }


        } // run


        dataList.forEachIndexed { i1, d1 ->
            println(" ")
            println(" ")
            dataList[i1].forEachIndexed { i2, d2 ->
                println(" ")
                dataList[i1][i2].forEachIndexed { i3, d3 ->
                    print("${dataList[i1][i2][i3].title} ")
                }
            }
        }



    }






}


