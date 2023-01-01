package com.sopt.peekabookaos.util.extensions

import android.view.View

fun View.setOnClickListener(onSingleClick: (View) -> Unit) {
    setOnClickListener(OnSingleClickListener { onSingleClick(it) })
}
