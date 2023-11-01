package com.woo.calendarapp.viewmodel

import android.graphics.Color
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.woo.calendarapp.EventObserver
import com.woo.calendarapp.KakaoRetrofit
import com.woo.calendarapp.module.RepositoryRoom
import com.woo.calendarapp.repository.Repository
import com.woo.calendarapp.schedule.Schedule
import com.woo.calendarapp.schedule.Schedulebar
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.*
import org.joda.time.DateTime
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    @RepositoryRoom private val repo: Repository
) : ViewModel() {

    var toolbarDate : MutableLiveData<String> = MutableLiveData()
    fun setDate(d:String){
        toolbarDate.value = d
    }
    fun getDate():String = toolbarDate.value.toString()

    var clickDay : MutableLiveData<DateTime> = MutableLiveData()

    private lateinit var dataList : Array<Array<Array<Schedulebar>>>

    private lateinit var monthScheduleList : List<Schedule>

    private lateinit var monthList : List<DateTime>


    var clickMapLocation : MutableLiveData<Pair<Double,Double>> = MutableLiveData()
    fun setLocation(x:Double, y:Double){
        clickMapLocation.value = Pair(x,y)
    }
    fun getLocation() = clickMapLocation.value



    private val _updateKeywordMap : MutableLiveData<EventObserver.Event<Boolean>> = MutableLiveData()
    val updateKeywordMap : LiveData<EventObserver.Event<Boolean>> = _updateKeywordMap

    fun updateClick() {
        _updateKeywordMap.value = EventObserver.Event(true)
    }



    private val _addComplate : MutableLiveData<EventObserver.Event<Boolean>> = MutableLiveData()
    val addComplate : LiveData<EventObserver.Event<Boolean>> = _addComplate

    private val _addMainComplate : MutableLiveData<EventObserver.Event<Boolean>> = MutableLiveData()
    val addMainComplate : LiveData<EventObserver.Event<Boolean>> = _addMainComplate

    private val _detailComplate : MutableLiveData<EventObserver.Event<Boolean>> = MutableLiveData()
    val detailComplate : LiveData<EventObserver.Event<Boolean>> = _detailComplate

    private val _deleteComplate : MutableLiveData<EventObserver.Event<Boolean>> = MutableLiveData()
    val deleteComplate : LiveData<EventObserver.Event<Boolean>> = _deleteComplate

    private val _updateComplate : MutableLiveData<EventObserver.Event<Boolean>> = MutableLiveData()
    val updateComplate : LiveData<EventObserver.Event<Boolean>> = _updateComplate

    private val _updateOpen : MutableLiveData<EventObserver.Event<Boolean>> = MutableLiveData()
    val updateOpen : LiveData<EventObserver.Event<Boolean>> = _updateOpen

    fun updateOpen(){
        _updateOpen.postValue(EventObserver.Event(true))
    }



    private val _searchKeyword : MutableLiveData<EventObserver.Event<Boolean>> = MutableLiveData()
    val searchKeyword : LiveData<EventObserver.Event<Boolean>> = _searchKeyword


    private lateinit var searchKeyList : KakaoRetrofit.ResultSearchKeyword
    fun getSearchKeyList() = searchKeyList

    fun searchKeyword(k:String){
        viewModelScope.launch(Dispatchers.IO) { //코루틴

            val job = CoroutineScope(Dispatchers.Default).launch {
                searchKeyList = repo.getSearchKeyword(k)
            }
            job.join()
            _searchKeyword.postValue(EventObserver.Event(true))

        }
    }



    fun updateSchedule(schedule: Schedule, id:Int){
        viewModelScope.launch(Dispatchers.IO) { //코루틴

            // 활성화 dispatcherIO는 백그라운드에서 실행
            val job = viewModelScope.launch(Dispatchers.Default) {
                repo.setUpdateData(id, schedule)
            }
            job.join()

            _addMainComplate.postValue(EventObserver.Event(true))
            _updateComplate.postValue(EventObserver.Event(true))

        }
    }



    fun addSchedule(schedule: Schedule) {
        Log.e(
            "viewModel",
            " ${schedule.title}  ${schedule.scheduleBarColor} ${schedule.scheduleTextColor}  ${schedule.startDate}   ${schedule.endDate}   ${schedule.content} "
        )
        viewModelScope.launch(Dispatchers.IO) { //코루틴

            // 활성화 dispatcherIO는 백그라운드에서 실행
            val job = viewModelScope.launch(Dispatchers.Default) {
                repo.setRoomData(
                    schedule
                )
            }
            job.join()

            _addMainComplate.postValue(EventObserver.Event(true))
            _addComplate.postValue(EventObserver.Event(true))

        }

        viewModelScope.launch(Dispatchers.IO) {
            val s = repo.getRoomAllData()
            s.forEachIndexed { ind, it ->
                println("${s[ind]}")
            }
        }
    }


    lateinit var dayScheduleList : MutableList<Schedule>
    fun daySchedule(date: DateTime):List<Schedule>{
        dayScheduleList = mutableListOf()
        clickDay.value = date
        viewModelScope.launch(Dispatchers.IO) {
            val job = viewModelScope.launch(Dispatchers.Default) {
                dayScheduleList = repo.getRoomDayDate(date) as MutableList<Schedule>
            }
            job.join()

        _detailComplate.postValue(EventObserver.Event(true))
        }
        return dayScheduleList
    }



    fun deleteSchedule(schedule: Schedule){
        viewModelScope.launch(Dispatchers.IO) {
            val job = viewModelScope.launch(Dispatchers.Default) {
                repo.setDeleteDate(schedule.id)
            }
            job.join()

            _addMainComplate.postValue(EventObserver.Event(true))
            _deleteComplate.postValue(EventObserver.Event(true))
        }


    }




    fun getSchedulebarData(month:List<DateTime>): Array<Array<Array<Schedulebar>>> {
        dataList = Array(6) {
            Array(5) {
                Array(7) {
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
            }
        }

        monthList = month
        /// getMonthDate(month)
        runBlocking{

        val job = viewModelScope.launch(Dispatchers.Default) {

            withContext(Dispatchers.Default) {
                monthScheduleList = repo.getRoomMonthDate(
                    month[0],
                    month[month.size - 1]
                )
            }


            val continueList: MutableList<Schedulebar> = mutableListOf()
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

                if(cnt != continueList.size){
                    if(cnt == 5) cnt -= 1

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
                            Color.parseColor("#00000000"),
                            Color.parseColor("#000000"))

                    val d = if (date.dayOfWeek == 7) {
                        date.dayOfWeek-1
                    } else {
                        6 - date.dayOfWeek
                    }

                    repeat(d){
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
                val index_ = index__ % 7
                if (index_ == 0) week++

                var n = 0
                var x = 0
                val deleteList = ArrayList<Int>()

                continueList.forEachIndexed { index, schedulebar ->
                    if (date > schedulebar.endDate) {
                        deleteList.add(index)
                    }
                }

                deleteList.asReversed().forEach {
                    continueList.removeAt(it)
                }
                deleteList.clear()

                    run breaker@{
                        continueList.forEachIndexed label3@{ i, _ ->


                            var idx_c = i
                            if (date.dayOfWeek != 7) {
                                var cnt = 0
                                for(i in 0..4){
                                    if(dataList[week][i][index_].date != 0){
                                        cnt++
                                    }
                                }

                                idx_c = cnt + i
                                if(idx_c > continueList.size-1) return@breaker
                            }

                            dataList[week].forEachIndexed { index_1, it_1 ->     // 5번 반복
                                if (dataList[week][index_1 + n][index_].date == 0) {

                                    repeat(continueList[idx_c].endDate.dayOfYear - date.dayOfYear + 1) {

                                        dataList[week][index_1 + n][index_ + it] =


                                            Schedulebar(continueList[idx_c].date,
                                                continueList[idx_c].startDate, continueList[idx_c].endDate,
                                                continueList[idx_c].title,continueList[idx_c].content,
                                                continueList[idx_c].scheduleBarColor, continueList[idx_c].scheduleTextColor)

                                        if (index_ + it + 1 == dataList[week][index_1].size) {
                                            return@label3
                                        }
                                    }

                                    n += +index_1
                                    return@label3
                                } else if (dataList[week].count() == index_1 + n + 1) {
                                   overSchedule(date, week, index_, ix)

                                    return@breaker
                                }
                            }

                        }
                    }

                monthScheduleList.forEachIndexed lable@{ index, date_ ->

                    if (monthScheduleList[ix].startDate > date) {     //현재 날짜가 시작 날짜보다 작을때
                        if(index_%6 != 0 ){
                            overSchedule(date, week, index_, ix)
                        }
                        return@lable2
                    } else if (monthScheduleList[ix].startDate == date || index__ == 0) {
                        continueList.add(
                            Schedulebar(date.toString("dd").toInt(),
                                monthScheduleList[ix].startDate, monthScheduleList[ix].endDate,
                                monthScheduleList[ix].title,monthScheduleList[ix].content,
                                monthScheduleList[ix].scheduleBarColor, monthScheduleList[ix].scheduleTextColor)
                        )

                        dataList[week].forEachIndexed { index_1, it_1 ->
                            if (dataList[week][index_1 + x][index_].date == 0) {
                                repeat(monthScheduleList[ix].endDate.dayOfYear - date.dayOfYear + 1) {
                                    dataList[week][index_1 + x][index_ + it] =
                                        Schedulebar(date.toString("dd").toInt(),
                                            monthScheduleList[ix].startDate, monthScheduleList[ix].endDate,
                                            monthScheduleList[ix].title,monthScheduleList[ix].content,monthScheduleList[ix].scheduleBarColor,monthScheduleList[ix].scheduleTextColor)

                                    if (index_ + it + 1 == dataList[week][index_1].size) {
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
                        return@lable2
                    }
                }
            }
        }//  코루틴

        job.join()

        }
        return dataList
    }






}