package com.woo.calendarapp.fragment

import android.Manifest
import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.content.ContentValues.TAG
import android.content.Context
import android.content.pm.PackageManager
import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.location.Location
import android.location.LocationRequest
import android.os.Build
import android.os.Bundle
import android.os.Looper
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.View.*
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat.checkSelfPermission
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStoreOwner
import com.google.android.gms.location.*
import com.woo.calendarapp.EventObserver
import com.woo.calendarapp.R
import com.woo.calendarapp.databinding.FragmentAddBinding
import com.woo.calendarapp.schedule.Schedule
import com.woo.calendarapp.utils.ScheduleUtils
import com.woo.calendarapp.viewmodel.MainViewModel
import dagger.hilt.android.AndroidEntryPoint
import net.daum.mf.map.api.MapPOIItem
import net.daum.mf.map.api.MapPoint
import net.daum.mf.map.api.MapView
import org.joda.time.DateTime
import yuku.ambilwarna.AmbilWarnaDialog
import java.util.*


@AndroidEntryPoint
class AddFragment : Fragment() {
    private lateinit var binding : FragmentAddBinding
    private lateinit var viewModel : MainViewModel

    private var bColor = Color.parseColor("#FF796F")
    private var tColor = Color.parseColor("#ffffff")

    private lateinit var addSchedulebar: GradientDrawable
    private lateinit var barColor : GradientDrawable
    private lateinit var textColor : GradientDrawable

    // 카카오맵
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private lateinit var mapView : MapView

    private val cal = Calendar.getInstance()

    @RequiresApi(Build.VERSION_CODES.N)
    @SuppressLint("ResourceAsColor")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val fragmentManager: FragmentManager = requireActivity().supportFragmentManager

        viewModel =  ViewModelProvider(activity as ViewModelStoreOwner)[MainViewModel::class.java]

        binding =  DataBindingUtil.inflate(inflater, R.layout.fragment_add, container, false)
        binding.mainViewModel = viewModel
        binding.fragment = this@AddFragment

        addSchedulebar = binding.addSchedulebar.background as GradientDrawable
        barColor = binding.barColor.background as GradientDrawable
        textColor = binding.textColor.background as GradientDrawable
        scheduleColor(tColor, bColor)


        if(arguments?.getString("start") == null){
            binding.startDate.text = DateTime.now().toString("yyyy-MM-dd")
        }else{
            binding.startDate.text = arguments?.getString("start")
        }
        if(arguments?.getString("end") == null){
            binding.endDate.text = DateTime.now().toString("yyyy-MM-dd")
        }else{
            binding.endDate.text = arguments?.getString("end")
        }


        binding.startDate.setOnClickListener {
            datePicker(0)
        }

        binding.endDate.setOnClickListener {
            datePicker(1)
        }


        binding.barColor.setOnClickListener {
            openColorPicker(0,bColor)
        }
        binding.textColor.setOnClickListener {
            openColorPicker(1,tColor)
        }


        viewModel.addComplate.observe(viewLifecycleOwner, EventObserver {
            Log.e("add observer","")
            fragmentManager.beginTransaction()
                .remove(this@AddFragment)
                .commit()
            fragmentManager.popBackStack()

        })

        binding.addCancel.setOnClickListener {
            fragmentManager.beginTransaction()
                .remove(this@AddFragment)
                .commit()
            fragmentManager.popBackStack()
        }

        // 키보드 없애기
        binding.sv1.setOnTouchListener(OnTouchListener { _,_ ->
            hideKeyboard()
            false
        })

