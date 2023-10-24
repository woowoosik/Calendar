package com.woo.calendarapp.fragment

import android.Manifest
import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.content.Context
import android.content.pm.PackageManager
import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.location.Location
import android.os.Build
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.activity.result.contract.ActivityResultContracts
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.core.content.ContextCompat
import androidx.core.view.isEmpty
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStoreOwner
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.snackbar.Snackbar
import com.woo.calendarapp.EventObserver
import com.woo.calendarapp.LoadingDialog
import com.woo.calendarapp.PermissionCheck
import com.woo.calendarapp.R
import com.woo.calendarapp.databinding.FragmentUpdateBinding
import com.woo.calendarapp.kakaoApi.KakaoMapUtils.Companion.moveMap
import com.woo.calendarapp.schedule.Schedule
import com.woo.calendarapp.viewmodel.MainViewModel
import dagger.hilt.android.AndroidEntryPoint
import net.daum.mf.map.api.MapPOIItem
import net.daum.mf.map.api.MapPoint
import net.daum.mf.map.api.MapView
import org.joda.time.DateTime
import yuku.ambilwarna.AmbilWarnaDialog
import java.util.*
import javax.inject.Inject


@AndroidEntryPoint
class UpdateFragment : Fragment() {

    private lateinit var binding : FragmentUpdateBinding
    private lateinit var viewModel: MainViewModel

    private var baColor = Color.parseColor("#FF796F")
    private var txtColor = Color.parseColor("#ffffff")

    private lateinit var updateSchedulebar: GradientDrawable
    private lateinit var barColor : GradientDrawable
    private lateinit var textColor : GradientDrawable

    // 카카오맵
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private lateinit var mapView : MapView

    private lateinit var mapLocation : Pair<Double,Double>


    private lateinit var startDate :String
    private lateinit var endDate :String

    @Inject
    lateinit var loadingDialog: LoadingDialog
    @Inject
    lateinit var permissionCheck : PermissionCheck

    private val cal = Calendar.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel = ViewModelProvider(activity as ViewModelStoreOwner)[MainViewModel::class.java]


