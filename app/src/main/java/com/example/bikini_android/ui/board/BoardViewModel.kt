package com.example.bikini_android.ui.board

import com.example.bikini_android.network.client.ApiClientHelper
import com.example.bikini_android.network.request.service.FeedService
import com.example.bikini_android.repository.feed.Feed
import com.example.bikini_android.ui.base.BaseViewModel
import com.example.bikini_android.util.bus.RxAction
import com.example.bikini_android.util.bus.event.ImageLoadEvent
import com.example.bikini_android.util.rx.addTo
import com.google.android.gms.maps.model.LatLng
import com.jakewharton.rxrelay2.PublishRelay
import com.jakewharton.rxrelay2.Relay
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class BoardViewModel : BaseViewModel() {
    val itemEventRelay: Relay<RxAction> = PublishRelay.create()
    val boardItemViewModel = BoardItemViewModel(itemEventRelay)

    val disposables = CompositeDisposable()

    init {
        itemEventRelay
            .ofType(ImageLoadEvent::class.java)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { event ->
                boardItemViewModel.imageUrl = event.imageUrl
            }.addTo(disposables)

        itemEventRelay
            .ofType(FeedPostEvent::class.java)
            .subscribe { event ->
                postFeed(event.latLng)
            }.addTo(disposables)
    }

    private fun postFeed(latLng: LatLng) {
        ApiClientHelper.createMainApiByService(FeedService::class)
            .postFeed(Feed(position = latLng))
            .subscribeOn(Schedulers.io())
            .subscribe()
            .addTo(disposables)
    }

    override fun onCleared() {
        disposables.dispose()
        super.onCleared()
    }
}
