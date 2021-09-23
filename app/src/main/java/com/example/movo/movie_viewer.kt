package com.example.movo

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import android.widget.TextView
import com.android.volley.AuthFailureError
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView
import org.json.JSONArray
import org.json.JSONObject
import java.util.HashMap

class movie_viewer : AppCompatActivity() {
    var vidid:String = "yWsnHPpOBdo"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movie_viewer)
        var title=findViewById<TextView>(R.id.tvTitleimg)
        title.text = intent.getStringExtra("title")
        findViewById<ImageView>(R.id.navback).setOnClickListener{
            startActivity(Intent(this,MainActivity::class.java))
            finish()
        }
        val url = "https://api.themoviedb.org/3/movie/"+intent.getStringExtra("id")+"/videos?api_key=21d6567f29a6acba96c962fbf1edb94b"
        val stringRequest: StringRequest = object : StringRequest(
            Method.GET, url,
            Response.Listener { response ->
                var jsonObject= JSONObject(response)
                Log.e("latest",response)
                val jsonArray: JSONArray = jsonObject.getJSONArray("results")
                if(jsonArray.length()>0) {
                    val item = jsonArray.getJSONObject(0)
                    vidid = item.getString("key")
                }
            },
            Response.ErrorListener { error ->
                Log.e("responseerror",error.localizedMessage)
            }) {
            @Throws(AuthFailureError::class)
            override fun getParams(): Map<String, String>? {
                val params: MutableMap<String, String> = HashMap()
                return params
            }
        }
        val requestQueue = Volley.newRequestQueue(this)
        requestQueue.add(stringRequest)

        val youTubePlayerView:YouTubePlayerView=findViewById(R.id.youtube_player_view)
        lifecycle.addObserver(youTubePlayerView)
        youTubePlayerView.addYouTubePlayerListener(object:AbstractYouTubePlayerListener (){
            override fun onReady(youTubePlayer: YouTubePlayer) {
                val videoId = vidid
                youTubePlayer.loadVideo(videoId,0f)
                youTubePlayer.pause()
            }
        })

    }

}