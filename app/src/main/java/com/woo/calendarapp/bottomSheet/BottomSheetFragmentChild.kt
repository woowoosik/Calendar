package com.woo.calendarapp.bottomSheet


import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStoreOwner
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.woo.calendarapp.EventObserver
import com.woo.calendarapp.R
import com.woo.calendarapp.databinding.BottomsheetChildBinding
import com.woo.calendarapp.fragment.AddFragment
import com.woo.calendarapp.fragment.UpdateFragment
import com.woo.calendarapp.generated.callback.OnClickListener
import com.woo.calendarapp.schedule.Schedule
import com.woo.calendarapp.viewmodel.MainViewModel


class BottomSheetFragmentChild : BottomSheetDialogFragment() {
    private lateinit var mainViewModel : MainViewModel
    private lateinit var binding : BottomsheetChildBinding
    private lateinit var bottomSheetBehavior : BottomSheetBehavior<*>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mainViewModel = ViewModelProvider(activity as ViewModelStoreOwner)[MainViewModel::class.java]

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = DataBindingUtil.inflate(inflater, R.layout.bottomsheet_child, container, false)

        binding.bottomChildTitle.text=arguments?.getString("title")
        binding.bottomChildContent.text=arguments?.getString("content")
        binding.bottomChildStartDate.text = arguments?.getString("start")
        binding.bottomChildEndDate.text = arguments?.getString("end")

        var bgShape : GradientDrawable = binding.bottomChildTitle.background as GradientDrawable
        arguments?.getInt("barColor")?.let { bgShape.setColor(it) }

        binding.bottomChildBack.setOnClickListener {
            bottomSheetBehavior!!.state = BottomSheetBehavior.STATE_HIDDEN
        }


        binding.bottomChildTitle.apply { // 텍스트가 길때 자동 스크롤
            setSingleLine()
            marqueeRepeatLimit = -1
            ellipsize = TextUtils.TruncateAt.MARQUEE
            isSelected = true
        }




        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.bottomChildUpdate.setOnClickListener {
            bottomSheetBehavior!!.state = BottomSheetBehavior.STATE_HIDDEN
            mainViewModel.updateOpen()
            println(" 수정하기 ")
            println(" - ${arguments?.getInt("id")}")
            println(" - ${arguments?.getInt("barColor")}")
            println(" - ${arguments?.getInt("textColor")}")
            val bundle = Bundle()
            bundle.putInt("id", arguments?.getInt("id")!!.toInt())
            bundle.putString("start", arguments?.getString("start"))
            bundle.putString("end", arguments?.getString("end"))
            bundle.putString("title", arguments?.getString("title"))
            bundle.putString("content", arguments?.getString("content"))
            bundle.putInt("barColor", arguments?.getInt("barColor")!!.toInt())
            bundle.putInt("textColor", arguments?.getInt("textColor")!!.toInt())
            val updateFragment = UpdateFragment()
            updateFragment.arguments = bundle
            requireActivity().supportFragmentManager.beginTransaction()
                .replace(R.id.main, updateFragment)
                .addToBackStack(null)
                .commit()
        }




    }



override fun onStart() {
    super.onStart()

    // full screen
    if (dialog != null) {
        val bottomSheet: View = dialog!!.findViewById(com.google.android.material.R.id.design_bottom_sheet)
        val dHeight = resources.displayMetrics.heightPixels
        bottomSheet.layoutParams.height = dHeight *9/10
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



}