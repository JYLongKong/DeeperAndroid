package com.lgjy.deeper.base.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.lgjy.deeper.base.R
import com.lgjy.deeper.base.mvvm.BaseFragment

/**
 * Created by LGJY on 2021/11/7.
 * Email：yujye@sina.com
 *
 * Tip18: Adapt to the tablet
 *
 * Main Fragment in DeepActivity distinguished whether it is a tablet
 *
 * if (isTablet) {
 *      inflate fragment_deep.xml in "layout-sw600dp"
 * } else {
 *      inflate fragment_deep.xml in "layout"
 * }
 */

internal class DeepFragment : BaseFragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_deep, container, false)
    }
}
