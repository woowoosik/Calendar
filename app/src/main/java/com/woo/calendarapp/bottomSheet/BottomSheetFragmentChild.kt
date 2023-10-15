package com.woo.calendarapp.bottomSheet


import android.Manifest
import android.content.pm.PackageManager
import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.location.Location
import android.os.Build
import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStoreOwner
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.woo.calendarapp.EventObserver
import com.woo.calendarapp.R
import com.woo.calendarapp.databinding.BottomsheetChildBinding
import com.woo.calendarapp.fragment.AddFragment
import com.woo.calendarapp.fragment.UpdateFragment
import com.woo.calendarapp.schedule.Schedule
import com.woo.calendarapp.utils.ScheduleUtils
import com.woo.calendarapp.viewmodel.MainViewModel
import net.daum.mf.map.api.MapPOIItem
import net.daum.mf.map.api.MapPoint
import net.daum.mf.map.api.MapView


class BottomSheetFragmentChild : BottomSheetDialogFragment() {
    private lateinit var mainViewModel : MainViewModel
    private lateinit var binding : BottomsheetChildBinding
    private lateinit var bottomSheetBehavior : BottomSheetBehavior<*>

    // 카카오맵
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private lateinit var mapView : MapView

    private lateinit var mapLocation : Pair<Double,Double>




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

        mapLocation = Pair(arguments?.getDouble("x"), arguments?.getDouble("y")) as Pair<Double, Double>

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
            println(" x ${arguments?.getDouble("x")}")
            println(" y ${arguments?.getDouble("y")}")
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

        println("child : 들어가기 전 argu ${arguments?.getDouble("x")}  ${arguments?.getDouble("y")}")
        println(" child : 들어가기 전 mapLocation ${mapLocation.first}, ${mapLocation.second} ")
        getMap()


    }


    fun marker(latitude:Double, longitude:Double , name:String){
        val marker = MapPOIItem()

        mapView.addPOIItem(marker)
        //맵 포인트 위도경도 설정
        //맵 포인트 위도경도 설정
        val mapPoint = MapPoint.mapPointWithGeoCoord(latitude, longitude)
        marker.itemName = name
        marker.tag = 0
        marker.mapPoint = mapPoint
        marker.markerType = MapPOIItem.MarkerType.BluePin // 기본으로 제공하는 BluePin 마커 모양.


        mapView.addPOIItem(marker)

    }



    fun getMap(){

        mapView = MapView(requireActivity())
        binding.mapView.addView(mapView)



        if (ContextCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED && ContextCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ) {

            println(" permission if ")
            if( !this::mapLocation.isInitialized ){
                fusedLocationClient.lastLocation
                    .addOnSuccessListener { location : Location? ->
                        println(" location +-  ${location}")
                        mapLocation = Pair(location!!.longitude, location!!.latitude)
                        moveMap(location!!.longitude, location!!.latitude)

                    }
            }else{
                println(" moveMap ${mapLocation.first}, ${mapLocation.second} ")
                moveMap(mapLocation.first, mapLocation.second)
            }
        }else{
            println(" permission else ")
            val locationPermissionRequest = registerForActivityResult(
                ActivityResultContracts.RequestMultiplePermissions()
            ) { permissions ->
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    when {
                        permissions.getOrDefault(Manifest.permission.ACCESS_FINE_LOCATION, false) -> {

                        }
                        permissions.getOrDefault(Manifest.permission.ACCESS_COARSE_LOCATION, false) -> {

                        } else -> {

                    }
                    }
                }
            }
            locationPermissionRequest.launch(arrayOf(
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION))
        }

    }


    fun moveMap( longitude:Double ,latitude:Double) {
        val mp = MapPoint.mapPointWithGeoCoord(latitude, longitude)
        mapView.setMapCenterPoint(mp, true)
        mapView.setZoomLevel(1, true)
        marker(latitude, longitude, "pick")
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