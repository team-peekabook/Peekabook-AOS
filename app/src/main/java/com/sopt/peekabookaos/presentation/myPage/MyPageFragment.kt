package com.sopt.peekabookaos.presentation.myPage

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.sopt.peekabookaos.databinding.FragmentMyPageBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MyPageFragment : Fragment() {
    private var _binding: FragmentMyPageBinding? = null
    private val binding: FragmentMyPageBinding
        get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentMyPageBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }
}
