package com.example.bikini_android.ui.board

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.bikini_android.ui.base.BaseViewModel
import com.example.bikini_android.util.etc.Event

class BoardViewModel : BaseViewModel() {

    private val _onPickImageClicked = MutableLiveData<Event<Unit>>()
    val onPickImageClicked: LiveData<Event<Unit>>
        get() = _onPickImageClicked

    private val _onPublishClicked = MutableLiveData<Event<Unit>>()
    val onPublishClicked: LiveData<Event<Unit>>
        get() = _onPublishClicked

    fun pickImageFromGallery() {
        _onPickImageClicked.postValue(Event(Unit))
    }

    fun publishFeed() {
        _onPublishClicked.postValue(Event(Unit))
    }
}