package com.example.login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
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
            initName(auth)
        }

        binding.btnLogout.setOnClickListener {
            auth.signOut()
            startActivity(Intent(this,LoginActivity::class.java))
            overridePendingTransition(androidx.appcompat.R.anim.abc_fade_in,
                com.google.android.material.R.anim.abc_fade_out)
        }

        binding.btnChangePwd.setOnClickListener {
            startActivity(Intent(this,PasswordActivity::class.java))
            overridePendingTransition(androidx.appcompat.R.anim.abc_fade_in,
                com.google.android.material.R.anim.abc_fade_out)
        }
    }

    private fun initName(firebaseAuth: FirebaseAuth) {
        firebaseAuth.currentUser.let {
            binding.tvName.text = getName(it?.email.toString())
        }
    }

    private fun getName(email: String): String {
        val name = StringBuffer()
        for(e in email) {
            if(e != '@') {
                name.append(e)
            } else {
                return name.toString()
            }
        }
        return name.toString()
    }

    override fun onBackPressed() {
        Toast.makeText(this, "Press log out to exit", Toast.LENGTH_SHORT)
            .show()
    }
}