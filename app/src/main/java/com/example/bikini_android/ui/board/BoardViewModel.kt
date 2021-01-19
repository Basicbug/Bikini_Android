package com.example.bikini_android.ui.board

import com.example.bikini_android.network.client.ApiClientHelper
import com.example.bikini_android.network.request.service.FeedService
import com.example.bikini_android.repository.feed.Feed
import com.example.bikini_android.repository.feed.LocationInfo
import com.example.bikini_android.ui.base.BaseViewModel
import com.example.bikini_android.util.bus.RxAction
import com.example.bikini_android.util.logging.Logger
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

    init {
        itemEventRelay
            .ofType(FeedPostEvent::class.java)
            .subscribe { event ->
                postFeed(event.locationInfo)
            }.addTo(disposables)
    }

    fun attachImageSelected(imageUrl: String) {
        boardItemViewModel.imageUrl = imageUrl
    }

    private fun postFeed(locationInfo: LocationInfo) {
        ApiClientHelper.createMainApiByService(FeedService::class)
            .postFeed(Feed(locationInfo = locationInfo))
            .subscribeOn(Schedulers.io())
            .subscribe({
                logger.info { it.toString() }
            }, {
                logger.error { it.toString() }
            })
            .addTo(disposables)
    }

    override fun onCleared() {
        disposables.dispose()
        super.onCleared()
    }
}
