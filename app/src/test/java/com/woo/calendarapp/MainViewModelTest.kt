package com.woo.calendarapp


import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.woo.calendarapp.EventObserver
import com.woo.calendarapp.module.RepositoryRoom
import com.woo.calendarapp.repository.Repository
import com.woo.calendarapp.schedule.Schedule
import com.woo.calendarapp.schedule.Schedulebar
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.*
import org.joda.time.DateTime
import org.junit.Test

class MainViewModelTest {

    private lateinit var dataList : Array<Array<Array<Schedulebar>>>

    private var monthScheduleList : MutableList<Schedule> = mutableListOf()

    @Test
    fun getSchedulebarData() {
        monthScheduleList.add(
            Schedule(
            DateTime(2023,8,27,0,0),
            DateTime(2023,9,1,0,0),
            "contnet2701",
            "contnet2701",
            -14423855,
            -15072252
        )
        )
        monthScheduleList.add(
            Schedule(
            DateTime(2023,8,27,0,0),
            DateTime(2023,9,1,0,0),
            "contnet2701",
            "contnet2701",
            -14423855,
            -15072252
        )
        )
        monthScheduleList.add(
            Schedule(
            DateTime(2023,8,27,0,0),
            DateTime(2023,8,31,0,0),
            "contnet2731",
            "contnet2731",
            -14423855,
            -15072252
        )
        )
        monthScheduleList.add(
            Schedule(
                DateTime(2023,8,27,0,0),
                DateTime(2023,8,30,0,0),
                "contnet2730",
                "contnet2730",
                -14423855,
                -15072252
            )
        )
        monthScheduleList.add(
            Schedule(
                DateTime(2023,8,27,0,0),
                DateTime(2023,8,29,0,0),
                "contnet2729",
                "contnet2729",
                -14423855,
                -15072252
            )
        )
        monthScheduleList.add(
            Schedule(
                DateTime(2023,8,27,0,0),
                DateTime(2023,8,28,0,0),
                "etDate_2728",
                "etDate_2728",
                -14423855,
                -15072252
            )
        )
        monthScheduleList.add(
            Schedule(
                DateTime(2023,8,28,0,0),
                DateTime(2023,9,2,0,0),
                "contnet2829",
                "contnet2829",
                -14423855,
                -15072252
            )
        )
        monthScheduleList.add(
            Schedule(
                DateTime(2023,8,28,0,0),
                DateTime(2023,9,1,0,0),
                "contnet2828",
                "contnet2828",
                -14423855,
                -15072252
            )
        )
        monthScheduleList.add(
            Schedule(
                DateTime(2023,8,28,0,0),
                DateTime(2023,8,30,0,0),
                "contnet2828",
                "contnet2828",
                -14423855,
                -15072252
            )
        )
        monthScheduleList.add(
            Schedule(
                DateTime(2023,8,28,0,0),
                DateTime(2023,8,28,0,0),
                "contnet2828",
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
                        "00000000000",
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

            var continueList_: MutableList<Schedulebar> = mutableListOf()
            var continueList: MutableList<Schedulebar> = mutableListOf()
            var week = -1
            var ix = 0
            monthList.forEachIndexed lable2@{ index__, date ->
                var index_ = index__ % 7
                if (index_ == 0) week++

                var idx = 0
                println(" @@@@@@@@@@@@  week  : ${week}    ${date.toString("MM-dd")}  @@@@@@@@@@@@  $idx")

                var deleteList = ArrayList<Int>()

                continueList.forEach {
                    println(" @@@@@@@@@@@@   ${it}    @@@@@@@@@@@@ ")
                }





                if (monthScheduleList.size - 1 < ix) {

                    continueList.forEachIndexed { idx, it ->
                        if (date > it.endDate) {
                            println("continue: 삭제 들어옴")
                            deleteList.add(idx)
                        }
                    }

                    deleteList.asReversed().forEach {
                        println("continue 지우기")
                        continueList.removeAt(it)
                    }
                    deleteList.clear()


                    continueList.forEach {
                        println(" @@@@@@@@@@@@   ${it}    @@@@@@@@@@@@ ")
                    }

                    if(continueList.size >= 6){

                        // // + cnt  하고 이후 스케쥴 지우기
                        println("전: ${dataList[week][4][index__%7].title}")
                        dataList[week][4][index__%7] =
                            Schedulebar(
                                dataList[week][4][index__%7].date,
                                dataList[week][4][index__%7].startDate,
                                dataList[week][4][index__%7].endDate,
                                "+${continueList.size - 4}",
                                dataList[week][4][index__%7].content,
                                dataList[week][4][index__%7].scheduleBarColor,
                                dataList[week][4][index__%7].scheduleTextColor
                            )

                        println("후: ${dataList[week][4][index__%7].title}")

                       /* var d = if (date.dayOfWeek == 7) {
                            println("${date.dayOfWeek}  일요일 ")
                            date.dayOfWeek
                        } else {
                            println("${date.dayOfWeek}  일요일아님  d= ${7 - date.dayOfWeek} ")
                            7 - date.dayOfWeek
                        }

                        repeat(6 - d) {
                            dataList[week][4][index__%7] =
                                Schedulebar(
                                    0,
                                    DateTime(1, 1, 1, 0, 0, 0),
                                    DateTime(1, 1, 1, 0, 0, 0),
                                    "00000000000",
                                    "",
                                    -1,
                                    -1
                                )


                        }*/





                    }



                }











                println("${date.dayOfWeek} == '7'")
                var n = 0
                var x = 0
                if (date.dayOfWeek == 7) {
                    run breaker@{
                        continueList.forEachIndexed label3@{ idx_c, it_c ->

                            println("@continueList@@  ${date}   $idx_c   ${continueList[idx_c]}")
                            // 들어온 날짜가 더 클 경우 리스트에서 지우기
                            if (date > it_c.endDate) {
                                println("continue: 삭제 들어옴")
                                deleteList.add(idx_c)
                            } else {
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
                                                return@label3
                                            }
                                        }

                                        n += +index_1
                                        return@label3
                                    } else if (dataList[week].count() == index_1 + n + 1) {
                                        println(" continue  데이터 넣기X V 자리 없으면 return 하는 작업")


                                        val a = date.dayOfYear
                                        val b = monthScheduleList[ix].startDate.dayOfYear
                                        println(" @@ ${a}   ${b}")

                                        println(" if  ${monthScheduleList.size} - 1 != $ix ")
                                        if (monthScheduleList.size - 1 >= ix) {
                                            println("${monthScheduleList.size } -1  <=  ${ix}+")

                                            println("continueList size :  ${continueList.size} -4 ")

                                            // // + cnt  하고 이후 스케쥴 지우기
                                            println("${dataList[week][4][index_].title}")
                                            dataList[week][4][index_] =
                                                Schedulebar(date.toString("dd").toInt(),
                                                    monthScheduleList[ix].startDate,
                                                    monthScheduleList[ix].endDate,
                                                    "+${continueList.size-4}",
                                                    monthScheduleList[ix].content,
                                                    monthScheduleList[ix].scheduleBarColor,
                                                    monthScheduleList[ix].scheduleTextColor)

                                            println("${dataList[week][4][index_].title}")

                                            var d = if (date.dayOfWeek == 7) {
                                                println("${date.dayOfWeek}  일요일 ")
                                                date.dayOfWeek
                                            } else {
                                                println("${date.dayOfWeek}  일요일아님  d= ${7 - date.dayOfWeek} ")
                                                7 - date.dayOfWeek
                                            }

                                            repeat(6- d) {
                                                dataList[week][4][index_ + it] =
                                                    Schedulebar(
                                                        0,
                                                        DateTime(1, 1, 1, 0, 0, 0),
                                                        DateTime(1, 1, 1, 0, 0, 0),
                                                        "",
                                                        "",
                                                        -1,
                                                        -1
                                                    )


                                            }

                                            ix += 1
                                            return@breaker
                                        }




                                        println("${dataList[week][index_1 + n][index_].date }   $date")
                                        println(" continue  데이터 넣기X V 자리 없으면 return 하는 작업")
                                        // 자리 없으면 return 하는 작업
                                        return@breaker

                                    }
                                }
                            }
                        }
                    }
                    // 지난거 지우기
                    deleteList.asReversed().forEach {
                        println("continue 지우기")
                        continueList.removeAt(it)
                    }
                    deleteList.clear()
                }

