package com.sopt.peekabookaos.presentation.notificationBookshelf.follow

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.DialogFragment
import com.sopt.peekabookaos.R
import com.sopt.peekabookaos.databinding.DialogFollowBinding
import com.sopt.peekabookaos.presentation.main.MainActivity

class FollowDialog : DialogFragment() {
    private var _binding: DialogFollowBinding? = null
    private val binding get() = _binding ?: error(getString(R.string.binding_error))
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding =
            DataBindingUtil.inflate(inflater, R.layout.dialog_follow, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initLayout()
        initFollowDialogContent()
        initFollowBtnClickListener()
    }

    private fun initFollowDialogContent() {
        val nickname = arguments?.getString(NICKNAME) ?: ""
        binding.tvDialogFollowTitle.text =
            getString(R.string.notification_bookshelf_dialog_follow_content, nickname)
    }

    private fun initLayout() {
        val ratio = 0.89
        val layoutParams = requireNotNull(dialog).window!!.attributes
        layoutParams.width = (resources.displayMetrics.widthPixels * ratio).toInt()
        layoutParams.height = WindowManager.LayoutParams.WRAP_CONTENT
        requireNotNull(dialog).window!!.attributes = layoutParams
        isCancelable = true
    }

    private fun initFollowBtnClickListener() {
        binding.btnDialogFollowConfirm.setOnClickListener {
            moveToMain()
        }
    }

    private fun moveToMain() {
        val intent = Intent(requireContext(), MainActivity::class.java).apply {
            addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP)
        }
        startActivity(intent)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        const val TAG = "FollowDialogFragment"
        const val NICKNAME = "nickname"
    }
}
