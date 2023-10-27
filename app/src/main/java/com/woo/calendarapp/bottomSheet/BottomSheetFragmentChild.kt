package com.woo.calendarapp.bottomSheet


import android.Manifest
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.location.Location
import android.os.Build
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.core.content.ContextCompat
import androidx.core.view.isEmpty
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStoreOwner
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetBehavior.BottomSheetCallback
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.snackbar.Snackbar
import com.woo.calendarapp.EventObserver
import com.woo.calendarapp.LoadingDialog
import com.woo.calendarapp.PermissionCheck
import com.woo.calendarapp.R
import com.woo.calendarapp.databinding.BottomsheetChildBinding
import com.woo.calendarapp.fragment.UpdateFragment
import com.woo.calendarapp.kakaoApi.KakaoMapUtils
import com.woo.calendarapp.viewmodel.MainViewModel
import dagger.hilt.android.AndroidEntryPoint
import net.daum.mf.map.api.MapPOIItem
import net.daum.mf.map.api.MapPoint
import net.daum.mf.map.api.MapView
import javax.inject.Inject


@AndroidEntryPoint
class BottomSheetFragmentChild : BottomSheetDialogFragment() {
    private lateinit var mainViewModel : MainViewModel
    private lateinit var binding : BottomsheetChildBinding
    private lateinit var bottomSheetBehavior : BottomSheetBehavior<*>

    // 카카오맵
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private lateinit var mapView : MapView

    private lateinit var mapLocation : Pair<Double,Double>

    @Inject
    lateinit var loadingDialog: LoadingDialog
    @Inject
    lateinit var permissionCheck : PermissionCheck

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mainViewModel = ViewModelProvider(activity as ViewModelStoreOwner)[MainViewModel::class.java]

        mapLocation = Pair(arguments?.getDouble("x"), arguments?.getDouble("y")) as Pair<Double, Double>

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

        binding.mapView.layoutParams.height = resources.displayMetrics.heightPixels/3

        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        val updateFragment = UpdateFragment()

        binding.bottomChildUpdate.setOnClickListener {
            loadingDialog.show()

            bottomSheetBehavior!!.state = BottomSheetBehavior.STATE_HIDDEN
            mainViewModel.updateOpen()

            val bundle = Bundle()
            bundle.putInt("id", arguments?.getInt("id")!!.toInt())
            bundle.putString("start", arguments?.getString("start"))
            bundle.putString("end", arguments?.getString("end"))
            bundle.putString("title", arguments?.getString("title"))
            bundle.putString("content", arguments?.getString("content"))
            bundle.putInt("barColor", arguments?.getInt("barColor")!!.toInt())
            bundle.putInt("textColor", arguments?.getInt("textColor")!!.toInt())
            bundle.putBoolean("isChecked", arguments?.getBoolean("isChecked")!!)
            if(arguments?.getBoolean("isChecked")!!){
                bundle.putDouble("x", arguments?.getDouble("x")!!)
                bundle.putDouble("y", arguments?.getDouble("y")!!)
            }
            updateFragment.arguments = bundle
            requireActivity().supportFragmentManager.beginTransaction()
                .replace(R.id.main, updateFragment)
                .addToBackStack(null)
                .commit()

            binding.mapView.removeAllViews()

            updateFragment.lifecycle.addObserver(LifecycleEventObserver { source, event ->
                if( event == Lifecycle.Event.ON_RESUME){
                    loadingDialog.dismiss()
                }
            })


        }

        getMap()

        if(permissionCheck.isAllPermissionsGranted() && arguments?.getBoolean("isChecked") == true){
            mapView.visibility = View.VISIBLE
        }else{
            mapView.visibility = View.GONE

        }

    }

    @SuppressLint("MissingPermission")
    fun getMap(){
        mapView = MapView(requireActivity())
        binding.mapView.addView(mapView)

        if (permissionCheck.isAllPermissionsGranted()) {
            if( !this::mapLocation.isInitialized ){
                fusedLocationClient.lastLocation
                    .addOnSuccessListener { location : Location? ->
                        mapLocation = Pair(location!!.longitude, location!!.latitude)
                    }
            }else{
                KakaoMapUtils.moveMap(mapLocation.first, mapLocation.second, mapView)
            }

        } else {
            requestPermissionLauncher.launch(permissionCheck.permission)
        }
    }

    private val requestPermissionLauncher =
        registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) { permissions ->
            permissions.entries.forEach { permission ->
                when {
                    permission.value -> {
                        Snackbar.make(binding.root, "설정에서 권한을 허용해주세요. ", Snackbar.LENGTH_SHORT).show()
                    }
                    shouldShowRequestPermissionRationale(permission.key) -> {
                        Snackbar.make(binding.root,
                            "권한설정 확인", Snackbar.LENGTH_SHORT).show()
                    }
                    else -> Snackbar.make(binding.root, "권한이 거절되어있습니다. 설정에서 허용해주세요.", Snackbar.LENGTH_SHORT).show()
                }
            }
        }


    override fun onStart() {
        super.onStart()
        if(binding.mapView.isEmpty()){
            Log.e("onStart" , " addFragment")

            binding.mapView.removeAllViews()
            getMap()
        }
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