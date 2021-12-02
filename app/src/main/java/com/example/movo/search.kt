package com.example.movo

import android.content.Intent
import android.media.Image
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
import androidx.cardview.widget.CardView
import androidx.constraintlayout.widget.ConstraintLayout
import com.android.volley.AuthFailureError
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.squareup.picasso.Picasso
import org.json.JSONArray
import org.json.JSONObject
import java.util.HashMap

class search : AppCompatActivity() {
    var movieID:String=""
    lateinit var list:ArrayList<String>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)
        if(intent.hasExtra("list")){
            list=intent.getStringArrayListExtra("list")!!
        }else{
            findViewById<ConstraintLayout>(R.id.constraintLayout).visibility=View.GONE
            searchMovie(intent.getStringExtra("moviename")!!)
            list= arrayListOf()
        }
        findViewById<ImageView>(R.id.search).setOnClickListener{
            searchMovie(findViewById<EditText>(R.id.sfrc_edtSearch).text.toString())
        }
        findViewById<Button>(R.id.addthismovie).setOnClickListener{
            addmovie(movieID)
        }


    }

    private fun addmovie(movieID: String) {
        list.add(movieID)
        var inn=Intent(applicationContext,createlist::class.java)
        inn.putExtra("list",list)
        inn.putExtra("listname",intent.getStringExtra("listname"))
        startActivity(inn)
    }

    private fun searchMovie(movie: String) {
        val url = "https://www.omdbapi.com/?t=$movie&plot=full&apikey=183144ca"
        val stringRequest: StringRequest = object : StringRequest(
            Method.POST, url,
            Response.Listener { response ->
                findViewById<LinearLayout>(R.id.data).visibility= View.VISIBLE
                var jsonObject= JSONObject(response)
                Picasso.get().load(jsonObject.getString("Poster")).into(findViewById<ImageView>(R.id.poster))
                findViewById<TextView>(R.id.title).text=jsonObject.getString("Title")
                findViewById<TextView>(R.id.year).text=jsonObject.getString("Year")
                findViewById<TextView>(R.id.runtime).text=jsonObject.getString("Runtime")
                findViewById<TextView>(R.id.genere).text=jsonObject.getString("Genre")
                findViewById<TextView>(R.id.actors).text=jsonObject.getString("Actors")
                findViewById<TextView>(R.id.language).text=jsonObject.getString("Language")
                findViewById<TextView>(R.id.imdb).text=jsonObject.getString("imdbRating")
                findViewById<TextView>(R.id.plot).text=jsonObject.getString("Plot")
                movieID=jsonObject.getString("imdbID")+"%"+jsonObject.getString("Title")
                if(intent.hasExtra("from")){
                    findViewById<CardView>(R.id.addthismoviecard).visibility=View.VISIBLE
                }else{

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

    }
}