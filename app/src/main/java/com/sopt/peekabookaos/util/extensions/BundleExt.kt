package com.sopt.peekabookaos.util.extensions

import android.os.Build
import android.os.Bundle

fun <T> Bundle.getBundleParcelable(name: String, clazz: Class<T>): T? {
    return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
        getParcelable(name, clazz)
    } else {
        getParcelable(name)
    }
}
