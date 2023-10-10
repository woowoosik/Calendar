package com.woo.calendarapp.fragment

import android.content.ContentValues.TAG
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.children
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStoreOwner
import com.woo.calendarapp.EventObserver
import com.woo.calendarapp.R
import com.woo.calendarapp.bottomSheet.BottomSheetFragment
import com.woo.calendarapp.calendar.ChildView
import com.woo.calendarapp.calendar.DayItemView
import com.woo.calendarapp.databinding.FragmentMainBinding
import com.woo.calendarapp.schedule.Schedule
import com.woo.calendarapp.schedule.Schedulebar
import com.woo.calendarapp.utils.CalendarUtils.Companion.getMonthList
import com.woo.calendarapp.viewmodel.MainFragmentViewModel
import com.woo.calendarapp.viewmodel.MainViewModel
import dagger.hilt.android.AndroidEntryPoint
import org.joda.time.DateTime

@AndroidEntryPoint
class MainFragment: Fragment() {
    private lateinit var mainFragmentBinding : FragmentMainBinding
    private lateinit var mainFragmentVM : MainFragmentViewModel

    private lateinit var mainViewModel: MainViewModel
    private lateinit var listAll :  Array<Array<Array<Schedulebar>>>



    private var millis: Long = 0L

    companion object {
        private const val MILLIS = "MILLIS"

        fun newInstance(millis: Long) = MainFragment().apply {
            arguments = Bundle().apply {
                putLong(MILLIS, millis)
            }
        }
    }




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            millis = it.getLong(MILLIS)
        }
        println("onstart onCreate")

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        println("onstart onViewCreated")
    }



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        println("onstart onCreateView")
        mainFragmentVM = ViewModelProvider(activity as ViewModelStoreOwner)[MainFragmentViewModel::class.java]
        mainViewModel = ViewModelProvider(activity as ViewModelStoreOwner)[MainViewModel::class.java]

        mainFragmentBinding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_main, container, false)
        mainFragmentBinding.calendarView.initCalendar(
            DateTime(millis),
            getMonthList(DateTime(millis)),
            mainViewModel
        )

        getSchedulebar()



        mainViewModel.detailComplate.observe(viewLifecycleOwner, EventObserver {
        /*    val bottomSheetFragment = BottomSheetFragment()
            bottomSheetFragment.show(childFragmentManager, "bottomsheet")*/
            println("MainFramgnet detailComplate 1 ${mainViewModel.clickDay.value}")

            mainViewModel.dayScheduleList.forEachIndexed{ idx , it__ ->

                println("    ${mainViewModel.dayScheduleList[idx]}     ")

            }
            println("MainFramgnet detailComplate 2")

            val bottomSheetFragment = BottomSheetFragment()
            activity?.let { it1 -> bottomSheetFragment.show(it1.supportFragmentManager, bottomSheetFragment.tag) }
            println("MainFramgnet detailComplate 3")
        })



        /*mainFragmentBinding.calendarView.children.
        val listener = object:DayItemView.OnDayItemClickListener{
            override fun onItemClick(date: DateTime, dateList: MutableList<Schedule>) {
                val bottomSheetFragment = BottomSheetFragment()
                activity?.let { it1 -> bottomSheetFragment.show(it1.supportFragmentManager, bottomSheetFragment.tag) }

            }

        }
*/




        return mainFragmentBinding.root
    }

    override fun onStart() {
        super.onStart()
        println("onstart mainframgent")
    }
    override fun onResume() {
        super.onResume()
        mainViewModel.setDate(DateTime(millis).toString("yyyy-MM"))

    }

    fun showBottomSheet(){
        val bottomSheetFragment = BottomSheetFragment()
        activity?.let {
                it1 -> bottomSheetFragment.show(it1.supportFragmentManager, bottomSheetFragment.tag)
        }
    }


    fun drawSchedulebar(list: List<Int>, schedulebar:Schedulebar, context: Context){
        mainFragmentBinding.fl.addView(
            ChildView(
                list, schedulebar , context
            )
        )
    }



    fun getSchedulebar(){
        println("datr: ${DateTime(millis)}")
        println("getMonthList : ${getMonthList(DateTime(millis))}")

        listAll = mainViewModel.getSchedulebarData(getMonthList(DateTime(millis)))

        listAll.forEachIndexed{ idx , it__ ->
            listAll[idx].forEachIndexed { index, it ->
                println(" ")
                listAll[idx][index].forEachIndexed { index_, it_ ->
                    print("    ${listAll[idx][index][index_].title}     ")
                }
            }
            println("")
        }

        val dWidth = resources.displayMetrics.widthPixels
        val dHeight = resources.displayMetrics.heightPixels

        var h = dHeight / 42
        var _width = dWidth / 7
        val margin = 3

        var length = 0
        // var ind = 3 // 테스트 index

        listAll.forEachIndexed { index, _ ->
            var _height = h * (index * 7) - ((index - 1) * h) + index  // h * (index * 7) - ((index-1) * h )

            listAll[index].forEachIndexed { index_, it_ ->
                listAll[index][index_].forEachIndexed { index_2, it_2 ->
                    Log.e("1@", " $index  $index_  $index_2  ")
                    Log.e("1@", " ${ listAll[index][index_][index_2]}    ")

                    Log.e("2@", " it_2 : ${it_2}  ")
                    if (it_2.date != 0) {
                        // if (index_2 != 0) {   줄인거
                        // 현 date 전 date 같은가
                        if (index_2 != 0 && it_2 != listAll[index][index_][index_2 - 1]) {
                            Log.e("3@", " ${listAll[index][index_][index_2 - 1]}  ")
                            // 다름
                            drawSchedulebar(listOf(_height, ((index_2-length)*_width)+margin, length*_width-margin),listAll[index][index_][index_2-1], requireContext())


                            length = 1
                            //마지막일 경우
                            if (index_2 == listAll[index][index_].size - 1) {
                                println("  다르고 마지막 ")
                                drawSchedulebar(listOf(
                                    _height,
                                    (listAll[index][index_].size - length) * _width + margin,
                                    length * _width - margin
                                ), it_2 , requireContext() )
                                length = 0
                            }

                        } else  {
                            // 같음 ( length ++ )
                            length++
                            Log.e(TAG, " if |||  index: $index_2 | length: $length ")
                            Log.e(TAG, " if |||   ${_width}  ${_height} ")
                            // 마지막일 경우
                            if (index_2 == listAll[index][index_].size - 1) {
                                drawSchedulebar(listOf(
                                    _height,
                                    (listAll[index][index_].size - length) * _width + margin,
                                    length * _width - margin
                                ), it_2 , requireContext() )
                                length = 0
                            }
                        }


                    } else if (it_2.date == 0) {
                        // 0을 만난서 스케줄을 그림  11110
                        Log.e(TAG, " else |||  index: $index_2 | length: $length  ")
                        Log.e(TAG, " if |||   ${_width}  ${_height} ")

                        //그리기
                        Log.e(TAG, "  index:  $index_2   it:   $it_2   ${_width}  ${_height}")
                        Log.e(
                            TAG,
                            "${_height}, ${(index_2 - length) * _width}, ${length * _width} "
                        )
                        if(length!=0){
                            drawSchedulebar(listOf(
                                _height,
                                (index_2 - length) * _width + margin,
                                length * _width - margin
                            ), listAll[index][index_][index_2-1] , requireContext() )
                        }
                        length = 0
                    }
                }
                println("@@@@ ${_height}  $h")
                _height += h
                length = 0
            }
        }
    } // getSchedulebar



}