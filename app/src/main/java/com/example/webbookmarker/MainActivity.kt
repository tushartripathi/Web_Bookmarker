package com.example.webbookmarker

import android.os.Bundle
import android.util.Log
import android.view.MotionEvent
import android.view.View.OnTouchListener
import android.webkit.JavascriptInterface
import android.webkit.WebChromeClient
import android.webkit.WebSettings
import android.webkit.WebViewClient
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.webbookmarker.databinding.ActivityMainBinding
import kotlinx.coroutines.delay


class MainActivity : AppCompatActivity() {
    lateinit var binding : ActivityMainBinding
    val viewModel: MainViewModel by lazy {
        ViewModelProvider(this).get(MainViewModel::class.java)
    }

    var longPressHandled = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val webSettings: WebSettings = binding.mainWebViewId.getSettings()
        webSettings.javaScriptEnabled = true

        binding.mainWebViewId.setWebViewClient(WebViewClient())
        binding.mainWebViewId.setWebChromeClient(WebChromeClient())

        // Set up long-press listener
        // Set up long-press listener
        binding.mainWebViewId.setOnTouchListener(OnTouchListener { v, event ->
            Log.d("sdssfs12",event.action.toString())
            if (event.action == MotionEvent.ACTION_UP) {
                // Inject JavaScript to detect long-press
                binding.mainWebViewId.loadUrl(
                    "javascript:(function() {" +
                            "var elements = document.getElementsByTagName('*');" +
                            "for (var i = 0; i < elements.length; i++) {" +
                            "    elements[i].addEventListener('contextmenu', function(e) {" +
                            "        e.preventDefault();" +
                            "        Android.onLongPress(this.innerText);" +
                            "    });" +
                            "}" +
                            "})();"
                )
            }
            false
        })

        viewModel.longPressHandel.observe(this,{value->
            Log.d("sdssfs","valyue update = " + value)
            longPressHandled=value
        })

        // JavaScript interface for receiving long-press events

        // JavaScript interface for receiving long-press events
        binding.mainWebViewId.addJavascriptInterface(LongPressJavaScriptInterface(), "Android")


        binding.mainWebViewId.loadUrl("https://medium.com/@tushartripathi301997/databinding-viewbinding-5f907419c877")


    }


    inner class LongPressJavaScriptInterface {
        @JavascriptInterface
        fun onLongPress(text: String?) {
            if (!longPressHandled) {
                longPressHandled = true;

                Log.d("sdssfs", "onlOngperss () "+text)
                val bottomSheetFragment = BottomSheet()
                bottomSheetFragment.show(supportFragmentManager, bottomSheetFragment.tag)
                Thread.sleep(1000)
              //  longPressHandled = false;
            }
        }
    }
}