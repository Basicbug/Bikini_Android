package com.example.bikini_android.ui.board

import com.example.bikini_android.ui.base.BaseViewModel
import com.example.bikini_android.util.bus.RxAction
import com.example.bikini_android.util.rx.addTo
import com.jakewharton.rxrelay2.PublishRelay
import com.jakewharton.rxrelay2.Relay
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable

class BoardViewModel : BaseViewModel() {
    val itemEventRelay: Relay<RxAction> = PublishRelay.create()
    val boardItemViewModel = BoardItemViewModel(itemEventRelay)
    val disposables = CompositeDisposable()
    init {
        itemEventRelay
            .ofType(ImageLoadEvent::class.java)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { event ->
                boardItemViewModel.imageUrl = event.imageUri
            }.addTo(disposables)
    }

    override fun onCleared() {
        disposables
        super.onCleared()
    }
}