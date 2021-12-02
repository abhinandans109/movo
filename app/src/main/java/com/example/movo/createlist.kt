package com.example.movo

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.LinearLayout
import android.widget.Toast
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.firestore.DocumentSnapshot

import com.google.android.gms.tasks.OnCompleteListener

import com.google.firebase.firestore.DocumentReference

import com.google.firebase.firestore.FirebaseFirestore




class createlist : AppCompatActivity() {
    lateinit var recyclerView:RecyclerView
    lateinit var list:ArrayList<String>
    lateinit var list2:ArrayList<String>
    lateinit var adapter: recycleradapter
     var s:String=""
    var  db =Firebase.firestore
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_createlist)
        if(intent.hasExtra("listname"))
        s= intent.getStringExtra("listname")!!
        findViewById<Button>(R.id.addmovie).text="Click to add movie to $s"
        recyclerView=findViewById(R.id.createrv)
        list= arrayListOf()
        adapter= recycleradapter(applicationContext, list, null)
        recyclerView.adapter=adapter
        recyclerView.layoutManager=LinearLayoutManager(applicationContext)

        findViewById<Button>(R.id.addmovie).setOnClickListener{
            var intent=Intent(applicationContext,search::class.java)
            intent.putExtra("from","createlist")
            intent.putExtra("list",list)
            intent.putExtra("listname",s)
            startActivity(intent)
        }

        findViewById<Button>(R.id.share).setOnClickListener{
            findViewById<LinearLayout>(R.id.ppl).visibility=View.VISIBLE
        }
        findViewById<Button>(R.id.privately).setOnClickListener{
            val shareIntent = Intent()
            shareIntent.action = Intent.ACTION_SEND
            shareIntent.type="text/plain"
            shareIntent.putExtra(Intent.EXTRA_TEXT, "https://www.abhinandan.com/${FirebaseAuth.getInstance().uid.toString()}/${intent.getStringExtra("playlistname")}/private");
            startActivity(Intent.createChooser(shareIntent,"share"))
        }
        findViewById<Button>(R.id.publicaly).setOnClickListener{
            val shareIntent = Intent()
            shareIntent.action = Intent.ACTION_SEND
            shareIntent.type="text/plain"
            shareIntent.putExtra(Intent.EXTRA_TEXT, "https://www.abhinandan.com/${FirebaseAuth.getInstance().uid.toString()}/${intent.getStringExtra("playlistname")}/public");
            startActivity(Intent.createChooser(shareIntent,"share"))
        }
        if(intent.hasExtra("list")||intent.hasExtra("listfromadapter")){
            if(intent.hasExtra("list"))
            list= intent.getStringArrayListExtra("list")!!
            if(intent.hasExtra("listfromadapter")){
                findViewById<CardView>(R.id.cardView3).visibility=View.GONE
                findViewById<CardView>(R.id.cardView).visibility=View.GONE
                findViewById<CardView>(R.id.shareplaylist).visibility=View.VISIBLE
                list2= intent.getStringArrayListExtra("listfromadapter")!!
                adapter= recycleradapter(applicationContext, list2, null)
            }else{
                adapter= recycleradapter(applicationContext, list, null)
            }
            recyclerView.adapter=adapter
            recyclerView.layoutManager=LinearLayoutManager(applicationContext)
        }else{
             var uri=intent.data
            findViewById<CardView>(R.id.cardView3).visibility=View.GONE
            findViewById<CardView>(R.id.cardView).visibility=View.GONE
            if(uri!=null){
                var list3=uri.pathSegments
                var mode=list3[2];
                if(mode=="public"){
                    FirebaseFirestore.getInstance().collection("movies").document(list3[0]).get().addOnCompleteListener{
                        var ob=  it.result
                        var data=ob.data
                        if (data != null) {
                            for ((key,value) in data){
                                if(key==list3[1]){

                                    findViewById<CardView>(R.id.shareplaylist).visibility=View.VISIBLE
                                    adapter= recycleradapter(applicationContext,
                                        value as ArrayList<String>, null)
                                    recyclerView=findViewById(R.id.createrv)
                                    recyclerView.adapter=adapter
                                    recyclerView.layoutManager=LinearLayoutManager(applicationContext)

                                }
                            }
                        }
                    }
                }else if(list3[0]==FirebaseAuth.getInstance().uid.toString()){
                    FirebaseFirestore.getInstance().collection("movies").document(list3[0]).get().addOnCompleteListener{
                        var ob=  it.result
                        var data=ob.data
                        if (data != null) {
                            for ((key,value) in data){
                                if(key==list3[1]){
                                    findViewById<CardView>(R.id.cardView3).visibility=View.GONE
                                    findViewById<CardView>(R.id.cardView).visibility=View.GONE
                                    findViewById<CardView>(R.id.shareplaylist).visibility=View.VISIBLE
                                    adapter= recycleradapter(applicationContext,
                                        value as ArrayList<String>, null)
                                    recyclerView=findViewById(R.id.createrv)
                                    recyclerView.adapter=adapter
                                    recyclerView.layoutManager=LinearLayoutManager(applicationContext)

                                }
                            }
                        }
                    }
                }else{
                    Toast.makeText(applicationContext,"You dont have access to this list",Toast.LENGTH_SHORT).show()
                }
            }
        }
        findViewById<Button>(R.id.saveplaylist).setOnClickListener{
            var mp= mapOf(s to list)
            val rootRef = FirebaseFirestore.getInstance()
            val docIdRef = rootRef.collection("movies").document(FirebaseAuth.getInstance().uid.toString())
            docIdRef.get().addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val document = task.result
                    if (document.exists()) {
                        db.collection("movies").document(FirebaseAuth.getInstance().uid.toString()).update(mp).addOnCompleteListener{
                            if(it.isSuccessful){
                                startActivity(Intent(applicationContext,MainActivity::class.java))

                            }
                        }
                    } else {
                        db.collection("movies").document(FirebaseAuth.getInstance().uid.toString()).set(mp).addOnCompleteListener{
                            if(it.isSuccessful){
                                startActivity(Intent(applicationContext,MainActivity::class.java))

                            }
                        }
                    }
                }
            }

        }






    }
}