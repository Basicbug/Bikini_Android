package com.example.bikini_android.ui.board

import android.net.Uri
import androidx.lifecycle.SavedStateHandle
import com.example.bikini_android.R
import com.example.bikini_android.app.ToastHelper
import com.example.bikini_android.manager.login.LoginManagerProxy
import com.example.bikini_android.repository.feed.Feed
import com.example.bikini_android.repository.feed.FeedRepositoryInjector
import com.example.bikini_android.repository.feed.convertLocationInfo
import com.example.bikini_android.ui.base.BaseViewModel
import com.example.bikini_android.ui.progress.ProgressItemViewModel
import com.example.bikini_android.util.bus.RxAction
import com.example.bikini_android.util.bus.RxActionBus
import com.example.bikini_android.util.bus.event.ReloadFeedEvent
import com.example.bikini_android.util.file.FileUtils
import com.example.bikini_android.util.rx.addTo
import com.google.android.gms.maps.model.LatLng
import com.jakewharton.rxrelay2.PublishRelay
import com.jakewharton.rxrelay2.Relay
import io.reactivex.disposables.CompositeDisposable

class BoardViewModel(private val handle: SavedStateHandle) : BaseViewModel() {

    private val feedsRepository = FeedRepositoryInjector.getFeedRepository()
    private var imageUri: Uri? = handle.get<Uri>(KEY_URI)

    val itemEventRelay: Relay<RxAction> = PublishRelay.create()
    val boardItemViewModel =
        BoardItemViewModel(itemEventRelay, handle.get<String>(KEY_CONTENT), imageUri)
    val progressViewModel = ProgressItemViewModel()
    val disposables = CompositeDisposable()

    fun setImageUriSelected(uri: Uri) {
        imageUri = uri
        attachImageSelected(uri.toString())
    }

    private fun attachImageSelected(imageUrl: String) {
        boardItemViewModel.imageUrl = imageUrl
    }

    fun postFeed(postLocation: LatLng) {
        getValidFeedAndImageUrl(postLocation)?.let { (feed, imageUri) ->
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
                    RxActionBus.post(ReloadFeedEvent())
                    itemEventRelay.accept(BoardItemViewModel.EventType.FINISH)
                }
                .addTo(disposables)
        }
    }

    private fun getValidFeedAndImageUrl(postLocation: LatLng): Pair<Feed, Uri>? {
        if (imageUri == null) {
            ToastHelper.show(R.string.image_unselected)
            return null
        }
        val feed = makePostFeed(postLocation).also { feed ->
            if (feed.content.isEmpty()) {
                ToastHelper.show(R.string.content_empty)
                return null
            }
        }
        return Pair(feed, imageUri!!)
    }

    private fun makePostFeed(postLocation: LatLng): Feed {
        return Feed(
            content = boardItemViewModel.content.get() ?: "",
            locationInfo = postLocation.convertLocationInfo(),
            username = LoginManagerProxy.userName
        )
    }

    override fun saveState() {
        handle[KEY_CONTENT] = boardItemViewModel.content.get()
        handle[KEY_URI] = imageUri
    }

    override fun onCleared() {
        disposables.dispose()
        super.onCleared()
    }

    companion object {
        private const val KEY_CONTENT = "keyContent"
        private const val KEY_URI = "keyUri"
    }
}
