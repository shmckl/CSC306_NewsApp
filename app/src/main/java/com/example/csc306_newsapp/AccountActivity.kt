package com.example.csc306_newsapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

/**
 * User information page
 */
class AccountActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_account)

        var mAuth = Firebase.auth

        //Sign-out button functionality
        var username = mAuth.currentUser?.email
        findViewById<TextView>(R.id.welcome).setText("Welcome\n$username")
        findViewById<Button>(R.id.btn_logout).setOnClickListener{
            mAuth.signOut()
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}