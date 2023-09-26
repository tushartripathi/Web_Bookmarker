package com.example.webbookmarker.Web

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
    fun addNoteBulletJS(x:Int,y:Int, id:Long , color : String = "#fc4e03"):String
    {
        var str =  "javascript:(function() {"+
                "var bulletDiv = document.createElement('div');"+
                "bulletDiv.innerHTML = '•';"+ // Bullet point character
                "bulletDiv.style.position = 'absolute';"+
                "bulletDiv.style.left = '0px';"+
                "bulletDiv.style.top = '"+y+"px';"+
                "bulletDiv.style.fontSize = '80px';"+ // Set the font size to 80 pixels
                "bulletDiv.style.color = '#fc4e03';"+
                "bulletDiv.style.opacity = '0.5';"+ // Set the opacity to 0.5 (adjust as needed)
                "bulletDiv.style.cursor = 'pointer';"+ // Set the cursor to a pointer
                "bulletDiv.addEventListener('click', function() {"+
                "   Android.NoteClicked("+id+");"+
                "});"+
                "document.body.appendChild(bulletDiv);"+
                "})();"


        return str
    }

}