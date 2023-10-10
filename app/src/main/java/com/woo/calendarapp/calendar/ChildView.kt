package com.woo.calendarapp.calendar

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import android.graphics.Typeface
import android.graphics.drawable.GradientDrawable
import android.util.AttributeSet
import android.util.DisplayMetrics
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.core.content.ContextCompat
import androidx.core.view.forEach
import com.woo.calendarapp.R
import com.woo.calendarapp.databinding.ChildBinding
import com.woo.calendarapp.schedule.Schedule
import com.woo.calendarapp.schedule.Schedulebar


class ChildView @JvmOverloads constructor(
    private var list: List<Int>,
    private var schedulebar : Schedulebar,
    context: Context, attrs: AttributeSet? = null
) : LinearLayout(context, attrs) {

    private val binding =
        ChildBinding.inflate(LayoutInflater.from(context))


    init{
        Log.e("ChildView", " ")
        initView()
    }


    @SuppressLint("ResourceAsColor")
    private fun initView(){
        // margin
        val params = LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.MATCH_PARENT
        )
        params.marginStart = list[1]
        params.topMargin = list[0]
        binding.childTv.layoutParams = params

        Log.e("ChildView ", " ${list[0]}  ${list[1]}  ${list[2]}")
        binding.childTv.text = " 테스트 "
       // binding.childTv.setBackgroundColor(Color.parseColor("#dd0f00"))

        binding.childTv.setBackgroundResource(R.drawable.schedulebar)

        var bgShape : GradientDrawable = binding.childTv.background as GradientDrawable
       /* if(list[2] == 1073){
            bgShape.setColor(Color.parseColor("#FF91CA8B"))
        }else{
            bgShape.setColor(R.color.schedulebarColor)
        }*/


        println(" child bar:  ${schedulebar.scheduleBarColor}")
        println(" child text:  ${schedulebar.scheduleTextColor}")



        bgShape.setColor(schedulebar.scheduleBarColor)
        binding.childTv.setTextColor(schedulebar.scheduleTextColor)

        binding.childTv.text = schedulebar.title

        val dHeight = resources.displayMetrics.heightPixels
        var h = dHeight/42

        /// h/5.toFloat() ?????
        binding.childTv.textSize = h/5.toFloat()
        binding.childTv.setTypeface(null, Typeface.BOLD_ITALIC)
        binding.childTv.gravity= Gravity.CENTER
        binding.childTv.width = list[2]
        binding.childTv.height = h


        addView(binding.root)

    }

}