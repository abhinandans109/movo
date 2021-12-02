package com.example.movo

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        FirebaseFirestore.getInstance().collection("movies").document(FirebaseAuth.getInstance().uid.toString()).get().addOnCompleteListener{
          var ob=  it.result
            var data=ob.data
            if (data != null) {
                var r=findViewById<RecyclerView>(R.id.rv_list)
                var l:ArrayList<String> = arrayListOf()
                var movies:MutableMap<String,ArrayList<String>> = hashMapOf()
                r.adapter=recycleradapter(applicationContext,l,movies)
                r.layoutManager=LinearLayoutManager(applicationContext)
                for ((key,value) in data){
                    var i=key
                    l.add(i)
                    movies.put(i, value as ArrayList<String>)
                    r.adapter=recycleradapter(applicationContext, l, movies)
                }
            }
        }
        findViewById<Button>(R.id.createlist).setOnClickListener{
            findViewById<ConstraintLayout>(R.id.consplaylistname).visibility= View.VISIBLE
        }
        findViewById<Button>(R.id.go).setOnClickListener{
            var intent=Intent(this,createlist::class.java)
            var e:String=findViewById<EditText>(R.id.listname).text.toString()
            intent.putExtra("listname",e)
            startActivity(intent)
        }


    }

}