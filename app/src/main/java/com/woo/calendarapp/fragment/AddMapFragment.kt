package com.woo.calendarapp.fragment

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import android.os.Build
import android.os.Bundle
import android.system.Os.remove
import android.util.Log
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.view.isEmpty
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.*
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStoreOwner
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.woo.calendarapp.EventObserver
import com.woo.calendarapp.LoadingDialog
import com.woo.calendarapp.R
import com.woo.calendarapp.adapter.BottomRecyclerAdapter
import com.woo.calendarapp.bottomSheet.BottomSheetAddMap
import com.woo.calendarapp.databinding.FragmentAddMapBinding
import com.woo.calendarapp.schedule.Schedule
import com.woo.calendarapp.utils.ScheduleUtils
import com.woo.calendarapp.viewmodel.MainViewModel
import kotlinx.coroutines.delay
import net.daum.mf.map.api.MapPOIItem
import net.daum.mf.map.api.MapPoint
import net.daum.mf.map.api.MapView

class AddMapFragment : Fragment() {

    private lateinit var binding : FragmentAddMapBinding
    private lateinit var viewModel: MainViewModel

    // 카카오맵
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private lateinit var mapView : MapView

    private lateinit var mapLocation : Pair<Double,Double>


    private lateinit var loadingDialog: LoadingDialog

/*

    private var updateListener : OnUpdateClickListener? = null
    interface OnUpdatelickListener{
        fun onUpdateClick()
    }
    fun setUpdateClickListener(onUpdateClickListener: OnUpdateClickListener) {
        this.updateListener = onUpdateClickListener
    }
*/



    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        Log.e("onCreateView", "AddMapFragment")
        viewModel =  ViewModelProvider(activity as ViewModelStoreOwner)[MainViewModel::class.java]

        binding =  DataBindingUtil.inflate(inflater, R.layout.fragment_add_map, container, false)
        binding.mainViewModel = viewModel

        //kakao map
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(requireContext())

        loadingDialog = LoadingDialog(requireActivity())


        getMap()

        // 아래 fragment 터치 막기
        binding.layout.isClickable = true

        binding.btMapSearch.setOnClickListener {


            searchKeyword()


           // viewModel.searchKeyword("은행")
        }


        viewModel.searchKeyword.observe(viewLifecycleOwner, EventObserver {



            println("searchKeyword observe ")
            println("${viewModel.getSearchKeyList().documents.size}")



           val bottomSheetAddMap = BottomSheetAddMap()

            bottomSheetAddMap.show(requireActivity().supportFragmentManager, bottomSheetAddMap.tag)

            bottomSheetAddMap.lifecycle.addObserver(LifecycleEventObserver { source, event ->
                if( event == Lifecycle.Event.ON_PAUSE){
                Log.e("addMapFramgnet", "ON_PAUSE dismiss")
                loadingDialog.dismiss()
            }
            })




        })



        binding.btPlace.setOnClickListener {
            if (ActivityCompat.checkSelfPermission(
                    requireContext(),
                    Manifest.permission.ACCESS_FINE_LOCATION
                ) == PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                    requireContext(),
                    Manifest.permission.ACCESS_COARSE_LOCATION
                ) == PackageManager.PERMISSION_GRANTED
            ) {
                fusedLocationClient.lastLocation
                    .addOnSuccessListener { location : Location? ->
                        println(" location +-  ${location}")
                        mapLocation = Pair(location!!.longitude, location!!.latitude)
                        moveMap(location!!.longitude, location!!.latitude)

                    }
            }else{
                Toast.makeText(requireContext(),
                    "permission 허락", Toast.LENGTH_SHORT).show()
            }

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


        binding.etMapSearch.setOnEditorActionListener { v, actionId, event ->
            var handled = false
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                searchKeyword()
                handled = true
            }
            handled
        }



        return binding.root
    }




    fun searchKeyword(){
        println("검색버튼 클릭")
        loadingDialog.show()
        if(binding.etMapSearch.text.toString() == ""){
            Toast.makeText(requireContext(),
                "검색 키워드를 입력해주세요.", Toast.LENGTH_SHORT).show()
            loadingDialog.dismiss()
        }else{
            viewModel.searchKeyword(binding.etMapSearch.text.toString())
            // viewModel.searchKeyword("은행")
        }
    }

    override fun onViewStateRestored(savedInstanceState: Bundle?) {
        super.onViewStateRestored(savedInstanceState)

        Log.e("onViewStateRestored", "AddMapFragment")
    }

    override fun onAttach(context: Context) {
        Log.e("onAttach", "AddMapFragment")
        super.onAttach(context)
    }
    override fun onDestroyView() {
        Log.e("onDestroyView", "AddMapFragment")
        super.onDestroyView()
    }
    override fun onDestroy() {
        Log.e("onDestroy", "AddMapFragment")
        super.onDestroy()

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
                Log.w("addMapFragment", "getMap() else")
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

        Log.w("addMapFragment", "moveMap()")
        val mp = MapPoint.mapPointWithGeoCoord(latitude, longitude)
        mapView.setMapCenterPoint(mp, true)
        mapView.setZoomLevel(1, true)
        marker(latitude, longitude, "pick")
    }


    fun updateMap(){
        println("updateMap   ${mapLocation.first}  ${mapLocation.second}")

        val vm = viewModel.getLocation()
        Log.w("addMapFragment", " ${vm }  ${mapLocation}")
        if( vm == null || vm != mapLocation){
            Log.w("addMapFragment", " 들어옴")
            viewModel.setLocation(mapLocation.first, mapLocation.second)
        }

        viewModel.updateClick()

        removeFragment()
    }


    fun removeFragment() {
        Log.e("removeFragment", "addMapFragment ")
        val fragmentManager: FragmentManager = requireActivity().supportFragmentManager
        fragmentManager.beginTransaction()
            .remove(this@AddMapFragment)
            .commit()
        fragmentManager.popBackStack()
    }

    override fun onStart() {
        super.onStart()
        if(binding.addMapView.isEmpty()){
            getMap()
        }
    }

    override fun onStop() {
        super.onStop()

        binding.addMapView.removeView(mapView)
        Log.e("addMapFragment", "onStop  ")

        Log.e("addMapFragment", "onStop ${viewModel.updateKeywordMap.value.toString()} ")
    }





}