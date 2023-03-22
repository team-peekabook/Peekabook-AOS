package com.sopt.peekabookaos.util.extensions

import android.view.View
import com.sopt.peekabookaos.util.OnSingleClickListener

fun View.setSingleOnClickListener(onSingleClick: (View) -> Unit) {
    setOnClickListener(OnSingleClickListener { onSingleClick(it) })
}
