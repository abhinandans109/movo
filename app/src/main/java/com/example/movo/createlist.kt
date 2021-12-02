package com.example.movo

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
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
        if(intent.hasExtra("list")||intent.hasExtra("listfromadapter")){
            if(intent.hasExtra("list"))
            list= intent.getStringArrayListExtra("list")!!
            if(intent.hasExtra("listfromadapter")){
                findViewById<CardView>(R.id.cardView3).visibility=View.GONE
                findViewById<CardView>(R.id.cardView).visibility=View.GONE
                list2= intent.getStringArrayListExtra("listfromadapter")!!
                adapter= recycleradapter(applicationContext, list2, null)
            }else{
                adapter= recycleradapter(applicationContext, list, null)
            }
            recyclerView.adapter=adapter
            recyclerView.layoutManager=LinearLayoutManager(applicationContext)
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