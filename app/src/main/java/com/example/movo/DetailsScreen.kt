package com.example.movo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import com.squareup.picasso.Picasso

class DetailsScreen : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_details_screen)
        var movie=intent.getSerializableExtra("data") as movieModel
        Picasso.get().load(movie.poster).into(findViewById<ImageView>(R.id.poster))
        findViewById<TextView>(R.id.title).text=movie.title
        findViewById<TextView>(R.id.imdb).text=movie.imdb
        findViewById<TextView>(R.id.plot).text=movie.plot
        findViewById<TextView>(R.id.genere).text=movie.genres
        findViewById<TextView>(R.id.language).text=movie.language



    }
}