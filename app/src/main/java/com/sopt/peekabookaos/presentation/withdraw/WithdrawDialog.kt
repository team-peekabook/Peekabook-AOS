package com.sopt.peekabookaos.presentation.withdraw

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.activityViewModels
import com.sopt.peekabookaos.R
import com.sopt.peekabookaos.databinding.DialogWithdrawBinding
import com.sopt.peekabookaos.presentation.onboarding.OnboardingActivity

class WithdrawDialog : DialogFragment() {
    private var _binding: DialogWithdrawBinding? = null
    private val binding get() = _binding ?: error(getString(R.string.binding_error))

    private val withdrawViewModel by activityViewModels<WithdrawViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = DataBindingUtil.inflate(inflater, R.layout.dialog_withdraw, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.vm = withdrawViewModel
        initLayout()
        initConfirmBtnClickListener()
    }

    private fun initLayout() {
        val ratio = 0.89
        val layoutParams = requireNotNull(dialog).window!!.attributes
        layoutParams.width = (resources.displayMetrics.widthPixels * ratio).toInt()
        layoutParams.height = WindowManager.LayoutParams.WRAP_CONTENT
        requireNotNull(dialog).window!!.attributes = layoutParams
        isCancelable = true
    }

    private fun initConfirmBtnClickListener() {
        binding.btnWithdrawDialogConfirm.setOnClickListener {
            startActivity(
                Intent(requireActivity(), OnboardingActivity::class.java).apply {
                    addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
                }
            )
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        const val TAG = "WithdrawDialogFragment"
    }
}
