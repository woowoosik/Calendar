package com.woo.calendarapp.bottomSheet

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStoreOwner
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.woo.calendarapp.KakaoRetrofit
import com.woo.calendarapp.LoadingDialog
import com.woo.calendarapp.R
import com.woo.calendarapp.adapter.BottomAddMapAdapter
import com.woo.calendarapp.databinding.BottomsheetAddMapBinding
import com.woo.calendarapp.fragment.AddMapFragment
import com.woo.calendarapp.viewmodel.MainViewModel

class BottomSheetAddMap : BottomSheetDialogFragment() {

    private lateinit var binding : BottomsheetAddMapBinding
    private lateinit var viewModel : MainViewModel

    private lateinit var bottomSheetBehavior : BottomSheetBehavior<*>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel = ViewModelProvider(activity as ViewModelStoreOwner)[MainViewModel::class.java]
    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        Log.w("BottomSheetAddMap", " onCreateVIew")
        binding = DataBindingUtil.inflate(inflater, R.layout.bottomsheet_add_map, container, false)


        if(viewModel.getSearchKeyList().documents.isEmpty()){
            binding.txt.text = "검색결과가 없습니다."
            binding.txt.visibility = VISIBLE
            binding.recyclerAddMap.visibility = GONE
        }else{
            binding.recyclerAddMap.visibility = VISIBLE
            binding.txt.visibility = GONE
            println("adapter ${viewModel.getSearchKeyList().documents[0].place_name}")
            val adapter = BottomAddMapAdapter(viewModel.getSearchKeyList())
            binding.recyclerAddMap.layoutManager = LinearLayoutManager(context)
            binding.recyclerAddMap.adapter = adapter

            adapter.setOnItemClickListener(object:BottomAddMapAdapter.OnItemClickListener{
                override fun onItemClick(item: KakaoRetrofit.Place) {
                    bottomSheetBehavior!!.state = BottomSheetBehavior.STATE_HIDDEN
                    println("x : ${item.x.toDouble()}  y : ${item.y.toDouble()}")
                    viewModel.setLocation(item.x.toDouble(), item.y.toDouble())
                }

            })


        }






        return binding.root
    }


    override fun onStart() {
        super.onStart()

        // full screen

        if (dialog != null) {
            val bottomSheet: View = dialog!!.findViewById(com.google.android.material.R.id.design_bottom_sheet)

            val dHeight = resources.displayMetrics.heightPixels
            bottomSheet.layoutParams.height = dHeight * 9/10

        }

        val view = view

        view!!.post{
            val parent = view!!.parent as View
            val params = parent.layoutParams as CoordinatorLayout.LayoutParams
            val behavior = params.behavior
            bottomSheetBehavior = behavior as BottomSheetBehavior<*>
            bottomSheetBehavior!!.peekHeight = view!!.measuredHeight
            parent.setBackgroundColor(Color.TRANSPARENT)

        }

    }

    override fun onStop() {
        super.onStop()
        Log.w("addMap", "onStop")
       // bottomSheetBehavior!!.state = BottomSheetBehavior.STATE_HIDDEN
    }
}