package com.example.bikini_android.ui.board

import android.content.Intent
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.example.bikini_android.R
import com.example.bikini_android.databinding.ActivityBoardBinding
import com.example.bikini_android.ui.base.BaseActivity
import com.example.bikini_android.util.bus.RxAction
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

        if (requestCode == PICK_IMAGE) {

            data?.let {intent->
                val imageUri = intent.data
                imageUri?.let {
                    itemEventRelay.accept(ImageLoadEvent(it.toString()))
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
                        finish()
                    }
                    BoardItemViewModel.EventType.NAVIGATE_GALLERY ->
                        navigateToGallery()
                    else -> Unit
                }
            }.addTo(disposables)
    }

    private fun navigateToGallery() {
        val galleryIntent = Intent(Intent.ACTION_PICK).apply {
            type = "image/*"
        }
        startActivityForResult(galleryIntent, PICK_IMAGE)
    }

    companion object {
        private const val PICK_IMAGE = 1
    }
}