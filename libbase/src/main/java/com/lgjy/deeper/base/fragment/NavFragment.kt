package com.lgjy.deeper.base.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.lgjy.deeper.base.R
import com.lgjy.deeper.base.mvvm.BaseFragment

/**
 * Home Page with Bottom Navigation
 */

internal class NavFragment : BaseFragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_nav, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (childFragmentManager.findFragmentById(R.id.container_nav) as? NavHostFragment)?.navController?.let {
            view.findViewById<BottomNavigationView>(R.id.bottom_nav).apply {
                itemIconTintList = null     // get out of BottomNavigationView's control for icon style
                setupWithNavController(it)  // connect navController and BottomNavigationView
                setOnItemReselectedListener { }   // avoid recreate when reselected
            }
        }
    }
}
