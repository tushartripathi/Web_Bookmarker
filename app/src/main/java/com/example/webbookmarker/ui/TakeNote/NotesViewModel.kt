package com.example.webbookmarker.ui.TakeNote

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.webbookmarker.Room.AppDatabase
import com.example.webbookmarker.Room.Notes.NotesEntity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class NotesViewModel : ViewModel() {
    var notes = MutableLiveData<List<NotesEntity>>()
    suspend fun getValues(url:String, appDatabase:AppDatabase)
    {
      withContext(Dispatchers.IO) {
          notes.postValue(appDatabase.noteDao()?.getAllNotes(url))
      }
    }



}