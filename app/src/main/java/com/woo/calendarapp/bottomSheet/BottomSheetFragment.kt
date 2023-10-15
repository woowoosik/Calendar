package com.woo.calendarapp.bottomSheet

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.Point
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.FrameLayout
import android.widget.Toast
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStoreOwner
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetBehavior.STATE_EXPANDED
import com.google.android.material.bottomsheet.BottomSheetBehavior.STATE_HIDDEN
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.woo.calendarapp.EventObserver
import com.woo.calendarapp.R
import com.woo.calendarapp.adapter.BottomRecyclerAdapter
import com.woo.calendarapp.databinding.BottomsheetMainBinding
import com.woo.calendarapp.fragment.AddFragment
import com.woo.calendarapp.fragment.UpdateFragment
import com.woo.calendarapp.generated.callback.OnClickListener
import com.woo.calendarapp.itemTouch.ItemTouchHelperCallback
import com.woo.calendarapp.schedule.Schedule
import com.woo.calendarapp.viewmodel.MainViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class BottomSheetFragment : BottomSheetDialogFragment(){
    private lateinit var mainViewModel : MainViewModel

    private lateinit var sheetBinding : BottomsheetMainBinding
    private lateinit var bottomSheetBehavior : BottomSheetBehavior<*>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mainViewModel = ViewModelProvider(activity as ViewModelStoreOwner)[MainViewModel::class.java]

    }
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?):
            View? {

        sheetBinding = DataBindingUtil.inflate(inflater, R.layout.bottomsheet_main, container, false)

        sheetBinding.recyclerPager.layoutManager = LinearLayoutManager(context)

        // val adapter = BottomRecyclerAdapter(sheetViewModel.schedule)
        println("BottomFragment ${mainViewModel.dayScheduleList}")
        val adapter = BottomRecyclerAdapter(mainViewModel.dayScheduleList)
        sheetBinding.recyclerPager.adapter = adapter

        mainViewModel.clickDay.observe(requireActivity()) {
            sheetBinding.clickDate.text = it.toString("yyyy-MM-dd")
        }

        adapter.setItemClickListener(object : BottomRecyclerAdapter.OnItemClickListener{
            override fun onItemClick(schedule: Schedule) {
                println("$${schedule.title}  ${schedule.content}  ${schedule.scheduleBarColor}")

                println("x  ${schedule.x}  y  ${schedule.y}  ")
                // bottomSheetFragment.show(childFragmentManager, "bottomsheet")
                val bottomSheetFragmentChild = BottomSheetFragmentChild()
                val bundle = Bundle()
                bundle.putInt("id", schedule.id)
                bundle.putString("start", schedule.startDate.toString("yyyy-MM-dd"))
                bundle.putString("end", schedule.endDate.toString("yyyy-MM-dd"))
                bundle.putString("title", schedule.title)
                bundle.putString("content", schedule.content)
                bundle.putInt("barColor", schedule.scheduleBarColor)
                bundle.putInt("textColor", schedule.scheduleTextColor)
                bundle.putDouble("x", schedule.x)
                bundle.putDouble("y", schedule.y)
                bottomSheetFragmentChild.arguments = bundle
                activity?.let { it1 -> bottomSheetFragmentChild.show(it1.supportFragmentManager, bottomSheetFragmentChild.tag) }
            }
        })

        adapter.setDeleteItemClickListener(object :BottomRecyclerAdapter.OnItemClickListener{
            override fun onItemClick(schedule: Schedule) {
                println("삭제 버튼 ")
                mainViewModel.deleteSchedule(schedule)

            }
        })
        mainViewModel.deleteComplate.observe(viewLifecycleOwner, EventObserver {

          //  adapter.setData()
        })


        sheetBinding.bottomMainBack.setOnClickListener {
            println("bottomsheet bakc cilck")
            bottomSheetBehavior!!.state = STATE_HIDDEN
        }

        deleteListener()


        mainViewModel.updateOpen.observe(viewLifecycleOwner, EventObserver {
            println("click open update")
            bottomSheetBehavior!!.state = STATE_HIDDEN
        })

        sheetBinding.addSchedule.setOnClickListener {
            // 추가
            bottomSheetBehavior!!.state = BottomSheetBehavior.STATE_HIDDEN
            val bundle = Bundle()
            bundle.putString("start", sheetBinding.clickDate.text.toString())
            bundle.putString("end", sheetBinding.clickDate.text.toString())
            val addFragment = AddFragment()
            addFragment.arguments = bundle
            requireActivity().supportFragmentManager.beginTransaction()
                .replace(R.id.main, addFragment)
                .addToBackStack(null)
                .commit()
        }



        return sheetBinding.root
    }


    override fun onStart() {
        super.onStart()

        // full screen

        if (dialog != null) {
            val bottomSheet: View = dialog!!.findViewById(com.google.android.material.R.id.design_bottom_sheet)

            val dHeight = resources.displayMetrics.heightPixels
            bottomSheet.layoutParams.height = dHeight

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


    fun deleteListener() {
        // 리스너를 구현한 Adapter 클래스를 Callback 클래스의 생성자로 지정
        val itemTouchHelperCallback = ItemTouchHelperCallback()

        // ItemTouchHelper의 생성자로 ItemTouchHelper.Callback 객체 셋팅
        val helper = ItemTouchHelper(itemTouchHelperCallback)
        // RecyclerView에 ItemTouchHelper 연결
        helper.attachToRecyclerView(sheetBinding.recyclerPager)
        //
        sheetBinding.recyclerPager.apply {
            setOnTouchListener { _, _ ->
                itemTouchHelperCallback.removePreviousClamp(this)
                false
            }
        }

    }






}