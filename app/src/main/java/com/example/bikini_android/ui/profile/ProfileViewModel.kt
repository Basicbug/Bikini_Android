package com.example.bikini_android.ui.profile

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.bikini_android.ui.base.BaseViewModel
import com.example.bikini_android.util.etc.Event

class ProfileViewModel : BaseViewModel() {

    private val _onMakeFeedClicked = MutableLiveData<Event<Unit>>()
    val onMakeFeedClicked: LiveData<Event<Unit>>
        get() = _onMakeFeedClicked

    fun onMakeFeedClick() {
        _onMakeFeedClicked.postValue(Event(Unit))
    }

}