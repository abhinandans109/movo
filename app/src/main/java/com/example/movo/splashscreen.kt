package com.example.movo

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler

class splashscreen : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splashscreen)
        var handler=Handler()
        handler.postDelayed({
                 startActivity(Intent(applicationContext,MainActivity::class.java))
        },1500)
    }
}