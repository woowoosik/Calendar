package com.woo.calendarapp.adapter

import android.graphics.drawable.GradientDrawable
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.woo.calendarapp.R
import com.woo.calendarapp.databinding.BottomsheetRcItemBinding
import com.woo.calendarapp.schedule.Schedule

class BottomRecyclerAdapter(private var list : MutableList<Schedule>)
    : RecyclerView.Adapter<BottomRecyclerAdapter.PagerViewHolder>()  {


    private lateinit var itemClickListener : OnItemClickListener
    private lateinit var deleteItemClickListener : OnItemClickListener

    interface OnItemClickListener {
        fun onItemClick(schedule: Schedule)
    }

    fun setItemClickListener(onItemClickListener: OnItemClickListener) {
        this.itemClickListener = onItemClickListener
    }
    fun setDeleteItemClickListener(onItemClickListener: OnItemClickListener) {
        this.deleteItemClickListener = onItemClickListener
    }


    private lateinit var binding : BottomsheetRcItemBinding
    inner class PagerViewHolder(val binding:BottomsheetRcItemBinding)
        :RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PagerViewHolder {
        binding = DataBindingUtil.inflate(LayoutInflater.from(parent.context),
            R.layout.bottomsheet_rc_item,parent,false)
        return PagerViewHolder(binding)
    }

    override fun onBindViewHolder(holder: PagerViewHolder, position: Int) {

        Log.e(" onBinsViewHolder", " ${list[position].title}  ${list[position].content}")
        holder.binding.itemTitle.text = list[position].title
        holder.binding.itemContent.text = list[position].content
        var bgShape : GradientDrawable = binding.itemBar.background as GradientDrawable
        bgShape.setColor(list[position].scheduleBarColor)
        holder.binding.itemParent.setOnClickListener {
          //itemClickListener.onItemClick()
            itemClickListener.onItemClick(list[position])

        }
        holder.binding.itemDeleteBtn.setOnClickListener {
            deleteItemClickListener.onItemClick(list[position])
            list.removeAt(position)
            notifyItemRemoved(position)

        }


    }

    override fun getItemCount(): Int  = list.size




}