package com.sopt.peekabookaos.util.extensions

import android.os.Bundle
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import com.sopt.peekabookaos.R
import com.sopt.peekabookaos.util.ToastMessageUtil
import kotlin.system.exitProcess

inline fun <T : Fragment> T.withArgs(argsBuilder: Bundle.() -> Unit): T {
    return this.apply {
        arguments = Bundle().apply(argsBuilder)
    }
}

fun Fragment.initBackPressedCallback() {
    var onBackPressedTime = 0L
    val deadline = 2000L
    requireActivity().onBackPressedDispatcher.addCallback(
        viewLifecycleOwner,
        object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                val curTime = System.currentTimeMillis()
                val gap = curTime - onBackPressedTime
                if (gap > deadline) {
                    onBackPressedTime = curTime
                    ToastMessageUtil.showToast(
                        requireContext(),
                        getString(R.string.finish_app_toast_msg)
                    )
                } else {
                    requireActivity().finishAffinity()
                    System.runFinalization()
                    exitProcess(0)
                }
            }
        })
}
