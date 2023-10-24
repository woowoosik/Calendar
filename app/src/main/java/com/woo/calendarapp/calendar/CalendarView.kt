package com.woo.calendarapp.calendar

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.widget.FrameLayout
import androidx.core.content.withStyledAttributes
import androidx.core.view.children
import com.woo.calendarapp.bottomSheet.BottomSheetFragment
import com.woo.calendarapp.schedule.Schedule
import com.woo.calendarapp.utils.CalendarUtils
import com.woo.calendarapp.viewmodel.MainViewModel
import org.joda.time.DateTime
import org.joda.time.DateTimeConstants
import kotlin.math.max

class CalendarView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null
) : FrameLayout(context, attrs) {

    private var _height: Float = 0f
    private var dWidth = resources.displayMetrics.widthPixels

    init {
        val dHeight = resources.displayMetrics.heightPixels
        _height = (dHeight / 7).toFloat()
    }

    init{


    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val h = paddingTop + paddingBottom + max(
            suggestedMinimumHeight,
            (_height * CalendarUtils.WEEKS_PER_MONTH).toInt()
        )
        setMeasuredDimension(FrameLayout.getDefaultSize(suggestedMinimumWidth, widthMeasureSpec), h)

    }

    override fun onLayout(changed: Boolean, l: Int, t: Int, r: Int, b: Int) {

        val iWidth = (dWidth / DateTimeConstants.DAYS_PER_WEEK).toFloat()
        val iHeight = (height / CalendarUtils.WEEKS_PER_MONTH).toFloat()

        var index = 0
        children.forEach { view ->
            val left = (index % DateTimeConstants.DAYS_PER_WEEK) * iWidth
            val top = (index / DateTimeConstants.DAYS_PER_WEEK) * iHeight
            view.layout(left.toInt(), top.toInt(), (left + iWidth).toInt(), (top + iHeight).toInt())

            index++
        }

    }

    fun initCalendar(firstDayOfMonth: DateTime, list: List<DateTime>, viewModel: MainViewModel) {
        list.forEach {
            addView(
                DayItemView(
                    context = context,
                    date = it,
                    firstDayOfMonth = firstDayOfMonth,
                    mainViewModel = viewModel
                )
            )
        }


    }





}