package com.woo.calendarapp.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.woo.calendarapp.KakaoRetrofit
import com.woo.calendarapp.R
import com.woo.calendarapp.databinding.BottomsheetAddMapItemBinding
import com.woo.calendarapp.schedule.Schedule

class BottomAddMapAdapter(private var list: KakaoRetrofit.ResultSearchKeyword) :
    RecyclerView.Adapter<BottomAddMapAdapter.PagerViewHolder>(){

    private lateinit var binding : BottomsheetAddMapItemBinding

    private lateinit var listener : OnItemClickListener

    interface OnItemClickListener {
        fun onItemClick(item:KakaoRetrofit.Place)
    }

    fun setOnItemClickListener(listener: OnItemClickListener){
        this.listener = listener
    }

    inner class PagerViewHolder(val binding: BottomsheetAddMapItemBinding)
        :RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): PagerViewHolder {
        binding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.bottomsheet_add_map_item,parent,false)
        return PagerViewHolder(binding)
    }

    override fun getItemCount(): Int  = list.documents.size

    override fun onBindViewHolder(holder: PagerViewHolder, position: Int) {
        println("onBindViewHolder size ${list.documents.size}  ${list.documents[position].place_name}")
        holder.binding.address.text = "지번 : ${list.documents[position].address_name}"
        holder.binding.keyword.text = list.documents[position].place_name
        holder.binding.roadAddress.text ="도로명 : ${ list.documents[position].road_address_name }"
        holder.binding.place.text = "카테고리 : ${ list.documents[position].category_name }"

        holder.binding.addMapItem.setOnClickListener {
            listener.onItemClick(list.documents[position])
        }
    }
}