                var deleteList_ = ArrayList<Int>()
                monthScheduleList.forEachIndexed lable@{ index, date_ ->

                    continueList.forEachIndexed { index, schedulebar ->
                        if (date > schedulebar.endDate) {
                            println("continue: 삭제 들어옴 ${schedulebar.endDate}")
                            deleteList.add(index)
                        }
                    }
                    println("${date} > ${date_.endDate}")




                    println("${monthScheduleList.size} - 1 < $ix")
                    if(monthScheduleList.size - 1 < ix ) return@lable2

                    println("@viwmodel@ ${date}   $ix   ${monthScheduleList[ix]}")

                    if (monthScheduleList[ix].startDate > date) {     //현재 날짜가 시작 날짜보다 작을때
                        println("데이터 넣기X 1")
                        return@lable2
                    } else if (monthScheduleList[ix].startDate == date || index__ == 0) {
                        println("${date}    start:  ${monthScheduleList[ix].startDate}    end:  ${monthScheduleList[ix].endDate}")
                        println("${monthScheduleList[ix].startDate <= date}  &&   ${date <= monthScheduleList[ix].endDate}")

                        // continueList add
                        println(" !!!! ${monthScheduleList[ix].endDate.dayOfYear} - ${date.dayOfYear} !!!!   ")

                        // 토요일이 넘어가면 다음 주 리스트(continueList)에 추가
                       /* var d = if (date.dayOfWeek == 7) {
                            println("${date.dayOfWeek}  일요일 ")
                            date.dayOfWeek
                        } else {
                            println("${date.dayOfWeek}  일요일아님  d= ${7 - date.dayOfWeek} ")
                            7 - date.dayOfWeek
                        }

                        println(" !!!! ${monthScheduleList[ix].endDate.dayOfYear - date.dayOfYear} >= $d !!!!  ")
                        if (monthScheduleList[ix].endDate.dayOfYear - date.dayOfYear >= d) {
                            println(" continue 데이터 : ${monthScheduleList[ix].endDate} ")
                            continueList.add(
                                Schedulebar(date.toString("dd").toInt(),
                                    monthScheduleList[ix].startDate, monthScheduleList[ix].endDate,
                                    monthScheduleList[ix].title,monthScheduleList[ix].content,
                                    monthScheduleList[ix].scheduleBarColor, monthScheduleList[ix].scheduleTextColor)
                            )
                        }
*/

                        println(" continue 데이터 : ${monthScheduleList[ix].endDate} ")
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


                                    println(" 들어옴 : $week  ${index_1 + x}  ${index_ + it}  ${dataList[week][index_1 + x][index_ + it]}")

                                    if (index_ + it + 1 == dataList[week][index_1].size) {
                                        println(" 들어옴->나가기 : ${index_ + it + 1}  ${dataList[week][index_1].size}")

                                        println("${monthScheduleList.size}  $ix")
                                        if (monthScheduleList.size - 1 != ix) {
                                            ix++
                                            return@lable
                                        } else {
                                            return@lable2
                                        }
                                    }
                                }

                                x += +index_1

                                if (monthScheduleList.size - 1 != ix) {
                                    ix++
                                    return@lable
                                } else {
                                    return@lable2
                                }
                            } else if (dataList[week].count() == index_1 + x + 1) {
                                println("${dataList[week].count()}  ==  ${ index_1 } + $x + 1  ")
                                println("데이터 넣기X V 자리 없으면 return 하는 작업")


                                var cnt = 0

                                val a = date.dayOfYear
                                val b = monthScheduleList[ix+cnt].startDate.dayOfYear
                                println(" @@ ${a}   ${b}")

                                println(" if  ${monthScheduleList.size} - 1 != $ix ")
                                if (monthScheduleList.size - 1 >= ix) {
                                    println("${monthScheduleList.size } -1  <=  ${ix}+${cnt}")


                                    println("continueList size :  ${continueList.size} -4 ")

                                    // // + cnt  하고 이후 스케쥴 지우기
                                    println("${dataList[week][4][index_].title}")
                                    dataList[week][4][index_] =
                                        Schedulebar(date.toString("dd").toInt(),
                                            monthScheduleList[ix].startDate,
                                            monthScheduleList[ix].endDate,
                                            "+${continueList.size-4}",
                                            monthScheduleList[ix].content,
                                            monthScheduleList[ix].scheduleBarColor,
                                            monthScheduleList[ix].scheduleTextColor)

                                    println("${dataList[week][4][index_].title}")


                                    var d = if (date.dayOfWeek == 7) {
                                        println("${date.dayOfWeek}  일요일 ")
                                        date.dayOfWeek
                                    } else {
                                        println("${date.dayOfWeek}  일요일아님  d= ${7 - date.dayOfWeek} ")
                                        7 - date.dayOfWeek
                                    }

                                    repeat(6- d){
                                        dataList[week][4][index_+it] =
                                            Schedulebar(
                                                0,
                                                DateTime(1, 1, 1, 0, 0, 0),
                                                DateTime(1, 1, 1, 0, 0, 0),
                                                "",
                                                "",
                                                -1,
                                                -1
                                            )

                                    }

                                    ix += 1
                                    return@lable
                                } else {
                                    return@lable2
                                }







                                // 자리 없으면 return 하는 작업

                            }
                        }

                    } else {
                        println("데이터 넣기X 2")
                        return@lable2
                    }

                }



                }


        }


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




