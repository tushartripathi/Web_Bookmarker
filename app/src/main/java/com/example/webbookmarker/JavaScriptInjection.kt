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


    fun getJSString(x:Int,y:Int, text:String):String
    {
        var str =  "javascript:(function() {"+
            "var helloDiv = document.createElement('div');"+
            "helloDiv.innerHTML = '"+text+"';"+
            "helloDiv.style.position = 'absolute';"+
            "helloDiv.style.left = '0px';"+
            "helloDiv.style.top = '"+y+"px';"+
            "helloDiv.style.color = 'black';"+
            "document.body.appendChild(helloDiv);"+
        "})();"
        return str
    }

}