        if(arguments?.getBoolean("isChecked")!!){
            mapLocation = Pair(arguments?.getDouble("x"), arguments?.getDouble("y")) as Pair<Double, Double>

        }

    }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_update, container, false)

        binding.mainViewModel = viewModel
        binding.fragment = this

        updateSchedulebar = binding.updateSchedulebar.background as GradientDrawable
        barColor = binding.barColor.background as GradientDrawable
        textColor = binding.textColor.background as GradientDrawable

        scheduleColor(
            arguments?.getInt("textColor")!!.toInt(),
            arguments?.getInt("barColor")!!.toInt()
        )

        binding.updateSchedulebar.text = arguments?.getString("title")
        //kakao map
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(requireContext())

        binding.etTitle.setText("${arguments?.getString("title")}")
        binding.etContent.setText("${arguments?.getString("content")}")
        startDate = arguments?.getString("start")!!
        endDate = arguments?.getString("end")!!

        getMap()


        // 아래 fragment 터치 막기
        binding.layout.isClickable = true


        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val fragmentManager: FragmentManager = requireActivity().supportFragmentManager


        binding.startDate.setOnClickListener {
            datePicker(0)
        }

        binding.endDate.setOnClickListener {
            datePicker(1)
        }
        binding.barColor.setOnClickListener {
            openColorPicker(0,baColor)
        }
        binding.textColor.setOnClickListener {
            openColorPicker(1,txtColor)
        }



        viewModel.updateComplate.observe(viewLifecycleOwner, EventObserver {
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

        binding.updatePlaceSwitch.isChecked = arguments?.getBoolean("isChecked")!!
        if(arguments?.getBoolean("isChecked")!!){
            binding.updateKeywordMap.visibility = View.VISIBLE
        }else{
            binding.updateKeywordMap.visibility = View.GONE
        }

        binding.updatePlaceSwitch.setOnCheckedChangeListener { _, b ->
            if(permissionCheck.isAllPermissionsGranted() && b){
                binding.updatePlaceSwitch.isChecked = true
                binding.updateKeywordMap.visibility = View.VISIBLE

                binding.mapView.removeView(mapView)
                getMap()
            }else{
                binding.updatePlaceSwitch.isChecked = false
                binding.updateKeywordMap.visibility = View.GONE
            }
        }


        binding.updateKeywordSearch.setOnClickListener {

            loadingDialog.show()
            binding.mapView.removeView(mapView)

            val addMapFragment = AddMapFragment()
            requireActivity().supportFragmentManager.beginTransaction()
                .replace(R.id.main, addMapFragment)
                .addToBackStack(null)
                .commit()

            addMapFragment.lifecycle.addObserver(LifecycleEventObserver { source, event ->
                if( event == Lifecycle.Event.ON_RESUME){
                    loadingDialog.dismiss()
                }
            })

        }


        viewModel.updateKeywordMap.observe(viewLifecycleOwner, EventObserver {
            mapLocation = Pair(viewModel.getLocation()!!.first, viewModel.getLocation()!!.second)
            moveMap(viewModel.getLocation()!!.first, viewModel.getLocation()!!.second, mapView)

        })

        binding.startDate.text = startDate
        binding.endDate.text = endDate


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
                    0 -> {
                        binding.startDate.text = "$year-${String.format("%02d", month+1)}-${String.format("%02d", dayOfMonth)}"
                        this.startDate = binding.startDate.text.toString()
                    }
                    else -> {
                        binding.endDate.text = "$year-${String.format("%02d", month+1)}-${String.format("%02d", dayOfMonth)}"
                        this.endDate = binding.endDate.text.toString()
                    }
                }
            }
        }

        when(b){
            0 -> {
                DatePickerDialog(
                    requireActivity(),
                    R.style.DialogTheme,
                    dateSetListener,
                    startDate.toString("yyyy").toInt(),
                    startDate.toString("M").toInt() - 1,
                    startDate.toString("dd").toInt()
                ).show()


            }
            else -> {
                DatePickerDialog(
                    requireActivity(),
                    R.style.DialogTheme,
                    dateSetListener,
                    endDate.toString("yyyy").toInt(),
                    endDate.toString("M").toInt() - 1,
                    endDate.toString("dd").toInt()
                ).show()
            }
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
        viewModel.updateSchedule(
            Schedule(
                DateTime("${binding.startDate.text}"),
                DateTime("${binding.endDate.text}"),
                binding.etTitle.text.toString(),
                binding.etContent.text.toString(),
                baColor,
                txtColor,
                // 나중에 수정해야할거
                mapLocation.first,
                mapLocation.second,
                binding.updatePlaceSwitch.isChecked

            ),
            arguments?.getInt("id")!!.toInt()
        )
    }


    @SuppressLint("MissingPermission")
    fun getMap(){
        mapView = MapView(requireActivity())
        binding.mapView.addView(mapView)

        if (permissionCheck.isAllPermissionsGranted()) {
            if( !this::mapLocation.isInitialized ){
                fusedLocationClient.lastLocation
                    .addOnSuccessListener { location : Location? ->
                        mapLocation = Pair(location!!.longitude, location!!.latitude)
                        viewModel.setLocation(location!!.longitude, location!!.latitude)
                        moveMap(location!!.longitude, location!!.latitude, mapView)

                    }
            }else{
                moveMap(mapLocation.first, mapLocation.second, mapView)
            }

        } else {
            requestPermissionLauncher.launch(permissionCheck.permission)
        }
    }

    private val requestPermissionLauncher =
        registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) { permissions ->
            permissions.entries.forEach { permission ->
                when {
                    permission.value -> {
                        Snackbar.make(binding.root, "설정에서 권한을 허용해주세요. ", Snackbar.LENGTH_SHORT).show()
                    }
                    shouldShowRequestPermissionRationale(permission.key) -> {
                        Snackbar.make(binding.root,
                            "권한설정 확인", Snackbar.LENGTH_SHORT).show()
                    }
                    else -> Snackbar.make(binding.root, "권한이 거절되어있습니다. 설정에서 허용해주세요.", Snackbar.LENGTH_SHORT).show()
                }
            }
        }


    override fun onStart() {
        super.onStart()

        if(binding.mapView.isEmpty()){
            getMap()
        }

    }

    override fun onStop() {
        super.onStop()
        // binding.mapView.removeView(mapView)
        binding.mapView.removeAllViews()
    }




}