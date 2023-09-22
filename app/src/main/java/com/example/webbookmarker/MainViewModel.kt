package com.example.webbookmarker

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MainViewModel : ViewModel() {
    var longPressHandel = MutableLiveData<Boolean>()
    var yAxisPosition = MutableLiveData<Int>()
    var notesVlaue = MutableLiveData<String>()

    fun setLongPressValue(b:Boolean)
    {
        longPressHandel.postValue(b)
    }
    fun setYazis(value:Int)
    {
        yAxisPosition.postValue(value)
    }
    fun setNotesValue(value:String)
    {
        notesVlaue.postValue(value)
    }

}