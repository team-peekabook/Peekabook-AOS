package com.sopt.peekabookaos.util.extensions

import android.app.Activity
import android.os.Build

fun Activity.activityTransition(transitionType: Int, enterAnim: Int, exitAnim: Int) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.UPSIDE_DOWN_CAKE) {
        overrideActivityTransition(transitionType, enterAnim, exitAnim)
    } else {
        overridePendingTransition(enterAnim, exitAnim)
    }
}
