package com.sopt.peekabookaos.presentation.barcodeScanner

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.DisplayMetrics
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.camera.core.AspectRatio
import androidx.camera.core.CameraControl
import androidx.camera.core.CameraInfo
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.Preview
import androidx.camera.core.UseCase
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.core.content.ContextCompat
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import com.google.mlkit.vision.barcode.common.Barcode
import com.sopt.peekabookaos.R
import com.sopt.peekabookaos.data.entity.Book
import com.sopt.peekabookaos.databinding.ActivityBarcodeScannerBinding
import com.sopt.peekabookaos.presentation.createUpdateBook.CreateUpdateBookActivity
import com.sopt.peekabookaos.presentation.createUpdateBook.CreateUpdateBookActivity.Companion.CREATE
import com.sopt.peekabookaos.presentation.createUpdateBook.CreateUpdateBookActivity.Companion.LOCATION
import com.sopt.peekabookaos.util.binding.BindingActivity
import com.sopt.peekabookaos.util.extensions.ToastMessageUtil
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import timber.log.Timber
import java.util.concurrent.Executors
import java.util.concurrent.atomic.AtomicBoolean
import kotlin.math.abs
import kotlin.math.max
import kotlin.math.min

private val REQUIRED_PERMISSIONS = arrayOf(Manifest.permission.CAMERA)
private const val RATIO_4_3_VALUE = 4.0 / 3.0
private const val RATIO_16_9_VALUE = 16.0 / 9.0
typealias BarcodeAnalyzerListener = (barcode: MutableList<Barcode>) -> Unit

@AndroidEntryPoint
class BarcodeScannerActivity :
    BindingActivity<ActivityBarcodeScannerBinding>(R.layout.activity_barcode_scanner) {
    private val barcodeViewModel: BarcodeViewModel by viewModels()

    private var processingBarcode = AtomicBoolean(false)
    private lateinit var cameraInfo: CameraInfo
    private lateinit var cameraControl: CameraControl
    private val executor by lazy { Executors.newSingleThreadExecutor() }

    private val multiPermissionCallback =
        registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) { map ->
            if (map.entries.isEmpty()) {
                ToastMessageUtil.showToast(
                    this@BarcodeScannerActivity,
                    getString(R.string.barcode_permission)
                )
            } else {
                binding.pvBarcode.post {
                    startCamera()
                }
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initPermissionCallBack()
        initUiStateObserve()
        initCloseBtnClickListener()
    }

    private fun initPermissionCallBack() {
        multiPermissionCallback.launch(REQUIRED_PERMISSIONS)
        if (allPermissionsGranted()) {
            binding.pvBarcode.post {
                startCamera()
            }
        } else {
            requestAllPermissions()
        }
    }

    private fun startCamera() {
        @Suppress("DEPRECATION")
        val metrics =
            DisplayMetrics().also { binding.pvBarcode.display.getRealMetrics(it) }
        val screenAspectRatio = aspectRatio(metrics.widthPixels, metrics.heightPixels)
        val rotation = binding.pvBarcode.display.rotation

        // Bind the CameraProvider to the LifeCycleOwner
        val cameraSelector =
            CameraSelector.Builder().requireLensFacing(CameraSelector.LENS_FACING_BACK).build()
        val cameraProviderFuture = ProcessCameraProvider.getInstance(this)
        cameraProviderFuture.addListener({
            // CameraProvider
            val cameraProvider: ProcessCameraProvider = cameraProviderFuture.get()

            // Preview
            val preview = Preview.Builder().setTargetAspectRatio(screenAspectRatio)
                // Set initial target rotation
                .setTargetRotation(rotation).build()

            preview.setSurfaceProvider(binding.pvBarcode.surfaceProvider)

            // ImageAnalysis
            val textBarcodeAnalyzer = initAnalyzer(screenAspectRatio, rotation)
            cameraProvider.unbindAll()

            try {
                val camera = cameraProvider.bindToLifecycle(
                    this,
                    cameraSelector,
                    preview,
                    textBarcodeAnalyzer
                )
                cameraControl = camera.cameraControl
                cameraInfo = camera.cameraInfo
                cameraControl.setLinearZoom(0.5f)
            } catch (exc: Exception) {
                exc.printStackTrace()
            }
        }, ContextCompat.getMainExecutor(this))
    }

    private fun requestAllPermissions() {
        multiPermissionCallback.launch(REQUIRED_PERMISSIONS)
    }

    private fun allPermissionsGranted() = REQUIRED_PERMISSIONS.all {
        ContextCompat.checkSelfPermission(this, it) == PackageManager.PERMISSION_GRANTED
    }

    private fun onBarcodeDetected(barcodes: List<Barcode>) {
        if (barcodes.isNotEmpty()) {
            barcodeViewModel.initBarcode(barcodes[0].rawValue!!)
        }
    }

    private fun initCloseBtnClickListener() {
        binding.btnBarcodeClose.setOnClickListener {
            finish()
        }
    }

    private fun initUiStateObserve() {
        barcodeViewModel.uiState.flowWithLifecycle(lifecycle).onEach { detected ->
            if (detected) {
                val toCreateBook = Intent(this, CreateUpdateBookActivity::class.java)
                toCreateBook.putExtra(CREATE, bookData)
                toCreateBook.putExtra(LOCATION, CREATE)
                startActivity(toCreateBook)
                finish()
            }
        }.launchIn(lifecycleScope)
    }

    companion object {
        private val bookData = Book(
            bookImage = "http://image.yes24.com/goods/90365124/XL",
            bookTitle = "아무튼, 여름",
            author = "김신회",
            description = "",
            memo = ""
        )
    }
}
