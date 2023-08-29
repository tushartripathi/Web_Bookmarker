package com.example.webbookmarker

object JavaScriptInjection {
       var longPressJS =
        "javascript:(function() {" +
        "var elements = document.getElementsByTagName('*');" +
        "for (var i = 0; i < elements.length; i++) {" +
        "    elements[i].addEventListener('contextmenu', function(e) {" +
        "        e.preventDefault();" +
        "        Android.onLongPress(true);" +
        "    });" +
        "}" +
        "})();"


}