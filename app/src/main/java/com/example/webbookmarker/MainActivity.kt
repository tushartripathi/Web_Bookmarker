package com.example.webbookmarker

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.ContextMenu
import android.view.MotionEvent
import android.view.View
import android.view.View.OnTouchListener
import android.webkit.JavascriptInterface
import android.webkit.WebChromeClient
import android.webkit.WebSettings
import android.webkit.WebViewClient
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.webbookmarker.databinding.ActivityMainBinding
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
    var longPressHandled = false
    @SuppressLint("ClickableViewAccessibility")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val webSettings: WebSettings = binding.mainWebViewId.getSettings()
        webSettings.javaScriptEnabled = true

        binding.mainWebViewId.apply {
            webViewClient = WebViewClient()
            webChromeClient = WebChromeClient()
            setOnTouchListener(OnTouchListener { v, event ->
                if (event.action == MotionEvent.ACTION_UP && !longPressHandled) {

                    this.loadUrl(JavaScriptInjection.longPressJS)
                }
                false
            })
            addJavascriptInterface(LongPressJavaScriptInterface(), "Android")
            loadUrl("https://medium.com/@tushartripathi301997/databinding-viewbinding-5f907419c877")
        }

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




    }


    inner class LongPressJavaScriptInterface {
        @JavascriptInterface
        fun onLongPress(text: String?) {
            if (!longPressHandled) {
                longPressHandled = true
                val bottomSheetFragment = BottomSheet()
                bottomSheetFragment.show(supportFragmentManager, bottomSheetFragment.tag)

            }
        }
    }
}