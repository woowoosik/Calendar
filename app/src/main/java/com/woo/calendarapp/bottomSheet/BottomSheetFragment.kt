package com.woo.calendarapp.bottomSheet

import android.annotation.SuppressLint
import android.graphics.Color
import android.os.Bundle
import android.view.*
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStoreOwner
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetBehavior.STATE_HIDDEN
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.woo.calendarapp.EventObserver
import com.woo.calendarapp.R
import com.woo.calendarapp.adapter.BottomRecyclerAdapter
import com.woo.calendarapp.databinding.BottomsheetMainBinding
import com.woo.calendarapp.fragment.AddFragment
import com.woo.calendarapp.itemTouch.ItemTouchHelperCallback
import com.woo.calendarapp.schedule.Schedule
import com.woo.calendarapp.viewmodel.MainViewModel
import dagger.hilt.android.AndroidEntryPoint

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
            View {

        sheetBinding = DataBindingUtil.inflate(inflater, R.layout.bottomsheet_main, container, false)

        sheetBinding.recyclerPager.layoutManager = LinearLayoutManager(context)

        val adapter = BottomRecyclerAdapter(mainViewModel.dayScheduleList)
        sheetBinding.recyclerPager.adapter = adapter

        mainViewModel.clickDay.observe(requireActivity()) {
            sheetBinding.clickDate.text = it.toString("yyyy-MM-dd")
        }

        adapter.setItemClickListener(object : BottomRecyclerAdapter.OnItemClickListener{
            override fun onItemClick(schedule: Schedule) {

                val bottomSheetFragmentChild = BottomSheetFragmentChild()
                val bundle = Bundle()
                bundle.putInt("id", schedule.id)
                bundle.putString("start", schedule.startDate.toString("yyyy-MM-dd"))
                bundle.putString("end", schedule.endDate.toString("yyyy-MM-dd"))
                bundle.putString("title", schedule.title)
                bundle.putString("content", schedule.content)
                bundle.putInt("barColor", schedule.scheduleBarColor)
                bundle.putInt("textColor", schedule.scheduleTextColor)
                bundle.putBoolean("isChecked", schedule.isChecked)
                if(schedule.isChecked){
                    bundle.putDouble("x", schedule.x)
                    bundle.putDouble("y", schedule.y)
                }
                bottomSheetFragmentChild.arguments = bundle
                activity?.let { it1 -> bottomSheetFragmentChild.show(it1.supportFragmentManager, bottomSheetFragmentChild.tag) }
            }
        })

        adapter.setDeleteItemClickListener(object :BottomRecyclerAdapter.OnItemClickListener{
            override fun onItemClick(schedule: Schedule) {
                mainViewModel.deleteSchedule(schedule)

            }
        })
        mainViewModel.deleteComplate.observe(viewLifecycleOwner, EventObserver {

          //  adapter.setData()
        })


        sheetBinding.bottomMainBack.setOnClickListener {
            bottomSheetBehavior.state = STATE_HIDDEN
        }

        deleteListener()


        mainViewModel.updateOpen.observe(viewLifecycleOwner, EventObserver {

            bottomSheetBehavior.state = STATE_HIDDEN
        })

        sheetBinding.addSchedule.setOnClickListener {
            // 추가
            bottomSheetBehavior.state = STATE_HIDDEN
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


        if (dialog != null) {
            val bottomSheet: View = dialog!!.findViewById(com.google.android.material.R.id.design_bottom_sheet)

            val dHeight = resources.displayMetrics.heightPixels
            bottomSheet.layoutParams.height = dHeight

        }

        val view = view

        view!!.post{
            val parent = view.parent as View
            val params = parent.layoutParams as CoordinatorLayout.LayoutParams
            val behavior = params.behavior
            bottomSheetBehavior = behavior as BottomSheetBehavior<*>
            bottomSheetBehavior.peekHeight = view.measuredHeight
            parent.setBackgroundColor(Color.TRANSPARENT)

        }

    }


    @SuppressLint("ClickableViewAccessibility")
    fun deleteListener() {
        val itemTouchHelperCallback = ItemTouchHelperCallback()
        val helper = ItemTouchHelper(itemTouchHelperCallback)
        helper.attachToRecyclerView(sheetBinding.recyclerPager)
        sheetBinding.recyclerPager.apply {
            setOnTouchListener { _, _ ->
                itemTouchHelperCallback.removePreviousClamp(this)
                false
            }
        }

    }





}