package com.sopt.peekabookaos.presentation.myPage

import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.view.View
import androidx.fragment.app.viewModels
import com.google.android.gms.oss.licenses.OssLicensesMenuActivity
import com.sopt.peekabookaos.R
import com.sopt.peekabookaos.databinding.FragmentMyPageBinding
import com.sopt.peekabookaos.presentation.block.BlockedUserActivity
import com.sopt.peekabookaos.presentation.profileModify.ProfileModifyActivity
import com.sopt.peekabookaos.presentation.withdraw.WithdrawActivity
import com.sopt.peekabookaos.util.ToastMessageUtil.showToast
import com.sopt.peekabookaos.util.binding.BindingFragment
import com.sopt.peekabookaos.util.extensions.setSingleOnClickListener
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MyPageFragment : BindingFragment<FragmentMyPageBinding>(R.layout.fragment_my_page) {
    private val myPageViewModel: MyPageViewModel by viewModels()
    private val notificationPermissionRequestCode = 101

    override fun onResume() {
        super.onResume()
        myPageViewModel.getMyPage()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.viewModel = myPageViewModel
        initNotificationBtnClickListener()
        initBlockBtnClickListener()
        initWithdrawBtnClickListener()
        initLogoutBtnClickListener()
        initModifyClickListener()
        initLinkInfoClickListener()
        initLinkAskClickListener()
        initLinkPolicyClickListener()
        initOpenSourceClickListener()
    }

    private fun initNotificationBtnClickListener() {
        binding.tvMyPageNotification.setSingleOnClickListener {
            val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS).apply {
                data = Uri.fromParts("package", requireContext().packageName, null)
            }
            requireContext().startActivity(intent)
        }
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

    private fun initModifyClickListener() {
        binding.btnMyPageEdit.setSingleOnClickListener {
            if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.P) {
                showToast(requireContext(), getString(R.string.my_page_28_donot_modify))
            } else {
                Intent(requireActivity(), ProfileModifyActivity::class.java).apply {
                    putExtra(USER_INFO, myPageViewModel.user.value)
                }.also { intent ->
                    startActivity(intent)
                }
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

    private fun initLinkAskClickListener() {
        binding.tvMyPageAsk.setOnClickListener {
            startActivity(
                Intent(Intent.ACTION_VIEW, Uri.parse(getString(R.string.my_page_ask_link)))
            )
        }
    }

    private fun initOpenSourceClickListener() {
        binding.tvMyPageOpenSourceInfo.setSingleOnClickListener {
            startActivity(Intent(requireActivity(), OssLicensesMenuActivity::class.java))
            OssLicensesMenuActivity.setActivityTitle(getString(R.string.my_page_open_source))
        }
    }

    companion object {
        const val USER_INFO = "user_info"
    }
}
