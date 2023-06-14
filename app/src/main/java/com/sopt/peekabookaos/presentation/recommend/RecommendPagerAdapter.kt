package com.sopt.peekabookaos.presentation.recommend

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter

class RecommendPagerAdapter(fragmentActivity: FragmentActivity) :
    FragmentStateAdapter(fragmentActivity) {

    override fun getItemCount() = 2
    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> RecommendedFragment()
            1 -> RecommendingFragment()
            else -> throw IndexOutOfBoundsException()
        }
    }
}
