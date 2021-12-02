package com.example.movo

import android.app.ActivityOptions
import android.content.ContentValues.TAG
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.util.Patterns
import android.view.View
import android.widget.*
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.AuthFailureError
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.*
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import java.util.*


class Login_Page : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    //    private lateinit var MAuth: FirebaseAuth
    private val RC_SIGN_IN: Int = 123
    private lateinit var googleSignInClient: GoogleSignInClient
    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if(FirebaseAuth.getInstance().currentUser!=null){
            startActivity(Intent(applicationContext,MainActivity::class.java))
            finish()
        }
        supportActionBar?.title="CashCut"
        setContentView(R.layout.activity_login_page)
        auth = FirebaseAuth.getInstance()
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken("161467446806-5cuf9u7o6tsruhied0l8a7v5hagfi03o.apps.googleusercontent.com")
            .requestEmail()
            .build()
        googleSignInClient = GoogleSignIn.getClient(this, gso)
        auth = Firebase.auth

        findViewById<ImageView>(R.id.SignInButton).setOnClickListener {
            signIn()
        }
        findViewById<Button>(R.id.login).setOnClickListener {
            signupuser()
            findViewById<ProgressBar>(R.id.pb_login).visibility=View.VISIBLE
        }
        findViewById<TextView>(R.id.loginpage_register).setOnClickListener{
            val intent = Intent(this, registration_page::class.java)
            startActivity(intent)
        }
    }
    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    private fun signupuser() {
        if (findViewById<EditText>(R.id.username).text.toString().isEmpty()) {
            findViewById<EditText>(R.id.username).error = "Please Enter Email"
            findViewById<EditText>(R.id.username).requestFocus()
            return
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(findViewById<EditText>(R.id.username).text.toString())
                .matches()
        ) {
            findViewById<EditText>(R.id.username).error = "Please Enter Valid Email"
            findViewById<EditText>(R.id.username).requestFocus()
            return
        }
        if (findViewById<EditText>(R.id.password).text.toString().isEmpty()) {
            findViewById<EditText>(R.id.password).error = "Please Enter Password"
            findViewById<EditText>(R.id.password).requestFocus()
            return

        }

        auth.signInWithEmailAndPassword(findViewById<EditText>(R.id.username).text.toString(), findViewById<EditText>(
            R.id.password
        ).text.toString())
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    val user = auth.currentUser

                    updateUI2(user)
                } else {
                    // If sign in fails, display a message to the user.
                    Toast.makeText(baseContext, "Authentication failed."+task.exception.toString(),
                        Toast.LENGTH_SHORT).show()
                    updateUI2(null)
                }
            }
    }



    private fun signIn() {
        val signInIntent = googleSignInClient.signInIntent
        startActivityForResult(signInIntent, RC_SIGN_IN)
    }

    override fun onStart() {
        super.onStart()
        // Check if user is signed in (non-null) and update UI accordingly.
        val currentUser = auth.currentUser
        updateUI(currentUser)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            handleSignInResult(task)
        }
    }

    private fun handleSignInResult(completedTask: Task<GoogleSignInAccount>) {

        try {
            val account =
                completedTask.getResult(ApiException::class.java)!!
            Log.d(TAG, "firebaseAuthWithGoogle:" + account.id)
            firebaseAuthWithGoogle(account.idToken!!)
        } catch (e: ApiException) {
            Log.w(TAG, "signInResult:failed code=" + e.statusCode)
        }

    }

    private fun firebaseAuthWithGoogle(idToken: String) {
        val credential = GoogleAuthProvider.getCredential(idToken, null)
        auth.signInWithCredential(credential)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Toast.makeText(this,"Login Sucessful",Toast.LENGTH_SHORT).show()
                    val user = auth.currentUser
                    updateUI(user)
                } else {
                    // If sign in fails, display a message to the user.
                    Log.w(TAG, "signInWithCredential:failure", task.exception)
                    Toast.makeText(this,"Login Failed Try Again",Toast.LENGTH_SHORT).show()

                    updateUI(null)
                }
            }
    }

    private fun updateUI(firebaseUser: FirebaseUser?) = if (firebaseUser != null) {
        val mainActivityIntent = Intent(this, MainActivity::class.java)
        startActivity(mainActivityIntent)
        finish()
    }else{}

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    private fun updateUI2(currentUser: FirebaseUser?) {
        findViewById<ProgressBar>(R.id.pb_login)
        if(currentUser != null) {
            val mainActivityIntent = Intent(this, MainActivity::class.java)
            startActivity(mainActivityIntent)
            finish()
        }else {
            Toast.makeText(baseContext, "Authentication failed.",
                Toast.LENGTH_SHORT).show()
        }
    }
}



