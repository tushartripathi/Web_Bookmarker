package com.example.webbookmarker

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MainViewModel : ViewModel() {
    var longPressHandel = MutableLiveData<Boolean>()

    fun setLongPressValue(b:Boolean)
    {
        longPressHandel.postValue(b)
    }

    fun getLongPressValue(): Boolean?
    {
        return longPressHandel.value?:false
    }

}