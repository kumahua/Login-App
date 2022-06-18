package com.example.login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.login.databinding.ActivityLoginBinding
import com.google.firebase.auth.FirebaseAuth

class LoginActivity : AppCompatActivity() {
    private lateinit var binding : ActivityLoginBinding
    private lateinit var firebaseAuth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        firebaseAuth = FirebaseAuth.getInstance()

        binding.btnLogin.setOnClickListener {
            val email: String = binding.etEmail.text.toString().trim{it <= ' '}
            val pwd: String = binding.etPwd.text.toString().trim{it <= ' '}

            if(email.isEmpty()){
                Toast.makeText(this,"Please Enter Email !!",Toast.LENGTH_SHORT).show()
            }
            if(email.isNotEmpty() && pwd.isNotEmpty()) {
                firebaseAuth.signInWithEmailAndPassword(email, pwd).addOnCompleteListener {
                    if(it.isSuccessful) {
                        val intent =Intent(this,MainActivity::class.java)
                        startActivity(intent)
                    } else {
                        Toast.makeText(this, it.exception.toString(), Toast.LENGTH_SHORT).show()
                    }
                }
            } else {
                Toast.makeText(this, "Empty Fields Are not Allowed !!", Toast.LENGTH_SHORT).show()
            }

        }

        binding.tvSignUp.setOnClickListener {
            startActivity(Intent(this, RegisterActivity::class.java))
        }
    }

    override fun onStart() {
        super.onStart()
        //check if user is Logged
        if(firebaseAuth.currentUser != null) {
            startActivity(Intent(this,MainActivity::class.java))
        }
    }


//    private fun getName(email: String) : String? {
//        val name: StringBuffer = StringBuffer()
//        for(s in email) {
//            if(s != '@') {
//                name.append(s)
//            } else {
//                return name.toString()
//            }
//        }
//        return null
//    }
}