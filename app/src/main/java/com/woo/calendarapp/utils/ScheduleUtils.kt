package com.woo.calendarapp.utils

import android.content.Context
import android.location.Geocoder
import android.location.Location
import com.woo.calendarapp.calendar.ChildView
import com.woo.calendarapp.schedule.Schedulebar
import java.util.*

class ScheduleUtils {

    companion object {
        fun geoCoding(address: String, context: Context): Location {
            return try {
                Geocoder(context, Locale.KOREA).getFromLocationName(address, 1)?.let {
                    Location("").apply {
                        latitude = it[0].latitude
                        longitude = it[0].longitude
                    }
                } ?: Location("").apply {
                    latitude = 0.0
                    longitude = 0.0
                }
            } catch (e: Exception) {
                e.printStackTrace()
                geoCoding(address, context) //재시도
            }
        }

        fun getAddress(location: Location, context: Context): String {
            return try {
                with(
                    Geocoder(context, Locale.KOREA).getFromLocation(
                        location.latitude,
                        location.longitude,
                        1
                    ).first()
                ) {
                    getAddressLine(0)   //주소
                    countryName     //국가이름 (대한민국)
                    countryCode     //국가코드
                    adminArea       //행정구역 (서울특별시)
                    locality        //관할구역 (중구)
                    subLocality
                    thoroughfare    //상세구역 (봉래동2가)
                    subThoroughfare
                    featureName     //상세주소 (122-21)
                    postalCode

                    return " $countryName $countryCode $adminArea $locality $subLocality $thoroughfare $subThoroughfare $featureName"
                }


            } catch (e: Exception) {
                e.printStackTrace()
                getAddress(location, context)
            }
        }
    }

}