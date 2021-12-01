package com.lgjy.deeper.ndk.fragment

import android.content.Context
import android.hardware.camera2.CameraCharacteristics
import android.hardware.camera2.CameraManager
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.lgjy.deeper.base.mvvm.BaseFragment
import com.lgjy.deeper.common.util.LogP
import com.lgjy.deeper.ndk.R

internal class NDKFragment : BaseFragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_ndk, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        printAllCameraInfo()
    }

    fun printAllCameraInfo() {
        val cameraManager = context?.getSystemService(Context.CAMERA_SERVICE) as? CameraManager ?: return
        kotlin.runCatching {
            cameraManager.cameraIdList.forEach { cameraId ->
                val characteristics = cameraManager.getCameraCharacteristics(cameraId)

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                    val size = characteristics.physicalCameraIds
                    LogP.i(TAG, "physicalCameraIds->$size")
//                        .forEach { physicalCameraId->
//                        LogP.i(TAG, "physicalCameraId->$physicalCameraId")
//                    }
                }

                val deviceLevel = characteristics.get(CameraCharacteristics.INFO_SUPPORTED_HARDWARE_LEVEL)
                val facing = characteristics.get(CameraCharacteristics.LENS_FACING)
                val isDepthCamera = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    characteristics.get(CameraCharacteristics.DEPTH_DEPTH_IS_EXCLUSIVE)
                } else {
                    false
                }

                Log.i(
                    "===yjy", "cameraId->$cameraId deviceLevel->$deviceLevel facing->$facing " +
                            "isDepthCamera->$isDepthCamera"
                )
            }
        }.onFailure { LogP.e("===yjy", "printAllCameraInfo()", it) }
    }

    companion object {
        private const val TAG = "===NDKFragment"
    }
}
