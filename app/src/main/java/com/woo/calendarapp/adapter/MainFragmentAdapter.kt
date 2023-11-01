package com.woo.calendarapp.adapter

import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.woo.calendarapp.fragment.MainFragment
import org.joda.time.DateTime

class MainFragmentAdapter(fm:FragmentActivity) : FragmentStateAdapter(fm)  {

    private var start: Long = DateTime().withDayOfMonth(1).withTimeAtStartOfDay().millis

    override fun getItemCount(): Int = Int.MAX_VALUE

    override fun createFragment(position: Int): MainFragment {
        val millis = getItemId(position)
        return MainFragment.newInstance(millis)
    }

    override fun getItemId(position: Int): Long {
        return DateTime(start).plusMonths(position - START_POSITION).millis
    }

    override fun containsItem(itemId: Long): Boolean {
        val date = DateTime(itemId)

        return date.dayOfMonth == 1 && date.millisOfDay == 0
    }

    companion object {
        const val START_POSITION = Int.MAX_VALUE / 2

    }




}