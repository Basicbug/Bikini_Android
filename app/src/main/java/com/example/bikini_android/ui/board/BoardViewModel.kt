package com.example.bikini_android.ui.board

import android.net.Uri
import androidx.lifecycle.SavedStateHandle
import com.basicbug.core.rx.SchedulerProvider
import com.basicbug.core.rx.addTo
import com.basicbug.core.util.bus.RxAction
import com.basicbug.core.util.bus.RxActionBus
import com.example.bikini_android.manager.login.LoginManagerProxy
import com.example.bikini_android.repository.feed.Feed
import com.example.bikini_android.repository.feed.FeedRepository
import com.example.bikini_android.repository.feed.convertLocationInfo
import com.basicbug.core.ui.base.BaseViewModel
import com.basicbug.core.ui.progress.ProgressItemViewModel
import com.example.bikini_android.util.bus.event.ReloadFeedEvent
import com.example.bikini_android.util.file.FileUtilsImpl
import com.google.android.gms.maps.model.LatLng
import com.jakewharton.rxrelay2.PublishRelay
import com.jakewharton.rxrelay2.Relay
import io.reactivex.disposables.CompositeDisposable

class BoardViewModel(
    private val handle: SavedStateHandle,
    private val feedsRepository: FeedRepository,
    private val loginManager: LoginManagerProxy,
    private val fileUtils: FileUtilsImpl,
    private val schedulerProvider: SchedulerProvider
) : BaseViewModel() {

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
                    fileUtils.getImageMultiParts(listOf(imageUri))
                )
                .doOnError {
                    progressViewModel.isVisible = false
                }
                .observeOn(schedulerProvider.main())
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
            itemEventRelay.accept(EventType.INVALID_IMAGE)
            return null
        }
        val feed = makePostFeed(postLocation).also { feed ->
            if (feed.content.isEmpty()) {
                itemEventRelay.accept(EventType.INVALID_CONTENT)
                return null
            }
        }
        return Pair(feed, imageUri!!)
    }

    private fun makePostFeed(postLocation: LatLng): Feed {
        return Feed(
            content = boardItemViewModel.content.get() ?: "",
            locationInfo = postLocation.convertLocationInfo(),
            username = loginManager.userName
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

    enum class EventType : RxAction {
        INVALID_IMAGE, INVALID_CONTENT, ;
    }

    companion object {
        private const val KEY_CONTENT = "keyContent"
        private const val KEY_URI = "keyUri"
    }
}
