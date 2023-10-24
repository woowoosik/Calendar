package com.woo.calendarapp.calendar

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Rect
import android.text.TextPaint
import android.util.AttributeSet
import android.util.Log
import android.view.View
import com.woo.calendarapp.fragment.MainFragment
import com.woo.calendarapp.schedule.Schedule
import com.woo.calendarapp.utils.CalendarUtils
import com.woo.calendarapp.viewmodel.MainViewModel
import org.joda.time.DateTime

class DayItemView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0,
    private val date: DateTime = DateTime(),
    private val firstDayOfMonth: DateTime = DateTime(),
    private val mainViewModel: MainViewModel
) : View(context) {



    private val bounds = Rect()

    private var paint: Paint = Paint()

    init {

        val dHeight = resources.displayMetrics.heightPixels
        var h = dHeight / 42
        paint = TextPaint().apply {
            isAntiAlias = true
            textSize = h.toFloat()
            color = Color.parseColor("#518525")
            setBackgroundColor(Color.parseColor("#00000000"))
            color = CalendarUtils.getDateColor(date.dayOfWeek)

            if (!CalendarUtils.isSameMonth(date, firstDayOfMonth)) {
                alpha = 50
            }
        }
        setOnClickListener {
            mainViewModel.daySchedule(date)
        }

    }


    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        if (canvas == null) return

        val date = date.dayOfMonth.toString()
        paint.getTextBounds(date, 0, date.length, bounds)
        canvas.drawText(
            date,
            ((width - bounds.width())/2 ).toFloat()  ,
            (height  /8 +5 ).toFloat(),
            paint
        )
    }

}