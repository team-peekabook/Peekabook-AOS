package com.sopt.peekabookaos.presentation.main

import android.os.Bundle
import androidx.activity.addCallback
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.sopt.peekabookaos.R
import com.sopt.peekabookaos.databinding.ActivityMainBinding
import com.sopt.peekabookaos.util.binding.BindingActivity
import com.sopt.peekabookaos.util.extensions.ToastMessageUtil
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber
import kotlin.system.exitProcess

@AndroidEntryPoint
class MainActivity : BindingActivity<ActivityMainBinding>(R.layout.activity_main) {
    private var onBackPressedTime = 0L
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initBottomNavigationView()
        initBackPressedCallback()
    }

    private fun initBottomNavigationView() {
        val navController = findNavController()
        binding.bnvMain.setupWithNavController(navController)
    }

    private fun findNavController(): NavController {
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.fcv_main) as NavHostFragment
        return navHostFragment.navController
    }

    private fun initBackPressedCallback() {
        onBackPressedDispatcher.addCallback {
            val fragmentId: Int? = findNavController(R.id.fcv_main).currentDestination?.id
            fragmentId?.also { id ->
                if (id == R.id.bookshelfFragment) {
                    val curTime = System.currentTimeMillis()
                    val gap = curTime - onBackPressedTime
                    if (gap > WAITING_DEADLINE) {
                        onBackPressedTime = curTime
                        ToastMessageUtil.showToast(
                            this@MainActivity,
                            getString(R.string.finish_app_toast_msg)
                        )
                        return@addCallback
                    }
                    finishAffinity()
                    System.runFinalization()
                    exitProcess(0)
                }
            } ?: Timber.e(getString(R.string.null_point_exception))
        }
    }

    companion object {
        private const val WAITING_DEADLINE = 2000L
    }
}
