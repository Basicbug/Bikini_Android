package com.example.bikini_android.ui.board

import android.Manifest
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import androidx.core.content.FileProvider
import androidx.core.net.toUri
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.example.bikini_android.R
import com.example.bikini_android.app.ToastHelper
import com.example.bikini_android.databinding.ActivityBoardBinding
import com.example.bikini_android.ui.base.BaseActivity
import com.example.bikini_android.ui.base.BaseViewModel
import com.example.bikini_android.ui.dialog.SelectImageMethodBottomSheet
import com.example.bikini_android.ui.dialog.SelectImageMethodViewModel
import com.example.bikini_android.util.bus.RxAction
import com.example.bikini_android.util.bus.RxActionBus
import com.example.bikini_android.util.bus.event.CameraAndExternalReadAndWriteStoragePermissionEvent
import com.example.bikini_android.util.bus.event.ExternalReadAndWriteStoragePermissionEvent
import com.example.bikini_android.util.camera.CameraUtils
import com.example.bikini_android.util.file.FileUtils
import com.example.bikini_android.util.permission.PermissionUtils
import com.example.bikini_android.util.rx.addTo
import com.jakewharton.rxrelay2.Relay
import io.reactivex.android.schedulers.AndroidSchedulers
import java.io.File

class BoardActivity : BaseActivity() {

    lateinit var binding: ActivityBoardBinding
    private lateinit var viewModel: BoardViewModel
    private lateinit var itemEventRelay: Relay<RxAction>
    private lateinit var viewModels: List<BaseViewModel>
    private lateinit var boardMapViewModel: BoardMapViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_board)
        viewModels = BoardViewModelsHelper.getViewModels(this, savedInstanceState)
        viewModel = ViewModelProvider(this)[BoardViewModel::class.java]
        boardMapViewModel = ViewModelProvider(
            this,
            BoardViewModelFactoryProvider(this, savedInstanceState)
        )[BoardMapViewModel::class.java]

        itemEventRelay = viewModel.itemEventRelay
        binding.apply {
            viewmodel = viewModel
        }
        setUpObservers()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (requestCode) {
            REQUEST_CODE_GALLERY_PAGE -> {
                data?.let { intent ->
                    val imageUrl = intent.data
                    imageUrl?.let {
                        viewModel.setImageUriSelected(it)
                        ToastHelper.show(R.string.recommend_positioning)
                    }
                }
            }
            REQUEST_CODE_CAMERA_PAGE -> {
                FileUtils.getCameraImageFile()?.let { file ->
                    file.toUri().let {
                        viewModel.setImageUriSelected(it)
                        ToastHelper.show(R.string.recommend_positioning)
                    }
                }
            }
        }
    }

    private fun setUpObservers() {
        itemEventRelay
            .ofType(BoardViewModel.EventType::class.java)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                when (it) {
                    BoardViewModel.EventType.INVALID_CONTENT -> {
                        ToastHelper.show(R.string.content_empty)
                    }
                    BoardViewModel.EventType.INVALID_IMAGE -> {
                        ToastHelper.show(R.string.image_unselected)
                    }
                    else -> Unit
                }
            }.addTo(disposables)
        itemEventRelay
            .ofType(BoardItemViewModel.EventType::class.java)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                when (it) {
                    BoardItemViewModel.EventType.POST_FEED -> {
                        viewModel.postFeed(boardMapViewModel.marker.position)
                    }
                    BoardItemViewModel.EventType.NAVIGATE_SELECT_IMAGE_METHOD -> {
                        navigateToSelectImageMethod()
                    }
                    BoardItemViewModel.EventType.FINISH -> {
                        finish()
                    }
                    else -> Unit
                }
            }.addTo(disposables)

        itemEventRelay
            .ofType(SelectImageMethodViewModel.EventType::class.java)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                when (it) {
                    SelectImageMethodViewModel.EventType.NAVIGATE_CAMERA -> {
                        navigateToCamera()
                    }
                    SelectImageMethodViewModel.EventType.NAVIGATE_GALLERY -> {
                        navigateToGallery()
                    }
                    else -> Unit
                }
            }.addTo(disposables)

        RxActionBus.toObservable(ExternalReadAndWriteStoragePermissionEvent::class.java)
            .subscribe {
                if (it.isAccept) {
                    startGalleryPage()
                }
            }.addTo(disposables)

        RxActionBus.toObservable(CameraAndExternalReadAndWriteStoragePermissionEvent::class.java)
            .subscribe {
                if (it.isAccept) {
                    startCameraPage()
                }
            }.addTo(disposables)
    }

    private fun navigateToSelectImageMethod() {
        SelectImageMethodBottomSheet(itemEventRelay).show(supportFragmentManager, null)
    }

    private fun navigateToCamera() {
        if (FileUtils.checkReadExternalStoragePermission() && CameraUtils.checkCameraPermission()) {
            startCameraPage()
        } else {
            checkCameraPermission()
        }
    }

    private fun navigateToGallery() {
        if (FileUtils.checkReadExternalStoragePermission()) {
            startGalleryPage()
        } else {
            checkGalleryPermission()
        }
    }

    private fun startCameraPage() {
        Intent(MediaStore.ACTION_IMAGE_CAPTURE).also { takePictureIntent ->
            takePictureIntent.resolveActivity(packageManager)?.also {
                val photoFile: File = FileUtils.createCameraImageFile()
                photoFile.also {
                    val photoURI: Uri = FileProvider.getUriForFile(
                        this,
                        "com.example.android.fileprovider",
                        it
                    )
                    takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI)
                    startActivityForResult(takePictureIntent, REQUEST_CODE_CAMERA_PAGE)
                }
            }
        }
    }

    private fun startGalleryPage() {
        val galleryIntent = Intent(Intent.ACTION_PICK).apply {
            type = "image/*"
        }
        startActivityForResult(galleryIntent, REQUEST_CODE_GALLERY_PAGE)
    }

    private fun checkCameraPermission() {
        PermissionUtils.requestPermission(
            this,
            PermissionUtils.CAMERA_READ_AND_EXTERNAL_STORAGE_PERMISSION_REQUEST_CODE,
            arrayOf(
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.CAMERA
            ),
            true
        )
    }

    private fun checkGalleryPermission() {
        PermissionUtils.requestPermission(
            this,
            PermissionUtils.READ_AND_EXTERNAL_STORAGE_PERMISSION_REQUEST_CODE,
            arrayOf(
                Manifest.permission.READ_EXTERNAL_STORAGE
            ),
            true
        )
    }

    override fun onBackPressed() {
        if (viewModel.progressViewModel.isVisible)
            return
        super.onBackPressed()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        BoardViewModelsHelper.saveState(viewModels)
        super.onSaveInstanceState(outState)
    }

    companion object {
        private const val REQUEST_CODE_GALLERY_PAGE = 1
        private const val REQUEST_CODE_CAMERA_PAGE = 2
    }
}
