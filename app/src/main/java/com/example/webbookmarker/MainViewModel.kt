package com.example.webbookmarker

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MainViewModel : ViewModel() {
    var longPressHandel = MutableLiveData<Boolean>()
    var yAxisPosition = MutableLiveData<Int>()

    fun setLongPressValue(b:Boolean)
    {
        longPressHandel.postValue(b)
    }
    fun setYazis(value:Int)
    {
        yAxisPosition.postValue(value)
    }

}