package com.example.webbookmarker.ui.TakeNote

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.MotionEvent
import android.view.View.OnTouchListener
import android.webkit.JavascriptInterface
import android.webkit.WebChromeClient
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebView.setWebContentsDebuggingEnabled
import android.webkit.WebViewClient
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.room.Room.databaseBuilder
import com.example.webbookmarker.ui.Fragments.AddNoteBottomSheet
import com.example.webbookmarker.Room.AppDatabase
import com.example.webbookmarker.Room.Migration.MigrationPath
import com.example.webbookmarker.Room.Notes.NotesEntity
import com.example.webbookmarker.Web.JavaScriptInjection
import com.example.webbookmarker.databinding.ActivityMainBinding
import com.example.webbookmarker.ui.Fragments.ReadNoteBottomSheet
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext


class MainActivity : AppCompatActivity() {

    lateinit var binding : ActivityMainBinding
    val viewModel: MainViewModel by lazy {
        ViewModelProvider(this).get(MainViewModel::class.java)
    }
    val dbViewModel: NotesViewModel by lazy {
        ViewModelProvider(this).get(NotesViewModel::class.java)
    }
    val appDatabase: AppDatabase by lazy {
        databaseBuilder(
            applicationContext,
            AppDatabase::class.java, "notes_database"
        ).addMigrations(MigrationPath.migration1to2).build()
    }

    var url :String? = null

    var longPressHandled = false
    var yAxisPosition =0
    @SuppressLint("ClickableViewAccessibility")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        getIntentValeus()
        val webSettings: WebSettings = binding.mainWebViewId.getSettings()
        webSettings.javaScriptEnabled = true
        setUrl()

        viewModelOperations()

        binding.mainWebViewId.setOnTouchListener { _, event ->
            Log.d("asdfasdasdf","event foudn y = ${event.y}")
            Log.d("asdfasdasdf","event foudn raw y = ${event.rawY}")

            yAxisPosition = event.rawY.toInt() // Y-coordinate of the touch event
            Toast.makeText(this,yAxisPosition,Toast.LENGTH_SHORT).show()
            false

        }


        binding.mainWebViewId.apply {

            webChromeClient = WebChromeClient()
            setOnTouchListener(OnTouchListener { v, event ->
                if (event.action == MotionEvent.ACTION_UP && !longPressHandled) {
                    this.loadUrl(JavaScriptInjection.longPressJS)
                }
                false
            })
            setOnScrollChangeListener { view, x, y, ox, oy ->
                     yAxisPosition = y;
            }
            webViewClient = object : WebViewClient(){
                override fun onPageFinished(view: WebView?, url: String?) {
                    super.onPageFinished(view, url)

                    runBlocking {
                        getViewModelData()
                    }

                }
            }
            setWebContentsDebuggingEnabled(true)

            addJavascriptInterface(LongPressJavaScriptInterface(), "Android")

        }
        url?.let { binding.mainWebViewId.loadUrl(it) }

    }

    private fun getIntentValeus() {
        url = intent.getStringExtra("url")
    }

    private fun setUrl() {
        url = "https://medium.com/@tushartripathi301997/serializable-and-parcelable-310c93dc8c42"
    }

    private fun viewModelOperations() {
        setViewLevelViewModel()
        setDataViewModels()

    }

    private fun setViewLevelViewModel() {
        val viewModelJob = Job()
        val viewModelScope = CoroutineScope(Dispatchers.Default + viewModelJob)


        viewModel.longPressHandel.observe(this) { value ->
            runBlocking {
                viewModelScope.launch {
                    delay(500)
                    withContext(Dispatchers.Main)
                    {
                        longPressHandled = value
                    }
                }
            }
        }

        viewModel.notesVlaue.observe(this) { value ->
            runBlocking {
                saveValue(yAxisPosition, value)
            }
            resetValue()
        }

    }

    private fun setDataViewModels() {

        dbViewModel.notes.observe(this) { value ->

            for (note in value) {

                binding.mainWebViewId.evaluateJavascript(JavaScriptInjection.addNoteBulletJS(0,note.yAxis,note.id)){

                }
            }
        }
    }

    private suspend fun getViewModelData() {
        url?.let { dbViewModel.getValues(it, appDatabase) }
    }

    private suspend fun saveValue(yAxisPosition: Int?, value: String?) {
        val newNote = url?.let { yAxisPosition?.let { it1 -> NotesEntity( notes = value, yAxis = it1, url = it) } }
        withContext(Dispatchers.IO) {
            appDatabase.noteDao()?.insert(newNote)
            getViewModelData()
        }
    }

    private fun resetValue() {
        yAxisPosition=0;

    }


    inner class LongPressJavaScriptInterface {
        @JavascriptInterface
        fun onLongPress(text: String?) {
            if (!longPressHandled) {
                longPressHandled = true
                Log.d("asdfasdasdf","y acus valuye = "+ yAxisPosition)
                viewModel.setYazis(yAxisPosition)
                val addNoteBottomSheetFragment = AddNoteBottomSheet()
                addNoteBottomSheetFragment.show(supportFragmentManager, addNoteBottomSheetFragment.tag)

            }
        }

        @JavascriptInterface
        fun NoteClicked(id:Long)
        {
            viewModel.setShowNoteId(id);
            val readNoteBottomSheetFragment = ReadNoteBottomSheet()
            readNoteBottomSheetFragment.show(supportFragmentManager, readNoteBottomSheetFragment.tag)
        }
    }
}