package com.lgjy.deeper.ndk.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.lgjy.deeper.base.mvvm.BaseFragment
import com.lgjy.deeper.ndk.R

internal class NDKFragment : BaseFragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_ndk, container, false)
    }
}
