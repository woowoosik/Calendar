package com.woo.calendarapp.viewmodel

import android.graphics.Color
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.woo.calendarapp.EventObserver
import com.woo.calendarapp.KakaoRetrofit
import com.woo.calendarapp.R
import com.woo.calendarapp.kakaoApi.KakaoAPI
import com.woo.calendarapp.module.RepositoryRoom
import com.woo.calendarapp.repository.Repository
import com.woo.calendarapp.schedule.Schedule
import com.woo.calendarapp.schedule.Schedulebar
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.*
import org.joda.time.DateTime
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
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

    val _updateKeywordMap : MutableLiveData<EventObserver.Event<Boolean>> = MutableLiveData()
    val updateKeywordMap : LiveData<EventObserver.Event<Boolean>> = _updateKeywordMap





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

    private val _searchKeyword : MutableLiveData<EventObserver.Event<Boolean>> = MutableLiveData()
    val searchKeyword : LiveData<EventObserver.Event<Boolean>> = _searchKeyword


    private lateinit var searchKeyList : KakaoRetrofit.ResultSearchKeyword
    fun getSearchKeyList() = searchKeyList

    fun searchKeyword(k:String){
        viewModelScope.launch(Dispatchers.IO) { //코루틴


/*

            val job = CoroutineScope(Dispatchers.Default).launch {
                 searchKeyList = repo.getSearchKeyword(k)

                println("viewmodel@@@@ = ${searchKeyList.documents.size}")
            }
            job.join()
*/

            /*val baseUrl = "https://dapi.kakao.com/"
            val retrofit = Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .build()

            val api = retrofit.create(KakaoAPI::class.java) // 통신 인터페이스를 객체로 생성

            searchKeyList = KakaoRetrofit.ResultSearchKeyword(mutableListOf())

            var list : MutableList<KakaoRetrofit.Place> = mutableListOf()
            for(i in 1..3){
                val call =
                    api.getSearchKeyword("KakaoAK 52109dafc4fdea8d693a1cfb2a51d9f3", k.replace("\\s".toRegex(), ""), i) // 검색 조건 입력

                val response = call.execute()
                println("CoroutineScope 1")
                CoroutineScope(Dispatchers.Main).launch {
                    val job = CoroutineScope(Dispatchers.Main).launch {
                        val body = response.body()

                        println("CoroutineScope 2")
                        if (response.isSuccessful) {
                            if (body != null) {
                                for(i in 0 until body.documents.size){
                                    list.add(body.documents[i])
                                }
                                println("${response}")
                            //    println("CoroutineScope 3 ${searchKeyList.documents.size}    ${searchKeyList.documents[0].place_name}")
                            }
                        } else {
                            Log.d("Retrofit", "이미지 찾기 | 통신 실패")
                        }
                    }
                    job.join()

                }

            }

            searchKeyList.documents = list
*/
            val job = CoroutineScope(Dispatchers.Default).launch {
                searchKeyList = repo.getSearchKeyword(k)

                println("viewmodel@@@@ = ${searchKeyList.documents.size}")
            }
            job.join()


            println("viewmodel@@@@ ${searchKeyList.documents.size}")
            _searchKeyword.postValue(EventObserver.Event(true))

        }
    }


    fun updateOpen(){
        _updateOpen.postValue(EventObserver.Event(true))
    }
    fun updateSchedule(schedule: Schedule, id:Int){

        println("updateSchedule : id :${id}")
        println("updateSchedule : id :${schedule.id}")
        println("updateSchedule : ${schedule}")
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
        Log.e("MainViewModel", " ")
        // Log.e("viemodel", " $s")
        //Log.e("viewModel", " $title  $barColor $textColor  $startDate   $endDate   $content ")

        Log.e(
            "viewModel",
            " ${schedule.title}  ${schedule.scheduleBarColor} ${schedule.scheduleTextColor}  ${schedule.startDate}   ${schedule.endDate}   ${schedule.content} "
        )

        Log.e("mainViewModel ", "테스트")
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

            println("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@")
            val s = repo.getRoomAllData()
            s.forEachIndexed { ind, it ->
                println("${s[ind]}")
            }
        }
    }

    fun getMonthDate(month:List<DateTime>){

        println("${month[0] }   ${month[month.size-1]}")

        viewModelScope.launch(Dispatchers.IO) {
            println(" getMonthDate 1 @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@")
            CoroutineScope(Dispatchers.IO).async {
                monthScheduleList = repo.getRoomMonthDate(
                    month[0],
                    month[month.size-1]
                )
            }.await()
            monthScheduleList.forEachIndexed { ind, it->
                println("${monthScheduleList[ind]}")
            }
            println(" getMonthDate 2 @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@")
        }
    }


    lateinit var dayScheduleList : MutableList<Schedule>
    fun daySchedule(date: DateTime):List<Schedule>{
        dayScheduleList = mutableListOf()
        clickDay.value = date
        println(" daySchedule 1 $date @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@")
        viewModelScope.launch(Dispatchers.IO) {
            val job = viewModelScope.launch(Dispatchers.Default) {
                dayScheduleList = repo.getRoomDayDate(date) as MutableList<Schedule>
            }
            job.join()



        println(" daySchedule 2 $date @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@")
        dayScheduleList.forEachIndexed { ind, it->
            println("${dayScheduleList[ind]}")
        }

        println(" daySchedule 3  @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@")
        _detailComplate.postValue(EventObserver.Event(true))
        }
        return dayScheduleList
    }



    fun deleteSchedule(schedule: Schedule){
        Log.e("mainViewModel", "id:  ${schedule.id}   데이터 지우기")
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
            kotlin.Array(5) {
                kotlin.Array(7) {
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

                if(cnt != continueList.size){
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
                            Color.parseColor("#00000000"),
                            Color.parseColor("#000000"))

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


                println("${date.dayOfWeek} == 7")



              //  if (date.dayOfWeek == 7) {
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
                            // }
                        }
                    }
              //  }




            /*else{
                    // 일요일 아님
                    println(" ")
                    for(i in 0..4){
                        if(dataList[week][i][index_].date != 0){
                            println("$i  id: ${dataList[week][i][index_]}  ${dataList[week][i][index_]}")
                        }
                    }
                    println(" ")
                    println("일요일 아님")

                    run breaker@{
                        continueList.forEachIndexed label3@{ i, _ ->
                            var idx_c = i
                            var cnt = 0
                            for(i in 0..4){
                                if(dataList[week][i][index_].date != 0){
                                    cnt++
                                }
                            }

                            idx_c = cnt + i
                            if(idx_c > continueList.size-1) return@breaker

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

                                    println(" ${date.dayOfYear}  ==  ${continueList[idx_c].endDate.dayOfYear}")
                                    println(" 반복 ${continueList[idx_c].endDate.dayOfYear - date.dayOfYear + 1} ")
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
                            // }
                        }
                    }
                    println(" ")
                    for(i in 0..4){
                        if(dataList[week][i][index_].date != 0){
                            println("$i  id: ${dataList[week][i][index_]}  ${dataList[week][i][index_]}")
                        }
                    }
                    println(" ")*/





                   /* println(" ")
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


*/



              //  } // if (date.dayOfWeek == 7) else





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
                                        // overSchedule(date, week, index_, ix)

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
            dataList.forEachIndexed { i1, d1 ->
                println(" ")
                println("-title- ")
                println(" ")
                dataList[i1].forEachIndexed { i2, d2 ->
                    println(" ")
                    dataList[i1][i2].forEachIndexed { i3, d3 ->
                        print("${dataList[i1][i2][i3].title} ")
                    }
                }
            }





/*
            monthScheduleList.forEachIndexed { ind, it ->
                println("${monthScheduleList[ind]}")
            }
            println(" getMonthDate 2 @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@")


            var continueList: MutableList<Schedulebar> = mutableListOf()
            var week = -1
            var ix = 0
            monthList.forEachIndexed lable2@{ index__, date ->
                var index_ = index__ % 7
                if (index_ == 0) week++

                var idx = 0
                println(" @@@@@@@@@@@@  week  : ${week}    ${date.toString("MM-dd")}  @@@@@@@@@@@@  $idx")


                continueList.forEach {
                    println(" @@@@@@@@@@@@   ${it}    @@@@@@@@@@@@ ")
                }

                var n = 0
                var x = 0
                var deleteList = ArrayList<Int>()
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
                }

                monthScheduleList.forEachIndexed lable@{ index, date_ ->

                    println("@viwmodel@ ${date}   $ix   ${monthScheduleList[ix]}")

                    if (monthScheduleList[ix].startDate > date) {     //현재 날짜가 시작 날짜보다 작을때
                        println("데이터 넣기X 1")
                        return@lable2
                    } else if (monthScheduleList[ix].startDate == date || index__ == 0) {


                        println("${date}    start:  ${monthScheduleList[ix].startDate}    end:  ${monthScheduleList[ix].endDate}")
                        println("${monthScheduleList[ix].startDate <= date}  &&   ${date <= monthScheduleList[ix].endDate}")


                        // continueList add
                        println(" !!!! ${monthScheduleList[ix].endDate.dayOfYear} - ${date.dayOfYear} !!!!   ")
                        var d = if (date.dayOfWeek == 7) {
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
                                // 자리 없으면 return 하는 작업
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
            }*/
        }//  코루틴

        job.join()

        }


        dataList.forEachIndexed { i1, d1 ->
            println(" ")
            println(" -date-  ")
            println(" ")
            dataList[i1].forEachIndexed { i2, d2 ->
                println(" ")
                dataList[i1][i2].forEachIndexed { i3, d3 ->
                    print("${dataList[i1][i2][i3].date} ")
                }
            }
        }



        return dataList
    }






}