package com.example.movo

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.util.Patterns
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.AuthFailureError
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.google.firebase.auth.FirebaseAuth
import java.util.*

class registration_page : AppCompatActivity() {
    private lateinit var auth:FirebaseAuth;
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registration_page)
        setSupportActionBar(findViewById(R.id.toolbar_register))
        supportActionBar?.setTitle("Register")
        findViewById<Button>(R.id.registerPage_input_button_submit).setOnClickListener {
            registerUser();
        }
        auth= FirebaseAuth.getInstance()
    }

    private fun registerUser() {
        if(Patterns.EMAIL_ADDRESS.matcher(findViewById<EditText>(R.id.registerPage_input_email).text).matches()) else{
            findViewById<EditText>(R.id.registerPage_input_email).error = "Enter a Valid Email"
            return
        }
        if(findViewById<EditText>(R.id.registerPage_input_name).text.isEmpty()){
            findViewById<EditText>(R.id.registerPage_input_name).error="Enter Your Name"
            return
        }
        if(findViewById<EditText>(R.id.registerPage_input_passowrd).text.isEmpty()){
            findViewById<EditText>(R.id.registerPage_input_passowrd).error="Enter Password"
            return
        }
        
        if(findViewById<EditText>(R.id.r_cnfpass).text.isEmpty()){
            findViewById<EditText>(R.id.r_cnfpass).error="Confirm password"
            return
        }
        if(findViewById<EditText>(R.id.r_cnfpass).text.toString()!=findViewById<EditText>(R.id.registerPage_input_passowrd).text.toString()){
            findViewById<EditText>(R.id.r_cnfpass).error="Passwords do no match"
            return
        }
        auth.createUserWithEmailAndPassword(findViewById<EditText>(R.id.registerPage_input_email).text.toString(),findViewById<EditText>(R.id.registerPage_input_passowrd).text.toString()).addOnCompleteListener{
            Log.e("ddvhoeihve",it.exception.toString())
            if(it.isSuccessful){
                Toast.makeText(applicationContext,"Registration Successful",Toast.LENGTH_SHORT).show()
                startActivity(Intent(applicationContext, MainActivity::class.java))
                finish()
            }else{
                Toast.makeText(applicationContext,"Registration Failed : "+ (it.exception?.localizedMessage
                    ?:""),Toast.LENGTH_SHORT).show()

            }
        }

    }
}