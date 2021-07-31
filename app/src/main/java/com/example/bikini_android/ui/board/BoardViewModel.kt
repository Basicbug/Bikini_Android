package com.example.bikini_android.ui.board

import android.net.Uri
import com.example.bikini_android.R
import com.example.bikini_android.app.TEST_USER_ID
import com.example.bikini_android.app.ToastHelper
import com.example.bikini_android.repository.feed.Feed
import com.example.bikini_android.repository.feed.FeedRepositoryInjector
import com.example.bikini_android.ui.base.BaseViewModel
import com.example.bikini_android.ui.progress.ProgressItemViewModel
import com.example.bikini_android.util.bus.RxAction
import com.example.bikini_android.util.bus.RxActionBus
import com.example.bikini_android.util.bus.event.RefreshFeedEvent
import com.example.bikini_android.util.file.FileUtils
import com.example.bikini_android.util.map.LocationUtils
import com.example.bikini_android.util.rx.addTo
import com.jakewharton.rxrelay2.PublishRelay
import com.jakewharton.rxrelay2.Relay
import io.reactivex.disposables.CompositeDisposable

class BoardViewModel : BaseViewModel() {

    val itemEventRelay: Relay<RxAction> = PublishRelay.create()
    val boardItemViewModel = BoardItemViewModel(itemEventRelay)
    val progressViewModel = ProgressItemViewModel()
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
        getValidFeedAndImageUrl()?.let { (feed, imageUri) ->
            progressViewModel.isVisible = true
            feedsRepository
                .addFeedToRemote(
                    feed,
                    FileUtils.getImageMultiParts(listOf(imageUri))
                )
                .doOnError {
                    progressViewModel.isVisible = false
                }
                .subscribe { _ ->
                    progressViewModel.isVisible = false
                    RxActionBus.post(RefreshFeedEvent())
                    itemEventRelay.accept(BoardItemViewModel.EventType.FINISH)
                }
                .addTo(disposables)
        }
    }

    private fun getValidFeedAndImageUrl(): Pair<Feed, Uri>? {
        if (imageUri == null) {
            ToastHelper.show(R.string.image_unselected)
            return null
        }
        val feed = makePostFeed().also { feed ->
            if (feed.content.isEmpty()) {
                ToastHelper.show(R.string.content_empty)
                return null
            }
        }
        return Pair(feed, imageUri!!)
    }

    private fun makePostFeed(): Feed {
        return Feed(
            content = boardItemViewModel.content.get() ?: "",
            locationInfo = LocationUtils.getCurrentLocationInfo(),
            username = TEST_USER_ID
        )
    }

    override fun onCleared() {
        disposables.dispose()
        super.onCleared()
    }
}
