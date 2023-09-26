package com.example.webbookmarker.ui.TakeNote

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MainViewModel : ViewModel() {
    var longPressHandel = MutableLiveData<Boolean>()
    var yAxisPosition = MutableLiveData<Int>()
    var notesVlaue = MutableLiveData<String>()
    var showNoteId = MutableLiveData<Long>()

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

    fun setShowNoteId(value :Long){
        showNoteId.postValue(value)
    }

}