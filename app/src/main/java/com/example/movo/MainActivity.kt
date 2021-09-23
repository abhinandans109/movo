package com.example.movo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.EditText
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.AuthFailureError
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import org.json.JSONArray
import org.json.JSONObject
import java.util.HashMap

class MainActivity : AppCompatActivity() {
    var listup= mutableListOf<movieModel>()
    var listtr= mutableListOf<movieModel>()
    var listpo= mutableListOf<movieModel>()
    lateinit var recyclerViewup:RecyclerView
    lateinit var recyclerViewtr:RecyclerView
    lateinit var recyclerViewpo:RecyclerView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        recyclerViewup=findViewById(R.id.upcoming)
        recyclerViewup.layoutManager=LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,true)
        recyclerViewup.adapter=rvAdapter(this,listup)
        recyclerViewtr=findViewById(R.id.trending)
        recyclerViewtr.layoutManager=LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,true)
        recyclerViewtr.adapter=rvAdapter(this,listtr)
        recyclerViewpo=findViewById(R.id.popular)
        recyclerViewpo.layoutManager=LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,true)
        recyclerViewpo.adapter=rvAdapter(this,listpo)
        getlatest()
        gettrnding()
        getpopular()

    }

    private fun getpopular() {
        val url = "https://api.themoviedb.org/3/movie/popular?api_key=21d6567f29a6acba96c962fbf1edb94b"
        val stringRequest: StringRequest = object : StringRequest(
            Method.POST, url,
            Response.Listener { response ->
                var jsonObject= JSONObject(response)
                Log.e("latest",response)
                val jsonArray:JSONArray= jsonObject.getJSONArray("results")
                for (i in 0 until jsonArray.length()) {
                    val item = jsonArray.getJSONObject(i)
                    listpo.add(movieModel(item.getString("poster_path"),item.getString("id"),item.getString("title")))
                    recyclerViewpo.adapter?.notifyDataSetChanged()
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

    private fun gettrnding() {
        val url = "https://api.themoviedb.org/3/movie/top_rated?api_key=21d6567f29a6acba96c962fbf1edb94b"
        val stringRequest: StringRequest = object : StringRequest(
            Method.POST, url,
            Response.Listener { response ->
                var jsonObject= JSONObject(response)
                Log.e("latest",response)
                val jsonArray:JSONArray= jsonObject.getJSONArray("results")
                for (i in 0 until jsonArray.length()) {
                    val item = jsonArray.getJSONObject(i)
                    listtr.add(movieModel(item.getString("poster_path"),item.getString("id"),item.getString("title")))
                    recyclerViewtr.adapter?.notifyDataSetChanged()
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

    private fun getlatest() {
        val url = "https://api.themoviedb.org/3/movie/upcoming?api_key=21d6567f29a6acba96c962fbf1edb94b"
        val stringRequest: StringRequest = object : StringRequest(
            Method.POST, url,
            Response.Listener { response ->
                var jsonObject= JSONObject(response)
                Log.e("latest",response)
               val jsonArray:JSONArray= jsonObject.getJSONArray("results")
                for (i in 0 until jsonArray.length()) {
                    val item = jsonArray.getJSONObject(i)
                    listup.add(movieModel(item.getString("poster_path"),item.getString("id"),item.getString("title")))
                    recyclerViewup.adapter?.notifyDataSetChanged()
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