package com.example.login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.login.databinding.ActivityMainBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var auth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = Firebase.auth

        if(auth.currentUser != null) {
            auth.currentUser.let {
                initName(auth)
            }
        }

        binding.btnLogout.setOnClickListener {
            auth.signOut()
            startActivity(Intent(this,LoginActivity::class.java))
        }


        binding.btnChangePwd.setOnClickListener {
            startActivity(Intent(this,PasswordActivity::class.java))
        }
    }

    private fun initName(firebaseAuth: FirebaseAuth) {
        firebaseAuth.currentUser.let {
            binding.tvHello.text = "Hello " + it?.email
        }
    }
}