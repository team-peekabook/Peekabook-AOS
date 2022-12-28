package com.sopt.peekabookaos.util.binding

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide

object BindingAdapter {
    @JvmStatic
    @BindingAdapter("setImage")
    fun ImageView.setImage(imgUrl: String?) {
        this.let {
            Glide.with(context)
                .load(imgUrl)
                .into(this)
        }
    }
}
