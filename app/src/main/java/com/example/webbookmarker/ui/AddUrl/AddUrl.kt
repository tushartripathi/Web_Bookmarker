package com.example.webbookmarker.ui.AddUrl

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.webbookmarker.R
import com.example.webbookmarker.databinding.ActivityAddUrlBinding
import com.example.webbookmarker.ui.TakeNote.MainActivity

class AddUrl : AppCompatActivity() {

    lateinit var binding : ActivityAddUrlBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityAddUrlBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.addBtn.setOnClickListener{
            var url = binding.enterUrlET.text.toString().trim()
            if(url.isNotEmpty())
            {
                var intent = Intent(this, MainActivity::class.java)
                intent.putExtra("url",url)
                startActivity(intent)
            }
            else
            {
                //todo add error message for enter email
            }
        }
    }
}