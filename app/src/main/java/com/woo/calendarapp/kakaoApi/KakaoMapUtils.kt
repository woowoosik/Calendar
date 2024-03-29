package com.woo.calendarapp.kakaoApi

import net.daum.mf.map.api.MapPOIItem
import net.daum.mf.map.api.MapPoint
import net.daum.mf.map.api.MapView


class KakaoMapUtils {

    companion object {

        fun marker(latitude:Double, longitude:Double , name:String, mapView: MapView){
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


        fun moveMap( longitude:Double ,latitude:Double, mapView:MapView) {
            val mp = MapPoint.mapPointWithGeoCoord(latitude, longitude)
            mapView.setMapCenterPoint(mp, true)
            mapView.setZoomLevel(1, true)
            marker(latitude, longitude, "pick",mapView)
        }



    }



}