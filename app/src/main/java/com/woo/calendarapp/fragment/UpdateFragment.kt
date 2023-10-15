package com.woo.calendarapp.fragment

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStoreOwner
import com.woo.calendarapp.EventObserver
import com.woo.calendarapp.R
import com.woo.calendarapp.databinding.FragmentUpdateBinding
import com.woo.calendarapp.schedule.Schedule
import com.woo.calendarapp.viewmodel.MainViewModel
import org.joda.time.DateTime
import yuku.ambilwarna.AmbilWarnaDialog
import java.util.*

class UpdateFragment : Fragment() {

    private lateinit var binding : FragmentUpdateBinding
    private lateinit var viewModel: MainViewModel

    private var baColor = Color.parseColor("#FF796F")
    private var txtColor = Color.parseColor("#ffffff")

    private lateinit var updateSchedulebar: GradientDrawable
    private lateinit var barColor : GradientDrawable
    private lateinit var textColor : GradientDrawable


    private val cal = Calendar.getInstance()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_update, container, false)
        viewModel = ViewModelProvider(activity as ViewModelStoreOwner)[MainViewModel::class.java]
        binding.mainViewModel = viewModel
        binding.fragment = this

        updateSchedulebar = binding.updateSchedulebar.background as GradientDrawable
        barColor = binding.barColor.background as GradientDrawable
        textColor = binding.textColor.background as GradientDrawable

        scheduleColor(
            arguments?.getInt("textColor")!!.toInt(),
            arguments?.getInt("barColor")!!.toInt()
        )

        binding.etTitle.setText("${arguments?.getString("title")}")
        binding.etContent.setText("${arguments?.getString("content")}")
        binding.startDate.text = arguments?.getString("start")
        binding.endDate.text = arguments?.getString("end")


        // 아래 fragment 터치 막기
        binding.layout.isClickable = true



        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val fragmentManager: FragmentManager = requireActivity().supportFragmentManager


        println("bcolor 1 ${baColor}")
        println("tcolor 1 ${txtColor}")

        binding.startDate.setOnClickListener {
            datePicker(0)
            println("bcolor 2 ${baColor}")
            println("tcolor 2 ${txtColor}")
        }

        binding.endDate.setOnClickListener {
            datePicker(1)
            println("bcolor 3 ${baColor}")
            println("tcolor 3 ${txtColor}")
        }
        binding.barColor.setOnClickListener {
            openColorPicker(0,baColor)
            println("bcolor 4 ${baColor}")
            println("tcolor 4 ${txtColor}")
        }
        binding.textColor.setOnClickListener {
            openColorPicker(1,txtColor)
            println("bcolor 4 ${baColor}")
            println("tcolor 4 ${txtColor}")
        }



        viewModel.updateComplate.observe(viewLifecycleOwner, EventObserver {
            Log.e("add observer","")
            fragmentManager.beginTransaction()
                .remove(this)
                .commit()
            fragmentManager.popBackStack()

        })


        binding.updateCancel.setOnClickListener {
            fragmentManager.beginTransaction()
                .remove(this )
                .commit()
            fragmentManager.popBackStack()
        }






        // 키보드 없애기
        binding.sv1.setOnTouchListener(View.OnTouchListener { _, _ ->
            hideKeyboard()
            false
        })

        // 제목 text schedulebar에 넘기기
        binding.etTitle.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int){
                if(s.isEmpty()){
                    binding.updateSchedulebar.text = "Schedule Bar"
                }else{
                    binding.updateSchedulebar.text = s
                }
            }

            override fun afterTextChanged(p0: Editable?){

            }


        })



    }

    private fun hideKeyboard() {
        if (activity != null && requireActivity().currentFocus != null) {
            // 프래그먼트기 때문에 getActivity() 사용
            val inputManager =
                requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            inputManager.hideSoftInputFromWindow(
                requireActivity().currentFocus!!.windowToken,
                InputMethodManager.HIDE_NOT_ALWAYS
            )
        }
    }

    fun datePicker(b:Int){   //캘린더뷰 만들기
        val startDate = DateTime("${binding.startDate.text}")
        val endDate = DateTime("${binding.endDate.text}")

        val dateSetListener = DatePickerDialog.OnDateSetListener { view, year, month, dayOfMonth ->
            val selectDate = DateTime("$year-${String.format("%02d", month+1)}-${String.format("%02d", dayOfMonth)}")

            if((selectDate.compareTo(endDate) == 1 && b==0) || (selectDate.compareTo(startDate) == -1 && b==1)){
                binding.startDate.text = "$year-${String.format("%02d", month+1)}-${String.format("%02d", dayOfMonth)}"
                binding.endDate.text = "$year-${String.format("%02d", month+1)}-${String.format("%02d", dayOfMonth)}"
            }else{
                when(b){
                    0 ->  binding.startDate.text = "$year-${String.format("%02d", month+1)}-${String.format("%02d", dayOfMonth)}"
                    else ->  binding.endDate.text = "$year-${String.format("%02d", month+1)}-${String.format("%02d", dayOfMonth)}"
                }
            }
        }

        when(b){
            0 ->  DatePickerDialog(requireActivity(),
                dateSetListener,
                startDate.toString("yyyy").toInt() ,
                startDate.toString("M").toInt()-1,
                startDate.toString("dd").toInt()
            ).show()
            else ->  DatePickerDialog(requireActivity(),
                dateSetListener,
                endDate.toString("yyyy").toInt() ,
                endDate.toString("M").toInt()-1,
                endDate.toString("dd").toInt()
            ).show()
        }

    }


    @SuppressLint("ResourceAsColor")
    fun openColorPicker(button:Int, color:Int) {
        val colorPicker = AmbilWarnaDialog(context , color, object : AmbilWarnaDialog.OnAmbilWarnaListener {
            override fun onCancel(dialog: AmbilWarnaDialog) {}
            override fun onOk(dialog: AmbilWarnaDialog, color: Int) {
                when(button){
                    0 ->  baColor = color
                    else -> txtColor = color
                }
                scheduleColor(txtColor, baColor)
            }
        })
        colorPicker.show()
    }



    @SuppressLint("ResourceAsColor")
    fun scheduleColor(tColor : Int, bColor:Int){
        binding.updateSchedulebar.setTextColor(tColor)
        updateSchedulebar.setColor(bColor)
        barColor.setColor(bColor)
        textColor.setColor(tColor)
        baColor = bColor
        txtColor = tColor
    }

    fun updateSchedule(){

        Log.e("updateSchedule", "${baColor}  ${txtColor}")
        viewModel.updateSchedule(
            Schedule(
                DateTime("${binding.startDate.text}"),
                DateTime("${binding.endDate.text}"),
                binding.etTitle.text.toString(),
                binding.etContent.text.toString(),
                baColor,
                txtColor,
                // 나중에 수정해야할거
                viewModel.getLocation()!!.first,
                viewModel.getLocation()!!.second

            ),
            arguments?.getInt("id")!!.toInt()
        )



    }
}