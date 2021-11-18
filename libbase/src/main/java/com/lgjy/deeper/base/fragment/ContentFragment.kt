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
 * Initial content fragment in tablets
 */

internal class ContentFragment : BaseFragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_content, container, false)
    }
}
