package com.sopt.peekabookaos.presentation.barcodeScanner

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.DisplayMetrics
import android.view.View
import androidx.activity.result.contract.ActivityResultContracts
import androidx.camera.core.AspectRatio
import androidx.camera.core.CameraControl
import androidx.camera.core.CameraInfo
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.Preview
import androidx.camera.core.UseCase
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.google.mlkit.vision.barcode.common.Barcode
import com.sopt.peekabookaos.R
import com.sopt.peekabookaos.databinding.FragmentBarcodeScannerBinding
import com.sopt.peekabookaos.util.binding.BindingFragment
import com.sopt.peekabookaos.util.extensions.repeatOnStarted
import com.sopt.peekabookaos.util.extensions.setSingleOnClickListener
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
class BarcodeScannerFragment :
    BindingFragment<FragmentBarcodeScannerBinding>(R.layout.fragment_barcode_scanner) {
    private val barcodeViewModel: BarcodeViewModel by viewModels()

    private var processingBarcode = AtomicBoolean(false)
    private lateinit var cameraInfo: CameraInfo
    private lateinit var cameraControl: CameraControl
    private val executor by lazy { Executors.newSingleThreadExecutor() }

    private val multiPermissionCallback =
        registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) { map ->
            if (map.entries.isEmpty()) {
                return@registerForActivityResult
            } else {
                binding.pvBarcode.post {
                    startCamera()
                }
            }
        }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initPermissionCallBack()
        collectServerState()
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
        val cameraProviderFuture = ProcessCameraProvider.getInstance(requireContext())
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
            ContextCompat.getMainExecutor(requireContext())
        )
    }

    private fun requestAllPermissions() {
        multiPermissionCallback.launch(REQUIRED_PERMISSIONS)
    }

    private fun allPermissionsGranted() = REQUIRED_PERMISSIONS.all {
        ContextCompat.checkSelfPermission(requireContext(), it) == PackageManager.PERMISSION_GRANTED
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
        if (barcodes.isNotEmpty() && barcodeViewModel.serverState.value == BarcodeState.IDLE) {
            barcodeViewModel.postBarcode(barcodes[0].rawValue!!)
        }
    }

    private fun initCloseBtnClickListener() {
        binding.btnBarcodeClose.setOnClickListener {
            requireActivity().finish()
        }
    }

    private fun initHardDetectedClickListener() {
        binding.llBarcodeHardDetected.setSingleOnClickListener {
            /** LOCATION 키워드로 "create" 보내기 */
            findNavController().navigate(R.id.action_barcodeScannerFragment_to_searchBookFragment)
        }
    }

    private fun collectServerState() {
        repeatOnStarted {
            barcodeViewModel.serverState.collect { uiState ->
                when (uiState) {
                    BarcodeState.SUCCESS -> {
                        /** Book 키워드로 barcodeViewModel.uiState.value[0] 보내기 */
                        /** LOCATION 키워드로 "create" 보내기 */
                        findNavController().navigate(R.id.action_barcodeScannerFragment_to_createBookFragment)
                    }
                    BarcodeState.ERROR -> {
                        BarcodeErrorDialog().show(childFragmentManager, BarcodeErrorDialog.TAG)
                    }
                    BarcodeState.IDLE -> {
                        return@collect
                    }
                }
            }
        }
    }
}
