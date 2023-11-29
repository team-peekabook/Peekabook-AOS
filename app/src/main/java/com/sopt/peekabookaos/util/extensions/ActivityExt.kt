package com.sopt.peekabookaos.util.extensions

import android.app.Activity
import android.os.Build
import androidx.activity.addCallback
import androidx.appcompat.app.AppCompatActivity
import com.sopt.peekabookaos.R
import com.sopt.peekabookaos.util.ToastMessageUtil.showToast
import kotlin.system.exitProcess

fun Activity.activityTransition(transitionType: Int, enterAnim: Int, exitAnim: Int) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.UPSIDE_DOWN_CAKE) {
        overrideActivityTransition(transitionType, enterAnim, exitAnim)
    } else {
        @Suppress("DEPRECATION")
        overridePendingTransition(enterAnim, exitAnim)
    }
}

fun AppCompatActivity.initBackPressedCallback() {
    var onBackPressedTime = 0L
    val deadline = 2000L
    onBackPressedDispatcher.addCallback(this) {
        if (System.currentTimeMillis() - onBackPressedTime >= deadline) {
            onBackPressedTime = System.currentTimeMillis()
            showToast(this@initBackPressedCallback, getString(R.string.finish_app_toast_msg))
        } else {
            this@initBackPressedCallback.finishAffinity()
            System.runFinalization()
            exitProcess(0)
        }
    }
}
