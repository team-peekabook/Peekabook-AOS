package com.sopt.peekabookaos.util.extensions

import android.view.WindowManager
import androidx.fragment.app.DialogFragment

fun DialogFragment.initLayout() {
    val ratio = 0.89
    val layoutParams = requireNotNull(dialog).window!!.attributes
    layoutParams.width = (resources.displayMetrics.widthPixels * ratio).toInt()
    layoutParams.height = WindowManager.LayoutParams.WRAP_CONTENT
    requireNotNull(dialog).window!!.attributes = layoutParams
}
