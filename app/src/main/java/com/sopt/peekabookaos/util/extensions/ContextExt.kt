package com.sopt.peekabookaos.util.extensions

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.util.DisplayMetrics
import android.util.Size
import android.view.View
import android.view.WindowInsets
import android.view.WindowManager
import com.sopt.peekabookaos.util.OnSingleClickListener

fun View.setSingleOnClickListener(onSingleClick: (View) -> Unit) {
    setOnClickListener(OnSingleClickListener { onSingleClick(it) })
}

fun Context.isNetworkConnected(): Boolean {
    var isConnected = false
    val cm = this.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    val capabilities = cm.getNetworkCapabilities(cm.activeNetwork)
    if (capabilities != null) {
        isConnected = isConnectedToWiFi(capabilities) || isConnectedToCellular(capabilities)
    }
    return isConnected
}

private fun isConnectedToWiFi(capabilities: NetworkCapabilities): Boolean {
    return capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)
}

private fun isConnectedToCellular(capabilities: NetworkCapabilities): Boolean {
    return capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)
}

fun Context.getScreenSize(): Pair<Int, Int> {
    return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
        val metrics = this.getSystemService(WindowManager::class.java).currentWindowMetrics
        val windowInsets = metrics.windowInsets

        val insets = windowInsets.getInsetsIgnoringVisibility(
            WindowInsets.Type.systemBars() or WindowInsets.Type.displayCutout()
        )
        val insetsWidth = insets.right + insets.left
        val insetsHeight = insets.top + insets.bottom

        val legacySize = Size(
            metrics.bounds.width() - insetsWidth,
            metrics.bounds.height() - insetsHeight
        )
        Pair(legacySize.width, legacySize.height)
    } else {
        val metrics = DisplayMetrics()
        @Suppress("DEPRECATION")
        this.getSystemService(WindowManager::class.java).defaultDisplay.getRealMetrics(metrics)
        Pair(metrics.widthPixels, metrics.heightPixels)
    }
}
