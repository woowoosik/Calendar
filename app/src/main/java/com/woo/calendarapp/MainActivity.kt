package com.woo.calendarapp

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.woo.calendarapp.adapter.MainFragmentAdapter
import com.woo.calendarapp.databinding.ActivityMainBinding
import com.woo.calendarapp.fragment.AddFragment
import com.woo.calendarapp.viewmodel.MainViewModel
import dagger.hilt.android.AndroidEntryPoint
import org.joda.time.DateTime


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

            val yn = DateTime.now().toString("yyyy")
            val n = DateTime.now().toString("MM")
            val yh = DateTime(mainViewModel.getDate()).toString("yyyy")
            val h = DateTime(mainViewModel.getDate()).toString("MM")

            mainBinding.calendar.adapter = MainFragmentAdapter(this)
            if(yh == yn){
                mainBinding.calendar.setCurrentItem(MainFragmentAdapter.START_POSITION +( h.toInt() - n.toInt()), false)
            }else{
                mainBinding.calendar.setCurrentItem(MainFragmentAdapter.START_POSITION +( h.toInt() - n.toInt() + (12*(yh.toInt()-yn.toInt()))), false)
            }
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

        // 수동으로 권한 변경시 강제로 메인으로
        if(savedInstanceState != null){
            finishAffinity()
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            System.exit(0)
        }

    }



}

