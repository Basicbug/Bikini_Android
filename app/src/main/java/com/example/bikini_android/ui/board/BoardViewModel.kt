package com.example.bikini_android.ui.board

import com.example.bikini_android.network.client.ApiClientHelper
import com.example.bikini_android.network.request.service.FeedService
import com.example.bikini_android.repository.feed.Feed
import com.example.bikini_android.ui.base.BaseViewModel
import com.example.bikini_android.util.bus.RxAction
import com.example.bikini_android.util.logging.Logger
import com.example.bikini_android.util.map.LocationUtils
import com.example.bikini_android.util.rx.addTo
import com.jakewharton.rxrelay2.PublishRelay
import com.jakewharton.rxrelay2.Relay
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class BoardViewModel : BaseViewModel() {
    private val logger = Logger().apply {
        TAG = this@BoardViewModel.javaClass.simpleName
    }
    val itemEventRelay: Relay<RxAction> = PublishRelay.create()
    val boardItemViewModel = BoardItemViewModel(itemEventRelay)

    val disposables = CompositeDisposable()

    fun attachImageSelected(imageUrl: String) {
        boardItemViewModel.imageUrl = imageUrl
    }

    fun postFeed() {
        ApiClientHelper.createMainApiByService(FeedService::class)
            .postFeed(makePostFeed())
            .subscribeOn(Schedulers.io())
            .subscribe({
                logger.info { it.toString() }
            }, {
                logger.error { it.toString() }
            })
            .addTo(disposables)
    }

    private fun makePostFeed(): Feed {
        return Feed(
            content = boardItemViewModel.content.get() ?: "",
            imageUrl = boardItemViewModel.imageUrl,
            locationInfo = LocationUtils.getCurrentLocationInfo()
        )
    }

    override fun onCleared() {
        disposables.dispose()
        super.onCleared()
    }
}
