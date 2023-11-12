package com.sopt.peekabookaos.util.binding

import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.databinding.BindingAdapter
import coil.imageLoader
import coil.request.ImageRequest
import coil.transform.CircleCropTransformation
import com.sopt.peekabookaos.R

object BindingAdapter {
    @JvmStatic
    @BindingAdapter("setImage")
    fun ImageView.setImage(imgUrl: String?) {
        this.let {
            val request = ImageRequest.Builder(context)
                .data(imgUrl)
                .target(this)
                .build()
            context.imageLoader.enqueue(request)
        }
    }

    @JvmStatic
    @BindingAdapter("setCircleImage")
    fun ImageView.setCircleImage(imgUrl: String?) {
        this.let {
            val request = ImageRequest.Builder(context)
                .data(imgUrl)
                .target(this)
                .transformations(CircleCropTransformation())
                .fallback(R.drawable.ic_profile_default)
                .build()
            context.imageLoader.enqueue(request)
        }
    }

    @JvmStatic
    @BindingAdapter("isFollowed", "isBlocked")
    fun TextView.setFollowButtonAppearance(isFollowed: Boolean, isBlocked: Boolean) {
        if (isBlocked) {
            setBackgroundColor(ContextCompat.getColor(context, R.color.peeka_red))
            setText(R.string.search_user_unblock)
        } else {
            if (isFollowed) {
                setBackgroundColor(ContextCompat.getColor(context, R.color.peeka_g_2))
                setText(R.string.search_user_following)
            } else {
                setBackgroundColor(ContextCompat.getColor(context, R.color.peeka_red))
                setText(R.string.search_user_follow)
            }
        }
    }

    @JvmStatic
    @BindingAdapter("isFollowed", "isBlocked")
    fun ImageView.setLineColor(isFollowed: Boolean, isBlocked: Boolean) {
        if (isBlocked) {
            setImageResource(R.drawable.shape_red_line_circle)
        } else {
            if (isFollowed) {
                setImageResource(R.drawable.shape_g2_line_circle)
            } else {
                setImageResource(R.drawable.shape_red_line_circle)
            }
        }
    }

    @JvmStatic
    @BindingAdapter("imageResource")
    fun ImageView.setAppImageResource(resId: Int) {
        setImageResource(resId)
    }
}
