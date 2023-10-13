package com.woo.calendarapp.kakaoApi

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import android.os.Build
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import com.google.android.gms.location.LocationServices
import com.woo.calendarapp.utils.ScheduleUtils
import net.daum.mf.map.api.MapPOIItem
import net.daum.mf.map.api.MapPoint
import net.daum.mf.map.api.MapView

class KakaoMap() {
/*
    fun marker(location: Location, name:String,mapView:MapView){
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



    fun getMap(context: Context){

        val mapView = MapView(context)
        binding.mapView.addView(mapView)

        println("@@ 주소 위도 경도 geoCode   ::   ${ScheduleUtils.geoCoding("경기도 수원시 영통구 망포2동 덕영대로 1400", context)}")
        // println("@@ 주소 위도 경도 getAddress    ::   ${ScheduleUtils.getAddress(geoCoding("경기도 수원시 영통구 망포2동 덕영대로 1400", requireActivity()))}")

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(context)


        if (ContextCompat.checkSelfPermission(
                context,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED && ContextCompat.checkSelfPermission(
                context,
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

    }*/


}