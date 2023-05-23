package com.sopt.peekabookaos.util.binding

import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.sopt.peekabookaos.R

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

    @JvmStatic
    @BindingAdapter("setCircleImage")
    fun ImageView.setCircleImage(imgUrl: String?) {
        this.let {
            Glide.with(context)
                .load(imgUrl)
                .apply {
                    if (imgUrl == null) {
                        placeholder(R.drawable.ic_profile_default)
                    }
                }
                .circleCrop()
                .into(this)
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
    @BindingAdapter("setImageResource")
    fun ImageView.setImageResource(resId: Int) {
        setImageResource(resId)
    }
}
