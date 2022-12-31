package com.sopt.peekabookaos.util.extensions

import android.view.View

fun View.setOnSingleClickListener(onSingleClick: (View) -> Unit) {
    setOnClickListener(OnSingleClickListener { onSingleClick(it) })
}
