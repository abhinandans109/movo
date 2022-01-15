package com.example.movo

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
import androidx.cardview.widget.CardView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.AuthFailureError
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.squareup.picasso.Picasso
import org.json.JSONArray
import org.json.JSONObject
import java.lang.Exception
import java.util.HashMap

class MainActivity : AppCompatActivity() {
    lateinit var list:ArrayList<movieModel>
    lateinit var adapter:recycleradapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        list= arrayListOf()
        adapter= recycleradapter(applicationContext,list)
        findViewById<RecyclerView>(R.id.rv_list).adapter=adapter
        findViewById<RecyclerView>(R.id.rv_list).layoutManager=LinearLayoutManager(applicationContext)
        addmovies()
        findViewById<ImageView>(R.id.home_search).setOnClickListener{
            startActivity(Intent(applicationContext,search::class.java))
        }

    }

    private fun addmovies() {
        val url = "https://api.tvmaze.com/search/shows?q=all"
        val stringRequest: StringRequest = object : StringRequest(
            Method.GET, url,
            Response.Listener { response ->
                try {
                    val jsonArray=JSONArray(response)
                    for (i in 0 until jsonArray.length()-1){
                        var obj=(jsonArray[i] as JSONObject).getJSONObject("show")
                        var genres=""
                        var gen=obj.getJSONArray("genres")
                        for (j in 0 until gen.length()-1)
                            genres+=gen[j]

                        var movieModel=movieModel(obj.getJSONObject("image").getString("medium"),obj.getString("summary"),obj.getString("name"),obj.getJSONObject("externals").getString("imdb"),obj.getString("language"),genres)
                        list.add(movieModel)
                        adapter.notifyDataSetChanged()

                    }
                }
                catch ( e: Exception){
                    Toast.makeText(applicationContext,e.localizedMessage,Toast.LENGTH_SHORT).show()
                }

            },
            Response.ErrorListener { error ->
                Log.e("responseerror",error!!.localizedMessage.toString())
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