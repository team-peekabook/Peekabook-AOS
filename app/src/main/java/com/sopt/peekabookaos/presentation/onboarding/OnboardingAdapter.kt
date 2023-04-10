package com.sopt.peekabookaos.presentation.onboarding

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.sopt.peekabookaos.databinding.ItemOnboardingBinding
import com.sopt.peekabookaos.domain.entity.Onboarding
import com.sopt.peekabookaos.util.ItemDiffCallback

class OnboardingAdapter :
    ListAdapter<Onboarding, OnboardingAdapter.OnboardingViewHolder>(onboardingDiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OnboardingViewHolder {
        val binding =
            ItemOnboardingBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return OnboardingViewHolder(binding)
    }

    override fun onBindViewHolder(holder: OnboardingViewHolder, position: Int) {
        holder.onBind(getItem(position))
    }

    class OnboardingViewHolder(
        val binding: ItemOnboardingBinding
    ) :
        RecyclerView.ViewHolder(binding.root) {
        fun onBind(data: Onboarding) {
            binding.data = data
        }
    }

    companion object {
        private val onboardingDiffCallback = ItemDiffCallback<Onboarding>(
            onItemsTheSame = { old, new -> old.image == new.image },
            onContentsTheSame = { old, new -> old == new }
        )
    }
}
