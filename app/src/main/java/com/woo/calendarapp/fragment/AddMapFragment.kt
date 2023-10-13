package com.woo.calendarapp.fragment

import android.Manifest
import android.content.pm.PackageManager
import android.location.Location
import android.os.Build
import android.os.Bundle
import android.system.Os.remove
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStoreOwner
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.woo.calendarapp.EventObserver
import com.woo.calendarapp.R
import com.woo.calendarapp.bottomSheet.BottomSheetAddMap
import com.woo.calendarapp.databinding.FragmentAddMapBinding
import com.woo.calendarapp.utils.ScheduleUtils
import com.woo.calendarapp.viewmodel.MainViewModel
import net.daum.mf.map.api.MapPOIItem
import net.daum.mf.map.api.MapPoint
import net.daum.mf.map.api.MapView

class AddMapFragment : Fragment(){

    private lateinit var binding : FragmentAddMapBinding
    private lateinit var viewModel: MainViewModel

    // 카카오맵
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private lateinit var mapView : MapView

    private lateinit var mapLocation : Pair<Double,Double>


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        viewModel =  ViewModelProvider(activity as ViewModelStoreOwner)[MainViewModel::class.java]

        binding =  DataBindingUtil.inflate(inflater, R.layout.fragment_add_map, container, false)
        binding.mainViewModel = viewModel

        //kakao map
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(requireContext())

        getMap()

        // 아래 fragment 터치 막기
        binding.layout.isClickable = true

        binding.btMapSearch.setOnClickListener {
            println("검색버튼 클릭")
            if(binding.etMapSearch.text.toString() == ""){
                Toast.makeText(requireContext(),
                    "검색 키워드를 입력해주세요.", Toast.LENGTH_SHORT).show()
            }else{
                viewModel.searchKeyword(binding.etMapSearch.text.toString())
               // viewModel.searchKeyword("은행")
            }

           // viewModel.searchKeyword("은행")
        }

        viewModel.searchKeyword.observe(viewLifecycleOwner, EventObserver {
            println("searchKeyword observe ")
            println("${viewModel.getSearchKeyList().documents.size}")

           val bottomSheetAddMap = BottomSheetAddMap()
            bottomSheetAddMap.show(requireActivity().supportFragmentManager, bottomSheetAddMap.tag)
        })


        binding.btPlace.setOnClickListener {
            binding.addMapView.removeView(mapView)
            getMap()
        }

       viewModel.clickMapLocation.observe(requireActivity()) {
           println("x : ${it.first}  y : ${it.second}")
           mapLocation = Pair(it.first, it.second)
           moveMap(it.first, it.second)
       }


        binding.updateMap.setOnClickListener {
            println("fragment updateMap")
            updateMap()
        }

        binding.cancelMap.setOnClickListener {
            removeFragment()
        }


        return binding.root
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
        binding.addMapView.addView(mapView)

        println("@@ 주소 위도 경도 geoCode   ::   ${ScheduleUtils.geoCoding("경기도 수원시 영통구 망포2동 덕영대로 1400", requireActivity())}")
        // println("@@ 주소 위도 경도 getAddress    ::   ${ScheduleUtils.getAddress(geoCoding("경기도 수원시 영통구 망포2동 덕영대로 1400", requireActivity()))}")




        if (ContextCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED && ContextCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ) {

            if( !this::mapLocation.isInitialized ){
                fusedLocationClient.lastLocation
                    .addOnSuccessListener { location : Location? ->
                        println(" location +-  ${location}")
                        mapLocation = Pair(location!!.longitude, location!!.latitude)
                        moveMap(location!!.longitude, location!!.latitude)

                    }
            }else{
                moveMap(mapLocation.first, mapLocation.second)
            }
        }else{
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


    fun updateMap(){
        println("updateMap")
        viewModel._updateKeywordMap.postValue(EventObserver.Event(true))

        removeFragment()
    }


    fun removeFragment() {
        val fragmentManager: FragmentManager = requireActivity().supportFragmentManager
        fragmentManager.beginTransaction()
            .remove(this@AddMapFragment)
            .commit()
        fragmentManager.popBackStack()
    }


    override fun onStop() {
        super.onStop()
        binding.addMapView.removeView(mapView)

    }





}