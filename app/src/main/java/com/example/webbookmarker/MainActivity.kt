package com.example.webbookmarker

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.ContextMenu
import android.view.MotionEvent
import android.view.View
import android.view.View.OnTouchListener
import android.view.ViewGroup
import android.webkit.JavascriptInterface
import android.webkit.WebChromeClient
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebView.setWebContentsDebuggingEnabled
import android.webkit.WebViewClient
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout.LayoutParams.START
import androidx.constraintlayout.widget.ConstraintLayout.LayoutParams.TOP
import androidx.constraintlayout.widget.ConstraintSet
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
    var yAxisPosition =0
    @SuppressLint("ClickableViewAccessibility")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val webSettings: WebSettings = binding.mainWebViewId.getSettings()
        webSettings.javaScriptEnabled = true

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
            Log.d("sdsdsdf","hello")
            webViewClient = object : WebViewClient(){
                override fun onPageFinished(view: WebView?, url: String?) {
                    super.onPageFinished(view, url)

                    //todo remove this
                    view?.loadUrl(JavaScriptInjection.getJSString(0,200,"this"))

                }
            }
            setWebContentsDebuggingEnabled(true)

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




//        val xCoordinate = 100 // X-coordinate in pixels
//        val yCoordinate = 200 // Y-coordinate in pixels
//
//        val textView = TextView(this)
//        textView.id = View.generateViewId()
//        textView.text = "Dynamically Positioned TextView"
//
//        binding.parentLayout.addView(textView)
//
//        // Update the position of the TextView using ConstraintSet
//        val constraintSet = ConstraintSet()
//        constraintSet.clone(binding.parentLayout)
//        constraintSet.connect(
//            textView.id,
//            START,
//            binding.parentLayout.id,
//            START,
//            xCoordinate
//        )
//        constraintSet.connect(
//            textView.id,
//            TOP,
//            binding.parentLayout.id,
//            TOP,
//            yCoordinate
//        )
//        constraintSet.applyTo(binding.parentLayout)

    }





    inner class LongPressJavaScriptInterface {
        @JavascriptInterface
        fun onLongPress(text: String?) {
            if (!longPressHandled) {
                longPressHandled = true
                viewModel.setYazis(yAxisPosition)
                val bottomSheetFragment = BottomSheet()
                bottomSheetFragment.show(supportFragmentManager, bottomSheetFragment.tag)

            }
        }
    }
}