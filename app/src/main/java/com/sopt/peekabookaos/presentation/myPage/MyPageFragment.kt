package com.sopt.peekabookaos.presentation.myPage

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import com.sopt.peekabookaos.R
import com.sopt.peekabookaos.databinding.FragmentMyPageBinding
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
        initWithdrawBtnClickListener()
        initLogoutBtnClickListener()
        initLinkInfoClickListener()
        initLinkPolicyClickListener()
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

    private fun initLinkInfoClickListener() {
        binding.tvMyPageInfo.setOnClickListener {
            var intent = Intent(
                Intent.ACTION_VIEW,
                Uri.parse("https://interesting-door-b57.notion.site/About-Team-b25424073add46b9a186d69c17815bf2")
            )
            startActivity(intent)
        }
    }

    private fun initLinkPolicyClickListener() {
        binding.tvMyPagePolicy.setOnClickListener {
            var intent = Intent(
                Intent.ACTION_VIEW,
                Uri.parse("https://interesting-door-b57.notion.site/d00db57d23414511ad8ec1f2f24c230a")
            )
            startActivity(intent)
        }
    }
    /*private fun initModifyClickListener(){
        binding.ivMyPageEdit.setSingleClickListener {
            //프로필 수정하기가 머지되면 그 화면으로 넘어가기
        }
    }*/
}
