package com.example.bikini_android.ui.board

import android.net.Uri
import com.example.bikini_android.app.AppResources
import com.example.bikini_android.repository.feed.Feed
import com.example.bikini_android.repository.feed.FeedRepositoryInjector
import com.example.bikini_android.ui.base.BaseViewModel
import com.example.bikini_android.util.bus.RxAction
import com.example.bikini_android.util.file.FileUtils
import com.example.bikini_android.util.logging.Logger
import com.example.bikini_android.util.map.LocationUtils
import com.example.bikini_android.util.rx.addTo
import com.jakewharton.rxrelay2.PublishRelay
import com.jakewharton.rxrelay2.Relay
import io.reactivex.disposables.CompositeDisposable
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.File

class BoardViewModel : BaseViewModel() {
    private val logger = Logger().apply {
        TAG = this@BoardViewModel.javaClass.simpleName
    }
    val itemEventRelay: Relay<RxAction> = PublishRelay.create()
    val boardItemViewModel = BoardItemViewModel(itemEventRelay)

    val disposables = CompositeDisposable()
    private val feedsRepository = FeedRepositoryInjector.getFeedRepositoryImpl()
    private var imageUri: Uri? = null
    fun setImageUriSelected(uri: Uri) {
        imageUri = uri
        attachImageSelected(uri.toString())
    }

    private fun attachImageSelected(imageUrl: String) {
        boardItemViewModel.imageUrl = imageUrl
    }

    fun postFeed() {
        imageUri?.let {
            val imageFile = File(FileUtils.getPath(it, AppResources.getContext()))
            feedsRepository.addFeedToRemote(
                makePostFeed(),
                listOf(
                    MultipartBody.Part.createFormData(
                        "images", "photo.jpg", RequestBody.create(
                            MediaType.parse("image/jpeg"), imageFile
                        )
                    )
                )
            )
                .subscribe({
                    logger.info { it.toString() }
                }, {
                    logger.error { it.toString() }
                })
                .addTo(disposables)

        }
        //이미지 선택 토스트
    }

    private fun makePostFeed(): Feed {
        return Feed(
            content = boardItemViewModel.content.get() ?: "",
            locationInfo = LocationUtils.getCurrentLocationInfo()
        )
    }

    override fun onCleared() {
        disposables.dispose()
        super.onCleared()
    }
}
