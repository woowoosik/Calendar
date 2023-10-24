package com.woo.calendarapp

import android.content.pm.PackageInfo
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Base64
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.kakao.util.maps.helper.Utility
import com.woo.calendarapp.adapter.MainFragmentAdapter
import com.woo.calendarapp.databinding.ActivityMainBinding
import com.woo.calendarapp.fragment.AddFragment
import com.woo.calendarapp.viewmodel.MainViewModel
import dagger.hilt.android.AndroidEntryPoint
import org.joda.time.DateTime
import java.security.MessageDigest
import java.security.NoSuchAlgorithmException


@AndroidEntryPoint
class MainActivity : AppCompatActivity(){

    private lateinit var mainBinding : ActivityMainBinding
    private lateinit var mainViewModel : MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        mainViewModel = ViewModelProvider(this)[MainViewModel::class.java]
        mainBinding.vm = mainViewModel



        mainViewModel.addMainComplate.observe(this, EventObserver {
            val n = DateTime.now().toString("MM")
            val h = DateTime(mainViewModel.getDate()).toString("MM")
            mainBinding.calendar.adapter = MainFragmentAdapter(this)
            mainBinding.calendar.setCurrentItem(MainFragmentAdapter.START_POSITION +( h.toInt() - n.toInt()), false)
        })

        mainBinding.calendar.adapter = MainFragmentAdapter(this)
        mainBinding.calendar.setCurrentItem(MainFragmentAdapter.START_POSITION, false)



        mainViewModel.toolbarDate.observe(this, Observer{
            mainBinding.tb.toolbarTxt.text = it
        })

        mainBinding.tb.addSchedule.setOnClickListener {

           supportFragmentManager.beginTransaction()
                .replace(R.id.main, AddFragment())
                .addToBackStack(null)
                .commit()
        }

    }


}

