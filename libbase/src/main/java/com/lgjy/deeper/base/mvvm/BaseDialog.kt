package com.lgjy.deeper.base.mvvm

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.Gravity
import android.view.WindowManager
import androidx.annotation.LayoutRes
import androidx.fragment.app.DialogFragment
import com.lgjy.deeper.base.R

abstract class BaseDialog : DialogFragment {

    constructor() : super()

    constructor(@LayoutRes contentLayoutId: Int) : super(contentLayoutId)

    /**
     * set window popup from bottom
     */
    fun setShowFromBottom() {
        dialog?.window?.apply {
            decorView.setPadding(0,0,0,0)
            setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            with(attributes) {
                width = WindowManager.LayoutParams.MATCH_PARENT
                height = WindowManager.LayoutParams.WRAP_CONTENT
                gravity = Gravity.BOTTOM
                windowAnimations = R.style.AnimBottomDialog
            }
        }
    }
}