/*

    @Test
    fun sche(){
        val monthList = CalendarUtils.getMonthList(DateTime(2023,8,1,0,0))
        // val roomDate
        monthList.forEachIndexed() lable2@{ index_ , it_ ->

            // 7번 반복
            // 반복 값


            if(it_ == 13){
                getDate_13()

                var x = 0
                viewmodelDate.forEachIndexed lable@ { index, schedule ->
                    println("label 14  test ${ index} | ${schedule.startDate}  ${schedule.endDate}")

                    dataList[0].forEachIndexed { index_1, it_1 ->     // 5번 반복
                        // dataList[0][index_1].forEachIndexed { index_2, it_2 ->
                        println(" 전 :  ${index_1 + x}  $index_   ${dataList[0][index_1+x][index_]}   x: $x")
                        if (dataList[0][index_1+x][index_] == 0) {
                            println("   ${viewmodelDate[index].endDate.dayOfMonth }  - $it_    " +
                                    " 반복 횟수 : ${viewmodelDate[index].endDate.dayOfMonth - it_+1}")

                            repeat(viewmodelDate[index].endDate.dayOfMonth - it_+1) {

                                dataList[0][index_1+x][index_+it] = it_
                                println(" 들어옴 : ${index_1 + x}  ${index_ + it}  ${dataList[0][index_1+x][index_+it]}")

                            }
                            x += +index_1
                            return@lable
                        }else if(dataList[0].count() == index_1+1){
                            // 자리 없으면 return 하는 작업
                            return@lable2
                        }
                    }
                }

                arrTest()
            }
            else if(it_ == 14){
                getDate_14()
                viewmodelDate.forEachIndexed lable@{ index, schedule ->
                    println("label 14 test ${index} | ${schedule.startDate}  ${schedule.endDate}")


                    dataList[0].forEachIndexed { index_1, it_1 ->     // 5번 반복
                        // dataList[0][index_1].forEachIndexed { index_2, it_2 ->
                        println(" 전 :  $index_1  $index_   ${dataList[0][index_1][index_]}")
                        if (dataList[0][index_1][index_] == 0) {
                            println(
                                "   ${viewmodelDate[index].endDate.dayOfMonth}  - $it_    " +
                                        " 반복 횟수 : ${viewmodelDate[index].endDate.dayOfMonth - it_ + 1}"
                            )
                            repeat(viewmodelDate[index].endDate.dayOfMonth - it_ + 1) {

                                dataList[0][index_1][index_+it] = it_
                                println(" 들어옴 : $index_1  ${index_ + it}  ${dataList[0][index_1][index_+it]}")

                            }
                            return@lable
                        }else if(dataList[0].count() == index_1+1){
                            // 자리 없으면 return 하는 작업
                            return@lable2
                        }


                    }
                }
                arrTest()

            } else if (it_ == 16) {
                getDate_16()
                viewmodelDate.forEachIndexed lable@{ index, schedule ->
                    println("label 16 test ${index} | ${schedule.startDate}  ${schedule.endDate}")


                    dataList[0].forEachIndexed { index_1, it_1 ->     // 5번 반복
                        // dataList[0][index_1].forEachIndexed { index_2, it_2 ->
                        println(" 전 :  $index_1  $index_   ${dataList[0][index_1][index_]}")
                        if (dataList[0][index_1][index_] == 0) {
                            println(
                                "   ${viewmodelDate[index].endDate.dayOfMonth}  - $it_    " +
                                        " 반복 횟수 : ${viewmodelDate[index].endDate.dayOfMonth - it_ + 1}"
                            )
                            repeat(viewmodelDate[index].endDate.dayOfMonth - it_ + 1) {

                                dataList[0][index_1][index_ + it] = it_
                                println(" 들어옴 : $index_1  ${index_ + it}  ${dataList[0][index_1][index_ + it]}")

                            }
                            return@lable
                        }else if(dataList[0].count() == index_1+1){
                            // 자리 없으면 return 하는 작업
                            return@lable2
                        }



                    }
                }
                arrTest()
            } else if (it_ == 17) {
                getDate_17()
                var x = 0
                viewmodelDate.forEachIndexed lable@ { index, schedule ->
                    println("label 17  test ${ index} | ${schedule.startDate}  ${schedule.endDate}")

                    dataList[0].forEachIndexed { index_1, it_1 ->     // 5번 반복
                        // dataList[0][index_1].forEachIndexed { index_2, it_2 ->
                        println(" 전 :  ${index_1 + x}  $index_   ${dataList[0][index_1+x][index_]}   x: $x")
                        println(" 전 :  count :  ${dataList[0].count()}   index+1:  ${index_1+1} ")
                        if (dataList[0][index_1+x][index_] == 0) {
                            println("   ${viewmodelDate[index].endDate.dayOfMonth }  - $it_    " +
                                    " 반복 횟수 : ${viewmodelDate[index].endDate.dayOfMonth - it_+1}")

                            println(" ${viewmodelDate[index].endDate.dayOfMonth}  >  ${dateDate[dateDate.size-1]}")
                            if(viewmodelDate[index].endDate.dayOfMonth > dateDate[dateDate.size-1]){
                                repeat(dateDate[dateDate.size-1] - it_ + 1) {


                                    dataList[0][index_1][index_ + it] = it_
                                    println(" 들어옴 : $index_1  ${index_ + it}  ${dataList[0][index_1][index_ + it]}")

                                }
                            }
                            x += +index_1
                            return@lable
                        }else if(dataList[0].count() == index_1+1){
                            // 자리 없으면 return 하는 작업
                            return@lable2
                        }

                    }
                }

                arrTest()


            }
        }


        println("")
        dataList[0][1].map { print(" $it ") }

    }

    @Test
    fun arrTest(){
        dataList[0].forEachIndexed { index, it ->
            println(" ")
            dataList[0][index].forEachIndexed { index_, it_ ->
                print("    ${dataList[0][index][index_]}     ")
            }
        }

    }
*/

}

