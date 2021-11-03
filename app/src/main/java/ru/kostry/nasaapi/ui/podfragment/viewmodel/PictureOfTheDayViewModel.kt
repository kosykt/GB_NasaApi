package ru.kostry.nasaapi.ui.podfragment.viewmodel

import androidx.lifecycle.ViewModel

class PictureOfTheDayViewModel: ViewModel() {

    private var _onMainFAB = false
    val onMainFAB = _onMainFAB

    fun setOnMainFAB(position: Boolean){
        _onMainFAB = position
    }
}