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
            println("${n}")
            println("$h")
            println("-_---- -- -${h.toInt() - n.toInt()}")
            mainBinding.calendar.adapter = MainFragmentAdapter(this)
            mainBinding.calendar.setCurrentItem(MainFragmentAdapter.START_POSITION +( h.toInt() - n.toInt()), false)
        })

        mainBinding.calendar.adapter = MainFragmentAdapter(this)
        mainBinding.calendar.setCurrentItem(MainFragmentAdapter.START_POSITION, false)



        mainViewModel.toolbarDate.observe(this, Observer{
            mainBinding.tb.toolbarTxt.text = it
        })

        mainBinding.tb.addSchedule.setOnClickListener {
            Log.e("MaingActivity"," 스케쥴 추가 이동 ")

          //  val addFragment = AddFragment()
           /* addFragment.loadingDialog = LoadingDialog(this)
            addFragment.permissionCheck = PermissionCheck(this)
*/
           supportFragmentManager.beginTransaction()
                .replace(R.id.main, AddFragment())
                .addToBackStack(null)
                .commit()
        }

        /*
        fun getHashKey() {
            var packageInfo: PackageInfo? = null
            try {
                packageInfo = packageManager.getPackageInfo(packageName, PackageManager.GET_SIGNATURES)
            } catch (e: PackageManager.NameNotFoundException) {
                e.printStackTrace()
            }
            if (packageInfo == null) Log.e("KeyHash", "KeyHash:null")
            for (signature in packageInfo!!.signatures) {
                try {
                    val md: MessageDigest = MessageDigest.getInstance("SHA")
                    md.update(signature.toByteArray())
                    //Log.d("KeyHash", Base64.encodeToString(md.digest(), Base64.DEFAULT))
                    Log.d("KeyHash", Base64.encode(md.digest(),0).toString())
                } catch (e: NoSuchAlgorithmException) {
                    Log.d("KeyHash", "Unable to get MessageDigest. signature=$signature")

                }
            }

        }
        var keyHash = Utility.getKeyHash(this)
        Log.d("KeyHash", "keyhash : $keyHash")
*/

       /* try {
            val information =
                packageManager.getPackageInfo(packageName, PackageManager.GET_SIGNING_CERTIFICATES)
            val signatures = information.signingInfo.apkContentsSigners
            val md = MessageDigest.getInstance("SHA")
            for (signature in signatures) {
                val md: MessageDigest
                md = MessageDigest.getInstance("SHA")
                md.update(signature.toByteArray())
                var hashcode = String(Base64.encode(md.digest(), 0))
                Log.d("hashcode", "" + hashcode)
            }
        } catch (e: Exception) {
            Log.d("hashcode", "에러::" + e.toString())

        }
*/
    }


}

