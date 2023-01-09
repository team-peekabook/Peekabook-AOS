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
import com.google.mlkit.vision.barcode.common.Barcode
import com.sopt.peekabookaos.R
import com.sopt.peekabookaos.databinding.ActivityBarcodeScannerBinding
import com.sopt.peekabookaos.presentation.createUpdateBook.CreateUpdateBookActivity
import com.sopt.peekabookaos.presentation.createUpdateBook.CreateUpdateBookActivity.Companion.CREATE
import com.sopt.peekabookaos.presentation.createUpdateBook.CreateUpdateBookActivity.Companion.LOCATION
import com.sopt.peekabookaos.presentation.search.book.SearchBookActivity
import com.sopt.peekabookaos.util.binding.BindingActivity
import com.sopt.peekabookaos.util.extensions.ToastMessageUtil
import com.sopt.peekabookaos.util.extensions.repeatOnStarted
import dagger.hilt.android.AndroidEntryPoint
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
        collectServerStatusState()
        initCloseBtnClickListener()
        initHardDetectedClickListener()
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

        val cameraSelector =
            CameraSelector.Builder().requireLensFacing(CameraSelector.LENS_FACING_BACK).build()
        val cameraProviderFuture = ProcessCameraProvider.getInstance(this)
        cameraProviderFuture.addListener(
            {
                val cameraProvider: ProcessCameraProvider = cameraProviderFuture.get()

                val preview = Preview.Builder().setTargetAspectRatio(screenAspectRatio)
                    .setTargetRotation(rotation).build()

                preview.setSurfaceProvider(binding.pvBarcode.surfaceProvider)

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
            },
            ContextCompat.getMainExecutor(this)
        )
    }

    private fun requestAllPermissions() {
        multiPermissionCallback.launch(REQUIRED_PERMISSIONS)
    }

    private fun allPermissionsGranted() = REQUIRED_PERMISSIONS.all {
        ContextCompat.checkSelfPermission(this, it) == PackageManager.PERMISSION_GRANTED
    }

    private fun aspectRatio(width: Int, height: Int): Int {
        val previewRatio = max(width, height).toDouble() / min(width, height)
        if (abs(previewRatio - RATIO_4_3_VALUE) <= abs(previewRatio - RATIO_16_9_VALUE)) {
            return AspectRatio.RATIO_4_3
        }
        return AspectRatio.RATIO_16_9
    }

    private fun initAnalyzer(screenAspectRatio: Int, rotation: Int): UseCase {
        return ImageAnalysis.Builder().setTargetAspectRatio(screenAspectRatio)
            .setBackpressureStrategy(ImageAnalysis.STRATEGY_KEEP_ONLY_LATEST)
            .setTargetRotation(rotation).build().also {
                it.setAnalyzer(
                    executor,
                    BarcodeAnalyser { barcode ->
                        if (processingBarcode.compareAndSet(false, false)) {
                            onBarcodeDetected(barcode)
                        }
                    }
                )
            }
    }

    private fun onBarcodeDetected(barcodes: List<Barcode>) {
        if (barcodes.isNotEmpty() && barcodeViewModel.serverStatus.value !is BarcodeState.SUCCESS) {
            barcodeViewModel.postBarcode(barcodes[0].rawValue!!)
        }
    }

    private fun initCloseBtnClickListener() {
        binding.btnBarcodeClose.setOnClickListener {
            finish()
        }
    }

    private fun initHardDetectedClickListener() {
        binding.llBarcodeHardDetected.setOnClickListener {
            Intent(this, SearchBookActivity::class.java).apply {
                putExtra(LOCATION, CREATE)
            }.also { intent ->
                startActivity(intent)
                finish()
            }
        }
    }

    private fun collectServerStatusState() {
        repeatOnStarted {
            barcodeViewModel.serverStatus.collect { uiState ->
                when (uiState) {
                    BarcodeState.SUCCESS -> {
                        Intent(this, CreateUpdateBookActivity::class.java).apply {
                            putExtra(CREATE, barcodeViewModel.uiState.value)
                            putExtra(LOCATION, CREATE)
                        }.also { intent ->
                            startActivity(intent)
                            finish()
                        }
                    }

                    BarcodeState.ERROR -> {
                        /* 에러 다이얼로그 구현 */
                    }

                    BarcodeState.IDLE -> {
                        return@collect
                    }
                }
            }
        }
    }
}