        // 제목 text schedulebar에 넘기기
        binding.etTitle.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int){
                if(s.isEmpty()){
                    binding.addSchedulebar.text = "Schedule Bar"
                }else{
                    binding.addSchedulebar.text = s
                }
            }

            override fun afterTextChanged(p0: Editable?){

            }


        })

        // 아래 fragment 터치 막기
        binding.layout.isClickable = true


        //keyword map
        binding.keywordDelete.setOnClickListener {
            binding.addKeywordMap.visibility = GONE
            binding.deleteKeywordMap.visibility = VISIBLE
        }
        binding.keywordAdd.setOnClickListener {
            binding.deleteKeywordMap.visibility = GONE
            binding.addKeywordMap.visibility = VISIBLE
        }




        mapView = MapView(requireActivity())
        binding.mapView.addView(mapView)

        println("@@ 주소 위도 경도 geoCode   ::   ${ScheduleUtils.geoCoding("경기도 수원시 영통구 망포2동 덕영대로 1400", requireActivity())}")
        // println("@@ 주소 위도 경도 getAddress    ::   ${ScheduleUtils.getAddress(geoCoding("경기도 수원시 영통구 망포2동 덕영대로 1400", requireActivity()))}")


        fusedLocationClient = LocationServices.getFusedLocationProviderClient(requireContext())


        if (checkSelfPermission(
                requireContext(),
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED && checkSelfPermission(
                requireContext(),
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ) {

            fusedLocationClient.lastLocation
                .addOnSuccessListener { location : Location? ->
                    println(" location +-  ${location}")
                    val mp = MapPoint.mapPointWithGeoCoord(location!!.latitude, location.longitude)
                    mapView.setMapCenterPoint(mp, true)
                    mapView.setZoomLevel(1, true)
                    marker(location, "pick")
                }
        }else{
            val locationPermissionRequest = registerForActivityResult(
                ActivityResultContracts.RequestMultiplePermissions()
            ) { permissions ->
                when {
                    permissions.getOrDefault(Manifest.permission.ACCESS_FINE_LOCATION, false) -> {

                    }
                    permissions.getOrDefault(Manifest.permission.ACCESS_COARSE_LOCATION, false) -> {

                    } else -> {

                }
                }
            }
            locationPermissionRequest.launch(arrayOf(
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION))
        }





 /*       val p = geoCoding("경기도 수원시 영통구 망포2동 덕영대로 1400", requireActivity())
        val mp = MapPoint.mapPointWithGeoCoord(p.latitude, p.longitude)
        val mapPoint = MapPoint.mapPointWithGeoCoord(37.28730797086605, 127.01192716921177)
        //val msapPoint = MapPoint.mapPointWithGeoCoord(35.898054, 128.544296)
        //지도의 중심점을 수원 화성으로 설정, 확대 레벨 설정 (값이 작을수록 더 확대됨)
        mapView.setMapCenterPoint(mp, true)
        mapView.setZoomLevel(1, true)

        //마커 생성
        val marker = MapPOIItem()
        marker.itemName = "이곳이 수원 화성입니다"
        marker.mapPoint = mapPoint
        marker.markerType = MapPOIItem.MarkerType.BluePin
        marker.selectedMarkerType = MapPOIItem.MarkerType.RedPin*/




        // val point: Point = binding.mapView.toScreenPoint(LatLng.from(37.402005, 127.108621))
/*
        var geocoder = kakao.maps.services.Geocoder();

        // 주소로 좌표를 검색합니다
        geocoder.addressSearch('제주특별자치도 제주시 첨단로 242', function(result, status) {

            // 정상적으로 검색이 완료됐으면
            if (status === kakao.maps.services.Status.OK) {

                var coords = new kakao.maps.LatLng(result[0].y, result[0].x);

                // 결과값으로 받은 위치를 마커로 표시합니다
                var marker = new kakao.maps.Marker({
                        map: map,
                        position: coords
                });

                // 인포윈도우로 장소에 대한 설명을 표시합니다
                var infowindow = new kakao.maps.InfoWindow({
                        content: '<div style="width:150px;text-align:center;padding:6px 0;">우리회사</div>'
                });
                infowindow.open(map, marker);

                // 지도의 중심을 결과값으로 받은 위치로 이동시킵니다
                map.setCenter(coords);
            }
        });
        */

        return binding.root
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

    @SuppressLint("ResourceAsColor")
    fun openColorPicker(button:Int, color:Int) {
        val colorPicker = AmbilWarnaDialog(context , color, object : AmbilWarnaDialog.OnAmbilWarnaListener {
            override fun onCancel(dialog: AmbilWarnaDialog) {}
            override fun onOk(dialog: AmbilWarnaDialog, color: Int) {
                when(button){
                    0 ->  bColor = color
                    else -> tColor = color
                }
                scheduleColor(tColor, bColor)
            }
        })
        colorPicker.show()
    }

    fun scheduleColor(tColor : Int, bColor:Int){
        binding.addSchedulebar.setTextColor(tColor)
        addSchedulebar.setColor(bColor)
        barColor.setColor(bColor)
        textColor.setColor(tColor)
    }

    @SuppressLint("ResourceType")
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
                R.style.DialogTheme,
                dateSetListener,
                startDate.toString("yyyy").toInt() ,
                startDate.toString("M").toInt()-1,
                startDate.toString("dd").toInt()
            ).show()
            else ->  DatePickerDialog(requireActivity(),
                R.style.DialogTheme,
                dateSetListener,
                endDate.toString("yyyy").toInt() ,
                endDate.toString("M").toInt()-1,
                endDate.toString("dd").toInt()
            ).show()
        }
    }

    fun addSchedule()  {
        Log.e("adddFragment"," click ")
        Log.e("adddFragment"," ${DateTime("${binding.startDate.text}")}  ${binding.etTitle.text}  $bColor")
        viewModel.addSchedule(
            Schedule(
                DateTime("${binding.startDate.text}"),
                DateTime("${binding.endDate.text}"),
                binding.etTitle.text.toString(),
                binding.etContent.text.toString(),
                bColor,
                tColor

        )
        )

    }

    fun marker(location:Location, name:String){
        val marker = MapPOIItem()

        mapView.addPOIItem(marker)
        //맵 포인트 위도경도 설정
        //맵 포인트 위도경도 설정
        val mapPoint = MapPoint.mapPointWithGeoCoord(location.latitude, location.longitude)
        marker.itemName = name
        marker.tag = 0
        marker.mapPoint = mapPoint
        marker.markerType = MapPOIItem.MarkerType.BluePin // 기본으로 제공하는 BluePin 마커 모양.


        mapView.addPOIItem(marker)

    }




}