package com.sopt.peekabookaos.presentation.main

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import androidx.activity.addCallback
import androidx.activity.viewModels
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.sopt.peekabookaos.R
import com.sopt.peekabookaos.databinding.ActivityMainBinding
import com.sopt.peekabookaos.util.binding.BindingActivity
import com.sopt.peekabookaos.util.extensions.initBackPressedCallback
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

@AndroidEntryPoint
class MainActivity : BindingActivity<ActivityMainBinding>(R.layout.activity_main) {
    private val mainViewModel: MainViewModel by viewModels()
    private val notificationPermissionRequestCode = 101

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.vm = mainViewModel
        initBottomNavigationView()
        clickBackPressedButton()
        checkAndRequestNotificationPermission()
    }

    private fun checkAndRequestNotificationPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (ContextCompat.checkSelfPermission(
                    this,
                    Manifest.permission.POST_NOTIFICATIONS
                ) == PackageManager.PERMISSION_DENIED
            ) {
                ActivityCompat.requestPermissions(
                    this,
                    arrayOf(Manifest.permission.POST_NOTIFICATIONS),
                    notificationPermissionRequestCode
                )
            }
        }
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

    private fun clickBackPressedButton() {
        onBackPressedDispatcher.addCallback {
            val fragmentId: Int? = findNavController(R.id.fcv_main).currentDestination?.id
            fragmentId?.also { id ->
                if (id == R.id.bookshelfFragment) {
                    initBackPressedCallback()
                }
            } ?: Timber.e(getString(R.string.null_point_exception))
        }
    }
}
