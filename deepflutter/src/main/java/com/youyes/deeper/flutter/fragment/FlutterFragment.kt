package com.youyes.deeper.flutter.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.youyes.deeper.base.mvvm.BaseFragment
import com.youyes.deeper.flutter.R

internal class FlutterFragment : BaseFragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_flutter, container, false)
    }
}