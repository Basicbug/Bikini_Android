package com.example.bikini_android.ui.board

import android.Manifest
import android.content.Intent
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.example.bikini_android.R
import com.example.bikini_android.databinding.ActivityBoardBinding
import com.example.bikini_android.ui.base.BaseActivity
import com.example.bikini_android.util.bus.RxAction
import com.example.bikini_android.util.bus.RxActionBus
import com.example.bikini_android.util.bus.event.ReadExternalStoragePermissionEvent
import com.example.bikini_android.util.file.FileUtils
import com.example.bikini_android.util.permission.PermissionUtils
import com.example.bikini_android.util.rx.addTo
import com.jakewharton.rxrelay2.Relay
import io.reactivex.android.schedulers.AndroidSchedulers

class BoardActivity : BaseActivity() {

    lateinit var binding: ActivityBoardBinding
    private lateinit var viewModel: BoardViewModel
    private lateinit var itemEventRelay: Relay<RxAction>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_board)
        viewModel = ViewModelProvider(this)[BoardViewModel::class.java]
        itemEventRelay = viewModel.itemEventRelay
        binding.apply {
            viewmodel = viewModel.boardItemViewModel
        }
        setUpObservers()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == REQUEST_CODE_GALLERY_PAGE) {

            data?.let { intent ->
                val imageUrl = intent.data
                imageUrl?.let {
                    viewModel.setImageUriSelected(it)
                }
            }
        }
    }

    private fun setUpObservers() {
        itemEventRelay
            .ofType(BoardItemViewModel.EventType::class.java)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                when (it) {
                    BoardItemViewModel.EventType.POST_FEED -> {
                        viewModel.postFeed()
                    }
                    BoardItemViewModel.EventType.NAVIGATE_GALLERY -> {
                        navigateToGallery()
                    }
                    BoardItemViewModel.EventType.FINISH -> {
                        finish()
                    }
                    else -> Unit
                }
            }.addTo(disposables)
        RxActionBus.toObservable(ReadExternalStoragePermissionEvent::class.java).subscribe {
            if (it.isAccept) {
                startGalleryPage()
            }
        }.addTo(disposables)
    }

    private fun navigateToGallery() {
        if (FileUtils.checkReadExternalStoragePermission()) {
            startGalleryPage()
        } else {
            PermissionUtils.requestPermission(
                this,
                PermissionUtils.READ_EXTERNAL_STORAGE_PERMISSION_REQUEST_CODE,
                Manifest.permission.READ_EXTERNAL_STORAGE, true
            )
        }
    }

    private fun startGalleryPage() {
        val galleryIntent = Intent(Intent.ACTION_PICK).apply {
            type = "image/*"
        }
        startActivityForResult(galleryIntent, REQUEST_CODE_GALLERY_PAGE)
    }

    companion object {
        private const val REQUEST_CODE_GALLERY_PAGE = 1
    }
}
