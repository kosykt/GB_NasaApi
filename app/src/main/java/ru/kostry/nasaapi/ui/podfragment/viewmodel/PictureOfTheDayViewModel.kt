package ru.kostry.nasaapi.ui.podfragment.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class PictureOfTheDayViewModel: ViewModel() {

    private var _onMainFAB = MutableLiveData<Boolean>()
    val onMainFAB: LiveData<Boolean> = _onMainFAB

    fun setOnMainFAB(position: Boolean){
        _onMainFAB.value = position
    }
}