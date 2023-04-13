package com.sopt.peekabookaos.presentation.myPage

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import com.sopt.peekabookaos.R
import com.sopt.peekabookaos.databinding.FragmentMyPageBinding
import com.sopt.peekabookaos.presentation.block.BlockedUserActivity
import com.sopt.peekabookaos.presentation.profileModify.ProfileModifyActivity
import com.sopt.peekabookaos.presentation.withdraw.WithdrawActivity
import com.sopt.peekabookaos.util.binding.BindingFragment
import com.sopt.peekabookaos.util.extensions.setSingleOnClickListener
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MyPageFragment : BindingFragment<FragmentMyPageBinding>(R.layout.fragment_my_page) {
    private val myPageViewModel: MyPageViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.vm = myPageViewModel
        initBlockBtnClickListener()
        initWithdrawBtnClickListener()
        initLogoutBtnClickListener()
        initEditClickListener()
        initLinkInfoClickListener()
        initLinkPolicyClickListener()
    }

    private fun initBlockBtnClickListener() {
        binding.tvMyPageBlock.setSingleOnClickListener {
            startActivity(Intent(requireActivity(), BlockedUserActivity::class.java))
        }
    }

    private fun initWithdrawBtnClickListener() {
        binding.tvMyPageWithdraw.setSingleOnClickListener {
            startActivity(Intent(requireActivity(), WithdrawActivity::class.java))
        }
    }

    private fun initLogoutBtnClickListener() {
        binding.tvMyPageLogout.setSingleOnClickListener {
            LogoutDialog().show(childFragmentManager, LogoutDialog.TAG)
        }
    }

    private fun initEditClickListener() {
        binding.ivMyPageEdit.setSingleOnClickListener {
            Intent(requireActivity(), ProfileModifyActivity::class.java).apply {
                putExtra(USER_INFO, myPageViewModel.userData.value)
            }.also { intent ->
                startActivity(intent)
            }
        }
    }

    private fun initLinkInfoClickListener() {
        binding.tvMyPageDeveloperInfo.setOnClickListener {
            startActivity(
                Intent(
                    Intent.ACTION_VIEW,
                    Uri.parse(getString(R.string.my_page_developer_info_link))
                )
            )
        }
    }

    private fun initLinkPolicyClickListener() {
        binding.tvMyPagePolicy.setOnClickListener {
            startActivity(
                Intent(Intent.ACTION_VIEW, Uri.parse(getString(R.string.my_page_policy_link)))
            )
        }
    }

    companion object {
        const val USER_INFO = "user_info"
    }
}
